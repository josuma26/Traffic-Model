/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficcongestion;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author jsula
 */
public class Path {
    ArrayList<Node> nodes;
    double length;
    
    public Path(){
        nodes = new ArrayList<>();
        length = 0;
    }
    
    public Path(Graph g,int[] indexes){
        nodes = new ArrayList<>();
        length = 0;
        int index = indexes[0];
        for(int i = 0;i<indexes.length;i++){
            Node thisNode = g.nodes.get(indexes[i]);
            for(Number[] connection:thisNode.connections){
                if ((int)connection[0] == index){
                    length += (double)connection[1];
                    break;
                }
            }
            nodes.add(thisNode);
            index = indexes[i];
        }
    }
    public Path(Node[] inputNodes){
        nodes = new ArrayList<>();
        length = 0;
        int index = inputNodes[0].index;
        for(Node n:inputNodes){
            nodes.add(n);
            for(Number[] connection:n.connections){
                if ((int)connection[0] == index){
                    length += (double)connection[1];
                    break;
                }
            }
            index = n.index;
        }
        nodes.addAll(Arrays.asList(inputNodes));
        
    }
    
    public void addNode(Node n){
        if (this.nodes.size() != 0){
            int lastIndex = this.nodes.get(this.nodes.size()-1).index;
            for(Number[] connection:n.connections){
                if ((int)connection[0] == lastIndex){
                    length += (double)connection[1];
                }
            }
           
        }
        else{
            length = 0;
        }
        this.nodes.add(n);
       
        
    }
    
    public Path copy(){
        Path newCopy = new Path();
        for(Node n:this.nodes){
            newCopy.addNode(n);
        }
        return newCopy;
    }
    
    public void print(){
        for (Node n:this.nodes){
            System.out.print(" " + n.name);
        }
        System.out.println(" " + this.length);
    }
    
}
