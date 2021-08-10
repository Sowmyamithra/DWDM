import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionProgram
{
	public static void main(String[] args)
	{
		String Driver = "org.postgresql.Driver";
		try{
			Class.forName(Driver);
			Connection c = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/" , "postgres" , "@Nanna55");
			if(c != null){
				System.out.println("CONNECTION ESTABLISHED");
			}
			else{
				System.out.println("FAILED TO ESTABLISH CONNECTION");
			}			
		}
		catch(SQLException e){
			System.err.format("SQL State : %s\n%s" , e.getSQLState() , e.getMessage());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
} 