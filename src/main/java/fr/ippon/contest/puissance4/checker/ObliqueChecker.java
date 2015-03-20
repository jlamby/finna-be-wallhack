package fr.ippon.contest.puissance4.checker;

import java.util.ArrayList;
import java.util.List;

import fr.ippon.contest.puissance4.GameConstants;
import fr.ippon.contest.puissance4.Puissance4;

/**
 * @author jlamby
 *
 */
public class ObliqueChecker extends BaseChecker {

    public ObliqueChecker(Puissance4 game) {
        super(game);
    }

    @Override
    public boolean check(int startLine, int startRow) {
        boolean status = false;

        status |= checkObliqueUpperLeftToLowerRight(startLine, startRow);
        status |= checkObliqueUpperRightToLowerLeft(startLine, startRow);

        return status;
    }

    boolean checkObliqueUpperLeftToLowerRight(int startLine, int startRow) {
        List<Character> slice = new ArrayList<>();

        int minLineIndex = startLine;
        int minRowIndex = startRow;

        if (startLine > startRow) {
            minLineIndex -= startRow;
            minRowIndex -= startRow;
        }
        else {
            minLineIndex -= startLine;
            minRowIndex -= startRow;
        }

        int currentLineIndex = minLineIndex;
        int currentRowIndex = minRowIndex;

        while (currentLineIndex < GameConstants.MAX_LINES && currentRowIndex < GameConstants.MAX_ROWS) {
            slice.add(game.getOccupant(currentLineIndex,currentRowIndex));

            currentLineIndex++;
            currentRowIndex++;
        }

        return checkSlice(slice);
    }

    boolean checkObliqueUpperRightToLowerLeft(int startLine, int startRow) {
        List<Character> slice = new ArrayList<>();

        int minLineIndex = startLine;
        int maxRowIndex = startRow;

        maxRowIndex = startRow + startLine;
        maxRowIndex = (maxRowIndex > GameConstants.MAX_ROWS - 1) ? GameConstants.MAX_ROWS - 1 : maxRowIndex;
        minLineIndex -= (maxRowIndex - startRow);

        int currentLineIndex = minLineIndex;
        int currentRowIndex = maxRowIndex;

        while (currentLineIndex < GameConstants.MAX_LINES && currentRowIndex >= 0) {
            slice.add(game.getOccupant(currentLineIndex,currentRowIndex));

            currentLineIndex++;
            currentRowIndex--;
        }

        return checkSlice(slice);
    }

}
