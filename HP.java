import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HP{
	public static void main(String args[])
	{
			String driver = "org.postgresql.Driver";
			try{
			
				Class.forName(driver);
				Connection c = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/DP","postgres","@Nanna55");
					if(c != null){
						System.out.println("Connection established");
						String q1 = "select id , weight from student1";
						Statement st1 = c.createStatement();
						ResultSet rs1 = st1.executeQuery(q1);
						String q2 = "create table table1(id integer , weight real)";
						String q3 = "create table table2(id integer , weight real)";
						Statement st2 = c.createStatement();
						Statement st3 = c.createStatement();
						st2.executeUpdate(q2);
						st3.executeUpdate(q3);
						while(rs1.next()){
							int id = rs1.getInt("id");
							double wt = rs1.getDouble("weight");
							if(wt > 50){
								PreparedStatement ps1 = c.prepareStatement("insert into table1(id , weight) values(? , ?)");
								ps1.setInt(1 , id);
								ps1.setDouble(2 , wt);
								ps1.executeUpdate();
							}
							else{
								PreparedStatement ps1 = c.prepareStatement("insert into table2(id , weight) values(? , ?)");
								ps1.setInt(1 , id);
								ps1.setDouble(2 , wt);
								ps1.executeUpdate();
							}
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