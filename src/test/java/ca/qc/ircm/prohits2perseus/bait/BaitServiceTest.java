package ca.qc.ircm.prohits2perseus.bait;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.qc.ircm.prohits2perseus.test.config.ServiceTestAnnotations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ServiceTestAnnotations
public class BaitServiceTest {
  @Autowired
  private BaitService service;

  @Test
  public void get() {
    Bait bait = service.get(1L);
    assertEquals(1L, bait.getId());
    assertEquals("POLR3B", bait.getName());
    assertEquals(55703, bait.getGeneId());
  }

  @Test
  public void get_3() {
    Bait bait = service.get(3L);
    assertEquals(3L, bait.getId());
    assertEquals("Flag", bait.getName());
    assertNull(bait.getGeneId());
  }

  @Test
  public void get_Null() {
    assertNull(service.get(null));
  }
}
