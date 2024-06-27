import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Numarare {
	static class Resolve {
		public static final String INPUT_FILE = "numarare.in";
		public static final String OUTPUT_FILE = "numarare.out";
		public static final int MOD = 1000000007;
		int N, M;
		List<List<Integer>> graph1;
		List<List<Integer>> graph2;
		List<List<Integer>> graph;

		public void solve() {
			readInput();
			writeOutput(getResult());
		}

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				N = sc.nextInt();
				M = sc.nextInt();
				graph1 = new ArrayList<>(N + 1);
				graph2 = new ArrayList<>(N + 1);
				graph = new ArrayList<>(N + 1);

				for (int i = 0; i <= N; i++) {
					graph1.add(new ArrayList<>());
					graph2.add(new ArrayList<>());
					graph.add(new ArrayList<>());
				}
				for (int i = 1; i <= 2 * M; i++) {
					int x = sc.nextInt();
					int y = sc.nextInt();
					if (i <= M) {
						graph1.get(x).add(y);
					} else {
						graph2.get(x).add(y);
					}
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.print(result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * Construiesc graful cu muchiile comun dintre cele doua grafuri;
		 * Sortez nodurile in ordinea topologica;
		 * Aplic programarea dinamica pentru a calcula numarul de drumuri de la nodul 1 la nodul N;
		 *
		 * @return dp[N] - numarul de drumuri de la nodul 1 la nodul N
		 */
		private int getResult() {
			for (int i = 1; i <= N; i++) {
				graph.get(i).addAll(graph1.get(i)); // adaug vecinii din graph1
				graph.get(i).retainAll(graph2.get(i)); // pastrez doar vecinii comuni graph2
			}

			List<Integer> sortedNodes = topologicalSort(graph);

			int[] dp = new int[N + 1];
			dp[1] = 1;
			for (int node : sortedNodes) {
				for (int neighbor : graph.get(node)) {
					dp[neighbor] = (dp[neighbor] + dp[node]) % MOD;
				}
			}
			return dp[N];
		}

		/**
		 * Sortare Topologica - BFS: algoritmul lui Kahn
		 * https://www.geeksforgeeks.org/topological-sorting-indegree-based-solution/
		 */
		private List<Integer> topologicalSort(List<List<Integer>> graph) {
			int[] indegree = new int[N + 1];
			for (List<Integer> list : graph) {
				for (int vertex : list) {
					indegree[vertex]++;
				}
			}

			Queue<Integer> q = new LinkedList<>();
			for (int i = 1; i <= N; i++) {
				if (indegree[i] == 0) {
					q.add(i);
				}
			}

			List<Integer> result = new ArrayList<>();
			while (!q.isEmpty()) {
				int node = q.poll();
				result.add(node);
				for (int adjacent : graph.get(node)) {
					indegree[adjacent]--;
					if (indegree[adjacent] == 0) {
						q.add(adjacent);
					}
				}
			}
			return result;
		}
	}

	public static void main(String[] args) {
		new Resolve().solve();
	}
}
