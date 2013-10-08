package tacticalWargame;

import java.util.Comparator;

public class speedComparator implements Comparator<Agent> {


	public int compare(Agent left, Agent right){
		
		int l,r;
		
		l = left.GetSpeed();
		
		r = right.GetSpeed();
		
		if(l<r)
			return 1;
		
		else if(l>r)
			return -1;
		
		else
			return 0;
	}

}

