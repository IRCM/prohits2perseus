package ca.qc.ircm.prohits2perseus.test.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import javafx.application.Platform;

/**
 * Utilities for JavaFX tests.
 */
public class JavaFxTests {
  /**
   * Wait for all JavaFX tasks to complete.
   */
  public static void waitForPlatform() {
    FutureTask<Void> waitForPlatform = new FutureTask<>((Callable<Void>) () -> null);
    Platform.runLater(waitForPlatform);
    try {
      waitForPlatform.get();
    } catch (InterruptedException | ExecutionException e) {
      // Ignore.
    }
  }
}
