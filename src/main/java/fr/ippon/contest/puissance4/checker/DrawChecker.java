package fr.ippon.contest.puissance4.checker;

import fr.ippon.contest.puissance4.GameConstants;
import fr.ippon.contest.puissance4.Puissance4;

/**
 * @author jlamby
 *
 */
public class DrawChecker extends BaseChecker {

    public DrawChecker(Puissance4 game) {
        super(game);
    }

    @Override
    public boolean check(int startLine, int startRow) {
        for (int rowIndex = 0; rowIndex < GameConstants.MAX_ROWS; rowIndex++) {
            char cellContent = game.getOccupant(0, rowIndex);

            if (cellContent == GameConstants.EMPTY_CELL) {
                return false;
            }
        }

        return true;
    }

}
