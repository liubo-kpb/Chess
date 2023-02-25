package MainClasses;

import java.awt.Color;

import javax.swing.ImageIcon;

public class CreateFigure {

    public Figure kingW;
    public Figure queenW;
    public Figure rookW[] = new Figure[2];
    public Figure bishopW[] = new Figure[2];
    public Figure knightW[] = new Figure[2];
    public Figure pawnW[] = new Figure[8];

    public Figure kingB;
    public Figure queenB;
    public Figure rookB[] = new Figure[2];
    public Figure bishopB[] = new Figure[2];
    public Figure knightB[] = new Figure[2];
    public Figure pawnB[] = new Figure[8];

    public Figure[] allFigures;

    public CreateFigure() {
        kingW = new Figure("White king", "W king", new Position(7, 4), new ImageIcon("Photos\\White figures\\KingW.png"), Color.WHITE);
        queenW = new Figure("White queen", "W queen1", new Position(7, 3), new ImageIcon("Photos\\White figures\\QueenW.png"), Color.WHITE);
        rookW[0] = new Figure("White rook", "W rook1", new Position(7, 0), new ImageIcon("Photos\\White figures\\RookW.png"), Color.WHITE);
        rookW[1] = new Figure("White rook", "W rook2", new Position(7, 7), new ImageIcon("Photos\\White figures\\RookW.png"), Color.WHITE);
        bishopW[0] = new Figure("White bishop", "W bishop1", new Position(7, 2), new ImageIcon("Photos\\White figures\\BishopW.png"), Color.WHITE);
        bishopW[1] = new Figure("White bishop", "W bishop2", new Position(7, 5), new ImageIcon("Photos\\White figures\\BishopW.png"), Color.WHITE);
        knightW[0] = new Figure("White knight", "W knight1", new Position(7, 1), new ImageIcon("Photos\\White figures\\KnightW.png"), Color.WHITE);
        knightW[1] = new Figure("White knight", "W knight2", new Position(7, 6), new ImageIcon("Photos\\White figures\\KnightW.png"), Color.WHITE);

        kingB = new Figure("Black king", "B king", new Position(0, 4), new ImageIcon("Photos\\Black figures\\KingB.png"), Color.BLACK);
        queenB = new Figure("Black queen", "B queen1", new Position(0, 3), new ImageIcon("Photos\\Black figures\\QueenB.png"), Color.BLACK);
        rookB[0] = new Figure("Black rook", "B rook1", new Position(0, 0), new ImageIcon("Photos\\Black figures\\RookB.png"), Color.BLACK);
        rookB[1] = new Figure("Black rook", "B rook2", new Position(0, 7), new ImageIcon("Photos\\Black figures\\RookB.png"), Color.BLACK);
        bishopB[0] = new Figure("Black bishop", "B bishop1", new Position(0, 2), new ImageIcon("Photos\\Black figures\\BishopB.png"), Color.BLACK);
        bishopB[1] = new Figure("Black bishop", "B bishop2", new Position(0, 5), new ImageIcon("Photos\\Black figures\\BishopB.png"), Color.BLACK);
        knightB[0] = new Figure("Black knight", "B knight1", new Position(0, 1), new ImageIcon("Photos\\Black figures\\KnightB.png"), Color.BLACK);
        knightB[1] = new Figure("Black knight", "B knight2", new Position(0, 6), new ImageIcon("Photos\\Black figures\\KnightB.png"), Color.BLACK);

        for (int i = 0; i < pawnW.length; ++i) {
            pawnB[i] = new Figure("Black pawn", "B pawn" + (i + 1), new Position(1, i), new ImageIcon("Photos\\Black figures\\PawnB.png"), Color.BLACK);
        }
        for (int i = 0; i < pawnW.length; ++i) {
            pawnW[i] = new Figure("White pawn", "W pawn" + (1 + i), new Position(6, i), new ImageIcon("Photos\\White figures\\PawnW.png"), Color.WHITE);
        }
        
        Figure[] allFigures = {rookB[0], knightB[0], bishopB[0], queenB, kingB, bishopB[1], knightB[1], rookB[1],
            pawnB[0], pawnB[1], pawnB[2], pawnB[3], pawnB[4], pawnB[5], pawnB[6], pawnB[7],
            pawnW[0], pawnW[1], pawnW[2], pawnW[3], pawnW[4], pawnW[5], pawnW[6], pawnW[7],
            rookW[0], knightW[0], bishopW[0], queenW, kingW, bishopW[1], knightW[1], rookW[1]};
        this.allFigures = allFigures;
    }

    //getter methods for the figures
    Figure[] getAllFigures() {
        return allFigures;
    }
}
