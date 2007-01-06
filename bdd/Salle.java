package bdd;

import java.io.Serializable;

public class Salle implements Serializable{

	private static int nbSalle =0;
	
	/*Déclaration des constantes*/
	public static final int TP = 1;
	public static final int AMPHI =2;
	public static final int COURS = 3;
	
	/*Déclaration des attributs*/
	private String nom_salle;
	private int type_Salle;
	private int taille;
	
	public static int getTypeSalle(String nom)
	{
		int type_enseignement = -1;
		
		if(nom.compareToIgnoreCase("TP")==0){type_enseignement = Salle.TP;}
		else if(nom.compareToIgnoreCase("AMPHI")==0){type_enseignement = Salle.AMPHI;}
		else if(nom.compareToIgnoreCase("COURS")==0){type_enseignement = Salle.COURS;}
				
		return type_enseignement;
	}

	/**
	 * @param nom_salle
	 * @param salle
	 * @param taille
	 */
	public Salle(String nom_salle, int salle, int taille) {
		super();
		// TODO Auto-generated constructor stub
		this.nom_salle = nom_salle;
		type_Salle = salle;
		this.taille = taille;
	}

	/**
	 * @return Returns the nom_salle.
	 */
	public String getNom_salle() {
		return nom_salle;
	}

	/**
	 * @param nom_salle The nom_salle to set.
	 */
	public void setNom_salle(String nom_salle) {
		this.nom_salle = nom_salle;
	}

	/**
	 * @return the taille
	 */
	public int getTaille() {
		return taille;
	}

	/**
	 * @param taille the taille to set
	 */
	public void setTaille(int taille) {
		this.taille = taille;
	}

	/**
	 * @return the type_Salle
	 */
	public int getType_Salle() {
		return type_Salle;
	}

	/**
	 * @param type_Salle the type_Salle to set
	 */
	public void setType_Salle(int type_Salle) {
		this.type_Salle = type_Salle;
	}
	
	public String toString(){
		return (nom_salle);
	}
	
	
}
