package live;

import java.io.Serializable;
import java.util.Map;

public class Game implements Serializable {

	public String time;
	public String team1;
	public String team2;
	public String htScore;
	public String ftScore;
	public Map<Integer, Player> yellow1;
	public Map<Integer, Player> yellowred1;
	public Map<Integer, Player> red1;
	public Map<Integer, Player> subs1;
	public Map<Integer, Player> yellow2;
	public Map<Integer, Player> yellowred2;
	public Map<Integer, Player> red2;
	public Map<Integer, Player> subs2;

	public Game(String time, String team1, String team2, String htScore, String ftScore, Map<Integer, Player> yellow1,
			Map<Integer, Player> yellowred1, Map<Integer, Player> red1, Map<Integer, Player> subs1,
			Map<Integer, Player> yellow2, Map<Integer, Player> yellowred2, Map<Integer, Player> red2,
			Map<Integer, Player> subs2) {
		this.time = time;
		this.team1 = team1;
		this.team2 = team2;
		this.htScore = htScore.replace("[", "").replace("]", "");
		this.ftScore = ftScore.replace("[", "").replace("]", "");
		this.yellow1 = yellow1;
		this.yellowred1 = yellowred1;
		this.red1 = red1;
		this.subs1 = subs1;
		this.yellow2 = yellow2;
		this.yellowred2 = yellowred2;
		this.red2 = red2;
		this.subs2 = subs2;
	}

	public String toString() {
		String score = "";
		if ("".equals(this.ftScore)) {
			score = this.htScore;
		} else {
			score = this.ftScore;
		}

		return (this.team1.replace(" ", "") + this.team2.replace(" ", "")).replace(" ", "").replace(".", "")
				.replace("-", "").replace("`", "").replace("&", "");
	}
}

