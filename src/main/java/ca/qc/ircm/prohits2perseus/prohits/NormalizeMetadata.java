package ca.qc.ircm.prohits2perseus.prohits;

import java.util.List;

/**
 * Normalization metadata.
 */
public class NormalizeMetadata {
  /**
   * Gene on which to normalize.
   */
  public String geneName;
  /**
   * Samples to ignore.
   */
  public List<String> ignoreSamples;
  /**
   * Column of gene names.
   */
  public int geneNameIndex;
  /**
   * Column of first sample.
   */
  public int samplesStartIndex;
}
