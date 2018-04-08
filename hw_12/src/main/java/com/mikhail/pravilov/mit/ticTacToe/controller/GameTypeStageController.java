package com.mikhail.pravilov.mit.ticTacToe.controller;

import com.mikhail.pravilov.mit.ticTacToe.model.TicTacToeBestStrategyBot;
import com.mikhail.pravilov.mit.ticTacToe.model.TicTacToeHotSeat;
import com.mikhail.pravilov.mit.ticTacToe.model.TicTacToeRandomBot;
import com.mikhail.pravilov.mit.ticTacToe.model.TicTacToeStatistic;
import com.mikhail.pravilov.mit.ticTacToe.model.TicTacToeStatistic.Mode;
import com.mikhail.pravilov.mit.ticTacToe.view.GameStageSupplier;
import javafx.scene.control.Alert;

/**
 * Class that implements choose game type stage controllers.
 */
public class GameTypeStageController {
    /**
     * Starts new window(stage) for hot seat game.
     */
    public void startHotSeatGame() {
        new GameStageSupplier(new TicTacToeHotSeat(3, 3)).getStage().show();
    }

    /**
     * Starts new window(stage) for game with random bot.
     */
    public void startRandomBotGame() {
        new GameStageSupplier(new TicTacToeRandomBot(3, 3)).getStage().show();
    }

    /**
     * Starts new window(stage) for game with not loosing bot.
     */
    public void startBestStrategyBotGame() {
        new GameStageSupplier(new TicTacToeBestStrategyBot(3, 3)).getStage().show();
    }

    /**
     * Starts new window(alert) that displays statistic of user.
     */
    public void showStatistic() {
        Alert showStatisticAlert = new Alert(Alert.AlertType.INFORMATION);
        showStatisticAlert.setTitle("Statistic");
        showStatisticAlert.setHeaderText("You have the following statistic:");
        TicTacToeStatistic statistic = TicTacToeStatistic.getInstance();
        StringBuilder contentText = new StringBuilder();
        for (Mode mode: Mode.values()) {
            contentText.append(mode).append('\n').
                    append("Dagger: ").append(statistic.getNumberOfDaggerWins(mode)).
                    append(", Zero: ").append(statistic.getNumberOfZeroWins(mode)).
                    append(", Draws: ").append(statistic.getNumberOfDraws(mode)).
                    append('\n');
        }
        showStatisticAlert.setContentText(contentText.toString());
        showStatisticAlert.showAndWait();
    }
}
