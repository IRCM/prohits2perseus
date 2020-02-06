package ca.qc.ircm.prohits2perseus.prohits;

import ca.qc.ircm.prohits2perseus.sample.SampleService;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Creates FetchSamplesTask.
 */
@Component
public class FetchSamplesTaskFactory {
  private final SampleService service;

  @Autowired
  protected FetchSamplesTaskFactory(SampleService service) {
    this.service = service;
  }

  public FetchSamplesTask create(SampleCompareMetadata metadata, Locale locale) {
    return new FetchSamplesTask(metadata, locale, service);
  }
}
