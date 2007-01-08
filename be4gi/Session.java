package be4gi;

import java.io.OutputStream;

/**
 * @author castan
 *
 */
public interface Session {
	
	/**
	 * méthode permettant de fermer une session ouverte. Si la session avait déjà été fermée, une
	 * exception sera levée.
	 * 
	 * @throws Exception levée si la session était déjà fermée.
	 */
	public void fermer() throws Exception;
	
	/**
	 * méthode permettant de savoir si une session est ouverte ou fermée
	 * @return true si la session est ouverte, false sinon
	 */
	public boolean estOuverte();
	
	/**
	 * cette méthode permet de recevoir sous forme d'un flux XML, l'emploi du temps correspondant
	 * à l'utilisateur associé à la session (si c'est un inspecteur d'étude, c'est l'emploi du 
	 * temps de l'année dont il s'occupe.
	 * 
	 * Une exception est levée si l'utilisateur associé à la session n'a pas les droits requis
	 * pour une telle requête.
	 */
	public void getEDT(OutputStream outStreamXML) throws Exception;
	
	/**
	 * cette méthode permet de recevoir sous forme d'un flux XML, l'emploi du temps correspondant
	 * à l'ensemble d'une promotion. Le format correspondra à la dtd du fichier d'initialisation de
	 * la base de donnée, avec seulement l'élément "edt" comme élément racine.
	 * 
	 * une exeption est levée si la promotion n'existe pas.
	 */
	public void getEDT(OutputStream outStreamXML, String promotion) throws Exception;
	
	/**
	 * cette méthode permet de recevoir sous forme d'un flux XML, les reservations pour 
	 * une salle donnée. Le format correspondra à la dtd du fichier d'initialisation de la base de
	 * donnée, avec seulement l'élément "edt" comme élément racine.
	 * 
	 * une exeption est levée si la salle n'existe pas.
	 */
	public void getRéservation(OutputStream outStreamXML, String salle) throws Exception;
	
	/**
	 * cette méthode permet d'effectuer une réservation. Les formats utilisés pour les chaînes de
	 * caractères correspondant aux date, heure et durée sont les mêmes que ceux utilisés dans le
	 * fichier d'initialisation de la base de donnée.
	 * 
	 * une exception est levée si l'opération n'est pas permise. La cause sera indiquée en tant que
	 * message associé à l'exception (heure incorrecte, salle inexistante, salle déjà réservée, etc.)
	 */
	public void setRéservation(String date, String heure, String durée, String salle, 
		                	   String groupe, String matière) throws Exception;

	/**
	 * Cette méthode permet d'obtenir l'ensemble des emails sous forme d'un flux xml. Le format 
	 * correspondra à la dtd du fichier d'initialisation de la base de donnée, mais avec seulement le
	 * contenu des éléments  "inspecteurs", "enseignants" et "étudiants" sans les
	 * les logins et les mots de passe.
	 * @param outStreamXML
	 */
	public void getEmail (OutputStream outStreamXML) throws Exception;
}
