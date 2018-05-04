package mit.mikhail.pravilov.com;

import javafx.application.Application;
import javafx.stage.Stage;
import mit.mikhail.pravilov.com.view.FindPairSceneSupplier;

import static java.lang.System.exit;

public class FindPairApplication extends Application {
    private static int size;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage stage = new Stage();
        stage.setScene(new FindPairSceneSupplier().getScene(size));
        stage.show();
    }

    /**
     * Main method of program. Runs tictactoe application.
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Arguments: field size");
            exit(0);
        }
        size = Integer.parseInt(args[0]);
        if (size % 2 != 0 || size <= 0 || size > 15) {
            System.err.println("Field size is incorrect!");
            exit(0);
        }
        Application.launch(args);
    }
}
