package com.mikhail.pravilov.mit.ticTacToe.controller;

import com.mikhail.pravilov.mit.ticTacToe.model.TicTacToeBestStrategyBot;
import com.mikhail.pravilov.mit.ticTacToe.model.TicTacToeHotSeat;
import com.mikhail.pravilov.mit.ticTacToe.model.TicTacToeRandomBot;
import com.mikhail.pravilov.mit.ticTacToe.model.TicTacToeStatistic;
import com.mikhail.pravilov.mit.ticTacToe.model.TicTacToeStatistic.Mode;
import com.mikhail.pravilov.mit.ticTacToe.view.GameSceneSupplier;
import com.mikhail.pravilov.mit.ticTacToe.view.GameTypeSceneSupplier;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class that implements choose game type stage controllers.
 */
public class GameTypeStageController {
    private Stage getStage(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        return (Stage) source.getScene().getWindow();
    }

    /**
     * Starts new window(stage) for hot seat game.
     */
    public void startHotSeatGame(ActionEvent actionEvent) throws IOException {
        getStage(actionEvent).setScene(new GameSceneSupplier(new TicTacToeHotSeat(3, 3)).getScene());
    }

    /**
     * Starts new window(stage) for game with random bot.
     */
    public void startRandomBotGame(ActionEvent actionEvent) throws IOException {
        getStage(actionEvent).setScene(new GameSceneSupplier(new TicTacToeRandomBot(3, 3)).getScene());
    }

    /**
     * Starts new window(stage) for game with not loosing bot.
     */
    public void startBestStrategyBotGame(ActionEvent actionEvent) throws IOException {
        getStage(actionEvent).setScene(new GameSceneSupplier(new TicTacToeBestStrategyBot(3, 3)).getScene());
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

    public void backToChooseGameType(ActionEvent actionEvent) throws IOException {
        getStage(actionEvent).setScene(new GameTypeSceneSupplier().getScene());
    }
}
