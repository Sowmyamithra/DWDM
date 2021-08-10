import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Cleaning
{
	public static void main(String[] args)
	{	
		String Driver = "org.postgresql.Driver";
		try{
			Class.forName(Driver);
			Connection c = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/DC" , "postgres" , "@Nanna55");
			if(c != null){
				System.out.println("CONNECTION ESTABLISHED");
				String q1 = "select avg(weight) from weight";
				Statement st1 = c.createStatement();
				ResultSet rs1 = st1.executeQuery(q1);
				double avg = 0.0;
				while(rs1.next()){
					avg = rs1.getDouble(1);
				}
				rs1.close();
				q1 = "select weight from weight";
				rs1 = st1.executeQuery(q1);
				while(rs1.next()){
					PreparedStatement ps1 = c.prepareStatement("update weight set weight = ? where weight is null");
					ps1.setDouble(1 , avg);
					ps1.executeUpdate();
				}
				rs1.close();
			}
			else{
				System.out.println("FAILED TO ESTABLISH CONNECTION");
			}
		}
		catch(SQLException e){
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}