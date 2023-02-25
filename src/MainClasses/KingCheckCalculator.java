package MainClasses;

import static MainClasses.CreateFrame.allFigure;
import static MainClasses.CreateFrame.button;
import static MainClasses.InGame.playableColor;
import static MainClasses.InGame.playedFigure;
import java.awt.Color;

public class KingCheckCalculator {

    public Figure king;
    public Rule rule = new Rule();

    public Figure[] attackingFigure = new Figure[16];
    public static Figure attacker;

    public static boolean firstTurn = true;
    public static boolean kingIsCheck = false;

    public int rowDiff;
    public int colDiff;
    public Position position;

    public boolean isKingCheck() throws KingNotFoundException, NullPointerException {
        king = findKing();
        if (king == null) {
            throw new KingNotFoundException();
        }
        attacker = playedFigure;
        int row = king.getPosition().getRow();
        int col = king.getPosition().getCol();

        //if the just now played figure is threatening the king this method will
        //tell the program that the king is check
        boolean clearWay = rule.isFigureBetween(row, col);
        boolean threat = rule.isValidMove(row, col, button[row][col]);
        if (!clearWay && threat) {
            kingIsCheck = true;
            return true;
        }
        return false;
    }

    public boolean hasSafePosition() throws KingNotFoundException {
        king = findKing();
        if (king == null) {
            throw new KingNotFoundException();
        }
        setAttackingFigure();
        Position[] position = king.getPosition().getSurroundingPositions();
        for (int i = 0; i < position.length; ++i) {
            for (int j = 0; j < attackingFigure.length; ++j) {
                if (attackingFigure[j] == null) {
                    break;
                }
                for (int p = 0; p < attackingFigure[j].threateningPositionsLength(); ++p) {
                    if (position[i].equals(attackingFigure[j].getThreateningPosition()[p])
                            && position[i] != king.getPosition()
                            && isSafePosition(position[i])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean defendOptions(int option, int i, int j) throws KingNotFoundException {
        if (firstTurn || attacker == null) {//I needed to make this because I used to have NPEs
            return true;
        }
        king = findKing();
        if (king == null) {
            throw new KingNotFoundException();
        }
        int p = 0;
        if (king.getColor().equals(Color.WHITE)) {
            p = 16;
        }
        Position kingPosition = king.getPosition();
        Position attackerPosition = attacker.getPosition();
        Position[] positionBetween = kingPosition.between(attackerPosition);   //the max length of path that can be 
        //in attack is the length of the table
        //minus the position the figure is standing on
        boolean result = false;
        switch (option) {
            case 1:
                //is there a figure that can block the attacking path
                if (!kingIsCheck) {
                    return true;
                }
                for (int m = p; m < p + 16; ++m) {
                    Position[] possibleMoves = allFigure[m].getThreateningPosition();
                    for (int q = 0; q < positionBetween.length; ++q) {
                        for (int n = 0; n < possibleMoves.length; ++n) {
                            if (positionBetween[q].equals(possibleMoves[n])) {
                                kingIsCheck = false;
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2:
                //if the king isn't still check after the move the method returns true
                if (positionBetween == null) {
                    kingIsCheck = false;
                    return true;
                }
                result = isKingSafe(i, j);
                if (!result) {
                    return false;
                }
                if (new Position(i, j).equals(attackerPosition)) {
                    kingIsCheck = false;
                    return true;
                }
                for (int q = 0; q < positionBetween.length; ++q) {
                    if (new Position(i, j).equals(positionBetween[q])) {
                        kingIsCheck = false;
                        return true;
                    }
                }
        }
        return result;
    }

    public boolean isKingSafe(int i, int j) {
        int r = king.getPosition().getRow();
        int c = king.getPosition().getCol();
        setAttackingFigure();
        for (int q = 0; q < attackingFigure.length; ++q) {
            if (attackingFigure[q] == null) {
                break;
            }
            for (int n = 0; n < attackingFigure[q].threateningPositionsLength(); ++n) {
                if (attackingFigure[q].getThreateningPosition()[n].equals(playedFigure.getPosition())
                        && !attackingFigure[q].getPosition().equals(new Position(i, j))
                        && attackingFigure[q].goTo(r, c)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isSafePosition(Position position) throws KingNotFoundException {
        king = findKing();
        if (king == null) {
            throw new KingNotFoundException();
        }
        Position[] surrondrPosition = king.getPosition().getSurroundingPositions();
        if (attackingFigure[0] == null) {
            setAttackingFigure();
        }
        if (attackingFigure[0] == null) {
            return true;
        }
        for (int p = 0; p < attackingFigure.length; ++p) {
            if (attackingFigure[p] == null) {//becaus the game could be with lesser figures than 16
                break;
            }
            for (int q = 0; q < attackingFigure[p].threateningPositionsLength(); ++q) {
                if (position.equals(attackingFigure[p].getThreateningPosition()[q])) {
                    return false;
                }
            }
        }
        kingIsCheck = false;
        return true;
    }

    public Figure figureAt(Position position) {
        Figure figure = null;
        for (int i = 0; i < allFigure.length; ++i) {
            if (position.equals(allFigure[i].getPosition())) {
                figure = allFigure[i];
            }
        }
        return figure;
    }

    public Figure findKing() {
        if (playableColor.equals(Color.WHITE)) {
            //white king
            return allFigure[28];
        } else if (playableColor.equals(Color.BLACK)) {
            //black king
            return allFigure[4];
        }
        return null;
    }

    public void setAttackingFigure() {
        int n = 0;
        if (king.getColor().equals(Color.BLACK)) {
            n = 16;
        }
        int startFrom = n;
        for (int i = 0; i < attackingFigure.length; ++i) {
            if (n > startFrom + 15) {
                break;
            }
            if (allFigure[n].getTaken()) {
                --i;
            } else {
                attackingFigure[i] = allFigure[n];
            }
            ++n;
        }
    }
}
