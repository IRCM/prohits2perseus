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

import ca.qc.ircm.prohits2perseus.AppResources;
import java.io.File;
import java.util.Locale;
import javafx.concurrent.Task;

/**
 * Parses Prohits sample comparison file.
 */
public class ParseSampleCompareTask extends Task<SampleCompareMetadata> {
  /**
   * Prohits sample comparison file.
   */
  private final File file;
  /**
   * User's locale.
   */
  private final Locale locale;
  /**
   * Parses Prohits sample comparison file.
   */
  private final SampleCompareParser parser;

  /**
   * Creates an instance of ParseSampleCompareTask.
   * 
   * @param file
   *          Prohits sample comparison file
   * @param locale
   *          user's locale
   * @param parser
   *          parses Prohits sample comparison file
   */
  protected ParseSampleCompareTask(File file, Locale locale, SampleCompareParser parser) {
    this.file = file;
    this.locale = locale;
    this.parser = parser;
  }

  /**
   * Returns sample metadata parsed from Prohits sample comparison file.
   * 
   * @return sample metadata parsed from Prohits sample comparison file
   * @throws Exception
   *           could not extract sample metadata from Prohits sample comparison file
   */
  @Override
  protected SampleCompareMetadata call() throws Exception {
    AppResources resources = new AppResources(ParseSampleCompareTask.class, locale);
    updateMessage(resources.message("message", file.getName()));
    SampleCompareMetadata metadata = parser.parseMetadata(file);
    updateProgress(1.0, 1.0);
    return metadata;
  }
}
