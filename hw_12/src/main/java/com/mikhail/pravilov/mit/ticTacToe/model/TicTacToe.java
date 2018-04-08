package com.mikhail.pravilov.mit.ticTacToe.model;

import com.mikhail.pravilov.mit.ticTacToe.model.TicTacToeStatistic.Mode;
import javafx.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToe.GameState.*;
import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToe.TypeOfCell.DAGGER;
import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToe.TypeOfCell.FREE;
import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToe.TypeOfCell.ZERO;

/**
 * Abstract class for base logic of TicTacToe game.
 */
abstract public class TicTacToe {
    /**
     * Game field.
     */
    @NotNull
    private TypeOfCell[][] field;
    /**
     * Field width.
     */
    private int width;
    /**
     * Field height.
     */
    private int height;
    /**
     * Who moves now: zeros or daggers.
     */
    private boolean isZeroTurn = true;
    /**
     * Game state: runs, draw, someone wins.
     */
    @NotNull
    private GameState gameState = RUNNING;
    /**
     * Stack of made turns.
     */
    @NotNull
    private ArrayList<Pair<Integer, Integer>> turns = new ArrayList<>();

    /**
     * Constructs TicTacToe game with width * height field.
     * @param width of the field.
     * @param height of the field.
     */
    TicTacToe(int width, int height) {
        this.width = width;
        this.height = height;
        field = new TypeOfCell[width][height];

        for (TypeOfCell[] row : field) {
            Arrays.fill(row, FREE);
        }
    }

    /**
     * Returns type of game(mode): hot seat, with random bot, with best strategy bot.
     * @return mode of the current game.
     */
    abstract public Mode getMode();

    /**
     * Processes user turn in (x, y) corresponding as it should be: with bot turn or not or something else.
     * @param x cell coordinate in field.
     * @param y cell coordinate in field.
     * @throws IncorrectTurnException if turn in (x, y) cannot be performed.
     * @throws GameIsEndedException if game is over.
     */
    abstract public void processUserTurn(int x, int y) throws IncorrectTurnException, GameIsEndedException;

    /**
     * Returns all free cells in the field as pairs: x and y coordinates.
     * @return ArrayList of free cells as pairs.
     */
    @NotNull
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

    /**
     * Throws exception if turn in (x, y) is invalid (not free or game is over and no turns available).
     * @param x cell coordinate in field.
     * @param y cell coordinate in field.
     * @throws IncorrectTurnException if cell is not free.
     * @throws GameIsEndedException if game is over.
     */
    private void throwIfNotCorrectTurn(int x, int y) throws IncorrectTurnException, GameIsEndedException {
        if (field[x][y] != FREE) {
            throw new IncorrectTurnException("Cell is not free");
        }
        if (isEnded()) {
            throw new GameIsEndedException("Game is over!");
        }
    }

    /**
     * Performs a turn of a player. Checks if turn is correct, if yes,
     * then cell becomes not free (dagger or zero, depending on isZeroTurn), then updates state of the game.
     * @param x cell coordinate in field.
     * @param y cell coordinate in field.
     * @throws IncorrectTurnException if cell is not free.
     * @throws GameIsEndedException if game is over.
     */
    void doTurn(int x, int y) throws IncorrectTurnException, GameIsEndedException {
        GameState prevGameState = gameState;
        throwIfNotCorrectTurn(x, y);
        setCell(x, y, isZeroTurn ? TypeOfCell.ZERO : TypeOfCell.DAGGER);
        turns.add(new Pair<>(x, y));
        isZeroTurn = !isZeroTurn;
        updateStatisticInformation(prevGameState, false);
    }

    /**
     * Performs an "unturn" of last turn - cancels last turn of a player.
     * If no turns were made function does not do anything.
     */
    void undoLastTurn() {
        if (turns.isEmpty()) {
            return;
        }
        GameState prevGameState = gameState;
        Pair<Integer, Integer> lastTurn = turns.get(turns.size() - 1);
        setCell(lastTurn.getKey(), lastTurn.getValue(), TypeOfCell.FREE);
        turns.remove(turns.size() - 1);
        isZeroTurn = !isZeroTurn;
        updateStatisticInformation(prevGameState, true);
    }

    /**
     * Updates statistic information. Considers if unturn was made and updates statistic corresponding to unturn.
     * @param prevGameState game state before making turn or unturn.
     * @param isUndoTurn true if undo of turn was made.
     */
    private void updateStatisticInformation(@NotNull GameState prevGameState, boolean isUndoTurn) {
        GameState before = isUndoTurn ? gameState : prevGameState;
        GameState after = isUndoTurn ? prevGameState : gameState;

        if (before == RUNNING && after != RUNNING) {
            switch (after) {
                case ZERO_WON:
                    TicTacToeStatistic.getInstance().addZeroWin(isUndoTurn ? -1 : 1, getMode());
                    break;
                case DAGGER_WON:
                    TicTacToeStatistic.getInstance().addDaggerWin(isUndoTurn ? -1 : 1, getMode());
                    break;
                case DRAW:
                    TicTacToeStatistic.getInstance().addDraw(isUndoTurn ? -1 : 1, getMode());
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown game state");
            }
        }
    }

    /**
     * Checks if someone wins now or draw or game is still running. Doesn't bother about unturn or turn was made.
     */
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

    /**
     * Checks if game is over.
     * @return true if game is over otherwise false.
     */
    public boolean isEnded() {
        return gameState != RUNNING;
    }

    /**
     * Sets given value to the given cell.
     * @param x coordinate of the cell.
     * @param y coordinate of the cell.
     * @param typeOfCell type of cell to be set.
     */
    private void setCell(int x, int y, @NotNull TypeOfCell typeOfCell) {
        field[x][y] = typeOfCell;
        updateWinningInformation();
    }

    /**
     * Checks if cells (x, y), (x, y + 1), (x, y + 2) are winning (all are zeros or daggers).
     * @param x coordinate of top cell.
     * @param y coordinate of top cell.
     * @return true if this three cells are winning otherwise false.
     */
    @Contract(pure = true)
    private boolean isWinDown(int x, int y) {
        return field[x][y] != FREE && y + 2 < height && field[x][y] == field[x][y + 1] && field[x][y + 1] == field[x][y + 2];
    }

    /**
     * Checks if cells (x, y), (x + 1, y), (x + 2, y) are winning (all are zeros or daggers).
     * @param x coordinate of left cell.
     * @param y coordinate of left cell.
     * @return true if this three cells are winning otherwise false.
     */
    @Contract(pure = true)
    private boolean isWinRight(int x, int y) {
        return field[x][y] != FREE && x + 2 < width && field[x][y] == field[x + 1][y] && field[x + 1][y] == field[x + 2][y];
    }

    /**
     * Checks if cells (x, y), (x + 1, y + 1), (x + 2, y + 2) are winning (all are zeros or daggers).
     * @param x coordinate of top-left cell.
     * @param y coordinate of top-left cell.
     * @return true if this three cells are winning otherwise false.
     */
    @Contract(pure = true)
    private boolean isWinRightDown(int x, int y) {
        return field[x][y] != FREE && x + 2 < width && y + 2 < height &&
                field[x][y] == field[x + 1][y + 1] && field[x + 1][y + 1] == field[x + 2][y + 2];
    }

    /**
     * Checks if cells (x, y), (x - 1, y + 1), (x - 2, y + 2) are winning (all are zeros or daggers).
     * @param x coordinate of top-right cell.
     * @param y coordinate of top-right cell.
     * @return true if this three cells are winning otherwise false.
     */
    @Contract(pure = true)
    private boolean isWinLeftDown(int x, int y) {
        return field[x][y] != FREE && x - 2 >= 0 && y + 2 < height &&
                field[x][y] == field[x - 1][y + 1] && field[x - 1][y + 1] == field[x - 2][y + 2];
    }

    /**
     * Returns width of the field.
     * @return field width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns height of the field.
     * @return field height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns state of the game (running, draw, someone wins).
     * @return game state.
     */
    @NotNull
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Returns type of cell in field (zero, dagger or free).
     * @param x coordinate of cell.
     * @param y coordinate of cell.
     * @return type of cell ({@link TypeOfCell}).
     */
    @NotNull
    public TypeOfCell getTypeOfCell(int x, int y) {
        return field[x][y];
    }

    /**
     * Describes available states of game: running, draw, dagger won, zero won.
     */
    public enum GameState {
        RUNNING, DRAW, DAGGER_WON, ZERO_WON
    }

    /**
     * Describes types of cells: free (no one made turn here), zero (zero made turn here), dagger (as well).
     */
    public enum TypeOfCell {
        FREE, DAGGER, ZERO
    }

    /**
     * Describes exception that should be thrown if someone outside tried to make turn in occupied cell.
     */
    public class IncorrectTurnException extends Exception {
        IncorrectTurnException(String message) {
            super(message);
        }
    }

    /**
     * Describes exception that should be thrown if someone outside tried to make turn but game has already finished.
     */
    public class GameIsEndedException extends Exception {
        GameIsEndedException(String message) {
            super(message);
        }
    }
}
