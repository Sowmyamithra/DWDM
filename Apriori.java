import java.util.*;

public class Apriori{
	static boolean isSubset(HashSet<Integer> s1 , HashSet<Integer> s2){
		int ct = 0;
		for(int x : s1){
			if(s2.contains(x)){ct++;}
		}
		if(ct == s2.size()-1){return true;}
		return false;
	}
	public static void main(String[] args){
		ArrayList<HashSet<Integer>> transactions = new ArrayList<>();
		ArrayList<HashSet<Integer>> ans = new ArrayList<>();
		HashSet<Integer> t = new HashSet<>();
		t.add(1);t.add(2);t.add(5);transactions.add(t);
		t = new HashSet<>();
		t.add(2);t.add(4);transactions.add(t);
		t = new HashSet<>();
		t.add(2);t.add(3);transactions.add(t);
		t = new HashSet<>();
		t.add(1);t.add(2);t.add(4);transactions.add(t);
		t = new HashSet<>();
		t.add(1);t.add(3);transactions.add(t);
		t = new HashSet<>();
		t.add(2);t.add(3);transactions.add(t);
		t = new HashSet<>();
		t.add(1);t.add(3);transactions.add(t);
		t = new HashSet<>();
		t.add(1);t.add(2);t.add(5);t.add(3);transactions.add(t);
		t = new HashSet<>();
		t.add(1);t.add(2);t.add(3);transactions.add(t);

		HashMap<Integer , HashSet<Integer>> items = new HashMap<>();
		int n = transactions.size();
		for(int i = 0;i < n;i++){
			for(int x : transactions.get(i)){
				if(items.get(x) == null){
					HashSet<Integer> st = new HashSet<>();
					st.add(i);
					items.put(x , st);
				}	
				else{
					HashSet<Integer> st = items.get(x);
					st.add(i);
					items.put(x , st);
				}
			}
		}
		
		HashMap<HashSet<Integer> , Integer> freq = new HashMap<>();
		HashMap<HashSet<Integer> , Integer> need = new HashMap<>();
		HashMap<HashSet<Integer> , Integer> rem = new HashMap<>();
		
		int s_ct = 2;
		for(int key : items.keySet()){
			int ct = items.get(key).size();
			HashSet<Integer> st = new HashSet<>();
			st.add(key);
			if(ct < s_ct){
				rem.put(st , ct);
			}
			else{
				need.put(st , ct);
			}
		}
		//for(Set<Integer> key : need.keySet()){System.out.println(key + " " + need.get(key));}
	
		int k = 2;
		while(need.size() != 0){
			ArrayList<HashSet<Integer>> temp = new ArrayList<>();
			for(HashSet<Integer> key : need.keySet()){
				temp.add(key);
				ans.add(key);
			}
			n = temp.size();
			freq.clear();
			for(int i = 0;i < n;i++){
				for(int j = i+1;j < n;j++){
					HashSet<Integer> res = new HashSet<>();
					for(int x : temp.get(i)){res.add(x);}
					for(int x : temp.get(j)){res.add(x);}
					int diff = temp.get(i).size() + temp.get(j).size() - res.size();
					boolean flag = true;
					for(HashSet<Integer> key : rem.keySet()){
						if(isSubset(key , res)){flag = false;break;}
					}
					if(diff == k-2 && flag){
						ArrayList<Integer> li = new ArrayList<>();
						for(int x : res){li.add(x);}
						int s = li.size();
						int final_ct = 0;
						for(int l = 0;l < transactions.size();l++){
							int ct1 = 0;
							for(int m = 0;m < s;m++){
								if(transactions.get(l).contains(li.get(m))){ct1++;}
							}
							if(ct1 == s){final_ct++;}
						}
						freq.put(res , final_ct);	
					}
				}
			}
			rem.clear();need.clear();
			for(HashSet<Integer> key : freq.keySet()){
				int ct1 = freq.get(key);
				if(ct1 < s_ct){rem.put(key , freq.get(key));}
				else{need.put(key , freq.get(key));}
			}
			k++;
		}
		n = ans.size();
		System.out.println("ITEM SETS");
		for(int i = 0;i < n;i++){
			for(int x : ans.get(i)){
				System.out.print("i" + x + " ");
			}
			System.out.println();
		}	
	}
}