package ca.qc.ircm.prohits2perseus.prohits;

import java.io.File;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Creates FetchSamplesTask.
 */
@Component
public class ParseSampleCompareTaskFactory {
  private final SampleCompareParser parser;

  @Autowired
  protected ParseSampleCompareTaskFactory(SampleCompareParser parser) {
    this.parser = parser;
  }

  public ParseSampleCompareTask create(File file, Locale locale) {
    return new ParseSampleCompareTask(file, locale, parser);
  }
}
