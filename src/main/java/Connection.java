// Imports for network connections
import java.net.InetAddress;
import java.net.Socket;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Date;
import java.text.*;
import java.util.Collections;
import java.util.ArrayList;

// Writing to .txt files
import java.io.BufferedWriter;
import java.io.FileWriter;
import javafx.concurrent.*;



public class Connection {

    public int numConnections = 0;
    public int numRouters = 0;
    public ArrayList<String> deviceNames;

    public int connection(int routers) {
        int timeout = 1000;
        int port = 1234;

        numRouters = routers;
        numConnections = 0;

        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();

            // MAC address needs to change depending on device I am on
            String macAddress = "F8-2F-A8-F5-B8-5F";

            NetworkInterface adapter = null;

            for (NetworkInterface iface : Collections.list(nets)) {
                byte[] mac = iface.getHardwareAddress();

                if (mac == null || !iface.isUp()) {
                    continue;
                }

                StringBuilder sb = new StringBuilder();
                for (byte b : mac) {
                    sb.append(String.format("%02X%s", b, "-"));
                }

                sb.deleteCharAt(sb.length() - 1);
                if (sb.toString().equals(macAddress)) {
                    adapter = iface;
                    break;
                }
            }

            if (adapter == null) {
                System.out.println("No network adapter found");
                System.exit(1);
            } else {
                System.out.println("Using: " + adapter.getName());
            }

            Enumeration<InetAddress> myAddress = adapter.getInetAddresses();

            String currentIP = myAddress.nextElement().getHostAddress();
            System.out.println(currentIP);

            String subnet = getSubnet(currentIP);
            System.out.println("Subnet: " + subnet);

            deviceNames = new ArrayList<String>();

            for (int i = 0; i < 254; i++) {

                String host = subnet + i;
                System.out.println("Checking :" + host);

                InetAddress address = InetAddress.getByName(host);
                if (address.isReachable(timeout)) {
                    System.out.println(host + " is reachable");

                    String hostName;

                    hostName = address.getHostName();
                    deviceNames.add(hostName);
                    System.out.println(hostName);

                    numConnections++;
                }

                if(Thread.currentThread().interrupted()) {
                    return 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        routerCorrect();
        storeData(Integer.toString(numConnections));
        return numConnections;
    }

    // Retrieving the subnet - finds lastIndexOf "."
    public static String getSubnet(String currentIP) {
        int firstSeparator = currentIP.lastIndexOf("/");
        int lastSeparator = currentIP.lastIndexOf(".");
        return currentIP.substring(firstSeparator + 1, lastSeparator + 1);
    }

    // Number of routers taken away from number of found devices
    public void routerCorrect() {
        numConnections = numConnections - numRouters;
    }

    // Write to .txt file - Only no. of devices
    public void storeData(String deviceNo) {
        try {
            Date dNow = new Date();
            SimpleDateFormat format = new SimpleDateFormat ("E dd.MM.yyyy 'at' hh:mm:ss");
            BufferedWriter output = new BufferedWriter (new FileWriter ("connectedDevices.txt", true));
            output.write("No. devices: " + deviceNo + " - " + format.format(dNow));


            // Print device names found
            if(deviceNames.size() > 0){
                output.write(" - Devices: ");
                for (int i = 0; i < deviceNames.size(); i++){
                    if(i == 0) {
                        output.write(deviceNames.get(i));
                    } else {
                        output.write(", " + deviceNames.get(i));
                    }
                }
            }
            output.newLine();
            output.close();
        } catch (Exception error){
            System.err.format("Problem writing");
            error.printStackTrace();
        }
    }
}