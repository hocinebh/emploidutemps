/**
 * 
 */
package Systeme;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Time;
import java.util.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import org.jdom.filter.*;

import bdd.*;

/**
 * @author Tonya Vo Thanh et Alexander Remen
 *
 */
public class Gestion_BDD implements Serializable {

	//Definition des constantes
	private static final int ETUDIANT =1;
	private static final int RESPONSABLE =2;
	private static final int ENSEIGNANT =3;
	private static final String ficXml ="XML/bdedt2.xml";
	private static final String ficSauvegarde = "tmp/system";
	
	//Definition des attributs
	private File fichier;
	private org.jdom.Document document;
	private Element racine;
	private Vector<Personne> utilisateurs;
	private Vector<Promotion> promotions;
	private Vector<Groupe> groupes;
	private Vector<Matiere> matieres;
	private Vector<Salle> salles;
	private Vector<Cours> cours; 
	
	//Création d'un singleton
	private static Gestion_BDD instance;
	
	private Gestion_BDD(Boolean chargeXml)
	{
		//Création du fichier se sauvegarde si il n existe pas
		fichier = new File(ficSauvegarde);
		if(!fichier.exists())
		{
			try {
				fichier.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		//Initialisation des vecteurs
		utilisateurs = new Vector<Personne>();
		promotions = new Vector<Promotion>();
		groupes = new Vector<Groupe>();
		salles = new Vector <Salle>();
		cours = new Vector<Cours>();
		matieres = new Vector<Matiere>();
		
		//SAXBuilder sxb = new SAXBuilder(true);
		SAXBuilder sxb = new SAXBuilder();
		sxb.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		if(chargeXml)
		{
			try {
				document = sxb.build(new File(ficXml));
				System.out.println("Création du jdom");
				
				//On initialise un nouvel élément racine avec 
				//l'élément racine du document .
				racine = document.getRootElement();
				
				//affiche();
								
				System.out.println("debut du chargement");
				//Chargement du fichier xml
				chargement();			
			 }
		    // indicates a well-formedness or validity error
		    catch (JDOMException e) { 
		      System.out.println(ficXml + " is not valid.");
		      System.out.println(e.getMessage());
		    }  
		    catch (IOException e) { 
		      System.out.println("Could not check " + ficXml);
		      System.out.println(" because " + e.getMessage());
		    } 
		    catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				chargeBDD();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static Gestion_BDD getInstance()
	{	
		if(instance==null)
		{
			instance = new Gestion_BDD(true);
		}
		return instance;
	}
	
	public static Gestion_BDD getInstance(boolean ok)
	{	
		if(instance==null)
		{
			instance = new Gestion_BDD(ok);
		}
		return instance;
	}
	
//==============================================================
//  Fonctions de chargement a partir d'un fichier xml
//==============================================================
	
	/**
	 * Fonction qui charge le document xml dans la bdd
	 */
	
	private void chargement() throws Exception
	{
		chargePromotion(racine.getChild("étudiants").getChildren());
		chargeResp(racine.getChild("inspecteurs").getChildren());
		chargeEns(racine.getChild("enseignants").getChildren());
		chargeGroupes(racine.getChild("groupes").getChildren());
		chargeMatieres(racine.getChild("matières").getChildren());
		chargeSalles(racine.getChild("salles").getChildren());
		chargeEdt(racine.getChild("edt").getChildren());
		
		
		
	}
	
	/**
	 * Fonction qui charge les responsables
	 * @param listResponsables
	 * @throws Exception
	 */
	private void chargeResp(List listResponsables) throws Exception
	{
		Iterator i = listResponsables.iterator();
		
		while(i.hasNext())
		{
			Element courant = (Element)i.next();
			Promotion promo = getPromotion(courant.getAttributeValue("promotion"));			
			utilisateurs.add(new Responsable(courant.getAttributeValue("id"),courant.getChildText("login"), courant.getChildText("pass"), courant.getChildText("nom"), courant.getChildText("prénom"),"", courant.getChildText("mél"),0, promo));			
		}
	}
	
	/**
	 * Fonction qui charge les enseignants
	 * @param listEnseignants
	 */
	private void chargeEns(List listEnseignants)
	{
		Iterator i = listEnseignants.iterator();
		
		while(i.hasNext())
		{
			Element courant = (Element)i.next();
			
			utilisateurs.add(new Enseignant(courant.getAttributeValue("id"),courant.getChildText("login"), courant.getChildText("pass"), courant.getChildText("nom"), courant.getChildText("prénom"),"", courant.getChildText("mél"),0));			
		}
	}
	
	/**
	 * Fonction qui charge les promotions et leurs etudiants
	 * @param listPromotion
	 */
	private void chargePromotion(List listPromotion)
	{
		Iterator i = listPromotion.iterator();
		
		while(i.hasNext())
		{
			Element courant = (Element)i.next();
			Vector<Etudiant> etudiants = ChargeEtudiants(courant.getChildren("étudiant"));
			promotions.add(new Promotion(courant.getAttributeValue("id"), etudiants));		
		}
	}
	
	/**
	 * Fonction qui charge des etudiants
	 * @param listEtudiants
	 * @return un vector des etudiants cree
	 */
	private Vector<Etudiant> ChargeEtudiants(List listEtudiants)
	{
		Vector<Etudiant> v = new Vector<Etudiant>();
		Iterator i = listEtudiants.iterator();
		
		while(i.hasNext())
		{
			Element courant = (Element)i.next();
			Etudiant et = new Etudiant(courant.getAttributeValue("id"),courant.getChildText("login"), courant.getChildText("pass"), courant.getChildText("nom"), courant.getChildText("prénom"),"", courant.getChildText("mél"),0);
			v.add(et);
			utilisateurs.add(et);
		}
		return v;	
	}
	
	/**
	 * Fonction qui charge les groupes 
	 * @param listGroupes
	 * @throws Exception
	 */
	private void chargeGroupes(List listGroupes) throws Exception
	{
		Iterator i = listGroupes.iterator();
				
		while(i.hasNext())
		{
			boolean ok = false;
			
			Element courant = (Element)i.next();
			
			//Création du groupe et ajout à la liste de groupes
			Groupe gp = new Groupe(courant.getAttributeValue("id"));
			groupes.add(gp);
			
			//Ajout du groupe aux étudiants
			String [] etudiants = (courant.getAttributeValue("étudiants")).split(" ");
			 
			for(int j=0; j<etudiants.length; j++)
			{
				Etudiant etud = getEtudiant(etudiants[j]);
				
				etud.ajouteGroupe(gp);
				
				//On initialise une fois le responsable du groupe
				if(!ok)
				{
					Responsable resp=etud.getPromo().getResp();
					if (resp==null) throw new Exception("responsable inexistant");
					gp.setResponsable(resp);
					ok=true;
				}
			}
			
		}
	}
	/**
	 * Fonction qui charge les matieres
	 * @param listMatieres
	 * @throws Exception
	 */
	private void chargeMatieres(List listMatieres) throws Exception
	{
		Iterator i = listMatieres.iterator();
		
		while(i.hasNext())
		{
			Element courant = (Element)i.next();
		
			//Détermination du type
			int type = Matiere.optionnelle;
			if (courant.getAttributeValue("type")=="obligatoire")
			{
				type = Matiere.obligatoire;
			}
			
			//==Création de la matiere==//
			Matiere mat = new Matiere(courant.getAttributeValue("id"),courant.getChildText("intitulé"),type);
			
			//==Création et ajout des enseignements==//
			Iterator j = courant.getChild("enseignements").getChildren().iterator();
			while(j.hasNext())
			{
				Element courant2 = (Element)j.next();
				
				//Détermination du type d'enseignement
				int type_enseignement;
				if((type_enseignement=Enseignement.getTypeEnseignement(courant2.getName()))==-1)
				{
					throw new Exception("Erreur chargement Enseignement : Type inexistant");
				}
				
				//Détermination du volume horaire
				String vol[]= courant2.getAttributeValue("volume").split(":");
				if (vol.length!=2){throw new Exception("Erreur chargement Enseignement : format volume horaire incorect");}
				//Heure donné en secondes
				Time volume_horaire= new Time((Long.parseLong(vol[0])*60+Long.parseLong(vol[1]))*60);
				
				Enseignement enseign = new Enseignement(type_enseignement, volume_horaire);
				
				//Ajout des groupes et des enseignants à l'enseignement
				String enseignants [] = courant2.getAttributeValue("enseignants").split(" ");				
				String groupes [] = courant2.getAttributeValue("groupes").split(" ");
				if (groupes.length!=enseignants.length)
				{
					throw new Exception("Erreur chargement Matieres : nbGroupe différent de nbEnseignants");
				}
				
				for(int k=0; k<groupes.length; k++)
    			{
					Groupe gp = getGroupe(groupes[k]);
					Enseignant ens = getEnseignant(enseignants[k]);
    				enseign.ajoutGroupeEnseignant(gp, ens);
    			}
				
				//Ajout de l'enseignement à la matiere
				mat.ajoutEnseignement(enseign);
			}
			matieres.add(mat);
		}
	}
	/**
	 * Fonction qui charge les salles
	 * @param listSalles
	 * @throws Exception
	 */
	private void chargeSalles(List listSalles) throws Exception
	{
		Iterator i = listSalles.iterator();
		
		while(i.hasNext())
		{
			Element courant = (Element)i.next();
			int type_salle;
			if((type_salle = Salle.getTypeSalle(courant.getAttributeValue("type")))==-1)
			{
				throw new Exception("chargeSalle : type de salle inexistant");
			}
			
			salles.add(new Salle(courant.getAttributeValue("id"),type_salle, Integer.parseInt(courant.getAttributeValue("taille"))));		
		}
	}
	/**
	 * Fonction qui charge les cours
	 * @param listEdt
	 * @throws Exception
	 */
	private void chargeEdt(List listEdt) throws Exception
	{
		Iterator i = listEdt.iterator();
				
		while(i.hasNext())
		{
			Element courant = (Element)i.next();
			Creneau c = new Creneau(courant.getAttributeValue("date"), courant.getAttributeValue("heure"), courant.getAttributeValue("duree"));
			Salle s = getSalle(courant.getAttributeValue("salle"));
			Groupe gp = getGroupe(courant.getAttributeValue("groupe"));
			Matiere mat = getMatiere(courant.getAttributeValue("matière"));

			cours.add(new Cours(c,s,gp,mat));		
		}
	}

//==============================================================
//  Fonctions de sauvegarde dans un fichier xml
//==============================================================
	public void sauvegarde()
	{
		racine = new Element("bdedt");
		document = new Document(racine);
		
		Element inspecteurs = new Element("inspecteurs");
		racine.addContent(inspecteurs);
		Element enseignants = new Element("enseignants");
		racine.addContent(enseignants);
		Element etudiants = new Element("étudiants");
		racine.addContent(etudiants);
		Element groupes = new Element("groupes");
		racine.addContent(groupes);
		Element matieres = new Element("matières");
		racine.addContent(matieres);
		Element salles = new Element("salles");
		racine.addContent(salles);
		Element edt= new Element("edt");
		racine.addContent(edt);

	}
	
//==============================================================
//  Accesseurs
//==============================================================
	
	/**
	 * Fonction qui retourne une promotion à partir de sont nom
	 * @param name - nom de la promotion
	 * @return la promotion correspondant
	 * @throws Exception si la promotion n'existe pas
	 */
	private Promotion getPromotion(String name) throws Exception
	{
		Promotion promo= null;
		boolean trouve = false;
		
		Iterator i = promotions.iterator();
		while( i.hasNext() && (!trouve))
		{
			promo = (Promotion)i.next();
			if(promo.getNom_promotion().compareTo(name)==0)
			{
				trouve = true;
			}
		}
		if(!trouve) throw new Exception("Promotion inexistante");
		return promo;
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	private Groupe getGroupe(String name) throws Exception
	{
		Groupe gp= null;
		boolean trouve = false;
		
		Iterator i = groupes.iterator();
		while( i.hasNext() && (!trouve))
		{
			gp = (Groupe)i.next();
			if(gp.getnum_groupe().compareTo(name)==0)
			{
				trouve = true;
			}
		}
		if(!trouve) throw new Exception("Groupe inexistant");
		return gp;
	}
	
	private Etudiant getEtudiant(String id) throws Exception
	{
		Etudiant etudiant = null;
		boolean trouve = false;
		int i = 0;
		
		while (i<utilisateurs.size() && (!trouve))
		{
			if(utilisateurs.elementAt(i).getClass()==Etudiant.class)
			{
				etudiant = (Etudiant) utilisateurs.elementAt(i);
				if(etudiant.getNum_personne().compareTo(id)==0)
				{
					trouve = true;
				}
			}
			i++;
		}
		if(!trouve) throw new Exception("Etudiant inexistant");
		return etudiant;
	}
	private Enseignant getEnseignant(String id) throws Exception
	{
		Enseignant enseignant = null;
		boolean trouve = false;
		int i = 0;
		
		while (i<utilisateurs.size() && (!trouve))
		{
			if(utilisateurs.elementAt(i).getClass()==Enseignant.class)
			{
				enseignant = (Enseignant) utilisateurs.elementAt(i);
				if(enseignant.getNum_personne().compareTo(id)==0)
				{
					trouve = true;
				}
			}
			i++;
		}
		if(!trouve) throw new Exception("Enseignant inexistant");
		return enseignant;
	}

	private Salle getSalle(String name) throws Exception
	{
		Salle s= null;
		boolean trouve = false;
		
		Iterator i = salles.iterator();
		while( i.hasNext() && (!trouve))
		{
			s = (Salle)i.next();
			if(s.getNom_salle().compareTo(name)==0)
			{
				trouve = true;
			}
		}
		if(!trouve) throw new Exception("Salle inexistante");
		return s;
	}

	private Matiere getMatiere(String name) throws Exception
	{
		Matiere mat= null;
		boolean trouve = false;
		
		Iterator i = matieres.iterator();
		while( i.hasNext() && (!trouve))
		{
			mat = (Matiere)i.next();
			if(mat.getNum_matiere().compareTo(name)==0)
			{
				trouve = true;
			}
		}
		if(!trouve) throw new Exception("Matiere inexistante");
		return mat;
	}
	
	/**
	 * @return Returns the utilisateurs.
	 */
	public Vector<Personne> getUtilisateurs() {
		return utilisateurs;
	}

//==============================================================
//  Fonctions de chargement et de sauvegarde de la base 
//	dans un fichier (serialization)
//==============================================================
	/**
	 * Sauvegarde la base de donnees sur un fichier de sauvegarde
	 * @throws IOException
	 */
	public void sauveBDD() throws IOException
	{
		if(fichier.exists())fichier.delete();
		fichier.createNewFile();
		FileOutputStream fos = new FileOutputStream(fichier);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(this.utilisateurs);
		oos.writeObject(this.promotions);
		oos.writeObject(this.groupes);
		oos.writeObject(this.matieres);
		oos.writeObject(this.salles);
		oos.writeObject(this.cours);
		oos.close();
	}
	
	/**
	 * Charge la base de donnees a partir d'un fichier de sauvegarde
	 * @throws Exception
	 */
	private void chargeBDD()throws Exception
	{
		FileInputStream fis = new FileInputStream(fichier);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		this.utilisateurs=(Vector<Personne>)ois.readObject();
		this.promotions=(Vector<Promotion>)ois.readObject();
		this.groupes=(Vector<Groupe>)ois.readObject();
		this.matieres=(Vector<Matiere>)ois.readObject();
		this.salles=(Vector<Salle>)ois.readObject();
		this.cours=(Vector<Cours>)ois.readObject();
		
		ois.close();
	}

//===================================================
//   Fonctions de tests
//===================================================
	
	private void afficheObjets(Vector v)
	{
		Iterator i = v.iterator();
		while(i.hasNext())
		{
			System.out.println((i.next()).toString());
		}
	}
	
	public void testAffiche()
	{
		//Vérifications a retirer par la suite
		System.out.println("Fin chargement");
		System.out.println(this.utilisateurs.size()+" utilisateurs");
		System.out.println(this.promotions.size()+" promotions");
		System.out.println(this.groupes.size()+" groupes");
		System.out.println(this.matieres.size()+" matieres");
		System.out.println(this.cours.size()+" cours");
		System.out.println(this.salles.size()+" salles");
		afficheObjets(utilisateurs);
	}
	
	/**
	 * Procedure affiche qui affiche un document xml
	 *
	 */
	private void afficheXML()
	{
		try
		{
			//On utilise ici un affichage classique avec getPrettyFormat()
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, System.out);
		}
		catch (java.io.IOException e){}
	}

	public static void main(String[] args) 
	{
		Gestion_BDD bd = Gestion_BDD.getInstance(false);
		bd.testAffiche();
		try {
			bd.sauveBDD();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
