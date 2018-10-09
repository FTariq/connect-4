import javax.swing.*;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View implements ActionListener{
	JButton[][] button = new JButton[7][7];
	JFrame frame;
	JPanel pane;
	JMenuBar menubar;
	JMenu menu;
	JMenuItem menus[] = new JMenuItem[4];
	JMenu subMenu;
	JLabel currentmode;
	JLabel currentplayer;
	Game g;
	int mode;
	int pl=1;

	public View (int n){
		mode=n;

		frame = new JFrame("Connect 4");		
		frame.setSize(800,700);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menubar = new JMenuBar();
		frame.setJMenuBar(menubar);
		menu = new JMenu("New Game");
		menubar.add(menu);

		menus[0] = new JMenuItem("Two Player Game");
		menus[0].addActionListener(this);
		menu.add(menus[0]);

		subMenu = new JMenu("Player vs. Bot Game");
		menu.add(subMenu);

		menus[1] = new JMenuItem("Easy");
		menus[1].addActionListener(this);
		subMenu.add(menus[1]);

		menus[2] = new JMenuItem("Medium");
		menus[2].addActionListener(this);
		subMenu.add(menus[2]);

		menus[3] = new JMenuItem("Hard");
		menus[3].addActionListener(this);
		subMenu.add(menus[3]);

		menubar.add(Box.createHorizontalGlue());
		currentplayer = new JLabel("Player 1 (Colour: R)");
		currentplayer.setForeground(Color.gray);
		menubar.add(currentplayer);

		currentmode = new JLabel("");
		changemodelabel();
		currentmode.setForeground(Color.gray);
		menubar.add(currentmode);

		pane = new JPanel(new GridLayout(7,7));

		for(int y=6;y>-1;y--){
			for(int x=0;x<7;x++){
				button[y][x] = new JButton("");
				button[y][x].setEnabled(false);
				pane.add(button[y][x]);
			}
		}

		for(int y=0;y<7;y++){
			button[0][y].addActionListener(this);
			button[0][y].setEnabled(true);
			button[0][y].setActionCommand(""+y);
		}


		frame.getContentPane().add(pane);

		g = new Game(this, n);

		frame.setVisible(true);

	}

	public void updateColour(){
		//runs after every move to update board when a piece is placed. if the space is 0, returns to original grey colour (which happens when game is reset)
		for(int x=0;x<7;x++){
			for(int y=0;y<6;y++){
				if(g.getspace(x,y)==0){
					button[y+1][x].setBackground(null);
				}
				if(g.getspace(x,y)==1){
					button[y+1][x].setBackground(Color.red);
				}
				if(g.getspace(x,y)==2){
					button[y+1][x].setBackground(Color.yellow);
				}

			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		// if menu buttons pressed game is reset and mode is changed. Else a move is run. This method runs if any button on the board GUI is pressed.
		String clicked = e.getActionCommand();
		if(clicked=="Two Player Game" || clicked=="Easy" || clicked=="Medium" || clicked=="Hard"){
			if(clicked=="Two Player Game"){
				changegame(1);
			}
			if(clicked=="Easy"){
				changegame(2);
			}
			if(clicked=="Medium"){
				changegame(3);
			}
			if(clicked=="Hard"){
				changegame(4);
			}
		}
		else{
			int columnno = Integer.parseInt(clicked);
			g.move(columnno,mode);
			updateColour();
			changeplayerlabel();
			int checkwin=g.checkwin();

			if(checkwin!=0){
				setwinpopup(checkwin);
			}

			else{
				if(g.counter()==42){
					setstalematepopup();
				}
			}
		}

	}

	public void setwinpopup(int player){
		// congratulates player for a win, and if yes is pressed to playing again, game is reset
		int n = JOptionPane.showConfirmDialog(frame, "Player " + player + " has won! Play again?","Win",JOptionPane.YES_NO_OPTION);
		if(n==0){
			resetgame();
		}
	}

	public void setstalematepopup(){
		// states that nobody has won, and if yes is pressed to playing again, game is reset
		int n = JOptionPane.showConfirmDialog(frame, "Nobody wins! Play again?","Stalemate",JOptionPane.YES_NO_OPTION);
		if(n==0){
			resetgame();
		}
	}

	public void seterrorpopup(){
		// displayed if a full column is clicked on
		JOptionPane.showMessageDialog(frame, "That column is full. Please choose another.", "Error", JOptionPane.ERROR_MESSAGE);
	}


	public void resetgame(){
		// player label in menu bar and current player in game is reset to Player 1, all board spaces are reset to 0 and board is updated to show this, and counter is reset to 0
		pl=0;
		changeplayerlabel();
		g.resetplayer();
		g.resetBoard();
		updateColour();
		g.resetCounter();

	}

	public void changegame(int x){
		// same methods as resetgame(), but also changes mode, changes player 2 to human or ai depending on new mode, and changes game mode label in menu bar.
		mode=x;
		g.changeplayer(x);
		changemodelabel();
		resetgame();

	}


	public void changemodelabel(){
		// changes current mode label in the board GUI. run when mode changed.
		if(mode==1){
			currentmode.setText("  |  Two Player Game  ");
		}
		if(mode==2){
			currentmode.setText("  |  Easy AI Game  ");
		}
		if(mode==3){
			currentmode.setText("  |  Medium AI Game  ");
		}
		if(mode==4){
			currentmode.setText("  |  Hard AI Game  ");
		}
	}

	public void changeplayerlabel(){
		// changes current player label in the board GUI. run when game reset or mode changed.
		if(g.getnotmoved()==false){
			if(mode==1 || pl == 0){
				pl=(pl+1)%2;
				if(pl==1){
					currentplayer.setText("Player 1 (Colour: R)");
				}
				else{
					currentplayer.setText("Player 2 (Colour: Y)");
				}

			}
		}
		else{
			g.setnotmoved();
		}

	}
}

