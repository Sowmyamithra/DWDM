import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VP{
	public static void main(String args[])
	{
			String driver = "org.postgresql.Driver";
			try{
			
				Class.forName(driver);
				Connection c = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/DP","postgres","@Nanna55");
					if(c != null){
						System.out.println("Connection established");
						String q1 = "select id , weight , height from student2";
						Statement st1 = c.createStatement();
						ResultSet rs1 = st1.executeQuery(q1);
						String q2 = "create table table11(id integer , weight real)";
						String q3 = "create table table12(id integer , height real)";
						Statement st2 = c.createStatement();
						Statement st3 = c.createStatement();
						st2.executeUpdate(q2);
						st3.executeUpdate(q3);
						while(rs1.next()){
							int id = rs1.getInt("id");
							double wt = rs1.getDouble("weight");
							double ht = rs1.getDouble("height");
							PreparedStatement ps1 = c.prepareStatement("insert into table11(id , weight) values(? , ?)");
							ps1.setInt(1 , id);
							ps1.setDouble(2 , wt);
							ps1.executeUpdate();
							PreparedStatement ps2 = c.prepareStatement("insert into table12(id , height) values(? , ?)");
							ps2.setInt(1 , id);
							ps2.setDouble(2 , ht);
							ps2.executeUpdate();
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