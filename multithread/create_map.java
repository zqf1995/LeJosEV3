package test;

import lejos.robotics.geometry.Line;
import lejos.robotics.mapping.LineMap;

public class create_map {

	private static int factor1 = 200;
	private static int factor2 = 167;
	public static Line[] lines = {
	new Line(-3*factor1, -3*factor2, 3*factor1, -3*factor2),
	new Line(-3*factor1, 3*factor2, 3*factor1, 3*factor2),
	new Line(-3*factor1, -3*factor2, -3*factor1, 3*factor2),
	new Line(3*factor1, -3*factor2, 3*factor1, 3*factor2),
	
	new Line(-3*factor1, 2*factor2, -2*factor1, 2*factor2),	
	new Line(-2*factor1, 2*factor2, -2*factor1, 3*factor2),
	
	new Line(-1*factor1, 2*factor2, 1*factor1, 2*factor2),
	new Line(-1*factor1, 2*factor2, -1*factor1, 3*factor2),
	new Line(1*factor1, 2*factor2, 1*factor1, 3*factor2),
	
	new Line(3*factor1, 2*factor2, 2*factor1, 2*factor2),
	new Line(2*factor1, 2*factor2, 2*factor1, 3*factor2),

	new Line(-3*factor1, -1*factor2, -2*factor1, -1*factor2),
	new Line(-2*factor1, -1*factor2, -2*factor1, 1*factor2),
	new Line(-3*factor1, 1*factor2, -2*factor1, 1*factor2),	
	
	new Line(3*factor1, -1*factor2, 2*factor1, -1*factor2),
	new Line(2*factor1, -1*factor2, 2*factor1, 1*factor2),
	new Line(3*factor1, 1*factor2, 2*factor1, 1*factor2),		
	
	new Line(-3*factor1, -2*factor2, -2*factor1, -2*factor2),	
	new Line(-2*factor1, -2*factor2, -2*factor1, -3*factor2),	

	new Line(-1*factor1, -2*factor2, 1*factor1, -2*factor2),
	new Line(-1*factor1, -2*factor2, -1*factor1, -3*factor2),
	new Line(1*factor1, -2*factor2, 1*factor1, -3*factor2),	

	new Line(3*factor1, -2*factor2, 2*factor1, -2*factor2),
	new Line(2*factor1, -2*factor2, 2*factor1, -3*factor2),	
	};
	public  LineMap createmap() {
	   LineMap map1 = new LineMap(lines,null); 
       return map1;
	}
}
