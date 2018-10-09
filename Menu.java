import javax.swing.*;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu implements ActionListener{
	JFrame frame;
	JButton[] button = new JButton[4];
	
	public Menu(){
		frame = new JFrame("Choose a Game Mode");	
		frame.setLayout(new GridLayout(4,1));
		frame.setSize(400,700);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		button[0] = new JButton("Two Player Game");
		button[1] = new JButton("Easy AI Game");
		button[2] = new JButton("Medium AI Game");
		button[3] = new JButton("Hard AI Game");
		

		for(int x=0;x<4;x++){
			button[x].addActionListener(this);
			frame.add(button[x]);
		}
		
		frame.setVisible(true);
		
	}

	public void actionPerformed(ActionEvent e) {
		//based off the button pressed, a different mode of the game will be run
		if(e.getSource()==button[0]){
			frame.setVisible(false);
			new View(1);
			
		}
		if(e.getSource()==button[1]){
			frame.setVisible(false);
			new View(2);
		}
		if(e.getSource()==button[2]){
			frame.setVisible(false);
			new View(3);
		}
		if(e.getSource()==button[3]){
			frame.setVisible(false);
			new View(4);
		}
	}

}
