package bdd;

import java.io.Serializable;
import java.sql.Time;
import java.util.GregorianCalendar;


public class Creneau implements Serializable{

	public static final int AVANT = 0;
	public static final int APRES = 1;
	public static final int ERREUR = 2;
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
		//if(month==12) month=0;
		month--;
		Datedebut = new GregorianCalendar (Integer.parseInt(tmpJ[2]),month, Integer.parseInt(tmpJ[0]), Integer.parseInt(tmpH[0]), Integer.parseInt(tmpH[1]));
		Duree = Time.valueOf(duree+":00");
	}

	public String date() {
		int month = Datedebut.get(GregorianCalendar.MONTH);
		//if(month==0) month=12;
		month++;
		return Datedebut.get(GregorianCalendar.DAY_OF_MONTH)+"/"+month+"/"+Datedebut.get(GregorianCalendar.YEAR);
	}

	public String heure() {
		int minute = Datedebut.get(GregorianCalendar.MINUTE);
		String min=""+minute;
		if(minute<10)min="0"+minute;
		return Datedebut.get(GregorianCalendar.HOUR_OF_DAY)+":"+min;
	}
	public String heureFin()
	{
		int heure = (Datedebut.get(GregorianCalendar.HOUR_OF_DAY)+Duree.getHours());
		int minutes = Datedebut.get(GregorianCalendar.MINUTE)+Duree.getMinutes();
		String min=""+minutes;
		if(minutes<10)min="0"+minutes;
		
		return heure+":"+min;		
	}

	public String duree() {
		return Duree.toString().substring(0, 5);
	}
	
	public GregorianCalendar getDate()
	{
		return new GregorianCalendar(Datedebut.get(GregorianCalendar.YEAR),Datedebut.get(GregorianCalendar.MONTH),Datedebut.get(GregorianCalendar.DAY_OF_MONTH));
	}
	
	/**
	 * @return the datedebut
	 */
	public GregorianCalendar getDatedebut() {
		return Datedebut;
	}

	/**
	 * @param datedebut the datedebut to set
	 */
	public void setDatedebut(GregorianCalendar datedebut) {
		Datedebut = datedebut;
	}

	/**
	 * @return the duree
	 */
	public Time getDuree() {
		return Duree;
	}

	/**
	 * @param duree the duree to set
	 */
	public void setDuree(Time duree) {
		Duree = duree;
	}
	
	public static String DatetoString(GregorianCalendar date) {
		int month = date.get(GregorianCalendar.MONTH);
		month++;
		int minute = date.get(GregorianCalendar.MINUTE);
		String min=""+minute;
		if(minute<10)min="0"+minute;
		return date.get(GregorianCalendar.DAY_OF_MONTH)+"/"+month+"/"+date.get(GregorianCalendar.YEAR)+" "+date.get(GregorianCalendar.HOUR_OF_DAY)+":"+min;
	}

	
	public GregorianCalendar getDateFin()
	{
		int heure = (Datedebut.get(GregorianCalendar.HOUR_OF_DAY)+Duree.getHours());
		int minutes = Datedebut.get(GregorianCalendar.MINUTE)+Duree.getMinutes();
		GregorianCalendar DateFin = new GregorianCalendar(Datedebut.get(GregorianCalendar.YEAR),Datedebut.get(GregorianCalendar.MONTH),Datedebut.get(GregorianCalendar.DAY_OF_MONTH),heure,minutes);
		//DateFin.add(GregorianCalendar.HOUR_OF_DAY, (int) Duree.getHours());
		//System.out.println("duree "+ +":"+Duree.getMinutes());
		//System.out.println("deb : "+DatetoString(Datedebut)+" fin : "+DatetoString(DateFin));
		return DateFin;
	}
	
	public int compare(Creneau c)
	{		
		int retour=ERREUR;
		
		//System.out.println("date1 "+date()+" "+heure());
		//System.out.println("date2 "+c.date()+" "+c.heure());
		
		if((this.Datedebut.before(c.getDatedebut())) && (this.getDateFin().before(c.getDatedebut())))
		{
				retour=AVANT;			
		}
		else if((this.Datedebut.after(c.getDatedebut()))&& (this.Datedebut.after(c.getDateFin()))) 
		{
			//TODO 
			retour=APRES;
		}
		return retour;
	}
	
}
