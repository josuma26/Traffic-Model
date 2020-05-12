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
    ThreeWayIntersection riverMemorial,riverBackstone,riverPutnam,westernMemorial,westernPutnam,westernHew;
    TIntersection westernBackstone,callenderHew,memorialHingham;
    TwoWayTIntersection complete;
    
    Lane memorial,backstone,riverPutnamDown,riverPutnamUpRight,riverPutnamDownRight,
            westernHewDown,westernHewUpRight,westernHewDownRight,
            callenderHewUpRight,callenderHewDownRight,
            extraLane;
    
    double accel = 5,dist = 40,maxSpeed = 25;
    int x = 0,y = 0,laneWidth = 50,jWidth = 100,carWidth = 30,carHeight = 32;
    
    Navigation nav;
    public MemorialDriveSimulation(){
        riverMemorial = new ThreeWayIntersection(accel,dist,maxSpeed,x,y + 650,new int[]{100,500,200,200},laneWidth,jWidth,"River-Memorial");
        memorial = new Lane(Math.PI/2,maxSpeed,laneWidth,100,new Point2D.Double(x + 250,y + 1350));
        riverMemorial.addLoneLane(memorial,1,0,"Rive-memorial down lane");
        riverMemorial.entryIndices.add(new int[]{2,3});
        
        riverBackstone = new ThreeWayIntersection(accel,dist,maxSpeed,x + 300,y + 650,new int[]{100,500,400,400},laneWidth,jWidth,"River-Backstone");
        backstone = new Lane(Math.PI/2,maxSpeed,laneWidth,100,new Point2D.Double(x + 750,y + 1350));
        riverBackstone.addLoneLane(backstone,1,0,"River-backstone down lane");
        
        riverPutnam = new ThreeWayIntersection(accel,dist,maxSpeed,x + 800,y + 650,new int[]{100,500,200,200},laneWidth,jWidth,"River-Putnam");
        riverPutnamDown = new Lane(Math.PI/2,maxSpeed,laneWidth,100,new Point2D.Double(x + 1050,y + 1350));
        riverPutnamUpRight = new Lane(0,maxSpeed,laneWidth,250,new Point2D.Double(x + 1350,y + 1100 + laneWidth));
        riverPutnamDownRight = new Lane(0,maxSpeed,laneWidth,250,new Point2D.Double(x + 1350,y + 1100 + jWidth));
        riverPutnam.addLoneLane(riverPutnamDown, 1,0, "River-putnam down lane");
        riverPutnam.addLanePair(riverPutnamUpRight,0,"River-putnam sideways up right lane");
        riverPutnam.addLoneLane(riverPutnamDownRight,0,5,"River-putnam sideways down right lane");
        
        
        westernMemorial = new ThreeWayIntersection(accel,dist,maxSpeed,x,y + 350,new int[]{500,200,200,200},laneWidth,jWidth,"Western-Memorial");
        westernMemorial.entryIndices.add(new int[]{2,3});
        
        westernBackstone = new TIntersection(accel,dist,maxSpeed,x + 300,y + 550,new int[]{500,400,400},laneWidth,jWidth,"Western-Backstone");
        
        westernPutnam = new ThreeWayIntersection(accel,dist,maxSpeed,x + 800,y  +350,new int[]{500,200,200,200},laneWidth,jWidth,"Western-Putnam");
        
        westernHew = new ThreeWayIntersection(accel,dist,maxSpeed,x + 1100,y + 350,new int[]{300,200,350,350},laneWidth,jWidth,"Western-hew");
        westernHewDown = new Lane(Math.PI/2,maxSpeed,laneWidth,300,new Point2D.Double(x + 1500,y + 950));
        westernHewUpRight = new Lane(0,maxSpeed,laneWidth,100,new Point2D.Double(x + 1650,y + 500 + laneWidth));
        westernHewDownRight = new Lane(0,maxSpeed,laneWidth,100,new Point2D.Double(x + 1650,y + 500 + jWidth));
        westernHew.addLoneLane(westernHewDown, 1, 0, "Western-hew down lane");
        westernHew.addLanePair(westernHewUpRight,0,"Western-hew sideways up right lane");
        westernHew.addLoneLane(westernHewDownRight,0,5,"Western-hew sideways down right lane");
        
        callenderHew = new TIntersection(accel,dist,maxSpeed,x + 1100,y + 250,new int[]{200,350,350},laneWidth,jWidth,"Callender-Hew");
        callenderHewUpRight = new Lane(0,maxSpeed,laneWidth,100,new Point2D.Double(x + 1650,y + 200 + laneWidth));
        callenderHewDownRight = new Lane(0,maxSpeed,laneWidth,100,new Point2D.Double(x + 1650,y + 200 + jWidth));
        callenderHew.addLanePair(callenderHewUpRight, 0, "Callender-hew sidewasy up right lane");
        callenderHew.addLoneLane(callenderHewDownRight,0,3,"Callender-Hew sideways down right lane");

        memorialHingham = new TIntersection(accel,dist,maxSpeed,x + 300,y + 250,new int[]{200,700,700},laneWidth,jWidth,"Memorial-Hinghma");
        
        complete = new TwoWayTIntersection(accel,dist,maxSpeed,200,0,new int[]{200,250},laneWidth,jWidth,"Extra");
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
        //nav.print();
        
        nav.printEndPoints();
        
        int n = 100;
        
        
        
        
        
        run();
    }
    
    @Override
    public void initialize(){
        int n = 20;
        if(selfDriving){
            riverMemorial.scheduleCars(nav, 0, 4, n,carWidth,carHeight,10);
            riverMemorial.scheduleCars(nav,2,6,n,carWidth,carHeight,10);
            riverMemorial.scheduleCars(nav,3,7,n,carWidth,carHeight,10);
            riverMemorial.scheduleCars(nav,1,8,n,carWidth,carHeight,10);
            riverMemorial.scheduleCars(nav,9,7,n,carWidth,carHeight,10);
        }
        else{
            riverMemorial.sendCarsFromTo(nav, 0, 4, n, carWidth, carHeight, 10, 10, 20);
            riverMemorial.sendCarsFromTo(nav, 2, 6, n, carWidth, carHeight, 10, 10, 20);
            riverMemorial.sendCarsFromTo(nav, 3, 7, n, carWidth, carHeight, 10, 10, 20);
            riverMemorial.sendCarsFromTo(nav, 1, 8, n, carWidth, carHeight, 10, 10, 20);
            riverMemorial.sendCarsFromTo(nav, 9, 7, n, carWidth, carHeight, 10, 10, 20);
            riverMemorial.sendCarsFromTo(nav, 5, 4, n, carWidth, carHeight, 10, 10, 20);
        }
    }
    @Override
    public void update(){
        double interval = 0.05;
        riverMemorial.update(interval);
        riverBackstone.update(interval);
        riverPutnam.update(interval);
        westernMemorial.update(interval);
        westernBackstone.update(interval);
        westernPutnam.update(interval);
        westernHew.update(interval);
        callenderHew.update(interval);
        memorialHingham.update(interval);
        complete.update(interval);
        
    }
    
    @Override
    public void updateAuto(){
        double interval = 0.05;
        riverMemorial.updateAuto(interval);
        riverBackstone.updateAuto(interval);
        riverPutnam.updateAuto(interval);
        westernMemorial.updateAuto(interval);
        westernBackstone.updateAuto(interval);
        westernPutnam.updateAuto(interval);
        westernHew.updateAuto(interval);
        callenderHew.updateAuto(interval);
        memorialHingham.updateAuto(interval);
        complete.updateAuto(interval);
    }
    
    
    @Override
    public void paint(Graphics g){
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
