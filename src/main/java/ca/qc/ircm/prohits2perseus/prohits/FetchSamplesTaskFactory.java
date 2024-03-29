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

import ca.qc.ircm.prohits2perseus.sample.SampleService;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Creates {@link FetchSamplesTask}.
 */
@Component
public class FetchSamplesTaskFactory {
  /**
   * Sample service.
   */
  private final SampleService service;

  /**
   * Creates an instance of FetchSamplesTaskFactory.
   * 
   * @param service
   *          sample service
   */
  @Autowired
  protected FetchSamplesTaskFactory(SampleService service) {
    this.service = service;
  }

  /**
   * Creates {@link FetchSamplesTask}.
   * 
   * @param metadata
   *          metadata base on sample comparison file from Prohits.
   * @param locale
   *          locale
   * @return an instance of {@link FetchSamplesTask}
   */
  public FetchSamplesTask create(SampleCompareMetadata metadata, Locale locale) {
    return new FetchSamplesTask(metadata, locale, service);
  }
}
