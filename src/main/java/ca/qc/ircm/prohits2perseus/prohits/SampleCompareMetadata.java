package ca.qc.ircm.prohits2perseus.prohits;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sample compare metadata.
 */
public class SampleCompareMetadata {
  private static final String SAMPLE_NAME_PATTERN = "(\\d+) .*";

  /**
   * Samples.
   */
  public List<String> samples;
  /**
   * All genes present in file.
   */
  public List<String> genes;
  /**
   * Header line number.
   */
  public int headerLineNumber;
  /**
   * Column of first sample.
   */
  public int samplesStartIndex;
  /**
   * Column of gene names.
   */
  public int geneNameIndex;

  public Long id(String sample) {
    Pattern sampleNamePattern = Pattern.compile(SAMPLE_NAME_PATTERN);
    Matcher matcher = sampleNamePattern.matcher(sample);
    if (matcher.matches()) {
      return Long.parseLong(matcher.group(1));
    } else {
      return null;
    }
  }
}
