import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Normalisation_zscore{
	public static void main(String args[])
	{
			String driver = "org.postgresql.Driver";
			try{
			
				Class.forName(driver);
				Connection c = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/DN","postgres","@Nanna55");
					if(c != null){
						System.out.println("Connection established");
						String q1 = "select avg(salary) from employee";
						String q2 = "create table result2(id integer , salary real)";
						Statement st1 = c.createStatement();
						Statement st2 = c.createStatement();
						ResultSet rs1 = st1.executeQuery(q1);
						st2.executeUpdate(q2);
						double mean = 0.0;
						double sd = 0.0;
						int ct = 0;
						while(rs1.next()){
							mean = rs1.getDouble(1); 
						}
						rs1.close();
						q1 = "select salary from employee";
						rs1 = st1.executeQuery(q1);
						while(rs1.next()){
							double x = rs1.getDouble(1);
							sd = sd + (x - mean) * (x - mean);
							ct++;	
						}
						rs1.close();
						sd = Math.sqrt(sd / ct - 1);
						q1 = "select id , salary from employee";
						rs1 = st1.executeQuery(q1);
						while(rs1.next()){
							int id = rs1.getInt("id");
							double s = rs1.getDouble("salary");
							double new_s = (s - sd) / mean;
							PreparedStatement ps1 = c.prepareStatement("insert into result2(id , salary) values(? , ?)");
							ps1.setInt(1 , id);
							ps1.setDouble(2 , new_s);
							ps1.executeUpdate();
						}
						rs1.close();
					}
			
					else{
						System.out.println("Failed to estabish connection");
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