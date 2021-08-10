import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SS{
	public static void main(String args[])
	{
			String driver = "org.postgresql.Driver";
			try{
			
				Class.forName(driver);
				Connection c = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/DS","postgres","@Nanna55");
					if(c != null){
						System.out.println("Connection established");
						String q1 = "create table student(sid integer , sname char(20) , primary key(sid))";
						String q2 = "create table course(cid integer , cname char(20) , primary key(cid))";
						String q3 = "create table student_details(sid integer , cid integer , primary key(sid , cid) , foreign key(sid) references student(sid) , foreign key(cid) references course(cid))"; 
						Statement st1 = c.createStatement();
						Statement st2 = c.createStatement();
						Statement st3 = c.createStatement();
						st1.executeUpdate(q1);
						st2.executeUpdate(q2);
						st3.executeUpdate(q3);
						PreparedStatement ps1 = c.prepareStatement("insert into student(sid , sname) values(? , ?)");
						PreparedStatement ps2 = c.prepareStatement("insert into course(cid , cname) values(? , ?)");
						PreparedStatement ps3 = c.prepareStatement("insert into student_details(sid , cid) values(? , ?)");
						int ct = 2;
						Scanner sc = new Scanner(System.in);
						while(ct > 0){
							int sid , cid;
							String sname , cname;
							System.out.println("Enter sid , sname : ");
							sid = sc.nextInt();sc.nextLine();sname = sc.nextLine();sc.nextLine();
							System.out.println("Enter cid , cname : ");
							cid = sc.nextInt();sc.nextLine();cname = sc.nextLine();sc.nextLine();
							ps1.setInt(1 , sid);
							ps1.setString(2 , sname);
							ps1.executeUpdate();
							ps2.setInt(1 , cid);
							ps2.setString(2 , cname);
							ps2.executeUpdate();
							ps3.setInt(1 , sid);
							ps3.setInt(2 , cid);
							ps3.executeUpdate();
							ct--;	
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