package com.mikhail.pravilov.mit.ticTacToe.model;

import org.junit.Test;

import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToe.TypeOfCell.DAGGER;
import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToe.TypeOfCell.ZERO;
import static org.junit.Assert.*;

public class TicTacToeHotSeatTest {
    @Test
    public void processUserTurn() throws Exception {
        TicTacToeHotSeat game = new TicTacToeHotSeat(3, 3);
        int numberOfFreeCells = 9;
        boolean isZeroTurn = true;
        assertEquals(numberOfFreeCells, game.getFreeCellsCoords().size());
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (game.isEnded()) {
                    return;
                }
                game.doTurn(x, y);
                assertEquals(--numberOfFreeCells, game.getFreeCellsCoords().size());
                assertEquals(isZeroTurn ? ZERO : DAGGER, game.getTypeOfCell(x, y));
                isZeroTurn = !isZeroTurn;
            }
        }

    }
}