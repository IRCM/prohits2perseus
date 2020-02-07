package ca.qc.ircm.prohits2perseus.prohits;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.qc.ircm.prohits2perseus.test.config.NonTransactionalTestAnnotations;
import com.opencsv.CSVParser;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@NonTransactionalTestAnnotations
public class SampleCompareParserTest {
  @Autowired
  private SampleCompareParser parser;

  @Test
  public void parseMetadata() throws Throwable {
    File file = new File(getClass().getResource("/comparison_matrix.csv").toURI());
    SampleCompareMetadata metadata = parser.parseMetadata(file);
    assertEquals(9, metadata.samples.size());
    assertEquals("6 OF_20200131_COU_10(C-3xFLAG) 55703", metadata.samples.get(0));
    assertEquals("5 OF_20200131_COU_09(C-3xFLAG) 55703", metadata.samples.get(1));
    assertEquals("4 OF_20200131_COU_05(C-3xFLAG) 55703", metadata.samples.get(2));
    assertEquals("3 OF_20200131_COU_11(C-3xFLAG) 55703", metadata.samples.get(3));
    assertEquals("2 OF_20200131_COU_07(C-3xFLAG) 55703", metadata.samples.get(4));
    assertEquals("1 OF_20200131_COU_06(C-3xFLAG) 55703", metadata.samples.get(5));
    assertEquals("9 OF_20200131_COU_03 NA", metadata.samples.get(6));
    assertEquals("8 OF_20200131_COU_02 NA", metadata.samples.get(7));
    assertEquals("7 OF_20200131_COU_01 NA", metadata.samples.get(8));
    assertEquals(5207, metadata.genes.size());
    assertEquals("POLR3B", metadata.genes.get(0));
    assertEquals("TUBB4B", metadata.genes.get(1));
    assertEquals("AGT", metadata.genes.get(metadata.genes.size() - 2));
    assertEquals("BACE1", metadata.genes.get(metadata.genes.size() - 1));
    assertEquals(4, metadata.samplesStartColumnNumber);
    assertEquals(8, metadata.headerLineNumber);
  }

  @Test
  public void parse() throws Throwable {
    File file = new File(getClass().getResource("/comparison_matrix.csv").toURI());
    List<List<String>> lines = parser.parse(file);
    List<String> rawlinesExpected = Files.readAllLines(file.toPath());
    List<List<String>> linesExpected = new ArrayList<>();
    for (int i = 0; i < rawlinesExpected.size(); i++) {
      linesExpected.add(Stream.of(new CSVParser().parseLine(rawlinesExpected.get(i)))
          .collect(Collectors.toList()));
    }
    List<String> header = lines.get(0);
    assertEquals("Gene ID", header.get(0));
    assertEquals("Gene Name", header.get(1));
    assertEquals("LocusTag", header.get(2));
    assertEquals("Protein ID", header.get(3));
    assertEquals("6 OF_20200131_COU_10(C-3xFLAG) 55703", header.get(4));
    assertEquals("5 OF_20200131_COU_09(C-3xFLAG) 55703", header.get(5));
    assertEquals("4 OF_20200131_COU_05(C-3xFLAG) 55703", header.get(6));
    assertEquals("3 OF_20200131_COU_11(C-3xFLAG) 55703", header.get(7));
    assertEquals("2 OF_20200131_COU_07(C-3xFLAG) 55703", header.get(8));
    assertEquals("1 OF_20200131_COU_06(C-3xFLAG) 55703", header.get(9));
    assertEquals("9 OF_20200131_COU_03 NA", header.get(10));
    assertEquals("8 OF_20200131_COU_02 NA", header.get(11));
    assertEquals("7 OF_20200131_COU_01 NA", header.get(12));
    for (int i = 1; i < lines.size(); i++) {
      assertEquals(linesExpected.get(i + 8), lines.get(i));
    }
  }
}
