package VultBurglary;
import java.util.*;

public class VultBurglarySystemClass implements VultBurglarySystem{

	private Graph g;
	private int flux;
	private int numThieves;
	private int dreno;
  private int numLocations;
  private int numRoads;
	
	public VultBurglarySystemClass(int numLocations, int flux, int numThieves, int dreno, int numRoads) {
    g = new Graph(2*(numLocations+2),numLocations);
    this.flux = flux;
		this.numThieves = numThieves;
		this.dreno = dreno;
    this.numLocations = numLocations;
    this.numRoads = numRoads;
	}

	public void addPassage(int firstNode, int secondNode) {
    g.addEdge(firstNode, secondNode, 1);
    g.addEdge(secondNode, firstNode, 1);
	}

	public int getMaxFlux(){
		return g.DinicMaxflow(flux, dreno);
	}

  public void addBefore(int flux){
    g.addEdge(0, flux, numLocations);
  }

}

class Edge {
    public int v; // Vertex v (or "to" vertex)
                 // of a directed edge u-v. "From"
                 // vertex u can be obtained using
                 // index in adjacent array
   
    public int flow;    // flow of data in edge
   
    public int C;        // Capacity
   
    public int rev;        // To store index of reverse
                       // edge in adjacency list so that
                       // we can quickly find it.
     
    public Edge(int v, int flow, int C, int rev) {
        this.v = v;
        this.flow = flow;
        this.C = C;
        this.rev = rev;
    }
}
 
// Residual Graph
class Graph {
    private int V;                // No. of vertex
    private int[] level;        // Stores level of graph
    private List<Edge>[] adj;
    private int numLocations;
 
    @SuppressWarnings("unchecked")
    public Graph(int V, int numLocations) {
      this.numLocations = numLocations;
        adj = new ArrayList[2*V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<Edge>();
        }
        this.V = V;
        level = new int[V];
    }
 
      // Add edge to the graph
    public void addEdge(int u, int v, int C) {

      if(adj[u].size() == 1){ //optimizar pq nem sempre precisa, ex ter apenas 2 saidas e uma entrada
        addEdge(u,numLocations+u ,1); //criar caminhos entre entrada e saida
        addEdge(numLocations+u,u,1);
        for(int i = 0; i< adj[u].size();i++){       
          Edge tmp = new Edge(u, 0, 0, adj[u].size());
          adj[u].remove(i);
          for(int j = 0; j< adj[v].size(); i++){
            if(adj[v].get(j) == tmp)
              adj[v].remove(j);
          }

          tmp = new Edge(numLocations+u, 0, 0, adj[numLocations+u].size());
          adj[numLocations+u].add(adj[u].get(i));
          adj[v].add(tmp);
        }
      } else if(adj[u].size() > 1){
          Edge a = new Edge(v, 0, C, adj[v].size());
     
          Edge b = new Edge(numLocations+u, 0, 0, adj[u].size());

          adj[numLocations+u].add(a);
          adj[v].add(b);
      } else{
        Edge a = new Edge(v, 0, C, adj[v].size());
      
          // Back edge : 0 flow and 0 capacity
        Edge b = new Edge(u, 0, 0, adj[u].size());

        adj[u].add(a);
        adj[v].add(b);
      }

  }
 
      // Finds if more flow can be sent from s to t.
    // Also assigns levels to nodes.
    public boolean BFS(int s, int t) {
        for (int i = 0; i < V; i++) {
            level[i] = -1;
        }
 
        level[s] = 0;        // Level of source vertex
 
           // Create a queue, enqueue source vertex
        // and mark source vertex as visited here
        // level[] array works as visited array also.
        LinkedList<Integer> q = new LinkedList<Integer>();
        q.add(s);
 
        ListIterator<Edge> i;
        while (q.size() != 0) {
            int u = q.poll();
 
            for (i = adj[u].listIterator(); i.hasNext();) {
                Edge e = i.next();
                if (level[e.v] < 0 && e.flow < e.C) {
                   
                      // Level of current vertex is -
                      // Level of parent + 1
                    level[e.v] = level[u] + 1;
                    q.add(e.v);
                }
            }
        }
 
        return level[t] < 0 ? false : true;
    }
 
    // A DFS based function to send flow after BFS has
    // figured out that there is a possible flow and
    // constructed levels. This function called multiple
    // times for a single call of BFS.
    // flow : Current flow send by parent function call
    // start[] : To keep track of next edge to be explored.
    //           start[i] stores  count of edges explored
    //           from i.
    //  u : Current vertex
    //  t : Sink
    public int sendFlow(int u, int flow, int t, int start[]) {
       
          // Sink reached
        if (u == t) {
            return flow;
        }
 
          // Traverse all adjacent edges one -by - one.
        for (; start[u] < adj[u].size(); start[u]++) {
           
              // Pick next edge from adjacency list of u
            Edge e = adj[u].get(start[u]);
 
            if (level[e.v] == level[u] + 1 && e.flow < e.C) {
                  // find minimum flow from u to t
                int curr_flow = Math.min(flow, e.C - e.flow);
 
                int temp_flow = sendFlow(e.v, curr_flow, t, start);
                 
                  // flow is greater than zero
                if (temp_flow > 0) {
                      // add flow  to current edge
                    e.flow += temp_flow;
                   
                      // subtract flow from reverse edge
                    // of current edge
                    adj[e.v].get(e.rev).flow -= temp_flow;
                    return temp_flow;
                }
            }
        }
 
        return 0;
    }
 
      // Returns maximum flow in graph
    public int DinicMaxflow(int s, int t) {
        if (s == t) {
            return -1;
        }
 
        int total = 0;
 
        // Augment the flow while there is path
        // from source to sink
        while (BFS(s, t) == true) {
           
              // store how many edges are visited
              // from V { 0 to V }
            int[] start = new int[V + 1];
 
              // while flow is not zero in graph from S to D
            while (true) {
                int flow = sendFlow(s, Integer.MAX_VALUE, t, start);
                if (flow == 0) {
                    break;
                }
               
                  // Add path flow to overall flow
                total += flow;
            }
        }
 
          // Return maximum flow
        return total;
    }
}