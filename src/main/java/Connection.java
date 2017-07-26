import java.net.InetAddress;
import java.net.Socket;
public class Connection {

    public Connection() {
        int timeout=500;
        int port = 1234;

        try {
            String currentIP = InetAddress.getLocalHost().toString();
            String subnet = getSubnet(currentIP);
            System.out.println("subnet: " + subnet);

            for (int i=1;i<254;i++){

                String host = subnet + i;
                System.out.println("Checking :" + host);

                if (InetAddress.getByName(host).isReachable(timeout)){
                    System.out.println(host + " is reachable");
                    try {
                        Socket connected = new Socket(subnet, port);
                    }
                    catch (Exception s) {
                        System.out.println(s);
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static String getSubnet(String currentIP) {
        int firstSeparator = currentIP.lastIndexOf("/");
        int lastSeparator = currentIP.lastIndexOf(".");
        return currentIP.substring(firstSeparator+1, lastSeparator+1);
    }
}