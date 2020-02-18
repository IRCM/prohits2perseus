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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.qc.ircm.prohits2perseus.AppResources;
import ca.qc.ircm.prohits2perseus.sample.Sample;
import ca.qc.ircm.prohits2perseus.test.config.TestFxTestAnnotations;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.beans.value.ChangeListener;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testfx.framework.junit.ApplicationTest;

@RunWith(SpringJUnit4ClassRunner.class)
@TestFxTestAnnotations
public class ConvertTaskTest extends ApplicationTest {
  @Autowired
  private ConvertTaskFactory factory;
  @MockBean
  private SampleCompareParser parser;
  @MockBean
  private PerseusConverter converter;
  @MockBean
  private PerseusNormalizer normalizer;
  @Mock
  private ChangeListener<String> titleChangeListener;
  @Mock
  private ChangeListener<String> messageChangeListener;
  @Mock
  private ChangeListener<Number> progressChangeListener;
  @Rule
  public TemporaryFolder folder = new TemporaryFolder();
  private ConvertTask task;
  private File input;
  @Mock
  private List<Sample> samples;
  private boolean normalize = false;
  @Mock
  private NormalizeMetadata normalizeMetadata;
  @Mock
  private List<List<String>> parsedContent;
  private List<List<String>> convertedContent;
  private List<List<String>> normalizedContent;
  @Mock
  private SampleCompareMetadata metadata;
  private Locale locale = Locale.ENGLISH;
  private AppResources resources = new AppResources(ConvertTask.class, locale);

  @Before
  public void beforeTest() throws Throwable {
    input = folder.newFile("input.csv");
    task = factory.create(input, samples, normalize, normalizeMetadata, locale);
    metadata.samplesStartIndex = 4;
    metadata.geneNameIndex = 1;
    convertedContent = generateText();
    normalizedContent = generateText();
    when(parser.parseMetadata(any())).thenReturn(metadata);
    when(parser.parse(any())).thenReturn(parsedContent);
    when(converter.toPerseus(any(), anyInt(), any())).thenReturn(convertedContent);
    when(normalizer.normalize(any(), any())).thenReturn(normalizedContent);
  }

  private List<List<String>> generateText() {
    return IntStream
        .range(0, RandomUtils.nextInt(500, 2000)).mapToObj(i -> IntStream.range(0, 12)
            .mapToObj(j -> RandomStringUtils.randomAlphabetic(100)).collect(Collectors.toList()))
        .collect(Collectors.toList());
  }

  private void compareContent(List<List<String>> expected, Path file) throws IOException {
    CSVParser parser = new CSVParserBuilder().withSeparator('\t').build();
    List<String> converterRawLines = Files.readAllLines(file);
    List<List<String>> converterLines = new ArrayList<>();
    for (String line : converterRawLines) {
      converterLines.add(Arrays.asList(parser.parseLine(line)));
    }
    assertEquals(expected.size(), converterLines.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i), converterLines.get(i));
    }
  }

  @Test
  public void call() throws Throwable {
    task.titleProperty().addListener(titleChangeListener);
    task.messageProperty().addListener(messageChangeListener);
    task.progressProperty().addListener(progressChangeListener);
    List<File> outputs = task.call();
    verify(parser).parseMetadata(input);
    verify(parser).parse(input);
    verify(converter).toPerseus(parsedContent, metadata.samplesStartIndex, samples);
    verify(normalizer, never()).normalize(convertedContent, normalizeMetadata);
    verify(titleChangeListener, atLeastOnce()).changed(any(), any(),
        eq(resources.message("title", input.getName())));
    verify(messageChangeListener, atLeastOnce()).changed(any(), any(), any());
    verify(progressChangeListener, atLeastOnce()).changed(any(), any(), any());
    assertEquals(1, outputs.size());
    Path converterOutput = folder.getRoot().toPath().resolve("input.txt");
    assertTrue(outputs.contains(converterOutput.toFile()));
    assertTrue(Files.exists(converterOutput));
    compareContent(convertedContent, converterOutput);
    Path normalizerOutput = folder.getRoot().toPath().resolve("input-normalized.txt");
    assertFalse(outputs.contains(normalizerOutput.toFile()));
    assertFalse(Files.exists(normalizerOutput));
  }

  @Test
  public void call_Normalize() throws Throwable {
    normalize = true;
    task = factory.create(input, samples, normalize, normalizeMetadata, locale);
    task.titleProperty().addListener(titleChangeListener);
    task.messageProperty().addListener(messageChangeListener);
    task.progressProperty().addListener(progressChangeListener);
    List<File> outputs = task.call();
    verify(parser).parseMetadata(input);
    verify(parser).parse(input);
    verify(converter).toPerseus(parsedContent, metadata.samplesStartIndex, samples);
    verify(normalizer).normalize(convertedContent, normalizeMetadata);
    assertEquals(4, normalizeMetadata.samplesStartIndex);
    assertEquals(1, normalizeMetadata.geneNameIndex);
    waitForPlatform();
    verify(titleChangeListener, atLeastOnce()).changed(any(), any(),
        eq(resources.message("title", input.getName())));
    verify(messageChangeListener, atLeastOnce()).changed(any(), any(), any());
    verify(progressChangeListener, atLeastOnce()).changed(any(), any(), any());
    assertEquals(2, outputs.size());
    Path converterOutput = folder.getRoot().toPath().resolve("input.txt");
    assertTrue(outputs.contains(converterOutput.toFile()));
    assertTrue(Files.exists(converterOutput));
    compareContent(convertedContent, converterOutput);
    Path normalizerOutput = folder.getRoot().toPath().resolve("input-normalized.txt");
    assertTrue(outputs.contains(normalizerOutput.toFile()));
    assertTrue(Files.exists(normalizerOutput));
    compareContent(normalizedContent, normalizerOutput);
  }

  @Test(expected = IOException.class)
  public void call_ParseMetadataIOException() throws Throwable {
    when(parser.parseMetadata(any())).thenThrow(new IOException("test"));
    task.call();
  }

  @Test(expected = IOException.class)
  public void call_ParseIOException() throws Throwable {
    when(parser.parse(any())).thenThrow(new IOException("test"));
    task.call();
  }
}
