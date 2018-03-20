package com.mikhail.pravilov.mit.ticTacToe.model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

abstract public class TicTacToeBot extends TicTacToe {
    public TicTacToeBot(int width, int height) {
        super(width, height);
    }

    @Override
    public void processUserTurn(int x, int y) throws IncorrectTurnException, GameIsEndedException {
        doTurn(x, y);
        if (!isEnded()) {
            doBotTurn();
        }
    }

    abstract protected void doBotTurn();
}
