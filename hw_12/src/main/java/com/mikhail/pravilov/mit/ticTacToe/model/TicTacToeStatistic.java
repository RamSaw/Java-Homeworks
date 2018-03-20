package com.mikhail.pravilov.mit.ticTacToe.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class that describes statistic of tictactoe game.
 */
public class TicTacToeStatistic {
    /**
     * Class is singleton. This is instance of it.
     */
    @NotNull
    private final static TicTacToeStatistic INSTANCE = new TicTacToeStatistic();
    /**
     * Name of the field in json where dagger wins statistic is stored.
     */
    @NotNull
    private final static String DAGGER_WINS_FIELD_NAME = "DAGGER_WINS";
    /**
     * Name of the field in json where zero wins statistic is stored.
     */
    @NotNull
    private final static String ZERO_WINS_FIELD_NAME = "ZERO_WINS";
    /**
     * Name of the field in json where draw statistic is stored.
     */
    @NotNull
    private final static String DRAWS_FIELD_NAME = "DRAWS";

    /**
     * Number of dagger wins accounting wins in new session of application.
     */
    private int numberOfDaggerWins = 0;
    /**
     * Number of zero wins accounting wins in new session of application.
     */
    private int numberOfZeroWins = 0;
    /**
     * Number of draws accounting wins in new session of application.
     */
    private int numberOfDraws = 0;
    /**
     * Name of statistic file.
     */
    @NotNull
    private String nameOfStatisticFile = "statistic.json";
    /**
     * Path to statistic json file where it is stored.
     */
    @NotNull
    private Path pathToStatisticFile = Paths.get(nameOfStatisticFile);
    /**
     * Exception that can occur during reading statistic file.
     */
    @Nullable
    private IOException readingStatisticFileException;
    /**
     * Exception that can occur during parsing statistic json file.
     */
    @Nullable
    private JSONException parsingJsonException;

    /**
     * Returns instance of this class.
     * @return instance of this class.
     */
    @NotNull
    public static TicTacToeStatistic getInstance() {
        return INSTANCE;
    }

    /**
     * Private constructor for singleton aims. Loads previous statistic.
     */
    private TicTacToeStatistic() {
        loadPreviousStatistic();
    }

    /**
     * Checks that no exceptions occurred during restoring previous statistic.
     * @return true if previous statistic is loaded otherwise false.
     */
    public boolean isPreviousStatisticLoaded() {
        return readingStatisticFileException == null && parsingJsonException == null;
    }

    /**
     * Loads previous statistic from file stated by pathToStatisticFile.
     */
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

    /**
     * Adds n to numberOfDaggerWins variable.
     * @param n number to add.
     */
    void addDaggerWin(int n) {
        numberOfDaggerWins += n;
    }

    /**
     * Adds n to numberOfZeroWins variable.
     * @param n number to add.
     */
    void addZeroWin(int n) {
        numberOfZeroWins += n;
    }

    /**
     * Adds n to numberOfDraws variable.
     * @param n number to add.
     */
    void addDraw(int n) {
        numberOfDraws += n;
    }

    /**
     * Saves statistic to file specified by pathToStatisticFile.
     * @throws JSONException if json error occurred.
     * @throws IOException if exception during writing to file occurred.
     */
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

    /**
     * Getter for numberOfDaggerWins variable.
     * @return number of dagger wins.
     */
    public int getNumberOfDaggerWins() {
        return numberOfDaggerWins;
    }

    /**
     * Getter for numberOfZeroWins variable.
     * @return number of zero wins.
     */
    public int getNumberOfZeroWins() {
        return numberOfZeroWins;
    }

    /**
     * Getter for numberOfDraws variable.
     * @return number of draws.
     */
    public int getNumberOfDraws() {
        return numberOfDraws;
    }

    /**
     * Getter for nameOfStatisticFile
     * @return name of statistic file.
     */
    @NotNull
    public String getNameOfStatisticFile() {
        return nameOfStatisticFile;
    }
}
