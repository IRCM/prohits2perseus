package ca.qc.ircm.prohits2perseus.gui;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.qc.ircm.prohits2perseus.test.config.TestFxTestAnnotations;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testfx.framework.junit.ApplicationTest;

@RunWith(SpringJUnit4ClassRunner.class)
@TestFxTestAnnotations
public class ProgressDialogTest extends ApplicationTest {
  private ProgressDialog dialog;
  private Scene scene;

  @Override
  public void start(Stage stage) throws Exception {
    scene = new Scene(new BorderPane());
    stage.setScene(scene);
  }

  private ProgressDialog dialog(Task<?> task) throws InterruptedException, ExecutionException {
    FutureTask<ProgressDialog> future = new FutureTask<>(() -> {
      return new ProgressDialog(scene.getWindow(), task);
    });
    Platform.runLater(future);
    return future.get();
  }

  @Test
  public void updateTitle() throws Throwable {
    UpdateTitleTask task = new UpdateTitleTask();
    dialog = dialog(task);
    Thread thread = new Thread(task);
    thread.start();
    thread.join();
    assertEquals(task.title, dialog.stage.getTitle());
  }

  @Test
  public void updateMessage() throws Throwable {
    UpdateMessageTask task = new UpdateMessageTask();
    dialog = dialog(task);
    Thread thread = new Thread(task);
    thread.start();
    thread.join();
    assertEquals(task.message, dialog.presenter.message.getText());
  }

  @Test
  public void updateProgress() throws Throwable {
    UpdateProgressTask task = new UpdateProgressTask();
    dialog = dialog(task);
    Thread thread = new Thread(task);
    thread.start();
    thread.join();
    assertEquals(task.progress, dialog.presenter.progressIndicator.getProgress());
    assertEquals(task.progress, dialog.presenter.progressBar.getProgress());
  }

  @Test
  @Ignore("Does not work on Mojave right now")
  public void cancel() throws Throwable {
    BlockingTask task = new BlockingTask();
    dialog = dialog(task);
    Thread thread = new Thread(task);
    thread.start();
    sleep(100);
    clickOn(".button");
    assertTrue(task.isCancelled());
  }

  private class UpdateTitleTask extends Task<Void> {
    private String title = "Test";

    @Override
    protected Void call() throws Exception {
      updateTitle(title);
      Thread.sleep(100);
      return null;
    }
  }

  private class UpdateMessageTask extends Task<Void> {
    private String message = "Test";

    @Override
    protected Void call() throws Exception {
      updateMessage(message);
      Thread.sleep(100);
      return null;
    }
  }

  private class UpdateProgressTask extends Task<Void> {
    private double progress = 0.6;

    @Override
    protected Void call() throws Exception {
      updateProgress(progress, 1.0);
      Thread.sleep(100);
      return null;
    }
  }

  private class BlockingTask extends Task<Void> {
    @Override
    protected Void call() throws Exception {
      Thread.sleep(10000);
      return null;
    }
  }
}
