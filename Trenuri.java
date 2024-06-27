import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class Trenuri {
	static class Resolve {
		public static final String INPUT_FILE = "trenuri.in";
		public static final String OUTPUT_FILE = "trenuri.out";
		private Map<String, HashMap<String, Integer>> graph = new HashMap<>();
		private String start;
		private String end;
		private int M;

		public void solve() {
			readInput();
			writeOutput(getResult());
		}

		private void readInput() {
			try {
				Scanner scanner = new Scanner(new File(INPUT_FILE));
				start = scanner.next();
				end = scanner.next();
				M = scanner.nextInt();
				for (int i = 0; i < M; i++) {
					String fromCity = scanner.next();
					String toCity = scanner.next();

					if (!graph.containsKey(fromCity)) {
						graph.put(fromCity, new HashMap<>());
					}
					if (!graph.containsKey(toCity)) {
						graph.put(toCity, new HashMap<>());
					}
					graph.get(fromCity).put(toCity, !graph.get(fromCity).containsKey(toCity) ? 1 :
							graph.get(fromCity).get(toCity) + 1);

				}
				scanner.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int result) {
			try (PrintWriter writer = new PrintWriter(new File(OUTPUT_FILE))) {
				writer.print(result);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * Determina numarul maxim de orașe vizitate de la orașul de start la end
		 * folosind sortare topologica si programare dinamica. Se itereaza prin
		 * nodurile sortate topologic si se actualizeaza numarul maxim de orase
		 * vizitate pentru fiecare vecin al nodului curent.
		 *
		 * @return numarul maxim de orașe traversate
		 */
		private int getResult() {
			List<String> sorted = topologicalSort();
			HashMap<String, Integer> dp = new HashMap<>();
			dp.put(start, 1);

			for (String city : sorted) {
				if (dp.containsKey(city)) {
					for (String neighbor : graph.get(city).keySet()) {
						dp.put(neighbor, !dp.containsKey(neighbor) ? dp.get(city) + 1 :
								Math.max(dp.get(neighbor), dp.get(city) + 1));
					}
				}
			}
			return dp.get(end);
		}

		/**
		 * Sortare Topologica - BFS: algoritmul lui Kahn, dar schimbat putin
		 * (indegree cu map)
		 * https://www.geeksforgeeks.org/topological-sorting-indegree-based-solution/
		 */
		private List<String> topologicalSort() {
			Map<String, Integer> indegree = new HashMap<>();
			for (String city : graph.keySet()) {
				if (!indegree.containsKey(city)) {
					indegree.put(city, 0);
				}
				for (String neighbor : graph.get(city).keySet()) {
					indegree.put(neighbor, !indegree.containsKey(neighbor) ? 1
							: indegree.get(neighbor) + 1);
				}
			}

			Queue<String> q = new LinkedList<>();
			for (String city : indegree.keySet()) {
				if (indegree.get(city) == 0) {
					q.add(city);
				}
			}

			ArrayList<String> result = new ArrayList<>();
			while (!q.isEmpty()) {
				String city = q.poll();
				result.add(city);
				for (String adjacent : graph.get(city).keySet()) {
					indegree.put(adjacent, indegree.get(adjacent) - 1);
					if (indegree.get(adjacent) == 0) {
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
