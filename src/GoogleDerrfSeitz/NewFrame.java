package GoogleDerrfSeitz;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class NewFrame extends JFrame{
	private Container contents;
	
	ButtonHandler buttonHandler;
	static JButton[][] squares = new JButton[8][8];

	
	protected static int row = 7;
	protected static int col = 1;
	protected ImageIcon knight = new ImageIcon("Снимки\\White figures\\KnightW.png");
	
	private Color colorBlack = Color.BLACK;
	private Color colorWhite = Color.white;
	
	
	public NewFrame(){
		super("Chess");
		
		contents = getContentPane();
		contents.setLayout(new GridLayout(8, 8));
		
		buttonHandler = new ButtonHandler();
		
		makeButtons();
		
		setSize(500, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public NewFrame(Object source){
		findMove(source);
	}
	
	public void makeButtons(){
		
		for(int i = 0; i < 8; ++i){
			for(int j = 0; j < 8; ++j){
				squares[i][j] = new JButton();
				if((i+j) % 2 != 0){
					squares[i][j].setBackground(colorBlack);
				}
				else{
					squares[i][j].setBackground(colorWhite);
				}
				contents.add(squares[i][j]);
				squares[i][j].addActionListener(buttonHandler);
			}
		}
		squares[row][col].setIcon(resizeIcon(knight));
	}
	
	public void findMove(Object source){
		for(int i = 0; i < 8; ++i){
			for(int j = 0; j < 8; ++j){
				if(source == squares[i][j]){
					processClick(i,j);
					return;
				}
			}
		}
	}

	private void processClick(int i, int j){
		if(isValidMove(i, j) == false){
			return;
		}
		squares[row][col].setIcon(null);
		squares[i][j].setIcon(resizeIcon(knight));
		row = i;
		col = j;
	}
	
	private boolean isValidMove(int i, int j){
		int rowDelta = Math.abs(i - row);
		int colDelta = Math.abs(j - col);
		
		if((rowDelta == 1) && (colDelta == 2)){
			return true;
		}
		if((rowDelta == 2) && (colDelta == 1)){
			return true;
		}
		return false;
	}
	
	public ImageIcon resizeIcon(ImageIcon icon){
		Image image = icon.getImage();
		Image newImage = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newImage);
		
		return icon;
	}
}
