package MainClasses;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CreateFrame extends JFrame {

    public static InGame button[][] = new InGame[8][8];
    public static InGame endGame;

    public static Figure[] allFigure;

    InGame actionListener;

    public CreateFrame(int choice) {
        super("Cogitality");
        switch (choice) {
            case 0:
                begin();
                break;
            case 1:
                System.exit(0);
            case 2:
        }
    }

    public CreateFrame() {
        super("Cogitality");
        begin();
    }
    
    public void begin() {
        setResizable(false);
        setIconImage(getIcon());

        CreateFigure figure = new CreateFigure();
        allFigure = figure.getAllFigures();
        for (int n = 0; n < allFigure.length; ++n) {
            allFigure[n].setThreatenedPositions();
        }
        createPlayfield();
        setGame();

        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    //resizes the figure's image(icon) to fit the buttons
    public ImageIcon resizeIcon(ImageIcon icon) {
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(button[0][0].getSize().height * 5/6, button[0][0].getSize().width * 5/6, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);

        return icon;
    }
//sets up the game for start

    public void setGame() {
        Board board = new Board();
        String buttonName[][] = board.getTable();

        int p = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                button[i][j].setName(buttonName[i][j]);
                if (i <= 1 || i >= 6) {
                    button[i][j].setIcon(resizeIcon(allFigure[p].getSymbol()));
                    button[i][j].setActionCommand(allFigure[p].getIdentity());
                    ++p;
                }
            }
        }
    }

    //creates the playfield
    public void createPlayfield() {
        Container playField = getContentPane(); //field of figures
        playField.setLayout(new GridLayout(8, 8));
        
        JPanel board = new JPanel();
        board.setLayout(new GridLayout(8, 8));
        Dimension frameSize = this.getSize();
        board.setSize(frameSize.height / 2, frameSize.width / 2);
        board.setLocation(frameSize.height / 4, frameSize.width / 4);
        
        actionListener = new InGame();

        int temp = 0;
        int comparison = 8;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                button[i][j] = new InGame();
                button[i][j].setSize(50, 50);
                if (temp == comparison) {
                    temp++;
                    comparison += 9;
                }
                if (temp % 2 == 1) {
                    button[i][j].setBackground(Color.BLACK);
                } else if (temp % 2 == 0) {
                    button[i][j].setBackground(Color.WHITE);
                }

                board.add(button[i][j]);
                button[i][j].addActionListener(actionListener);
                ++temp;
            }//for
        }//for
        this.add(board);
    }

    //Icon
    public Image getIcon() {
        ImageIcon imageIcon = new ImageIcon("F:\\Java\\Programi\\Chess\\Photos\\ChessIcon.jpg");
        Image icon = imageIcon.getImage();
        return icon;
    }
}
