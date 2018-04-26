package test;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.AbstractFilter;

public class ultrasensor extends AbstractFilter{
	float[] sample;
	
	public ultrasensor(SampleProvider source) {
		super(source);
		sample = new float[sampleSize];
	}
    public float distance() {
    	super.fetchSample(sample, 0);
    	return sample[0];
    }
}
