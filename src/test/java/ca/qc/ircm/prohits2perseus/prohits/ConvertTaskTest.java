package ca.qc.ircm.prohits2perseus.prohits;

import static org.junit.Assert.fail;

import ca.qc.ircm.prohits2perseus.sample.Sample;
import ca.qc.ircm.prohits2perseus.test.config.TestFxTestAnnotations;
import java.io.File;
import java.util.List;
import java.util.Locale;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testfx.framework.junit.ApplicationTest;

@RunWith(SpringJUnit4ClassRunner.class)
@TestFxTestAnnotations
public class ConvertTaskTest extends ApplicationTest {
  @Autowired
  private ConvertTaskFactory factory;
  @MockBean
  private SampleCompareParser parser;
  @MockBean
  private PerseusConverter converter;
  @MockBean
  private PerseusNormalizer normalizer;
  @Rule
  public TemporaryFolder folder = new TemporaryFolder();
  private ConvertTask task;
  private File input;
  @Mock
  private List<Sample> samples;
  private boolean normalize = false;
  @Mock
  private NormalizeMetadata normalizeMetadata;
  private Locale locale = Locale.ENGLISH;

  @Before
  public void beforeTest() throws Throwable {
    input = folder.newFile("input.csv");
    task = factory.create(input, samples, normalize, normalizeMetadata, locale);
  }

  @Test
  public void call() throws Throwable {
    fail("Program test");
  }

  @Test
  public void call_Normalize() throws Throwable {
    fail("Program test");
  }

  @Test
  public void call_IOException() throws Throwable {
    fail("Program test");
  }
}
