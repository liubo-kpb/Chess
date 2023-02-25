package MainClasses;

import static MainClasses.CreateFrame.button;
import java.awt.Color;

import javax.swing.ImageIcon;

public class Figure {

    public String name;
    public String identity;
    public Position position;
    public Position[] threateningPositions = new Position[32];
    public boolean taken = false;
    public boolean skiped = false;
    public boolean hasMoved = false;
    public ImageIcon symbol;
    public Color color;

    public Figure(String name, String identity, Position position,
            ImageIcon symbol, Color color) {
        this.name = name;
        this.identity = identity;
        this.position = position;
        this.symbol = symbol;
        this.color = color;
        setThreatenedPositions();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj == this || !(obj instanceof Figure)) {
            return false;
        }
        Figure otherFigure = (Figure) obj;
        if (otherFigure.identity != this.identity) {
            return false;
        }
        return true;
    }
    
    //getters and setters
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ImageIcon getSymbol() {
        return symbol;
    }

    public void setSymbol(ImageIcon symbol) {
        this.symbol = symbol;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(int row, int col) {
        position.setRow(row);
        position.setCol(col);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSkiped(boolean skipped) {
        this.skiped = skipped;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }
    
    public boolean getTaken() {
        return taken;
    }

    public int threateningPositionsLength() {
        int length = 0;
        while (threateningPositions[length] != null) {
            ++length;
        }
        return length;
    }

    public Position[] getThreateningPosition() {
        return threateningPositions;
    }

    public boolean getHasMoved() {
     return hasMoved;   
    }
    
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean goTo(int i, int j) {
        int rowDiff = Math.abs(i - position.getRow());
        int colDiff = Math.abs(j - position.getCol());
        switch (name.substring(6, name.length())) {
            case "queen":
                if ((rowDiff == colDiff && rowDiff != 0)
                        || (rowDiff != 0 && colDiff == 0)
                        || (colDiff != 0 && rowDiff == 0)) {
                    return true;
                }
                break;
            case "rook":
                if ((rowDiff != 0 && colDiff == 0)
                        || (rowDiff == 0 && colDiff != 0)) {
                    return true;
                }
                break;
            case "bishop":
                if (rowDiff == colDiff && rowDiff != 0) {
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    //this method is more complex than the other ones and that is why I have put it here.
    public void setThreatenedPositions() {
        int[] coord = position.getCoordinates(); //getting the row and collumn
        char decider = identity.charAt(0);
        int n = 0;
        switch (name.substring(6, name.length())) {
            case "king":
                threateningPositions = position.getSurroundingPositions();
                break;
            case "queen":
                //saving the positions under the figure
                for (int i = coord[0] - 1; i >= 0; --i) {
                    if (i < 0) {
                        break;
                    }
                    if (button[i][coord[1]] == null) {
                        break;
                    }
                    if (button[i][coord[1]].getIcon() != null && decider == button[i][coord[1]].getActionCommand().charAt(0)) {
                        break;
                    } else if (button[i][coord[1]].getIcon() != null && decider != button[i][coord[1]].getActionCommand().charAt(0)) {
                        threateningPositions[n] = new Position(i, coord[1]);
                        ++n;
                        break;
                    } else {
                        threateningPositions[n] = new Position(i, coord[1]);
                    }
                    ++n;
                }
                //saving the positions above
                for (int i = coord[0] + 1; i < button.length; ++i) {
                    if (i >= button.length) {
                        break;
                    }
                    if (button[i][coord[1]] == null) {
                        break;
                    }
                    if (button[coord[0]][i].getIcon() != null && decider == button[coord[0]][i].getActionCommand().charAt(0)) {
                        break;
                    } else if (button[coord[0]][i].getIcon() != null && decider != button[coord[0]][i].getActionCommand().charAt(0)) {
                        threateningPositions[n] = new Position(coord[0], i);
                        ++n;
                        break;
                    } else {
                        threateningPositions[n] = new Position(coord[0], i);
                    }
                    ++n;
                }
                //saving the positions to the left
                for (int i = coord[1] - 1; i >= 0; --i) {
                    if (i < 0) {
                        break;
                    }
                    if (button[coord[0]][i] == null) {
                        break;
                    }
                    if (button[coord[0]][i].getIcon() != null && decider == button[coord[0]][i].getActionCommand().charAt(0)) {
                        break;
                    } else if (button[coord[0]][i].getIcon() != null && decider != button[coord[0]][i].getActionCommand().charAt(0)) {
                        threateningPositions[n] = new Position(coord[0], i);
                        ++n;
                        break;
                    } else {
                        threateningPositions[n] = new Position(coord[0], i);
                    }
                    ++n;
                }
                //saving the positions to the right
                for (int i = coord[1] + 1; i < button.length; ++i) {
                    if (i >= button.length) {
                        break;
                    }
                    if (button[coord[0]][i] == null) {
                        break;
                    }
                    if (button[coord[0]][i].getIcon() != null && decider == button[coord[0]][i].getActionCommand().charAt(0)) {
                        break;
                    } else if (button[coord[0]][i].getIcon() != null && decider != button[coord[0]][i].getActionCommand().charAt(0)) {
                        threateningPositions[n] = new Position(i, coord[1]);
                        ++n;
                        break;
                    } else {
                        threateningPositions[n] = new Position(i, coord[1]);
                    }
                    ++n;
                }
                //saving positions diagonal:
                //to topleft
                int r = coord[0] - 1;
                int c = coord[1] - 1;
                while (r < button.length && c < button.length && r >= 0 && c >= 0 && button[r][c] != null) {
                    if (button[r][c].getIcon() != null && decider == button[r][c].getActionCommand().charAt(0)) {
                        break;
                    } else if (button[r][c].getIcon() != null && decider != button[r][c].getActionCommand().charAt(0)) {
                        threateningPositions[n] = new Position(r, c);
                        ++n;
                        break;
                    } else {
                        threateningPositions[n] = new Position(r, c);
                    }
                    --r;
                    --c;
                    ++n;
                }
                //topright
                r = coord[0] - 1;
                c = coord[1] + 1;
                while (r < button.length && c < button.length && r >= 0 && c >= 0 && button[r][c] != null) {
                    if (button[r][c].getIcon() != null && decider == button[r][c].getActionCommand().charAt(0)) {
                        break;
                    } else if (button[r][c].getIcon() != null && decider != button[r][c].getActionCommand().charAt(0)) {
                        threateningPositions[n] = new Position(r, c);
                        ++n;
                        break;
                    } else {
                        threateningPositions[n] = new Position(r, c);
                    }
                    --r;
                    ++c;
                    ++n;
                }
                //bottomright
                r = coord[0] + 1;
                c = coord[1] + 1;
                while (r < button.length && c < button.length && r >= 0 && c >= 0 && button[r][c] != null) {
                    if (button[r][c].getIcon() != null && decider == button[r][c].getActionCommand().charAt(0)) {
                        break;
                    } else if (button[r][c].getIcon() != null && decider != button[r][c].getActionCommand().charAt(0)) {
                        threateningPositions[n] = new Position(r, c);
                        ++n;
                        break;
                    } else {
                        threateningPositions[n] = new Position(r, c);
                    }
                    ++r;
                    ++c;
                    ++n;
                }
                //bottomleft
                r = coord[0] + 1;
                c = coord[1] - 1;
                while (r < button.length && c < button.length && r >= 0 && c >= 0 && button[r][c] != null) {
                    if (button[r][c].getIcon() != null && decider == button[r][c].getActionCommand().charAt(0)) {
                        break;
                    } else if (button[r][c].getIcon() != null && decider != button[r][c].getActionCommand().charAt(0)) {
                        threateningPositions[n] = new Position(r, c);
                        ++n;
                        break;
                    } else {
                        threateningPositions[n] = new Position(r, c);
                    }
                    ++r;
                    --c;
                    ++n;
                }
                break;
            case "rook":
                //saving the positions under the figure
                for (int i = coord[0] - 1; i >= 0; --i) {
                    if (i < 0) {
                        break;
                    }
                    if (button[i][coord[1]] == null) {
                        break;
                    }
                    if (button[i][coord[1]].getIcon() != null && decider == button[i][coord[1]].getActionCommand().charAt(0)) {
                        break;
                    } else if (button[i][coord[1]].getIcon() != null && decider != button[i][coord[1]].getActionCommand().charAt(0)) {
                        threateningPositions[n] = new Position(i, coord[1]);
                        ++n;
                        break;
                    } else {
                        threateningPositions[n] = new Position(i, coord[1]);
                    }
                    ++n;
                }
                //saving the positions above
                for (int i = coord[0] + 1; i <= button.length; ++i) {
                    if (i >= button.length) {
                        break;
                    }
                    if (button[i][coord[1]] == null) {
                        break;
                    }
                    if (button[i][coord[1]].getIcon() != null && decider == button[i][coord[1]].getActionCommand().charAt(0)) {
                        break;
                    } else if (button[i][coord[1]].getIcon() != null && decider != button[i][coord[1]].getActionCommand().charAt(0)) {
                        threateningPositions[n] = new Position(i, coord[1]);
                        ++n;
                        break;
                    } else {
                        threateningPositions[n] = new Position(i, coord[1]);
                    }
                    ++n;
                }
                //saving the positions to the left
                for (int i = coord[1] - 1; i >= 0; --i) {
                    if (i < 0) {
                        break;
                    }
                    if (button[coord[0]][i] == null) {
                        break;
                    }
                    if (button[coord[0]][i].getIcon() != null && decider == button[coord[0]][i].getActionCommand().charAt(0)) {
                        break;
                    } else if (button[coord[0]][i].getIcon() != null && decider != button[coord[0]][i].getActionCommand().charAt(0)) {
                        threateningPositions[n] = new Position(coord[0], i);
                        ++n;
                        break;
                    } else {
                        threateningPositions[n] = new Position(coord[0], i);
                    }
                    ++n;
                }
                //saving the positions to the right
                for (int i = coord[1] + 1; i < button.length; ++i) {
                    if (i >= button.length) {
                        break;
                    }
                    if (button[coord[0]][i] == null) {
                        break;
                    }
                    if (button[coord[0]][i].getIcon() != null && decider == button[coord[0]][i].getActionCommand().charAt(0)) {
                        break;
                    } else if (button[coord[0]][i].getIcon() != null && decider != button[coord[0]][i].getActionCommand().charAt(0)) {
                        threateningPositions[n] = new Position(coord[0], i);
                        ++n;
                        break;
                    } else {
                        threateningPositions[n] = new Position(coord[0], i);
                    }
                    ++n;
                }
                break;
            case "knight":
                byte option = position.getSurroundingCase();
                if (option == 1) {
                    // col == 0
                    threateningPositions[0] = position.add(-2, 1);
                    threateningPositions[1] = position.add(-1, 2);
                    threateningPositions[2] = position.add(1, 2);
                    threateningPositions[3] = position.add(2, 1);
                    n = 3;
                } else if (option == 2) {
                    //col == 7
                    threateningPositions[0] = position.subtract(2, 1);
                    threateningPositions[1] = position.subtract(1, 2);
                    threateningPositions[2] = position.add(1, -2);
                    threateningPositions[3] = position.add(2, -1);
                    n = 3;
                } else if (option == 3) {
                    //row == 0
                    threateningPositions[0] = position.add(1, -2);
                    threateningPositions[1] = position.add(2, -1);
                    threateningPositions[2] = position.add(2, 1);
                    threateningPositions[4] = position.add(1, 2);
                    n = 3;
                } else if (option == 4) {
                    //row == 7
                    threateningPositions[0] = position.subtract(1, 2);
                    threateningPositions[1] = position.add(-1, 2);
                    threateningPositions[2] = position.subtract(2, 1);
                    threateningPositions[3] = position.add(-2, 1);
                    n = 3;
                } else if (option == 5) {
                    //row == 0 && col == 0
                    threateningPositions[0] = position.add(1, 2);
                    threateningPositions[1] = position.add(2, 1);
                    n = 1;
                } else if (option == 6) {
                    //row == 0 && col == 7
                    threateningPositions[0] = position.add(1, -2);
                    threateningPositions[1] = position.add(2, -1);
                    n = 1;
                } else if (option == 7) {
                    //row == 7 && col == 0
                    threateningPositions[0] = position.add(-1, 2);
                    threateningPositions[1] = position.add(-2, 1);
                    n = 1;
                } else if (option == 8) {
                    // row == 7 && col == 7
                    threateningPositions[0] = position.subtract(1, 2);
                    threateningPositions[1] = position.subtract(2, 1);
                    n = 1;
                } else if (option == 9) {
                    threateningPositions[0] = position.subtract(1, 2);
                    threateningPositions[1] = position.add(-1, 2);
                    threateningPositions[2] = position.subtract(2, 1);
                    threateningPositions[3] = position.add(-2, 1);
                    threateningPositions[4] = position.add(1, 2);
                    threateningPositions[5] = position.add(2, 1);
                    threateningPositions[6] = position.add(2, -1);
                    threateningPositions[7] = position.add(1, -2);
                    n = 7;
                }
                break;
            case "bishop":
                //saving positions diagonal:
                //to topleft
                r = coord[0] - 1;
                c = coord[1] - 1;
                while (r < button.length && c < button.length && r >= 0 && c >= 0 && button[r][c] != null) {
                    if (button[r][c].getIcon() != null && decider == button[r][c].getActionCommand().charAt(0)) {
                        break;
                    } else if (button[r][c].getIcon() != null && decider != button[r][c].getActionCommand().charAt(0)) {
                        threateningPositions[n] = new Position(r, c);
                        ++n;
                        break;
                    } else {
                        threateningPositions[n] = new Position(r, c);
                    }
                    --r;
                    --c;
                    ++n;
                }
                //topright
                r = coord[0] - 1;
                c = coord[1] + 1;
                while (r < button.length && c < button.length && r >= 0 && c >= 0 && button[r][c] != null) {
                    if (button[r][c].getIcon() != null && decider == button[r][c].getActionCommand().charAt(0)) {
                        break;
                    } else if (button[r][c].getIcon() != null && decider != button[r][c].getActionCommand().charAt(0)) {
                        threateningPositions[n] = new Position(r, c);
                        ++n;
                        break;
                    } else {
                        threateningPositions[n] = new Position(r, c);
                    }
                    --r;
                    ++c;
                    ++n;
                }
                //bottomright
                r = coord[0] + 1;
                c = coord[1] + 1;
                while (r < button.length && c < button.length && r >= 0 && c >= 0 && button[r][c] != null) {
                    if (button[r][c].getIcon() != null && decider == button[r][c].getActionCommand().charAt(0)) {
                        break;
                    } else if (button[r][c].getIcon() != null && decider != button[r][c].getActionCommand().charAt(0)) {
                        threateningPositions[n] = new Position(r, c);
                        ++n;
                        break;
                    } else {
                        threateningPositions[n] = new Position(r, c);
                    }
                    ++r;
                    ++c;
                    ++n;
                }
                //bottomleft
                r = coord[0] + 1;
                c = coord[1] - 1;
                while (r < button.length && c < button.length && r >= 0 && c >= 0 && button[r][c] != null) {
                    if (button[r][c].getIcon() != null && decider == button[r][c].getActionCommand().charAt(0)) {
                        break;
                    } else if (button[r][c].getIcon() != null && decider != button[r][c].getActionCommand().charAt(0)) {
                        threateningPositions[n] = new Position(r, c);
                        ++n;
                        break;
                    } else {
                        threateningPositions[n] = new Position(r, c);
                    }
                    ++r;
                    --c;
                    ++n;
                }
                break;
            case "pawn":
                if (color.equals(Color.WHITE)) {
                    threateningPositions[0] = position.subtract(1, 1);
                    threateningPositions[1] = position.add(-1, 1);
                } else if (color.equals(Color.BLACK)) {
                    threateningPositions[0] = position.add(1, 1);
                    threateningPositions[1] = position.add(1, -1);
                }
                n = 1;
                break;
        }
        for (++n; n < threateningPositions.length; ++n) {//clears the old positions
            if (threateningPositions[n] != null) {
                threateningPositions[n] = null;
            } else break;
        }
    }
}
