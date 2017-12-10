package injury;


public class Matchi {

	public SportItem sportItem;
	public Params params;
	public Boolean isShortDate;

	public class Params {

		public String sport;
		public String category;
		public String date;
	}

	public class SportItem {

		public Sport sport;
		public String rows;
		public Tournaments[] tournaments;

		public class Tournaments {

			public Tournament tournament;
			public Category category;
			public Season season;
			public boolean hasEventPlayerStatistics;
			public String hasEventPlayerHeatMap;
			public Events[] events;

			public class Tournament {

				public String id;
				public String name;
				public String slug;
				public String priority;
				public String order;
				public String uniqueId;
				public String uniqueName;
				public boolean hasEventPlayerStatistics;
				public String hasEventPlayerHeatMap;
			}

			public class Category {

				public String id;
				public String name;
				public String slug;
				public String priority;
				public String[] mcc;
				public String flag;
			}

			public class Season {

				public String id;
				public String name;
				public String slug;
				public String year;
			}

			public class Events {

				public int id;
				public String customId;
				public Score homeScore;
				public Score awayScore;
				public Status status;
				public String winnerCode;
				public Changes changes;
				public RoundInfo roundInfo;
				public Sport sport;
				public Team homeTeam;
				public Team awayTeam;
				public String hasHighlights;
				public String hasHighlightsStream;
				public boolean hasEventPlayerStatistics;
				public String hasEventPlayerHeatMap;
				public String homeRedCards;
				public String awayRedCards;
				public String statusDescription;
				public String hasLiveForum;
				public String uniqueTournamentId;
				public String name;
				public String startTime;
				public String formatedStartDate;
				public long startTimestamp;
				public String slug;
				public String hasLineupsList;
				public String hasOdds;
				public String hasLiveOdds;
				public String hasFirstToServe;
				public String hasDraw;
				public String isSyncable;

				public class Score {

					public String current;
					public String period1;
					public String normaltime;
				}

				public class Status {

					public int code;
					public String type;
				}

				public class Changes {

					public String changeDate;
					public String[] changes;
					public String changeTimestamp;
					public Boolean hasExpired;
					public Boolean hasHomeChanges;
					public Boolean hasAwayChanges;
				}

				public class Team {

					public String id;
					public String name;
					public String slug;
					public String gender;
				}

				public class RoundInfo {

					public String round;
				}
			}
		}

		public class Sport {

			public String id;
			public String name;
			public String slug;
		}
	}
}

