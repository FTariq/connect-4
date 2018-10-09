
public class AI extends Player {
	
	public AI(Board b1){

		super();
		b=b1;

	}

	public void addPiece(int mode, int player){
			b.aimove(mode,player);
		}
	
}

