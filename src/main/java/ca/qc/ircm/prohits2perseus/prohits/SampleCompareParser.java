package ca.qc.ircm.prohits2perseus.prohits;

import com.opencsv.CSVParser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

/**
 * Parses sample comparison file.
 */
@Component
public class SampleCompareParser {
  private static final String GENE_ID_COLUMN = "Gene ID";
  private static final String GENE_NAME_COLUMN = "Gene Name";

  /**
   * Parses sample comparison file metadata.
   *
   * @param file
   *          sample comparison file
   * @return metadata
   * @throws IOException
   *           could not read file
   */
  public SampleCompareMetadata parseMetadata(File file) throws IOException {
    CSVParser csvParser = new CSVParser();
    List<String> rawlines = Files.readAllLines(file.toPath());
    List<String[]> lines = new ArrayList<>();
    for (int i = 0; i < rawlines.size(); i++) {
      lines.add(csvParser.parseLine(rawlines.get(i)));
    }
    int headerIndex = IntStream.range(0, rawlines.size())
        .filter(i -> lines.get(i)[0].equals(GENE_ID_COLUMN)).findFirst().orElseThrow(
            () -> new IOException("File " + file + " not a Prohits sample comparison file"));
    int geneNameIndex = Arrays.asList(lines.get(headerIndex)).indexOf(GENE_NAME_COLUMN);
    SampleCompareMetadata metadata = new SampleCompareMetadata();
    String[] sampleColumns = lines.get(headerIndex - 2);
    metadata.samples =
        Stream.of(sampleColumns).filter(sample -> !sample.isEmpty()).collect(Collectors.toList());
    metadata.genes = lines.stream().skip(headerIndex + 1).map(columns -> columns[geneNameIndex])
        .collect(Collectors.toList());
    return metadata;
  }
}
