package com.mikhail.pravilov.mit.ticTacToe.model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class that describes random bot in tictactoe game.
 */
public class TicTacToeRandomBot extends TicTacToeBot {
    /**
     * Default constructor, constructs width * height field.
     * @param width of the field.
     * @param height of the field.
     */
    public TicTacToeRandomBot(int width, int height) {
        super(width, height);
    }

    @Override
    protected void doBotTurn() {
        try {
            ArrayList<Pair<Integer, Integer>> freeCellsCoords = getFreeCellsCoords();
            int randomIndex = ThreadLocalRandom.current().nextInt(0, freeCellsCoords.size());
            doTurn(freeCellsCoords.get(randomIndex).getKey(), freeCellsCoords.get(randomIndex).getValue());
        } catch (IncorrectTurnException | GameIsEndedException e) {
            throw new IllegalStateException("Fatal error in algorithm");
        }
    }
}
