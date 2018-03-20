package com.mikhail.pravilov.mit.ticTacToe.model;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToe.GameState.*;
import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToe.TypeOfCell.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TicTacToeTest {
    private TicTacToe game;

    @Before
    public void setUp() throws Exception {
        // Simplest version of the game is HotSeat.
        game = new TicTacToeHotSeat(3, 3);
    }

    @Test
    public void getFreeCellsCoords() throws Exception {
        List<Pair<Integer, Integer>> rightAnswer = new ArrayList<>(Arrays.asList(new Pair<>(0, 0), new Pair<>(0, 1), new Pair<>(0, 2),
                new Pair<>(1, 0), new Pair<>(1, 1), new Pair<>(1, 2),
                new Pair<>(2, 0), new Pair<>(2, 1), new Pair<>(2, 2)));

        assertEquals(rightAnswer, game.getFreeCellsCoords());
        while (!game.isEnded()) {
            int elementIndex = 0;
            game.doTurn(rightAnswer.get(elementIndex).getKey(), rightAnswer.get(elementIndex).getValue());
            rightAnswer.remove(elementIndex);
            assertEquals(rightAnswer, game.getFreeCellsCoords());
        }
    }

    @Test
    public void doTurn() throws Exception {
        boolean isZeroTurn = true;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (game.isEnded()) {
                    return;
                }
                assertEquals(FREE, game.getTypeOfCell(x, y));
                game.doTurn(x, y);
                assertEquals(isZeroTurn ? ZERO : DAGGER, game.getTypeOfCell(x, y));
                isZeroTurn = !isZeroTurn;
            }
        }
    }

    @Test(expected = TicTacToe.GameIsEndedException.class)
    public void doTurnThrowsGameIsEndedException() throws Exception {
        try {
            game.doTurn(0, 0);
            game.doTurn(1, 0);
            game.doTurn(0, 1);
            game.doTurn(1, 1);
            game.doTurn(0, 2);
        }
        catch (Exception e) {
            assertFalse(e instanceof TicTacToe.GameIsEndedException);
            throw e;
        }
        game.doTurn(1, 2);
    }

    @Test(expected = TicTacToe.IncorrectTurnException.class)
    public void doTurnThrowsIncorrectTurnException() throws Exception {
        try {
            game.doTurn(0, 0);
        }
        catch (Exception e) {
            assertFalse(e instanceof TicTacToe.IncorrectTurnException);
            throw e;
        }
        game.doTurn(0, 0);
    }

    @Test
    public void undoTurn() throws Exception {
        game.doTurn(0, 0);
        assertEquals(ZERO, game.getTypeOfCell(0, 0));
        game.undoLastTurn();
        assertEquals(FREE, game.getTypeOfCell(0, 0));
        game.doTurn(0, 0);
        game.doTurn(1, 0);
        game.doTurn(0, 1);
        game.doTurn(1, 1);
        game.doTurn(0, 2);
        assertTrue(game.isEnded());
        game.undoLastTurn();
        assertFalse(game.isEnded());
        assertEquals(FREE, game.getTypeOfCell(0, 2));
        game.undoLastTurn();
        assertEquals(FREE, game.getTypeOfCell(1, 1));
    }

    @Test
    public void isEnded() throws Exception {
        assertFalse(game.isEnded());
        game.doTurn(0, 0);
        assertFalse(game.isEnded());
        game.doTurn(1, 0);
        assertFalse(game.isEnded());
        game.doTurn(0, 1);
        assertFalse(game.isEnded());
        game.doTurn(1, 1);
        assertFalse(game.isEnded());
        game.doTurn(0, 2);
        assertTrue(game.isEnded());
    }

    @Test
    public void getWidth() throws Exception {
        assertEquals(3, game.getWidth());
    }

    @Test
    public void getHeight() throws Exception {
        assertEquals(3, game.getHeight());
    }

    @Test
    public void getGameStateDraw() throws Exception {
        /*
        Field
        oxo
        oxx
        xoo
         */
        assertEquals(RUNNING, game.getGameState());
        game.doTurn(0, 0);
        assertEquals(RUNNING, game.getGameState());
        game.doTurn(1, 0);
        assertEquals(RUNNING, game.getGameState());
        game.doTurn(0, 1);
        assertEquals(RUNNING, game.getGameState());
        game.doTurn(1, 1);
        assertEquals(RUNNING, game.getGameState());
        game.doTurn(1, 2);
        assertEquals(RUNNING, game.getGameState());
        game.doTurn(0, 2);
        assertEquals(RUNNING, game.getGameState());
        game.doTurn(2, 0);
        assertEquals(RUNNING, game.getGameState());
        game.doTurn(2, 1);
        assertEquals(RUNNING, game.getGameState());
        game.doTurn(2, 2);
        assertEquals(DRAW, game.getGameState());
    }

    @Test
    public void getGameStateZeroWon() throws Exception {
        /*
        Field
        oxF
        oxF
        oFF
         */
        assertEquals(RUNNING, game.getGameState());
        game.doTurn(0, 0);
        assertEquals(RUNNING, game.getGameState());
        game.doTurn(1, 0);
        assertEquals(RUNNING, game.getGameState());
        game.doTurn(0, 1);
        assertEquals(RUNNING, game.getGameState());
        game.doTurn(1, 1);
        assertEquals(RUNNING, game.getGameState());
        game.doTurn(0, 2);
        assertEquals(ZERO_WON, game.getGameState());
    }

    @Test
    public void getGameStateDaggerWon() throws Exception {
        /*
        Field
        oxF
        oxF
        Fxo
         */
        assertEquals(RUNNING, game.getGameState());
        game.doTurn(0, 0);
        assertEquals(RUNNING, game.getGameState());
        game.doTurn(1, 0);
        assertEquals(RUNNING, game.getGameState());
        game.doTurn(0, 1);
        assertEquals(RUNNING, game.getGameState());
        game.doTurn(1, 1);
        assertEquals(RUNNING, game.getGameState());
        game.doTurn(2, 2);
        assertEquals(RUNNING, game.getGameState());
        game.doTurn(1, 2);
        assertEquals(DAGGER_WON, game.getGameState());
    }

    @Test
    public void getTypeOfCell() throws Exception {
        assertEquals(FREE, game.getTypeOfCell(0, 0));
        game.doTurn(0, 0);
        assertEquals(ZERO, game.getTypeOfCell(0, 0));
        assertEquals(FREE, game.getTypeOfCell(1, 0));
        game.doTurn(1, 0);
        assertEquals(DAGGER, game.getTypeOfCell(1, 0));
    }
}