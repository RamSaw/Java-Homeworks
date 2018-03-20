package com.mikhail.pravilov.mit.ticTacToe.model;

public class TicTacToeHotSeat extends TicTacToe {
    public TicTacToeHotSeat(int width, int height) {
        super(width, height);
    }

    @Override
    public void processUserTurn(int x, int y) throws IncorrectTurnException, GameIsEndedException {
        doTurn(x, y);
    }
}
