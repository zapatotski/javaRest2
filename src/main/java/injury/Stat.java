package injury;


public class Stat {

	public Statistics[] statistics;

	public class Statistics {

		public String uniqueTournamentId;
		public String uniqueTournamentName;
		public Season[] seasons;

		public class Season {

			public String year;
			public String id;
			public Value[] statistics;

			public class Value {

				public int matchesTotal;
				public int matchesStarting;
				public int minutesPerGame;
				public int goals;
				public int yellowCards;
				public int yellowRedCards;
				public int redCards;
				public String groupName;
			}
		}
	}
}
