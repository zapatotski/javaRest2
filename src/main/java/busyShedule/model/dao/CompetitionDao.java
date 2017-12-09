package busyShedule.model.dao;

import busyShedule.model.bizmodel.Team;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompetitionDao {
	
	private File f = new File("comp_teams.data");
	
	public Map<Integer,List<Team>> getTeams(){
		return (Map<Integer,List<Team>>) deserialization(f);
	}
	
	public void setTeams(Map<Integer,List<Team>> m){
		serialization(f,m);
	}
	
	private void serialization(File f, Object m){
        FileOutputStream fOut=null;
        ObjectOutputStream oOut=null;
        try{
            fOut=new FileOutputStream(f);
            oOut=new ObjectOutputStream(fOut);
            oOut.writeObject(m);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
	}
	
    public Object deserialization(File f){
        FileInputStream fInput=null;
        ObjectInputStream oInput=null;
        Object result=null;
        try{
            fInput=new FileInputStream(f);
            oInput=new ObjectInputStream(fInput);
            result=oInput.readObject();
        }
        catch(Exception e){
        	throw new RuntimeException(e);
        }
        return result;
    }
}
