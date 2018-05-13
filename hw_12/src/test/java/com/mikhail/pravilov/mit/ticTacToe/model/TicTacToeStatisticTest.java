package com.mikhail.pravilov.mit.ticTacToe.model;

import org.junit.Test;

import java.io.File;

import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToeStatistic.Mode.BESTSTRATEGYBOT;
import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToeStatistic.Mode.HOTSEAT;
import static com.mikhail.pravilov.mit.ticTacToe.model.TicTacToeStatistic.Mode.RANDOMBOT;
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
        int before = TicTacToeStatistic.getInstance().getNumberOfDaggerWins(HOTSEAT);
        TicTacToeStatistic.getInstance().addDaggerWin(10, HOTSEAT);
        assertEquals(before + 10, TicTacToeStatistic.getInstance().getNumberOfDaggerWins(HOTSEAT));
        TicTacToeStatistic.getInstance().addDaggerWin(-10, HOTSEAT);
        assertEquals(before, TicTacToeStatistic.getInstance().getNumberOfDaggerWins(HOTSEAT));
    }

    @Test
    public void addZeroWin() throws Exception {
        int before = TicTacToeStatistic.getInstance().getNumberOfZeroWins(RANDOMBOT);
        TicTacToeStatistic.getInstance().addZeroWin(10, RANDOMBOT);
        assertEquals(before + 10, TicTacToeStatistic.getInstance().getNumberOfZeroWins(RANDOMBOT));
        TicTacToeStatistic.getInstance().addZeroWin(-10, RANDOMBOT);
        assertEquals(before, TicTacToeStatistic.getInstance().getNumberOfZeroWins(RANDOMBOT));
    }

    @Test
    public void addDraw() throws Exception {
        int before = TicTacToeStatistic.getInstance().getNumberOfDraws(BESTSTRATEGYBOT);
        TicTacToeStatistic.getInstance().addDraw(10, BESTSTRATEGYBOT);
        assertEquals(before + 10, TicTacToeStatistic.getInstance().getNumberOfDraws(BESTSTRATEGYBOT));
        TicTacToeStatistic.getInstance().addDraw(-10, BESTSTRATEGYBOT);
        assertEquals(before, TicTacToeStatistic.getInstance().getNumberOfDraws(BESTSTRATEGYBOT));
    }

    @Test
    public void save() throws Exception {
        TicTacToeStatistic.getInstance().save();
        assertTrue(new File(TicTacToeStatistic.getInstance().getNameOfStatisticFile()).exists());
    }
}