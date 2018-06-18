package mit.mikhail.pravilov.com.controller;

import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class that controls game flow and creates buttons for user interaction.
 */
public class GameController {
    /**
     * Size of the field.
     */
    private int size;
    /**
     * Values on the field.
     */
    private int[][] field;
    /**
     * Previous pressed button.
     */
    private Button prevButton;
    /**
     * Field of buttons for each cell.
     */
    private Button[][] buttons;
    /**
     * How much cells are not discovered yet.
     */
    private int leftToFind;

    /**
     * Initializes all values: integer values, buttons.
     * @param size of the field.
     */
    public GameController(int size) {
        this.size = size;
        buttons = new Button[size][size];
        leftToFind = size * size;
        initButtons();
    }

    /**
     * Initializes buttons: set ups on click listeners.
     */
    private void initButtons() {
        List<Integer> randomIntegers = getRandomIntegers();
        setUpField(randomIntegers);
        for (int i = 0; i < size; i++) {
            buttons[i] = new Button[size];
            for (int j = 0; j < size; j++) {
                Button button = new Button();
                button.setMinSize(500 /size, 500 / size);
                buttons[i][j] = button;
                int finalI = i;
                int finalJ = j;
                button.setOnMouseClicked(e -> {
                    button.setDisable(true);
                    button.setText(String.valueOf(field[finalI][finalJ]));
                    if (prevButton == null) {
                        prevButton = button;
                    }
                    else {
                        if (prevButton.getText().equals(button.getText())) {
                            leftToFind -= 2;
                            if (leftToFind == 0) {
                                Alert winAlert = new Alert(Alert.AlertType.INFORMATION);
                                winAlert.setTitle("Congrats");
                                winAlert.setHeaderText("WiN!");
                                winAlert.showAndWait();
                            }
                        }
                        else {
                            Button savePrevButton = prevButton;
                            Task<Void> sleeper = new Task<Void>() {
                                @Override
                                protected Void call() throws Exception {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException ignored) {
                                    }
                                    return null;
                                }
                            };
                            sleeper.setOnSucceeded(event -> {
                                savePrevButton.setDisable(false);
                                button.setDisable(false);
                                savePrevButton.setText("");
                                button.setText("");
                            });
                            new Thread(sleeper).start();
                        }
                        prevButton = null;
                    }
                });
            }
        }
    }

    /**
     * Sets up field and puts passed values in cells.
     * @param randomIntegers integers to put.
     */
    private void setUpField(List<Integer> randomIntegers) {
        field = new int[size][size];
        for (int i = 0; i < size; i++) {
            field[i] = new int[size];
            for (int j = 0; j < size; j++) {
                field[i][j] = randomIntegers.get(i * size + j);
            }
        }
    }

    /**
     * Creates integers with pairs and then shuffles them.
     * @return shuffled paired integers.
     */
    private List<Integer> getRandomIntegers() {
        List<Integer> randomIntegers = new LinkedList<>();
        for (int i = 0; i < size * size;) {
            int random = ThreadLocalRandom.current().nextInt(0, size * size / 2);
            randomIntegers.add(random);
            randomIntegers.add(random);
            i += 2;
        }
        Collections.shuffle(randomIntegers);
        return randomIntegers;
    }

    /**
     * Returns button corresponding the cell.
     * @param i x coordinate of cell.
     * @param j y coordinate of cell.
     * @return button for given cell.
     */
    public Button getButton(int i, int j) {
        return buttons[i][j];
    }

    /**
     * Get value in cell.
     * @param i x coordinate of cell.
     * @param j y coordinate of cell.
     * @return integer value in cell.
     */
    int getValue(int i, int j) {
        return field[i][j];
    }
}
