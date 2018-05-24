package test;

import lejos.hardware.Button;
import lejos.robotics.navigation.DestinationUnreachableException;

public class TestThread {
   public static void main(String args[]) {

	   final ShareData task = new ShareData();

	   new Thread(new Runnable() {

           @Override
           public void run() {
               task.btserver();
           }
       }).start();	   
	   
	   new Thread(new Runnable() {

           @Override
           public void run() {
               task.motorrun();
           }
       }).start();
	   
	   new Thread(new Runnable() {

           @Override
           public void run() {
               task.ultrarun();
           }
       }).start();	 

	   new Thread(new Runnable() {

           @Override
           public void run() {
               try {
				task.carrun();
			} catch (DestinationUnreachableException e) {
				e.printStackTrace();
			}
           }
       }).start();
	   
	   while(true) {
	       if (Button.ESCAPE.isDown()) { 
		       System.exit(0);
	       }
	   }
   }   
}