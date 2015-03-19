package fr.ippon.contest.puissance4;


/**
 * @author jlamby
 *
 */
public class CellStatus {

    public static final CellStatus EMPTY = new CellStatus('-');

    private CellStatus(char value) {
        this.value = value;
    }

    private CellStatus(Player player) {
        this.value = player.value;
    }

    public static CellStatus buildCell(char value) {
        if (value == EMPTY.value) {
            return EMPTY;
        }

        return buildNonEmptyCell(value);
    }

    public static CellStatus buildNonEmptyCell(Player player) {
        return new CellStatus(player);
    }

    public static CellStatus buildNonEmptyCell(char value) {
        return buildNonEmptyCell(Player.valueFromChar(value));
    }

    public final char value;

}
