package Systeme;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import bdd.*;

public class Gestion_EDT extends Thread {

	static final int port = 8080;
	private Socket soc;
	private Thread t;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	private Personne Utilisateur;
	private int typeEDT;
	private Gestion_BDD bd;
	
    /**
	 * @param soc
	 */
	public Gestion_EDT(Socket soc) {
		try
		{
			System.out.println("Nouveau client : "+soc);
			this.bd = Gestion_BDD.getInstance();
			this.soc = soc;
			
			in = new ObjectInputStream(this.soc.getInputStream());
		    out = new ObjectOutputStream(this.soc.getOutputStream());
		    
		    t = new Thread(this);
		    t.start();
		}catch(Exception e){
		   e.printStackTrace();
		}
	}
	
	/**
	 * Methode lancer lors de la reception de signaux
	 */
	public void run()
	{
		try {
			while (true)
			{
				Signal methode = (Signal)in.readObject();
				System.out.println("Reception du signal : "+methode.getNom());
				execute(methode);			
			}
			
		} catch (IOException e1) {} 
		  catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally // finally se produira le plus souvent lors de la deconnexion du client
	    {
	      FermerConnexion();
	    }

	}
	
	private void FermerConnexion()
	{
		try {
			out.close();
			in.close();
			soc.close();
			System.out.println("Depart client : "+soc);
		} catch (IOException e) {
			System.out.println("Erreur fermeture socket");
		}
	}
	
	private void execute(Signal methode) throws IOException
	{
		if(methode.getNom().compareTo("Connexion")==0)
		{
			Connection((String)methode.getParametres().elementAt(0),(String)methode.getParametres().elementAt(1));
			
		}
		else if(methode.getNom().compareTo("Test")==0)
		{
			System.out.println("Bonjour la connexion a réussi");
		}
		else if(methode.getNom().compareTo("EDT_Promotion")==0)
		{
			
		}
		else if(methode.getNom().compareTo("EDT_Promotion")==0)
		{
			
		}
		else if(methode.getNom().compareTo("EDT_Promotion")==0)
		{
			
		}
	}
	
	/**
	 * Méthodes a appeler lors de la 
	 * reception d'un signal
	 * @throws IOException 
	 */
	private void Connection(String nom, String mdp) throws IOException
	{
		boolean ok=false;
		
		for(int i=0; i<bd.getUtilisateurs().size() && !ok; i++)
		{
			Utilisateur=(Personne)bd.getUtilisateurs().elementAt(i);
			
			if((Utilisateur.getUsername().compareTo(nom)==0))
			{
				if(Utilisateur.getPassword().compareTo(mdp)==0)
				{
					ok= true;
				}
			}
		}
		
		out.writeBoolean(ok);
		
		//Si on a pas trouvé l'utilisateur
		if (!ok)
		{
			System.exit(0);
		}
	}
	
	private void Son_emploi_du_temps(){
		
	}

	private void EDT_Salle(){
		
	}
	
	private void EDT_Promotion(){
		
	}
	
	private void Erreursaisie(){
		
	}
	
	private void AfficherSpecEns(Enseignant ens, Groupe group){
		
	}
    
}
