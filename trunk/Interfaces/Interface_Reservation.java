package Interfaces;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.*;

import com.toedter.calendar.*;


public class Interface_Reservation {

	private GregorianCalendar DateHeureDebut;
	private static JFrame fenetre = new JFrame();
	static final SimpleDateFormat formatjour =  new SimpleDateFormat("dd/MM/yyyy",new Locale("fr","FR"));
	/**
     * Centre la fenetre au milieu de l'ecran
     * @param frame - la fenetre
     */
	private static void LocationFrame(JFrame frame) {
	   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	   Dimension frameSize = frame.getSize();
	   frame.setLocation((screenSize.width / 2) + (420), (screenSize.height / 2) - 300);
	}
	
	public void Affiche_Interface_Reservation(){
		
		fenetre.setTitle("Reservation");
		//fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setSize(210,600);
		LocationFrame(fenetre);
		
		JLabel LReservation = new JLabel("Reservation");
		LReservation.setFont(new Font("Verdana",Font.BOLD,15));
		fenetre.getContentPane().setLayout(new BorderLayout());
		fenetre.getContentPane().add(LReservation,BorderLayout.NORTH);
		
		JPanel panelcenter = new JPanel();
		panelcenter.setLayout(new GridLayout(14,1));
		/* Date */
		JLabel Ldate = new JLabel("Date :");
		panelcenter.add(Ldate);
		final JDateChooser ChoixDate = new JDateChooser();
		ChoixDate.setDateFormatString("d.MMMMM.YYYY");
		ChoixDate.setLocale(new Locale("fr","FR"));
		panelcenter.add(ChoixDate);
		/* Heure debut */
		JLabel Lheuredeb = new JLabel("Heure debut :");
		panelcenter.add(Lheuredeb);
		final JTextField Heuredeb = new JTextField("08:00");
		panelcenter.add(Heuredeb);
		/* Duree */
		JLabel Lduree = new JLabel("Duree :");
		panelcenter.add(Lduree);
		String[] duree = {"00:30","00:45","01:00","01:15","01:30","01:45","02:00","02:15","02:30","02:45","03:00","03:30","04:00","05:00","06:00"};
		JComboBox Duree = new JComboBox(duree);
		Duree.setSelectedItem("01:15");
		panelcenter.add(Duree);
		/* Groupe */
		JLabel LGroupe = new JLabel("Groupe");
		panelcenter.add(LGroupe);
		JComboBox Groupe = new JComboBox();
		panelcenter.add(Groupe);
		/* Enseignant */
		JLabel LEnseignant = new JLabel("Enseignant");
		panelcenter.add(LEnseignant);
		JComboBox Enseignant = new JComboBox();
		panelcenter.add(Enseignant);
		/* Matiere */
		JLabel LMatiere = new JLabel("Matiere");
		panelcenter.add(LMatiere);
		JTextField Matiere = new JTextField();
		panelcenter.add(Matiere);
		/* Salle */
		JLabel LSalle = new JLabel("Salle");
		LSalle.setHorizontalTextPosition(JLabel.CENTER);
		panelcenter.add(LSalle);
		JComboBox Salle = new JComboBox();
		panelcenter.add(Salle);
		fenetre.getContentPane().add(panelcenter,BorderLayout.CENTER);
		/* Bouttons valider et effacer */
		JPanel ButtonPanel = new JPanel();
		JButton Reset = new JButton("Reset");
		JButton Valider = new JButton("Valider");
		ButtonPanel.add(Reset);
		ButtonPanel.add(Valider);
		fenetre.getContentPane().add(ButtonPanel,BorderLayout.SOUTH);
		
		/* Action Valider */
		ActionListener valider = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				/**
				 * TODO
				 **/				
			}
		};
		Valider.addActionListener(valider);
		
		/* Action Reset */
		ActionListener reset = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				/**
				 * TODO
				 **/			
			}
		};
		Reset.addActionListener(reset);
		
		fenetre.setVisible(true);
	}
}
