package fr.ippon.contest.puissance4.checker;

import java.util.ArrayList;
import java.util.List;

import fr.ippon.contest.puissance4.GameConstants;
import fr.ippon.contest.puissance4.Puissance4;

/**
 * @author jlamby
 *
 */
public class VerticalChecker extends BaseChecker {

    public VerticalChecker(Puissance4 game) {
        super(game);
    }

    @Override
    public boolean check(int startLine, int startRow) {
        List<Character> slice = new ArrayList<>();

        for (int lineIndex = 0; lineIndex < GameConstants.MAX_LINES; lineIndex++) {
            slice.add(game.getOccupant(lineIndex, startRow));
        }

        return checkSlice(slice);
    }

}
