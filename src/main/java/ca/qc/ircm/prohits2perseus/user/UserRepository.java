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

package ca.qc.ircm.prohits2perseus.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User repository.
 */
public interface UserRepository extends JpaRepository<User, Long> {
  /**
   * Finds user with username.
   * 
   * @param username
   *          username
   * @return user with username
   */
  public Optional<User> findByUsername(String username);
}
