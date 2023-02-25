package MainClasses;

public class Position {

    public final int startingRow;
    public final int startingCol;
    public int row;
    public int col;

    public Position(int row, int col) {
        this.startingRow = row;
        this.startingCol = col;
        this.row = row;
        this.col = col;
    }

    //gives the positions between this.obj and the second obj "pointB"
    public Position[] between(Position pointB) {
        int row = pointB.getRow();
        int col = pointB.getCol();
        int rowDiff = Math.abs(this.row - row);
        int colDiff = Math.abs(this.col - col);
        byte option = 0;
        Position[] positionBetween = null;
        
        if (rowDiff == colDiff) {
            positionBetween = new Position[colDiff];
            option = 1; //diagonal
        } else if (rowDiff != 0 && colDiff == 0) {
            positionBetween = new Position[rowDiff];
            option = 3; //vertical
        } else if (rowDiff == 0 && colDiff != 0) {
            positionBetween = new Position[colDiff];
            option = 2; //horizontal
        }
        
        int r = 0;
        int c = 0;
        int p = 0;
        switch (option) {
            case 1://diagonal
                if (this.row > row && this.col > col) {
                    r = row;
                    while (r < this.row) {
                        positionBetween[p] = new Position (r, r);
                        ++p;
                        ++r;
                    }
                } else if (this.row > row && this.col < col) {
                    r = row;
                    c = this.col;
                    while (r < this.row && c < col) {
                        positionBetween[p] = new Position(r, c);
                        ++r;
                        ++c;
                        ++p;
                    }
                } else if (this. row < row && this.col < col) {
                    r = this.row;
                    c = this.col;
                    while (r < row && c < col) {
                        positionBetween[p] = new Position(r, c);
                        ++r;
                        ++c;
                        ++p;
                    }
                } else if (this.row < row && this.col > col) {
                    r = this.row;
                    c = col;
                    while (r < row && c < this.col) {
                        positionBetween[p] = new Position(r, c);
                        ++r;
                        ++c;
                        ++p;
                    }
                }
                break;
            case 2://horizontal
                r = this.row;
                if (this.col < col) {
                    c = this.col;
                    while (c < col) {
                        positionBetween[p] = new Position(r, c);
                        ++c;
                        ++p;
                    }
                } else if (this.col > col) {
                    c = col;
                    while (c < this.col) {
                        positionBetween[p] = new Position(r,c);
                        ++c;
                        ++p;
                    }
                }
                break;
            case 3://vertical
                c = this.col;
                if (this.row < row) {
                    r = this.row;
                    while (r < row) {
                        positionBetween[p] = new Position(r, c);
                        ++r;
                        ++p;
                    }
                } else if (this.row > row) {
                    r = row;
                    while (r < this.row) {
                        positionBetween[p] = new Position(r, c);
                        ++r;
                        ++p;
                    }
                }
        }
        return positionBetween;
    }
    
    public int[] getCoordinates() {
        int[] coord = {row, col};
        return coord;
    }

    public Position add(int row, int col) {
        //as oposed to operator +
        row = this.row + row;
        col = this.col + col;
        return new Position(row, col);
    }

    public Position subtract(int row, int col) {
        //we prefer positive numbers
        if (this.row < row) {
            int temp = row;
            row = this.row;
            this.row = temp;
        }
        if (this.col < col) {
            int temp = col;
            col = this.col;
            this.col = temp;
        }
        return new Position(this.row - row, this.col - col);
    }

    public byte getSurroundingCase() {
        //we need to know how the King is positioned
        byte option = 0;
        if (col == 0) {
            option = 1;
        }
        if (col == 7) {
            option = 2;
        }
        if (row == 0) {
            option = 3;
        }
        if (row == 7) {
            option = 4;
        }
        if (row == 0 && col == 0) {
            option = 5;
        }
        if (row == 0 && col == 7) {
            option = 6;
        }
        if (row == 7 && col == 0) {
            option = 7;
        }
        if (row == 7 && col == 7) {
            option = 8;
        }
        if (row > 0 && row < 7 && col > 0 && col < 7) {
            option = 9;
        }
        return option;
    }

    public Position[] getSurroundingPositions() {
        byte option = getSurroundingCase();
        //here we get the surrounding (able to move to) positions for the King
        Position[] newPosition = null;
        switch (option) {
            case 1: {
                //col == 0
                Position[] setPosition = {add(-1, 0), add(1, 0),
                    add(-1, 1), add(0, 1), add(1, 1)};
                newPosition = setPosition;
                break;
            }
            case 2: {
                // col == 7
                Position[] setPosition = {add(-1, 0), add(1, 0),
                    add(-1, -1), add(0, -1), add(1, -1)};
                newPosition = setPosition;
                break;
            }
            case 3: {
                //row == 0
                Position[] setPosition = {add(0, -1), add(0, 1),
                    add(1, -1), add(1, 0), add(1, 1)};
                newPosition = setPosition;
                break;
            }
            case 4: {
                //row == 7
                Position[] setPosition = {add(-1, -1), add(-1, 0), add(-1, 1),
                    add(0, 1), add(0, 1)};
                newPosition = setPosition;
                break;
            }
            case 5: {
                //row == 0 && col == 0
                Position[] setPosition = {add(1, 0), add(1, 1), add(0, 1)};
                newPosition = setPosition;
                break;
            }
            case 6: {
                //row == 0 && col == 7
                Position[] setPosition = {add(1, 0), add(1, -1), add(0, 1)};
                newPosition = setPosition;
                break;
            }
            case 7: {
                //row == 7 && col ==0
                Position[] setPosition = {add(-1, 0), add(-1, 1), add(0, 1)};
                newPosition = setPosition;
                break;
            }
            case 8: {
                //row == 7 && col == 7
                Position[] setPosition = {add(0, -1), add(-1, -1), add(-1, 0)};
                newPosition = setPosition;
                break;
            }
            case 9: {
                //row(0;7) && col(0;7) this is a coordinations belonging
                Position[] setPosition = {add(-1, -1), add(-1, 0), add(-1, 1),
                    add(0, -1), add(0, 1),
                    add(1, 1), add(1, 0), add(1, 1)};
                newPosition = setPosition;
                break;
            }
        }
        return newPosition;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj == this || !(obj instanceof Position)) {
            return false;
        }
        Position otherPosition = (Position) obj;
        if (otherPosition.row != this.row) {
            return false;
        }
        if (otherPosition.col != this.col) {
            return false;
        }
        return true;
    }

    public String toString() {
        return (row + 1) + "\t" + (col + 1);
    }

    //getters and setters
    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getStartingRow() {
        return startingRow;
    }

    public int getStartingCol() {
        return startingCol;
    }
}
