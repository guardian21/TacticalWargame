package tacticalWargame;

public class Position {
	private int x;
	private int y;
	
	
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
