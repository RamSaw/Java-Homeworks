package com.mikhail.pravilov.mit.ticTacToe.model;

/**
 * Class that describes basic behaviour all TicTacToe games with bots.
 */
abstract public class TicTacToeBot extends TicTacToe {
    /**
     * Default constructor, constructs width * height field.
     * @param width of the field.
     * @param height of the field.
     */
    TicTacToeBot(int width, int height) {
        super(width, height);
    }

    @Override
    public void processUserTurn(int x, int y) throws IncorrectTurnException, GameIsEndedException {
        doTurn(x, y);
        if (!isEnded()) {
            doBotTurn();
        }
    }

    /**
     * Performs bot turn as it should be.
     */
    abstract protected void doBotTurn();
}
