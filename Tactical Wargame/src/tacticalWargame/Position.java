package tacticalWargame;

public class Position {
	private int x;
	private int y;
	
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	//*********************************************************************************
	// All Set Methods Follow
	void SetPosition(int newx, int newy){
		x = newx;
		y = newy;
		return;
	}
	
	//*********************************************************************************
	// All Get Methods Follow
	int GetX(){
		return x;
	}
	int GetY(){
		return y;
	}
}
