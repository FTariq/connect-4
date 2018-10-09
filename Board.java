import java.util.Random;


public class Board {
	private int[][] spaces;
	private boolean notmoved;
	private boolean moved;
	int counter=0;

	public Board(){
		spaces = new int[6][7];

	}

	public int getspace(int n, int o){
		// returns the value of the space (x,y)
		return spaces[o][n];
	}

	public  void changespace(int x, int y, int player) {
		// changes value of space (x,y) to player 
		spaces[y][x]=player;
	}

	public int counter(){
		// returns the counter to see if the board is full 
		return counter;
	}

	public void addCounter(){
		// counter is added to after every move, including every human and ai move
		counter=counter+1;
	}

	public void resetCounter(){
		// counter is reset when a new game occurs, through the end game option or through choosing a new game mode
		counter=0;
	}

	public int scanY(int x){
		// returns the top y value of column x, so piece is placed at top of column. returning 6 = full column.
		int scan=6;
		boolean scanned=false;

		while(scanned==false){
			for(int y=0;y<6;y++){
				if (getspace(x,y)==0 && scanned==false){
					scan=y;												
					scanned=true;
					break;
				}
				if(getspace(x,5)!=0){
					scanned=true;
					break;
				}
			}
		}
		return scan;
	}

	public void resetBoard(){
		// all spaces are reset to 0
		for(int x=0;x<7;x++){
			for(int y=0;y<6;y++){
				changespace(x,y,0);
			}
		}
	}

	public int findverticalwin(){
		// goes through each column looking for four in a row
		int xwin=0;
		for(int x=0;x<7;x++){
			for(int y=0;y<3;y++){
				if(getspace(x,y)==1 && getspace(x,y+1)==1 && getspace(x,y+2)==1 && getspace(x,y+3)==1){
					xwin= 1;
				}
				if(getspace(x,y)==2 && getspace(x,y+1)==2 && getspace(x,y+2)==2 && getspace(x,y+3)==2){
					xwin= 2;
				}
			}
		}
		return xwin;
	}

	public int findhorizontalwin(){
		// goes through each row looking for four in a row
		int ywin=0;
		for(int y=0;y<6;y++){
			for(int x=0;x<4;x++){
				if(getspace(x,y)==1 && getspace(x+1,y)==1 && getspace(x+2,y)==1 && getspace(x+3,y)==1){
					ywin= 1;
				}
				if(getspace(x,y)==2 && getspace(x+1,y)==2 && getspace(x+2,y)==2 && getspace(x+3,y)==2){
					ywin= 2;
				}
			}
		}
		return ywin;
	}

	public int findrightdiagonalwin(){
		// looks for four in a row diagonally facing right
		int rightdiagwin = 0;
		for(int x=0;x<4;x++){
			for(int y=0;y<3;y++){
				if(getspace(x,y)==1 && getspace(x+1,y+1)==1 && getspace(x+2,y+2)==1 && getspace(x+3,y+3)==1){
					rightdiagwin=1;
				}
				if(getspace(x,y)==2 && getspace(x+1,y+1)==2 && getspace(x+2,y+2)==2 && getspace(x+3,y+3)==2){
					rightdiagwin=2;
				}
			}
		}
		return rightdiagwin;
	}

	public int findleftdiagonalwin(){
		// looks for four in a row diagonally facing left
		int leftdiagwin = 0;
		for(int x=3;x<7;x++){
			for(int y=0;y<3;y++){
				if(getspace(x,y)==1 && getspace(x-1,y+1)==1 && getspace(x-2,y+2)==1 && getspace(x-3,y+3)==1){
					leftdiagwin=1;
				}
				if(getspace(x,y)==2 && getspace(x-1,y+1)==2 && getspace(x-2,y+2)==2 && getspace(x-3,y+3)==2){
					leftdiagwin=2;
				}
			}
		}
		return leftdiagwin;
	}

	public int checkwin(){
		// adds the win methods, which will all be zero except for one if is a four in a row in that setup. 
		int win = findverticalwin() + findhorizontalwin() + findrightdiagonalwin() + findleftdiagonalwin();
		return win;
	}

	public void setnotmoved(){
		// used if scanY==6 and move cannot go ahead.
		notmoved=false;
	}

	public boolean getnotmoved(){
		// if returned true, the player will not switch and must move again.
		return notmoved;
	}

	public void humanmove(int pos, int player) {
		// places a counter in the column pos for the player player. if successful, change space and add to counter. else set notmoved to true so the player does not change.

		int scan=scanY(pos);

		if (scan!=6){
			changespace(pos,scan,player);
			addCounter();
		}
		else{
			notmoved=true;
		}

	}

	public void aimove(int mode, int player){
		// different events for ai will run depending on mode. 
		moved=false;
		if(mode==2){
			airandom(player);
		}

		if(mode==3){
			aiblock(player);
			airandom(player);
		}

		if(mode==4){
			aiwin(player);
			aiblock(player);
			airandom(player);

		}
	}

	public void airandom(int player){
		// choose random variable and find its topmost position. if full column, generate another number and try again. else, move there.
		while(moved==false){
			Random randomGenerator = new Random();
			int r = randomGenerator.nextInt(7);
			int scan=scanY(r);
			if (scan!=6){
				changespace(r,scan,player);
				moved=true;
				addCounter();
			}
		}
	}

	public void aiblock(int player){
		// ai places the other player's piece on every topmost space, and checkwin to see if other player wins from moving to a space. if so, ai moves to that space to prevent other player from winning.
		if(moved==false){
			for(int x=0;x<7;x++){
				int scan=scanY(x);
				if (scan!=6 && moved==false){
					changespace(x,scan,player-1);
					if(checkwin()==player-1){
						changespace(x,scan,player);
						moved=true;
						addCounter();
					}
					else{
						changespace(x,scan,0);
					}
				}

			}
		}
	}

	public void aiwin(int player){
		// ai places their own piece on every topmost space, and checkwin to see if they wins from moving to a space. if so, ai moves there and wins.
		for(int x=0;x<7;x++){
			int scan=scanY(x);
			if (scan!=6 && moved==false){
				changespace(x,scan,player);
				if(checkwin()==player){
					moved=true;
					addCounter();
				}   
				else{
					changespace(x,scan,0);
				}
			}
		}
	}

}








