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

package ca.qc.ircm.prohits2perseus.prohits;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.qc.ircm.prohits2perseus.test.config.NonTransactionalTestAnnotations;
import org.junit.jupiter.api.Test;

@NonTransactionalTestAnnotations
public class SampleCompareMetadataTest {
  private SampleCompareMetadata metadata = new SampleCompareMetadata();

  @Test
  public void parseMetadata() throws Throwable {
    assertEquals(6L, metadata.id("6 OF_20200131_COU_10(C-3xFLAG) 55703"));
    assertEquals(5L, metadata.id("5 OF_20200131_COU_09(C-3xFLAG) 55703"));
    assertEquals(4L, metadata.id("4 OF_20200131_COU_05(C-3xFLAG) 55703"));
    assertEquals(3L, metadata.id("3 OF_20200131_COU_11(C-3xFLAG) 55703"));
    assertEquals(2L, metadata.id("2 OF_20200131_COU_07(C-3xFLAG) 55703"));
    assertEquals(1L, metadata.id("1 OF_20200131_COU_06(C-3xFLAG) 55703"));
    assertEquals(9L, metadata.id("9 OF_20200131_COU_03 NA"));
    assertEquals(8L, metadata.id("8 OF_20200131_COU_02 NA"));
    assertEquals(7L, metadata.id("7 OF_20200131_COU_01 NA"));
  }
}
