package com.mikhail.pravilov.mit.ticTacToe.model;

import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToeStatistic.*;

/**
 * Class that implements not loosing bot that plays always the best strategy by pre-calculating all game.
 */
public class TicTacToeBestStrategyBot extends TicTacToeBot {
    /**
     * Default constructor, constructs width * height field.
     * @param width of the field.
     * @param height of the field.
     */
    public TicTacToeBestStrategyBot(int width, int height) {
        super(width, height);
    }

    @Override
    public Mode getMode() {
        return Mode.BESTSTRATEGYBOT;
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

    /**
     * Returns best turn to do (if win then win, if draw then draw, if loose then any).
     * Supposed that at least one free cell exists otherwise exception will be thrown.
     * @return best cell coordinates: (x, y).
     * @throws IncorrectTurnException if algorithm tried to do incorrect turn (it is fatal error).
     * @throws GameIsEndedException if algorithm tried to do turn but game is over (it is fatal error).
     */
    @NotNull
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

    /**
     * Returns winning position to go. If there is no such position then null will be returned.
     * @return winning cell coordinates: (x, y).
     * @throws IncorrectTurnException if algorithm tried to do incorrect turn (it is fatal error).
     * @throws GameIsEndedException if algorithm tried to do turn but game is over (it is fatal error).
     */
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

    /**
     * Returns not loosing (draw or win) position to go. If there is no such position then null will be returned.
     * @return not loosing cell coordinates: (x, y).
     * @throws IncorrectTurnException if algorithm tried to do incorrect turn (it is fatal error).
     * @throws GameIsEndedException if algorithm tried to do turn but game is over (it is fatal error).
     */
    @Nullable
    private Pair<Integer, Integer> getNotLoosingTurnPosition() throws IncorrectTurnException, GameIsEndedException {
        for (Pair<Integer, Integer> freeCellCoord : getFreeCellsCoords()) {
            if (isNotLoosing(freeCellCoord.getKey(), freeCellCoord.getValue())) {
                return freeCellCoord;
            }
        }
        return null;
    }

    /**
     * Checks if turn in this cell will have winning strategy after.
     * @param x cell coordinate to do turn.
     * @param y cell coordinate to do turn.
     * @return true if this turn is winning otherwise false.
     * @throws IncorrectTurnException if algorithm tried to do incorrect turn (it is fatal error).
     * @throws GameIsEndedException if algorithm tried to do turn but game is over (it is fatal error).
     */
    private boolean isWinning(int x, int y) throws IncorrectTurnException, GameIsEndedException {
        return checkWinRecursively(x, y);
    }

    /**
     * Checks recursively if moving here will be win strategy. Processes all possible turns.
     * @param x cell coordinate to do turn.
     * @param y cell coordinate to do turn.
     * @return true if this turn is winning otherwise false.
     * @throws IncorrectTurnException if algorithm tried to do incorrect turn (it is fatal error).
     * @throws GameIsEndedException if algorithm tried to do turn but game is over (it is fatal error).
     */
    private boolean checkWinRecursively(int x, int y) throws IncorrectTurnException, GameIsEndedException {
        if (isEnded()) {
            return false;
        }
        doTurn(x, y);

        for (Pair<Integer, Integer> freeCellCoord : getFreeCellsCoords()) {
            if (isEnded()) {
                GameState endGameState = getGameState();
                undoLastTurn();
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
            undoLastTurn();
            if (!existsWinningTurn) {
                undoLastTurn();
                return false;
            }
        }
        undoLastTurn();
        return true;
    }

    /**
     * Checks if turn in this cell will not loosing strategy after.
     * @param x cell coordinate to do turn.
     * @param y cell coordinate to do turn.
     * @return true if this turn is not loosing otherwise false.
     * @throws IncorrectTurnException if algorithm tried to do incorrect turn (it is fatal error).
     * @throws GameIsEndedException if algorithm tried to do turn but game is over (it is fatal error).
     */
    private boolean isNotLoosing(int x, int y) throws IncorrectTurnException, GameIsEndedException {
        return checkNotLoosingRecursively(x, y);
    }

    /**
     * Checks recursively if moving here will be not loosing strategy. Processes all possible turns.
     * @param x cell coordinate to do turn.
     * @param y cell coordinate to do turn.
     * @return true if this turn is not loosing otherwise false.
     * @throws IncorrectTurnException if algorithm tried to do incorrect turn (it is fatal error).
     * @throws GameIsEndedException if algorithm tried to do turn but game is over (it is fatal error).
     */
    private boolean checkNotLoosingRecursively(int x, int y) throws IncorrectTurnException, GameIsEndedException {
        if (isEnded()) {
            return getGameState() == GameState.DRAW;
        }
        doTurn(x, y);

        for (Pair<Integer, Integer> freeCellCoord : getFreeCellsCoords()) {
            if (isEnded()) {
                undoLastTurn();
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
            undoLastTurn();

            if (!existsNotLoosingTurn) {
                undoLastTurn();
                return false;
            }
        }
        undoLastTurn();
        return true;
    }
}
