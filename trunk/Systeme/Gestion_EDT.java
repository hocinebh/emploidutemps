package Systeme;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

import bdd.*;

public class Gestion_EDT extends Thread {

	static final int port = 8080;
	private Socket soc;
	private Thread t;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	private static final int PROMOTION =0;
	private static final int SALLE =1;
	private Personne utilisateur;
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
			this.typeEDT=Gestion_EDT.PROMOTION;
			
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
		/*
		 * signal Saisir_EDT()
signal afficher_liste_contacts()
signal reserver_salle(boolean)
signal afficher_creneaux_libres(Salle)
signal afficher_salles_libres(Creneau)
signal Choisir_EDT()
signal Selection_Personne(Personne)
signal Envoyer_message(Personne,String,String)
signal InitialiserTypeEDT(int)
signal AfficherPromo()
signal AfficherListeEnseignant()
signal SelectionSpecEns(Enseignant,Specialite)
signal SelectionCours(Creneau,Enseignant,boolean,int)
		 */
		if(methode.getNom().compareTo("Connexion")==0)
		{
			Connection((String)methode.getParametres().elementAt(0),(String)methode.getParametres().elementAt(1));
			
		}
		else if(methode.getNom().compareTo("Test")==0)
		{
			System.out.println("Bonjour la connexion a reussi");
		}
		else if(methode.getNom().compareTo("visualiser_EDT")==0)
		{
			Vector<Vector<Cours>> liste_cours = new Vector<Vector<Cours>>();
			if(utilisateur.getClass()==Responsable.class)
			{
				switch (typeEDT)
				{
					case Gestion_EDT.PROMOTION : Edt_Promotion(liste_cours,((Responsable)utilisateur).getPromo()); break;
					case Gestion_EDT.SALLE : Edt_Salle(liste_cours,(Salle)methode.getParametres().firstElement());break;
				}
			}
			else if(utilisateur.getClass()==Enseignant.class)
			{
				trie_par_jour(((Enseignant)utilisateur).getListe_cours(), liste_cours);
			}
			else if(utilisateur.getClass()==Etudiant.class)
			{
				EDT_etudiant(liste_cours);
			}
			
			out.writeObject(liste_cours);		
		}
		else if(methode.getNom().compareTo("EDT_Promotion")==0)
		{
			
		}
		else if(methode.getNom().compareTo("EDT_Promotion")==0)
		{
			
		}
	}
	
	private void EDT_etudiant(Vector<Vector<Cours>> liste_cours) {
		// TODO Auto-generated method stub
		
	}

	private void Edt_Salle(Vector<Vector<Cours>> liste_cours, Salle salle2) {
		// TODO Auto-generated method stub
		
	}

	private void Edt_Promotion(Vector<Vector<Cours>> liste_cours, Promotion promo) {
		// TODO Auto-generated method stub
		
	}
	private void trie_par_jour(Vector<Cours> cours, Vector<Vector<Cours>> liste_cours)
	{
		String date="";
		
		for(int i =0; i<cours.size(); i++)
		{
			//A chaque novelle date on ajoute un vecteur de cours
			if(cours.elementAt(i).getCreneau().getDate().compareTo(date)!=0)
			{
				liste_cours.add(new Vector<Cours>());
				date = cours.elementAt(i).getCreneau().getDate();
			}
			
			//On ajoute le cours au dernier vecteur de cours cree
			liste_cours.lastElement().add(cours.elementAt(i));
		}
	}
	
	/**
	 * Methodes a appeler lors de la 
	 * reception d'un signal
	 * @throws IOException 
	 */
	private void Connection(String nom, String mdp) throws IOException
	{
		boolean ok=false;
		
		for(int i=0; i<bd.getUtilisateurs().size() && !ok; i++)
		{
			utilisateur=(Personne)bd.getUtilisateurs().elementAt(i);
			
			if((utilisateur.getUsername().compareTo(nom)==0))
			{
				if(utilisateur.getPassword().compareTo(mdp)==0)
				{
					ok= true;
				}
			}
		}
		
		out.writeBoolean(ok);
		
		//Si on a pas trouv� l'utilisateur
		if (!ok)
		{
			System.exit(0);
		}
	}

	
	private void Erreursaisie(){
		
	}
	
	private void AfficherSpecEns(Enseignant ens, Groupe group){
		
	}
    
}
