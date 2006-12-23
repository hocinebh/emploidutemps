package systeme;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

/**
 * 
 * @author Alexander Remen et Tonya vo Thanh
 * Classe simple qui envoit un mail.
 * Elle doit etre utilisé par le serveur sur l'INSA pour que le serveur SMTP soit reconnu.
 */

public class SimpleMailSender {

	static boolean envoiok = true;
	private String smtpServer = "smtp.free.fr";
	// privqte String smtpServer = "melinite.insa-toulouse.fr"
	/**
	 * Methode qui envoie un mail. Il faut changer le SMTP dans la source pour qu'il marche sur le serveur en compte! 
	 * @param to : destinataire
	 * @param from : mel du expediteur 
	 * @param subject : sujet du mail
	 * @param body : contenu du mail
	 * @return Mail_envoye : true si l'envoi est reussi, false si echoue
	 */
	public static boolean envoimail(String to, String from, String subject, String body){
		try 
		{
			send("smtp.free.fr",to,from,subject,body);
		}
		catch (Exception ex)
		{
			envoiok = false;
		}
		return envoiok;
	}
	
	
	
}
