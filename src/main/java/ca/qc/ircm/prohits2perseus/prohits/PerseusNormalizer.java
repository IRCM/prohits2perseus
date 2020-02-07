package ca.qc.ircm.prohits2perseus.prohits;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;

/**
 * Normalizes Perseus data.
 */
@Component
public class PerseusNormalizer {
  /**
   * Normalizes Perseus data based on gene.
   *
   * @param data
   *          data
   * @param gene
   *          gene
   * @param geneIndex
   *          index of gene column
   * @param samplesStartIndex
   *          index of first sample
   * @return normalized Perseus data
   */
  public List<List<String>> normalize(List<List<String>> data, String gene, int geneIndex,
      int samplesStartIndex) {
    int geneLineNumber = IntStream.range(1, data.size())
        .filter(i -> data.get(i).get(geneIndex).equals(gene)).findFirst().orElse(-1);
    if (geneLineNumber == -1) {
      throw new IllegalArgumentException("gene " + gene + " not found in data");
    }
    for (int i = samplesStartIndex; i < data.get(geneLineNumber).size(); i++) {
      String value = data.get(geneLineNumber).get(i);
      try {
        Double.parseDouble(value);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("gene value " + value + " not a number");
      }
    }
    List<Double> geneValues = data.get(geneLineNumber).stream().skip(samplesStartIndex)
        .map(value -> Double.parseDouble(value)).collect(Collectors.toList());
    double max = geneValues.stream().mapToDouble(v -> v).max().orElse(1.0);
    List<Double> multiplier =
        geneValues.stream().map(value -> max / value).collect(Collectors.toList());
    List<List<String>> output = new ArrayList<>();
    output.add(new ArrayList<>(data.get(0)));
    // Normalize.
    data.stream().skip(1).forEach(source -> {
      List<String> line = new ArrayList<>(source);
      IntStream.range(samplesStartIndex, line.size()).forEach(index -> {
        String rawvalue = line.get(index);
        Double value = rawvalue.isEmpty() ? 0.0 : Double.parseDouble(rawvalue);
        line.set(index, String.valueOf(value * multiplier.get(index - samplesStartIndex)));
      });
      output.add(line);
    });
    // Replace zeros.
    List<Double> mins =
        IntStream.range(samplesStartIndex, output.get(0).size()).mapToDouble(index -> {
          List<String> rawvalues =
              output.stream().skip(1).map(line -> line.get(index)).collect(Collectors.toList());
          return rawvalues.stream().filter(value -> !value.equals("0.0"))
              .mapToDouble(value -> Double.parseDouble(value)).min().orElse(0.0);
        }).map(value -> value * 0.9).boxed().collect(Collectors.toList());
    output.stream().skip(1).forEach(line -> {
      IntStream.range(samplesStartIndex, line.size()).forEach(index -> {
        if (line.get(index).equals("0.0")) {
          line.set(index, String.valueOf(mins.get(index - samplesStartIndex)));
        }
      });
    });
    return output;
  }
}
