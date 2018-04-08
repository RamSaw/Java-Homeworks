package com.mikhail.pravilov.mit.ticTacToe.model;

import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToeStatistic.*;

/**
 * Class that describes hot seat tictactoe game.
 */
public class TicTacToeHotSeat extends TicTacToe {
    /**
     * Default constructor, constructs width * height field.
     * @param width of the field.
     * @param height of the field.
     */
    public TicTacToeHotSeat(int width, int height) {
        super(width, height);
    }

    @Override
    public Mode getMode() {
        return Mode.HOTSEAT;
    }

    @Override
    public void processUserTurn(int x, int y) throws IncorrectTurnException, GameIsEndedException {
        doTurn(x, y);
    }
}
