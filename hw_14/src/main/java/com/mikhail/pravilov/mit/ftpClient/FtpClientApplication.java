package com.mikhail.pravilov.mit.ftpClient;

import com.mikhail.pravilov.mit.ftpClient.view.SettingUpConnectionScene;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main application class.
 */
public class FtpClientApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage stage = new Stage();
        stage.setScene((new SettingUpConnectionScene()).getScene());
        stage.show();
    }

    /**
     * Main method of program. Runs FtpClient application.
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
