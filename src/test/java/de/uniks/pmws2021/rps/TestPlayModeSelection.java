package de.uniks.pmws2021.rps;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

public class TestPlayModeSelection extends ApplicationTest {
    private static Stage stage;
    private StageManager stageManager;

    private Button startButton;
    private RadioButton rockPaperScissorsRadioButton;
    private RadioButton rockPaperScissorsLizardSpockRadioButton;
    private CheckBox bestOfCheckBox;
    private TextField maxRoundsTextField;
    private Button rockButton;
    private Button paperButton;
    private Button scissorsButton;
    private Button lizardButton;
    private Button spockButton;
    private Text currentScoreText;
    private Text currentRoundText;
    private Text bestOfText;
    private ImageView youPlayImageView;
    private ImageView computerPlaysImageView;
    private Text resultText;
    private Button backToStartButton;
    private ImageView resultImageView;
    private Button leaveButton;

    public void loadAllStartViewReferences() {
        startButton = lookup("#startButton").query();
        rockPaperScissorsRadioButton = lookup("#rockPaperScissorsRadioButton").query();
        rockPaperScissorsLizardSpockRadioButton = lookup(
                "#rockPaperScissorsLizardSpockRadioButton").query();
        bestOfCheckBox = lookup("#bestOfCheckBox").query();
        maxRoundsTextField = lookup("#maxRoundsTextField").query();
    }

    public void loadAllIngameViewReferences() {
        leaveButton = lookup("#leaveButton").query();
        rockButton = lookup("#rockButton").query();
        paperButton = lookup("#paperButton").query();
        scissorsButton = lookup("#scissorsButton").query();
        lizardButton = lookup("#lizardButton").query();
        spockButton = lookup("#spockButton").query();

        currentScoreText = lookup("#currentScoreText").query();
        currentRoundText = lookup("#currentRoundText").query();
        bestOfText = lookup("#bestOfText").query();

        youPlayImageView = lookup("#youPlayImageView").query();
        computerPlaysImageView = lookup("#computerPlaysImageView").query();
    }

    public void loadAllResultViewReferences() {
        resultText = lookup("#resultText").query();
        backToStartButton = lookup("#backToStartButton").query();
        resultImageView = lookup("#resultImageView").query();
    }

    @Override
    public void start(Stage stage) {
        // start application
        TestPlayModeSelection.stage = stage;
        stageManager = new StageManager();
        stageManager.start(stage);
        TestPlayModeSelection.stage.centerOnScreen();
    }

    /* Because I could not click on the Buttons in the Assignment 3.3 (I called evaluate()-Method directly),
     * I have to test the Buttons in these 2 Tests! I still check for bestOf Condition!*/

    @Test
    public void testThreeRoundNormalConditionGameStart() {
        loadAllStartViewReferences();
        Platform.runLater(() -> {
            clickOn(rockPaperScissorsRadioButton);
            clickOn(maxRoundsTextField);
            write("3");
            clickOn(startButton);
        });
        sleep(1000);
        WaitForAsyncUtils.waitForFxEvents();
        loadAllIngameViewReferences();

        Assert.assertEquals("bestOf is wrong!", " ", bestOfText.getText());

        int switchButtons = 0;
        while (stage.getTitle().equals("Ingame")) {
            switch (switchButtons) {
                case 0:
                    Platform.runLater(() -> clickOn(scissorsButton));
                    WaitForAsyncUtils.waitForFxEvents();
                    sleep(200);
                    break;
                case 1:
                    Platform.runLater(() -> clickOn(paperButton));
                    WaitForAsyncUtils.waitForFxEvents();
                    sleep(200);
                    break;
                case 2:
                    Platform.runLater(() -> clickOn(rockButton));
                    WaitForAsyncUtils.waitForFxEvents();
                    sleep(200);
                    break;
                default:
                    switchButtons = 0;
            }
            switchButtons++;
        }

        loadAllResultViewReferences();
        Platform.runLater(() -> clickOn(backToStartButton));
        sleep(800);
    }

    @Test
    public void testThreeRoundBestOfConditionGameStart() {
        loadAllStartViewReferences();

        Platform.runLater(() -> {
            clickOn(rockPaperScissorsRadioButton);
            clickOn(maxRoundsTextField);
            write("3");
            clickOn(bestOfCheckBox);
            clickOn(startButton);
        });
        sleep(1000);
        WaitForAsyncUtils.waitForFxEvents();
        loadAllIngameViewReferences();

        Assert.assertEquals("bestOf is wrong!", "Best of 3", bestOfText.getText());

        int switchButtons = 0;
        while (stage.getTitle().equals("Ingame")) {
            switch (switchButtons) {
                case 0:
                    Platform.runLater(() -> clickOn(scissorsButton));
                    WaitForAsyncUtils.waitForFxEvents();
                    sleep(200);
                    break;
                case 1:
                    Platform.runLater(() -> clickOn(paperButton));
                    WaitForAsyncUtils.waitForFxEvents();
                    sleep(200);
                    break;
                case 2:
                    Platform.runLater(() -> clickOn(rockButton));
                    WaitForAsyncUtils.waitForFxEvents();
                    sleep(200);
                    break;
                default:
                    switchButtons = 0;
            }
            switchButtons++;
        }

        loadAllResultViewReferences();
        Platform.runLater(() -> clickOn(backToStartButton));
        sleep(800);
    }
}
