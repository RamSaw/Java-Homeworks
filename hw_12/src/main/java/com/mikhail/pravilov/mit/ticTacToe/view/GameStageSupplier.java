package com.mikhail.pravilov.mit.ticTacToe.view;

import com.mikhail.pravilov.mit.ticTacToe.model.TicTacToe;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.temporal.UnsupportedTemporalTypeException;

/**
 * Class that describes game stage supplier. Creates stage special for game process.
 */
public class GameStageSupplier implements StageSupplier {
    /**
     * TicTacToe game that is playing in this stage.
     */
    private TicTacToe game;
    /**
     * Field represented in ui by {@link GridPane}.
     */
    private GridPane field;
    /**
     * Stage of game process.
     */
    private Stage stage;
    /**
     * Cells of the field that extends buttons and can be clicked.
     */
    private Cell cells[][];

    /**
     * Constructor of game stage supplier. Creates Stage instance and sets main game process scene.
     * @param game to run.
     */
    public GameStageSupplier(@NotNull TicTacToe game) {
        this.game = game;
        this.stage = new Stage();
        try {
            stage.setScene(getGameScene());
        } catch (IOException e) {
            Alert errorDuringLoadingScene = new Alert(Alert.AlertType.INFORMATION);
            errorDuringLoadingScene.setTitle("Error");
            errorDuringLoadingScene.setHeaderText("Error during loading scene");
            errorDuringLoadingScene.setContentText("Reason: " + e.getLocalizedMessage());
            errorDuringLoadingScene.showAndWait();
        }
    }

    /**
     * Game scene getter.
     * @return main scene of the game process.
     * @throws IOException if fxml of scene failed to load.
     */
    @NotNull
    private Scene getGameScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/GameScene.fxml"));
        Scene scene = new Scene(root);
        HBox mainHBox = (HBox) scene.lookup("#mainHBox");
        setUpField();
        mainHBox.getChildren().add(0, field);
        return scene;
    }

    /**
     * Sets up all cells of game field. Defines onClick events for cells to make turns.
     */
    private void setUpField() {
        cells = new Cell[game.getWidth()][game.getHeight()];
        field = new GridPane();

        for (int x = 0; x < game.getWidth(); x++) {
            for (int y = 0; y < game.getHeight(); y++) {
                Cell cell = new Cell(x, y,"FREE");
                cells[x][y] = cell;
                cell.setMinSize(100, 100);
                int finalX = x;
                int finalY = y;
                cell.setOnAction(event -> {
                    try {
                        game.processUserTurn(finalX, finalY);
                    } catch (TicTacToe.IncorrectTurnException | TicTacToe.GameIsEndedException e) {
                        Alert errorTurnAlert = new Alert(Alert.AlertType.INFORMATION);
                        errorTurnAlert.setTitle("Error during making turn");
                        errorTurnAlert.setHeaderText("You cannot do this turn");
                        errorTurnAlert.setContentText("Reason: " + e.getLocalizedMessage());
                        errorTurnAlert.showAndWait();
                    }
                    updateField();
                    if (game.isEnded()) {
                        Alert winAlert = new Alert(Alert.AlertType.INFORMATION);
                        winAlert.setTitle("Game is over!");
                        winAlert.setHeaderText("Congratulations!");
                        String winningTeamCongrats = null;
                        switch (game.getGameState()) {
                            case DAGGER_WON:
                                winningTeamCongrats = "Dagger wins, congrats!";
                                break;
                            case ZERO_WON:
                                winningTeamCongrats = "Zero wins, congrats!";
                                break;
                            case DRAW:
                                winningTeamCongrats = "Draw!";
                                break;
                        }
                        winAlert.setContentText(winningTeamCongrats);
                        winAlert.showAndWait();
                        stage.close();
                    }
                });
                field.add(cell, x, y);
            }
        }
    }

    /**
     * Updates view and states of cells as buttons.
     */
    private void updateField() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                String newCellText;
                boolean isDisabled;
                switch (game.getTypeOfCell(cell.getX(), cell.getY())) {
                    case FREE:
                        newCellText = "FREE";
                        isDisabled = false;
                        break;
                    case DAGGER:
                        newCellText = "DAGGER";
                        isDisabled = true;
                        break;
                    case ZERO:
                        newCellText = "ZERO";
                        isDisabled = true;
                        break;
                    default:
                        throw new UnsupportedTemporalTypeException("Unknown type of cell");
                }
                cell.setText(newCellText);
                cell.setDisable(isDisabled);
            }
        }
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    /**
     * Class that describes cell of field in UI. It is particularly button.
     */
    private class Cell extends Button {
        /**
         * x coordinate of cell.
         */
        private int x;
        /**
         * y coordinate of cell.
         */
        private int y;

        /**
         * Constructor that creates instance of {@link Cell} with (x, y) position and text to display on button.
         * @param x coordinate of cell.
         * @param y coordinate of cell.
         * @param text to display.
         */
        Cell(int x, int y, String text) {
            super(text);
            this.x = x;
            this.y = y;
        }

        /**
         * Getter for x cell coordinate.
         * @return x cell coordinate.
         */
        int getX() {
            return x;
        }

        /**
         * Getter for y cell coordinate.
         * @return y cell coordinate.
         */
        int getY() {
            return y;
        }
    }
}
