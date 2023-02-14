import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.DatagramPacket;

public class DServer extends Node {
    static final int DEFAULT_SRC_PORT = 50001;
	Table table;
	Information info;



    DServer(int port) {
		try {
			socket= new DatagramSocket(port);
			listener.go();
		}
		catch(java.lang.Exception e) {e.printStackTrace();}
	}


    public synchronized void onReceipt(DatagramPacket packet) {
        try {
			

			PacketContent content= PacketContent.fromDatagramPacket(packet);

			if (content.getType()==PacketContent.FILEINFO) 
            {
                System.out.println("Received a packet");

				DatagramPacket response= new AckPacketContent("OK - Received this", false).toDatagramPacket();
                InetSocketAddress iaddr = (InetSocketAddress) packet.getSocketAddress();
				response.setSocketAddress(iaddr);
				socket.send(response);
                
				String fileName = ((FileInfoContent)content).getFileName();
				System.out.println("File name: " + fileName);
				
				int size = ((FileInfoContent)content).getFileSize();
				System.out.println("File size: " + size);
				
				String text = ((FileInfoContent)content).getText();
				System.out.println("File content:\n" + text);				
                this.notify();
			}
			else
			{
				System.out.println("Error: content.getType() is not 100(FILEINFO)");
			}
		}
		catch(Exception e) {e.printStackTrace();}
    }


	public synchronized void start() throws Exception {
        System.out.println("Starting DServer...");
		System.out.println("Waiting for contact\n");
		this.wait();
	}

    public static void main(String[] args) {
		try {
			(new DServer(DEFAULT_SRC_PORT)).start();
			System.out.println("Program completed on DServer");
		} catch(java.lang.Exception e) {e.printStackTrace();}
	}
}