/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author jsula
 */
public class Graph {
    ArrayList<Node> nodes;
    ArrayList<Path> paths;
    
    public Graph(String[] names, Number[][][] connections){
        this.nodes = new ArrayList<Node>();
      
        for(int i = 0;i<names.length;i++){
            ArrayList<Number[]> nodeConnections = new ArrayList<>();
            nodeConnections.addAll(Arrays.asList(connections[i]));
            nodes.add(new Node(names[i],i,nodeConnections));
        }
    }
    
    public void print(){
        for(Node n:nodes){
            System.out.println(n.name);
            for(int i = 0;i<n.connections.size();i++){
                System.out.printf("Connection to: %d Weight: %f\n",n.connections.get(i)[0],n.connections.get(i)[1]);
            }
        }
    }
    
    public ArrayList<Integer> getNodeIndexes(){
        ArrayList<Integer> indexes = new ArrayList<>();
        for(Node n:nodes){
            indexes.add(n.index);
        }
        return indexes;
    }
    
   public Path[] shortestPathFrom(Node start){
       Path[] paths = new Path[nodes.size()];
       
       for(int i = 0;i<paths.length;i++){
           //Node[] n = {start};
           paths[i] = new Path();
       }
       paths[start.index].addNode(start);
       
       ArrayList<Integer> unvisited = getNodeIndexes();
       
       Node current = start;
       while (!unvisited.isEmpty()){
           for(Number[] connection: current.connections){
               int index = (int)connection[0];
               if (unvisited.contains(index)){
                   double length = paths[index].length;
                   double weight = (double)connection[1];
                   
                   Path pathToCurrent = paths[current.index].copy();
              
                   if (length == 0 || (length != 0 && (pathToCurrent.length + weight) < length)){
                       paths[index] = pathToCurrent.copy();
                       paths[index].addNode(this.nodes.get(index));
                       
                   }
                   
                   
                   
               }
           }
           unvisited.remove(current.index);
           
           double lowest = 100000;
           
           for(int i:unvisited){
               if ((paths[i].length < lowest) && (paths[i].length != 0)){
                   
                   lowest = paths[i].length;
                   current = this.nodes.get(i);   
               }
               
           }
           
           
           
           
       }
       return paths;
   }
    
}
