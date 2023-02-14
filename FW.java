import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.DatagramPacket;

public class FW extends Node {
    static final int DEFAULT_SRC_PORT = 54321;
	Table table;
	Information info;



    FW(int port) {
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
				System.out.println("ACK Address: " + iaddr);
				response.setSocketAddress(iaddr);
				socket.send(response);
                
				String fileName = ((FileInfoContent)content).getFileName();
				System.out.println("File name: " + fileName);
				
				int size = ((FileInfoContent)content).getFileSize();
				System.out.println("File size: " + size);
				
				String text = ((FileInfoContent)content).getText();
				System.out.println("File content:\n" + text);

				String pathToDst = ((FileInfoContent)content).getPathToDst();
				String[] pathArr = pathToDst.split(", ");

				String strAddr = packet.getAddress().toString();
				String preIPAddr = strAddr.substring(1);

				String nextDst = findNexDst(pathArr, preIPAddr);
				System.out.println("Next destination: " + nextDst);

				info = table.hs.get(nextDst);
				int dstPort = info.port;
            
				InetSocketAddress dstAddress = new InetSocketAddress(nextDst, dstPort);
            
				System.out.println("Next destination address: " + dstAddress);

		    	System.out.println("Sending packet w/ file name & path...");
	
		    	packet.setSocketAddress(dstAddress);
		    	socket.send(packet);
			}
            else if(content.getType()==PacketContent.ACKPACKET)
			{
				System.out.println(content.toString() + "\n");
                this.notify();
			}
			else
			{
				System.out.println("Error: content.getType() is neither 100(FILEINFO) nor ACKPACKET(10)");
			}
		}
		catch(Exception e) {e.printStackTrace();}
    }

    private String findNexDst(String[] pathArr, String preIPAddr) {
		table = new Table();
		table.addKeyAndValue();
		for(int i = 0; i < pathArr.length; i++)
		{
			int curID = Integer.valueOf(pathArr[i]);
			String curNodeName = table.idMap.get(curID);
			Information info = table.hs.get(curNodeName);
			for(int k = 0; k < info.IPArr.size(); k++)
			{
				String IPAddress = info.IPArr.get(k);

				if(IPAddress.equals(preIPAddr))
				{
					int nextID = Integer.valueOf(pathArr[i+2]);
					String nextNodeName = table.idMap.get(nextID);
					return nextNodeName;
				}
			}
			
		}
		return null;
	}


	public synchronized void start() throws Exception {
        System.out.println("Starting Forwarder...");
		System.out.println("Waiting for contact\n");
		this.wait();
	}

    public static void main(String[] args) {
		try {
			(new FW(DEFAULT_SRC_PORT)).start();
			System.out.println("Program completed on Forwarder");
		} catch(java.lang.Exception e) {e.printStackTrace();}
	}
}