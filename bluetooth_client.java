package bluetooth;

import lejos.hardware.Bluetooth;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.remote.nxt.NXTCommConnector;
import lejos.remote.nxt.NXTConnection;

public class bluetooth_client {
    public static void main(String[] args) {
        int sendMessageNo = 2; // ���Ť��뷬��
        int recvMessageNo = 3; // ���Ť��뷬��

        String server = "98:D3:32:31:0D:9F"; // ���`�ЂȥǥХ�����MAC���ɥ쥹

        System.out.println("Connecting...");

        // ���`�Щ`�ȤΥǥХ������饳�ͥ�������ȡ�ä���
        NXTCommConnector connector = Bluetooth.getNXTCommConnector();
        NXTConnection connection = connector.connect(server, NXTConnection.RAW);
        if (connection == null) {
            System.out.println("Connect fail");
            Button.waitForAnyPress();
            System.exit(1);
        }
        System.out.println("Connected");

        // ��å��`�������Ť���
        System.out.println("Sending...");
        send(connection, sendMessageNo);
        Sound.systemSound(false, 3);
      while(true) {
        // ��å��`�������Ť���
        System.out.println("Receivng...");
        isReceived(connection, recvMessageNo);
        
		if (Button.ESCAPE.isDown()) { 
		       // ͨ�Ť��жϤ��Ф�
	        System.out.println("Closing...");
	        try {
	            if (null != connection) {
	                connection.close();
	            }
	        } catch (Exception ioe) {
	        }

	        System.out.println("Finished");
	        Button.waitForAnyPress();
			   System.exit(0);
		   }
      }
 
    }

    static boolean isReceived(NXTConnection connection, int messageNo) {
        byte[] recvBuff = new byte[20];

        //��å��`�������Ť��뤫��ESCAPE�ܥ���Ѻ�����ޤǴ���
        while(recvBuff[0] == 0 && Button.ESCAPE.isUp()){
            connection.read(recvBuff, recvBuff.length);
        }

        //��å��`�����������������å�����
        for (int i = 0; i < recvBuff.length; i++) {
            System.out.print(byteAsciiToChar(recvBuff[i]));
//            if (recvBuff[i] != messageNo) {
//                System.out.println();
//                return false;
//            }
        }
        System.out.println();
        return true;
    }

    static void send(NXTConnection connection, int messageNo) {
        // ��å��`����Хåե����O������
        byte[] sendBuff = new byte[16];
        for (int i = 0; i < sendBuff.length; i++) {
            sendBuff[i] = (byte) messageNo;
        }

        // ��å��`�������Ť���
        connection.write(sendBuff, sendBuff.length);
    }
    /** 
     * ͬ��asciiת��Ϊchar ֱ��intǿ��ת��Ϊchar 
     * @param ascii 
     * @return 
     */  
    public static char byteAsciiToChar(int ascii){  
        char ch = (char)ascii;  
        return ch;  
    } 
}