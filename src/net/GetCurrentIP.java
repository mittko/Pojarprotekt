package net;

import Log.OtherErr;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class GetCurrentIP {
	// 78.142.42.215 Public IP

	public static String EMBEDDED_DB_PATH="jdbc:derby:D:\\RealDB;";//78.142.42.215

	public static String DB_PATH= "jdbc:derby://127.0.0.1:1527/D:/RealDB";//78.142.42.215

	public static String LPS_DB_PATH="jdbc:derby://127.0.0.1:1527/D:/RealDBLPS";

	// = "jdbc:derby://127.0.0.1:1527/C:/Local/RealDB";
    //"jdbc:derby://78.142.42.215:1527/C:/RealDB";
	public class Address {
		public String IP;
		public int PORT;

		public Address(String ip, int port) {
			this.IP = ip;
			this.PORT = port;
		}
	}

	public static ArrayList<Address> address = new ArrayList<Address>();

	public static void addAddress(String ip, int port) {
		address.add(new GetCurrentIP().new Address(ip, port));
	}

	private GetCurrentIP() {

	}

	public static String getNetworkMachineName() {
		InetAddress net = null;
		try {
			net = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			OtherErr.otherErros(e.toString());
			// e.printStackTrace();
		}

		return net.getHostName();// net.getHostAddress();192.168.1.101
	}

	public static String getIP() {
		/*
		 * InetAddress net = null; try { net = InetAddress
		 * .getByName("PPROTECT-PC"); } catch (UnknownHostException e) { // TODO
		 * Auto-generated catch block OtherErr.otherErros(e.toString()); //
		 * e.printStackTrace(); } System.out.println(net.getHostAddress()); //
		 * get ip address of host
		 */
		return "PPROTECT-PC";// net.getHostAddress();192.168.1.101
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(GetCurrentIP.getNetworkMachineName());
	}

}
