package trafficcongestion;

import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author lixiaodan
 */
public class Car {
    int x,y,length,width,Speed,Acceleration;
    
    
    public Car(int x,int y,int length,int width){
        Scanner Input = new Scanner(System.in);

        this.x = x;
        this.y = y;
        this.length = length;
        this.width = width;
        Speed = 0;
        Acceleration = 0;
    }
    
    
    public void SetSpeed(int Tmp){
        this.Speed=Tmp;
        }
    public void SetAcceleration(int Tmp){
        this.Acceleration=Tmp;
        }
    
    public void update(int deltaT,int max){
        this.Speed += deltaT*this.Acceleration;
        if (this.Speed > max){
            this.Speed = max;
        }
        int DeltaD= this.Speed*deltaT;
        this.y +=DeltaD;
        
        System.out.printf("Location is at: X:%d and Y:%d Speed: %d Acceleration %d\n",this.x,this.y,this.Speed,this.Acceleration);
        }
    
}
