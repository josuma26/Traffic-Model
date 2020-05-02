package trafficmodel;

import java.util.HashMap;
import trafficmodel.Lane;

public class Edge{
    Lane lane;
    Node in,out;
    
    public Edge(Lane l,Node from,HashMap createdNodes){
        this.in = from;
        this.lane = l;
        
        
        if (l.out != null && createdNodes.get(l.out) != null){
            Node n = (Node) createdNodes.get(l.out);
            this.out = n;
            n.addInEdge(this);
        }
        
    }
    
    
    
    public void print(){
        System.out.printf("\t%s\n",lane.name);
  
        if (out == null){
            System.out.println("Out null");
        }
        
    }
    
}