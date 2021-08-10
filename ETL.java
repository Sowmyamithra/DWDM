import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ETL {
	public static void main(String args[])
	{
			String driver = "org.postgresql.Driver";
			try{
			
				Class.forName(driver);
				Connection c = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/DP","postgres","@Nanna55");
					if(c != null){
						System.out.println("Connection established");
						String q1 = "select id , weight from table11";
						String q2 = "select height from table12";
						String q3 = "create table BMI(id integer , index real)";
						Statement st1 = c.createStatement();
						Statement st2 = c.createStatement();
						Statement st3 = c.createStatement();
						ResultSet rs1 = st1.executeQuery(q1);
						ResultSet rs2 = st2.executeQuery(q2);
						st3.executeUpdate(q3);
						while(rs1.next() && rs2.next()){
							int id = rs1.getInt("id");
							double wt = rs1.getDouble("weight");
							double ht = rs2.getDouble("height");
							double bmi = wt / (ht * ht * 0.3048 * 0.3048);
							PreparedStatement ps1 = c.prepareStatement("insert into BMI(id , index) values(? , ?)");
							ps1.setInt(1 , id);
							ps1.setDouble(2 , bmi);
							ps1.executeUpdate();
						}
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