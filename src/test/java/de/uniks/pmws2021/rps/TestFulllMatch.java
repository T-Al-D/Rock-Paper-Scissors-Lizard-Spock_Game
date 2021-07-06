package de.uniks.pmws2021.rps;

import de.uniks.pmws2021.rps.model.Game;
import de.uniks.pmws2021.rps.model.Player;
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

import static de.uniks.pmws2021.rps.Constants.*;

public class TestFulllMatch extends ApplicationTest {

    private static Stage stage;
    private static StageManager stageManager;
    private static Game game;
    private static Player humanPlayer;
    private static Player computerPlayer;
    private Button startButton;
    private RadioButton rockPaperScissorsRadioButton;
    private RadioButton rockPaperScissorsLizardSpockRadioButton;
    private CheckBox bestOfCheckBox;
    private TextField maxRoundsTextField;
    private Text currentScoreText;
    private Text currentRoundText;
    private Text bestOfText;
    private ImageView youPlayImageView;
    private ImageView computerPlaysImageView;
    private Text resultText;
    private Button backToStartButton;
    private ImageView resultImageView;
    private Text finalResultScoreText;


    public void loadAllStartViewReferences() {
        startButton = lookup("#startButton").query();
        rockPaperScissorsRadioButton = lookup("#rockPaperScissorsRadioButton").query();
        rockPaperScissorsLizardSpockRadioButton = lookup(
                "#rockPaperScissorsLizardSpockRadioButton").query();
        bestOfCheckBox = lookup("#bestOfCheckBox").query();
        maxRoundsTextField = lookup("#maxRoundsTextField").query();
    }

    public void loadAllIngameViewReferences() {
        currentScoreText = lookup("#currentScoreText").query();
        currentRoundText = lookup("#currentRoundText").query();
        bestOfText = lookup("#bestOfText").query();

        youPlayImageView = lookup("#youPlayImageView").query();
        computerPlaysImageView = lookup("#computerPlaysImageView").query();
    }

    public void loadAllResultViewReferences() {
        resultText = lookup("#resultText").query();
        finalResultScoreText = lookup("#finalResultScoreText").query();
        backToStartButton = lookup("#backToStartButton").query();
        resultImageView = lookup("#resultImageView").query();
    }

    public static void settingPlayers() {
        game = stageManager.getEditor().getGame();
        for (Player player : game.getPlayers()
        ) {
            if (player.getPlayerName().equals("Human")) {
                humanPlayer = player;
            }
            if (player.getPlayerName().equals("Computer")) {
                computerPlayer = player;
            }
        }
    }

    @Override
    public void start(Stage stage) {
        // start application
        TestFulllMatch.stage = stage;
        stageManager = new StageManager();
        stageManager.start(stage);
        TestFulllMatch.stage.centerOnScreen();
    }

    /* I know, I´m supposed to check to Buttons in these Tests, but unfortunately how ever I try it´s IMPOSSIBLE for
     * me to create a Program where the computerMove is random AND can be set at the same time, therefore I call the
     * Method in the RPSEditor (evaluateRound()) directly, which can look a bit strange/weird in these Tests.
     * But I still can check for the winning condition and the resultScreen ! */

    @Test
    public void testWinBestOfThreeFullGame() {
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

        settingPlayers();

        int switchButtons = 0;
        while (stage.getTitle().equals("Ingame")) {
            switch (switchButtons) {
                case 0:
                    Platform.runLater(() ->
                            stageManager.getEditor().evaluateRound(humanPlayer, computerPlayer, SCISSORS, ROCK));
                    WaitForAsyncUtils.waitForFxEvents();
                    sleep(200);
                    break;
                case 1:
                    Platform.runLater(() ->
                            stageManager.getEditor().evaluateRound(humanPlayer, computerPlayer, PAPER, ROCK));
                    WaitForAsyncUtils.waitForFxEvents();
                    sleep(200);
                    break;
                case 2:
                    Platform.runLater(() ->
                            stageManager.getEditor().evaluateRound(humanPlayer, computerPlayer, ROCK, SCISSORS));
                    WaitForAsyncUtils.waitForFxEvents();
                    sleep(200);
                    break;
                default:
                    switchButtons = 0;
            }
            switchButtons++;
        }

        loadAllResultViewReferences();
        Assert.assertEquals("Title is not Result", "Result", stage.getTitle());
        Assert.assertEquals("finalScore is wrong!", "YOU 2 : 1 COM", finalResultScoreText.getText());
        Assert.assertEquals("Title is not Result", "Congratulations, you won !", resultText.getText());

        Platform.runLater(() -> clickOn(backToStartButton));
        sleep(800);
    }

    @Test
    public void testLoseFiveRoundFullGame() {
        loadAllStartViewReferences();

        Platform.runLater(() -> {
            clickOn(rockPaperScissorsLizardSpockRadioButton);
            clickOn(maxRoundsTextField);
            write("5");
            clickOn(startButton);
        });
        sleep(1000);
        WaitForAsyncUtils.waitForFxEvents();
        loadAllIngameViewReferences();

        settingPlayers();

        int switchButtons = 0;
        while (stage.getTitle().equals("Ingame")) {
            switch (switchButtons) {
                case 0:
                    Platform.runLater(() ->
                            stageManager.getEditor().evaluateRound(humanPlayer, computerPlayer, SPOCK, LIZARD));
                    WaitForAsyncUtils.waitForFxEvents();
                    sleep(200);
                    break;
                case 1:
                    Platform.runLater(() ->
                            stageManager.getEditor().evaluateRound(humanPlayer, computerPlayer, LIZARD, SCISSORS));
                    WaitForAsyncUtils.waitForFxEvents();
                    sleep(200);
                    break;
                case 2:
                    Platform.runLater(() ->
                            stageManager.getEditor().evaluateRound(humanPlayer, computerPlayer, PAPER, LIZARD));
                    WaitForAsyncUtils.waitForFxEvents();
                    sleep(200);
                    break;
                case 3:
                    Platform.runLater(() ->
                            stageManager.getEditor().evaluateRound(humanPlayer, computerPlayer, SPOCK, PAPER));
                    WaitForAsyncUtils.waitForFxEvents();
                    sleep(200);
                    break;
                case 4:
                    Platform.runLater(() ->
                            stageManager.getEditor().evaluateRound(humanPlayer, computerPlayer, SCISSORS, ROCK));
                    WaitForAsyncUtils.waitForFxEvents();
                    sleep(200);
                    break;
                default:
                    switchButtons = 0;
            }
            switchButtons++;
        }

        loadAllResultViewReferences();
        Assert.assertEquals("Title is not Result", "Result", stage.getTitle());
        Assert.assertEquals("finalScore is wrong!", "YOU 0 : 5 COM", finalResultScoreText.getText());
        Assert.assertEquals("Title is not Result", "Sorry, you lost !", resultText.getText());

        Platform.runLater(() -> clickOn(backToStartButton));
        sleep(800);
    }

    @Test
    public void testDrawWithDrawRound() {
        loadAllStartViewReferences();

        Platform.runLater(() -> {
            clickOn(rockPaperScissorsRadioButton);
            clickOn(maxRoundsTextField);
            write("2");
            clickOn(startButton);
        });
        sleep(1000);
        WaitForAsyncUtils.waitForFxEvents();
        loadAllIngameViewReferences();

        settingPlayers();

        int switchButtons = 0;
        while (stage.getTitle().equals("Ingame")) {
            switch (switchButtons) {
                case 0:
                    Platform.runLater(() ->
                            stageManager.getEditor().evaluateRound(humanPlayer, computerPlayer, ROCK, ROCK));
                    WaitForAsyncUtils.waitForFxEvents();
                    sleep(200);
                    break;
                case 1:
                    Platform.runLater(() ->
                            stageManager.getEditor().evaluateRound(humanPlayer, computerPlayer, PAPER, SCISSORS));
                    WaitForAsyncUtils.waitForFxEvents();
                    sleep(200);
                    break;
                case 2:
                    Platform.runLater(() ->
                            stageManager.getEditor().evaluateRound(humanPlayer, computerPlayer, ROCK, SCISSORS));
                    WaitForAsyncUtils.waitForFxEvents();
                    sleep(200);
                    break;
                default:
                    switchButtons = 0;
            }
            switchButtons++;
        }

        loadAllResultViewReferences();
        Assert.assertEquals("Title is not Result", "Result", stage.getTitle());
        Assert.assertEquals("finalScore is wrong!", "YOU 1 : 1 COM", finalResultScoreText.getText());
        Assert.assertEquals("Title is not Result", "A draw !", resultText.getText());

        Platform.runLater(() -> clickOn(backToStartButton));
        sleep(800);
    }
}
