package ca.qc.ircm.prohits2perseus.prohits;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.qc.ircm.prohits2perseus.test.config.NonTransactionalTestAnnotations;
import com.opencsv.CSVParser;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@NonTransactionalTestAnnotations
public class PerseusNormalizerTest {
  @Autowired
  private PerseusNormalizer perseusNormalizer;
  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  @Test
  public void normalize() throws Throwable {
    File sampleCompare = new File(getClass().getResource("/comparison_matrix.csv").toURI());
    List<String> rawlinesInput = Files.readAllLines(sampleCompare.toPath());
    List<List<String>> linesInput = new ArrayList<>();
    for (int i = 8; i < rawlinesInput.size(); i++) {
      linesInput.add(Stream.of(new CSVParser().parseLine(rawlinesInput.get(i))).limit(10)
          .collect(Collectors.toList()));
    }
    List<List<String>> output = perseusNormalizer.normalize(linesInput, "POLR3B", 1, 4);
    assertEquals(linesInput.get(0), output.get(0));
    List<Double> multipliers = DoubleStream.of(0.0, 0.0, 0.0, 0.0, 605.0 / 599, 605.0 / 584,
        605.0 / 574, 605.0 / 599, 605.0 / 551, 605.0 / 605).boxed().collect(Collectors.toList());
    assertEquals(5208, output.size());
    for (int i = 1; i < output.size(); i++) {
      List<String> inputLine = linesInput.get(i);
      List<String> outputLine = output.get(i);
      for (int j = 0; j < 4; j++) {
        assertEquals(inputLine.get(j), outputLine.get(j));
      }
      assertEquals(inputLine.size(), outputLine.size());
      for (int j = 4; j < inputLine.size(); j++) {
        double value = inputLine.get(j).isEmpty() ? 0.9 : Double.parseDouble(inputLine.get(j));
        assertEquals(String.valueOf(value * multipliers.get(j)), outputLine.get(j));
      }
    }
  }
}
