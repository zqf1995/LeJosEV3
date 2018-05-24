package test;

import lejos.hardware.Button;
import lejos.remote.nxt.NXTConnection;

public class Blueserver {
    public static byte[] intToByteArray(int a) {  
        return new byte[] {  
            (byte) ((a >> 24) & 0xFF),  
            (byte) ((a >> 16) & 0xFF),     
            (byte) ((a >> 8) & 0xFF),     
            (byte) (a & 0xFF)  
        };  
    }  	    
    
    public void send(NXTConnection connection, int messageNo) {
        byte[] sendBuff = new byte[4];
        sendBuff = intToByteArray(messageNo);
        connection.write(sendBuff, sendBuff.length);
    }	
    public static char byteAsciiToChar(int ascii){  
        char ch = (char)ascii;  
        return ch;  
    } 	    
    
    public boolean isReceived(NXTConnection connection, boolean pick_flag, int choosepath) {
        byte[] recvBuff = new byte[1];// the server send one byte message to the robot 
        while(recvBuff[0] == 0 && Button.ESCAPE.isUp()){
            connection.read(recvBuff, recvBuff.length);// just read one byte
        }
        //System.out.println(recvBuff[0]);
        if(recvBuff[0] == 49) { //if it is 0x01 then start the robot 
           pick_flag = true; // flag to run the following function
           choosepath = 1;
        }
        else if (recvBuff[0] == 50) {
           pick_flag = true; // flag to run the following function
           choosepath = 2;	            	
        }
        else if(recvBuff[0] == 51) { //if it is 0x01 then start the robot 
            pick_flag = true; // flag to run the following function
            choosepath = 3;
        }
        else if (recvBuff[0] == 52) {
            pick_flag = true; // flag to run the following function
            choosepath = 4;	            	
        }        
        return true;
    }	    
}
