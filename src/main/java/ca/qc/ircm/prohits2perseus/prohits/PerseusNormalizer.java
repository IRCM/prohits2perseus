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
  public List<List<String>> normalize(List<List<String>> data,
      NormalizeMetadata normalizeMetadata) {
    // Remove ignored samples.
    List<Integer> removeIndexes =
        IntStream.range(normalizeMetadata.samplesStartIndex, data.get(0).size())
            .filter(index -> normalizeMetadata.ignoreSamples.contains(data.get(0).get(index)))
            .sorted().boxed().collect(Collectors.toList());
    List<List<String>> output = data.stream()
        .map(line -> IntStream.range(0, line.size()).filter(index -> !removeIndexes.contains(index))
            .mapToObj(index -> line.get(index)).collect(Collectors.toCollection(ArrayList::new)))
        .collect(Collectors.toCollection(ArrayList::new));
    int geneLineNumber = IntStream.range(1, output.size()).filter(
        i -> output.get(i).get(normalizeMetadata.geneNameIndex).equals(normalizeMetadata.geneName))
        .findFirst().orElse(-1);
    if (geneLineNumber == -1) {
      throw new IllegalArgumentException(
          "gene " + normalizeMetadata.geneName + " not found in data");
    }
    for (int i = normalizeMetadata.samplesStartIndex; i < output.get(geneLineNumber).size(); i++) {
      String value = output.get(geneLineNumber).get(i);
      try {
        Double.parseDouble(value);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("gene value " + value + " not a number");
      }
    }
    List<Double> geneValues =
        output.get(geneLineNumber).stream().skip(normalizeMetadata.samplesStartIndex)
            .map(value -> Double.parseDouble(value)).collect(Collectors.toList());
    double max = geneValues.stream().mapToDouble(v -> v).max().orElse(1.0);
    List<Double> multiplier =
        geneValues.stream().map(value -> max / value).collect(Collectors.toList());
    // Normalize.
    output.stream().skip(1).forEach(line -> {
      IntStream.range(normalizeMetadata.samplesStartIndex, line.size()).forEach(index -> {
        String rawvalue = line.get(index);
        Double value = rawvalue.isEmpty() ? 0.0 : Double.parseDouble(rawvalue);
        line.set(index,
            String.valueOf(value * multiplier.get(index - normalizeMetadata.samplesStartIndex)));
      });
    });
    // Replace zeros.
    List<Double> mins = IntStream.range(normalizeMetadata.samplesStartIndex, output.get(0).size())
        .mapToDouble(index -> {
          List<String> rawvalues =
              output.stream().skip(1).map(line -> line.get(index)).collect(Collectors.toList());
          return rawvalues.stream().filter(value -> !value.equals("0.0"))
              .mapToDouble(value -> Double.parseDouble(value)).min().orElse(0.0);
        }).map(value -> value * 0.9).boxed().collect(Collectors.toList());
    output.stream().skip(1).forEach(line -> {
      IntStream.range(normalizeMetadata.samplesStartIndex, line.size()).forEach(index -> {
        if (line.get(index).equals("0.0")) {
          line.set(index, String.valueOf(mins.get(index - normalizeMetadata.samplesStartIndex)));
        }
      });
    });
    return output;
  }
}
