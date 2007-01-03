package bdd;

import java.io.Serializable;
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
	
	private void configureEnseignant() throws Exception
	{
		Iterator i = this.matiere.getListe_enseignement().iterator();
		
		
		while(i.hasNext() && (enseignant == null))
		{
			enseignant=((Enseignement)i.next()).getEnseignant(this.groupe);
		}
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
	
		
	
}
