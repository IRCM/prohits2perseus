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

import static ca.qc.ircm.prohits2perseus.test.util.JavaFxTests.waitForPlatform;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.qc.ircm.prohits2perseus.test.config.TestFxTestAnnotations;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import javafx.beans.value.ChangeListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testfx.framework.junit.ApplicationTest;

@TestFxTestAnnotations
public class ParseSampleCompareTaskTest extends ApplicationTest {
  @Autowired
  private ParseSampleCompareTaskFactory factory;
  @MockBean
  private SampleCompareParser parser;
  @Mock
  private SampleCompareMetadata metadata;
  @Mock
  private ChangeListener<String> messageChangeListener;
  @Mock
  private ChangeListener<Number> progressChangeListener;
  private ParseSampleCompareTask task;
  private File file = new File("test");
  private Locale locale = Locale.ENGLISH;

  @BeforeEach
  public void beforeTest() {
    task = factory.create(file, locale);
  }

  @Test
  public void call() throws Throwable {
    when(parser.parseMetadata(any())).thenReturn(metadata);
    task.messageProperty().addListener(messageChangeListener);
    task.progressProperty().addListener(progressChangeListener);
    SampleCompareMetadata metadata = task.call();
    assertEquals(this.metadata, metadata);
    verify(parser).parseMetadata(file);
    waitForPlatform();
    verify(messageChangeListener, atLeastOnce()).changed(any(), any(String.class),
        any(String.class));
    verify(progressChangeListener, atLeastOnce()).changed(any(), any(Number.class),
        any(Number.class));
  }

  @Test
  public void call_IOException() throws Throwable {
    when(parser.parseMetadata(any())).thenThrow(new IOException("test"));
    try {
      task.call();
      fail("Expected IOException");
    } catch (IOException e) {
      verify(parser).parseMetadata(file);
    }
  }
}
