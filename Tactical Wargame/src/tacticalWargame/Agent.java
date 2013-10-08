package tacticalWargame;

import java.io.IOException;
import java.util.Random;


public class Agent {
	
	Random rand = new Random();
	
	private String name;
	private int team;
	private Gun gun;
	private int speed;
	private int hp;
	private int view_range;
	private Position position;
	private boolean dodge_flag ;
	private boolean block_flag ;
	private boolean restore_flag;
	private boolean has_attacked;
	private boolean alive;
	private int has_moves ;
	private boolean ai ;
	
	//*********************************************************************************
	// All Constructors Follow	
	
	public Agent(String name,int team, Gun gun, int speed, int hp, int view_range, boolean ai){
		this.SetName(name);
		this.SetTeam(team);
		this.AddGun(gun);
		this.SetSpeed(speed);
		this.SetHp(hp);
		this.SetView_range(view_range);
		this.SetAi(ai);
		this.alive = true;
		return;
	}
	
	//*********************************************************************************	
	// Action Methods
	
	boolean Move(Position new_position){
		// Returns false if moved not allowed
		if (this.MoveAllowed(new_position)){
		this.position = new_position;
		return true;
		}
		return false;
	}
	
	void Dodge(){
		this.dodge_flag = true;
		return;
	}
	
	void Block(){
		this.block_flag = true;
		return;
	}
	
	void Attack (Agent enemy){
	// Returns true if Enemy died, else false	
		if (this.AttackSuccessful()) {
			int damage = enemy.Attacked (CalculateDamage(enemy));
			System.out.println(this.GetName()+ " has successfully attacked agent "+ enemy.GetName() + " for "+ damage + " damage");
			
			if (enemy.GetHp() <= 0){	// Enemy Died
				enemy.PrintDead();
				enemy.SetAlive(false);
				Game.team_alive[enemy.GetTeam()] --;
			}

		}
		else {
			System.out.println(this.GetName()+ " 's attack against agent "+ enemy.GetName() + " missed");
		}
		return;
	}
	
	
	
	
	//*********************************************************************************
	// Turn Related Methods
	void Turn() throws IOException{
		// Initialisations for every turn
		this.SetHas_attacked(false);
		this.SetHas_moves(this.GetSpeed());
		int i=0;
		
		if (!IsAi()){
			while (this.Has_more_moves() || !this.has_attacked){
				// Chose your actions
				System.out.println("Please choose your action. Enter :");
				System.out.println("A to attack (if you haven't attacked this round");
				System.out.println("F to finish your turn");
				PlayerAction(GetActionInput());			
			}
		}
		
		else {
			// AI Logic
			while (i<=Game.number_of_agents){
				if (Game.all_agents[i].GetTeam() != this.GetTeam()){
					this.Attack(Game.all_agents[i]);
					i= Game.number_of_agents+1; // to break from loop
				}
				i++;
			}
					}
		
		return;
	}
	
	void PlayerAction(char action) throws IOException{
		switch (action){
		case 'a' :
		case 'A' :  if (this.has_attacked){
						System.out.println("You have already attacked this round. Please chose another action");
					}
					else{
						this.Attack(GetTargetInput());
						this.SetHas_attacked(true);
					}
					break;
		case 'f' :
		case 'F' :  this.SetHas_attacked(true);
					this.SetHas_moves(0);
					this.Has_more_moves();
					break;
		default  :  System.out.println("This is not allowed. Please enter : ");
					System.out.println("A to attack (if you haven't attacked this round");
					System.out.println("F to finish your turn");
					action = this.GetActionInput();
		}
		return;
	}
			
	

	char GetActionInput() throws IOException{
		// Returns only the 1st char it reads and the rest is ignored
		return Game.input.nextLine().charAt(0);
	}
	
	Agent GetTargetInput() throws IOException{
		// Returns the targeted Agent
		System.out.println("Enter the Agent to target");
		String targetname = Game.input.nextLine();
		Agent agent =  ChoseAgent(targetname);
		while ( agent == null){
			targetname = Game.input.nextLine();
			agent =  ChoseAgent(targetname);
		}
		return agent;
	}
	

	
	Agent ChoseAgent(String name){
		// Returns an agent by his name. If name doesn't exist returns null
		int i=0;
		
		while (i<Game.number_of_agents){
			if (Game.all_agents[i].GetName().equals(name)){
				System.out.println("Target acquired");
				return Game.all_agents[i];
			}
			i++;
		}
		return null;
	}
	
	
	void InitialiseHas_moves(){
		this.has_moves = this.speed;
	}
	
	void DcrHas_moves(){
		this.has_moves--;
	}
	
	//*********************************************************************************
	// Attack Related Methods
	
	int Attacked (int damage){
		// Returs points of damage
		this.SetHp(hp-damage);
		return damage;
	}
	

	
	boolean AttackSuccessful(){
	// Returns true if attack found target, else false	
		/* probability = random number + accuracy.So if acc = 80  (%) then number + 80 has 
		 * 80% probability to be above 100. If yes then attack successful
		 */
		int probability = rand.nextInt(100) + this.gun.GetAccuracy();
		
		if (probability <100){
			return false;
		}
		return true;
	}
	
	int CalculateDamage(Agent enemy){
		int final_damage;
		
		final_damage = this.gun.GetDamage();
		return final_damage;
	}
	//*********************************************************************************
	// Move Related Methods
	
	boolean MoveAllowed(Position newposition){
		// TO DO 
		return true;
	}
	
	
	//*********************************************************************************
	// All Set Methods Follow
	
	void SetName(String name){
		this.name = name;
		return;
	}
	
	void SetTeam(int team){
		this.team = team;
		return;
	}

	void AddGun(Gun gun){
		this.gun = gun;
		return;
	}
	
	void SetHp(int hp){
		this.hp = hp;
		return;
	}
	
	void SetSpeed(int speed){
		this.speed = speed;
		return;
	}
	
	void SetView_range(int view_range){
		this.view_range = view_range;
		return;
	}
	
	void SetDodge_flag(boolean flag){
		this.dodge_flag = flag;
		return;
	}
	
	void Setblock_flag(boolean flag){
		this.block_flag = flag;
		return;
	}
	
	void SetRestore_flag(boolean flag){
		this.restore_flag = flag;
		return;
	}
	
	void SetPosition(Position position){
		this.position = position;
		return;
	}
	
	void SetHas_attacked(boolean flag){
		this.has_attacked = flag;
	}
	
	void SetHas_moves(int moves){
		this.has_moves = moves;
	}

	void SetAlive(boolean flag){
		this.alive = flag;
	}
	void SetAi(boolean flag){
		this.ai = flag;
	}
	
	//*********************************************************************************
	// All Get Methods Follow
	
	String GetName(){
		return this.name;
	}

	int GetTeam(){
		return this.team;
	}

	int GetHp(){
		return this.hp;
	}
	
	int GetSpeed(){
		return this.speed;
	}

	int GetView_range(){
		return this.view_range;
	}
	
	boolean GetDodge_flag(){
		return this.dodge_flag;
	}

	boolean GetBlock_flag(){
		return this.block_flag;
	}
	
	boolean GetRestore_flag(){
		return this.restore_flag;
	}
	
	boolean GetHas_attacked(){
		return this.has_attacked;
	}
		
	boolean IsAlive(){
		return this.alive;
	}
	
	boolean IsAi(){
		return this.ai;
	}
	
	boolean Has_more_moves(){
		if (has_moves == 0 ){
			return false;
		}
		return true;
	}
	// All print methods follow:
	
	void PrintHp(){
		if (this.IsAlive()){
			System.out.println(this.GetName()+"(team "+this.GetTeam()+") has "+ this.GetHp() +" hp left");
			return;
		}
		System.out.println(this.GetName()+"(team "+this.GetTeam()+") has died");
	}
	
	void PrintDead(){
		 System.out.println(this.GetName()+"(team "+this.GetTeam()+") has died");

	}
}