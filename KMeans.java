import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

class Pair{
		int x , y;
		Pair(int a , int b){
			x = a;
			y = b;
		}
	}

public class KMeans{
	public static void main(String args[])
	{
			String driver = "org.postgresql.Driver";
			try{
			
				Class.forName(driver);
				Connection c = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/C","postgres","@Nanna55");
					if(c != null){
						System.out.println("Connection established");
						String q1 = "select x , y from data";
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
						while(rs1.next()){
							int a = rs1.getInt("x");
							int b = rs1.getInt("y");
							Pair pr = new Pair(a , b);
							list[i] = pr;
							i++;
						}
						rs1.close();
						int k = 3;
						Pair pr1 = list[0] , pr2 = list[3] , pr3 = list[6];
						Set<Pair> st1 = new HashSet<Pair>();
						Set<Pair> st2 = new HashSet<Pair>();
						Set<Pair> st3 = new HashSet<Pair>();
						q1 = "select x , y from data";
						rs1 = stmt1.executeQuery(q1);
						while(rs1.next()){
							int a = rs1.getInt("x");
							int b = rs1.getInt("y");
							int d1 = Math.abs(pr1.x - a) + Math.abs(pr1.y - b);
							int d2 = Math.abs(pr2.x - a) + Math.abs(pr2.y - b);
							int d3 = Math.abs(pr3.x - a) + Math.abs(pr3.y - b);
							if(d1 > d2 && d1 > d3){
								st1.add(new Pair(a , b));
							}
							else if(d2 > d1 && d2 > d3){
								st2.add(new Pair(a , b));
							}
							else{
								st3.add(new Pair(a , b));
							}
						}
						rs1.close();
						System.out.println("After 1 iterations the cluster representatives are : ");
						System.out.println("Cluster 1 : (" + pr1.x + ") , (" + pr1.y + ")");
						System.out.println("Cluster 2 : (" + pr2.x + ") , (" + pr2.y + ")");
						System.out.println("Cluster 3 : (" + pr3.x + ") , (" + pr3.y + ")");
						int itr = 2;
						int it = 2;
						while(itr > 0){
							int x_sum = 0;
							int y_sum = 0;
							int ct = 0;
							for(Pair pr : st1){
								x_sum = x_sum + pr.x;
								y_sum = y_sum + pr.y;
								ct++;
							}
							pr1.x = x_sum / ct;
							pr1.y = y_sum / ct;
							x_sum = 0;
							y_sum = 0;
							ct = 0;
							for(Pair pr : st2){
								x_sum = x_sum + pr.x;
								y_sum = y_sum + pr.y;
								ct++;
							}
							pr2.x = x_sum / ct;
							pr2.y = y_sum / ct;
							x_sum = 0;
							y_sum = 0;
							ct = 0;
							for(Pair pr : st3){
								x_sum = x_sum + pr.x;
								y_sum = y_sum + pr.y;
								ct++;
							}
							pr3.x = x_sum / ct;
							pr3.y = y_sum / ct;
							st1.clear();
							st2.clear();
							st3.clear();
							q1 = "select x , y from data";
							rs1 = stmt1.executeQuery(q1);
							while(rs1.next()){
								int a = rs1.getInt("x");
								int b = rs1.getInt("y");
								int d1 = Math.abs(pr1.x - a) + Math.abs(pr1.y - b);
								int d2 = Math.abs(pr2.x - a) + Math.abs(pr2.y - b);
								int d3 = Math.abs(pr3.x - a) + Math.abs(pr3.y - b);
								if(d1 > d2 && d1 > d3){
									st1.add(new Pair(a , b));
								}
								else if(d2 > d1 && d2 > d3){
									st2.add(new Pair(a , b));
								}
								else{
									st3.add(new Pair(a , b));
								}
							}
							rs1.close();
							System.out.println("After " + (it++) +" iterations the cluster representatives are : ");
							System.out.println("Cluster 1 : (" + pr1.x + ") , (" + pr1.y + ")");
							System.out.println("Cluster 2 : (" + pr2.x + ") , (" + pr2.y + ")");
							System.out.println("Cluster 3 : (" + pr3.x + ") , (" + pr3.y + ")");
							itr--;
						}
						System.out.println("After 3 iterations the cluster representatives are : ");
						System.out.println("Cluster 1 : (" + pr1.x + ") , (" + pr1.y + ")");
						System.out.println("Cluster 2 : (" + pr2.x + ") , (" + pr2.y + ")");
						System.out.println("Cluster 3 : (" + pr3.x + ") , (" + pr3.y + ")");
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