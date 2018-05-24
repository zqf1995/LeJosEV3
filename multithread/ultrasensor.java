package test;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.AbstractFilter;

public class ultrasensor extends AbstractFilter{
	public static float[] sample;
	
	public  ultrasensor(SampleProvider source) {
		super(source);
		sample = new float[sampleSize];
	}
    public float distance() {
    	super.fetchSample(sample, 0);
    	return sample[0];
    }
}
