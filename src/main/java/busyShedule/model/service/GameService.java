package busyShedule.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import busyShedule.model.bizmodel.Game;
import busyShedule.model.dao.GameDao;

public class GameService {
	
	private Map<Integer,List<Game>> games=new GameDao().getGames();
	private CompetitionService compService=new CompetitionService();
	
	//Ne zabit o minimum number matchey!!!! Dodelat
	//Map<id_team,List<Game>>
	public Map<Integer,List<Game>> getGames(int number,int day, boolean mode, int[] comps){
		Map<Integer,List<Game>> result=new HashMap();
		//spisok id_teams iz spisca id_competition
		List<Integer> teamsId=new ArrayList();
		for(int i=0;i<comps.length;i++)
			teamsId.addAll(compService.getTeams(comps[i]));
		
		//logic
		for(Integer id:teamsId){
			if(!games.containsKey(id))
				continue;
			
			List<Game> teamGames=games.get(id);			
			
			if(mode){
				//dva proslih
				if(teamGames.size()<number)
					continue;
				Date dStart=teamGames.get(0).date;
				Date dEnd=teamGames.get(number-1).date;	
				if((dEnd.getTime()-dStart.getTime())<((long) day*86400000)){
					result.put(id, teamGames.subList(0, number));
				}
			}
			else{
				//odyn prosliy
				if(teamGames.size()<number+1)
					continue;
				Date dStart=teamGames.get(1).date;
				Date dEnd=teamGames.get(number).date;
				if((dEnd.getTime()-dStart.getTime())<((long) day*86400000)){
					result.put(id, teamGames.subList(1, number+1));
				}
			}
			
		}
		return result;
	}

}
