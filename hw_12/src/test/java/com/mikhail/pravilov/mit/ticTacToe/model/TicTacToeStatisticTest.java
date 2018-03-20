package com.mikhail.pravilov.mit.ticTacToe.model;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class TicTacToeStatisticTest {
    @Test
    public void getInstance() throws Exception {
        assertNotNull(TicTacToeStatistic.getInstance());
    }

    @Test
    public void isPreviousStatisticLoaded() throws Exception {
        File statisticFile = new File(TicTacToeStatistic.getInstance().getNameOfStatisticFile());
        if (statisticFile.exists() && !statisticFile.isDirectory()) {
            assertTrue(TicTacToeStatistic.getInstance().isPreviousStatisticLoaded());
        } else {
            assertFalse(TicTacToeStatistic.getInstance().isPreviousStatisticLoaded());
        }
    }

    @Test
    public void addDaggerWin() throws Exception {
        int before = TicTacToeStatistic.getInstance().getNumberOfDaggerWins();
        TicTacToeStatistic.getInstance().addDaggerWin(10);
        assertEquals(before + 10, TicTacToeStatistic.getInstance().getNumberOfDaggerWins());
        TicTacToeStatistic.getInstance().addDaggerWin(-10);
        assertEquals(before, TicTacToeStatistic.getInstance().getNumberOfDaggerWins());
    }

    @Test
    public void addZeroWin() throws Exception {
        int before = TicTacToeStatistic.getInstance().getNumberOfZeroWins();
        TicTacToeStatistic.getInstance().addZeroWin(10);
        assertEquals(before + 10, TicTacToeStatistic.getInstance().getNumberOfZeroWins());
        TicTacToeStatistic.getInstance().addZeroWin(-10);
        assertEquals(before, TicTacToeStatistic.getInstance().getNumberOfZeroWins());
    }

    @Test
    public void addDraw() throws Exception {
        int before = TicTacToeStatistic.getInstance().getNumberOfDraws();
        TicTacToeStatistic.getInstance().addDraw(10);
        assertEquals(before + 10, TicTacToeStatistic.getInstance().getNumberOfDraws());
        TicTacToeStatistic.getInstance().addDraw(-10);
        assertEquals(before, TicTacToeStatistic.getInstance().getNumberOfDraws());
    }

    @Test
    public void save() throws Exception {
        TicTacToeStatistic.getInstance().save();
        assertTrue(new File(TicTacToeStatistic.getInstance().getNameOfStatisticFile()).exists());
    }
}