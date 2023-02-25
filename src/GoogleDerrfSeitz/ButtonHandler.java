package GoogleDerrfSeitz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonHandler extends JButton implements ActionListener{
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		NewFrame returning = new NewFrame(source);
		//NewFrame.findMove(source);
	}
}
