import java.util.*;
public class ShortestPath {
	/*
	 * there is target node M.
	 * a certain node N is considered trusted if it is Y distance from M
	 * Y = trusted distance
	 * X = trusted threshold
	 * Y <= X, then node N is trusted
	 */
	
	public static void main(String [] args) {
		int node = 0;
		int [][] trustGraph = {{0, 2, 3}, {2, 0, 2}, {3, 2, 0}};
		int [] pretrustedPeer = {2};
		int trustThreshold = 10;
		boolean trusted = isTrusted(node, trustGraph, pretrustedPeer, trustThreshold);
		System.out.println(trusted);
		/*
		 * Other inputs for test
		 * node, trustGraph, pre-trusted Peer, trustThreshold:
		 * 0, {{0}}, {0}, 1
		 * 0, {{0, 2}, {2, 0}}, {1}, 1
		 * 0, {{0, 2}, {2, 0}}, {1}, 5
		 * 0, {{0, 2, 0}, {2, 0, 2}, {0, 2, 0}}, {2}, 1
		 * 0, {{0, 2, 0}, {2, 0, 2}, {0, 2, 0}}, {2}, 10
		 * 0, {{0, 2, 3}, {2, 0, 2}, {3, 2, 0}}, {2}, 1
		 */
		
	}
	
	//tweaked form of Dijkstra's algorithm
	public static boolean isTrusted(int node, int[][] trustGraph, int[] pretrustedPeers, int trustThreshold) {
	    Queue<Integer> Path = new LinkedList<Integer>(); //tree nodes
	    PriorityQueue<Integer> Q = new PriorityQueue<Integer>(); //fringe nodes
	    for(int i=0; i<pretrustedPeers.length; i++){
	        if(pretrustedPeers[i] == node){
	            return true;
	        }
	    }
	    //initialization
	    int[] d = new int[trustGraph.length]; //minimum distance of this index from node
	    int[] pi = new int[trustGraph.length]; //predecessor of node
	    for(int i=0; i<trustGraph.length; i++){
	        d[i] = Integer.MAX_VALUE; //infinity
	        pi[i] = i;
	    }
	    d[node] = 0;
	    Q.add(node); //our start node, in this case we start from Node 0
	    while(!Q.isEmpty()){
	        int curr = Q.poll(); //takes minimum weight node from Queue
	        Path.add(curr); //and adds it to the "path"
	        //curr to i
	        for(int i=0; i<trustGraph.length; i++){
	            int adjWeight = trustGraph[curr][i];
	            if(adjWeight > 0){ 
	            	if(d[i] == Integer.MAX_VALUE){ //so we don't double add
                        Q.add(i);
                        if(d[curr] + adjWeight < d[i]){
    	                    pi[i] = curr;
    	                    d[i] = d[curr] + adjWeight;
    	                }
                    }
	            }
	        }
	    }
	    //for(Integer i : R){System.out.print(i + " ");}
	    for(int n=0;n<d.length; n++) {System.out.print(d[n] + " ");} // these are the distances to each node from Node 0
	    System.out.println("\n distance from pretrusted peer: " + d[pretrustedPeers[0]] + "\n");
	    if(d[pretrustedPeers[0]] <= trustThreshold){
	        return true;
	    }
		return false;
	}
}
