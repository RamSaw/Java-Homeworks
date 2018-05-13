package com.mikhail.pravilov.mit.ticTacToe;

import com.mikhail.pravilov.mit.ticTacToe.model.TicTacToeStatistic;
import com.mikhail.pravilov.mit.ticTacToe.view.GameTypeSceneSupplier;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;

public class TicTacToeApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage stage = new Stage();
        stage.setScene(new GameTypeSceneSupplier().getScene());
        stage.show();
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

    /**
     * Main method of program. Runs tictactoe application.
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
