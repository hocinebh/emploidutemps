package Systeme;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import Systeme.Signal;
import bdd.Cours;
import bdd.Creneau;
import bdd.Enseignant;
import bdd.Groupe;
import bdd.Matiere;
import bdd.Personne;
import bdd.Salle;

import Interfaces.Interface_Connexion;
import Interfaces.Interface_EDT;
import Interfaces.Interface_Reservation;

public class Client {

	static final int port = 8080;
	private Socket soc;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private int typeUtilisateur;
	
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

	}
	
	public  Vector<Vector<Cours>> recuperercoursdelasemaine(Jours semaine) throws IOException, ClassNotFoundException{
		Signal sig_visu = new Signal("visualiser_EDT");
		sig_visu.addParametre(semaine);
		Signaler(sig_visu);
		return  (Vector<Vector<Cours>>)in.readObject();
	}
	
	public Vector<Vector<Cours>> recupererEDT(String promo) throws IOException, ClassNotFoundException{
		Signal s;
		if(promo.compareTo("")==0) s = new Signal("recuperer_EDT"); 
		else 
		{
			s = new Signal("recuperer_EDT_Promo");
			s.addParametre(promo);
		}
		Signaler(s);
		return (Vector<Vector<Cours>>)in.readObject();
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void Afficher_Emploi_du_temps() throws IOException, ClassNotFoundException{
		Interface_EDT Graphic_EDT= new Interface_EDT();
		
		
		Graphic_EDT.Afficher_EDT(this);
		/* initialisation de la fenetre mail */
		Signal s = new Signal("afficher_liste_contacts");
		Signaler(s);
		Vector<Personne> ListePersonne = (Vector<Personne>)in.readObject();
		Graphic_EDT.init_fenetre_mail(ListePersonne,this);
		/* Si c'est un inspecteur */
		if(typeUtilisateur==Personne.RESPONSABLE)
		{
			Interface_Reservation FenetreReservation = new Interface_Reservation();
			FenetreReservation.Affiche_Interface_Reservation(this);
		}
	}
	
	public Boolean Connexion(String login, String mdp) throws IOException, ClassNotFoundException
	{
		Signal s = new Signal("Connexion");
		
		s.addParametre(login);
		s.addParametre(mdp);
		Signaler(s);

		Boolean ok = ((Boolean)in.readObject());
		if(ok) typeUtilisateur = ((Integer)in.readObject());
		
		return ok;
	}
	
	public Boolean Ajouter_Cours(Matiere mat, Salle salle, Creneau cren, Groupe gp, Enseignant ens) throws IOException, ClassNotFoundException{
		Signal s = new Signal("Saisir_EDT");
		
		s.addParametre(mat);
		s.addParametre(salle);
		s.addParametre(cren);
		s.addParametre(gp);
		s.addParametre(ens);
		Signaler(s);
		
		return ((Boolean)in.readObject());
	}
	public Boolean Envoi_email(String email, String sujet, String Message) throws IOException, ClassNotFoundException {
		Signal s = new Signal("envoi_email");
		
		s.addParametre(email);
		s.addParametre(sujet);
		s.addParametre(Message);
		Signaler(s);
		
		return ((Boolean)in.readObject());
	}
	public Vector[] Recup_Listes_Reservation() throws IOException, ClassNotFoundException{
		
		Signal s = new Signal("recuperer_listes");
		Signaler(s);
		return ((Vector[])in.readObject());
	}
	
	public void Signaler(Signal s) throws IOException
	{
		out.writeObject(s);
		System.out.println("envoi d'un signal "+s.getNom());
	}
	
	public void FermerConnexion()
	{
		try {
			Signaler(new Signal("close"));
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

			Interface_Connexion Login = new Interface_Connexion();
			Login.affiche_login_screen(c);
			
			//c.FermerConnexion();
			//System.out.println("Fermeture client");
			
			
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
