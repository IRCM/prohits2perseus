package ca.qc.ircm.prohits2perseus.sample;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.qc.ircm.prohits2perseus.test.config.ServiceTestAnnotations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
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
