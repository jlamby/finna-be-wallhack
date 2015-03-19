package fr.ippon.contest.puissance4.checker;

import java.util.List;

import fr.ippon.contest.puissance4.Puissance4;

/**
 * @author jlamby
 *
 */
public abstract class BaseChecker implements Checker {

    public final Puissance4 game;

    public BaseChecker(Puissance4 game) {
        this.game = game;
    }

    boolean checkSlice(List<Character> slice) {
        int playerContiguousCell = 0;

        for (int i = 0; i < slice.size() && playerContiguousCell != 4; i++) {
            Character cellContent = slice.get(i);

            if (cellContent == game.getTour()) {
                playerContiguousCell++;
            } else {
                playerContiguousCell = 0;
            }
        }

        return playerContiguousCell == 4 ? true : false;
    }
}
