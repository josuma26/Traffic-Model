package trafficcongestion;

import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author lixiaodan
 */
public class Car {
    int x,y,length,width,Speed,Acceleration,Direction;
    
    
    public Car(int x,int y,int length,int width){
        this.x = x;
        this.y = y;
        this.length = length;
        this.width = width;
        Speed = 0;
        Acceleration = 0;
    }
    
    public void setDirection(int d){
        Direction = d;
    }
    public void SetSpeed(int Tmp){
        this.Speed=Tmp;
        }
    public void SetAcceleration(int Tmp){
        this.Acceleration=Tmp;
        }
    public void Turn(int Tmp){
        this.Direction=Tmp;
        }
    public void update(int deltaT){
        this.Speed += deltaT*this.Acceleration;
        int DeltaD= this.Speed*deltaT;
        switch(this.Direction){
            case 0:
                this.y +=DeltaD;   //0=Up to North,2:South
                break;
            case 2:
                this.y -=DeltaD;       //2=Downn to South
                break;
            case 1:
                this.x +=DeltaD;       //1=East
                break;
            case 3:
                this.x -=DeltaD;       //3=West
                break;
        }
        System.out.printf("Location is at: X:%d and Y:%d\n",this.x,this.y);
        }
    
}
