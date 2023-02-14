import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Table {

    Map<String, Information> hs;
    Map<Integer, String> idMap;

    Table()
    {
        hs = new HashMap<String, Information>();
        idMap = new HashMap<Integer, String>();
    }

    public void addKeyAndValue() {
        String container;
        String IPAddress1;
        String IPAddress2;
        ArrayList<String> IPArr;
        ArrayList<String> connectionArr;
        Information info;


        container = "MyLaptop";
        IPAddress1 = "192.168.17.2";
        IPArr = new ArrayList<>();
        IPArr.add(IPAddress1);
        int port = 50000;
        connectionArr = new ArrayList<>();
        connectionArr.add("FW1");
        int id = 0;
        info = new Information(id, IPArr, port, connectionArr);
        hs.put(container, info);
        idMap.put(id, container);

        container = "FW1";
        IPAddress1 = "192.168.17.3";
        IPAddress2 = "172.20.0.2";
        IPArr = new ArrayList<>();
        IPArr.add(IPAddress1);
        IPArr.add(IPAddress2);
        port = 54321;
        connectionArr = new ArrayList<>();
        connectionArr.add("MyLaptop");
        connectionArr.add("FW2");
        id = 1;
        info = new Information(id, IPArr, port, connectionArr);
        hs.put(container, info);
        idMap.put(id, container);

        container = "FW2";
        IPAddress1 = "172.20.0.3";
        IPAddress2 = "172.30.0.2";
        IPArr = new ArrayList<>();
        IPArr.add(IPAddress1);
        IPArr.add(IPAddress2);
        port = 54321;
        connectionArr = new ArrayList<>();
        connectionArr.add("FW1");
        connectionArr.add("FW3");
        id = 2;
        info = new Information(id, IPArr, port, connectionArr);
        hs.put(container, info);
        idMap.put(id, container);

        container = "FW3";
        IPAddress1 = "172.30.0.3";
        IPAddress2 = "172.40.0.2";
        IPArr = new ArrayList<>();
        IPArr.add(IPAddress1);
        IPArr.add(IPAddress2);
        port = 54321;
        connectionArr = new ArrayList<>();
        connectionArr.add("FW2");
        connectionArr.add("DServer");
        id = 3;
        info = new Information(id, IPArr, port, connectionArr);
        hs.put(container, info);
        idMap.put(id, container);
        
        container = "DServer";
        IPAddress1 = "172.40.0.3";
        IPArr = new ArrayList<>();
        IPArr.add(IPAddress1);
        port = 50001;
        connectionArr = new ArrayList<>();
        connectionArr.add("FW3");
        id = 4;
        info = new Information(id, IPArr, port, connectionArr);
        hs.put(container, info);
        idMap.put(id, container);


    }




}
