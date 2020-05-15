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
    protected int x,y;
    protected boolean selfDriving = true,intelligent = false,done = false;
    
    private int rate = 1;
    
    public int WINDOW_WIDTH = 1700, WINDOW_HEIGHT = 1400;
    
    protected int totalCars;
    public void run(){
        startTime = seconds();
        Timer timer = new Timer(this.rate,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if (selfDriving){
                    updateAuto();
                }
                else{
                    update();
                }
                if (done){
                    System.out.println(seconds() - startTime);
                    System.exit(1);
                    
                }
                repaint();                
            }
        });
        timer.start();
        
    }
    
    public void update(){
        
    }
    
    public void updateAuto(){
        
    }
    
    public void initialize(){
        
    }
    
    public void setMode(boolean autonomous){
        this.selfDriving = autonomous;
    }
    
    public void setRefreshRate(int rate){
        this.rate = rate;
    }
    
    protected long seconds(){
        return System.nanoTime()/1000000000;
    }
    
    protected void drawTimer(Graphics g){
        TrafficGraphics.drawTimer(g, seconds(), startTime);
    }
    
    protected int left(Navigation nav){
        int left = 0;
        for(Node n:nav.endpoints){
            for(Edge e:n.inEdges){
                left += e.lane.left;
            }
        }
        return left;
    }
    
    
}
