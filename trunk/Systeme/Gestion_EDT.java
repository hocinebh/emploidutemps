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
	private int typeUtilisateur;
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
			System.out.println("Bonjour la connexion a reussi");
		}
		else if(methode.getNom().compareTo("visualiser_EDT")==0)
		{
			//Retourne la liste des cours trier
			bd.testAffiche();
			Jours semaine = (Jours)methode.getParametres().elementAt(0);
			visualiser_EDT(methode,semaine);
			
		}
		else if(methode.getNom().compareTo("recuperer_EDT")==0)
		{
			out.writeObject(this.recuperer_EDT(methode));
		}
		else if(methode.getNom().compareTo("recuperer_EDT_Promo")==0)
		{
			Vector<Vector<Cours>> liste_cours = new Vector<Vector<Cours>>();
			Responsable resp;
			try {
				resp = (bd.getPromotion((String)methode.getParametres().firstElement())).getResp();
				trie_par_jour(bd.getCoursPromotion(resp), liste_cours);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.writeObject(liste_cours);
		}
		else if(methode.getNom().compareTo("afficher_liste_contacts")==0)
		{
			//Retourne la liste de responsable et d'enseignant
			out.writeObject(bd.getRespEns());
		}
		else if(methode.getNom().compareTo("Choisir_EDT")==0)
		{
			typeEDT = (Integer)methode.getParametres().firstElement();
		}
		else if(methode.getNom().compareTo("envoi_email")==0)
		{
			String email = (String)methode.getParametres().elementAt(0);
			String sujet = (String)methode.getParametres().elementAt(1);
			String message = (String)methode.getParametres().elementAt(2);
			SimpleMailSender new_mail = new SimpleMailSender();
			out.writeObject(new_mail.envoimail(email, utilisateur.getEmail(), sujet, message));
		}
		else if(methode.getNom().compareTo("recuperer_listes")==0)
		{
			Vector[] table = {bd.getSalles(),bd.getMatieres(),bd.getGroupesResp((Responsable)utilisateur),bd.getEns(),bd.getCoursPromotion((Responsable)utilisateur)};//		
			out.writeObject(table);
		}
		else if(methode.getNom().compareTo("Saisir_EDT")==0)
		{
			Saisir_EDT(methode);
			bd.testAffiche();
		}
		else if(methode.getNom().compareTo("Modifier_EDT")==0)
		{
			Modifier_EDT(methode);
		}
		else if(methode.getNom().compareTo("Supprimer_EDT")==0)
		{
			Supprimer_EDT((Cours)methode.getParametres().firstElement());
		}
		else if(methode.getNom().compareTo("close")==0)
		{
			if (utilisateur!=null)
			{	
				if(utilisateur.getClass()==Responsable.class)
				{
					bd.sauveBDD();
					bd.sauvegarde();
				}
			}
			FermerConnexion();
		}
		else if(methode.getNom().compareTo("")==0)
		{
			
		}
		
		//A la fin de chaque signal on sauvegarde la nouvelle base de donnees
		bd.sauveBDD();
	}
	

	private void Supprimer_EDT(Cours cours) throws IOException {
		Boolean ok = bd.supprime_cours(cours);
		out.writeObject(ok);
	}

	private void Saisir_EDT(Signal methode) throws IOException {
		Boolean ok= true;
		
		Matiere mat = (Matiere)methode.getParametres().elementAt(0);
		Salle salle = (Salle)methode.getParametres().elementAt(1);
		Creneau cren = (Creneau)methode.getParametres().elementAt(2);
		Groupe gp = (Groupe)methode.getParametres().elementAt(3);
		Enseignant ens = (Enseignant)methode.getParametres().elementAt(4);
		
		try {
			bd.addCours(new Cours(mat, salle, gp, cren, ens));
		} catch (Exception e) {
			ok=false;
		}
		
		out.writeObject(ok);
	}

	private void Modifier_EDT(Signal methode) throws IOException {
		Boolean ok = true;
		
		Matiere mat = (Matiere)methode.getParametres().elementAt(0);
		Salle salle = (Salle)methode.getParametres().elementAt(1);
		Creneau cren = (Creneau)methode.getParametres().elementAt(2);
		Groupe gp = (Groupe)methode.getParametres().elementAt(3);
		Enseignant ens = (Enseignant)methode.getParametres().elementAt(4);
		
		Cours c = bd.getCours(cren, salle);
		if(c==null) ok=false; //cours inexistant pas de modification
		else if (!(c.getGroupe().getResponsable().egal((Responsable)utilisateur)))
		{
			ok=false;
		}
		else
		{
			c.setMatiere(mat);
			c.setEnseignant(ens);
			c.setGroupe(gp);
		}
		
		out.writeObject(ok);
		
	}

	private void trie_par_jour(Vector<Cours> cours, Vector<Vector<Cours>> liste_cours)
	{
		String date="";
		
		for(int i =0; i<cours.size(); i++)
		{
			//A chaque novelle date on ajoute un vecteur de cours
			if(cours.elementAt(i).getCreneau().getDate().toString().compareTo(date)!=0)
			{
				liste_cours.add(new Vector<Cours>());
				date = cours.elementAt(i).getCreneau().getDate().toString();
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
		System.out.println("Connection: "+nom+ "  " + mdp);
		
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
		
		out.writeObject(ok);
		
		if(ok)
		{
			typeUtilisateur = Personne.ETUDIANT;
			if(utilisateur.getClass()==Responsable.class)
			{
				typeUtilisateur= Personne.RESPONSABLE;
			}
			else if(utilisateur.getClass()==Enseignant.class)
			{
				typeUtilisateur= Personne.ENSEIGNANT;
			}
			out.writeObject(typeUtilisateur);
		}
		
		//Si on a pas trouve l'utilisateur
		/*if (!ok)
		{
			System.exit(0);
		} */
	}

	private Vector<Vector<Cours>> recuperer_EDT(Signal methode)
	{
		Vector<Vector<Cours>> liste_cours = new Vector<Vector<Cours>>();
		
		switch(this.typeUtilisateur)
		{
			case Personne.RESPONSABLE :
			{
				switch (this.typeEDT)
				{
					case Gestion_EDT.PROMOTION : trie_par_jour(bd.getCoursPromotion((Responsable)utilisateur), liste_cours);break;
					case Gestion_EDT.SALLE : trie_par_jour(bd.getCoursSalle((Salle)methode.getParametres().firstElement()), liste_cours);break;
				}
				break;
			}
			case Personne.ENSEIGNANT :
			{
				trie_par_jour(((Enseignant)utilisateur).getListe_cours(), liste_cours);
				break;
			}
			case Personne.ETUDIANT :
			{
				trie_par_jour(bd.getCoursGroupes(((Etudiant)utilisateur).getGroupes()), liste_cours);
				break;
			}
		}
		
		return liste_cours;
	}
	
	private void visualiser_EDT(Signal methode,Jours Semaine) throws IOException
	{
		Vector<Vector<Cours>> liste_cours = recuperer_EDT(methode);
		
		/* on recupere seulement ceux de la semaine desiree */
		Vector<Vector<Cours>> tabCours = new Vector<Vector<Cours>>();
		
		int j=1, i=0;
		//System.out.println(Semaine.getJours(j));
		Date jour = Semaine.getJours(j);
		while(i< liste_cours.size() && j<6)
		{
			Cours c = liste_cours.elementAt(i).firstElement();
			int test =c.compareJour(jour);
			if(test==0)
			{
				tabCours.add(liste_cours.elementAt(i));
				j++;
				jour = Semaine.getJours(j);
				i++;
			}
			else if(test<0)
			{
				i++;
			}
			else if(test>0) //liste_cours[i] apres le jour 
			{
				tabCours.add(new Vector<Cours>());
				j++;
				jour = Semaine.getJours(j);
				
			}
		}
		
		while(tabCours.size()<5)
		{
			tabCours.add(new Vector<Cours>());
		}
		
		
		out.writeObject(tabCours);
	}
}
