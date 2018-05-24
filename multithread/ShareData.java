package test;

import java.io.File;

import lejos.hardware.Bluetooth;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.remote.nxt.NXTCommConnector;
import lejos.remote.nxt.NXTConnection;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.DestinationUnreachableException;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;
import lejos.robotics.pathfinding.ShortestPathFinder;
import lejos.utility.Delay;
import navigator_0517.PathFollower;

public class ShareData /*implements Runnable*/ {
    /*initialize two wheels*/
	public Wheel wheel2 = WheeledChassis.modelWheel(Motor.B, 55).offset(53);
	public Wheel wheel1 = WheeledChassis.modelWheel(Motor.C, 55).offset(-53);
	public Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
    /*set two wheels as pilot*/
	public MovePilot pilot = new MovePilot(chassis);   
	/*setup default pose provider (0,0,0)*/
	public OdometryPoseProvider poseProvider = new OdometryPoseProvider(pilot);
	public Navigator navigator = new Navigator(pilot,poseProvider);	

    /*set up new map*/	
    public static create_map create1 = new create_map();  
    public static LineMap map = create1.createmap();
    /*set up new blue tooth server*/
	public Blueserver newbt = new Blueserver();
	
	private Pose pose = new Pose();
	public Waypoint next = new Waypoint(0,0);
    
	public int cho;	 	
	
	/*sensor set up*/
	public	final Brick brick = BrickFinder.getDefault();
	public  final Port s1 = brick.getPort("S1");
	public  final Port s2 = brick.getPort("S2");
	//public  final Port s3 = brick.getPort("S3");
	public	final EV3UltrasonicSensor sensor_s1 = new EV3UltrasonicSensor(s1);			
	public final ultrasensor ultra_s1 = new ultrasensor(sensor_s1);
	
	//public final GyroSensor    gyro = new GyroSensor(SensorPort.S3); 
	public final EV3ColorSensor sensor_s2 = new EV3ColorSensor(s2);
	public final ColorSensor color_s2 = new ColorSensor(sensor_s2);
	SampleProvider ColorProvider;		   
	float[] ColorSample;
	boolean  pick_flag;  
	boolean finish_flag = false;
	int choosepath = 0;
	
	/*
	 * 
	 * 
	 * */
	public  void carrun() throws DestinationUnreachableException { 
		ShortestPathFinder finder = new ShortestPathFinder(PathFollower.map);
		finder.lengthenLines(5);
	
	    while(pick_flag == false) {}//wait until the robot get bluetooth message    
		
	    Pose start = new Pose(-300, -200 , 90);//create the load point pose
		if (choosepath == 1) {
		    Waypoint goal = new Waypoint(-300, 480);
			Path path = finder.findRoute(start, goal);         
		    newPath(path);
		}
		else if (choosepath == 2) {
		    Waypoint goal = new Waypoint(300, 480);
			Path path = finder.findRoute(start, goal);         
		    newPath(path);
		}
		else if (choosepath == 3) {
		    Waypoint goal = new Waypoint(570, -250);
			Path path = finder.findRoute(start, goal);         
		    newPath(path);
	    }
		else if (choosepath == 4) {
		    Waypoint goal = new Waypoint(570, 250);
			Path path = finder.findRoute(start, goal);         
		    newPath(path);
		}
		playwav("PeasantYesAttack1.wav");
		navigate();
		regumotordrive(-40);
		finish_flag = true;	
    }

	/*
	 * 
	 * 
	 * */
    public  void motorrun() {
    	int red_count = 0;
        while(pick_flag == false) {}//wait for start signal
    	while(true) {
          if(color_s2.getcolormode()==0 && red_count == 0) {
        	regumotordrive(40);
            Delay.msDelay(1000);
            red_count = red_count + 1;
          }	
        }
    }
    
    
    public  void ultrarun() {}
    
	/*
	 * 
	 * 
	 * */	
    public  void btserver() {

        pick_flag = false;
        int hend =0;
        int lend =0;
        NXTCommConnector connector = Bluetooth.getNXTCommConnector();
        NXTConnection connection = connector.waitForConnection(0, NXTConnection.RAW);	        
        System.out.println("Connected");
        playwav("KnightReady1.wav");

        while(pick_flag == false) {

        	newbt.isReceived(connection,pick_flag, choosepath);
	     	if (Button.ESCAPE.isDown()) { 
	     	    tryclose(connection);
		    }	            
        }
        while(!navigator.isMoving()) {
        }// to avoid reading null with get pose
        while(true) {
	     	if (Button.ESCAPE.isDown() || finish_flag == true) { 
		     	 tryclose(connection);
		    }
            try{
	     	    pose = navigator.getPoseProvider().getPose();
        	    hend = (int)pose.getX();
        	    lend = (int)pose.getY();        	
        	    newbt.send(connection,hend);
        	    newbt.send(connection,lend);
            } catch (Exception ioe) {}
           Delay.msDelay(1000);
        }
    }	    	

   public void tryclose(NXTConnection connection) {
       try {
           if (null != connection) {
               connection.close();
               System.exit(0);
           }
       } catch (Exception ioe) {}
   }
    
    public void newPath(Path path) {
    	navigator.setPath(path);
    }

	public void playwav(String x) {
		File file=new File(x);
	    Sound.playSample(file);
	}
	    
	public void regumotordrive(int x) {
	    Motor.D.setSpeed(30);
	    Motor.D.rotate(x);
	    Motor.D.stop();
	}
    
	public void newWaypoint(int x, int y){
	    navigator.addWaypoint(x, y);
	}
	
	public void navigate(){
		pilot.setLinearSpeed(40);
		pilot.setAngularSpeed(40);
		while(!navigator.pathCompleted()){
			navigator.followPath();
			//System.out.println(" " + (int)navigator.getPoseProvider().getPose().getX()
			//	       +  " "  + (int)navigator.getPoseProvider().getPose().getY()
			//	       +  " "  + (int)navigator.getPoseProvider().getPose().getHeading());
			   if (Button.ESCAPE.isDown()) { 
		            System.exit(0);          
		 	   }	
		}
	}

    
    
}
