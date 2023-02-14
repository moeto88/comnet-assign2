import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Dijkstra {
	double negInfinity = Double.NEGATIVE_INFINITY;
	double posInfinity = Double.POSITIVE_INFINITY;
	double costTo[];
	Graph graph;
	int srcId;
	int dstId;
	int max;
	ArrayList<Integer> path;

	public Dijkstra(int srcId, int dstId, Graph graph){
		this.graph =  graph;
		this.srcId = srcId;
		this.dstId = dstId;
		this.max = graph.adjLists.size();
	}

	public boolean dijkstraAlgo() {
		if(findIDs())
		{
			Map<Integer, ArrayList<Edge>> adjList = graph.adjLists;
			path = new ArrayList<>();
			path.add(srcId);
			costTo = new double[max];
			Queue<Integer> pq = new PriorityQueue<>(Comparator.comparing(integer -> costTo[integer]));
			
			for(int i = 0; i < max; i++)
			{
				if(i == srcId)
				{
					costTo[i] = 0;
					
				}
				else
				{
					costTo[i] = posInfinity;
				}
			}
			
			pq.add(srcId);
			
			boolean completedVertex[] = new boolean[max];
			
			while(pq.isEmpty() != true)
			{
				int vertex = pq.remove();
				
				
				ArrayList<Edge> keyList = adjList.get(vertex);
				
				for(Edge edge: keyList)
				{
					int connectingVertex = edge.adjVertex;
					
					if(completedVertex[connectingVertex] == false)
					{
						double updatedWeight = costTo[vertex] + edge.weight;
						
						if(updatedWeight < costTo[connectingVertex])
						{
							costTo[connectingVertex] = updatedWeight;
							path.add(connectingVertex);
						}
						
						pq.remove(connectingVertex);
						pq.add(connectingVertex);
					}
				}
				
				completedVertex[vertex] = true;
			}
			
			return true;
		}
		else
		{
			return false;
		}
	}

	private boolean findIDs() {
		if(graph.idMap.containsKey(srcId) && graph.idMap.containsKey(dstId))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}