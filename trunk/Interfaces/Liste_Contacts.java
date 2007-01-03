package Interfaces;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Systeme.SimpleMailSender;

public class Liste_Contacts {


	private static JTextField Sujet = new JTextField(35);
	private static JTextArea Message= new JTextArea("",20,37);

	/**
     * Centre la fenetre au milieu de l'ecran
     * @param frame - la fenetre
     */
	private static void centerFrame(JFrame frame) {
	   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	   Dimension frameSize = frame.getSize();
	   frame.setLocation((screenSize.width / 2) - (frameSize.width / 2), (screenSize.height / 2) - (frameSize.height / 2));
	}
	
	/**
	 * 
	 * @param liste_emails_nom - liste de tous les emails auxquels la personne peut envoyer
	 */
	public void Afficher_Liste_Contacts(String[] liste_emails_nom) {
	//public static void main(String[] args) {
		//String[] liste_emails_nom= {"Danny","Exposito","Seth","Dilhac","Acco","Alex","Tonya","Marc","Matthieu","Cubero","Castan","Conchon","Vache","Roccacher","Danny","Exposito","Seth","Dilhac","Acco","Alex","Tonya","Marc","Matthieu","tes1t","test2","test3","Vache","Roccacher"};
		final JFrame fenetre = new JFrame();
		fenetre.setTitle("Envoyer un message");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setSize(500,500);
		centerFrame(fenetre);
		
		
		
		/* Choix du nom NORD */
		JLabel LNom = new JLabel("Choix du destinataire");
		JComboBox ListeContacts = new JComboBox(liste_emails_nom);
		JPanel pcontacts = new JPanel();
		
		/* Bouton SUD*/
		JButton Valider = new JButton("Valider");
		JButton Annuler = new JButton("Annuler");
		JPanel buttonspane = new JPanel();
		
		/* Message et Sujet CENTER */
		JLabel LSujet = new JLabel("Sujet: ");
		JLabel LMessage = new JLabel("Message: ");
		
		JPanel messagepane = new JPanel();
		JPanel messagenord = new JPanel();
		JPanel messagesud = new JPanel();

				
		JMenuBar menu = new JMenuBar();
		JMenu mfichier = new JMenu("Fichier");
		JMenuItem quitter = new JMenuItem("Quitter");
		
		/* Action fermer*/
		ActionListener fermer = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int r = JOptionPane.showConfirmDialog(null,"Veux tu vraiment quitter?","Fermeture",JOptionPane.YES_NO_OPTION);
				if (r == JOptionPane.YES_OPTION){
					System.exit(1);
				}
				
			}
		};
		quitter.addActionListener(fermer);
		
		
		/* Action valider - envoyer mail */
		ActionListener envoyer = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				fenetre.setVisible(false);
				/*try {
					this.finalize();
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				/* On envoit un signal au serveur qui envoi ensuite le mail */
				//TODO
				SimpleMailSender NouveauMail = new SimpleMailSender();
				NouveauMail.envoimail("alex.remen@gmail.com","sadako22@free.fr",Sujet.getText(), Message.getText());
			}
		};
		Valider.addActionListener(envoyer);
		
		/* Action annuler */
		ActionListener annuler = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				fenetre.setVisible(false);
				/*try {
					super.finalize();
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				
				
			}
		};
		Annuler.addActionListener(annuler);
		
		mfichier.add(quitter);
		menu.add(mfichier);
		
		
		
		fenetre.getContentPane().setLayout(new BorderLayout());
		
		
		/*====================INSERTIONS DES ELEMENTS===================*/
				
		/* Labels et liste de contacts nord de la fenetre */
		
		pcontacts.add(LNom,BorderLayout.CENTER);		
		pcontacts.add(ListeContacts, BorderLayout.EAST);
		
		/* Le label sujet et textbox sujet au nord dans le centre de la fenetre */
		//messagenord.setLayout(new BorderLayout());
		messagenord.add(LSujet,BorderLayout.CENTER);
		messagenord.add(Sujet,BorderLayout.EAST);
		
		/* Les textbox sujet et message est sur la fenetre */
		//messagesud.setLayout(new BorderLayout());
		/*Message.setEditable(true);
		Message.setEnabled(true);*/
		Message.setLineWrap(true);
		messagesud.add(LMessage,BorderLayout.CENTER);
		messagesud.add(Message,BorderLayout.EAST);
		
		/* Le panel regroupant les 4 elements au centre de la fenetre */
		messagepane.setLayout(new BorderLayout());
		messagepane.add(messagenord,BorderLayout.NORTH);
		messagepane.add(messagesud,BorderLayout.SOUTH);
		
		
		/* Le panel regroupant les bouttons au sud de la fenetre */
		buttonspane.add(Annuler,BorderLayout.WEST);
		buttonspane.add(Valider,BorderLayout.EAST);

		/* Le panel principal */
		fenetre.getContentPane().add(pcontacts, BorderLayout.NORTH);
		fenetre.getContentPane().add(messagepane, BorderLayout.CENTER);
		fenetre.getContentPane().add(buttonspane,BorderLayout.SOUTH);
		fenetre.setJMenuBar(menu);
		
		//fenetre.pack();
		fenetre.setVisible(true);


	}
	
}
