package Systeme;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import Interfaces.Interface_Connexion;

public class Client {

	static final int port = 8080;
	private Socket soc;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public Client() throws UnknownHostException, IOException, ClassNotFoundException
	{
		//Reseau local
		String host = InetAddress.getLocalHost().getHostAddress();
		
		//Reseau internet
		//String host = "adresse du site"
		
		soc = new Socket(host,port);
		System.out.println("Connection etablie");
		//InputStream tmp = ;
		out = new ObjectOutputStream(soc.getOutputStream());
		in = new ObjectInputStream(soc.getInputStream());
		
		//Boolean ok = (Boolean)in.readObject();
		//System.out.println("ok : "+ ok);
		recept();
	    
	}
	
	public boolean Connexion(String login, String mdp) throws IOException, ClassNotFoundException
	{
		Signal s = new Signal("Connexion");
		s.addParametre(login);
		s.addParametre(mdp);
		Signaler(s);
		Boolean ok =(Boolean)in.readObject();
		System.out.println("ok : "+ ok);
		return (ok);
	}
	
	public void recept() throws IOException, ClassNotFoundException
	{
		Boolean ok =(Boolean)in.readObject();
		System.out.println("ok : "+ ok);
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
			System.out.println("Client cree");
			Signal s = new Signal("Test");
			c.Signaler(s);
			//Interface_Connexion Login = new Interface_Connexion();
			//Login.affiche_login_screen(c);
			boolean ok =c.Connexion("toto1","toto1");
			System.out.println("ok : "+ ok);
			while(true);
/*			c.FermerConnexion();
			System.out.println("Fermeture client");*/
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
