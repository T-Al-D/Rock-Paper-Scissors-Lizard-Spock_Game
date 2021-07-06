package de.uniks.pmws2021.rps.controller;

import de.uniks.pmws2021.rps.RPSEditor;
import de.uniks.pmws2021.rps.StageManager;
import de.uniks.pmws2021.rps.model.Player;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class ResultScreenController {

    private Player playerModel;
    private Player computerPlayerModel;
    private RPSEditor rpsEditor;
    private Parent view;
    private Text resultText;
    private Text finalResultScoreText;
    private Button backToStartButton;
    private ImageView resultImageView;

    public ResultScreenController(Parent parent, Player playerModel, Player computerPlayerModel, RPSEditor rpsEditor) {
        this.computerPlayerModel = computerPlayerModel;
        this.playerModel = playerModel;
        this.view = parent;
        this.rpsEditor = rpsEditor;
    }

    public void init() {

        // Load all view references
        resultText = (Text) view.lookup("#resultText");
        finalResultScoreText = (Text) view.lookup("#finalResultScoreText");
        backToStartButton = (Button) view.lookup("#backToStartButton");
        resultImageView = (ImageView) view.lookup("#resultImageView");

        // Add action listeners
        backToStartButton.setOnAction(event -> {
            StageManager.showStartScreen();
        });

        finalResultScoreText.setText(
                "YOU " + playerModel.getWinnings() + " : " + computerPlayerModel.getWinnings() + " COM");

        if (playerModel.getVictory() && !computerPlayerModel.getVictory()) {
            resultText.setText("Congratulations, you won !");
            loadAndSetPicture("Won.jpg", resultImageView);
        } else if (!playerModel.getVictory() && computerPlayerModel.getVictory()) {
            resultText.setText("Sorry, you lost !");
            loadAndSetPicture("Lost.jpg", resultImageView);
        } else {
            resultText.setText("A draw !");
            loadAndSetPicture("Draw.jpg", resultImageView);
        }
    }

    public void loadAndSetPicture(String dataName, ImageView image) {
        try {
            Image img = new Image(StageManager.class.getResource("pictures/" + dataName).toString());
            image.setImage(img);
        } catch (Exception e) {
            System.err.println(image + "Picture could not be loaded!");
            e.printStackTrace();
        }
    }

    public void stop() {
        // Clear references
        backToStartButton.setOnAction(null);
    }
}
