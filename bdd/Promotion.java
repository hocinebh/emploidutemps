package bdd;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

public class Promotion implements Serializable{
	
	/*Declaration des attributs*/
	private String nom_promotion;
	private Responsable resp;
	private Vector<Etudiant> liste_etudiant;

	/**
	 * @param nom_promotion
	 * @param liste_etudiant
	 */
	public Promotion(String nom_promotion, Vector<Etudiant> liste_etudiant) {
		super();
		this.nom_promotion = nom_promotion;
		this.liste_etudiant = liste_etudiant;
		
		Iterator i = this.liste_etudiant.iterator();
		while(i.hasNext())
		{
			((Etudiant) i.next()).setPromo(this);
		}
			
	}

	/**
	 * @return the resp
	 */
	public Responsable getResp() {
		return resp;
	}

	/**
	 * @param resp the resp to set
	 */
	public void setResp(Responsable resp) {
		this.resp = resp;
	}

	/**
	 * @return the num_promotion
	 */
	public String getNom_promotion() {
		return nom_promotion;
	}

	/**
	 * @return the liste_etudiant
	 */
	public Vector<Etudiant> getListe_etudiant() {
		return liste_etudiant;
	}
	
	public void ajoutEtudiant(Etudiant et)
	{
		liste_etudiant.add(et);
	}

	/**
	 * @param liste_etudiant the liste_etudiant to set
	 */
	public void setListe_etudiant(Vector<Etudiant> liste_etudiant) {
		this.liste_etudiant = liste_etudiant;
	}
	
	
	
	
}
