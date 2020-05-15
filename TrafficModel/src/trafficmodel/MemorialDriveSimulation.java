/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficmodel;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.Arrays;

/**
 *
 * @author jsula
 */
public class MemorialDriveSimulation extends Model{
    private ThreeWayIntersection riverMemorial,riverBackstone,riverPutnam,westernMemorial,westernPutnam,westernHew;
    private TIntersection westernBackstone,callenderHew,memorialHingham;
    private TwoWayTIntersection complete;
    
    private Lane memorial,backstone,riverPutnamDown,riverPutnamUpRight,riverPutnamDownRight,
            westernHewDown,westernHewUpRight,westernHewDownRight,
            callenderHewUpRight,callenderHewDownRight,
            extraLane;
    
    private double accel = 3,dist = 40,maxSpeed = 40;
    private int x = 0,y = 0,laneWidth = 25,jWidth = 50,carWidth = 15,carHeight = 17,separation = (int)(laneWidth - carWidth)/2;
    
    private Navigation nav;
    
    public MemorialDriveSimulation(){
        this.WINDOW_WIDTH = 2200;
        riverMemorial = new ThreeWayIntersection(accel,dist,maxSpeed,x,y + 600,new int[]{150,550,200,200},laneWidth,jWidth,"River-Memorial");
        memorial = new Lane(Math.PI/2,maxSpeed,laneWidth,150,new Point2D.Double(x + 225,y + 1350));
        riverMemorial.addLoneLane(memorial,1,0,"Rive-memorial down lane");
        riverMemorial.entryIndices.add(new int[]{2,3});
        
        riverBackstone = new ThreeWayIntersection(accel,dist,maxSpeed,x + 250,y + 600,new int[]{150,550,650,650},laneWidth,jWidth,"River-Backstone");
        backstone = new Lane(Math.PI/2,maxSpeed,laneWidth,150,new Point2D.Double(x + 925,y + 1350));
        riverBackstone.addLoneLane(backstone,1,0,"River-backstone down lane");
        
        riverPutnam = new ThreeWayIntersection(accel,dist,maxSpeed,x + 950,y + 600,new int[]{150,550,550,550},laneWidth,jWidth,"River-Putnam");
        riverPutnamDown = new Lane(Math.PI/2,maxSpeed,laneWidth,150,new Point2D.Double(x + 1525,y + 1350));
        riverPutnamUpRight = new Lane(0,maxSpeed,laneWidth,500,new Point2D.Double(x + 2050,y + 1125 + laneWidth));
        riverPutnamDownRight = new Lane(0,maxSpeed,laneWidth,500,new Point2D.Double(x + 2050,y + 1125 + jWidth));
        riverPutnam.addLoneLane(riverPutnamDown, 1,0, "River-putnam down lane");
        riverPutnam.addLanePair(riverPutnamUpRight,0,"River-putnam sideways up right lane");
        riverPutnam.addLoneLane(riverPutnamDownRight,0,5,"River-putnam sideways down right lane");
        
        
        westernMemorial = new ThreeWayIntersection(accel,dist,maxSpeed,x,y + 300,new int[]{550,250,200,200},laneWidth,jWidth,"Western-Memorial");
        westernMemorial.entryIndices.add(new int[]{2,3});
        
        westernBackstone = new TIntersection(accel,dist,maxSpeed,x + 250,y + 550,new int[]{550,650,650},laneWidth,jWidth,"Western-Backstone");
        
        westernPutnam = new ThreeWayIntersection(accel,dist,maxSpeed,x + 950,y  +300,new int[]{550,250,550,550},laneWidth,jWidth,"Western-Putnam");
        
        westernHew = new ThreeWayIntersection(accel,dist,maxSpeed,x + 1550,y + 300,new int[]{300,250,400,400},laneWidth,jWidth,"Western-hew");
        westernHewDown = new Lane(Math.PI/2,maxSpeed,laneWidth,300,new Point2D.Double(x + 1975,y + 900));
        westernHewUpRight = new Lane(0,maxSpeed,laneWidth,200,new Point2D.Double(x + 2200,y + 525 + laneWidth));
        westernHewDownRight = new Lane(0,maxSpeed,laneWidth,200,new Point2D.Double(x + 2200,y + 525 + jWidth));
        westernHew.addLoneLane(westernHewDown, 1, 0, "Western-hew down lane");
        westernHew.addLanePair(westernHewUpRight,0,"Western-hew sideways up right lane");
        westernHew.addLoneLane(westernHewDownRight,0,5,"Western-hew sideways down right lane");
        
        callenderHew = new TIntersection(accel,dist,maxSpeed,x + 1550,y + 250,new int[]{250,400,400},laneWidth,jWidth,"Callender-Hew");
        callenderHewUpRight = new Lane(0,maxSpeed,laneWidth,400,new Point2D.Double(x + 2400,y + 225 + laneWidth));
        callenderHewDownRight = new Lane(0,maxSpeed,laneWidth,400,new Point2D.Double(x + 2400,y + 225 + jWidth));
        callenderHew.addLanePair(callenderHewUpRight, 0, "Callender-hew sidewasy up right lane");
        callenderHew.addLoneLane(callenderHewDownRight,0,3,"Callender-Hew sideways down right lane");

        memorialHingham = new TIntersection(accel,dist,maxSpeed,x + 250,y + 250,new int[]{250,1250,1250},laneWidth,jWidth,"Memorial-Hinghma");
        
        complete = new TwoWayTIntersection(accel,dist,maxSpeed,200,0,new int[]{250,250},laneWidth,jWidth,"Extra");
        extraLane = new Lane(3*Math.PI/2,maxSpeed,laneWidth,250,new Point2D.Double(200 + laneWidth,0));
        complete.addLoneLane(extraLane,1,1,"Extra Lane");
        
        riverMemorial.setConnections(westernMemorial.lanes.get(0), riverBackstone.lanes.get(2), riverBackstone.lanes.get(3), memorial);
        riverBackstone.setConnections(westernBackstone.lanes.get(0), riverPutnam.lanes.get(2), riverPutnam.lanes.get(3), backstone);
        riverPutnam.setConnections(westernPutnam.lanes.get(0), riverPutnamDownRight, riverPutnamUpRight, riverPutnamDown);
        westernMemorial.setConnections(complete.lanes.get(0), westernBackstone.lanes.get(1), westernBackstone.lanes.get(2) , riverMemorial.lanes.get(1));
        westernBackstone.setConnections(westernPutnam.lanes.get(2), westernPutnam.lanes.get(3), riverBackstone.lanes.get(1));
        westernPutnam.setConnections(memorialHingham.lanes.get(0),westernHew.lanes.get(2), westernHew.lanes.get(3), riverPutnam.lanes.get(1));
        westernHew.setConnections(callenderHew.lanes.get(0), westernHewDownRight, westernHewUpRight, westernHewDown);
        memorialHingham.setConnections(callenderHew.lanes.get(1), callenderHew.lanes.get(2), westernPutnam.lanes.get(1));
        callenderHew.setConnections(callenderHewDownRight, callenderHewUpRight, westernHew.lanes.get(1));
        complete.setConnections(memorialHingham.lanes.get(1), memorialHingham.lanes.get(2), extraLane, westernMemorial.lanes.get(1));
        
        nav = new Navigation(riverMemorial,riverBackstone,riverPutnam,westernMemorial,westernBackstone,westernPutnam,westernHew,callenderHew,memorialHingham,complete);
        
    }
    
    
    @Override
    public void initialize(){
        int n = 100;
        totalCars = n*5;
        riverMemorial.scheduleCars(nav,0,n,carWidth,carHeight,separation,2,3,4,6,7,8,9);
        riverMemorial.scheduleCars(nav, 1, n, carWidth, carHeight, separation, 2,3,4,6,7,8,9);
        riverMemorial.scheduleCars(nav, 2, n, carWidth, carHeight, separation, 4,6,7,8);
        riverMemorial.scheduleCars(nav,3,n,carWidth,carHeight,separation,6,7,8);
        riverMemorial.scheduleCars(nav,9,n,carWidth,carHeight,separation,0,2,3,4,6,7,8);
        
    }
    @Override
    public void update(){
        double interval = 0.05;
        riverMemorial.update(interval,nav,intelligent);
        riverBackstone.update(interval,nav,intelligent);
        riverPutnam.update(interval,nav,intelligent);
        westernMemorial.update(interval,nav,intelligent);
        westernBackstone.update(interval,nav,intelligent);
        westernPutnam.update(interval,nav,intelligent);
        westernHew.update(interval,nav,intelligent);
        callenderHew.update(interval,nav,intelligent);
        memorialHingham.update(interval,nav,intelligent);
        complete.update(interval,nav,intelligent);
        
        done = left(nav) == totalCars;
    }
    
    @Override
    public void updateAuto(){
        double interval = 0.05;
        riverMemorial.updateAuto(interval,nav,intelligent);
        riverBackstone.updateAuto(interval,nav,intelligent);
        riverPutnam.updateAuto(interval,nav,intelligent);
        westernMemorial.updateAuto(interval,nav,intelligent);
        westernBackstone.updateAuto(interval,nav,intelligent);
        westernPutnam.updateAuto(interval,nav,intelligent);
        westernHew.updateAuto(interval,nav,intelligent);
        callenderHew.updateAuto(interval,nav,intelligent);
        memorialHingham.updateAuto(interval,nav,intelligent);
        complete.updateAuto(interval,nav,intelligent);
        
       done =  left(nav) == totalCars;
    }
    
    
    @Override
    public void paint(Graphics g){
        drawTimer(g);
        riverMemorial.paint(g);
        riverBackstone.paint(g);
        riverPutnam.paint(g);
        westernMemorial.paint(g);
        westernBackstone.paint(g);
        westernPutnam.paint(g);
        westernHew.paint(g);
        callenderHew.paint(g);
        memorialHingham.paint(g);
        complete.paint(g);
    }
    
    
}
