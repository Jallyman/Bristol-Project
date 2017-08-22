// Imports for network connections
import java.net.InetAddress;
import java.net.Socket;
import java.net.NetworkInterface;
import java.util.Enumeration;

import java.util.Collections;

// Writing to .txt files
import java.io.BufferedWriter;
import java.io.FileWriter;


public class Connection {

    public int numConnections = 0;
    public int numRouters = 0;

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

            for (int i = 100; i < 115; i++) {

                String host = subnet + i;
                System.out.println("Checking :" + host);

                InetAddress address = InetAddress.getByName(host);
                if (address.isReachable(timeout)) {
                    System.out.println(host + " is reachable");

                    String hostName;

                    hostName = address.getHostName();

                    System.out.println(hostName);

                    numConnections++;
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
    public void storeData(String line) {
        try {
            BufferedWriter output = new BufferedWriter (new FileWriter ("connectedDevices.txt", true));
            output.write(line);
            output.newLine();
            output.close();
        } catch (Exception error){
            System.err.format("Problem writing");
            error.printStackTrace();
        }
    }
}