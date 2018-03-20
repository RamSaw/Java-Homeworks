package com.mikhail.pravilov.mit.ticTacToe.model;

import javafx.util.Pair;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToe.GameState.*;
import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToe.TypeOfCell.DAGGER;
import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToe.TypeOfCell.FREE;
import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToe.TypeOfCell.ZERO;

abstract public class TicTacToe {
    private TypeOfCell[][] field;
    private int width;
    private int height;
    private boolean isZeroTurn = true;
    private GameState gameState = RUNNING;

    TicTacToe(int width, int height) {
        this.width = width;
        this.height = height;
        field = new TypeOfCell[width][height];

        for (TypeOfCell[] row : field) {
            Arrays.fill(row, FREE);
        }
    }

    abstract public void processUserTurn(int x, int y) throws IncorrectTurnException, GameIsEndedException;

    ArrayList<Pair<Integer, Integer>> getFreeCellsCoords() {
        ArrayList<Pair<Integer, Integer>> freeCellsCoords = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (field[x][y] == FREE) {
                    freeCellsCoords.add(new Pair<>(x, y));
                }
            }
        }
        return freeCellsCoords;
    }

    private void throwIfNotCorrectTurn(int x, int y) throws IncorrectTurnException, GameIsEndedException {
        if (field[x][y] != FREE) {
            throw new IncorrectTurnException("Cell is not free");
        }
        if (isEnded()) {
            throw new GameIsEndedException("Game is over!");
        }
    }

    private void throwIfNotCorrectUnTurn(int x, int y) throws IncorrectTurnException {
        if (field[x][y] == FREE) {
            throw new IncorrectTurnException("Cell is free");
        }
    }

    void doTurn(int x, int y) throws IncorrectTurnException, GameIsEndedException {
        GameState prevGameState = gameState;
        throwIfNotCorrectTurn(x, y);
        setCell(x, y, isZeroTurn ? TypeOfCell.ZERO : TypeOfCell.DAGGER);
        isZeroTurn = !isZeroTurn;
        updateStatisticInformation(prevGameState, false);
    }

    void undoTurn(int x, int y) throws IncorrectTurnException {
        GameState prevGameState = gameState;
        throwIfNotCorrectUnTurn(x, y);
        setCell(x, y, TypeOfCell.FREE);
        isZeroTurn = !isZeroTurn;
        updateStatisticInformation(prevGameState, true);
    }

    private void updateStatisticInformation(GameState prevGameState, boolean isUnTurn) {
        GameState before = isUnTurn ? gameState : prevGameState;
        GameState after = isUnTurn ? prevGameState : gameState;

        if (before == RUNNING && after != RUNNING) {
            switch (after) {
                case ZERO_WON:
                    TicTacToeStatistic.getInstance().addZeroWin(isUnTurn ? -1 : 1);
                    break;
                case DAGGER_WON:
                    TicTacToeStatistic.getInstance().addDaggerWin(isUnTurn ? -1 : 1);
                    break;
                case DRAW:
                    TicTacToeStatistic.getInstance().addDraw(isUnTurn ? -1 : 1);
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown game state");
            }
        }
    }

    private void updateWinningInformation() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Check if this cell is beginning cell of win sequence
                if (isWinDown(x, y) || isWinRight(x, y) || isWinRightDown(x, y) || isWinLeftDown(x, y)) {
                    if (field[x][y] == ZERO) {
                        gameState = ZERO_WON;
                    }
                    else if (field[x][y] == DAGGER) {
                        gameState = DAGGER_WON;
                    }
                    return;
                }
            }
        }
        if (getFreeCellsCoords().size() == 0) {
            gameState = DRAW;
        }
        else {
            gameState = RUNNING;
        }
    }

    public boolean isEnded() {
        return gameState != RUNNING;
    }

    private void setCell(int x, int y, TypeOfCell typeOfCell) {
        field[x][y] = typeOfCell;
        updateWinningInformation();
    }

    @Contract(pure = true)
    private boolean isWinDown(int x, int y) {
        return field[x][y] != FREE && y + 2 < height && field[x][y] == field[x][y + 1] && field[x][y + 1] == field[x][y + 2];
    }

    @Contract(pure = true)
    private boolean isWinRight(int x, int y) {
        return field[x][y] != FREE && x + 2 < width && field[x][y] == field[x + 1][y] && field[x + 1][y] == field[x + 2][y];
    }

    @Contract(pure = true)
    private boolean isWinRightDown(int x, int y) {
        return field[x][y] != FREE && x + 2 < width && y + 2 < height && field[x][y] == field[x + 1][y + 1] && field[x + 1][y + 1] == field[x + 2][y + 2];
    }

    @Contract(pure = true)
    private boolean isWinLeftDown(int x, int y) {
        return field[x][y] != FREE && x - 2 >= 0 && y + 2 < height && field[x][y] == field[x - 1][y + 1] && field[x - 1][y + 1] == field[x - 2][y + 2];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GameState getGameState() {
        return gameState;
    }

    public TypeOfCell getTypeOfCell(int x, int y) {
        return field[x][y];
    }

    public enum GameState {
        RUNNING, DRAW, DAGGER_WON, ZERO_WON
    }

    public enum TypeOfCell {
        FREE, DAGGER, ZERO
    }

    public class IncorrectTurnException extends Exception {
        IncorrectTurnException(String message) {
            super(message);
        }
    }

    public class GameIsEndedException extends Exception {
        GameIsEndedException(String message) {
            super(message);
        }
    }
}
