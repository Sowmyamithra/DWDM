import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Integration{
	public static void main(String[] args){
		String Driver = "org.postgresql.Driver";
		try{
			Class.forName(Driver);
			Connection c = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/DI" , "postgres" , "@Nanna55");
			if(c != null){
				System.out.println("CONNECTION ESTABLISHED");
				String q1 = "select id , gender from table1";
				String q2 = "select id , gender from table2";
				String q3 = "create table result(id integer , gender char(20))";
				Statement st1 = c.createStatement();
				Statement st2 = c.createStatement();
				Statement st3 = c.createStatement();
				ResultSet rs1 = st1.executeQuery(q1);
				ResultSet rs2 = st2.executeQuery(q2);
				st3.executeUpdate(q3);
				while(rs1.next()){
					int id = rs1.getInt("id");
					int g = rs1.getInt("gender");
					String ans = (g == 1) ? "Male" : "Female";
					PreparedStatement ps1 = c.prepareStatement("insert into result(id , gender) values(? , ?)");
					ps1.setInt(1 , id);
					ps1.setString(2 , ans);
					ps1.executeUpdate();	
				}
				rs1.close();
				while(rs2.next()){
					int id = rs2.getInt("id");
					String g = rs2.getString("gender");
					String ans = g.equals("M") ? "Male" : "Female";
					PreparedStatement ps1 = c.prepareStatement("insert into result(id , gender) values(? , ?)");
					ps1.setInt(1 , id);
					ps1.setString(2 , ans);
					ps1.executeUpdate();
				}
				rs2.close();
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