import java.util.ArrayList;

public class Information {
    ArrayList<String> IPArr;
    int port;
    ArrayList<String> connectionArr;
    int id;

    public Information(int id, ArrayList<String> IPArr, int port, ArrayList<String> connectionArr) {
    	this.id = id;
        this.IPArr = IPArr;
        this.port = port;
        this.connectionArr = connectionArr;
    }

}
