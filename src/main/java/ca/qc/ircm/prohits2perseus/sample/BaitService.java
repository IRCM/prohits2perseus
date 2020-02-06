package ca.qc.ircm.prohits2perseus.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Bait service.
 */
@Service
@Transactional
public class BaitService {
  private final BaitRepository repository;

  @Autowired
  protected BaitService(BaitRepository repository) {
    this.repository = repository;
  }

  /**
   * Returns bait with id.
   * 
   * @param id
   *          id
   * @return bait with id
   */
  public Bait get(Long id) {
    if (id == null) {
      return null;
    }
    return repository.findById(id).orElse(null);
  }
}
