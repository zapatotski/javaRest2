package busyShedule.model.bizmodel;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Game implements Serializable {

	public int id;
	public Team team1;
	public Team team2;
	public Competition comp;
	public Date date;

	public Game(int id, Team team1, Team team2, Competition comp, Date date) {
		this.id = id;
		this.team1 = team1;
		this.team2 = team2;
		this.comp = comp;
		this.date = date;
	}

	public Game() {

	}
}

