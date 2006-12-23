/**
 * 
 */
package bdd;

import java.io.Serializable;
import java.util.Vector;

/**
 * @author Tonya Vo Thanh & Alexander Remen
 *
 */
public class Matiere implements Serializable{

	private static int nbMat =0;
	
	/*Declaration des constantes*/
	public static final int obligatoire =1;
	public static final int optionnelle =2;
	
	/*Declaration des attributs*/
	private String num_matiere;
	private String intitule;
	private int type;
	private Vector<Enseignement> liste_enseignement;
	
	/**
	 * @param num_matiere
	 * @param intitule
	 * @param type
	 */
	public Matiere(String num_matiere, String intitule, int type) {
		super();
		this.num_matiere = num_matiere;
		this.intitule = intitule;
		this.type = type;
		liste_enseignement = new Vector<Enseignement>();
	}



	public void ajoutEnseignement(Enseignement e)
	{
		liste_enseignement.add(e);
	}



	/**
	 * @return Returns the num_matiere.
	 */
	public String getNum_matiere() {
		return num_matiere;
	}



	/**
	 * @param num_matiere The num_matiere to set.
	 */
	public void setNum_matiere(String num_matiere) {
		this.num_matiere = num_matiere;
	}

	/**
	 * @return Returns the liste_enseignement.
	 */
	public Vector<Enseignement> getListe_enseignement() {
		return liste_enseignement;
	}



	/**
	 * @param liste_enseignement The liste_enseignement to set.
	 */
	public void setListe_enseignement(Vector<Enseignement> liste_enseignement) {
		this.liste_enseignement = liste_enseignement;
	}
	
	
	

	
}
