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

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sample compare metadata.
 */
public class SampleCompareMetadata {
  private static final String SAMPLE_NAME_PATTERN = "(\\d+) .*";

  /**
   * Samples.
   */
  public List<String> samples;
  /**
   * All genes present in file.
   */
  public List<String> genes;
  /**
   * Header line number.
   */
  public int headerLineNumber;
  /**
   * Column of first sample.
   */
  public int samplesStartIndex;
  /**
   * Column of gene names.
   */
  public int geneNameIndex;

  /**
   * Returns sample's id from sample's name inside Prohits sample comparison data.
   * 
   * @param sample
   *          sample's name inside Prohits sample comparison data
   * @return sample's id from sample's name inside Prohits sample comparison data or null if it
   *         cannot be determined
   */
  public Long id(String sample) {
    Pattern sampleNamePattern = Pattern.compile(SAMPLE_NAME_PATTERN);
    Matcher matcher = sampleNamePattern.matcher(sample);
    if (matcher.matches()) {
      return Long.parseLong(matcher.group(1));
    } else {
      return null;
    }
  }
}
