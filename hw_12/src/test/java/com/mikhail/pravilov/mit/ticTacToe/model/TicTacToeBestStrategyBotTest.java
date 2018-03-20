package com.mikhail.pravilov.mit.ticTacToe.model;

import org.junit.Test;

import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToe.GameState.DAGGER_WON;
import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToe.GameState.DRAW;
import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToe.TypeOfCell.DAGGER;
import static org.junit.Assert.*;

public class TicTacToeBestStrategyBotTest {
    @Test
    public void doBotTurnBotWins() throws Exception {
        TicTacToeBestStrategyBot game = new TicTacToeBestStrategyBot(3, 3);
        /*
        Field
        xoo
        xxX
        oFo
         */
        game.doTurn(1, 0);
        game.doTurn(0, 0);
        game.doTurn(2, 0);
        game.doTurn(1, 1);
        game.doTurn(0, 2);
        game.doTurn(0, 1);
        game.doTurn(2, 2);
        game.doBotTurn();
        assertTrue(game.isEnded());
        assertEquals(DAGGER_WON, game.getGameState());
        assertEquals(DAGGER, game.getTypeOfCell(2, 1));
    }

    @Test
    public void doBotTurnDraw() throws Exception {
        TicTacToeBestStrategyBot game = new TicTacToeBestStrategyBot(3, 3);
        /*
        Field
        xoo
        FxX
        oxo
         */
        game.doTurn(1, 0);
        game.doTurn(0, 0);
        game.doTurn(2, 0);
        game.doTurn(1, 1);
        game.doTurn(0, 2);
        game.doTurn(1, 2);
        game.doTurn(2, 2);
        game.doBotTurn();
        assertEquals(DAGGER, game.getTypeOfCell(2, 1));
        game.doTurn(0, 1);
        assertTrue(game.isEnded());
        assertEquals(DRAW, game.getGameState());
    }
}