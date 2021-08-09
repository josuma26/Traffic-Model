package trafficmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import trafficmodel.CitySection;
import trafficmodel.Edge;
import trafficmodel.Joint;
import trafficmodel.Lane;


public class Node{
    ArrayList<Edge> inEdges,outEdges;
    String name;
    public Node(Joint j,HashMap createdNodes,String name){
        outEdges = new ArrayList<>();
        inEdges = new ArrayList<>();
        Lane[] outLanes = j.outLanes;
        ArrayList<Joint> outputs = new ArrayList<>();
        for(Lane lane:outLanes){
            if (lane.out == null || !outputs.contains(lane.out)){
                Edge edge = new Edge(lane,this,createdNodes);
                outputs.add(edge.lane.out);
                outEdges.add(edge);
            }
            
        }
        this.name = name;
    }
    
    public Node(CitySection section,int[] indices,HashMap<Joint,Node> createdNodes){
        name = section.name + " endpoint " + Arrays.toString(indices);
        inEdges = new ArrayList();
        outEdges = new ArrayList();
        
        Lane out = section.lanes.get(indices[1]);
        Lane in = section.lanes.get(indices[0]);
        
        Node toConnect = (Node) createdNodes.get(section.joint);
        if (out.out == null && in.out == null){
            //All edges enter joint
            boolean found = false;
            for(Edge edge:toConnect.outEdges){
                if (edge.lane.equals(out) || edge.lane.equals(in)){
                    if (!found){
                        edge.out = this;
                        inEdges.add(edge);
                        found = true;
                    }else{
                        toConnect.outEdges.remove(edge);
                    }
                }
            }
            
            
        }
        else if (out.out != null && in.out != null){
            //All edges leave joint
           Edge e = new Edge(in,this,createdNodes);
           outEdges.add(e);
        }
        else{
            //one and one based on specified indices
            Edge outEdge = new Edge(out,this,createdNodes);
            for(Edge edge:toConnect.outEdges){
                if (edge.lane.equals(in)){
                    edge.out = this;
                    inEdges.add(edge);
                }
            }

            outEdges.add(outEdge);
        }
        
        
    }
    
    public void addInEdge(Edge e){
        inEdges.add(e);
    }
    
    public void print(){
        System.out.printf("Node with %s %d edges\n",name,outEdges.size() + inEdges.size());
        System.out.println("Out edges:");
        for(Edge e:outEdges){
            e.print();
        }
        System.out.println("In edges:");
        for(Edge e:inEdges){
            e.print();
        }
    }
    
    public void printName(){
        System.out.println(name);
    }
}