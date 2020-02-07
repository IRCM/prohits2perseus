package ca.qc.ircm.prohits2perseus.prohits;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.qc.ircm.prohits2perseus.sample.Sample;
import ca.qc.ircm.prohits2perseus.test.config.NonTransactionalTestAnnotations;
import com.opencsv.CSVParser;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@NonTransactionalTestAnnotations
public class PerseusConverterTest {
  @Autowired
  private PerseusConverter perseusConverter;
  @Rule
  public TemporaryFolder folder = new TemporaryFolder();
  private List<Sample> samples = new ArrayList<>();

  @Before
  public void beforeTest() {
    samples.add(perseus("test 1"));
    samples.add(perseus("test 2"));
    samples.add(perseus("test 3"));
    samples.add(perseus("test 4"));
    samples.add(perseus("test 5"));
    samples.add(perseus("test 6"));
    samples.add(perseus("test 7"));
    samples.add(perseus("test 8"));
    samples.add(perseus("test 9"));
  }

  private Sample perseus(String perseus) {
    Sample sample = new Sample();
    sample.setPerseus(perseus);
    return sample;
  }

  @Test
  public void toPerseus() throws Throwable {
    File sampleCompare = new File(getClass().getResource("/comparison_matrix.csv").toURI());
    List<String> rawlinesInput = Files.readAllLines(sampleCompare.toPath());
    List<List<String>> linesInput = new ArrayList<>();
    for (int i = 8; i < rawlinesInput.size(); i++) {
      linesInput.add(
          Stream.of(new CSVParser().parseLine(rawlinesInput.get(i))).collect(Collectors.toList()));
    }
    List<List<String>> output = perseusConverter.toPerseus(linesInput, 4, samples);
    List<String> header = output.get(0);
    assertEquals("Gene ID", header.get(0));
    assertEquals("Gene Name", header.get(1));
    assertEquals("LocusTag", header.get(2));
    assertEquals("Protein ID", header.get(3));
    for (int i = 0; i < samples.size(); i++) {
      assertEquals(samples.get(i).getPerseus(), header.get(4 + i));
    }
    assertEquals(5208, output.size());
    for (int i = 1; i < output.size(); i++) {
      assertEquals(linesInput.get(i), output.get(i));
    }
  }
}
