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
import ca.qc.ircm.prohits2perseus.sample.Sample;
import ca.qc.ircm.prohits2perseus.sample.SampleService;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fetch samples from database base on ids.
 */
public class FetchSamplesTask extends Task<List<Sample>> {
  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory.getLogger(FetchSamplesTask.class);
  private final SampleCompareMetadata metadata;
  private final Locale locale;
  private final SampleService service;

  protected FetchSamplesTask(SampleCompareMetadata metadata, Locale locale, SampleService service) {
    this.metadata = metadata;
    this.locale = locale;
    this.service = service;
  }

  @Override
  protected List<Sample> call() throws Exception {
    AppResources resources = new AppResources(FetchSamplesTask.class, locale);
    List<Sample> samples = new ArrayList<>();
    int size = metadata.samples.size();
    for (int i = 0; i < metadata.samples.size(); i++) {
      String sample = metadata.samples.get(i);
      updateMessage(resources.message("message", sample));
      Long id = metadata.id(sample);
      Sample database = service.get(id);
      database.setControl(database.getBait() == null);
      if (database.getBait() != null) {
        database.setControl(
            database.getBait().getGeneId() == null || database.getBait().getGeneId() < 0);
      }
      samples.add(database);
      updateProgress((i + 1.0) / size, 1.0);
    }
    return samples;
  }
}
