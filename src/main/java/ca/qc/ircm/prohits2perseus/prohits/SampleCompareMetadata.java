package ca.qc.ircm.prohits2perseus.prohits;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sample compare metadata.
 */
public class SampleCompareMetadata {
  private static final String SAMPLE_NAME_PATTERN = "(\\d+) .*";

  public List<String> samples;
  public List<String> genes;

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
