package com.mikhail.pravilov.mit.ticTacToe;

import com.mikhail.pravilov.mit.ticTacToe.model.TicTacToeStatistic;
import com.mikhail.pravilov.mit.ticTacToe.view.GameTypeStageSupplier;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;

public class TicTacToeApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        new GameTypeStageSupplier().getStage().show();
    }

    @Override
    public void stop() throws Exception {
        try {
            TicTacToeStatistic.getInstance().save();
        }
        catch (JSONException | IOException e) {
            Alert savingStatisticError = new Alert(Alert.AlertType.INFORMATION);
            savingStatisticError.setTitle("Error");
            savingStatisticError.setHeaderText("Error during saving statistic");
            savingStatisticError.setContentText("Reason: " + e.getLocalizedMessage());
            savingStatisticError.showAndWait();
        }

        super.stop();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
