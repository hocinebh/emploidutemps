package Systeme;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	static final int port = 8080;
	private Socket soc;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public Client() throws UnknownHostException, IOException
	{
		//Réseau local
		String host = InetAddress.getLocalHost().getHostAddress();
		
		//Réseau internet
		//String host = "adresse du site"
		
		soc = new Socket(host,port);
		System.out.println("Connection établie");
		//InputStream tmp = ;
		out = new ObjectOutputStream(soc.getOutputStream());
		in = new ObjectInputStream(soc.getInputStream());
	    
	}
	
	public boolean Connexion(String login, String mdp) throws IOException
	{
		Signal s = new Signal("Connexion");
		s.addParametre(login);
		s.addParametre(mdp);
		Signaler(s);
				
		return (in.readBoolean());
	}
	
	public void Signaler(Signal s) throws IOException
	{
		out.writeObject(s);
		System.out.println("envoi d'un signal "+s.getNom());
	}
	
	public void FermerConnexion()
	{
		try {
			out.close();
			in.close();
			soc.close();
		} catch (IOException e) {
			System.out.println("Erreur fermeture socket");
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Client c = new Client();
			System.out.println("Client créé");
			Signal s = new Signal("Test");
			c.Signaler(s);
			
			c.FermerConnexion();
			System.out.println("Fermeture client");
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
