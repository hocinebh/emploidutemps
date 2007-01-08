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
	
	private static final String fichierXml ="XML/bdedtApi.xml"; 
	/**
	 * 
	 */
	public EmploiDuTemps() {
		//On lance le serveur
		Serveur.lanceServeur();
	}

	public Session créerSession(String login, String pass) {
		Client c = null;
		boolean ok = false;
		try {
			c = new Client();
			ok=true;
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
		boolean ok = true;
		try {
			//On ecrit dans le fichier utiliser au chargement de la bdd
			File fichier = new File(fichierXml);
			if(fichier.exists())fichier.delete();
			fichier.createNewFile();
			
			FileOutputStream fos = new FileOutputStream(fichier);
			fos.write(inStreamXML.read());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ok= false;
		}
		return ok;
	}

	public boolean sauvegarderBase(OutputStream outStreamXML){
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
				outStreamXML.write(fis.read());
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
		Session s= edt.créerSession("tvo_than", "toto2"); 
		
		try {
			OutputStream outStreamXML = new FileOutputStream("XML/testEdt.xml");
			s.getEDT(outStreamXML);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
