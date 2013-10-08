package tacticalWargame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


public class Game {

	/**
	 * @param args
	 */
	
	static int number_of_teams ;  // real +1 to start array from 1 instead of 0	
	static int number_of_agents ;
	static int[] team_alive ;
	static int winner ;
	static int dx; // length of map
	static int dy; // height of map
	
	static Comparator<Agent> speedcomparator = new speedComparator();

	static Agent[] all_agents ;
	static Scanner input = new Scanner(System.in);
	
	
	
	public static void main(String[] args) throws IOException {
		
		Game.AgentsCreation();
		
		int i;
		int teams_count =0;
			
		
		
		System.out.println("***********************************************************");
		System.out.println("Battle is about to start. All agents stats: ");
		for (i=0; i<Game.number_of_agents; i++){
			Game.all_agents[i].PrintHp();
		}

		boolean gameFinished = false;

		while (!gameFinished){
			Arrays.sort(Game.all_agents,speedcomparator);
			
			for (i=0; i < Game.number_of_agents; i++){
				if (Game.all_agents[i].IsAlive()){
					System.out.println("Now it is agent's "+ Game.all_agents[i].GetName() + " turn");
					Game.all_agents[i].Turn();
				}
				else{
					System.out.println("Agent "+ Game.all_agents[i].GetName()+ " is dead, so no turn for him :( ");
				}
			}
			
			
			System.out.println("***********************************************************");
			System.out.println("Round Finished. Agents stats: ");
			for (i=0; i<Game.number_of_agents; i++){
				Game.all_agents[i].PrintHp();
			}
			
			teams_count =0;
			for (i=1; i <= Game.number_of_teams; i++){
				if (Game.team_alive[i] != 0){
					teams_count++;
					winner = i;
				}
			}
			System.out.println("Teams count = " + teams_count);
			if (teams_count < 2){
				gameFinished = true;
				break;
			}
		}	
		System.out.println("*****************************************************************");
		System.out.println("*****************************************************************");
		System.out.println("*****************************************************************");
		System.out.println("*****************************************************************");
		System.out.println("*****************************************************************");
		System.out.println("*****************************************************************");
		
		System.out.println("The winner is Team " + winner);

		System.out.println("*****************************************************************");
		System.out.println("*****************************************************************");
		System.out.println("*****************************************************************");

		
		
	}
	
	
	
	static void AgentsCreation(){
		/* Initialises all teams, agents and weapons. Currently initialises agents  by input
		 * and weapons by hand. To do: initialise create more weapons, add random initialisation, etc
		 * also add checks for all the input getting
		 */
		
		Gun pistol = new Gun("Pistol", 3, 70, 2);
		Gun hands = new Gun("Hands", 1, 90,1);
		Gun knive = new Gun("Knive",2,80,1);
		
		
		int i;
		String name;
		int team;
		Gun gun;
		int speed;
		int hp;
		int view_range;
		boolean ai;
		char tempchoice;
		
		System.out.println("Welcome to tactical fighters, a fun and addicting game");
		System.out.println("Please enter which map will be used");
		try {
			Game.MapRead(Game.input.nextLine());
		} catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);	       
        }
		System.out.println("Please enter number of teams");
		Game.number_of_teams = Integer.parseInt(Game.input.nextLine());
		System.out.println("Please enter total number of agents (not per team)");
		Game.number_of_agents = Integer.parseInt(Game.input.nextLine());
		
		Game.all_agents = new Agent[number_of_agents];
		Game.team_alive = new int[Game.number_of_teams+1];


		for (i=0; i<Game.number_of_teams; i++){
			Game.team_alive[i] = 0;
		}

		
		
		for (i=0; i<Game.number_of_agents; i++){
			
			System.out.println("Please enter name of agent number: " + i);
			name = Game.input.nextLine();
	// Default Initialisation values		
			gun = hands;
			speed = 1;
			hp = 10;
			view_range = 3;
	// Read Correct balues from Text File		
			File tempfile = new File(name+".txt");
	        try {
	            Scanner scanner = new Scanner(tempfile);
	            
	                String weaponchoice = scanner.nextLine();
	    			switch (weaponchoice){
					case "pistol": gun = pistol;
										break;
					case "knive" : gun = knive;
									break;
					case "hands" : gun = hands;	
				}

	            speed= Integer.parseInt(scanner.nextLine());
	            hp= Integer.parseInt(scanner.nextLine());
	            view_range= Integer.parseInt(scanner.nextLine());
		        scanner.close();		
	            
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	            System.exit(0);	        }

        
	        System.out.println("Please enter the number of team agent " + name + " is on");
			team = Integer.parseInt(Game.input.nextLine());
			
// Older Version allowing creation of agents on start up			
//			System.out.println("Please chose the gun of agent " + name );
//			System.out.println("Enter p for pistol, h for hands, k for knive");
//			tempchoice = Game.input.nextLine().charAt(0);
//			switch (tempchoice){
//				case 'p':
//				case 'P':	gun = pistol;
//							break;
//				case 'k':
//				case 'K':	gun = knive;
//							break;
//				default:	gun = hands;	
//			}
//
//			System.out.println("Please enter the speed of agent " + name );
//			speed = Integer.parseInt(Game.input.nextLine());
//			
//			System.out.println("Please enter the hp of agent " + name );
//			hp = Integer.parseInt(Game.input.nextLine());
//			
//			System.out.println("Please enter the view range of agent " + name );
//			view_range = Integer.parseInt(Game.input.nextLine());
			
			System.out.println("Please enter Y if player is player controlled, else N if he is AI controlled");
			tempchoice = Game.input.nextLine().charAt(0);
			if (tempchoice == 'y' || tempchoice == 'Y'){
				ai = false;
			}
			else{
				ai = true;
			}
			
			Agent temp = new Agent(name,team, gun, speed,hp,view_range, ai);
			all_agents[i]= temp;
			Game.team_alive[team]++;

			
		}
	}


	static void MapRead(String mapname) throws FileNotFoundException{
	
		// Reads a map from a file. File format:
		// x y (dimensions, 2 integers)
		//xxxxxxxxx
		//xooooxoox
		//xooxoooxx
		//xooooxxox
		//xxoooooox
		//xxxxxxxxx
		//
				
		File f1 = new File(mapname);
				
		Scanner sc = new Scanner(f1);    // Read the input file first lines (size, and starting positions)

		dx = sc.nextInt();
		
		dy = sc.nextInt();
		
		String tmp = sc.nextLine();
		String[] mapLines = new String[dy];
				
		/* Read the map :
		 * we assume
		 * that the map starts from top left and coordinates rise towards right and bottom
		 * We also use for calculations starting position as 0,0 (so a 10x10 map would have 
		 * bottom right position: 9,9)
		 * Also, all our tables are [y][x], so that we understand them easier. (so coordinates 2,3 are in the table index 3,2)
		 */
		
		char[][] map = new char[dy][dx];
		
		int i,j;
		for(i=0; i<dy ;i++){
			
			mapLines[i] = sc.nextLine();
			
			map[i] = mapLines[i].toCharArray();
		}
		sc.close();
	
	
	
	System.out.println("---------------MAP------------------_");
	
	for(i=0;i<dy;i++){
		for(j=0;j<dx;j++)
			System.out.print(map[i][j] + "  ");
		System.out.println();
	}
	
	System.out.println("__________________________________________________________________________");

	}
}
