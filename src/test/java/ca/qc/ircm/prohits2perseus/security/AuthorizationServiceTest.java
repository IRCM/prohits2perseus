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

package ca.qc.ircm.prohits2perseus.security;

import ca.qc.ircm.prohits2perseus.sample.Sample;
import ca.qc.ircm.prohits2perseus.sample.SampleRepository;
import ca.qc.ircm.prohits2perseus.test.config.ServiceTestAnnotations;
import java.security.AccessControlException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ServiceTestAnnotations
public class AuthorizationServiceTest {
  @Autowired
  private AuthorizationService service;
  @Autowired
  private SampleRepository sampleRepository;
  private String originalUsername;

  @Before
  public void beforeTest() {
    originalUsername = System.getProperty("user.name");
  }

  @After
  public void afterTest() {
    System.setProperty("user.name", originalUsername);
  }

  @Test
  public void checkRead_Allowed() {
    System.setProperty("user.name", "poitrasc");
    Sample sample = sampleRepository.findById(1L).orElse(null);
    service.checkRead(sample);
  }

  @Test(expected = AccessControlException.class)
  public void checkRead_Denied() {
    System.setProperty("user.name", "smithj");
    Sample sample = sampleRepository.findById(10L).orElse(null);
    service.checkRead(sample);
  }

  @Test
  public void checkRead_NullData() {
    System.setProperty("user.name", "poitrac");
    service.checkRead(null);
  }

  @Test
  public void checkRead_NullProject() {
    System.setProperty("user.name", "poitrasc");
    Sample sample = sampleRepository.findById(1L).orElse(null);
    sample.setProject(null);
    service.checkRead(sample);
  }

  @Test(expected = AccessControlException.class)
  public void checkRead_NullUser() {
    System.setProperty("user.name", "");
    Sample sample = sampleRepository.findById(1L).orElse(null);
    service.checkRead(sample);
  }
}
