package test;

import lejos.robotics.SampleProvider;
import lejos.robotics.filter.AbstractFilter;

public class touchsensor extends AbstractFilter {
   float[] sample;
   
   public touchsensor(SampleProvider source) {
	   super(source);
	   sample = new float[sampleSize];
	   
   }
   
   public boolean pressed() {
	   super.fetchSample(sample, 0);
	   if (sample[0] == 0) {
		   return false;
	   } else {
		   return true;
	   }
   }
}
