/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.util.ArrayList;

/**
 *
 * @author jsula
 */
public class Node {
    ArrayList<Number[]> connections;
    String name;
    Integer index;
    
    public Node(String names,int index){
        this.name = name;
        this.index = index;
        
    }
    
    public Node(String name,int index,ArrayList<Number[]> connections){
        this.name = name;
        this.connections = connections;
        this.index = index;
    }
}
