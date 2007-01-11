package bdd;

import java.io.Serializable;
import java.util.Vector;

public class Groupe implements Serializable {

	/*Declaration des attributs static*/
	private static int nbSpec =0; 
	
	/*D�claration des attributs*/
	private String num_groupe;
	private Responsable responsable;

	/**
	 * @param num_groupe
	 */
	public Groupe(String num_groupe) {
		super();
		this.num_groupe = num_groupe;
	}

	/**
	 * @return the num_groupe
	 */
	public String getnum_groupe() {
		return num_groupe;
	}

	/**
	 * @param num_groupe the num_groupe to set
	 */
	public void setnum_groupe(String num_groupe) {
		this.num_groupe = num_groupe;
	}

	/**
	 * @return Returns the responsable.
	 */
	public Responsable getResponsable() {
		return responsable;
	}

	/**
	 * @param responsable The responsable to set.
	 */
	public void setResponsable(Responsable responsable) {
		this.responsable = responsable;
	}
	
	public boolean egal(Groupe g)
	{
		return ((this.num_groupe.compareTo(g.getnum_groupe()))==0);
	}

	public String toString(){
		if (this != null) 
			return num_groupe;
		else return "";
		
	}
	
	
}
