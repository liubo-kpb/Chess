package MainClasses;

import static MainClasses.CreateFrame.allFigure;
import static MainClasses.CreateFrame.button;
import static MainClasses.InGame.playableColor;
import static MainClasses.InGame.playedFigure;
import static MainClasses.KingCheckCalculator.firstTurn;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Rule {

    //used to store the figure's position
    public Position position;

    public boolean canTake;
    public static boolean nextTurn = false;
    public static Position afterSkipPosition;

    public int colDiff;
    public int rowDiff;

    public boolean isValidMove(int i, int j, InGame button) throws KingNotFoundException {
        canTake = taking(button);
        if (!canTake) {
            return false;
        }

        String name = playedFigure.getName().substring(6, playedFigure.getName().length());
        if (!name.equals("pawn")) {
            nextTurn = false; //the now playing figure is not a pawn so you lose the ability to make en passant
        }
        switch (name) {
            case "king":
                KingCheckCalculator kcc = new KingCheckCalculator();
                casting(i, j, kcc);
                if (toMoveKing(i, j) && kcc.isSafePosition(new Position(i, j))) {
                    return true;
                }
                return false;
            case "queen":
                return toMoveQueen(i, j);
            case "rook":
                return toMoveRook(i, j);
            case "knight":
                return toMoveKnight(i, j);
            case "bishop":
                return toMoveBishop(i, j);
            case "pawn":
                return toMovePawn(i, j, button);
        }
        return false;
    }

    //checks if there is a figure between the played figure and the given go-to command
    public boolean isFigureBetween(int i, int j) throws NullPointerException {
        position = playedFigure.getPosition();
        byte move = 0;
        int r = position.getRow();
        int c = position.getCol();
        int rowDiff = Math.abs(i - r);
        int colDiff = Math.abs(j - c);

        if (rowDiff == colDiff) {
            move = 1; //diagonal move
        } else if (rowDiff != 0 && colDiff == 0) {
            move = 2; //vertical move
        } else if (rowDiff == 0 && colDiff != 0) {
            move = 3; //horizontal move
        }
        if (move == 1) {
            return diagonalCheck(i, j, r, c);
        }
        if (r > i) {			//if the number of the position
            int temp = r;		// is higher than i or j, it switches them
            r = i;
            i = temp;
        }
        if (c > j) {
            int temp = c;
            c = j;
            j = temp;
        }
        switch (move) {
            case 2: //vertical
                ++r;
                while (r < i) {
                    if (button[r][c].getIcon() != null) {
                        return true;
                    }
                    ++r;
                }
                break;
            case 3: //horizontal
                ++c;
                while (c < j) {
                    if (button[r][c].getIcon() != null) {
                        return true;
                    }
                    ++c;
                }
        }
        return false;
    }

    public boolean diagonalCheck(int i, int j, int r, int c) {
        if (i < r && j < c) {
            --r;
            --c;
            while (i < r) {
                if (button[r][c].getIcon() != null) {
                    return true;
                }
                --r;
                --c;
            }
        } else if (i < r && j > c) {
            --r;
            ++c;
            while (i < r) {
                if (button[r][c].getIcon() != null) {
                    return true;
                }
                --r;
                ++c;
            }
        } else if (i > r && j > c) {
            ++r;
            ++c;
            while (i > r) {
                if (button[r][c].getIcon() != null) {
                    return true;
                }
                ++r;
                ++c;
            }
        } else if (i > r && j < c) {
            ++r;
            --c;
            while (i < r) {
                if (button[r][c].getIcon() != null) {
                    return true;
                }
                ++r;
                --c;
            }
        }
        return false;
    }
    
    public boolean taking(InGame button) {
        char figureColor = playedFigure.getIdentity().charAt(0);

        if (button.getIcon() != null && figureColor == button.getActionCommand().charAt(0)) {
            return false;
        }
        return true;
    }
    //changes the in turn color

    public void changePlayerColor() {
        if (playedFigure.getColor().equals(Color.WHITE)) {
            playableColor = Color.BLACK;
        } else if (playedFigure.getColor().equals(Color.BLACK)) {
            playableColor = Color.WHITE;
        }
    }

    //cheching if you can move the according figure
    public void casting(int i, int j, KingCheckCalculator kcc) throws KingNotFoundException {
        int m = 0;
        if (playedFigure.getColor().equals(Color.WHITE)) {
            m = 16;
        }
        Figure rook = null;
        if (j == 2) {
            rook = allFigure[m];
        } else if (j == 6) {
            rook = allFigure[m + 7];
        }
        if ((i == 0 || i == 7) && ((j == 2 && !rook.getHasMoved() && kcc.isSafePosition(new Position(i, j + 1)) && !isFigureBetween(i, j - 1))
                || (j == 6 && !rook.getHasMoved() && kcc.isSafePosition(new Position(i, j - 1))) && !isFigureBetween(i, j + 1))
                && !playedFigure.getHasMoved() && kcc.isSafePosition(new Position(i, j))) {
            //king
            button[i][playedFigure.getPosition().getCol()].setIcon(null);
            button[i][playedFigure.getPosition().getCol()].setActionCommand(null);
            button[i][j].setActionCommand(playedFigure.getIdentity());
            button[i][j].setIcon(resizeIcon(playedFigure.getSymbol()));
            playedFigure.setPosition(i, j);
            //rook
            button[i][rook.getPosition().getCol()].setIcon(null);
            button[i][rook.getPosition().getCol()].setActionCommand(null);
            switch (j) {
                case 2:
                    button[i][j + 1].setActionCommand(rook.getIdentity());
                    button[i][j + 1].setIcon(resizeIcon(rook.getSymbol()));
                    for (int n = 0; n < allFigure.length; n += 7) {
                        if (allFigure[n].getName().endsWith("pawn")) {
                            n = 24;
                        }
                        if (allFigure[n].equals(rook)) {
                            allFigure[n].setPosition(i, j + 1);
                        }
                    }
                    break;
                case 6:
                    button[i][j - 1].setActionCommand(rook.getIdentity());
                    button[i][j - 1].setIcon(resizeIcon(rook.getSymbol()));
                    for (int n = 0; n < allFigure.length; n += 7) {
                        if (allFigure[n].getName().endsWith("pawn")) {
                            n = 24;
                        }
                        if (allFigure[n].equals(rook)) {
                            allFigure[n].setPosition(i, j - 1);
                        }
                    }
            }
            changePlayerColor();
        }
    }

    public boolean toMoveKing(int i, int j) {
        position = playedFigure.getPosition();

        rowDiff = Math.abs(i - position.getRow());
        colDiff = Math.abs(j - position.getCol());

        if ((rowDiff == 1 && colDiff == 0)
                || (rowDiff == 0 && colDiff == 1)
                || (rowDiff == colDiff && rowDiff == 1)) {
            return true;
        }
        return false;
    }

    public boolean toMoveQueen(int i, int j) {
        position = playedFigure.getPosition();

        rowDiff = Math.abs(i - position.getRow());
        colDiff = Math.abs(j - position.getCol());

        if ((rowDiff == colDiff && rowDiff != 0)
                || (rowDiff != 0 && colDiff == 0)
                || (colDiff != 0 && rowDiff == 0)) {
            return true;
        }
        return false;
    }

    public boolean toMoveRook(int i, int j) {
        position = playedFigure.getPosition();

        rowDiff = Math.abs(i - position.getRow());
        colDiff = Math.abs(j - position.getCol());

        if ((rowDiff != 0 && colDiff == 0)
                || (rowDiff == 0 && colDiff != 0)) {
            return true;
        }
        return false;
    }

    public boolean toMoveKnight(int i, int j) {
        position = playedFigure.getPosition();

        rowDiff = Math.abs(i - position.getRow());
        colDiff = Math.abs(j - position.getCol());

        if ((rowDiff == 2 && colDiff == 1)
                || (rowDiff == 1 && colDiff == 2)) {
            firstTurn = false;
            return true;
        }
        return false;
    }

    public boolean toMoveBishop(int i, int j) {
        position = playedFigure.getPosition();

        rowDiff = Math.abs(i - position.getRow());
        colDiff = Math.abs(j - position.getCol());

        if (rowDiff == colDiff && rowDiff != 0) {
            return true;
        }
        return false;
    }

    public boolean toMovePawn(int i, int j, InGame button) {
        position = playedFigure.getPosition();
        Position startPosition = new Position(position.getStartingRow(), position.getStartingCol());
        rowDiff = i - position.getRow();
        colDiff = j - position.getCol();

        //white pawn
        if (rowDiff == -1 && colDiff == 0 && button.getIcon() == null && playedFigure.getColor().equals(Color.WHITE)) {
            nextTurn = false;
            firstTurn = false;
            return true;
        }
        if ((rowDiff == -2 && colDiff == 0 && position.equals(startPosition)) && button.getIcon() == null && playedFigure.getColor().equals(Color.WHITE)) {
            playedFigure.setSkiped(true);
            nextTurn = true;
            afterSkipPosition = position.add(-2, 0);
            firstTurn = false;
            return true;
        }
        //black pawn
        if (rowDiff == 1 && colDiff == 0 && button.getIcon() == null && playedFigure.getColor().equals(Color.BLACK)) {
            nextTurn = false;
            firstTurn = false;
            return true;
        }
        if ((rowDiff == 2 && colDiff == 0 && position.equals(startPosition)) && button.getIcon() == null && playedFigure.getColor().equals(Color.BLACK)) {
            playedFigure.setSkiped(true);
            nextTurn = true;
            afterSkipPosition = position.add(2, 0);
            firstTurn = false;
            return true;
        }
        //this is for the diagonal taking of the pawn
        //line 256 - black pawn
        //line 257 - white pawn
        if ((rowDiff == -1 && Math.abs(rowDiff) == Math.abs(colDiff) && canTake && button.getIcon() != null && playedFigure.getColor().equals(Color.WHITE))
                || (rowDiff == 1 && rowDiff == Math.abs(colDiff) && canTake && button.getIcon() != null && playedFigure.getColor().equals(Color.BLACK))) {
            nextTurn = false;
            return true;
        }

        //en passant
        if (!nextTurn) {
            return false;
        }
        //its the same if as in the upper one, save the need for the check for nextTurn and the positioning (is the 
        //player attacking the position behind the skiping pawn
        //white pawn
        if (nextTurn && (new Position(i, j)).equals(afterSkipPosition.add(-1, 0))
                && ((rowDiff == -1 && Math.abs(rowDiff) == Math.abs(colDiff) && canTake && button.getIcon() == null && playedFigure.getColor().equals(Color.WHITE)))) {
            nextTurn = false;
            CreateFrame.button[i + 1][j].setIcon(null);
            CreateFrame.button[i + 1][j].setActionCommand(null);
            figureAt(new Position(i, j)).setTaken(true);
            return true;
        }
        //black pawn
        if (nextTurn && (new Position(i, j)).equals(afterSkipPosition.add(1, 0))
                && (rowDiff == 1 && rowDiff == Math.abs(colDiff) && canTake && button.getIcon() == null && playedFigure.getColor().equals(Color.BLACK))) {
            nextTurn = false;
            CreateFrame.button[i - 1][j].setIcon(null);
            CreateFrame.button[i - 1][j].setActionCommand(null);
            figureAt(new Position(i, j)).setTaken(true);
            return true;
        }
        return false;
    }

    public ImageIcon resizeIcon(ImageIcon icon) {
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(button[0][0].getSize().height * 5/6, button[0][0].getSize().width * 5/6, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);
        return icon;
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

    public static int whiteQueenNum = 2;//used only in below method
    public static int blackQueenNum = 2;

    public void makeQueen(int i, int j) {
        if (playedFigure.getName().endsWith("pawn") && playedFigure.getColor().equals(Color.WHITE) && i == 0) {
            for (int q = allFigure.length / 2; q < allFigure.length; ++q) {
                if (playedFigure.getIdentity().equals(allFigure[q].getIdentity())) {
                    allFigure[q] = new Figure("White queen", "W queen" + whiteQueenNum, new Position(i, j), new ImageIcon("Photos\\White figures\\QueenW.png"), Color.WHITE);
                    playedFigure = allFigure[q];
                    button[i][j].setIcon(resizeIcon(allFigure[q].getSymbol()));
                    button[i][j].setActionCommand(allFigure[q].getIdentity());
                    ++whiteQueenNum;
                    return;
                }
            }
        }
        if (playedFigure.getName().endsWith("pawn") && playedFigure.getColor().equals(Color.BLACK) && i == 7) {
            for (int q = 0; q < allFigure.length / 2; ++q) {
                if (playedFigure.getIdentity().equals(allFigure[q].getIdentity())) {
                    allFigure[q] = new Figure("Black queen", "B queen" + blackQueenNum, new Position(i, j), new ImageIcon("Photos\\Black figures\\QueenB.png"), Color.BLACK);
                    playedFigure = allFigure[q];
                    button[i][j].setIcon(resizeIcon(allFigure[q].getSymbol()));
                    button[i][j].setActionCommand(allFigure[q].getIdentity());
                    ++blackQueenNum;
                    return;
                }
            }
        }
    }
}
