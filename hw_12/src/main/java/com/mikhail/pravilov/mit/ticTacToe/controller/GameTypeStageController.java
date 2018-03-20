package com.mikhail.pravilov.mit.ticTacToe.controller;

import com.mikhail.pravilov.mit.ticTacToe.model.TicTacToeBestStrategyBot;
import com.mikhail.pravilov.mit.ticTacToe.model.TicTacToeHotSeat;
import com.mikhail.pravilov.mit.ticTacToe.model.TicTacToeRandomBot;
import com.mikhail.pravilov.mit.ticTacToe.model.TicTacToeStatistic;
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
        showStatisticAlert.setContentText("Dagger: " + statistic.getNumberOfDaggerWins() + ", Zero: " + statistic.getNumberOfZeroWins() + ", Draws: " + statistic.getNumberOfDraws());
        showStatisticAlert.showAndWait();
    }
}
