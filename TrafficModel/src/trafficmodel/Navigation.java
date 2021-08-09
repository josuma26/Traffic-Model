/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author jsula
 */
public class Navigation {
    ArrayList<Node> nodes;
    ArrayList<Node> endpoints;
    public Navigation(CitySection ... sections){
        nodes = new ArrayList<>();
        endpoints = new ArrayList<>();
        HashMap<Joint,Node> createdNodes = new HashMap();
        
        for(CitySection section:sections){
            Joint joint = section.joint;
            Node node = new Node(joint,createdNodes,section.name);
            nodes.add(node);
            createdNodes.put(joint,node);
            
        }
        
        for(Node n:nodes){
            for(Edge e:n.outEdges){
                if (e.lane.out != null){
                    Node outNode = (Node) createdNodes.get(e.lane.out);
                    e.out = outNode;
                    if (!outNode.inEdges.contains(e)){
                        outNode.addInEdge(e);
                    }
                }
            }
        }
        
        for(CitySection section:sections){
            for(int[] indices:section.entryIndices){
                Node n = new Node(section,indices,createdNodes);
                nodes.add(n);
                endpoints.add(n);
            }
        }
    }
    
    public Path getPath(Node a,Node b){
        ArrayList<Node> orderedNodes = new ArrayList<>();
        orderedNodes.add(a);
        
        HashMap<Node,Object[]> trackedNodes = new HashMap();
        trackedNodes.put(a, new Object[]{null,0});
        int count = 0;
        
        ArrayList<Node> done = new ArrayList<>();
        while(trackedNodes.get(b) == null){
            Node node = orderedNodes.get(0);
            Object[] d = trackedNodes.get(node);
            Node from = (Node)d[0];
            int distance = (int)d[1];
            for(Edge e:node.outEdges){
                if (e.out != null && !e.out.equals(from) && !done.contains(e.out)){
                    if (trackedNodes.get(e.out) == null){
                        trackedNodes.put(e.out,new Object[]{node,distance + e.lane.timeToCross()});                 
                    }
                    else{
                        int currentDistance = (int)trackedNodes.get(e.out)[1];
                        if (distance + e.lane.timeToCross() < currentDistance){
                            trackedNodes.replace(e.out, new Object[]{node,distance + e.lane.timeToCross()});
                        }
                    }
                    int size = orderedNodes.size();
                    for(int i = 0;i<=size;i++){
                        if (i == orderedNodes.size()){
                            orderedNodes.add(e.out);
                        }
                        else if ((int)trackedNodes.get(e.out)[1] < (int)trackedNodes.get(orderedNodes.get(i))[1]){
                            orderedNodes.add(i,e.out);
                            break;
                        }
                     } 
                }
                
            }
            orderedNodes.remove(node);
            
            
            
            done.add(node);
            count++;
        }
        ArrayList<Node> path = new ArrayList<>();
        path.add(b);
        Node from = path.get(0);
        while(from != null){
            from = (Node) trackedNodes.get(from)[0];
            path.add(0,from);
        }
        Path p = new Path(path);
        return p;
        
    }
    
    
    
    public void print(){
        System.out.println("Navigation system:");
        for(Node n:nodes){
            n.print();
            System.out.println();
        }
    }
    
    public void printEndPoints(){
        for(int i = 0;i<this.endpoints.size();i++){
            System.out.printf("index: %d Entry: %s\n",i,endpoints.get(i).name);
        }
    }
}



