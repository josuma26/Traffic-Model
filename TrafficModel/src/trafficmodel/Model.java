/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author jsula
 */
public class Model extends Component{
    //every lane mustoverride update and paint
    protected double startTime;
    int x,y;
    
    
    public void run(){
        startTime = seconds();
        Timer timer = new Timer(5,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //update();
                updateAuto();
                repaint();                
            }
        });
        timer.start();
        
    }
    
    public void update(){
        
    }
    
    public void updateAuto(){
        
    }
    protected long seconds(){
        return System.nanoTime()/1000000000;
    }
    
    
}
