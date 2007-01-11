package API;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;

import be4gi.Edt;
import be4gi.Session;
import Interfaces.Interface_Connexion;
import Systeme.*;

public class EmploiDuTemps implements Edt {
	private Gestion_BDD bd; 
	private static final String fichierXml ="XML/bdedtApi.xml"; 
	/**
	 * 
	 */
	public EmploiDuTemps() {
		//On lance le serveur
		Serveur.lanceServeur();
		//System.out.println("lancement serveur");
	}

	public Session créerSession(String login, String pass) {
		//System.out.println("creerSession");
		Client c = null;
		boolean ok = false;
		try {
			c = new Client();
			ok=c.Connexion(login, pass);
			if(ok)System.out.println("Client : "+login+" connecté");
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
		return new SessionEDT(ok, c);
	}

	public boolean initialiserBase(InputStream inStreamXML) {
		//System.out.println("InitialiserBase");
		boolean ok = true;
		try {
			//On ecrit dans le fichier utiliser au chargement de la bdd
			File fichier = new File(fichierXml);
			if(fichier.exists())fichier.delete();
			fichier.createNewFile();
			
			FileOutputStream fos = new FileOutputStream(fichier);
			while(inStreamXML.available()>0) fos.write(inStreamXML.read());
			bd= Gestion_BDD.getInstance();
			bd.chargement();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ok= false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ok= false;
		}
		return ok;
	}

	public boolean sauvegarderBase(OutputStream outStreamXML){
		//System.out.println("sauvegarderBase");
		boolean ok = true; 
		File fichier = new File(fichierXml);
		if(!fichier.exists())
		{
			System.out.println("Aucune base de données n'a été charger");
			ok = false;
		}
		else
		{
			try {
				FileInputStream fis = new FileInputStream(fichier);
				while(fis.available()>0)outStreamXML.write(fis.read());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ok=false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ok=false;
			}
		}
		return ok;
	}

	public static void main(String[] args)
	{
		EmploiDuTemps edt = new EmploiDuTemps();
		Session s= edt.créerSession("blanche", "neige"); 
		
		try {
			OutputStream outStreamXML = new FileOutputStream("XML/testEdt.xml");
			OutputStream outStreamXML2 = new FileOutputStream("XML/testEdt2.xml");
			OutputStream outStreamXML3 = new FileOutputStream("XML/testEdt3.xml");
			OutputStream outStreamXML4 = new FileOutputStream("XML/testEdt4.xml");
			s.getEDT(outStreamXML);
			s.getEDT(outStreamXML2,"4RT");
			s.getEmail(outStreamXML3);
			s.getRéservation(outStreamXML4, "3");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
