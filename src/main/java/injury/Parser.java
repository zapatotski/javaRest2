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
import java.net.URL;
import java.net.URI;
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
	
	public static void createFilesForInjury() {
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
			//delete old files
			File dir = new File(".");
			File[] files = dir.listFiles(new FilenameFilter() {
			    public boolean accept(File dir, String name) {
			        return name.toLowerCase().endsWith(".inj");
			    }
			});
			for(File fd:files)
				fd.delete();
		    //create files
	        String sql = "SELECT data FROM LastDay order by id desc limit 7";
	        statement = conn.prepareStatement(sql);
	        
	        result = statement.executeQuery();
	        int knt=7;
	        while (result.next()) {
	        	    String filePath =knt+".inj";
	                Blob blob = result.getBlob("data");
	                InputStream inputStream = blob.getBinaryStream();
	                OutputStream outputStream = new FileOutputStream(filePath);
	 
	                int bytesRead = -1;
	                byte[] buffer = new byte[4096];
	                while ((bytesRead = inputStream.read(buffer)) != -1) {
	                    outputStream.write(buffer, 0, bytesRead);
	                }
	 
	                inputStream.close();
	                outputStream.close();
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
		File fl=new File("matchiinjury.xml");
		if(!fl.exists()) {
	    	String month="";
	    	String date="";
	    	int m=new Date().getMonth()+1;
	    	int d=new Date().getDate();
	    	if(m<10){
	    		month="0"+m;
	    	}
	    	else{
	    		month=""+m;
	    	}
	    	if(d<10){
	    		date="0"+d;
	    	}
	    	else{
	    		date=""+d;
	    	}
	        String strUrl="https://www.sofascore.com/football//2017-"+month+"-"+date+"/json?_="+new Date().getTime()/10000;
	        URL url=null;
			try {
				url = new URL(strUrl);
		        serialization2(parse(url),fl);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		
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
		    	stmt.execute("CREATE TABLE LastDay (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,data BLOB)");
		    
		    //save file
		    InputStream inputStream = null;
			try {
				inputStream = new FileInputStream(fl);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    String sql = "INSERT INTO LastDay (data) values (?)";
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
	
	public static void refreshShedule(String nameurl) {
		try {
		URL url = new URL("http://"+nameurl+"/api/shedule/refresh");
		HttpURLConnection e = (HttpURLConnection) url.openConnection();
		int responseCode = e.getResponseCode();
		System.out.println("RefreshShedule - "+new Date().getHours() + ":" + new Date().getMinutes() + " " + responseCode);	
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void refreshInjury(String nameurl) {
		try {
				URL url = new URL("http://"+nameurl+"/admininjury.jsp");
				HttpURLConnection e = (HttpURLConnection) url.openConnection();
				int responseCode = e.getResponseCode();
				System.out.println("RefreshInjury"+new Date().getHours() + ":" + new Date().getMinutes() + " " + responseCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static File f = new File("kesh.xml");

	public static Map<String, List<Game>> parse(URL url) {
		LinkedHashMap<String, List<Game>> map = new LinkedHashMap();

		try {

			HttpURLConnection e = (HttpURLConnection) url.openConnection();
			int responseCode = e.getResponseCode();
			String jsontext = "";
			int i;

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
			} else {
				System.out.println("someproblem1");
			}

			Gson var64 = new Gson();
			Matchi var65 = (Matchi) var64.fromJson(jsontext, Matchi.class);

			HashMap<Integer, Game> kesh = new HashMap();
			Map<Integer, Game> keshout = new HashMap();
			if (f.exists())
				keshout = deserialization(f);

			int knt=0;
			int kntpr=0;
			for (i = 0; i < var65.sportItem.tournaments.length; ++i) {

				String var66 = var65.sportItem.tournaments[i].category.name + " "
						+ var65.sportItem.tournaments[i].tournament.name;
				ArrayList var67 = new ArrayList();
				
	
				
				for (int var68 = 0; var68 < var65.sportItem.tournaments[i].events.length; ++var68) {
					int var69 = var65.sportItem.tournaments[i].events[var68].id;
					boolean stat = var65.sportItem.tournaments[i].hasEventPlayerStatistics;
					String hourfortime = "";
					String minfortime = "";
					int hft = new Date(
							(var65.sportItem.tournaments[i].events[var68].startTimestamp * 1000L + 10800000L))
									.getHours();
					int mft = new Date(
							(var65.sportItem.tournaments[i].events[var68].startTimestamp * 1000L + 10800000L))
									.getMinutes();
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
					String team1 = var65.sportItem.tournaments[i].events[var68].homeTeam.name;
					String team2 = var65.sportItem.tournaments[i].events[var68].awayTeam.name;
					String htScore = var65.sportItem.tournaments[i].events[var68].homeScore.current + "-"
							+ var65.sportItem.tournaments[i].events[var68].awayScore.current;
					String ftScore = var65.sportItem.tournaments[i].events[var68].homeScore.normaltime + "-"
							+ var65.sportItem.tournaments[i].events[var68].awayScore.normaltime;
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

					System.out.println("Pered filtrom:"+var69);
					if (stat && ((var65.sportItem.tournaments[i].events[var68].startTimestamp * 1000L
							+ 10800000L) > (new Date((new Date()).getYear(), (new Date()).getMonth(),
									(new Date()).getDate())).getTime())
							&& ((var65.sportItem.tournaments[i].events[var68].startTimestamp * 1000L
									+ 10800000L) < (new Date((new Date()).getYear(), (new Date()).getMonth(),
											(new Date()).getDate(), 23, 59, 60)).getTime())) {
						
						System.out.println(kntpr+") Proshel filtr:"+var69);
						kntpr++;
						
						Game var70 = new Game(time, team1, team2, ftScore, ftScore, myel1, myelred1, mred1, msubs1,
								myel2, myelred2, mred2, msubs2);

						if ((("finished".equals(var65.sportItem.tournaments[i].events[var68].status.type))
								&& (((new Date().getTime() / 1000)
										- var65.sportItem.tournaments[i].events[var68].startTimestamp) > 10000L)
								&& keshout.containsKey(var69))
								|| "notstarted".equals(var65.sportItem.tournaments[i].events[var68].status.type)) {
							
							if (keshout.containsKey(var69)) {
								System.out.println("V keshe: "+knt+") "+var69);
								var70 = keshout.get(var69);
								knt++;
							}

						} else {

							URL url2 = new URL("https://www.sofascore.com/event/" + var69 + "/json?_="
									+ (new Date()).getTime() / 10000L);

							try {
								e = (HttpURLConnection) url2.openConnection();
								responseCode = e.getResponseCode();
								String g = "";
								if (responseCode == 200) {
									BufferedInputStream in = new BufferedInputStream(e.getInputStream());
									ArrayList str = new ArrayList();

									for (int b = in.read(); b != -1; b = in.read()) {
										str.add(Character.valueOf((char) b));
									}

									char[] chars = new char[str.toArray().length];
									int x = 0;

									for (Iterator ev = str.iterator(); ev.hasNext(); ++x) {
										Object gson2 = ev.next();
										chars[x] = ((Character) gson2).charValue();
									}

									g = new String(chars);
									Gson var71 = new Gson();
									Eventes var72 = (Eventes) var71.fromJson(g, Eventes.class);

									for (int h = 0; h < var72.incidents.length; ++h) {
										try {
											URL url3;
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
											if ("substitution".equals(var72.incidents[h].incidentType)
													&& var72.incidents[h].injury) {
												url3 = new URL("https://www.sofascore.com/player/"
														+ var72.incidents[h].playerOut.id + "/statistics/json?_="
														+ (new Date()).getTime() / 10000L);
												mt = 0;
												ms = 0;
												min = 0;
												goal = 0;
												yel = 0;
												yelred = 0;
												red = 0;

												try {
													e1 = (HttpURLConnection) url3.openConnection();
													responseCode = e1.getResponseCode();
													jsontext3 = "";
													if (responseCode == 200) {
														in3 = new BufferedInputStream(e1.getInputStream());
														str3 = new ArrayList();

														for (b3 = in3.read(); b3 != -1; b3 = in3.read()) {
															str3.add(Character.valueOf((char) b3));
														}

														chars3 = new char[str3.toArray().length];
														d = 0;

														for (st = str3.iterator(); st.hasNext(); ++d) {
															gson3 = st.next();
															chars3[d] = ((Character) gson3).charValue();
														}

														jsontext3 = new String(chars3);
														var73 = new Gson();
														var74 = (Stat) var73.fromJson(jsontext3, Stat.class);

														try {
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
														} catch (Exception var59) {
															;
														}
													}
												} catch (Exception var60) {
													throw new RuntimeException(var60);
												}

												if (var72.incidents[h].playerTeam == 1) {
													msubs1.put(Integer.valueOf(var72.incidents[h].playerOut.id),
															new Player(var72.incidents[h].playerOut.id,
																	var72.incidents[h].playerOut.name, "", min, mt, ms,
																	goal, yel, yelred, red));
												} else {
													msubs2.put(Integer.valueOf(var72.incidents[h].playerOut.id),
															new Player(var72.incidents[h].playerOut.id,
																	var72.incidents[h].playerOut.name, "", min, mt, ms,
																	goal, yel, yelred, red));
												}
											} else if ("card".equals(var72.incidents[h].incidentType)) {
												url3 = new URL("https://www.sofascore.com/player/"
														+ var72.incidents[h].player.id + "/statistics/json?_="
														+ (new Date()).getTime() / 10000L);
												mt = 0;
												ms = 0;
												min = 0;
												goal = 0;
												yel = 0;
												yelred = 0;
												red = 0;

												try {
													e1 = (HttpURLConnection) url3.openConnection();
													responseCode = e1.getResponseCode();
													jsontext3 = "";
													if (responseCode == 200) {
														in3 = new BufferedInputStream(e1.getInputStream());
														str3 = new ArrayList();

														for (b3 = in3.read(); b3 != -1; b3 = in3.read()) {
															str3.add(Character.valueOf((char) b3));
														}

														chars3 = new char[str3.toArray().length];
														d = 0;

														for (st = str3.iterator(); st.hasNext(); ++d) {
															gson3 = st.next();
															chars3[d] = ((Character) gson3).charValue();
														}

														jsontext3 = new String(chars3);
														var73 = new Gson();
														var74 = (Stat) var73.fromJson(jsontext3, Stat.class);

														try {
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
														} catch (Exception var57) {

														}
													}
												} catch (Exception var58) {
													throw new RuntimeException(var58);
												}

												if (var72.incidents[h].playerTeam == 1) {
													if ("Yellow".equals(var72.incidents[h].type)) {
														myel1.put(Integer.valueOf(var72.incidents[h].player.id),
																new Player(var72.incidents[h].player.id,
																		var72.incidents[h].player.name, "", min, mt, ms,
																		goal, yel, yelred, red));
													} else if ("YellowRed".equals(var72.incidents[h].type)) {
														myelred1.put(Integer.valueOf(var72.incidents[h].player.id),
																new Player(var72.incidents[h].player.id,
																		var72.incidents[h].player.name, "", min, mt, ms,
																		goal, yel, yelred, red));
													} else if ("Red".equals(var72.incidents[h].type)) {
														mred1.put(Integer.valueOf(var72.incidents[h].player.id),
																new Player(var72.incidents[h].player.id,
																		var72.incidents[h].player.name, "", min, mt, ms,
																		goal, yel, yelred, red));
													}
												} else if ("Yellow".equals(var72.incidents[h].type)) {
													myel2.put(Integer.valueOf(var72.incidents[h].player.id),
															new Player(var72.incidents[h].player.id,
																	var72.incidents[h].player.name, "", min, mt, ms,
																	goal, yel, yelred, red));
												} else if ("YellowRed".equals(var72.incidents[h].type)) {
													myelred2.put(Integer.valueOf(var72.incidents[h].player.id),
															new Player(var72.incidents[h].player.id,
																	var72.incidents[h].player.name, "", min, mt, ms,
																	goal, yel, yelred, red));
												} else if ("Red".equals(var72.incidents[h].type)) {
													mred2.put(Integer.valueOf(var72.incidents[h].player.id),
															new Player(var72.incidents[h].player.id,
																	var72.incidents[h].player.name, "", min, mt, ms,
																	goal, yel, yelred, red));
												}
											}
										} catch (NullPointerException var61) {
											;
										}
									}
								} else {
									System.out.println("someproblem2");
								}
							} catch (Exception var62) {
								throw new RuntimeException(var62);
							}
							var70 = new Game(time, team1, team2, ftScore, ftScore, myel1, myelred1, mred1, msubs1,
									myel2, myelred2, mred2, msubs2);
						}

						var67.add(var70);
						kesh.put(var69, var70);

					}

				}

				if (var67.size() != 0) {
					map.put(var66, var67);
				}
			}

			serialization(kesh, f);

			return map;
		} catch (Exception var63) {
			throw new RuntimeException(var63);
		}
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
				fOut.close();
				oOut.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}
	
    public static void serialization2(Map<String,List<Game>> m, File f){
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
        finally{
        	try{
        		fOut.close();
        		oOut.close();
        	}
        	catch(Exception ex){
        		throw new RuntimeException(ex);
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
		finally{
        	try{
        		fInput.close();
        		oInput.close();
        	}
        	catch(Exception ex){
        		throw new RuntimeException(ex);
        	}
        }
		return q;
	}

}

