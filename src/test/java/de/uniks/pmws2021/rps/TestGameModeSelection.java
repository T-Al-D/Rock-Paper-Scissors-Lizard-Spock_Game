package de.uniks.pmws2021.rps;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

public class TestGameModeSelection extends ApplicationTest {

    private static Stage stage;
    private StageManager stageManager;

    private Button startButton;
    private RadioButton rockPaperScissorsRadioButton;
    private RadioButton rockPaperScissorsLizardSpockRadioButton;
    private CheckBox bestOfCheckBox;
    private TextField maxRoundsTextField;
    private Button leaveButton;
    private Button rockButton;
    private Button paperButton;
    private Button scissorsButton;
    private Button lizardButton;
    private Button spockButton;
    private Text currentScoreText;
    private Text currentRoundText;
    private Text bestOfText;

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
    }

    @Override
    public void start(Stage stage) {
        // start application
        TestGameModeSelection.stage = stage;
        stageManager = new StageManager();
        stageManager.start(stage);
        TestGameModeSelection.stage.centerOnScreen();
    }

    @Test
    public void testClassicModeStart() {
        loadAllStartViewReferences();
        WaitForAsyncUtils.waitForFxEvents();

        // StartStage - Name check
        Assert.assertEquals("Title is not Start", "Start", stage.getTitle());

        Platform.runLater(() -> {
            clickOn(rockPaperScissorsRadioButton);
            clickOn(maxRoundsTextField);
            write("3");
            clickOn(startButton);
        });
        WaitForAsyncUtils.waitForFxEvents();

        sleep(800);
        loadAllIngameViewReferences();

        // Check Texts
        Assert.assertEquals("Title is not Ingame", "Ingame", stage.getTitle());
        Assert.assertEquals("bestOf ist Wrong!", " ", bestOfText.getText());
        Assert.assertEquals("currentScore is Wrong!",
                "Current Score:     YOU 0 : 0 COM", currentScoreText.getText());
        Assert.assertEquals("currentRoundText is Wrong !",
                "Round: 0 / 3", currentRoundText.getText());

        // Check Buttons Enabeld
        Assert.assertFalse("rockButton is not active!", rockButton.isDisabled());
        Assert.assertFalse("paperButton is not active!", paperButton.isDisabled());
        Assert.assertFalse("scissorsButton is not active!", scissorsButton.isDisabled());
        Assert.assertTrue("lizardButton is active!", lizardButton.isDisabled());
        Assert.assertTrue("spockButtonButton is active!", spockButton.isDisabled());

        // Check Buttons Visible
        Assert.assertTrue("rockButton is not Visible!", rockButton.isVisible());
        Assert.assertTrue("paperButton is not Visible!", paperButton.isVisible());
        Assert.assertTrue("scissorsButton is not Visible!", scissorsButton.isVisible());
        Assert.assertFalse("lizardButton is not Visible!", lizardButton.isVisible());
        Assert.assertFalse("spockButton is not Visible!", spockButton.isVisible());

        // Check for leave Button
        Platform.runLater(() -> clickOn(leaveButton));
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testExtendedModeStart() {
        loadAllStartViewReferences();

        // StartStage - Name check
        Assert.assertEquals("Title is not Start", "Start", stage.getTitle());

        Platform.runLater(() -> {
            clickOn(rockPaperScissorsLizardSpockRadioButton);
            clickOn(maxRoundsTextField);
            write("5");
            clickOn(bestOfCheckBox);
            clickOn(startButton);
        });
        WaitForAsyncUtils.waitForFxEvents();

        sleep(800);
        loadAllIngameViewReferences();

        // Check Texts
        Assert.assertEquals("Title is not Ingame", "Ingame", stage.getTitle());
        Assert.assertEquals("bestOf is wrong!", "Best of 5", bestOfText.getText());
        Assert.assertEquals("currentScore is wrong!",
                "Current Score:     YOU 0 : 0 COM", currentScoreText.getText());
        Assert.assertEquals("currentRoundText is wrong !",
                "Round: 0 / 5", currentRoundText.getText());

        // Check Buttons Enabled
        Assert.assertFalse("rockButton is not active!", rockButton.isDisabled());
        Assert.assertFalse("paperButton is not active!", paperButton.isDisabled());
        Assert.assertFalse("scissorsButton is not active!", scissorsButton.isDisabled());
        Assert.assertFalse("lizardButton is not active!", lizardButton.isDisabled());
        Assert.assertFalse("spockButton is not active!", spockButton.isDisabled());

        // Check Buttons Visible
        Assert.assertTrue("rockButton is not Visible!", rockButton.isVisible());
        Assert.assertTrue("paperButton is not Visible!", paperButton.isVisible());
        Assert.assertTrue("scissorsButton is not Visible!", scissorsButton.isVisible());
        Assert.assertTrue("lizardButton is not Visible!", lizardButton.isVisible());
        Assert.assertTrue("spockButton is not Visible!", spockButton.isVisible());

        // Check for leave Button
        Platform.runLater(() -> clickOn(leaveButton));
        WaitForAsyncUtils.waitForFxEvents();
    }
}
