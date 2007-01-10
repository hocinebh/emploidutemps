package Systeme;

import java.net.ServerSocket;
import java.util.Vector;

/**
 * 
 * Serveur
 *
 */
public class Serveur extends Thread{

	static final int port = 8080;
	
	//Création d'un singleton
	private static Serveur instance = new Serveur();
	private Thread t;
	private Serveur()
	{
		t = new Thread(this);
		t.start();
	}
	public static void lanceServeur()
	{
		if(instance==null){instance=new Serveur();}
	}
	
	public void run()
	{
		try{
		    ServerSocket ses = new ServerSocket(port);
		    System.out.println("serveur socket creer");
		     while(true)
		     {     
		    	 new Gestion_EDT(ses.accept());
		     }
	     }catch(Exception e){
	        e.printStackTrace();
	     }
	}
	
	public static void main(String[] args) {
		Serveur.lanceServeur();
	}
	
}
