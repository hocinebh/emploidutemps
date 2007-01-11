package bdd;

import java.util.Iterator;
import java.util.Vector;


public class Etudiant extends Personne {

	//private int semestre;
	private Vector<Groupe> groupes;
	private Promotion promo;
	
	/**
	 * @param username
	 * @param password
	 * @param nom
	 * @param prenom
	 * @param adresse
	 * @param email
	 * @param tel
	 * @param semestre
	 * @param specialite
	 */
	public Etudiant(String num, String username, String password, String nom, String prenom, String adresse, String email, int tel) {
		super(num,username, password, nom, prenom, adresse, email, tel);
		groupes = new Vector<Groupe>();
	}

	
	
	/**
	 * @return Returns the promo.
	 */
	public Promotion getPromo() {
		return promo;
	}

	/**
	 * @param promo The promo to set.
	 */
	public void setPromo(Promotion promo) {
		this.promo = promo;
	}



	public void ajouteGroupe(Groupe g)
	{
		groupes.add(g);
	}

	

	/**
	 * @return the groupes
	 */
	public Vector<Groupe> getGroupes() {
		return groupes;
	}



	/**
	 * @param groupes the groupes to set
	 */
	public void setGroupes(Vector<Groupe> groupes) {
		this.groupes = groupes;
	}

	public boolean estDuGroupe(Groupe g)
	{		
		return groupes.contains(g);
	}


	private String AfficheGroupes()
	{
		String txt= "";
		Iterator i = groupes.iterator();
		while(i.hasNext())
		{
			txt=txt+((Groupe)i.next()).toString()+" ";
		}
		return txt;
	}

	/*public String toString() {
		String text = "Personne "+num_personne+" "+this.nom+" "+this.prenom+"/n Login : "+this.username+" mdp : "+this.password+"/n";// Groupes : "+AfficheGroupes();
		return text;
	}*/

}
