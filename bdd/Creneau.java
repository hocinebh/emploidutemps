package bdd;

import java.io.Serializable;
import java.sql.Time;
import java.util.GregorianCalendar;


public class Creneau implements Serializable{

	private GregorianCalendar  Datedebut; //contient date et heure début
    private Time Duree;
	
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
		
		Datedebut = new GregorianCalendar ();
		Datedebut.set(Integer.parseInt(tmpJ[2]), Integer.parseInt(tmpJ[1]), Integer.parseInt(tmpJ[0]), Integer.parseInt(tmpH[0]), Integer.parseInt(tmpH[1]));
		Duree = new Time((Long.parseLong(tmpH[0])+Long.parseLong(tmpD[0]))*60+(Long.parseLong(tmpH[1])+Long.parseLong(tmpD[0]))*60);
	}
	
}
