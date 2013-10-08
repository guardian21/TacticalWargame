package tacticalWargame;

public class Gun {
	private String name;
	private int damage;
	private int accuracy;
	private int range;

	
	//*********************************************************************************
	// All Constructors Follow	
	public Gun(String name, int damage, int accuracy, int range){
		SetName(name);
		SetDamage(damage);
		SetAccuracy(accuracy);
		SetRange(range);
	return;	
	}
	
	//*********************************************************************************
	// All Set Methods Follow
	
	void SetName(String newname){
		name = newname;
		return;
	}
	
	void SetDamage(int newdamage){
		damage = newdamage;
		return;
	}
	
	void SetAccuracy(int newaccuracy){
		accuracy = newaccuracy;
		return;
	}
	
	void SetRange(int newrange){
		range = newrange;
		return;
	}

	
	
	// All Get Methods Follow
	
	String GetName(){
		return name;
	}
	
	int GetDamage(){
		return damage;
	}
	
	int GetAccuracy(){
		return accuracy;
	}
	
	int GetRange(){
		return range;
	}
}
