package com.mikhail.pravilov.mit.ftpClient.controller;

import com.mikhail.pravilov.mit.ftpClient.model.FtpFileManager;
import com.mikhail.pravilov.mit.ftpClient.view.FileManagerScene;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Controller class for connecting to server scene: processes input host and port.
 */
public class SettingUpConnectionSceneController {
    /**
     * Reads host and port numbers. Then runs shows file manager window if succeeded.
     *
     * @param mouseEvent needed to get stage.
     */
    public void ConnectButtonClicked(@NotNull MouseEvent mouseEvent) {
        Stage stage = ControllerUtils.getStage(mouseEvent);
        Scene scene = stage.getScene();
        String host = ControllerUtils.getTextFromTextField("#hostTextField", scene);
        int port = Integer.parseInt(ControllerUtils.getTextFromTextField("#portTextField", scene));
        FtpFileManager ftpFileManager;
        try {
            ftpFileManager = new FtpFileManager(host, port);
        } catch (IOException e) {
            ControllerUtils.showAlertErrorMessage("connecting to server: check host and port number", e.getLocalizedMessage());
            return;
        }
        try {
            stage.setScene((new FileManagerScene(ftpFileManager)).getScene());
        } catch (IOException e) {
            ControllerUtils.showAlertErrorMessage("loading fxml scene", e.getLocalizedMessage());
            tryToCloseFtpFileManager(ftpFileManager);
            return;
        }
        stage.setOnCloseRequest(event -> {
            tryToCloseFtpFileManager(ftpFileManager);
        });
    }

    /**
     * Tries to close ftp file manager.
     *
     * @param ftpFileManager to close.
     */
    private void tryToCloseFtpFileManager(@NotNull FtpFileManager ftpFileManager) {
        try {
            ftpFileManager.close();
        } catch (IOException e) {
            ControllerUtils.showAlertErrorMessage("closing connection with server", e.getLocalizedMessage());
        }
    }
}
