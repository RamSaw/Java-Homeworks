package com.mikhail.pravilov.mit.ftpClient.controller;

import com.mikhail.pravilov.mit.ftpClient.model.FileManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Controller for file manager: processes selecting folders and files.
 */
public class FileManagerSceneController {
    /**
     * {@link ListView} where listed all files in current directory.
     */
    @FXML
    private ListView<String> filesListView;
    /**
     * {@link ListView} where listed all directories in current directory.
     */
    @FXML
    private ListView<String> directoriesListView;
    /**
     * {@link TextField} where current directory is written.
     */
    @FXML
    private TextField pathTextField;
    /**
     * {@link FileManager} that supplies list of files and transfers a file.
     */
    private FileManager fileManager;

    /**
     * Attaches given {@link FileManager} to this scene and initializes it.
     *
     * @param fileManager to attach.
     */
    public void init(@NotNull FileManager fileManager) {
        this.fileManager = fileManager;
        try {
            fileManager.setCurrentPath(Paths.get(System.getProperty("user.home")));
        } catch (IOException e) {
            ControllerUtils.showAlertErrorMessage("getting list of files in directory", e.getLocalizedMessage());
        }
        updateFilesListView();
    }

    /**
     * Processes double mouse clicked on directory: goes in it.
     *
     * @param mouseEvent supplies mouse clicking information.
     */
    public void directoriesListViewMouseClicked(@NotNull MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            String item = directoriesListView.getSelectionModel().getSelectedItem();
            if (fileManager.getCurrentDirectories().contains(item)) {
                try {
                    fileManager.goDown(item);
                } catch (IOException e) {
                    ControllerUtils.showAlertErrorMessage("getting list of files in directory", e.getLocalizedMessage());
                }
                updateFilesListView();
            }
            else {
                throw new IllegalStateException("Selected item is not valid");
            }
        }
    }

    /**
     * Processes back button clicking: goes back in files tree.
     */
    public void backButtonMouseClicked() {
        try {
            fileManager.goUp();
        } catch (IOException e) {
            ControllerUtils.showAlertErrorMessage("getting list of files in directory", e.getLocalizedMessage());
        }
        updateFilesListView();
    }

    /**
     * Updates list views.
     */
    private void updateFilesListView() {
        pathTextField.setText(fileManager.getCurrentPath().toAbsolutePath().toString());
        directoriesListView.setItems(FXCollections.observableList(fileManager.getCurrentDirectories()));
        filesListView.setItems(FXCollections.observableList(fileManager.getCurrentFiles()));
    }

    /**
     * Processes double mouse clicked on files: starts dialog to choose folder where to download this file.
     *
     * @param mouseEvent supplies mouse clicking information.
     */
    public void filesListViewMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            String item = filesListView.getSelectionModel().getSelectedItem();
            if (fileManager.getCurrentFiles().contains(item)) {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("Select folder to save a file");
                directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                File directory = directoryChooser.showDialog(ControllerUtils.getStage(mouseEvent));
                if (directory != null) {
                    try {
                        fileManager.copy(item, Paths.get(directory.toURI()));
                    } catch (IOException e) {
                        ControllerUtils.showAlertErrorMessage("downloading a file", e.getLocalizedMessage());
                    }
                }
            } else {
                throw new IllegalStateException("Selected item is not valid");
            }
        }
    }
}
