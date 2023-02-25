package MainClasses;

import static MainClasses.CreateFrame.button;
import static MainClasses.CreateFrame.allFigure;
import static MainClasses.CreateFrame.endGame;
import static MainClasses.KingCheckCalculator.kingIsCheck;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class InGame extends JButton implements ActionListener {

    public InGame thisButton;
    public InGame oldButton;
    public InGame clickedButton;

    public Figure selectedFigure;
    public static Figure playedFigure;
    public static Color playableColor = Color.WHITE;

    public Rule rule = new Rule();

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        try {
            checkUsedButton(source);
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "NPE!", "Problems:", 0);
            ex.printStackTrace();
        } catch (TurnException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (KingNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (KingUnderThreatException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Tooltip", 0);
        }
    }

    //the methods are (sort of) in order of use
    public void checkUsedButton(Object source) throws NullPointerException, TurnException, KingNotFoundException, KingUnderThreatException {
        int i = 0;
        int j = 0;
        
        clickedButton = null;
        if (source == endGame) {
            System.exit(0);
        }
        while (i < button.length) {
             while (j < button.length) {
                if (source == button[i][j]) {
                    clickedButton = button[i][j];
                    break;
                }
                ++j;
            }
            if (clickedButton != null) {
                break;
            }
            j = 0;
            ++i;
        }
        makeMove(i, j);
    }

    public void makeMove(int i, int j) throws NullPointerException, TurnException, KingNotFoundException, KingUnderThreatException {
        byte option = 0;
        if (clickedButton.getIcon() != null && selectedFigure == null) {
            for (int n = 0; n < allFigure.length; ++n) {
                String comparison1 = allFigure[n].getIdentity();
                String comparison2 = clickedButton.getActionCommand();

                if (comparison1.equals(comparison2)) {
                    selectedFigure = allFigure[n];
                    break;
                }
            }//for
        }
        if (playedFigure != null) {
            option = 2;
        }
        if (selectedFigure != null && !selectedFigure.getColor().equals(playableColor) && playedFigure == null) {
            selectedFigure = null;
            throw new TurnException();
        } else if (selectedFigure != null && selectedFigure.getColor().equals(playableColor)) {
            option = 1;
        }
        switch (option) {
            case 1: {
                playedFigure = selectedFigure;
                oldButton = clickedButton;
                selectedFigure = null;
                break;
            }
            case 2: {
                selectedFigure = null;
                thisButton = clickedButton;
                
                boolean isKing = playedFigure.getName().endsWith("king");
                boolean isKnight = playedFigure.getName().endsWith("knight");
                if (!isKing && !isKnight && rule.isFigureBetween(i, j)) {
                    return;
                }
                
                KingCheckCalculator kcc = new KingCheckCalculator();
                if (!isKing && !kcc.defendOptions(1, 0, 0) && !kcc.defendOptions(2, i, j)) {
                    throw new KingUnderThreatException();
                }
                //if the player hasn't moved a figure the game doesn't switch turns
                if (!playFigure(i, j)) {
                    return;
                }
                rule.changePlayerColor();
                if (!isKing) {
                    kingCheckAction(kcc);
                }
                oldButton = null;
                thisButton = null;
                playedFigure = null;
                break;
            }
        }
    }
    
    public boolean playFigure(int i, int j) throws NullPointerException, KingNotFoundException {
        boolean result = rule.isValidMove(i, j, thisButton);
        if (!result) {
            return false;
        }
        if((playedFigure.getName().endsWith("king") || playedFigure.getName().endsWith("pawn") || playedFigure.getName().endsWith("rook")) || playedFigure.getName().endsWith("knight")) {
            playedFigure.setHasMoved(true);
        }
        if (thisButton.getIcon() != null) {
            figureAt(new Position(i, j)).setTaken(true);
        }
        thisButton.setIcon(resizeIcon(playedFigure.getSymbol()));
        thisButton.setActionCommand(playedFigure.getIdentity());
        oldButton.setIcon(null);
        oldButton.setActionCommand(null);
        playedFigure.setPosition(i, j);
        rule.makeQueen(i, j);
        for (int n = 0; n < allFigure.length; ++n) {
            allFigure[n].setThreatenedPositions();
        }
        if (allFigure[4].getTaken() || allFigure[28].getTaken()) {
            endGame(true, new KingCheckCalculator());
        }
        bugSolver(i, j);
        return result;
    }

    //this method will change check if one of the kings is check then do the acording actions
    //allFigure[28] - white king        allFigure[4] - black king
    public void kingCheckAction(KingCheckCalculator kcc) throws KingNotFoundException, NullPointerException {
        boolean isKingCheck = kcc.isKingCheck();
        if ((kingIsCheck && playableColor.equals(Color.WHITE)) || (isKingCheck && playableColor.equals(Color.WHITE))) {
            button[allFigure[28].getPosition().getRow()][allFigure[28].getPosition().getCol()].setBackground(Color.RED);
        } else if ((kingIsCheck && playableColor.equals(Color.BLACK)) || (isKingCheck && playableColor.equals(Color.BLACK))) {
            button[allFigure[4].getPosition().getRow()][allFigure[4].getPosition().getCol()].setBackground(Color.RED);
        } else {
            int temp = 0;
            int comparison = 8;
            for (int n = 0; n < 8; ++n) {
                for (int p = 0; p < 8; ++p) {
                    if (temp == comparison) {
                        temp++;
                        comparison += 9;
                    }
                    if (temp % 2 == 1) {
                        button[n][p].setBackground(Color.BLACK);
                    } else if (temp % 2 == 0) {
                        button[n][p].setBackground(Color.WHITE);
                    }
                    ++temp;
                }//for
            }//for
        }//else
        
            endGame(isKingCheck, kcc);
        
    }

    public void endGame(boolean isKingCheck, KingCheckCalculator kcc) throws KingNotFoundException {
        int f = 0;
        if (playableColor.equals(Color.WHITE)) {
            f = 16;
        }
        Figure[] oposingFigures = new Figure[16];
        for (int i = 0; i < oposingFigures.length; ++i) {
            oposingFigures[i] = allFigure[f];
            ++f;
        }
        for (int i = 0; i < oposingFigures.length; ++i) {
            for (int q = 0; q < oposingFigures[i].threateningPositionsLength(); ++q) {
                if (playedFigure.getPosition().equals(oposingFigures[i].getThreateningPosition()[q])) {
                    return;
                }
            }
        }
        if (isKingCheck && !kcc.hasSafePosition() && !kcc.defendOptions(1, 0, 0)) {
        JOptionPane.showMessageDialog(null, playedFigure.getName().substring(0, 5) + "s win!");
            int choice = JOptionPane.showConfirmDialog(null, "Do you want to play again?");
            CreateFrame newGame = new CreateFrame(choice);
         }
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

    public static Position pawnPosition = new Position(6, 0);
    public static Position knightPosition = new Position(7, 1);
    public void bugSolver(int i, int j) {
        //this is to isolate a problem that the figures are having. I hava no idea where it comes from
        //it's the pawn on A2 (position (6;0)) and the knight on B1 (position - (7;1))
        if (playedFigure.getIdentity().endsWith("pawn1")) {
            pawnPosition = new Position(i, j);
        }
        if (playedFigure.getIdentity().endsWith("knight1")) {
            knightPosition = new Position (i, j);
        }
        allFigure[16].setPosition(pawnPosition.getRow(), pawnPosition.getCol());
        allFigure[25].setPosition(knightPosition.getRow(), knightPosition.getCol());
    }
}
