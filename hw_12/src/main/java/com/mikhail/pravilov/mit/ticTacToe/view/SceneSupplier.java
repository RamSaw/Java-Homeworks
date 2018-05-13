package com.mikhail.pravilov.mit.ticTacToe.view;

import javafx.scene.Scene;

import java.io.IOException;

/**
 * Interface that describes methods of stage suppliers.
 */
interface SceneSupplier {
    /**
     * Stage getter.
     * @return Stage instance.
     */
    Scene getScene() throws IOException;
}
