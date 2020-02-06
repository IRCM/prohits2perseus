package ca.qc.ircm.prohits2perseus.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Sample service.
 */
@Service
@Transactional
public class SampleService {
  private final SampleRepository repository;

  @Autowired
  protected SampleService(SampleRepository repository) {
    this.repository = repository;
  }

  /**
   * Returns sample with id.
   *
   * @param id
   *          id
   * @return sample with id
   */
  public Sample get(Long id) {
    if (id == null) {
      return null;
    }
    return repository.findById(id).orElse(null);
  }
}
