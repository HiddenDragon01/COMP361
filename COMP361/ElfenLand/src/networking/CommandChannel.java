package networking;

import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.lang.Thread;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import commands.RemoteCommand;

import java.lang.Runnable;
import java.io.InputStream;
// Start of user code for imports
// End of user code

/**
 * CommandChannel class definition.
 * Generated by the TouchCORE code generator.
 */

public class CommandChannel implements Runnable {
    
    protected int myPort;
    protected HashMap<String, Socket> sockets;
    
    protected ObjectOutputStream ob;
    
    
    // This is used for the server
    public CommandChannel(int port) {
        this.myPort = port;
        this.sockets = new HashMap<String, Socket>();
        Thread thread = new Thread(this);
        thread.start();
    }

    
    // This is used for the client
    public CommandChannel(String host,  int port) {
        this.myPort = port;
        sockets = new HashMap<String, Socket>();
        try {
            InetAddress hostInetAddress = InetAddress.getByName(host);
            Socket socket = new Socket(hostInetAddress, this.myPort);
            boolean added = addSocket(host, socket);
        } catch (java.net.UnknownHostException e) {
            
        } catch (java.io.IOException e) {
            
        }
    }

    public void send(String host,  RemoteCommand cmd) {
        Socket socket = getSocket(host);
        try {
    		System.out.println("in send operation from command channel");

            if (socket == null) {
                socket = new Socket(host, this.myPort);
                boolean added = addSocket(host, socket);
            }
            OutputStream outputStream = socket.getOutputStream();
            
            if (ob == null){
        		System.out.println("in send (ob is null) operation from command channel");

                ObjectOutputStream objectoutputstream = new ObjectOutputStream(outputStream);
                ob = objectoutputstream;
                ob.writeObject(cmd);
                ob.reset();
                System.out.println("in send (after write obj) operation from command channel");
            } else {
            	
                ob.writeObject(cmd);
                
                ob.reset();
            }

            


        } catch (java.io.IOException e) {
            
        }
    }

    public void acceptCommandsFrom(String host) {
        Socket socket = getSocket(host);
        if (socket != null) {
            try {
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectinputstream = new ObjectInputStream(inputStream);
                CommandExecutor executor = new CommandExecutor(objectinputstream);
                Thread thread = new Thread(executor);
                thread.start();
            } catch (java.io.IOException e) {
                
            }
        }
    }

    public void run() {
    	    	
        try {
            ServerSocket serversocket = new ServerSocket(this.myPort);
            while (true) {
                Socket newConn = serversocket.accept();
                SocketAddress sa = newConn.getRemoteSocketAddress();
                String from = ((java.net.InetSocketAddress) sa).getHostName();
                                
                boolean added = addSocket(from, newConn);
                InputStream inputStream = newConn.getInputStream();
                ObjectInputStream objectinputstream = new ObjectInputStream(inputStream);
                CommandExecutor create = new CommandExecutor(objectinputstream);
                Thread thread = new Thread(create);
                thread.start();
            }
        } catch (java.io.IOException e) {
            
        }
    }

    public boolean addSocket(String k,  Socket a) {
        Socket oldValue = sockets.put(k, a);
        return true;
    }

    public boolean removeSocket(String k) {
        Socket removed = sockets.remove(k);
        return true;
    }

    public Socket getSocket(String k) {
        Socket value = sockets.get(k);
        return value;
    }

    public boolean containsKeySockets(String k) {
        boolean containsKey = sockets.containsKey(k);
        return containsKey;
    }

    public boolean containsValueSockets(Socket a) {
        boolean containsValue = sockets.containsValue(a);
        return containsValue;
    }

    public String getKeyForSocket(Socket v) {
        /* TODO: No message view defined */
        return "";
    }

    public int sizeOfSockets() {
        int size = sockets.size();
        return size;
    }

    public HashMap<String, Socket> getSockets() {
        return this.sockets;
    }

    public CommandChannel() {
        this.sockets = new HashMap<String, Socket>();
    }
}