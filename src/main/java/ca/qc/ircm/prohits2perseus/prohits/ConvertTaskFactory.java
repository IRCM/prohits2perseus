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
