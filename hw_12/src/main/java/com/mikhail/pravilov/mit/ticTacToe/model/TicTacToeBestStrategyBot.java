package com.mikhail.pravilov.mit.ticTacToe.model;

import javafx.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class TicTacToeBestStrategyBot extends TicTacToeBot {
    public TicTacToeBestStrategyBot(int width, int height) {
        super(width, height);
    }

    @Override
    protected void doBotTurn() {
        try {
            Pair<Integer, Integer> bestTurnPosition = getBestTurnPosition();
            doTurn(bestTurnPosition.getKey(), bestTurnPosition.getValue());
        } catch (IncorrectTurnException | GameIsEndedException e) {
            throw new IllegalStateException("Fatal error in bot algorithm");
        }
    }

    private Pair<Integer, Integer> getBestTurnPosition() throws IncorrectTurnException, GameIsEndedException {
        Pair<Integer, Integer> winningTurnPosition = getWinningTurnPosition();
        if (winningTurnPosition != null) {
            return winningTurnPosition;
        }

        Pair<Integer, Integer> NotLoosingTurnPosition = getNotLoosingTurnPosition();
        if (NotLoosingTurnPosition != null) {
            return NotLoosingTurnPosition;
        }

        return getFreeCellsCoords().get(0);
    }

    @Nullable
    private Pair<Integer, Integer> getWinningTurnPosition() throws IncorrectTurnException, GameIsEndedException {
        ArrayList<Pair<Integer, Integer>> freeCellsCoords = getFreeCellsCoords();
        for (Pair<Integer, Integer> freeCellCoord : freeCellsCoords) {
            if (isWinning(freeCellCoord.getKey(), freeCellCoord.getValue())) {
                return freeCellCoord;
            }
        }
        return null;
    }

    @Nullable
    private Pair<Integer, Integer> getNotLoosingTurnPosition() throws IncorrectTurnException, GameIsEndedException {
        for (Pair<Integer, Integer> freeCellCoord : getFreeCellsCoords()) {
            if (isNotLoosing(freeCellCoord.getKey(), freeCellCoord.getValue())) {
                return freeCellCoord;
            }
        }
        return null;
    }

    private boolean isWinning(int x, int y) throws IncorrectTurnException, GameIsEndedException {
        return checkWinRecursively(x, y);
    }

    private boolean checkWinRecursively(int x, int y) throws IncorrectTurnException, GameIsEndedException {
        if (isEnded()) {
            return false;
        }
        doTurn(x, y);

        for (Pair<Integer, Integer> freeCellCoord : getFreeCellsCoords()) {
            if (isEnded()) {
                GameState endGameState = getGameState();
                undoTurn(x, y);
                return endGameState != GameState.DRAW;
            }
            doTurn(freeCellCoord.getKey(), freeCellCoord.getValue());
            boolean existsWinningTurn = false;
            for (Pair<Integer, Integer> freeCellCoordNew : getFreeCellsCoords()) {
                if (checkWinRecursively(freeCellCoordNew.getKey(), freeCellCoordNew.getValue())) {
                    existsWinningTurn = true;
                    break;
                }
            }
            undoTurn(freeCellCoord.getKey(), freeCellCoord.getValue());
            if (!existsWinningTurn) {
                undoTurn(x, y);
                return false;
            }
        }
        undoTurn(x, y);
        return true;
    }

    private boolean isNotLoosing(int x, int y) throws IncorrectTurnException, GameIsEndedException {
        return checkNotLoosingRecursively(x, y);
    }

    private boolean checkNotLoosingRecursively(int x, int y) throws IncorrectTurnException, GameIsEndedException {
        if (isEnded()) {
            return getGameState() == GameState.DRAW;
        }
        doTurn(x, y);

        for (Pair<Integer, Integer> freeCellCoord : getFreeCellsCoords()) {
            if (isEnded()) {
                undoTurn(x, y);
                return true;
            }
            doTurn(freeCellCoord.getKey(), freeCellCoord.getValue());

            boolean existsNotLoosingTurn = getGameState() == GameState.DRAW;
            for (Pair<Integer, Integer> freeCellCoordNew : getFreeCellsCoords()) {
                if (checkNotLoosingRecursively(freeCellCoordNew.getKey(), freeCellCoordNew.getValue())) {
                    existsNotLoosingTurn = true;
                    break;
                }
            }
            undoTurn(freeCellCoord.getKey(), freeCellCoord.getValue());

            if (!existsNotLoosingTurn) {
                undoTurn(x, y);
                return false;
            }
        }
        undoTurn(x, y);
        return true;
    }
}
