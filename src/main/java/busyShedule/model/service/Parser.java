package busyShedule.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import busyShedule.model.bizmodel.*;

public class Parser {

	// itogovoe map v kotoroe zapisuutsa vse matchi
	static Map<Integer, PriorityQueue<Game>> mapGame = new HashMap();
	// itogoviy spisok sostyazaniy
	static Map<Integer, List<Team>> mapCompetition = new HashMap();

	public static Map<Integer, List<Game>> parse(String[] hrefs) {
		for (String href : hrefs) {
			// map id_team-team chtob ne ozdavat noviy komandy
			Map<Integer, Team> mapTeamComp = new HashMap();
			Competition competition = null;
			String country = "";
			String compName = "";
			int compId = 0;
			String startWeak = "";
			String endWeak = "";
			try {
				Document doc = Jsoup.connect(href).get();

				Elements countryElements = doc.select("results");
				if (countryElements.isEmpty())
					continue;

				Element countryElement = countryElements.first();
				country = countryElement.attr("country");

				Elements tournamentElements = doc.select("tournament");
				if (tournamentElements.isEmpty())
					continue;

				Element tournamentElement = tournamentElements.first();
				compName = tournamentElement.attr("league");
				try {
					compId = Integer.valueOf(tournamentElement.attr("id"));
				} catch (Exception e) {
					continue;
				}

				Elements matchElements = doc.select("match");
				if (matchElements.isEmpty())
					continue;

				startWeak = matchElements.first().attr("date");
				endWeak = matchElements.last().attr("date");

				String nextWeak = "";

				// inizializiruem orevnovanie
				competition = new Competition(compId, compName, country, startWeak, nextWeak, endWeak, null);

				Boolean b = true;
				int gameId = 0;
				String date = "";
				int team1Id = 0;
				String team1Name = "";
				int team2Id = 0;
				String team2Name = "";

				for (int j = 0; j < matchElements.size(); j++) {
					try {
						gameId = Integer.valueOf(matchElements.get(j).attr("id"));
					} catch (Exception e) {
						continue;
					}
					date = matchElements.get(j).attr("date");
					int[] dataArr = toIntArray(date);
					if (dataArr.length != 3)
						continue;

					if ((!matchElements.get(j).attr("status").equals("FT")) && b) {
						nextWeak = matchElements.get(j).attr("date");
						b = false;
					}

					Elements team1Elements = matchElements.get(j).select("localteam");
					Elements team2Elements = matchElements.get(j).select("visitorteam");
					try {
						team1Id = Integer.valueOf(team1Elements.first().attr("id"));
						team1Name = team1Elements.first().attr("name");
						team2Id = Integer.valueOf(team2Elements.first().attr("id"));
						team2Name = team2Elements.first().attr("name");
					} catch (Exception e) {
						continue;
					}

					if (!mapTeamComp.containsKey(team1Id))
						mapTeamComp.put(team1Id, new Team(team1Id, team1Name));
					if (!mapTeamComp.containsKey(team2Id))
						mapTeamComp.put(team2Id, new Team(team2Id, team2Name));

					// vozmozen Exception v new Date();
					Game g = new Game(gameId, mapTeamComp.get(team1Id), mapTeamComp.get(team2Id), competition,
							new Date(dataArr[2] - 1900, dataArr[1] - 1, dataArr[0]));

					if (!mapGame.containsKey(team1Id))
						mapGame.put(team1Id, new PriorityQueue<Game>(new Comparator<Game>() {
							public int compare(Game t, Game t1) {
								if (t.date.getTime() < t1.date.getTime())
									return -1;
								if (t.date.getTime() > t1.date.getTime())
									return 1;
								else
									return 0;
							}
						}));

					if (!mapGame.containsKey(team2Id))
						mapGame.put(team2Id, new PriorityQueue<Game>(new Comparator<Game>() {
							public int compare(Game t, Game t1) {
								if (t.date.getTime() < t1.date.getTime())
									return -1;
								if (t.date.getTime() > t1.date.getTime())
									return 1;
								else
									return 0;
							}
						}));

					mapGame.get(team1Id).add(g);
					mapGame.get(team2Id).add(g);
				}

				competition.nextWeak = nextWeak;
				competition.teams = new ArrayList(mapTeamComp.values());

				if (!mapCompetition.containsKey(competition.id))
					mapCompetition.put(competition.id, competition.teams);
				else
					mapCompetition.replace(competition.id, competition.teams);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// perevodim v Map<id_team,List<Game>> - spisok s 2 proslih igr
		Map<Integer, List<Game>> result = new HashMap();
		for (Integer i : mapGame.keySet()) {
			PriorityQueue<Game> gl = mapGame.get(i);
			int n = gl.size();
			List<Game> resultList = new ArrayList();
			List<Game> lastGame = new ArrayList();
			for (int k = 0; k < n; k++) {
				Game tg = gl.poll();
				if (tg.date.getTime() < new Date().getTime()) {
					lastGame.add(tg);
				} else
					resultList.add(tg);
			}
			if (lastGame.size() < 2)
				continue;
			else {
				resultList.add(0, lastGame.get(lastGame.size() - 2));
				resultList.add(1, lastGame.get(lastGame.size() - 1));
				result.put(i, resultList);
			}
		}

		return result;
	}

	private static int[] toIntArray(String s) {
		String[] str = s.split("\\.");
		int[] arr = new int[str.length];
		try {
			for (int i = 0; i < str.length; i++)
				arr[i] = Integer.valueOf(str[i]);
		} catch (Exception e) {
		}
		return arr;
	}

}

