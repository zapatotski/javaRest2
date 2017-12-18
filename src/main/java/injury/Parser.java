//package main.java.injury;
package injury;


import com.google.gson.Gson;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
// import main.java.injury,live..
import injury.Stat.Statistics.Season.Value;
import live.Game;
import live.Player;

public class Parser {
	
	public static InputStream getInjury() {
		InputStream otvet=null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection conn = null;
		try {
			 URI jdbUri = new URI(System.getenv("JAWSDB_URL"));
			 String username = jdbUri.getUserInfo().split(":")[0];
			 String password = jdbUri.getUserInfo().split(":")[1];
			 String port = String.valueOf(jdbUri.getPort());
			 String jdbUrl = "jdbc:mysql://" + jdbUri.getHost() + ":" + port + jdbUri.getPath();
			 conn=DriverManager.getConnection(jdbUrl, username, password);
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		catch (Exception ex2) {
		    // handle any errors
		    System.out.println(ex2);
		}
		
		ResultSet result=null;
		PreparedStatement statement=null;
		try {
		    //create inputstream
	        String sql = "SELECT data FROM LastDay WHERE id=0;";
	        statement = conn.prepareStatement(sql);
	        
	        result = statement.executeQuery();
	        while (result.next()) {
	                Blob blob = result.getBlob("data");
	                InputStream inputStream = blob.getBinaryStream();
	                otvet=inputStream;
	            }
		}
		catch(Exception ecv) {
			System.out.println("Poshlo po pizde");
	    }
		finally {
		    // it is a good idea to release
		    // resources in a finally{} block
		    // in reverse-order of their creation
		    // if they are no-longer needed

		    if (result != null) {
		        try {
		            result.close();
		        } catch (SQLException sqlEx) { } // ignore

		        result = null;
		    }

		    if (statement != null) {
		        try {
		            statement.close();
		        } catch (SQLException sqlEx) { } // ignore

		        statement = null;
		    }
		}
		return otvet;
	}

	public static InputStream[] createInputStreamForInjury() {
		InputStream [] otvet=new InputStream[7];
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection conn = null;
		try {
			 URI jdbUri = new URI(System.getenv("JAWSDB_URL"));
			 String username = jdbUri.getUserInfo().split(":")[0];
			 String password = jdbUri.getUserInfo().split(":")[1];
			 String port = String.valueOf(jdbUri.getPort());
			 String jdbUrl = "jdbc:mysql://" + jdbUri.getHost() + ":" + port + jdbUri.getPath();
			 conn=DriverManager.getConnection(jdbUrl, username, password);
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		catch (Exception ex2) {
		    // handle any errors
		    System.out.println(ex2);
		}
		
		ResultSet result=null;
		PreparedStatement statement=null;
		try {
		    //create inputstream
	        String sql = "SELECT data FROM LastDay order by id desc limit 7";
	        statement = conn.prepareStatement(sql);
	        
	        result = statement.executeQuery();
	        int knt=6;
	        while (result.next()) {
	                Blob blob = result.getBlob("data");
	                InputStream inputStream = blob.getBinaryStream();
	                otvet[knt]=inputStream;
	                knt--;
	            }
		}
		catch(Exception ecv) {
			System.out.println("Poshlo po pizde");
	    }
		finally {
		    // it is a good idea to release
		    // resources in a finally{} block
		    // in reverse-order of their creation
		    // if they are no-longer needed

		    if (result != null) {
		        try {
		            result.close();
		        } catch (SQLException sqlEx) { } // ignore

		        result = null;
		    }

		    if (statement != null) {
		        try {
		            statement.close();
		        } catch (SQLException sqlEx) { } // ignore

		        statement = null;
		    }
		}
		return otvet;
	}
	
	public static void setInjury(File fl) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection conn = null;			
		try {
			 URI jdbUri = new URI(System.getenv("JAWSDB_URL"));
			 String username = jdbUri.getUserInfo().split(":")[0];
			 String password = jdbUri.getUserInfo().split(":")[1];
			 String port = String.valueOf(jdbUri.getPort());
			 String jdbUrl = "jdbc:mysql://" + jdbUri.getHost() + ":" + port + jdbUri.getPath();
			 conn=DriverManager.getConnection(jdbUrl, username, password);		    		    
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		catch (Exception ex2) {
		    // handle any errors
		    System.out.println(ex2);
		}
		
		Statement stmt = null;
		ResultSet rs = null;

		try {
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery("SHOW tables like 'LastDay'");
		    
		    if(!rs.next())
		    	stmt.execute("CREATE TABLE LastDay (id INT NOT NULL PRIMARY KEY,data BLOB)");
		    
		    //save file        
		    InputStream inputStream=null;
			try {
				inputStream = new FileInputStream(fl);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    String sql = "REPLACE INTO LastDay (id,data) values (0,?)";
		    PreparedStatement statement = conn.prepareStatement(sql);
		    statement.setBlob(1, inputStream);
		    statement.executeUpdate();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		finally {
		    // it is a good idea to release
		    // resources in a finally{} block
		    // in reverse-order of their creation
		    // if they are no-longer needed

		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException sqlEx) { } // ignore

		        rs = null;
		    }

		    if (stmt != null) {
		        try {
		            stmt.close();
		        } catch (SQLException sqlEx) { } // ignore

		        stmt = null;
		    }
		}
	}
	
	public static void saveFileForInjury() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection conn = null;			
		try {
			 URI jdbUri = new URI(System.getenv("JAWSDB_URL"));
			 String username = jdbUri.getUserInfo().split(":")[0];
			 String password = jdbUri.getUserInfo().split(":")[1];
			 String port = String.valueOf(jdbUri.getPort());
			 String jdbUrl = "jdbc:mysql://" + jdbUri.getHost() + ":" + port + jdbUri.getPath();
			 conn=DriverManager.getConnection(jdbUrl, username, password);		    		    
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		catch (Exception ex2) {
		    // handle any errors
		    System.out.println(ex2);
		}
		
		Statement stmt = null;
		ResultSet rs = null;

		try {
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery("SHOW tables like 'LastDay'");
		    
		    if(!rs.next())
		    	stmt.execute("CREATE TABLE LastDay (id INT NOT NULL PRIMARY KEY,data BLOB)");
		    
		    //save file
		    InputStream inputStream = getInjury();
		    
		    String sql = "INSERT INTO LastDay (id,data) values ("+new Date().getMonth()+""+new Date().getDate()+""+new Date().getMinutes()+""+new Date().getSeconds()+",?)";
		    PreparedStatement statement = conn.prepareStatement(sql);
		    statement.setBlob(1, inputStream);
		    statement.executeUpdate();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		finally {
		    // it is a good idea to release
		    // resources in a finally{} block
		    // in reverse-order of their creation
		    // if they are no-longer needed

		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException sqlEx) { } // ignore

		        rs = null;
		    }

		    if (stmt != null) {
		        try {
		            stmt.close();
		        } catch (SQLException sqlEx) { } // ignore

		        stmt = null;
		    }
		}
	}
	
	public static void refreshInjury(String nameurl) {
		if(new Date().getHours()>15 && new Date().getHours()<23) {
			try {
					URL url = new URL("http://"+nameurl+"/admininjury.jsp");
					HttpURLConnection e = (HttpURLConnection) url.openConnection();
					int responseCode = e.getResponseCode();
					System.out.println("RefreshInjury"+new Date().getHours() + ":" + new Date().getMinutes() + " " + responseCode);
			} catch (Exception e) {
			}
		}
	}

	
	static File f = new File("kesh.xml");

	static long DURATION=7200000L;
	
	private static String readFeed(URL url) {
		String jsontext = "";
		int i;
       	int pop=0;
       	HttpURLConnection e=null;
       	int responseCode=0;
        while(true) {
        	try {
    			e = (HttpURLConnection) url.openConnection();
    			responseCode = e.getResponseCode();
				if (responseCode == 200) {
					BufferedInputStream gson = new BufferedInputStream(e.getInputStream());
					ArrayList m = new ArrayList();
	
					for (i = gson.read(); i != -1; i = gson.read()) {
						m.add(Character.valueOf((char) i));
					}
	
					char[] key = new char[m.toArray().length];
					int list = 0;
	
					for (Iterator id = m.iterator(); id.hasNext(); ++list) {
						Object j = id.next();
						key[list] = ((Character) j).charValue();
					}
	
					jsontext = new String(key);
					break;
				} else {
					pop++;
					if(pop==3)
						return "";
					Thread.currentThread().sleep(1000);
				}
        	}
        	catch(Exception exs) {
				pop++;
				if(pop==3)
					return "";
				try {
					Thread.currentThread().sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        }
        return jsontext;
	}
	
	public static Map<String, List<Game>> parse(URL url, String month, String date) {
		LinkedHashMap<String, List<Game>> map = new LinkedHashMap();
		int i;
		Gson var64 = new Gson();
		Matchi games = (Matchi) var64.fromJson(readFeed(url), Matchi.class);
		HashMap<Integer, Game> kesh = new HashMap();
		Map<Integer, Game> keshout = new HashMap();
		if (f.exists())
			keshout = deserialization(f);
		
		for (i = 0; i < games.sportItem.tournaments.length; ++i) {
			String var66 = games.sportItem.tournaments[i].category.name + " "+ games.sportItem.tournaments[i].tournament.name;
			ArrayList listGames = new ArrayList();
			for (int var68 = 0; var68 < games.sportItem.tournaments[i].events.length; ++var68) {
				int idGame = games.sportItem.tournaments[i].events[var68].id;
				boolean stat = games.sportItem.tournaments[i].hasEventPlayerStatistics;
				//form time
				String hourfortime = "";
				String minfortime = "";
				int hft = new Date((games.sportItem.tournaments[i].events[var68].startTimestamp * 1000L + DURATION)).getHours();
				int mft = new Date((games.sportItem.tournaments[i].events[var68].startTimestamp * 1000L + DURATION)).getMinutes();
				if (hft < 10) {
					hourfortime = "0" + hft;
				} else {
					hourfortime = "" + hft;
				}
				if (mft < 10) {
					minfortime = "0" + mft;
				} else {
					minfortime = "" + mft;
				}
				String time = hourfortime + ":" + minfortime;
				
				String team1 = games.sportItem.tournaments[i].events[var68].homeTeam.name;
				String team2 = games.sportItem.tournaments[i].events[var68].awayTeam.name;
				String htScore = games.sportItem.tournaments[i].events[var68].homeScore.current + "-"+ games.sportItem.tournaments[i].events[var68].awayScore.current;
				String ftScore = games.sportItem.tournaments[i].events[var68].homeScore.normaltime + "-"+ games.sportItem.tournaments[i].events[var68].awayScore.normaltime;
				if ("null-null".equals(ftScore)) {
					ftScore = "-";
				}

				if ("null-null".equals(htScore)) {
					htScore = "-";
				}

				HashMap<Integer, Player> myel1 = new HashMap();
				HashMap<Integer, Player> myelred1 = new HashMap();
				HashMap<Integer, Player> mred1 = new HashMap();
				HashMap<Integer, Player> msubs1 = new HashMap();
				HashMap<Integer, Player> myel2 = new HashMap();
				HashMap<Integer, Player> myelred2 = new HashMap();
				HashMap<Integer, Player> mred2 = new HashMap();
				HashMap<Integer, Player> msubs2 = new HashMap();

				//esli est stata i nachalo matcha v etot den				
				long startday= new Date(new Date().getYear(), Integer.valueOf(month)-1,Integer.valueOf(date)).getTime();
				long endday=new Date(new Date().getYear(),Integer.valueOf(month)-1,Integer.valueOf(date), 23, 59, 60).getTime();
				
				if (stat && ((games.sportItem.tournaments[i].events[var68].startTimestamp * 1000L + DURATION) > startday) && ((games.sportItem.tournaments[i].events[var68].startTimestamp * 1000L + DURATION) <endday)) {
					System.out.println(team1+" - "+team2);
					Game igra = new Game(time, team1, team2, ftScore, ftScore, myel1, myelred1, mred1, msubs1, myel2, myelred2, mred2, msubs2);
                    //esli game zakonchilsya i proshlo bolshe 16 minut ot konca i v keshe est eta game ILI game ne nachalsy, to esli v keshe est game berem ee iz kesha i idem dalshe
					if ((("finished".equals(games.sportItem.tournaments[i].events[var68].status.type)) && (((new Date().getTime() / 10000) - games.sportItem.tournaments[i].events[var68].startTimestamp) > 1000L) && keshout.containsKey(idGame))
							|| "notstarted".equals(games.sportItem.tournaments[i].events[var68].status.type)) {
						if (keshout.containsKey(idGame)) {
							igra = keshout.get(idGame);
						}
					} else {
						URL url2=null;
						try {
							url2 = new URL("https://www.sofascore.com/event/" + idGame + "/json?_=" + (new Date()).getTime() / 10000L);
						}
						catch(Exception e) {
							e.printStackTrace();
						}
						Gson var71 = new Gson();
						Eventes var72 = (Eventes) var71.fromJson(readFeed(url2), Eventes.class);
						
						for (int h = 0; h < var72.incidents.length; ++h) {
							try {
								URL url3=null;
								int mt;
								int ms;
								int min;
								int goal;
								int yel;
								int yelred;
								int red;
								HttpURLConnection e1;
								String jsontext3;
								BufferedInputStream in3;
								ArrayList str3;
								int b3;
								char[] chars3;
								int d;
								Object gson3;
								Iterator st;
								Value e2;
								int var54;
								int var55;
								Value[] var56;
								Gson var73;
								Stat var74;
								//esli zamena po travme
								if ("substitution".equals(var72.incidents[h].incidentType) && var72.incidents[h].injury) {
									try {
										url3 = new URL("https://www.sofascore.com/player/"+ var72.incidents[h].playerOut.id + "/statistics/json?_="+ (new Date()).getTime() / 10000L);
									} catch (Exception e) {
										e.printStackTrace();
									}
									mt = 0;
									ms = 0;
									min = 0;
									goal = 0;
									yel = 0;
									yelred = 0;
									red = 0;
									var73 = new Gson();
									var74 = (Stat) var73.fromJson(readFeed(url3), Stat.class);
	
									var56 = var74.statistics[0].seasons[0].statistics;
									var55 = var74.statistics[0].seasons[0].statistics.length;
									for (var54 = 0; var54 < var55; ++var54) {
										e2 = var56[var54];
										if ("Matches".equals(e2.groupName)) {
											mt = e2.matchesTotal;
											ms = e2.matchesStarting;
											min = e2.minutesPerGame * mt;
										}								
										if ("Goals".equals(e2.groupName)) {
											goal = e2.goals;
										}									
										if ("Cards".equals(e2.groupName)) {
											yel = e2.yellowCards;
											yelred = e2.yellowRedCards;
											red = e2.redCards;
										}
									}
									//esli s pervoy comandy
									if (var72.incidents[h].playerTeam == 1) {
										msubs1.put(Integer.valueOf(var72.incidents[h].playerOut.id),
										new Player(var72.incidents[h].playerOut.id,
										var72.incidents[h].playerOut.name, "", min, mt, ms,goal, yel, yelred, red));
									} else {
										msubs2.put(Integer.valueOf(var72.incidents[h].playerOut.id),
										new Player(var72.incidents[h].playerOut.id,
										var72.incidents[h].playerOut.name, "", min, mt, ms,goal, yel, yelred, red));
									}
								}
								else 
									//esli kartochka
									if ("card".equals(var72.incidents[h].incidentType)) {
										try {
											url3 = new URL("https://www.sofascore.com/player/"+ var72.incidents[h].player.id + "/statistics/json?_="+ (new Date()).getTime() / 10000L);
										} catch (Exception e) {
											e.printStackTrace();
										}
										mt = 0;
										ms = 0;
										min = 0;
										goal = 0;
										yel = 0;
										yelred = 0;
										red = 0;
										var73 = new Gson();
										var74 = (Stat) var73.fromJson(readFeed(url3), Stat.class);
										var56 = var74.statistics[0].seasons[0].statistics;
										var55 = var74.statistics[0].seasons[0].statistics.length;
										for (var54 = 0; var54 < var55; ++var54) {
											e2 = var56[var54];
											if ("Matches".equals(e2.groupName)) {
												mt = e2.matchesTotal;
												ms = e2.matchesStarting;
												min = e2.minutesPerGame * mt;
											}
									
											if ("Goals".equals(e2.groupName)) {
												goal = e2.goals;
											}
									
											if ("Cards".equals(e2.groupName)) {
												yel = e2.yellowCards;
												yelred = e2.yellowRedCards;
												red = e2.redCards;
											}
										}
										
										//esli igrok 1 komandy
										if (var72.incidents[h].playerTeam == 1) {
											if ("Yellow".equals(var72.incidents[h].type)) {
												myel1.put(Integer.valueOf(var72.incidents[h].player.id),new Player(var72.incidents[h].player.id,var72.incidents[h].player.name, "", min, mt, ms,goal, yel, yelred, red));
											} else if ("YellowRed".equals(var72.incidents[h].type)) {
												myelred1.put(Integer.valueOf(var72.incidents[h].player.id),new Player(var72.incidents[h].player.id,var72.incidents[h].player.name, "", min, mt, ms,goal, yel, yelred, red));
											} else if ("Red".equals(var72.incidents[h].type)) {
												mred1.put(Integer.valueOf(var72.incidents[h].player.id),new Player(var72.incidents[h].player.id,var72.incidents[h].player.name, "", min, mt, ms,goal, yel, yelred, red));
											}
										}
										else if ("Yellow".equals(var72.incidents[h].type)) {
											myel2.put(Integer.valueOf(var72.incidents[h].player.id),new Player(var72.incidents[h].player.id,var72.incidents[h].player.name, "", min, mt, ms,goal, yel, yelred, red));
										} else if ("YellowRed".equals(var72.incidents[h].type)) {
											myelred2.put(Integer.valueOf(var72.incidents[h].player.id),new Player(var72.incidents[h].player.id,var72.incidents[h].player.name, "", min, mt, ms,goal, yel, yelred, red));
										} else if ("Red".equals(var72.incidents[h].type)) {
											mred2.put(Integer.valueOf(var72.incidents[h].player.id),new Player(var72.incidents[h].player.id,var72.incidents[h].player.name, "", min, mt, ms,goal, yel, yelred, red));
										}
									//konec esli kartochka
								}
							}
							catch(Exception ex) {}
							//konec prohoda incident
						   }
						//konec ne v keshe
						}
					igra = new Game(time, team1, team2, ftScore, ftScore, myel1, myelred1, mred1, msubs1, myel2, myelred2, mred2, msubs2);
					listGames.add(igra);
					kesh.put(idGame, igra);
				//konec esli match popadaet v vremenye ramki
				}
             //konec eventsam
			}			
			if (listGames.size() != 0) {
				map.put(var66, listGames);
			}
		//konec tourniram
		}
		serialization(kesh, f);
		return map;
	}

	public static void serialization(Map<Integer, Game> m, File f) {
		FileOutputStream fOut = null;
		ObjectOutputStream oOut = null;
		try {
			fOut = new FileOutputStream(f);
			oOut = new ObjectOutputStream(fOut);
			oOut.writeObject(m);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				oOut.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	

	public static Map<Integer, Game> deserialization(File f) {
		FileInputStream fInput = null;
		ObjectInputStream oInput = null;
		Map<Integer, Game> q = null;
		try {
			fInput = new FileInputStream(f);
			oInput = new ObjectInputStream(fInput);
			q = (Map<Integer, Game>) oInput.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return q;
	}

}

