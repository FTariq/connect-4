
public class Game {
	Board b1;
	Player[] p = new Player[2];
	View v;
	int pl=0;

	public Game(View v1, int mode){

		v = v1;
		b1 = new Board();
		p[0] = new Human(b1);
		if(mode == 1){
			p[1] = new Human(b1);
		}
		else{
			p[1] = new AI(b1);
		}
	}

	public void move(int pos,int mode){
		// adds different parameters to player move methods depending on the mode, which then runs through and adds the player piece to the chosen column. if notmoved is found to be true, the player does not change.
		if(b1.counter()!=42 && b1.checkwin()==0){	
			if (mode == 1){
				p[pl].addPiece(pos,pl+1);
				if(b1.getnotmoved()==true){
					v.seterrorpopup();
				}
				else{
					pl=(pl+1)%2;
				}
			}

			else{
				// does not switch players with ai move, as ai is shown to always be player 2. the ai does not move if player 1 hasn't successfully moved.
				p[0].addPiece(pos,1);
				
				if(b1.checkwin()==0 && b1.getnotmoved()==false){
					p[1].addPiece(mode, 2);
				}
				if(getnotmoved()==true){
					v.seterrorpopup();
					setnotmoved();
				}
			}
		}
	}


	public int getwin(){
		return b1.checkwin();
	}

	public void resetplayer(){
		pl=0;
	}

	public void resetBoard(){
		b1.resetBoard();
	}

	public void resetCounter(){
		b1.resetCounter();
	}

	public int getplayer(){
		return pl;
	}

	public int getspace(int x, int y){
		return b1.getspace(x,y);
	}

	public int checkwin(){
		return b1.checkwin();
	}

	public int counter(){
		return b1.counter();
	}
	
	public boolean getnotmoved(){
		return b1.getnotmoved();
	}
	
	public void setnotmoved(){
		b1.setnotmoved();
	}

	public void changeplayer(int mode){
		// player changes depending on mode. only runs when changing mode from board GUI.
		if(mode==1){
			p[1] = new Human(b1);
		}
		else{
			p[1] = new AI(b1);
		}
	}

}
