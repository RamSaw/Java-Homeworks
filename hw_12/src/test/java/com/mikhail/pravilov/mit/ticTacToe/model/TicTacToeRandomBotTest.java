package com.mikhail.pravilov.mit.ticTacToe.model;

import org.junit.Test;

import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToe.TypeOfCell.DAGGER;
import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToe.TypeOfCell.ZERO;
import static org.junit.Assert.*;

public class TicTacToeRandomBotTest {
    @Test
    public void doBotTurn() throws Exception {
        TicTacToeRandomBot game = new TicTacToeRandomBot(3, 3);
        int numberOfFreeCells = 9;
        assertEquals(numberOfFreeCells, game.getFreeCellsCoords().size());
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (game.isEnded()) {
                    return;
                }
                game.doBotTurn();
                assertEquals(--numberOfFreeCells, game.getFreeCellsCoords().size());
            }
        }
    }
}