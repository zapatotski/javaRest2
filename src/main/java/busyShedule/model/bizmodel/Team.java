package busyShedule.model.bizmodel;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Team implements Serializable{
	
	public int id;
	public String name;
	
	public Team(int id, String name){
		this.id=id;
		this.name=name;
	}
	
	public Team(){}

}
