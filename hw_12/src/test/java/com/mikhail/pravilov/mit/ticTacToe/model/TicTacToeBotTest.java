package com.mikhail.pravilov.mit.ticTacToe.model;

import javafx.util.Pair;
import org.junit.Test;

import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToe.GameState.DAGGER_WON;
import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToe.TypeOfCell.ZERO;
import static org.junit.Assert.*;

public class TicTacToeBotTest {
    @Test
    public void processUserTurnOfBestStrategyBot() throws Exception {
        TicTacToeBestStrategyBot game = new TicTacToeBestStrategyBot(3, 3);
        game.processUserTurn(0, 0);
        assertEquals(ZERO, game.getTypeOfCell(0,0));
        game.processUserTurn(2, 0);
        assertEquals(ZERO, game.getTypeOfCell(0,0));
        game.processUserTurn(0, 2);
        assertEquals(ZERO, game.getTypeOfCell(0,0));
        if (game.isEnded()) {
            assertEquals(DAGGER_WON, game.getGameState());
            return;
        }
        game.processUserTurn(2, 2);
        assertEquals(ZERO, game.getTypeOfCell(0,0));
        assertEquals(DAGGER_WON, game.getGameState());
    }

    @Test
    public void processUserTurnOfRandomBot() throws Exception {
        TicTacToeRandomBot game = new TicTacToeRandomBot(3, 3);
        int numberOfFreeCells = 9;
        while (!game.isEnded()) {
            assertEquals(numberOfFreeCells, game.getFreeCellsCoords().size());
            Pair<Integer, Integer> freeCell = game.getFreeCellsCoords().get(0);
            game.processUserTurn(freeCell.getKey(), freeCell.getValue());
            numberOfFreeCells -= 2;
        }
    }
}