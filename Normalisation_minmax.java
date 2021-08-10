import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Normalisation_minmax{
	public static void main(String args[])
	{
			String driver = "org.postgresql.Driver";
			try{
			
				Class.forName(driver);
				Connection c = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/DN","postgres","@Nanna55");
					if(c != null){
						System.out.println("Connection established");
						String q1 = "select max(salary) from employee";
						String q2 = "select min(salary) from employee";
						String q3 = "create table result1(id integer , salary real)";
						Statement st1 = c.createStatement();
						Statement st2 = c.createStatement();
						Statement st3 = c.createStatement();
						ResultSet rs1 = st1.executeQuery(q1);
						ResultSet rs2 = st2.executeQuery(q2);
						st3.executeUpdate(q3);
						double min = 0.0 , max = 0.0 , min1 = 0.0 , max1 = 1.0;
						while(rs1.next()){
							max = rs1.getDouble(1); 
						}
						rs1.close(); 
						while(rs2.next()){
							min = rs2.getDouble(1); 
						}
						rs2.close(); 
						q1 = "select id , salary from employee";
						rs1 = st1.executeQuery(q1);
						while(rs1.next()){
							int id = rs1.getInt("id");
							double s = rs1.getDouble("salary");
							PreparedStatement ps1 = c.prepareStatement("insert into result1(id , salary) values(? , ?)");
							double new_s = (((s - min) / (max - min) ) * (max1 - min1)) + min1;	
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