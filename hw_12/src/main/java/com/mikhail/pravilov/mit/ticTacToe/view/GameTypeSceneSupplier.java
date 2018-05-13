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
public class GameTypeSceneSupplier implements SceneSupplier {
    @Override
    public Scene getScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/GameTypeScene.fxml"));
        return new Scene(root);
    }
}
