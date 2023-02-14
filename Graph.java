import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    Map<Integer, ArrayList<Edge>> adjLists;
    Map<Integer, String> idMap;

    public Graph(Table table, Forwarder fwd) {
        adjLists = new HashMap<Integer, ArrayList<Edge>>();
        idMap = table.idMap;
        for(int i = 0; i < fwd.fwdList.size(); i++)
        {
            String container = fwd.fwdList.get(i);
            Information info = table.hs.get(container);
            int id = info.id;
            ArrayList<Edge> list = new ArrayList<>();

            int vertex = id;
            
            for(int k = 0; k < info.connectionArr.size(); k++)
            {
                String adjContainer = info.connectionArr.get(k);
                Information adjInfo = table.hs.get(adjContainer);
                int adjVertex = adjInfo.id;
                double weight = 1;
                Edge edge = new Edge(vertex, adjVertex, weight);
                list.add(edge);
            }
            
            adjLists.put(vertex, list);
        }
        
        System.out.println();
    }

}
