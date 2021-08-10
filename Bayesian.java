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
public class Bayesian{
	public static void main(String args[])
	{
			String driver = "org.postgresql.Driver";
			try{
			
				Class.forName(driver);
				Connection c = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/C","postgres","@Nanna55");
					if(c != null){
						System.out.println("Connection established");
						String q1 = "select * from data3";
						Statement st1 = c.createStatement();
						ResultSet rs1 = st1.executeQuery(q1);
						Statement st2 = c.createStatement();
						ResultSet rs2 = st2.executeQuery(q1);
						
						int n = 0;
						while(rs2.next()){
							n++;
						}
						rs2.close();
						
						String[][] list = new String[n][6];
						int i = 0;
						while(rs1.next()){
							String a = rs1.getString("name");
							String b = rs1.getString("birth");
							String c1 = rs1.getString("fly");
							String d = rs1.getString("water");
							String e = rs1.getString("legs");
							list[i][0] = a;list[i][1] = b;list[i][2] = c1;list[i][3] = d;list[i][4] = e;
							i++;
						}
						rs1.close();
						
						int[] response = new int[]{1 , 0 , 0 , 1 , 0 ,0 ,1 , 0 ,1 ,0 ,0 ,0, 1 ,0 ,0 ,0,1 ,0 ,1 ,0}; //1->mammal ; 0->non-mammal
						
						double[][] birth = new double[3][4];
						Pair[] mp= new Pair[2];	
						mp[0] = new Pair(0,0);mp[1] = new Pair(0,0);	
						for(i = 0;i < n;i++){
							String t = list[i][1];
							if(t.charAt(0) == 'y'){
								if(response[i] == 1){mp[1].x++;}
								else{mp[1].y++;}
							}
							else{
								if(response[i] == 1){mp[0].x++;}
								else{mp[0].y++;}
							}
						}
						birth[0][0] = mp[1].x;birth[0][1] = mp[1].y;
						birth[1][0] = mp[0].x;birth[1][1] = mp[0].y;
						birth[2][0] = birth[0][0] + birth[1][0];
						birth[2][1] = birth[0][1] + birth[1][1];
						birth[0][2] = birth[0][0] / birth[2][0];birth[1][2] = birth[1][0] / birth[2][0];
						birth[0][3] = birth[0][1] / birth[2][1];birth[1][3] = birth[1][1] / birth[2][1];
				
						double[][] fly = new double[3][4];
						mp = new Pair[2];
						mp[0] = new Pair(0 , 0);mp[1] = new Pair(0 ,0);	
						for(i = 0;i < n;i++){
							String t = list[i][2];
							if(t.charAt(0) == 'y'){
								if(response[i] == 1){mp[1].x++;}
								else{mp[1].y++;}
							}
							else{
								if(response[i] == 1){mp[0].x++;}
								else{mp[0].y++;}
							}
						}
						fly[0][0] = mp[1].x;fly[0][1] = mp[1].y;
						fly[1][0] = mp[0].x;fly[1][1] = mp[0].y;
						fly[2][0] = fly[0][0] + fly[1][0];
						fly[2][1] = fly[0][1] + fly[1][1];
						fly[0][2] = fly[0][0] / fly[2][0];fly[1][2] = fly[1][0] / fly[2][0];
						fly[0][3] = fly[0][1] / fly[2][1];fly[1][3] = fly[1][1] / fly[2][1];
						
						double[][] water = new double[4][4];	
						mp = new Pair[3];
						mp[0] = new Pair(0 , 0);mp[1] = new Pair(0 ,0);mp[2] = new Pair(0 , 0);
						for(i = 0;i < n;i++){
							String t = list[i][3];
							if(t.charAt(0) == 'y'){
								if(response[i] == 1){mp[1].x++;}
								else{mp[1].y++;}
							}
							else if(t.charAt(0) == 'c'){
								if(response[i] == 1){mp[2].x++;}
								else{mp[2].y++;}
							}
							else{
								if(response[i] == 1){mp[0].x++;}
								else{mp[0].y++;}
							}
						}
						water[0][0] = mp[1].x;water[0][1] = mp[1].y;
						water[1][0] = mp[0].x;water[1][1] = mp[0].y;
						water[2][0] = mp[2].x;water[2][1] = mp[2].y;
						water[3][0] = water[0][0] + water[1][0] + water[2][0];
						water[3][1] = water[0][1] + water[1][1] + water[2][1];
						water[0][2] = water[0][0] / water[3][0];water[1][2] = water[1][0] / water[3][0];water[2][2] = water[2][0] / water[3][0];
						water[0][3] = water[0][1] / water[3][1];water[1][3] = water[1][1] / water[3][1];water[2][3] = water[2][1] / water[3][1];	
			
						double[][] legs = new double[3][4];	
						mp = new Pair[2];
						mp[0] = new Pair(0 , 0);mp[1] = new Pair(0 ,0);	
						for(i = 0;i < n;i++){
							String t = list[i][4];
							if(t.charAt(0) == 'y'){
								if(response[i] == 1){mp[1].x++;}
								else{mp[1].y++;}
							}
							else{
								if(response[i] == 1){mp[0].x++;}
								else{mp[0].y++;}
							}
						}
						legs[0][0] = mp[1].x;legs[0][1] = mp[1].y;
						legs[1][0] = mp[0].x;legs[1][1] = mp[0].y;
						legs[2][0] = legs[0][0] + legs[1][0];
						legs[2][1] = legs[0][1] + legs[1][1];
						legs[0][2] = legs[0][0] / legs[2][0];legs[1][2] = legs[1][0] / legs[2][0];
						legs[0][3] = legs[0][1] / legs[2][1];legs[1][3] = legs[1][1] / legs[2][1];

						double[][] mammal = new double[3][2];	
						int ct = 0;
						for(i = 0;i < n;i++){
							if(response[i] == 1){ct++;}
						}
						mammal[0][0] = ct;mammal[1][0] = n - ct;
						mammal[2][0] = mammal[0][0] + mammal[1][0];
						mammal[0][1] = mammal[0][0] / mammal[2][0];mammal[1][1] = mammal[1][0] / mammal[2][0];
						
						double p_yes = 0.0 , p_no = 0.0;
						HashMap<String , Integer> t= new HashMap<>();
						t.put("yes" , 0);t.put("no" , 1);t.put("can" , 2);
						
						Scanner sc = new Scanner(System.in);
						
						System.out.println("Enter test data for birth : ");
						String a = sc.nextLine();
						
						System.out.println("Enter test data for fly : ");
						String b = sc.nextLine();
						
						System.out.println("Enter test data for water : ");
						String c1 = sc.nextLine();
						
						System.out.println("Enter test data for legs : ");
						String d = sc.nextLine();
						
						p_yes = birth[t.get(a)][2] * fly[t.get(b)][2] * water[t.get(c1)][2] * legs[t.get(d)][2] * mammal[0][1];
						p_no = birth[t.get(a)][3] * fly[t.get(b)][3] * water[t.get(c1)][3] * legs[t.get(d)][3] * mammal[1][1];
						
						double total = p_yes + p_no;
						p_yes = p_yes / total;
						p_no = p_no / total;
						
						if(p_yes >= p_no){
    							System.out.println("The given feature vector will belong to the class Mammal");
  						}
  						else{
   							System.out.println("The given feature vector will belong to the class Non-Mammal");
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