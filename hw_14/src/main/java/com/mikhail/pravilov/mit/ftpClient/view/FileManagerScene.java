package com.mikhail.pravilov.mit.ftpClient.view;

import com.mikhail.pravilov.mit.ftpClient.controller.FileManagerSceneController;
import com.mikhail.pravilov.mit.ftpClient.model.FileManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Class that constructs and supplies file manager scene (window).
 */
public class FileManagerScene {
    /**
     * Constructed file manager scene.
     */
    @NotNull
    private Scene scene;

    /**
     * Constructs file manager scene with given {@link FileManager} and initializes its controller.
     *
     * @param fileManager to init scene controller with.
     * @throws IOException if cannot load fxml scene.
     */
    public FileManagerScene(@NotNull FileManager fileManager) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FileManagerScene.fxml"));
        Parent root = loader.load();
        FileManagerSceneController fileManagerSceneController = loader.getController();
        fileManagerSceneController.init(fileManager);
        scene = new Scene(root);
    }

    @NotNull
    public Scene getScene() {
        return scene;
    }
}
