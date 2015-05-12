package edu.upenn.cis.cis455;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


/**
 * Based on  http://stackoverflow.com/questions/3732109/simple-http-server-in-java-using-only-java-se-api
 * 
 *
 */
public class TestSimpleServer {
	
	public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/test", new MyHandler(args));
        server.setExecutor(null); // creates a default executor
        server.start();
    }
	
	static String convolute(String str) {
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < str.length(); i += 2)
			ret.append(str.charAt(i));
		
		for (int j = 1; j < str.length(); j += 2)
			ret.append(str.charAt(j));
		
		return ret.toString();
	}

    static class MyHandler implements HttpHandler {
    	String[] args = null;
    	MyHandler(String[] args) {
    		if(args == null || args.length == 0) {
    			this.args = null;
    		}
    		else {
	    		this.args = new String[args.length];
	    		for(int i = 0; i < args.length; i++) {
	    			this.args[i] = new String(args[i]);
	    		}	
    		}
    	}
    	
    	MyHandler() {
    		this(null);
    	}
    	
    	
        public void handle(HttpExchange t) throws IOException {
        	String response;
        	if(args != null) {
        		response = args[0];
        	} else {
        		response = "dichenli";
        	}
            t.sendResponseHeaders(200, response.length());
            System.out.println(response);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
