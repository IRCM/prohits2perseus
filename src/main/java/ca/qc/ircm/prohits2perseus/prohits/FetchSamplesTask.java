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
      samples.add(database);
      updateProgress((i + 1.0) / size, 1.0);
    }
    return samples;
  }
}
