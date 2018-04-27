package pack;


import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class ColorSensor {
    EV3ColorSensor ColorSensor;
    SampleProvider ColorProvider;
    float[] ColorSample;
	public static void main(String[] args) {
		new ColorSensor();

	}
	public ColorSensor() {
		Port s3 = LocalEV3.get().getPort("S3");
		ColorSensor = new EV3ColorSensor(s3);
		ColorProvider = ColorSensor.getColorIDMode();
		// ColorProvider = ColorSensor.getRGBMode();
		ColorSample = new float[ColorProvider.sampleSize()];
		
		while (Button.ESCAPE.isUp()) {
		/*	ColorProvider.fetchSample(ColorSample, 0);
			System.out.println("R" + ColorSample[0]);
			System.out.println("G" + ColorSample[1]);
			System.out.println("B" + ColorSample[2]); 
		*/
			int GetColorID = ColorSensor.getColorID();
			Delay.msDelay(250);
			
		}
	}
}
