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
	
		
	
}
