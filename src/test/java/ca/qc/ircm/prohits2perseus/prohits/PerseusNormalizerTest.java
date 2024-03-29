/*
 * Copyright (c) 2020 Institut de recherches cliniques de Montreal (IRCM)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package ca.qc.ircm.prohits2perseus.prohits;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.qc.ircm.prohits2perseus.test.config.NonTransactionalTestAnnotations;
import com.opencsv.CSVParser;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@NonTransactionalTestAnnotations
public class PerseusNormalizerTest {
  @Autowired
  private PerseusNormalizer perseusNormalizer;

  @Test
  public void normalize() throws Throwable {
    File sampleCompare = new File(getClass().getResource("/comparison_matrix.csv").toURI());
    List<String> rawlinesInput = Files.readAllLines(sampleCompare.toPath());
    List<List<String>> linesInput = new ArrayList<>();
    for (int i = 6; i < rawlinesInput.size(); i++) {
      linesInput.add(
          Stream.of(new CSVParser().parseLine(rawlinesInput.get(i))).collect(Collectors.toList()));
    }
    for (int i = 4; i < linesInput.get(0).size(); i++) {
      linesInput.get(2).set(i, linesInput.get(0).get(i));
    }
    linesInput = linesInput.subList(2, linesInput.size());
    NormalizeMetadata metadata = new NormalizeMetadata();
    metadata.geneName = "POLR3B";
    metadata.ignoreSamples = Arrays.asList("9 OF_20200131_COU_03 NA", "8 OF_20200131_COU_02 NA",
        "7 OF_20200131_COU_01 NA");
    metadata.geneNameIndex = 1;
    metadata.samplesStartIndex = 4;
    List<List<String>> output = perseusNormalizer.normalize(linesInput, metadata);
    assertEquals(linesInput.get(0).subList(0, linesInput.get(0).size() - 3), output.get(0));
    List<Double> multipliers = DoubleStream.of(0.0, 0.0, 0.0, 0.0, 605.0 / 599, 605.0 / 584,
        605.0 / 574, 605.0 / 599, 605.0 / 551, 605.0 / 605).boxed().collect(Collectors.toList());
    assertEquals(5208, output.size());
    for (int i = 1; i < output.size(); i++) {
      List<String> inputLine = linesInput.get(i);
      List<String> outputLine = output.get(i);
      for (int j = 0; j < 4; j++) {
        assertEquals(inputLine.get(j), outputLine.get(j));
      }
      assertEquals(inputLine.size() - 3, outputLine.size());
      for (int j = 4; j < inputLine.size() - 3; j++) {
        double value = inputLine.get(j).isEmpty() ? 0.9 : Double.parseDouble(inputLine.get(j));
        assertEquals(String.valueOf(value * multipliers.get(j)), outputLine.get(j));
      }
    }
  }
}
