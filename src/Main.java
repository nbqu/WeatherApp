import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) {
		FastScanner fs = new FastScanner ();

		int n = fs.nextInt(), m = fs.nextInt();

		int [] ar = fs.readArray(n);

		ArrayList<ArrayList<Integer>> g = new ArrayList<ArrayList<Integer>> ();

		for (int i =0; i<n;i++) {
			g.add(new ArrayList<Integer> ());
		}

		for (int i=0;i<n-1;i++) {
			int u = fs.nextInt(), v = fs.nextInt();
			u--;
			v--;
			g.get(u).add(v);
			g.get(v).add(u);
		}

		int [] indegree = new int [n];
		Arrays.fill(indegree,0);

		for (int i=0;i<n;i++) {
			for (int node: g.get(i)) {
				indegree[node] ++;
			}
		}


		int start = 0;
		int [] visited = new int [n];
		Arrays.fill(visited, 0);
		int cnt = 0;

		dfs(start,g,visited,m,ar,cnt,indegree);



		if (ans.size() == -1) 
			System.out.println(0);
		else
			System.out.println(ans.size());

	}

	static ArrayList<Integer> ans = new ArrayList<> ();
	static ArrayList<Integer> track = new ArrayList<> ();

	public static void dfs(int curr, ArrayList<ArrayList<Integer>> g, int [] visited, int m, int [] ar, int cnt, int [] indegree) {

		visited[curr] = 1;
		cnt += ar[curr];


		for (int neighbor: g.get(curr)) {
			if (visited[neighbor] == 0) {
				if (cnt <= m) {
					//if leaf node
					if (indegree[neighbor] == 1 && neighbor != 0) {
						if (cnt == m && ar[neighbor] != ar[curr]) 
							dfs(neighbor,g,visited,m,ar,cnt,indegree);
						else if (cnt == m && ar[neighbor] == 0)
							dfs(neighbor,g,visited,m,ar,cnt,indegree);
						else continue;
					}
					//not leaf node
					else {
						if (cnt + ar[neighbor] <= m) {
							dfs(neighbor,g,visited,m,ar,cnt,indegree);
						}
					}
				}
				else return;
			}
		}
		//if leaf node 
		if (indegree[curr] == 1 && curr != 0) {
			track.add(curr);
			ans.add(cnt);
		}

	}



	static class FastScanner {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st=new StringTokenizer("");

		String next() {
			while (!st.hasMoreElements())
				try {
					st=new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			return st.nextToken();
		}

		boolean hasNext() {
			String next=null;
			try {
				next = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (next==null) {
				return false;
			}
			st=new StringTokenizer(next);
			return true;
		}

		int nextInt() {
			return Integer.parseInt(next());
		}
		long nextLong() {
			return Long.parseLong(next());
		}
		int[] readArray(int n) {
			int[] a=new int[n];
			for (int i=0; i<n; i++) a[i]=nextInt();
			return a;
		}
	}

}
