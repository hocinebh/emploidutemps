package be4gi;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Interface devant être réalisée par le logiciel de gestion des emplois du temps
 * 
 * @author castan
 *
 */
public interface Edt {
	
	/**
	 * Cette méthode permet d'initialiser les données à partir d'un flux XML
	 * valide suivant la dtd bdedt.dtd fournie dans le sujet du BE.
	 * @param inStreamXML le flux XML valide suivant la dtd bdedt.dtd
	 * @return vrai si les données ont été initialisées
	 */
	public boolean initialiserBase (InputStream inStreamXML);
	
	/**
	 * Cette méthode permet d'obtenir l'ensemble des données sous forme d'un
	 * flux XML valide suivant la dtd bdedt.dtd fournie dans le sujet du BE.
	 * 
	 * Le flux de sortie obtenue doit être compatible avec le flux d'entrée de
	 * la méthode initialiserBase(Reader inStreamXML);
	 * 
	 * @param outStreamXML le writer dans lequel on sauvegardera les données
	 * @return vrai si le writer a été correctement initialisé
	 */
	public  boolean sauvegarderBase(OutputStream outStreamXML);
	
	/**
	 * Cette méthode permet d'ouvrir une session. Tout utilisateur (inspecteur des études,
	 * enseignant, étudiant) peut ouvrir une session qui lui permettra d'effectuer des opérations
	 * sur l'emploi du temps, à partir du moment où il a été correctement identifié. Dans le cas 
	 * contraire, la création d'une session renverra la valeur null. Un même utilisateur doit
	 * pouvoir ouvrir plusieurs sessions en même temps.
	 * 
	 * @param login pour identifier l'utilisateur
	 * @param pass pour valider l'identification de l'utilisateur
	 * @return la session ouverte ou null si l'identification n'a pu être faite.
	 */
	public Session créerSession(String login, String pass);

}
