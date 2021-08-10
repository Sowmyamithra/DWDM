import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class FC{
	public static void main(String args[])
	{
			String driver = "org.postgresql.Driver";
			try{
			
				Class.forName(driver);
				Connection c = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/DS","postgres","@Nanna55");
					if(c != null){
						System.out.println("Connection established");
						String q1 = "create table employee(eid integer , ename char(20) , primary key(eid))";
						String q2 = "create table dept(did integer , dname char(20) , primary key(did))";
						String q3 = "create table employee_details(eid integer , did integer , primary key(eid , did) , foreign key(eid) references employee(eid) , foreign key(did) references dept(did))";
						String q4 = "create table manager(mid integer , mname char(20) , primary key(mid))";
						String q5 = "create table manager_details(mid integer , did integer , primary key(mid , did) , foreign key(mid) references manager(mid) , foreign key(did) references dept(did))";
						Statement st1 = c.createStatement();
						Statement st2 = c.createStatement();
						Statement st3 = c.createStatement();
						Statement st4 = c.createStatement();
						Statement st5 = c.createStatement();
						st1.executeUpdate(q1);
						st2.executeUpdate(q2);
						st3.executeUpdate(q3);
						st4.executeUpdate(q4);
						st5.executeUpdate(q5);
						PreparedStatement ps1 = c.prepareStatement("insert into employee(eid , ename) values(? , ?)");
						PreparedStatement ps2 = c.prepareStatement("insert into dept(did , dname) values(? , ?)");
						PreparedStatement ps3 = c.prepareStatement("insert into employee_details(eid , did) values(? , ?)");
						PreparedStatement ps4 = c.prepareStatement("insert into manager(mid , mname) values(? , ?)");
						PreparedStatement ps5 = c.prepareStatement("insert into manager_details(mid , did) values(? , ?)");
						int ct = 2;
						Scanner sc = new Scanner(System.in);
						while(ct > 0){
							int eid , did , mid;
							String ename , dname , mname;
							System.out.println("Enter eid , ename");
							eid = sc.nextInt();sc.nextLine();ename = sc.nextLine();sc.nextLine();
							System.out.println("Enter did , dname");
							did = sc.nextInt();sc.nextLine();dname = sc.nextLine();sc.nextLine();
							System.out.println("Enter mid , mname");
							mid = sc.nextInt();sc.nextLine();mname = sc.nextLine();sc.nextLine();
							ps1.setInt(1 , eid);
							ps1.setString(2 , ename);
							ps1.executeUpdate();
							ps2.setInt(1 , did);
							ps2.setString(2 , dname);
							ps2.executeUpdate();
							ps3.setInt(1 , eid);
							ps3.setInt(2 , did);
							ps3.executeUpdate();
							ps4.setInt(1 , mid);
							ps4.setString(2 , mname);
							ps4.executeUpdate();
							ps5.setInt(1 , mid);
							ps5.setInt(2 , did);
							ps5.executeUpdate();
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