package ca.qc.ircm.prohits2perseus.prohits;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.qc.ircm.prohits2perseus.sample.Sample;
import ca.qc.ircm.prohits2perseus.sample.SampleRepository;
import ca.qc.ircm.prohits2perseus.sample.SampleService;
import ca.qc.ircm.prohits2perseus.test.config.TransactionalFxTestAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javafx.beans.value.ChangeListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testfx.framework.junit.ApplicationTest;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalFxTestAnnotations
public class FetchSamplesTaskTest extends ApplicationTest {
  @Autowired
  private FetchSamplesTaskFactory factory;
  @MockBean
  private SampleService service;
  @Mock
  private SampleCompareMetadata metadata;
  @Mock
  private ChangeListener<String> messageChangeListener;
  @Mock
  private ChangeListener<Number> progressChangeListener;
  @Autowired
  private SampleRepository repository;
  private Sample sample1;
  private Sample sample2;
  private Sample sample3;
  private FetchSamplesTask task;
  private Locale locale = Locale.ENGLISH;

  @Before
  public void beforeTest() {
    metadata.samples = new ArrayList<>();
    task = factory.create(metadata, locale);
    sample1 = repository.findById(1L).get();
    sample2 = repository.findById(4L).get();
    sample3 = repository.findById(7L).get();
  }

  @Test
  public void call() throws Throwable {
    metadata.samples.add("test 1");
    metadata.samples.add("test 2");
    metadata.samples.add("test 3");
    when(metadata.id(any())).thenReturn(1L, 2L, 3L);
    when(service.get(any())).thenReturn(sample1, sample2, sample3);
    task.messageProperty().addListener(messageChangeListener);
    task.progressProperty().addListener(progressChangeListener);
    List<Sample> samples = task.call();
    assertEquals(3, samples.size());
    assertEquals(sample1, samples.get(0));
    assertFalse(samples.get(0).isControl());
    assertEquals(sample2, samples.get(1));
    assertFalse(samples.get(1).isControl());
    assertEquals(sample3, samples.get(2));
    assertTrue(samples.get(2).isControl());
    verify(metadata).id("test 1");
    verify(metadata).id("test 2");
    verify(metadata).id("test 3");
    verify(service).get(1L);
    verify(service).get(2L);
    verify(service).get(3L);
    verify(messageChangeListener, atLeastOnce()).changed(any(), any(String.class),
        any(String.class));
    verify(progressChangeListener, atLeastOnce()).changed(any(), any(Number.class),
        any(Number.class));
  }

  @Test
  public void call_IOException() throws Throwable {
    metadata.samples.add("test 1");
    metadata.samples.add("test 2");
    metadata.samples.add("test 3");
    when(metadata.id(any())).thenReturn(1L, 2L, 3L);
    when(service.get(any())).thenThrow(new IllegalArgumentException("test"));
    try {
      task.call();
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      verify(service).get(1L);
    }
  }
}
