package fr.ippon.contest.puissance4;


/**
 * @author jlamby
 *
 */
public enum Player {

    YELLOW(GameConstants.YELLOW_CELL) {
        @Override
        Player switchPlayer() {
            return RED;
        }
    },
    RED(GameConstants.RED_CELL) {
        @Override
        Player switchPlayer() {
            return YELLOW;
        }
    };

    private Player(char value) {
        this.value = value;
    }

    abstract Player switchPlayer();

    public static Player valueFromChar(char value) {
        return (value == 'J') ? YELLOW : RED;
    }

    public final char value;

}
