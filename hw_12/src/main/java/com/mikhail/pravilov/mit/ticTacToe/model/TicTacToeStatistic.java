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
import java.util.HashMap;

/**
 * Class that describes statistic of tictactoe game.
 */
public class TicTacToeStatistic {
    /**
     * Name of the field in json where dagger wins statistic is stored.
     */
    @NotNull
    private final static HashMap<Mode, String> DAGGER_WINS_FIELD_NAME =
            new HashMap<Mode, String>() {{
                put(Mode.HOTSEAT, "DAGGER_WINS_HOTSEAT");
                put(Mode.RANDOMBOT, "DAGGER_WINS_RANDOMBOT");
                put(Mode.BESTSTRATEGYBOT, "DAGGER_WINS_BESTSTRATEGYBOT");
            }};
    /**
     * Name of the field in json where zero wins statistic is stored.
     */
    @NotNull
    private final static HashMap<Mode, String> ZERO_WINS_FIELD_NAME =
            new HashMap<Mode, String>() {{
                put(Mode.HOTSEAT, "ZERO_WINS_HOTSEAT");
                put(Mode.RANDOMBOT, "ZERO_WINS_RANDOMBOT");
                put(Mode.BESTSTRATEGYBOT, "ZERO_WINS_BESTSTRATEGYBOT");
            }};
    /**
     * Name of the field in json where draw statistic is stored.
     */
    @NotNull
    private final static HashMap<Mode, String> DRAWS_FIELD_NAME =
            new HashMap<Mode, String>() {{
                put(Mode.HOTSEAT, "DRAWS_HOTSEAT");
                put(Mode.RANDOMBOT, "DRAWS_RANDOMBOT");
                put(Mode.BESTSTRATEGYBOT, "DRAWS_BESTSTRATEGYBOT");
            }};
    /**
     * Number of dagger wins accounting wins in new session of application.
     */
    private HashMap<Mode, Integer> numberOfDaggerWins =
            new HashMap<Mode, Integer>() {{
                put(Mode.HOTSEAT, 0);
                put(Mode.RANDOMBOT, 0);
                put(Mode.BESTSTRATEGYBOT, 0);
            }};
    /**
     * Number of zero wins accounting wins in new session of application.
     */
    private HashMap<Mode, Integer> numberOfZeroWins =
            new HashMap<Mode, Integer>() {{
                put(Mode.HOTSEAT, 0);
                put(Mode.RANDOMBOT, 0);
                put(Mode.BESTSTRATEGYBOT, 0);
            }};
    /**
     * Number of draws accounting wins in new session of application.
     */
    private HashMap<Mode, Integer> numberOfDraws =
            new HashMap<Mode, Integer>() {{
                put(Mode.HOTSEAT, 0);
                put(Mode.RANDOMBOT, 0);
                put(Mode.BESTSTRATEGYBOT, 0);
            }};
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
     * Class is singleton. This is instance of it.
     */
    @NotNull
    private final static TicTacToeStatistic INSTANCE = new TicTacToeStatistic();

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
            for (Mode mode : Mode.values()) {
                numberOfDaggerWins.put(mode, jsonStatistic.getInt(DAGGER_WINS_FIELD_NAME.get(mode)));
                numberOfZeroWins.put(mode, jsonStatistic.getInt(ZERO_WINS_FIELD_NAME.get(mode)));
                numberOfDraws.put(mode, jsonStatistic.getInt(DRAWS_FIELD_NAME.get(mode)));
            }
        } catch (JSONException e) {
            parsingJsonException = e;
        } catch (IOException e) {
            readingStatisticFileException = e;
        }
    }

    /**
     * Adds n to numberOfDaggerWins variable.
     * @param n number to add.
     * @param mode mode where to add statistic.
     */
    void addDaggerWin(int n, Mode mode) {
        numberOfDaggerWins.put(mode, numberOfDaggerWins.get(mode) + n);
    }

    /**
     * Adds n to numberOfZeroWins variable.
     * @param n number to add.
     * @param mode mode where to add statistic.
     */
    void addZeroWin(int n, Mode mode) {
        numberOfZeroWins.put(mode, numberOfZeroWins.get(mode) + n);
    }

    /**
     * Adds n to numberOfDraws variable.
     * @param n number to add.
     * @param mode mode where to add statistic.
     */
    void addDraw(int n, Mode mode) {
        numberOfDraws.put(mode, numberOfDraws.get(mode) + n);
    }

    /**
     * Saves statistic to file specified by pathToStatisticFile.
     * @throws JSONException if json error occurred.
     * @throws IOException if exception during writing to file occurred.
     */
    public void save() throws JSONException, IOException {
        JSONObject jsonStatistic = new JSONObject();
        for (Mode mode : Mode.values()) {
            jsonStatistic.put(DAGGER_WINS_FIELD_NAME.get(mode), numberOfDaggerWins.get(mode));
            jsonStatistic.put(ZERO_WINS_FIELD_NAME.get(mode), numberOfZeroWins.get(mode));
            jsonStatistic.put(DRAWS_FIELD_NAME.get(mode), numberOfDraws.get(mode));
        }
        try (BufferedWriter fileWriter = Files.newBufferedWriter(pathToStatisticFile)) {
            fileWriter.write(jsonStatistic.toString());
            fileWriter.flush();
        }
    }

    /**
     * Getter for numberOfDaggerWins variable.
     * @param mode mode which statistic to return.
     * @return number of dagger wins.
     */
    public int getNumberOfDaggerWins(Mode mode) {
        return numberOfDaggerWins.get(mode);
    }

    /**
     * Getter for numberOfZeroWins variable.
     * @param mode mode which statistic to return.
     * @return number of zero wins.
     */
    public int getNumberOfZeroWins(Mode mode) {
        return numberOfZeroWins.get(mode);
    }

    /**
     * Getter for numberOfDraws variable.
     * @param mode mode which statistic to return.
     * @return number of draws.
     */
    public int getNumberOfDraws(Mode mode) {
        return numberOfDraws.get(mode);
    }

    /**
     * Getter for nameOfStatisticFile
     * @return name of statistic file.
     */
    @NotNull
    public String getNameOfStatisticFile() {
        return nameOfStatisticFile;
    }

    public enum Mode {
        HOTSEAT, RANDOMBOT, BESTSTRATEGYBOT
    }
}
