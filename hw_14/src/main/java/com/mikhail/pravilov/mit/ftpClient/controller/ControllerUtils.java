package com.mikhail.pravilov.mit.ftpClient.controller;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

/**
 * Static methods for often used function in javafx controllers.
 */
class ControllerUtils {
    /**
     * Returns stage where event has happened.
     *
     * @param event that happened.
     * @return event stage.
     */
    @NotNull
    static Stage getStage(@NotNull Event event) {
        Node source = (Node) event.getSource();
        return (Stage) source.getScene().getWindow();
    }

    /**
     * Returns text in {@link TextField}.
     *
     * @param elementId id of {@link TextField}.
     * @param scene where to look for given element.
     * @return text in specified {@link TextField}.
     */
    @NotNull
    static String getTextFromTextField(@NotNull String elementId, @NotNull Scene scene) {
        return ((TextField) scene.lookup(elementId)).getText();
    }

    /**
     * Shows error in alert window.
     *
     * @param when during what operation the error occurred.
     * @param errorMessage description of the error.
     */
    static void showAlertErrorMessage(@NotNull String when, @NotNull String errorMessage) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Error during " + when);
        errorAlert.setContentText("Error message: " + errorMessage);
        errorAlert.showAndWait();
    }
}
