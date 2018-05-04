package mit.mikhail.pravilov.com.view;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import mit.mikhail.pravilov.com.controller.GameController;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class FindPairSceneSupplier {
    private GridPane field;

    /**
     * Game scene getter.
     * @return main scene of the game process.
     * @throws IOException if fxml of scene failed to load.
     */
    @NotNull
    private Scene getGameScene(int n) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("/view/FindPairScene.fxml"));
        setUpField(n);
        return new Scene(field);
    }

    private void setUpField(int n) {
        GameController gameController = new GameController(n);
        field = new GridPane();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                field.add(gameController.getButton(i, j), i, j);
            }
        }
    }
    public Scene getScene(int n) throws IOException {
        return getGameScene(n);
    }
}
