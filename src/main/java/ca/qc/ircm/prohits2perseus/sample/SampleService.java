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
  /**
   * Sample's repository.
   */
  private final SampleRepository repository;

  /**
   * Creates an instance of SampleService.
   * 
   * @param repository
   *          sample's repository
   */
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
