package busyShedule.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import busyShedule.model.bizmodel.Team;
import busyShedule.model.dao.CompetitionDao;

public class CompetitionService {

	// s id_comp -> spisok id_teams of this id_comp
	public List<Integer> getTeams(int comp) {
		Map<Integer, List<Team>> m = new CompetitionDao().getTeams();
		List<Integer> result = new ArrayList();
		if (m.containsKey(comp)) {
			for (Team t : m.get(comp))
				result.add(t.id);
		}
		return result;
	}

	/*
	 * public List<Competition> getCompetition(String country){
	 * 
	 * }
	 */

}

