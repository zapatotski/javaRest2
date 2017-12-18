package busyShedule.model.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import busyShedule.model.bizmodel.Game;
import busyShedule.model.service.RefreshManager;

public class GameDao {

	private File f = new File("team_games.data");

	public Map<Integer, List<Game>> getGames() {
		if(!f.exists())
			new RefreshManager().updateData();
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
	        String sql = "SELECT data FROM Shedule WHERE id=0";
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
		    
		    if(conn != null) {
			    try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}
		return (Map<Integer, List<Game>>) deserialization(otvet);
	}

	public void setGames(Map<Integer, List<Game>> m) {
		serialization(f, m);
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
		    rs = stmt.executeQuery("SHOW tables like 'Shedule'");
		    
		    if(!rs.next())
		    	stmt.execute("CREATE TABLE Shedule (id INT NOT NULL PRIMARY KEY,data LONGBLOB)");
		    
		    //save file        
		    InputStream inputStream=null;
			try {
				inputStream = new FileInputStream(f);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    String sql = "REPLACE INTO Shedule (id,data) values (0,?)";
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
		    
		    if(conn != null) {
			    try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}
	}

	private void serialization(File f, Object m) {
		FileOutputStream fOut = null;
		ObjectOutputStream oOut = null;
		try {
			fOut = new FileOutputStream(f);
			oOut = new ObjectOutputStream(fOut);
			oOut.writeObject(m);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Object deserialization(InputStream fInput) {
		ObjectInputStream oInput = null;
		Object result = null;
		try {
			oInput = new ObjectInputStream(fInput);
			result = oInput.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

}

