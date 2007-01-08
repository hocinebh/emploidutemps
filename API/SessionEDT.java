package API;

import java.io.OutputStream;
import java.util.Vector;

import Systeme.Client;
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
		Vector<Vector<Cours>>ListeCours;
	}

	public void getEDT(OutputStream outStreamXML, String promotion)
			throws Exception {
		// TODO Auto-generated method stub

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
