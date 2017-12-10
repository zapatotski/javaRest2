package live;

import java.io.Serializable;

public class Player implements Serializable {

	public int id;
	public String name;
	public String pos;
	public int min;
	public int appear;
	public int lineup;
	public int goal;
	public int yellow;
	public int yellowred;
	public int red;

	public Player(int id, String name, String pos, int min, int appear, int lineup, int goal, int yellow, int yelloered,
			int red) {
		this.id = id;
		this.name = name;
		this.pos = pos;
		this.min = min;
		this.appear = appear;
		this.lineup = lineup;
		this.goal = goal;
		this.yellow = yellow;
		this.yellowred = this.yellowred;
		this.red = red;
	}

	public String toString() {
		return this.name + " " + this.appear + "/" + this.lineup + " " + this.goal + " " + this.yellow + " "
				+ this.yellowred + " " + this.red;
	}
}
