import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SFS{
	public static void main(String args[])
	{
			String driver = "org.postgresql.Driver";
			try{
			
				Class.forName(driver);
				Connection c = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/DS","postgres","@Nanna55");
					if(c != null){
						System.out.println("Connection established");
						String q1 = "create table address1(aid integer , city char(20) , primary key(aid))";
						String q2 = "create table student1(sid integer , sname char(20) , aid integer , primary key(sid) , foreign key(aid) references address1(aid))";
						String q3 = "create table course1(cid integer , cname char(20) , primary key(cid))";
						String q4 = "create table student_details1(sid integer , cid integer , primary key(sid , cid) , foreign key(sid) references student1(sid) , foreign key(cid) references course1(cid))"; 
						Statement st1 = c.createStatement();
						Statement st2 = c.createStatement();
						Statement st3 = c.createStatement();
						Statement st4 = c.createStatement();
						st1.executeUpdate(q1);
						st2.executeUpdate(q2);
						st3.executeUpdate(q3);
						st4.executeUpdate(q4);
						PreparedStatement ps1 = c.prepareStatement("insert into address1(aid , city) values(? , ?)");
						PreparedStatement ps2 = c.prepareStatement("insert into student1(sid , sname , aid) values(? , ? , ?)");
						PreparedStatement ps3 = c.prepareStatement("insert into course1(cid , cname) values(? , ?)");
						PreparedStatement ps4 = c.prepareStatement("insert into student_details1(sid , cid) values(? , ?)");
						int ct = 2;
						Scanner sc = new Scanner(System.in);
						while(ct > 0){
							int sid , cid , aid;
							String sname , cname , city;
							System.out.println("Enter aid , city : ");
							aid = sc.nextInt();sc.nextLine();city = sc.nextLine();sc.nextLine();
							System.out.println("Enter sid , sname : ");
							sid = sc.nextInt();sc.nextLine();sname = sc.nextLine();sc.nextLine();
							System.out.println("Enter cid , cname : ");
							cid = sc.nextInt();sc.nextLine();cname = sc.nextLine();sc.nextLine();
							ps1.setInt(1 , aid);
							ps1.setString(2 , city);
							ps1.executeUpdate();
							ps2.setInt(1 , sid);
							ps2.setString(2 , sname);
							ps2.setInt(3 , aid);
							ps2.executeUpdate();
							ps3.setInt(1 , cid);
							ps3.setString(2 , cname);
							ps3.executeUpdate();
							ps4.setInt(1 , sid);
							ps4.setInt(2 , cid);
							ps4.executeUpdate();	
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