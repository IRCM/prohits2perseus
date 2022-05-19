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

package ca.qc.ircm.prohits2perseus;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.qc.ircm.prohits2perseus.gui.MainPreloader;
import java.util.Locale;
import org.junit.jupiter.api.Test;

public class AppResourcesTest {
  private Locale locale = Locale.ENGLISH;

  @Test
  public void message_Name() {
    AppResources resources = new AppResources("", locale);
    assertEquals("Prohits -> Perseus", resources.message("name"));
  }

  @Test
  public void message_NameFrench() {
    AppResources resources = new AppResources("", Locale.FRENCH);
    assertEquals("Prohits -> Perseus", resources.message("name"));
  }

  @Test
  public void message_PreloaderClassname() {
    AppResources resources = new AppResources(MainPreloader.class.getName(), locale);
    assertEquals("test is loading", resources.message("message", "test"));
  }

  @Test
  public void message_PreloaderClassnameFrench() {
    AppResources resources = new AppResources(MainPreloader.class.getName(), Locale.FRENCH);
    assertEquals("test est en démarrage", resources.message("message", "test"));
  }

  @Test
  public void message_MainPreloader() {
    AppResources resources = new AppResources(MainPreloader.class, locale);
    assertEquals("test is loading", resources.message("message", "test"));
  }

  @Test
  public void message_MainPreloaderFrench() {
    AppResources resources = new AppResources(MainPreloader.class, Locale.FRENCH);
    assertEquals("test est en démarrage", resources.message("message", "test"));
  }
}
