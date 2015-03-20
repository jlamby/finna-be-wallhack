package fr.ippon.contest.puissance4;

import java.util.Arrays;
import java.util.Random;

import fr.ippon.contest.puissance4.checker.Checker;
import fr.ippon.contest.puissance4.checker.DrawChecker;
import fr.ippon.contest.puissance4.checker.HorizontalChecker;
import fr.ippon.contest.puissance4.checker.ObliqueChecker;
import fr.ippon.contest.puissance4.checker.VerticalChecker;

/**
 * @author jlamby
 *
 */
public class Puissance4Impl implements Puissance4 {

    private static final Random RANDOM_GENERATOR  = new Random();

    private Checker             drawChecker       = new DrawChecker(this);
    private Checker             verticalChecker   = new VerticalChecker(this);
    private Checker             horizontalChecker = new HorizontalChecker(this);
    private Checker             obliqueChecker    = new ObliqueChecker(this);

    private EtatJeu             gameState;

    private char[][]            grid              = new char[GameConstants.MAX_LINES][GameConstants.MAX_ROWS];
    private Player              currentPlayer;

    public Puissance4Impl() {
        nouveauJeu();
    }

    @Override
    public void nouveauJeu() {
        for (int line = 0; line < GameConstants.MAX_LINES; line++) {
            char[] newRow = new char[GameConstants.MAX_ROWS];
            Arrays.fill(newRow, GameConstants.EMPTY_CELL);

            grid[line] = newRow;
        }

        gameState = EtatJeu.EN_COURS;
        currentPlayer = retrieveRandomStartingPlayer();
    }

    @Override
    public void chargerJeu(char[][] grid, char startingPlayer) {
        validateGrid(grid);
        validatePlayer(startingPlayer);

        this.grid = grid;
        this.currentPlayer = retriveLastPlayer(startingPlayer);

        checkGameStatusAfterLoading();
    }

    @Override
    public void jouer(int row) {
        validateNumberBetween(row, 0, GameConstants.MAX_ROWS);

        int firstAvailableLine = firstAvailableLine(row);
        grid[firstAvailableLine][row] = currentPlayer.value;

        checkGameStatus(firstAvailableLine, row);
    }

    int firstAvailableLine(int row) {
        for (int line = GameConstants.MAX_LINES - 1; line >= 0; line--) {
            char currentValue = grid[line][row];

            if (currentValue == GameConstants.EMPTY_CELL) {
                return line;
            }
        }

        throw new IllegalStateException("Row is already full");
    }

    @Override
    public EtatJeu getEtatJeu() {
        return gameState;
    }

    @Override
    public char getTour() {
        return currentPlayer.value;
    }

    @Override
    public char getOccupant(int line, int row) {
        validateNumberBetween(line, 0, GameConstants.MAX_LINES);
        validateNumberBetween(row, 0, GameConstants.MAX_ROWS);

        return grid[line][row];
    }

    Player retrieveRandomStartingPlayer() {
        return RANDOM_GENERATOR.nextBoolean() ? Player.YELLOW : Player.RED;
    }

    Player retriveLastPlayer(char previousPlayerAsChar) {
        Player previousPlayer = Player.valueFromChar(previousPlayerAsChar);

        if (previousPlayer == Player.YELLOW) {
            return Player.RED;
        }

        return Player.YELLOW;
    }

    void switchPlayer() {
        currentPlayer = currentPlayer.switchPlayer();
    }

    void validateGrid(char[][] grid) {
        validateNumber(grid.length, GameConstants.MAX_LINES);
        validateNumber(grid[0].length, GameConstants.MAX_ROWS);
    }

    void validateNumber(int value, int reference) {
        if (value != reference) {
            throw new IllegalArgumentException("Invalid value. It must be equal to " + reference + ", was equal to "
                    + value);
        }
    }

    void validateNumberBetween(int value, int min, int max) {
        if ((value < min) || (value >= max)) {
            throw new IllegalArgumentException("Invalid value. It must be include in : " + min + " <= value <= "
                    + (max - 1) + ", was equal to " + value);
        }
    }

    void validatePlayer(char value) {
        if (Player.valueFromChar(value) == null) {
            throw new IllegalArgumentException("Invalid value.");
        }
    }

    void checkGameStatusAfterLoading() {
        boolean globalStatus = false;

        globalStatus |= checkHorizontal(3, 3);

        for (int rowIndex = 0; rowIndex < GameConstants.MAX_ROWS; rowIndex++) {
            globalStatus |= checkVertical(3, rowIndex);
        }

        for (int lineIndex = 0; lineIndex < GameConstants.MAX_LINES; lineIndex++) {
            globalStatus |= checkOblique(lineIndex, 3);
        }

        updateGameState(globalStatus);
    }

    void checkGameStatus(int startLine, int startRow) {
        boolean globalStatus = false;

        globalStatus |= checkHorizontal(startLine, startRow);
        globalStatus |= checkVertical(startLine, startRow);
        globalStatus |= checkOblique(startLine, startRow);

        updateGameState(globalStatus);
    }

    void updateGameState(boolean isWon) {
        if (isWon) {
            if (getTour() == Player.YELLOW.value) {
                gameState = EtatJeu.JAUNE_GAGNE;
            }
            else {
                gameState = EtatJeu.ROUGE_GAGNE;
            }
        } else if (checkDraw()) {
            gameState = EtatJeu.MATCH_NUL;
        } else {
            gameState = EtatJeu.EN_COURS;
            switchPlayer();
        }
    }

    boolean checkDraw() {
        return drawChecker.check(0, 0);
    }

    boolean checkVertical(int startLine, int startRow) {
        return verticalChecker.check(startLine, startRow);
    }

    boolean checkHorizontal(int startLine, int startRow) {
        return horizontalChecker.check(startLine, startRow);
    }

    boolean checkOblique(int startLine, int startRow) {
        return obliqueChecker.check(startLine, startRow);
    }

}
