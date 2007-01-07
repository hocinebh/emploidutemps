package bdd;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

public class Cours implements Serializable{

	private Matiere matiere;
	private Groupe groupe;
	private Enseignant enseignant;
	private Salle salle;
	private Creneau creneau;
	
	/**
	 * @param creneau
	 * @param salle
	 * @param groupe
	 * @param matiere
	 * @throws Exception 
	 */
	public Cours(Creneau creneau, Salle salle, Groupe groupe, Matiere matiere) throws Exception {
		super();
		this.creneau = creneau;
		this.salle = salle;
		this.groupe = groupe;
		this.matiere = matiere;
		this.enseignant = null;
		configureEnseignant();
	}
	
	/**
	 * @param matiere
	 * @param salle
	 * @param groupe
	 * @param creneau
	 * @param enseignant
	 */
	public Cours(Matiere matiere, Salle salle, Groupe groupe, Creneau creneau, Enseignant enseignant) {
		super();
		this.matiere = matiere;
		this.salle = salle;
		this.groupe = groupe;
		this.creneau = creneau;
		this.enseignant = enseignant;
	}



	private void configureEnseignant() throws Exception
	{
		enseignant=matiere.getEnseignant(this.groupe);
		if (enseignant == null) throw new Exception("Erreur cours : enseignant ou groupe inexistant");
		enseignant.ajoutCours(this);
	}

	/**
	 * @return the enseignant
	 */
	public Enseignant getEnseignant() {
		return enseignant;
	}

	/**
	 * @param enseignant the enseignant to set
	 */
	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}

	/**
	 * @return the groupe
	 */
	public Groupe getGroupe() {
		return groupe;
	}

	/**
	 * @param groupe the groupe to set
	 */
	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	/**
	 * @return the matiere
	 */
	public Matiere getMatiere() {
		return matiere;
	}

	/**
	 * @param matiere the matiere to set
	 */
	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	/**
	 * @return the salle
	 */
	public Salle getSalle() {
		return salle;
	}

	/**
	 * @param salle the salle to set
	 */
	public void setSalle(Salle salle) {
		this.salle = salle;
	}

	/**
	 * @return the creneau
	 */
	public Creneau getCreneau() {
		return creneau;
	}

	/**
	 * @param creneau the creneau to set
	 */
	public void setCreneau(Creneau creneau) {
		this.creneau = creneau;
	}


	public String toString() {
		
		return "Date : "+this.creneau.date()+" "+this.creneau.heure()+", Matiere : "+this.matiere.getIntitule()+", groupe : "+groupe.getnum_groupe();
	}

	public int compareJour(Date jour1) {
		GregorianCalendar d1 = this.creneau.getDate();
		//String jour[]=jour1.toString().split(":");
		//System.out.println(jour1.getDay()+"/"+jour1.getMonth()+"/"+jour1.getYear());
		GregorianCalendar d2 = new GregorianCalendar();
		d2.setTime(jour1);
		d2.set(GregorianCalendar.HOUR_OF_DAY, 0);
		d2.set(GregorianCalendar.MINUTE, 0);
		d2.set(GregorianCalendar.SECOND, 0);
		d2.set(GregorianCalendar.MILLISECOND, 0);
		
		System.out.println("d1 : "+Creneau.DatetoString(d1)+" d2 : "+Creneau.DatetoString(d2));
		
		return d1.compareTo(d2);
	}
	
		
	
}
