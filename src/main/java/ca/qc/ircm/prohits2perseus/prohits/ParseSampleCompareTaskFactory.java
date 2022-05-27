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

import java.io.File;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Creates {@link ParseSampleCompareTask}.
 */
@Component
public class ParseSampleCompareTaskFactory {
  /**
   * Parses Prohits sample comparison file.
   */
  private final SampleCompareParser parser;

  /**
   * Creates an instance of ParseSampleCompareTaskFactory.
   * 
   * @param parser
   *          parses Prohits sample comparison file
   */
  @Autowired
  protected ParseSampleCompareTaskFactory(SampleCompareParser parser) {
    this.parser = parser;
  }

  /**
   * Creates {@link ParseSampleCompareTask}.
   * 
   * @param file
   *          sample comparison file from Prohits
   * @param locale
   *          user's locale
   * @return an instance of {@link ParseSampleCompareTask}
   */
  public ParseSampleCompareTask create(File file, Locale locale) {
    return new ParseSampleCompareTask(file, locale, parser);
  }
}
