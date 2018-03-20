package com.mikhail.pravilov.mit.ticTacToe.model;

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
    public void processUserTurn(int x, int y) throws IncorrectTurnException, GameIsEndedException {
        doTurn(x, y);
    }
}
