package bdd;

import java.util.Vector;

public class Enseignant extends Personne {

	private static int nbPers =0;
	private Vector<Cours> liste_cours;
	
	/**
	 * @param num_personne
	 * @param username
	 * @param password
	 * @param nom
	 * @param prenom
	 * @param adresse
	 * @param email
	 * @param tel
	 */
	public Enseignant(String num_personne, String username, String password, String nom, String prenom, String adresse, String email, int tel) {
		super(num_personne, username, password, nom, prenom, adresse, email, tel);
		
		liste_cours = new Vector<Cours>();
	}

	public void ajoutCours(Cours c)
	{
		liste_cours.add(c);
	}
	
	public boolean egal(Enseignant ens)
	{
		return ((num_personne.compareTo(ens.getNum_personne()))==0);
	}
	


}
