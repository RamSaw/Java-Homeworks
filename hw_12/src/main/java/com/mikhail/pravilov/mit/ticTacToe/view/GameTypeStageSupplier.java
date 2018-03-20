package com.mikhail.pravilov.mit.ticTacToe.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Game type stage supplier. Return stage where you can select type of tictactoe game.
 */
public class GameTypeStageSupplier implements StageSupplier {
    /**
     * Stage that returns.
     */
    private Stage stage;

    /**
     * Creates Stage instance and sets main choose game type scene.
     */
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

    /**
     * Returns main scene of this stage. Scene is to choose game type.
     * @return created scene.
     * @throws IOException if fxml scene file failed to load.
     */
    @NotNull
    private Scene getChooseGameTypeScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/GameTypeScene.fxml"));
        return new Scene(root);
    }

    @Override
    public Stage getStage() {
        return stage;
    }
}
