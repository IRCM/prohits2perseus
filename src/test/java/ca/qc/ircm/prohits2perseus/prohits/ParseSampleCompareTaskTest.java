package ca.qc.ircm.prohits2perseus.prohits;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.qc.ircm.prohits2perseus.test.config.TestFxTestAnnotations;
import java.io.File;
import java.io.IOException;
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
@TestFxTestAnnotations
public class ParseSampleCompareTaskTest extends ApplicationTest {
  @Autowired
  private ParseSampleCompareTaskFactory factory;
  @MockBean
  private SampleCompareParser parser;
  @Mock
  private SampleCompareMetadata metadata;
  @Mock
  private ChangeListener<String> messageChangeListener;
  @Mock
  private ChangeListener<Number> progressChangeListener;
  private ParseSampleCompareTask task;
  private File file = new File("test");
  private Locale locale = Locale.ENGLISH;

  @Before
  public void beforeTest() {
    task = factory.create(file, locale);
  }

  @Test
  public void call() throws Throwable {
    when(parser.parseMetadata(any())).thenReturn(metadata);
    task.messageProperty().addListener(messageChangeListener);
    task.progressProperty().addListener(progressChangeListener);
    SampleCompareMetadata metadata = task.call();
    assertEquals(this.metadata, metadata);
    verify(parser).parseMetadata(file);
    verify(messageChangeListener, atLeastOnce()).changed(any(), any(String.class),
        any(String.class));
    verify(progressChangeListener, atLeastOnce()).changed(any(), any(Number.class),
        any(Number.class));
  }

  @Test
  public void call_IOException() throws Throwable {
    when(parser.parseMetadata(any())).thenThrow(new IOException("test"));
    try {
      task.call();
      fail("Expected IOException");
    } catch (IOException e) {
      verify(parser).parseMetadata(file);
    }
  }
}
