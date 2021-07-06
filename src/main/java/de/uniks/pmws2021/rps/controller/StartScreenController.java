package de.uniks.pmws2021.rps.controller;

import de.uniks.pmws2021.rps.RPSEditor;
import de.uniks.pmws2021.rps.StageManager;
import de.uniks.pmws2021.rps.model.Player;
import javafx.scene.Parent;
import javafx.scene.control.*;

public class StartScreenController {

    private Player playerModel;
    private Player computerPlayerModel;
    private RPSEditor rpsEditor;
    private Parent view;
    private Button startButton;
    private RadioButton rockPaperScissorsRadioButton;
    private RadioButton rockPaperScissorsLizardSpockRadioButton;
    private CheckBox bestOfCheckBox;
    private TextField maxRoundsTextField;

    public StartScreenController(Parent parent, RPSEditor rpsEditor) {
        this.view = parent;
        this.rpsEditor = rpsEditor;
    }

    public void init() {
        // Load all view references
        startButton = (Button) view.lookup("#startButton");
        rockPaperScissorsRadioButton = (RadioButton) view.lookup("#rockPaperScissorsRadioButton");
        rockPaperScissorsLizardSpockRadioButton = (RadioButton) view.lookup(
                "#rockPaperScissorsLizardSpockRadioButton");
        bestOfCheckBox = (CheckBox) view.lookup("#bestOfCheckBox");
        maxRoundsTextField = (TextField) view.lookup("#maxRoundsTextField");

        // Add action listeners
        startButton.setOnAction(event -> {
            // if game Extension true, with lizard and spock
            int gameRounds = 0;
            boolean bestOf = bestOfCheckBox.isSelected();
            boolean gameExtension = rockPaperScissorsLizardSpockRadioButton.isSelected();

            try {
                int roundsInput = Integer.parseInt(maxRoundsTextField.getText());
                if (bestOf && (roundsInput % 2 != 0)) {
                    gameRounds = roundsInput;
                }
                if (!bestOf) {
                    gameRounds = roundsInput;
                }
                if (bestOf && (roundsInput % 2 == 0)) {
                    throw (new NumberFormatException());
                }

                playerModel = rpsEditor.havePlayer("Human");
                computerPlayerModel = rpsEditor.havePlayer("Computer");
                rpsEditor.haveGame(gameExtension, bestOf, gameRounds, playerModel, computerPlayerModel);

                StageManager.showIngameScreen();

            } catch (NumberFormatException numberFormatException) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("No Valid Rounds!");
                a.setHeaderText("Game stopped because invalid Number!");
                a.setContentText("Please insert a valid Number in the Textfield!");
                a.setResizable(true);
                a.showAndWait();
            }
        });
    }

    public void stop() {
        // Clear references
        startButton.setOnAction(null);
        rockPaperScissorsRadioButton.setOnAction(null);
        rockPaperScissorsLizardSpockRadioButton.setOnAction(null);
    }
}

