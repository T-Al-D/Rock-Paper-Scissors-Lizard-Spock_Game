package de.uniks.pmws2021.rps.controller;

import de.uniks.pmws2021.rps.RPSEditor;
import de.uniks.pmws2021.rps.StageManager;
import de.uniks.pmws2021.rps.model.Game;
import de.uniks.pmws2021.rps.model.Player;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

import static de.uniks.pmws2021.rps.Constants.*;
import static java.lang.Thread.sleep;

public class IngameScreenController {

    private final Game gameModel;
    private final Player computerPlayerModel;
    private final Player playerModel;
    private final Parent view;
    private final RPSEditor rpsEditor;
    private Button leaveButton;
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
    private final PropertyChangeListener currentRoundListener = this::currentRoundChanged;
    private final PropertyChangeListener playerVictoryListener = this::goToResultScreen;
    private final PropertyChangeListener computerPlayerVictoryListener = this::goToResultScreen;
    private final PropertyChangeListener playerMoveListener = this::loadPictureforMove;
    private final PropertyChangeListener computerPlayerMoveListener = this::loadPictureforMove;


    public IngameScreenController(Parent parent, Game gameModel, Player playerModel,
                                  Player computerPlayerModel, RPSEditor rpsEditor) {
        this.gameModel = gameModel;
        this.computerPlayerModel = computerPlayerModel;
        this.playerModel = playerModel;
        this.view = parent;
        this.rpsEditor = rpsEditor;
    }

    public void init() {
        // Load all view references
        leaveButton = (Button) view.lookup("#leaveButton");
        rockButton = (Button) view.lookup("#rockButton");
        paperButton = (Button) view.lookup("#paperButton");
        scissorsButton = (Button) view.lookup("#scissorsButton");
        lizardButton = (Button) view.lookup("#lizardButton");
        spockButton = (Button) view.lookup("#spockButton");

        currentScoreText = (Text) view.lookup("#currentScoreText");
        currentRoundText = (Text) view.lookup("#currentRoundText");
        bestOfText = (Text) view.lookup("#bestOfText");

        youPlayImageView = (ImageView) view.lookup("#youPlayImageView");
        computerPlaysImageView = (ImageView) view.lookup("#computerPlaysImageView");

        // Add action listeners
        leaveButton.setOnAction(event -> {
            stop();
            StageManager.showStartScreen();
        });

        rockButton.setOnAction(event ->
                rpsEditor.evaluateRound(playerModel, computerPlayerModel, ROCK, computerMoveGenerator()));

        paperButton.setOnAction(event ->
                rpsEditor.evaluateRound(playerModel, computerPlayerModel, PAPER, computerMoveGenerator()));

        scissorsButton.setOnAction(event ->
                rpsEditor.evaluateRound(playerModel, computerPlayerModel, SCISSORS, computerMoveGenerator()));

        if (playerModel.getGame().getGameModeExtension()) {

            lizardButton.setOnAction(event ->
                    rpsEditor.evaluateRound(playerModel, computerPlayerModel, LIZARD, computerMoveGenerator()));

            spockButton.setOnAction(event ->
                    rpsEditor.evaluateRound(playerModel, computerPlayerModel, SPOCK, computerMoveGenerator()));

        } else {
            // disable Button + invisibility
            lizardButton.setDisable(true);
            lizardButton.setVisible(false);
            spockButton.setDisable(true);
            spockButton.setVisible(false);
        }

        //Texts
        currentRoundText.setText(
                "Round: " + playerModel.getGame().getCurrentRound() + " / " + playerModel.getGame().getMaxRounds());

        currentScoreText.setText(
                "Current Score:     YOU " + playerModel.getWinnings() +
                        " : " + computerPlayerModel.getWinnings() + " COM");

        if (playerModel.getGame().getBestOf()) {
            bestOfText.setText("Best of " + playerModel.getGame().getMaxRounds());
        }

        //PropertyChangeListener
        gameModel.addPropertyChangeListener(Game.PROPERTY_CURRENT_ROUND, currentRoundListener);
        playerModel.addPropertyChangeListener(Player.PROPERTY_VICTORY, playerVictoryListener);
        playerModel.addPropertyChangeListener(Player.PROPERTY_CURRENT_MOVE, playerMoveListener);
        computerPlayerModel.addPropertyChangeListener(Player.PROPERTY_VICTORY, computerPlayerVictoryListener);
        computerPlayerModel.addPropertyChangeListener(Player.PROPERTY_CURRENT_MOVE, computerPlayerMoveListener);
    }

    public String computerMoveGenerator() {
        Random random = new Random();
        int randomizedNumber;
        if (playerModel.getGame().getGameModeExtension()) {
            randomizedNumber = random.nextInt(5);
        } else {
            randomizedNumber = random.nextInt(3);
        }
        switch (randomizedNumber) {
            case 0:
                return ROCK;
            case 1:
                return PAPER;
            case 2:
                return SCISSORS;
            case 3:
                return LIZARD;
            case 4:
                return SPOCK;
            default:
                return null;
        }
    }

    private void loadPictureforMove(PropertyChangeEvent propertyChangeEvent) {
        Player player = (Player) propertyChangeEvent.getSource();
        String imageName;
        switch (player.getCurrentMove()) {
            case ROCK:
                imageName = "Rock.JPG";
                break;
            case PAPER:
                imageName = "Paper.JPG";
                break;
            case SCISSORS:
                imageName = "Scissors.JPG";
                break;
            case LIZARD:
                imageName = "Lizard.JPG";
                break;
            case SPOCK:
                imageName = "Spock.JPG";
                break;
            default:
                imageName = null;
        }
        if (player.equals(playerModel)) {
            loadAndSetPicture(imageName, youPlayImageView);
        } else {
            loadAndSetPicture(imageName, computerPlaysImageView);
        }
    }

    private void loadAndSetPicture(String dataName, ImageView image) {
        try {
            Image img = new Image(StageManager.class.getResource("pictures/" + dataName).toString());
            image.setImage(img);
        } catch (Exception e) {
            System.err.println(image + "Picture could not be loaded!");
            e.printStackTrace();
        }
    }

    private void currentRoundChanged(PropertyChangeEvent propertyChangeEvent) {
        currentRoundText.setText(
                "Round: " + playerModel.getGame().getCurrentRound() + " / " + playerModel.getGame().getMaxRounds());
        currentScoreText.setText("Current Score:     YOU " + playerModel.getWinnings() + " : "
                + computerPlayerModel.getWinnings() + " COM");

        if (gameModel.getMaxRounds() == gameModel.getCurrentRound()) {
            goToResultScreen(propertyChangeEvent);
        }
    }

    private void goToResultScreen(PropertyChangeEvent propertyChangeEvent) {
        try {
            sleep(2000);
            stop();
            StageManager.showResultScreen();
        } catch (Exception e) {
            System.err.println(e + "has been found!");
        }
    }

    public void stop() {
        // Clear references
        leaveButton.setOnAction(null);
        rockButton.setOnAction(null);
        paperButton.setOnAction(null);
        scissorsButton.setOnAction(null);
        lizardButton.setOnAction(null);
        spockButton.setOnAction(null);
    }
}
