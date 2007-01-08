package API;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import Systeme.*;
import bdd.Cours;
import be4gi.Session;

public class SessionEDT implements Session {

	private boolean ouverte;
	private Client client;

	/**
	 * @param ouverte
	 * @param client
	 */
	public SessionEDT(boolean ouverte, Client client) {
		this.ouverte = ouverte;
		this.client=null;
		if(ouverte)	this.client = client;
	}

	public boolean estOuverte() {
		return ouverte;
	}

	public void fermer() throws Exception {
		if(!ouverte) throw new Exception("Connexion déja fermer");
		client.FermerConnexion();
	}

	public void getEDT(OutputStream outStreamXML) throws Exception {
		if(!ouverte) throw new Exception("Aucune connexion ouverte");
		
		Vector<Vector<Cours>>ListeCours = client.recupererEDT("");
		
		Element racine = new Element("edt");
		Document document = new Document(racine);
		
		Iterator i = ListeCours.iterator();
		while(i.hasNext())
		{
			Gestion_BDD.sauvegardeCours(racine, (Vector<Cours>) i.next());
		}
		
		try
		   {
		      //On utilise ici un affichage classique avec getPrettyFormat()
		      XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
		      sortie.output(document, outStreamXML);
		      Gestion_BDD.afficheXML(document);
		   }
		   catch (java.io.IOException e){e.printStackTrace();}
	}

	public void getEDT(OutputStream outStreamXML, String promotion)throws Exception {
		if(!ouverte) throw new Exception("Aucune connexion ouverte");
		
		Vector<Vector<Cours>>ListeCours = client.recupererEDT(promotion);
		if(ListeCours.size()==0)throw new Exception("Promotion inexistante");
		
		Element racine = new Element("edt");
		Document document = new Document(racine);
		
		Iterator i = ListeCours.iterator();
		while(i.hasNext())
		{
			Gestion_BDD.sauvegardeCours(racine, (Vector<Cours>) i.next());
		}
		
		try
		   {
		      //On utilise ici un affichage classique avec getPrettyFormat()
		      XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
		      sortie.output(document, outStreamXML);
		      Gestion_BDD.afficheXML(document);
		   }
		   catch (java.io.IOException e){e.printStackTrace();}

	}

	public void getEmail(OutputStream outStreamXML) throws Exception {
		// TODO Auto-generated method stub

	}

	public void getRéservation(OutputStream outStreamXML, String salle)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void setRéservation(String date, String heure, String durée,
			String salle, String groupe, String matière) throws Exception {
		// TODO Auto-generated method stub

	}

}
