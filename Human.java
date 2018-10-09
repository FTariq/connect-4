public  class Human extends Player {


	public Human(Board b1) {
		super();
		b =b1;

	}


	public void addPiece(int pos, int player) {
		b.humanmove(pos,player);
							
	}

}

