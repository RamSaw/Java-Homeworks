package com.mikhail.pravilov.mit.ticTacToe.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class GameTypeStageSupplier implements StageSupplier {
    private Stage stage;

    public GameTypeStageSupplier() {
        stage = new Stage();
        try {
            stage.setScene(getChooseGameTypeScene());
        } catch (IOException e) {
            Alert errorDuringLoadingScene = new Alert(Alert.AlertType.INFORMATION);
            errorDuringLoadingScene.setTitle("Error");
            errorDuringLoadingScene.setHeaderText("Error during loading scene");
            errorDuringLoadingScene.setContentText("Reason: " + e.getLocalizedMessage());
            errorDuringLoadingScene.showAndWait();
        }
    }

    private Scene getChooseGameTypeScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/GameTypeScene.fxml"));
        return new Scene(root);
    }

    @Override
    public Stage getStage() {
        return stage;
    }
}
