import java.net.DatagramSocket;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * Client class
 *
 * An instance accepts user input
 *
 */
public class MyLaptop extends Node {
    static final int DEFAULT_SRC_PORT = 50000;
	static final int DEFAULT_FINAL_DST_PORT = 50001;
    static final String DEFAULT_FINAL_DST_NODE = "DServer";
    static final String DEFAULT_SRC_NODE = "MyLaptop";

    String fileName;
    InetSocketAddress dstAddress;
    static Table table;
    static Forwarder fwd;
    Graph graph;
    ArrayList<Integer> path;
    String pathToDst;
    
    

    /**
     * Constructor
     *
     * Attempts to create socket at given port and create an InetSocketAddress for
     * the destinations
     */
    MyLaptop(String finalDstNode, String srcNode, int srcPort, String fileName) {
        try {
            this.fileName = fileName;
            
            graph = new Graph(table, fwd);
            
            Information info = table.hs.get(srcNode);
            int srcId = info.id;
            
            info = table.hs.get(finalDstNode);
            int finalDstId = info.id;

            path = findPath(srcId, finalDstId, graph);
            
            String nextDst = graph.idMap.get(path.get(1));
            System.out.println("Next destination: " + nextDst);
            info = table.hs.get(nextDst);
            int dstPort = info.port;
            
            dstAddress = new InetSocketAddress(nextDst, dstPort);
            
            System.out.println("Next destination address: " + dstAddress);
            
            socket = new DatagramSocket(srcPort);
            listener.go();
        } 
        catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Integer> findPath(int srcId, int dstId, Graph graph) {
    	Dijkstra algo = new Dijkstra(srcId, dstId, graph);
    	if(algo.dijkstraAlgo())
    	{
    		path = algo.path;
    		System.out.println("Path to the final destination(ID): " + path.toString());
            return path;
    	}
    	else
    	{
    		System.out.println("No path");
            return null;
    	}
    }

    /**
     * Assume that incoming packets contain a String and print the string.
     */
    public synchronized void onReceipt(DatagramPacket packet) {
        try {
            PacketContent content = PacketContent.fromDatagramPacket(packet);
            if (content.getType() == PacketContent.ACKPACKET) {
                System.out.println(content.toString() + "\n");
                this.notify();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
	 * Sender Method
	 *
	 */
	public synchronized void start() throws Exception {
		String fname = fileName;

        File file = new File(fileName); // Reserve buffer for length of file and read file
		byte[] buffer = new byte[(int) file.length()];
	    FileInputStream fin = new FileInputStream(file);
		int size = fin.read(buffer);
		if (size == -1) 
        {
            fin.close();
			throw new Exception("Problem with File Access:" + fileName);
		}
        else
        {
            System.out.println("File was found");
            System.out.println("File size: " + buffer.length);
		    System.out.println("The contents are here:");

		    FileReader fr = new FileReader(file);
		    BufferedReader br = new BufferedReader(fr);
		    String text;
		    StringBuilder sb = new StringBuilder();

		    while ((text = br.readLine()) != null) 
		    {
		    	System.out.println(text);
		    	sb.append(text + "\n");
		    }
		    br.close();

		    String builder = sb.toString();
		    
		    String pathToDst = path.toString().replace("[", "").replace("]", "");

		    FileInfoContent fcontent = new FileInfoContent(fname, size, builder, pathToDst);;
		    DatagramPacket packet = fcontent.toDatagramPacket();;

		    System.out.println("Sending packet w/ file name & path...");
	
		    packet.setSocketAddress(dstAddress);
		    socket.send(packet);
		    fin.close();
		    this.wait();
        }
	}

    /**
     * Test method
     *
     * Sends a packet to a given address
     */
    public static void main(String[] args) {
        try {
            makeTable();
            makeForwarderList();
            System.out.println("Starting MyLaptop...\n");
            System.out.println("Send a file(Type in yes or no)");
            Scanner sc = new Scanner(System.in);
            String input = sc.next();
            boolean exit = false;
            if (input.equalsIgnoreCase("yes")) {
                while (!exit) {
                    System.out.println("Which file would you like to send?");
                    String fileName = sc.next();
                
                    (new MyLaptop(DEFAULT_FINAL_DST_NODE, DEFAULT_SRC_NODE, DEFAULT_SRC_PORT, fileName)).start();
                    System.out.println("Program completed");
                    exit = true;
                }
            } else {
                System.out.println("Bye");
            }
            sc.close();

        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    private static void makeForwarderList() {
        fwd = new Forwarder();
        fwd.addForwarder();
    }

    private static void makeTable() {
        table = new Table();
        table.addKeyAndValue();
    }
}
