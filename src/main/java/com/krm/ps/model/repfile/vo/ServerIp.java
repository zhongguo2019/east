package com.krm.ps.model.repfile.vo;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class ServerIp {

	@SuppressWarnings("rawtypes")
	public String  getServerIp(){  
		    String serverip=null;
	    try {  
	        Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();  
	        InetAddress ip = null;  
	     
	        while (netInterfaces.hasMoreElements()) {  
	            NetworkInterface ni = (NetworkInterface) netInterfaces  
	                    .nextElement();  
	            ip = (InetAddress) ni.getInetAddresses().nextElement();  
	                serverip = ip.getHostAddress();  
	            if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()  
	                    && ip.getHostAddress().indexOf(":") == -1) {  
	            	serverip = ip.getHostAddress();  
	                break;  
	            } else {  
	                ip = null;  
	            }  
	        }  
	    } catch (SocketException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    }  
	     
	     return serverip;  
	   }  
	 
	public static String getLocalIP(){     
        InetAddress addr = null;     
                    try {  
                        addr = InetAddress.getLocalHost();  
                    } catch (UnknownHostException e) {  
                        // TODO Auto-generated catch block  
                        e.printStackTrace();  
                    }     
                  
                byte[] ipAddr = addr.getAddress();     
                String ipAddrStr = "";     
                for (int i = 0; i < ipAddr.length; i++) {     
                    if (i > 0) {     
                        ipAddrStr += ".";     
                    }     
                    ipAddrStr += ipAddr[i] & 0xFF;     
                }     
                //System.out.println(ipAddrStr);     
                        return ipAddrStr;     
        }  
	
	
	
	    public static void main(String[] args) throws Exception {
	        Enumeration<NetworkInterface> en = NetworkInterface
	                .getNetworkInterfaces();
	        while (en.hasMoreElements()) {
	            NetworkInterface ni = en.nextElement();
	            printParameter(ni);
	 
	        }
	        
	        Collection<InetAddress> colInetAddress =getAllHostAddress();
	        
	        for (InetAddress address : colInetAddress) {   
	          if (!address.isLoopbackAddress()) 
	           System.out.println("IP:"+address.getHostAddress());
	                     
	              }   

	    }
	 
	    public static void printParameter(NetworkInterface ni)
	            throws SocketException {
	        if (null != ni.getDisplayName()
	                && ni.getDisplayName().contains("Wireless") && ni.isUp()) {
	            System.out.println(" Name = " + ni.getName());
	            System.out.println(" Display Name = " + ni.getDisplayName());
	            System.out.println(" Is up = " + ni.isUp());
	            System.out
	                    .println(" Support multicast = " + ni.supportsMulticast());
	            System.out.println(" Is loopback = " + ni.isLoopback());
	            System.out.println(" Is virtual = " + ni.isVirtual());
	            System.out.println(" Is point to point = " + ni.isPointToPoint());
	            System.out
	                    .println(" Hardware address = " + ni.getHardwareAddress());
	            System.out.println(" MTU = " + ni.getMTU());
	 
	            System.out.println("\nList of Interface Addresses:");
	            List<InterfaceAddress> list = ni.getInterfaceAddresses();
	            Iterator<InterfaceAddress> it = list.iterator();
	 
	            while (it.hasNext()) {
	                InterfaceAddress ia = it.next();
	                if(null!=ia.getBroadcast()){
	                    System.out.println(" 本机实际联网网卡IPv4地址： " + ia.getAddress().getHostAddress());
	                }
	            }
	 
	            System.out
	                    .println("**************************************************");
	        }
	    }
	    

	    public static Collection<InetAddress> getAllHostAddress() {   
	           try {   
	               Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();   
	               Collection<InetAddress> addresses = new ArrayList<InetAddress>();   
	                  
	               while (networkInterfaces.hasMoreElements()) {   
	                   NetworkInterface networkInterface = networkInterfaces.nextElement();   
	                   Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();   
	                   while (inetAddresses.hasMoreElements()) {   
	                       InetAddress inetAddress = inetAddresses.nextElement();   
	                       addresses.add(inetAddress);   
	                   }   
	               }   
	                  
	               return addresses;   
	           } catch (SocketException e) {   
	               throw new RuntimeException(e.getMessage(), e);   
	           }   
	       }   

	
}
