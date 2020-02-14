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

import ca.qc.ircm.prohits2perseus.sample.Sample;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * Converts Prohits sample compare into an input usable by Perseus.
 */
@Component
public class PerseusConverter {
  /**
   * Converts sample compare to Perseus.
   *
   * @param sampleCompare
   *          sample compare
   * @param samplesStartIndex
   *          index of first sample
   * @param samples
   *          samples
   * @return sample compare compatible with Perseus
   */
  public List<List<String>> toPerseus(List<List<String>> sampleCompare, int samplesStartIndex,
      List<Sample> samples) {
    List<String> header = new ArrayList<>(sampleCompare.get(0));
    for (int i = 0; i < samples.size(); i++) {
      header.set(i + samplesStartIndex, samples.get(i).getPerseus());
    }
    List<List<String>> output = new ArrayList<>();
    output.add(header);
    output.addAll(sampleCompare.stream().skip(1).map(columns -> new ArrayList<>(columns))
        .collect(Collectors.toList()));
    return output;
  }
}
