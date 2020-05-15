package trafficmodel;

import java.util.ArrayList;
import java.util.Arrays;
import trafficmodel.Edge;
import trafficmodel.Node;

public class Path{
    double length;
    ArrayList<Node> steps;
    ArrayList<Lane> connections;
    
    public Path(Node start){
        steps = new ArrayList<>();
        connections = new ArrayList<>();
        steps.add(start);
        length = 0;
    }
    
    public Path(Lane[] connections){
        this.connections = new ArrayList<>();
        for(Lane l:connections){
            this.connections.add(l);
        }
    }
    
    
    public Path(ArrayList<Node> nodes){
        steps = new ArrayList<>();
        connections = new ArrayList<>();
        length = 0;
        steps.add(nodes.get(1));
        for(Node n:nodes.subList(2, nodes.size())){
            this.add(n);
        }
    }
    
    public Path(){
        
    }
    
    public Node beforeSelf(){
        if (steps.size() > 1){
            return steps.get(steps.size() - 2);
        }
        else{
            return null;
        }
        
    }
    
    public void add(Node step){
        Node last = steps.get(steps.size()-1);
        steps.add(step);
        for(Edge e:step.inEdges){
            if (e.in.equals(last)){
                length += e.lane.length;
                connections.add(e.lane);
            }
        }
    }
    
    public Path clone(){
        Path newPath = new Path();
        newPath.steps = (ArrayList<Node>)this.steps.clone();
        newPath.length = length;
        newPath.connections = (ArrayList<Lane>)this.connections.clone();
        return newPath;
    }
}