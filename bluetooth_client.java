package bluetooth;

import lejos.hardware.Bluetooth;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.remote.nxt.NXTCommConnector;
import lejos.remote.nxt.NXTConnection;

public class bluetooth_client {
    public static void main(String[] args) {
        int sendMessageNo = 2; // 送信する番号
        int recvMessageNo = 3; // 受信する番号

        String server = "98:D3:32:31:0D:9F"; // サーバ側デバイスのMACアドレス

        System.out.println("Connecting...");

        // サーバー側のデバイスからコネクションを取得する
        NXTCommConnector connector = Bluetooth.getNXTCommConnector();
        NXTConnection connection = connector.connect(server, NXTConnection.RAW);
        if (connection == null) {
            System.out.println("Connect fail");
            Button.waitForAnyPress();
            System.exit(1);
        }
        System.out.println("Connected");

        // メッセージを送信する
        System.out.println("Sending...");
        send(connection, sendMessageNo);
        Sound.systemSound(false, 3);
      while(true) {
        // メッセージを受信する
        System.out.println("Receivng...");
        isReceived(connection, recvMessageNo);
        
		if (Button.ESCAPE.isDown()) { 
		       // 通信の切断を行う
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

        //メッセージを受信するか、ESCAPEボタンが押されるまで待つ
        while(recvBuff[0] == 0 && Button.ESCAPE.isUp()){
            connection.read(recvBuff, recvBuff.length);
        }

        //メッセージが正しいかチェックする
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
        // メッセージをバッファに設定する
        byte[] sendBuff = new byte[16];
        for (int i = 0; i < sendBuff.length; i++) {
            sendBuff[i] = (byte) messageNo;
        }

        // メッセージを送信する
        connection.write(sendBuff, sendBuff.length);
    }
    /** 
     * 同理，ascii转换为char 直接int强制转换为char 
     * @param ascii 
     * @return 
     */  
    public static char byteAsciiToChar(int ascii){  
        char ch = (char)ascii;  
        return ch;  
    } 
}