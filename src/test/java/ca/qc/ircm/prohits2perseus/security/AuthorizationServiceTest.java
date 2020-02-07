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
}
