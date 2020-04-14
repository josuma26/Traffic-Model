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
    ArrayList<Nodea> nodes;
    ArrayList<Nodea> endpoints;
    public Navigation(CitySection ... sections){
        nodes = new ArrayList<>();
        endpoints = new ArrayList<>();
        HashMap createdNodes = new HashMap();
        
        for(CitySection section:sections){
            Joint joint = section.joint;
            if (createdNodes.get(this) == null){
                Nodea node = new Nodea(joint,createdNodes);
                nodes.add(node);
                createdNodes.put(joint,node);
            }
            
        }
        
        for(Nodea n:nodes){
            for(Edge e:n.outEdges){
                if (e.lane.out != null){
                    Nodea outNode = (Nodea) createdNodes.get(e.lane.out);
                    e.out = outNode;
                    if (!outNode.inEdges.contains(e)){
                        outNode.addInEdge(e);
                    }
                }
            }
        }
        
        for(CitySection section:sections){
            for(int[] indices:section.entryIndices){
                Nodea n = new Nodea(section,indices,createdNodes);
                nodes.add(n);
                endpoints.add(n);
            }
        }
    }
    
    public void getPath(Nodea a,Nodea b){
        ArrayList<Nodea> orderedNodes = new ArrayList<>();
        orderedNodes.add(a);
        
        HashMap trackedNodes = new HashMap();
        trackedNodes.put(a, 0);
        
        while(trackedNodes.get(b) == null){
            Nodea node = orderedNodes.get(0);
            boolean done = true;
            for(Edge e:node.outEdges){
                if (trackedNodes.get(e.out) == null){
                    trackedNodes.put(e.out,e.lane.length);
                    for(int i = 0;i<=orderedNodes.size();i++){
                        if (i == orderedNodes.size()){
                            orderedNodes.add(e.out);
                            break;
                        }
                        else if (e.lane.length < (Integer)trackedNodes.get(orderedNodes.get(i))){
                            orderedNodes.add(i,e.out);
                        }
                    }
                  
                    done = false;
                    break;
                }
                else{
                    
                }
                
            }
            if (done){
                orderedNodes.remove(node);
                System.out.println(orderedNodes.size());
                break;
            }
            
        }
        System.out.println(trackedNodes.get(b));
        
        
    }
    
    public void print(){
        System.out.println("Navigation system:");
        for(Nodea n:nodes){
            n.print();
            System.out.println();
        }
    }
}

class Patha{
    double length;
    ArrayList<Nodea> steps;
    
    public Patha(Nodea start){
        steps = new ArrayList<>();
        steps.add(start);
        length = 0;
    }
    
    public void add(Nodea step){
        Nodea last = steps.get(steps.size() - 1);
        steps.add(step);
        for(Edge e:step.inEdges){
            if (e.in.equals(last)){
                length += e.lane.length;
            }
        }
    }
}
class Nodea{
    ArrayList<Edge> inEdges,outEdges;
    
    public Nodea(Joint j,HashMap createdNodes){
        outEdges = new ArrayList<>();
        inEdges = new ArrayList<>();
        Lane[] outLanes = j.outLanes;
        for(Lane lane:outLanes){
            Edge edge = new Edge(lane,this,createdNodes);
            outEdges.add(edge);
        }
    }
    
    public Nodea(CitySection section,int[] indices,HashMap createdNodes){
        inEdges = new ArrayList();
        outEdges = new ArrayList();
        Lane out = section.lanes.get(indices[0]);
        Lane in = section.lanes.get(indices[1]);
        
        Nodea node = (Nodea) createdNodes.get(in.out);
        Edge inEdge = new Edge(in,this,createdNodes);
        Edge outEdge = new Edge(out, node,createdNodes);
        
        inEdges.add(inEdge);
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

class Edge{
    Lane lane;
    Nodea in,out;
    
    public Edge(Lane l,Nodea from,HashMap createdNodes){
        this.in = from;
        this.lane = l;
        
        
        if (l.out != null && createdNodes.get(l.out) != null){
            Nodea n = (Nodea) createdNodes.get(l.out);
            this.out = n;
            n.addInEdge(this);
        }
  
    }
    
    
    
    public void print(){
        System.out.printf("\t%s\n",lane.name);
    }
    
}