package test;



import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.AbstractFilter;


public class ColorSensor  extends AbstractFilter{
    //EV3ColorSensor ColorSensor;
    //SampleProvider ColorProvider;
    float[] ColorSample;

	public ColorSensor(SampleProvider source) {
		super(source);
		//Port s3 = LocalEV3.get().getPort("S3");
		//ColorSensor = new EV3ColorSensor(s3);
		// ColorProvider = ColorSensor.getRGBMode();
		ColorSample = new float[sampleSize];
		
		//	int GetColorID = ColorSensor.getColorID();
		//	Delay.msDelay(250);
			
		}
    public float getcolormode() {
    	super.fetchSample(ColorSample, 0);
    	return ColorSample[0];
    }
	}

