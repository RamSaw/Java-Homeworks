package mit.mikhail.pravilov.com.controller;

import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class GameControllerTest {
    public static class AsNonApp extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            // noop
        }
    }

    @BeforeClass
    public static void initJFX() {
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(AsNonApp.class);
            }
        };
        t.setDaemon(true);
        t.start();
    }

    @Test
    public void testFieldWithSize2() throws Exception {
        int size = 2;
        int minValue = 0;
        int maxValue = 2 * 2 / 2;
        Map<Integer, Integer> timesValueWasMet = new HashMap<>();
        GameController gameController = new GameController(size);
        checkTimesMet(timesValueWasMet, gameController, size, minValue, maxValue);
    }

    @Test
    public void testFieldWithSize14() throws Exception {
        int size = 14;
        int minValue = 0;
        int maxValue = 14 * 14 / 2;
        Map<Integer, Integer> timesValueWasMet = new HashMap<>();
        GameController gameController = new GameController(size);
        checkTimesMet(timesValueWasMet, gameController, size, minValue, maxValue);
    }

    private void checkTimesMet(Map<Integer, Integer> timesValueWasMet, GameController gameController, int size, int minValue, int maxValue) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                assertEquals(gameController.getButton(i, j).getText(), "");
                int value = gameController.getValue(i, j);
                assertTrue(value >= minValue);
                assertTrue(value < maxValue);
                timesValueWasMet.put(value, 1 + timesValueWasMet.getOrDefault(value, 0));
            }
        }
        for (Integer times : timesValueWasMet.values()) {
            assertTrue(times % 2 == 0);
        }
    }
}