package bdd;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class Creneau implements Serializable{

	private Date Date;
	private Time HeureDeb;
	private Time HeureFin;
	
	/**
	 * Constructeur d'un créneau
	 * 
	 * @param jour - au format dd/mm/yyyy
	 * @param heureDeb - au format hh:mm
	 * @param duree - au format hh:mm
	 * @throws Exception 
	 */
	public Creneau (String jour, String heureDeb, String duree) throws Exception
	{
		String tmpJ [] = jour.split("/");
		if (tmpJ.length!=3) throw new Exception("Creation creneau : Erreur format date");
		String tmpH [] = heureDeb.split(":");
		if (tmpH.length!=2) throw new Exception("Creation creneau : Erreur format heure début");
		String tmpD [] = heureDeb.split(":");
		if (tmpD.length!=2) throw new Exception("Creation creneau : Erreur format duree");
		
		Date = new Date(Integer.parseInt(tmpJ[2]), Integer.parseInt(tmpJ[1]), Integer.parseInt(tmpJ[0]));
		HeureDeb = new Time((Long.parseLong(tmpH[0])*60+Long.parseLong(tmpH[1]))*60);
		HeureFin = new Time((Long.parseLong(tmpH[0])+Long.parseLong(tmpD[0]))*60+(Long.parseLong(tmpH[1])+Long.parseLong(tmpD[0]))*60);
	}
	
}
