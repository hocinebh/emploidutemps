package be4gi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Hashtable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public final class TestEdtMultiSession {
	private FileWriter trace;
	private String nomFichierTrace;
	private String nomClasseEdt;
	private be4gi.Edt edtEnTest;
	private Hashtable<String, Session> lstSessions = new Hashtable<String, Session>();
	Document document;
	Element testCourant;
	
	public  TestEdtMultiSession(InputStream inXml) 
	throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		DocumentBuilder builder = factory.newDocumentBuilder();
		document = builder.parse(new InputSource(inXml));
		document.normalizeDocument();
	}
	
	public void initialiserBase(Element elTest) {
		trWrite("<initialiserBase>");
		ByteArrayInputStream bais = null;
		Element bd = recherchePremierFilsElément(elTest);
		DOMSource domBd = new DOMSource(bd);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		try {
			TransformerFactory tFactory =
				TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			StreamResult sr = new StreamResult(baos);
			transformer.transform(domBd, sr);
			bais = new ByteArrayInputStream(baos.toByteArray());
			boolean res = edtEnTest.initialiserBase(bais);
			trWrite(""+res);
		} catch (Exception e) {
			trWrite("exception "+e);
		}
		trWriteln("</initialiserBase>");
	}

	public void sauvegarderBase(Element elTest) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		trWrite("<sauvegarderBase>");
		boolean res = edtEnTest.sauvegarderBase(baos);
		if (res) trWrite(baos.toString());
		else trWrite("false");
		trWriteln("</sauvegarderBase>");
	}

	public void créerSession(Element elTest) {
		String idSession= elTest.getAttribute("idSession");
		String login = elTest.getAttribute("login");
		String pass = elTest.getAttribute("pass");
		trWrite("<créerSession idSession='"+idSession+"' login='"+login+"' pass='"+pass+"'>");
		Session session = edtEnTest.créerSession(login, pass);
		if (session!=null) {
			lstSessions.put(idSession, session);
			trWrite("true");
		} else trWrite("false");
		trWriteln("</créerSession>");
	}

	public void estOuverte(Element elTest) {
		String idSession= elTest.getAttribute("idSession");
		Session session = lstSessions.get(idSession);
		trWrite("<estOuverte idSession='"+idSession+"'>");
		trWrite(""+session.estOuverte());
		trWriteln("</estOuverte>");
	}

	public void getEDT(Element elTest) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String idSession= elTest.getAttribute("idSession");
		Session session = lstSessions.get(idSession);
		String promo=elTest.getAttribute("promo");
		trWrite("<getEdt idSession='"+idSession+"'");
		try {
			if (promo.equals("")) {
				trWrite(">");
				session.getEDT(baos);
				trWrite(baos.toString());
			} else {
				trWrite(" promo='"+promo+"'>");
				session.getEDT(baos, promo);
				trWrite(baos.toString());
			}
		} catch (Exception e) {
			trWrite("exception "+e);
		}
		trWriteln("</getEdt>");
	}

	public void getRéservation(Element elTest) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String idSession= elTest.getAttribute("idSession");
		Session session = lstSessions.get(idSession);
		String salle=elTest.getAttribute("salle");
		trWrite("<getRéservation idSession='"+idSession+"' salle='"+salle+"'>");
		try {
			session.getRéservation(baos, salle);
			trWrite(baos.toString());
		} catch (Exception e) {
			trWrite("exception "+e);
		}
		trWriteln("</getRéservation>");		
	}

	public void setRéservation(Element elTest) {
		String idSession= elTest.getAttribute("idSession");
		Session session = lstSessions.get(idSession);
		String date = elTest.getAttribute("date");
		String heure = elTest.getAttribute("heure");
		String durée = elTest.getAttribute("durée");
		String salle = elTest.getAttribute("salle"); 
 	   	String groupe = elTest.getAttribute("groupe");
 	   	String matière = elTest.getAttribute("matière");
		trWrite("<setRéservation idSession='"+idSession+"' date='"+date
				+"' heure='"+heure+"' durée='"+durée+"' salle='"+salle
				+"' groupe='"+groupe+"' matière='"+matière+"'>");
		try {
			session.setRéservation(date, heure, durée, salle, groupe, matière);
		} catch (Exception e) {
			trWrite("exception "+e);
		}
		trWriteln("</setRéservation>");	
	}

	public void getEmail(Element elTest) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String idSession= elTest.getAttribute("idSession");
		Session session = lstSessions.get(idSession);
		trWrite("<getEmail idSession='"+idSession+"'>");
		try {
			session.getEmail(baos);
			trWrite(baos.toString());
		} catch (Exception e) {
			trWrite("exception "+e);
		}
		trWriteln("</getEmail>");		
	}

	public void fermerSession(Element elTest) {
		String idSession= elTest.getAttribute("idSession");
		Session session = lstSessions.get(idSession);
		trWrite("<fermerSession idSession='"+idSession+"'>");
		try {
			session.fermer();
		} catch (Exception e) {
			trWrite("exception "+e);
		}
		trWriteln("</fermerSession>");	
	}
	
	private Element rechercheElémentSuivant(Node frère) {
		if (frère==null) return null;
		Node noeud = frère;
		while (true) {
			noeud = noeud.getNextSibling();
			if (noeud==null) return null;
			if (noeud.getNodeType()==Node.ELEMENT_NODE) return (Element) noeud;
		}
	}
	
	private Element recherchePremierFilsElément(Element père) {
		Node noeud = père.getFirstChild();
		if (noeud==null) return null;
		if (noeud.getNodeType()!=Node.ELEMENT_NODE) return rechercheElémentSuivant(noeud);
		else return (Element) noeud;
	}
	
	public void exécution(String nomGroupe, String nomClasseImplEdt) {
		this.nomFichierTrace = nomGroupe+".tr";
		this.nomClasseEdt = nomClasseImplEdt;
		try {
			trace = new FileWriter(nomFichierTrace);
		} catch (IOException e1) {
			erreurFatale("le fichier de "+nomFichierTrace+" n'a pu être créé !");
		}
		Class classeEdt;
		//écriture en-tête trace
		trWriteln("<?xml version='1.0' encoding='"+Charset.defaultCharset()+"'?>");
		trWriteln("<traceSéquenceTest>");
		try {
			classeEdt = Class.forName(nomClasseEdt);
			edtEnTest = (be4gi.Edt) classeEdt.newInstance();
			Element racine = document.getDocumentElement();
			testCourant = recherchePremierFilsElément(racine);
			while (testCourant!=null) {
				// traitement d'un test
				Element opération = recherchePremierFilsElément(testCourant);
				while (opération!=null) {
					String nom = opération.getNodeName();
					try {
						Class[] paramFormels = {Element.class};
						java.lang.reflect.Method méthode = 
							this.getClass().getMethod(nom, paramFormels);
						Object[] paramEffectifs = {opération};
						//System.out.println("appel méthode : "+nom);
						méthode.invoke(this, paramEffectifs);
					} catch (Exception e) {
						System.out.println("Erreur : "+e);
					}
					opération = rechercheElémentSuivant(opération);
				}
				testCourant = rechercheElémentSuivant(testCourant);
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Classe non trouvée "+e);
		} catch (InstantiationException e) {
			System.out.println("Problème d'instanciation de la classe "+e);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException "+e);
		} finally {
			try {
				trWriteln("</traceSéquenceTest>");
				trace.close();
			} catch (IOException e) {
				System.out.println("problème lors de la fermeture du fichier de trace !");
			}
		}
		System.out.println("Test terminé.");
		System.exit(0);
	}
	
	/**
	 * Méthode de lancement d'un test. Le nom du groupe d'étudiants (fait à partir de 
	 * la concaténation des noms de chaque étudiants) et Le nom de la classe principale 
	 * de l'objet emploi du temps, réalisant l'interface be4gi.Edt doivent être passés
	 *  au programme de test par la ligne de commande.
	 *  Par exemple, pour tester la classe exemple.EdtImplementation du groupe XxxxYyyy, 
	 *  nous aurons la ligne de commande suivante (on suppose que le classpath est correct:
	 *    java test.TestEdt XxxxYyyy exemple.EdtImplementation fichierDeTest.xml
	 *  
	 * @param args le nom du groupe et le nom de la classe réalisant l'interface be4gi.Edt
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		try {
			System.out.println("Lancement du test SeqTest.xml");
			File fichTst1 = new File(args[2]);
			InputStream is = new FileInputStream(fichTst1);
			TestEdtMultiSession tst = new TestEdtMultiSession(is);
			tst.exécution(args[0], args[1]);
		} catch (Exception e) {
			erreurFatale("Exception : "+e);
			e.printStackTrace();
		}	
	}
	
	private static void erreurFatale(String str) {
		System.out.print("ERREUR FATALE : ");
		System.out.println(str);
		System.exit(0);
	}
	
	private void trWrite(String str) {
		try {
			trace.write(str);
		} catch (IOException e) {
			erreurFatale("Erreur d'entrée/sortie sur le fichier "+nomFichierTrace+" !");
		}
	}
	
	private void trWriteln(String str) {
		trWrite(str);
		trWrite(System.getProperty("line.separator"));
	}	

}
