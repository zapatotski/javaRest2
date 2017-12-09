package busyShedule.model.bizmodel;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Competition implements Serializable{
	
	public int id;
	public String name;
	public String country;
	public String startWeak;
	public String nextWeak;
	public String endWeak;
	public List<Team> teams;
	
	public Competition(int id, String name, String country, String startWeak, String nextWeak, String endWeak,List<Team> teams){
		this.id=id;
		this.name=name;
		this.country=country;
		this.startWeak=startWeak;
		this.nextWeak=nextWeak;
		this.endWeak=endWeak;
		this.teams=teams;
	}
	
	public Competition(){}

}
