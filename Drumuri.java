import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Drumuri {
	static class Node implements Comparable<Node> {
		int node;
		long cost;

		public Node(int node, long cost) {
			this.node = node;
			this.cost = cost;
		}

		public int compareTo(Node other) {
			return Long.compare(this.cost, other.cost);
		}
	}

	static class Resolve {
		public static final String INPUT_FILE = "drumuri.in";
		public static final String OUTPUT_FILE = "drumuri.out";
		private Map<Integer, Map<Integer, Long>> graph = new HashMap<>();
		private Map<Integer, Map<Integer, Long>> reverseGraph = new HashMap<>();

		private int N, M;
		private int x, y, z;

		public void solve() {
			readInput();
			writeOutput(getResult());
		}

		private void readInput() {
			try {
				Scanner scanner = new Scanner(new File(INPUT_FILE));
				N = scanner.nextInt();
				M = scanner.nextInt();
				for (int i = 0; i < M; i++) {
					int a = scanner.nextInt();
					int b = scanner.nextInt();
					long c = scanner.nextLong();
					if (!graph.containsKey(a)) {
						graph.put(a, new HashMap<>());
					}
					graph.get(a).put(b, c);
					if (!reverseGraph.containsKey(b)) {
						reverseGraph.put(b, new HashMap<>());
					}
					reverseGraph.get(b).put(a, c);

				}
				x = scanner.nextInt();
				y = scanner.nextInt();
				z = scanner.nextInt();
				scanner.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(long result) {
			try {
				PrintWriter writer = new PrintWriter(new File(OUTPUT_FILE));
				writer.print(result);
				writer.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * Calculeaza costul minim folosind dijkstra de 3 ori:
		 * - de la x la toate celelalte noduri
		 * - de la y la toate celelalte noduri
		 * - de la toate celelalte noduri la z (graful invers)
		 *
		 * @return costul minim pentru drumul de la x la z, trecand si prin y
		 */
		private long getResult() {
			Map<Integer, Long> distFromX = new HashMap<>();
			dijkstra(distFromX, x, graph);
			Map<Integer, Long> distFromY = new HashMap<>();
			dijkstra(distFromY, y, graph);
			Map<Integer, Long> distToZ = new HashMap<>();
			dijkstra(distToZ, z, reverseGraph);

			long minCost = Long.MAX_VALUE;
			for (int i = 1; i <= N; i++) {
				if (distFromX.containsKey(i) && distFromY.containsKey(i)
						&& distToZ.containsKey(i)) {
					minCost = Math.min(minCost, distFromX.get(i) + distFromY.get(i)
							+ distToZ.get(i));
				}
			}

			return minCost;
		}

		/**
		 * Algoritmul lui Dijkstra pentru a gasirea celui mai scurt drum de
		 * la nodul de start la toate celelalte noduri în graf.
		 *
		 * @param dist  map ce stocheaza distanta minima de la nodul de start la
		 *              fiecare nod din graf
		 * @param start nodul de start
		 * @param graph reprezentarea grafului ca un map de mapuri
		 */
		private void dijkstra(Map<Integer, Long> dist, int start, Map<Integer,
				Map<Integer, Long>> graph) {
			PriorityQueue<Node> pq = new PriorityQueue<>();

			pq.add(new Node(start, 0));
			dist.put(start, 0L);

			while (!pq.isEmpty()) {
				Node current = pq.poll();
				if (current.cost > dist.getOrDefault(current.node, Long.MAX_VALUE)) {
					continue;
				}

				Map<Integer, Long> neighbors = graph.containsKey(current.node)
						? graph.get(current.node) : new HashMap<>();

				for (Map.Entry<Integer, Long> entry : neighbors.entrySet()) {
					int neighbor = entry.getKey();
					long newCost = current.cost + entry.getValue();
					if (newCost < dist.getOrDefault(neighbor, Long.MAX_VALUE)) {
						dist.put(neighbor, newCost);
						pq.add(new Node(neighbor, newCost));
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		new Resolve().solve();
	}
}