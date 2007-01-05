package Systeme;

import java.io.Serializable;
import java.util.Vector;

public class Signal implements Serializable{
	private String nom;
	private Vector parametres;
	/**
	 * @param nom
	 */
	public Signal(String nom) {
		super();
		this.nom = nom;
		this.parametres= new Vector();
	}
	/**
	 * @param nom
	 * @param parametres
	 */
	public Signal(String nom, Vector parametres) {
		super();
		this.nom = nom;
		this.parametres = parametres;
	}
	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * @return the parametres
	 */
	public Vector getParametres() {
		return parametres;
	}
	/**
	 * @param parametres the parametres to set
	 */
	public void setParametres(Vector parametres) {
		this.parametres = parametres;
	}
	
	public void addParametre(Object o)
	{
		parametres.add(o);
	}
}
