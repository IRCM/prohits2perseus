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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import ca.qc.ircm.prohits2perseus.test.config.ServiceTestAnnotations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTestAnnotations
public class SampleServiceTest {
  @Autowired
  private SampleService service;

  @Test
  public void get() {
    Sample sample = service.get(1L);
    assertEquals(1L, sample.getId());
    assertEquals("OF_20191011_COU_01", sample.getName());
    assertEquals(1L, sample.getBait().getId());
  }

  @Test
  public void get_Null() {
    assertNull(service.get(null));
  }
}
