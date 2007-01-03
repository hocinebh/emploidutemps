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
		String tmpD [] = duree.split(":");
		if (tmpD.length!=2) throw new Exception("Creation creneau : Erreur format duree");
		
		int month = Integer.parseInt(tmpJ[1]);
		if(month==12) month=0;
		Datedebut = new GregorianCalendar (Integer.parseInt(tmpJ[2]),month, Integer.parseInt(tmpJ[0]), Integer.parseInt(tmpH[0]), Integer.parseInt(tmpH[1]));
		Duree = Time.valueOf(duree+":00");
	}

	public String date() {
		int month = Datedebut.get(GregorianCalendar.MONTH);
		if(month==0) month=12;
		return Datedebut.get(GregorianCalendar.DAY_OF_MONTH)+"/"+month+"/"+Datedebut.get(GregorianCalendar.YEAR);
	}

	public String heure() {
		int minute = Datedebut.get(GregorianCalendar.MINUTE);
		String min=""+minute;
		if(minute<10)min="0"+minute;
		return Datedebut.get(GregorianCalendar.HOUR_OF_DAY)+":"+min;
	}

	public String duree() {
		return Duree.toString().substring(0, 5);
	}
	
}
