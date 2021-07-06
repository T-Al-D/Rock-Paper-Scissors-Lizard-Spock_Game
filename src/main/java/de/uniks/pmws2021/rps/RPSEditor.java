package de.uniks.pmws2021.rps;

import de.uniks.pmws2021.rps.model.Game;
import de.uniks.pmws2021.rps.model.Player;

import static de.uniks.pmws2021.rps.Constants.*;

public class RPSEditor {

    public static Game game = new Game();

    public Player havePlayer(String playerName) {

        return new Player().setPlayerName(playerName).setCurrentMove("").setWinnings(0).setVictory(false);
    }

    public Game haveGame(boolean gameExtension, boolean bestOf, int gameRounds, Player player1, Player player2) {

        game = new Game().setGameModeExtension(gameExtension).setBestOf(bestOf)
                .setMaxRounds(gameRounds).setCurrentRound(0);

        player1.setGame(game);
        player2.setGame(game);

        return game;
    }

    public Game getGame() {
        return game;
    }

    public Object evaluateRound(Player playerModel, Player computerPlayerModel, String playerMove, String computerMove) {

        computerPlayerModel.setCurrentMove(computerMove);
        playerModel.setCurrentMove(playerMove);

        // if it´s draw, do nothing
        if (playerMove.equals(computerMove)) {
            System.out.println("Both choose the same !");
            return null;
        }

        // player wins against computer
        if (playerMove.equals(ROCK) && (computerMove.equals(SCISSORS) || computerMove.equals(LIZARD))) {
            System.out.println("HUMAN win!");
            playerModel.setWinnings(playerModel.getWinnings() + 1);

        } else if (playerMove.equals(PAPER) && (computerMove.equals(ROCK) || computerMove.equals(SPOCK))) {
            System.out.println("HUMAN win!");
            playerModel.setWinnings(playerModel.getWinnings() + 1);

        } else if (playerMove.equals(SCISSORS) && (computerMove.equals(PAPER) || computerMove.equals(LIZARD))) {
            System.out.println("HUMAN win!");
            playerModel.setWinnings(playerModel.getWinnings() + 1);

        } else if (playerMove.equals(LIZARD) && (computerMove.equals(PAPER) || computerMove.equals(SPOCK))) {
            System.out.println("HUMAN win!");
            playerModel.setWinnings(playerModel.getWinnings() + 1);

        } else if (playerMove.equals(SPOCK) && (computerMove.equals(ROCK) || computerMove.equals(SCISSORS))) {
            System.out.println("HUMAN win!");
            playerModel.setWinnings(playerModel.getWinnings() + 1);

        } else { // if player didn´t win , computer won !
            System.out.println("COMPUTER win!");
            computerPlayerModel.setWinnings(computerPlayerModel.getWinnings() + 1);
        }

        game.setCurrentRound(game.getCurrentRound() + 1);

        // if it was the last round
        if (game.getMaxRounds() == game.getCurrentRound()) {
            evaluateWinner(playerModel, computerPlayerModel);
        }

        //one player won 2/3 of the rounds in bestOf
        if (game.getBestOf()) {
            if ((Math.round((game.getMaxRounds() * 0.67))) <= playerModel.getWinnings() ||
                    (Math.round((game.getMaxRounds() * 0.67))) <= computerPlayerModel.getWinnings()
            ) {
                evaluateWinner(playerModel, computerPlayerModel);
            }
        }
        return null;
    }

    public void evaluateWinner(Player player1, Player player2) {
        if (player1.getWinnings() > player2.getWinnings()) {
            player1.setVictory(true);
        } else if (player1.getWinnings() < player2.getWinnings()) {
            player2.setVictory(true);
        }
    }
}
