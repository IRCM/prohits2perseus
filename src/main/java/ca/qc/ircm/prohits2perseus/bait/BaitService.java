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

package ca.qc.ircm.prohits2perseus.bait;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Bait service.
 */
@Service
@Transactional
public class BaitService {
  /**
   * Bait's repository.
   */
  private final BaitRepository repository;

  /**
   * Create an instance of BaitService.
   * 
   * @param repository
   *          bait's repository
   */
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
