package bdd;

import java.io.Serializable;

public class Salle implements Serializable{

	private static int nbSalle =0;
	
	/*Déclaration des constantes*/
	private static final int TP = 1;
	private static final int AMPHI =2;
	private static final int COURS = 3;
	
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
	
	
	
	
}
