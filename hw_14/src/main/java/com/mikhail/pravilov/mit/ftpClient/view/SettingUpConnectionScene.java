package com.mikhail.pravilov.mit.ftpClient.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Implements constructing and supplying scene for connection to server.
 */
public class SettingUpConnectionScene {
    /**
     * Scene for connection to server.
     */
    @NotNull
    private Scene scene;

    /**
     * Constructs scene.
     *
     * @throws IOException if cannot load fxml scene.
     */
    public SettingUpConnectionScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/SettingUpConnectionScene.fxml"));
        scene = new Scene(root);
    }

    /**
     * Returns constructed scene.
     *
     * @return scene for connection to server.
     */
    @NotNull
    public Scene getScene() {
        return scene;
    }
}
