package com.mikhail.pravilov.mit.ticTacToe.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TicTacToeStatistic {
    private final static TicTacToeStatistic INSTANCE = new TicTacToeStatistic();
    private final static String DAGGER_WINS_FIELD_NAME = "DAGGER_WINS";
    private final static String ZERO_WINS_FIELD_NAME = "ZERO_WINS";
    private final static String DRAWS_FIELD_NAME = "DRAWS";

    private int numberOfDaggerWins = 0;
    private int numberOfZeroWins = 0;
    private int numberOfDraws = 0;
    private Path pathToStatisticFile = null;
    private IOException readingStatisticFileException;
    private JSONException parsingJsonException;


    public static TicTacToeStatistic getInstance() {
        return INSTANCE;
    }

    private TicTacToeStatistic() {
        pathToStatisticFile = Paths.get("statistic.json");
        loadPreviousStatistic();
    }

    public boolean isPreviousStatisticLoaded() {
        return readingStatisticFileException == null && parsingJsonException == null;
    }

    private void loadPreviousStatistic() {
        try {
            JSONObject jsonStatistic = new JSONObject(String.join("\n", Files.readAllLines(pathToStatisticFile)));
            numberOfDaggerWins = jsonStatistic.getInt(DAGGER_WINS_FIELD_NAME);
            numberOfZeroWins = jsonStatistic.getInt(ZERO_WINS_FIELD_NAME);
            numberOfDraws = jsonStatistic.getInt(DRAWS_FIELD_NAME);
        } catch (JSONException e) {
            parsingJsonException = e;
        } catch (IOException e) {
            readingStatisticFileException = e;
        }
    }

    void addDaggerWin(int n) {
        numberOfDaggerWins += n;
    }

    void addZeroWin(int n) {
        numberOfZeroWins += n;
    }

    void addDraw(int n) {
        numberOfDraws += n;
    }

    public void save() throws JSONException, IOException {
        JSONObject jsonStatistic = new JSONObject();
        jsonStatistic.put(DAGGER_WINS_FIELD_NAME, numberOfDaggerWins);
        jsonStatistic.put(ZERO_WINS_FIELD_NAME, numberOfZeroWins);
        jsonStatistic.put(DRAWS_FIELD_NAME, numberOfDraws);
        try (BufferedWriter fileWriter = Files.newBufferedWriter(pathToStatisticFile)) {
            fileWriter.write(jsonStatistic.toString());
            fileWriter.flush();
        }
    }

    public int getNumberOfDaggerWins() {
        return numberOfDaggerWins;
    }

    public int getNumberOfZeroWins() {
        return numberOfZeroWins;
    }

    public int getNumberOfDraws() {
        return numberOfDraws;
    }
}
