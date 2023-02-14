import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class for packet content that represents file information
 *
 */
public class FileInfoContent extends PacketContent {

	String filename;
	int size;
	String text;
	String pathToDst;

	/**
	 * Constructor that takes in information about a file.
	 * @param filename Initial filename.
	 * @param size Size of filename.
	 * @param worker Worker number specified by client.
	 * @param text The text content of the file from workers
	 */
	FileInfoContent(String filename, int size, String text, String pathToDst) {
		type= FILEINFO;
		this.filename = filename;
		this.size= size;
		this.text = text;
		this.pathToDst = pathToDst;
	}

	/**
	 * Constructs an object out of a datagram packet.
	 * @param packet Packet that contains information about a file.
	 */
	protected FileInfoContent(ObjectInputStream oin) {
		try {
			type= FILEINFO;
			filename= oin.readUTF();
			size= oin.readInt();
			text = oin.readUTF();
			pathToDst = oin.readUTF();
		}
		catch(Exception e) {e.printStackTrace();
		}
	}

	/**
	 * Writes the content into an ObjectOutputStream
	 *
	 */
	protected void toObjectOutputStream(ObjectOutputStream oout) {
		try {
			oout.writeUTF(filename);
			oout.writeInt(size);
			oout.writeUTF(text);
			oout.writeUTF(pathToDst);
		}
		catch(Exception e) {e.printStackTrace();
		}
	}


	/**
	 * Returns the content of the packet as String.
	 *
	 * @return Returns the content of the packet as String.
	 */
	public String toString() {
		return "Filename: " + filename;
	}

	/**
	 * Returns the file name contained in the packet.
	 *
	 * @return Returns the file name contained in the packet.
	 */
	public String getFileName() {
		return filename;
	}

	/**
	 * Returns the file size contained in the packet.
	 *
	 * @return Returns the file size contained in the packet.
	 */
	public int getFileSize() {
		return size;
	}

	
	public boolean getCompleted() {
		return true;
	}

	public String getText() {
		return text;
	}
	
	public String getPathToDst() {
		return pathToDst;
	}

}
