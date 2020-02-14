    
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficcongestion;


/**
 *
 * @author alanwang
 */
import java.awt.Point;
import java.util.ArrayList;

public class Lane {
    private int trafficflow = 0;
    private int vehicledensity = 0;
    public int MAX_SPEED,LANE_LENGTH,LANE_WIDTH;
    
    private int DIRECTION;
    
    private ArrayList<Car> cars;
    
    private boolean go = false;
    
    private Point point;
    
    private Joint out = null;
    public Lane(int speed, int length, int width, int direction,Point p){
        MAX_SPEED = speed;
        LANE_LENGTH = length;
        LANE_WIDTH = width;
        DIRECTION = direction;
        point = p;
        cars = new ArrayList<>();
    }
    public int setTrafficFlow(int carNum, int time){
        trafficflow = (carNum * 3600)/time;
        return trafficflow;
        // trafficflow = vehicle flow (no. vehicles per hour)
        // carNum = no. vehicles passing in t seconds
        // time = time for passing vehicles (s)


    }
    public int setVehicleDensity(int carNum, int length){
        vehicledensity = (carNum * 5280)/length;
        return vehicledensity;
        // k = vehicle density (vehicles per mile)
        // n = no. of vehicles occupying a length l of the road
        // l = the length of road occupied by the vehicles (ft)
    }
    public void addCars(ArrayList<Car> carList){
        cars = carList;
    }
    
    public void addCar(Car c){
        cars.add(c);
    }
    public void update(int timeInterval,int a,int d){
        ArrayList<Car> toRemove = new ArrayList<>();
        if (go){
            for (Car vehicle : cars){
                int index = cars.indexOf(vehicle);
                if (index == 0){
                    vehicle.SetAcceleration(a);
                }
                else{
                    Car inFront = cars.get(index - 1);
                    int distance = distance(vehicle,inFront);
                    if (distance >= d){
                        vehicle.SetAcceleration(a);
                    }
                
                }
                
                vehicle.update(timeInterval,MAX_SPEED);
                if (vehicle.x > point.getX()){
                    toRemove.add(vehicle);
                    if (out != null){
                        out.switchLane(vehicle, this);
                    }
                }
            }
            
        }
        else{
            for(Car c:cars){
                c.update(timeInterval,MAX_SPEED);
            }
        }
        cars.removeAll(toRemove);
    }
        
        
    public void setOut(Joint out){
        this.out = out;
    }
    
    private int distance(Car c1,Car c2){
        switch(this.DIRECTION){
            case 0:
                return Math.abs(c1.y - c2.y - c1.width);  //0=Up to North,2:South
            case 2:
                return Math.abs(c2.y - c1.y - c2.width);       //2=Downn to South
            case 1:
                return Math.abs(c1.x - c2.x - c1.length);       //1=East
            case 3:
                return Math.abs(c2.x - c1.x - c2.length);     //3=West
        }
        return 0;
    }
    
    
    public void toggleGo(){
        go = !go;
    }
}
 




