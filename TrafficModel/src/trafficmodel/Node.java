package trafficmodel;

import java.util.ArrayList;
import java.util.HashMap;
import trafficmodel.CitySection;
import trafficmodel.Edge;
import trafficmodel.Joint;
import trafficmodel.Lane;


public class Node{
    ArrayList<Edge> inEdges,outEdges;
    
    public Node(Joint j,HashMap createdNodes){
        outEdges = new ArrayList<>();
        inEdges = new ArrayList<>();
        Lane[] outLanes = j.outLanes;
        for(Lane lane:outLanes){
            Edge edge = new Edge(lane,this,createdNodes);
            outEdges.add(edge);
        }
    }
    
    public Node(CitySection section,int[] indices,HashMap<Joint,Node> createdNodes){
        inEdges = new ArrayList();
        outEdges = new ArrayList();
        Lane out = section.lanes.get(indices[1]);
        Lane in = section.lanes.get(indices[0]);
        
        Node toConnect = (Node) createdNodes.get(out.out);
        Edge outEdge = new Edge(out,this,createdNodes);
         for(Edge edge:toConnect.outEdges){
            if (edge.lane.equals(in)){
                edge.out = this;
                inEdges.add(edge);
            }
        }
        
        outEdges.add(outEdge);
        
    }
    
    public void addInEdge(Edge e){
        inEdges.add(e);
    }
    
    public void print(){
        System.out.printf("Node with %d edges\n",outEdges.size() + inEdges.size());
        System.out.println("Out edges:");
        for(Edge e:outEdges){
            e.print();
        }
        System.out.println("In edges:");
        for(Edge e:inEdges){
            e.print();
        }
    }
}