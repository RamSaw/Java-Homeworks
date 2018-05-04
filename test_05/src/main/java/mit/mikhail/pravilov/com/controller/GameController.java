package mit.mikhail.pravilov.com.controller;

import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameController {
    private int size;

    public int getValue(int i, int j) {
        return field[i][j];
    }

    private int[][] field;
    private Button prevButton;
    private Button[][] buttons;
    private int leftToFind;

    public GameController(int size) {
        this.size = size;
        buttons = new Button[size][size];
        leftToFind = size * size;
        initButtons();
    }

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

    private void setUpField(List<Integer> randomIntegers) {
        field = new int[size][size];
        for (int i = 0; i < size; i++) {
            field[i] = new int[size];
            for (int j = 0; j < size; j++) {
                field[i][j] = randomIntegers.get(i * size + j);
            }
        }
    }

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

    public Button getButton(int i, int j) {
        return buttons[i][j];
    }
}
