import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

class Pair{
		double x , y;
		int z;
		Pair(double a , double b , int c){
			x = a;
			y = b;	
			z = c;
		}
	}

public class KNN{
	public static void main(String args[])
	{
			String driver = "org.postgresql.Driver";
			try{
			
				Class.forName(driver);
				Connection c = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/C","postgres","@Nanna55");
					if(c != null){
						System.out.println("Connection established");
						String q1 = "select x , y , z from data1";
						Statement stmt1 = c.createStatement();
						Statement stmt2 = c.createStatement();
						ResultSet rs1 = stmt1.executeQuery(q1);
						ResultSet rs2 = stmt2.executeQuery(q1);
						int n = 0;
						while(rs2.next()){
							n++;
						}
						rs2.close();
						Pair[] list = new Pair[n];
						int i = 0;
						Map<Integer , Integer> mp = new HashMap<Integer , Integer>();
						int mx = -1;
						int cl = 0;
						while(rs1.next()){
							double a = rs1.getInt("x");
							double b = rs1.getInt("y");
							int c1 = rs1.getInt("z");
							Pair pr = new Pair(a , b , c1);
							mp.put(c1 , mp.getOrDefault(c1 , 0)+1);
							if(mp.get(c1) > mx){
								mx = mp.get(c1);
								cl = c1;
							}
							list[i] = pr;
							i++;
						}
						rs1.close();
						int k = 3;
						Scanner sc = new Scanner(System.in);
						System.out.println("Enter x , y of new data : ");
						double new_x = sc.nextDouble() , new_y = sc.nextDouble();
						double[] dist = new double[n];
						for(i = 0;i < n;i++){
							double val = Math.abs(list[i].x - new_x) + Math.abs(list[i].y - new_y);
							dist[i] = val;
						}
						double mn1 = dist[0] , mn2 = 0, mn3 = 0;
						int cl1 = list[0].z , cl2 = 0 , cl3 = 0;
						for(i = 0;i < n;i++){
							if(dist[i] < mn1){
								mn3 = mn2;
								mn2 = mn1;
								mn1 = dist[i];
								cl3 = cl2;
								cl2 = cl1;
								cl1 = list[i].z;							
							}	
						}
						mp.clear();	
						mp.put(cl1 , mp.getOrDefault(cl1 , 0));
						mp.put(cl2 , mp.getOrDefault(cl2 , 0));
						mp.put(cl3 , mp.getOrDefault(cl3 , 0));
						int a = mp.get(cl1) , b = mp.get(cl2) , c1 = mp.get(cl3);
						if(a > b && a > c1){
							System.out.println("Given test data belongs to class : " + cl1);
						}
						else if(b > a && b > c1){
							System.out.println("Given test data belongs to class : " + cl2);
						}
						else if(c1 > a && c1 > b){
							System.out.println("Given test data belongs to class : " + cl3);
						}
						else{
							System.out.println("Given test data belongs to class : " + cl);	
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