package fr.ippon.contest.puissance4.checker;

import java.util.ArrayList;
import java.util.List;

import fr.ippon.contest.puissance4.GameConstants;
import fr.ippon.contest.puissance4.Puissance4;

/**
 * @author jlamby
 *
 */
public class HorizontalChecker extends BaseChecker {

    public HorizontalChecker(Puissance4 game) {
        super(game);
    }

    @Override
    public boolean check(int startLine, int startRow) {
        List<Character> slice = new ArrayList<>();

        for (int rowIndex = 0; rowIndex < GameConstants.MAX_ROWS; rowIndex++) {
            slice.add(game.getOccupant(startLine,rowIndex));
        }

        return checkSlice(slice);
    }

}
