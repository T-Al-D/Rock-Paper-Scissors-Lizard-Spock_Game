package de.uniks.pmws2021.rps;

import de.uniks.pmws2021.rps.controller.IngameScreenController;
import de.uniks.pmws2021.rps.controller.ResultScreenController;
import de.uniks.pmws2021.rps.controller.StartScreenController;
import de.uniks.pmws2021.rps.model.Game;
import de.uniks.pmws2021.rps.model.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageManager extends Application {

    private static Stage stage;
    private static Game gameModel = new Game();
    private static Player playerModel = new Player();
    private static Player computerPlayerModel = new Player();
    private static RPSEditor rpsEditor = new RPSEditor();
    private static StartScreenController startScreenController;
    private static IngameScreenController ingameScreenController;
    private static ResultScreenController resultScreenController;

    @Override
    public void start(Stage primaryStage) {
        // start application
        stage = primaryStage;
        showStartScreen();
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        cleanup();
    }

    private static void cleanup() {
        // call cascading stop, erase References
        if (startScreenController != null) {
            startScreenController.stop();
            startScreenController = null;
        }
        if (ingameScreenController != null) {
            ingameScreenController.stop();
            ingameScreenController = null;
        }
        if (resultScreenController != null) {
            resultScreenController.stop();
            resultScreenController = null;
        }
    }

    public static void showStartScreen() {
        Parent parent = loadAndSetScene("StartScreen.fxml", "Start");
        startScreenController = new StartScreenController(parent, rpsEditor);
        startScreenController.init();
        System.out.println("Show the Start Screen");
    }

    public static void showIngameScreen() {
        settingPlayers();
        Parent parent = loadAndSetScene("IngameScreen.fxml", "Ingame");
        ingameScreenController = new IngameScreenController(parent, gameModel, playerModel, computerPlayerModel,
                rpsEditor);
        ingameScreenController.init();
        System.out.println("Show the Ingame Screen");
    }

    public static void showResultScreen() {
        Parent parent = loadAndSetScene("ResultScreen.fxml", "Result");
        resultScreenController = new ResultScreenController(parent, playerModel, computerPlayerModel, rpsEditor);
        resultScreenController.init();
        System.out.println("Show the Result Screen");
    }

    private static Parent loadAndSetScene(String fxmlFile, String title) {
        cleanup();
        try {
            //load view
            Parent root = FXMLLoader.load(StageManager.class.getResource("view/" + fxmlFile));
            Scene scene = new Scene(root);
            //DISPLAY
            stage.setScene(scene);
            stage.setTitle(title);
            return root;
        } catch (Exception e) {
            System.err.println(title + "Scene could not be loaded!");
            e.printStackTrace();
        }
        return null;
    }

    public static void settingPlayers() {
        gameModel = rpsEditor.getGame();
        for (Player player : gameModel.getPlayers()
        ) {
            if (player.getPlayerName().equals("Human")) {
                playerModel = player;
            }
            if (player.getPlayerName().equals("Computer")) {
                computerPlayerModel = player;
            }
        }
    }

    public RPSEditor getEditor() {
        return rpsEditor;
    }
}