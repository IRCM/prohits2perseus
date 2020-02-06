package ca.qc.ircm.prohits2perseus.prohits;

import ca.qc.ircm.prohits2perseus.AppResources;
import java.io.File;
import java.util.Locale;
import javafx.concurrent.Task;

/**
 * Parses sample comparison file.
 */
public class ParseSampleCompareTask extends Task<SampleCompareMetadata> {
  private final File file;
  private final Locale locale;
  private final SampleCompareParser parser;

  protected ParseSampleCompareTask(File file, Locale locale, SampleCompareParser parser) {
    this.file = file;
    this.locale = locale;
    this.parser = parser;
  }

  @Override
  protected SampleCompareMetadata call() throws Exception {
    AppResources resources = new AppResources(ParseSampleCompareTask.class, locale);
    updateMessage(resources.message("message", file.getName()));
    SampleCompareMetadata metadata = parser.parseMetadata(file);
    updateProgress(1.0, 1.0);
    return metadata;
  }
}
