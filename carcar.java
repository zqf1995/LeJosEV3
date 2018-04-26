package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import lejos.hardware.Bluetooth;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.remote.ev3.RMIRemoteBluetooth;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class carcar {
	 Wheel wheel1 = WheeledChassis.modelWheel(Motor.B, 81.6).offset(-70);//meaning of 81.6, -70
	 Wheel wheel2 = WheeledChassis.modelWheel(Motor.C, 81.6).offset(70);
	 Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
	 MovePilot pilot = new MovePilot(chassis);
   Random ran;
   touchsensor touch_s1;
   touchsensor touch_s4;
   ultrasensor ultra_s2;
   
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        BTConnector connector = new BTConnector();

        System.out.println("0. Auf Signal warten");

        NXTConnection conn = connector.waitForConnection(0, NXTConnection.RAW);

        InputStream is = conn.openInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is), 1);

        String message = "";

        while (true){

            System.out.println("1. Schleife gestartet");
            message = "";

            try {
                message = br.readLine();
                System.out.println("2. Message: " + message);
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
         new carcar();
        }
        
	}
    
	public carcar() {
	   ran = new Random();
	   Brick brick = BrickFinder.getDefault();
	   Port s1 = brick.getPort("S1");
	   Port s2 = brick.getPort("S2");
	   Port s4 = brick.getPort("S4");
	   EV3TouchSensor sensor_s1 = new EV3TouchSensor(s1);
	   EV3UltrasonicSensor sensor_s2 = new EV3UltrasonicSensor(s2);
	   EV3TouchSensor sensor_s4 = new EV3TouchSensor(s4);
	   touch_s1 = new touchsensor(sensor_s1);
	   touch_s4 = new touchsensor(sensor_s4);
	   ultra_s2 = new ultrasensor(sensor_s2);
	   pilot.forward();
	   while(true) {
		   Delay.msDelay(2);
		   if (touch_s1.pressed() || touch_s4.pressed()|| (ultra_s2.distance() < 0.3) ) {
		      pilot.stop();
		      pilot.travel(-30);
		      if (ran.nextBoolean()) {
		    	  pilot.rotate(180);
		      } else {
		    	  pilot.rotate(-180);
		      }
		      pilot.forward();		
		   }
		   if (Button.ESCAPE.isDown()) { 
			   pilot.stop();
			   sensor_s1.close();
			   sensor_s4.close();
			   sensor_s2.close();
			   System.exit(0);
		   }
	   }
	   
	}
	
}
