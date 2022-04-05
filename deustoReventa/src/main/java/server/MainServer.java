package server;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class MainServer implements Runnable{
	
	private Client client;
	private WebTarget webTarget;
	
	private Thread thread;
	private final AtomicBoolean running=new AtomicBoolean(false);
	
	public MainServer(String hostname, String port) {
			client=ClientBuilder.newClient();
			webTarget = client.target(String.format("http://%s:%s/rests", hostname, port));
			
			thread = new Thread(this);
			thread.start();
	}
	
	public void run() {
		running.set(true);
		while(running.get()) {
			try { 
				Thread.sleep(2000);
				System.out.println("Obtaining data from server...");
            } catch (InterruptedException e){ 
                Thread.currentThread().interrupt();
                System.out.println("Thread was interrupted, Failed to complete operation");
            }
		}
	}
	
	public void stop() {
		this.running.set(false);
	}
	
	public static void main(String []args) {
		String hostname = args[0];
		String port = args[1];
		
		MainServer ms=new MainServer(hostname, port);
	}
}
