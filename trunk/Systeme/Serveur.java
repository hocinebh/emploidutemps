package Systeme;

import java.net.ServerSocket;
import java.util.Vector;

/**
 * 
 * Serveur
 *
 */
public class Serveur {

	static final int port = 8080;
	
	//Création d'un singleton
	private static Serveur instance = new Serveur();
	private Serveur(){}
	public static Serveur getInstance(){return instance;}
	
	public static void main(String[] args) {
		try{
		    ServerSocket ses = new ServerSocket(port);
		    System.out.println("serveur socket cr�er");
		     while(true)
		     {     
		    	 new Gestion_EDT(ses.accept());
		     }
	     }catch(Exception e){
	        e.printStackTrace();
	     }
	}
	
}
