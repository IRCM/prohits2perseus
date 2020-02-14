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
import java.io.File;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Creates ConvertTask.
 */
@Component
public class ConvertTaskFactory {
  private final SampleCompareParser parser;
  private final PerseusConverter converter;
  private final PerseusNormalizer normalizer;

  @Autowired
  protected ConvertTaskFactory(SampleCompareParser parser, PerseusConverter converter,
      PerseusNormalizer normalizer) {
    this.parser = parser;
    this.converter = converter;
    this.normalizer = normalizer;
  }

  public ConvertTask create(File input, List<Sample> samples, boolean normalize,
      NormalizeMetadata normalizeMetadata, Locale locale) {
    return new ConvertTask(input, samples, normalize, normalizeMetadata, locale, parser, converter,
        normalizer);
  }
}
