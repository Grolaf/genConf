package genconf.controleur;

import genconf.*;
import genconf.controleur.*;
import genconf.modele.*;
import genconf.util.*;
import genconf.vue.*;


public class Controleur {

    private GenConf genconf;
    private IHM ihm;

    public Controleur(GenConf genconf) {
        this.genconf = genconf;

        // choisir la classe CLI ou GUI
//        this.ihm = new CLI(this);
        this.ihm = new IHM(this);
    }

    public void addCommunicationASession(Communication communication, Session session, Conference conference) {
        Session sessionRattachee = communication.getSession();
        boolean choix = false;
        if (sessionRattachee) {
        	choix = ihm.demanderARemplacerSessionDeCommunication();
        }
        boolean r = false;
        if (choix == true) {
        	r = communication.setSession(session);
        }
        if (r) {
        	ihm.notifier("La communication a correctement été ajoutée à la session");
        }
    }
    
    public void consulterMetadonneesConference(Conference conference) {
        ihm.afficherInformationsConf(conference);
    }
    
    public void creerConference() {
        
    }
    
    public Communication creerCommunication(Conference conference) {
    	Map<String, Communication> communications = genConf.getCommunications(conference.getNomConf());
    	ihm.afficherCommunications(communications);
    	
    	String titre = ihm.getTitreCommunication();
    	Iterator<String> it = communications.keySet().iterator();
    	while(it.hasNext()) {
    		Communication c = communications.get(it.next());
    		if (c.getTitre() == titre) {
    			ihm.notifier("Cette communication existe déjà");
    			return NULL;
    		}
    	}
    	
    	String nom = ihm.saisirNomUtilisateur();
    	String prenom = ihm.saisirPrenomUtilisateur();
    	String mail = ihm.saisirMailUtilisateur();
    	
    	Utilisateur u = conference.getUtilisateur(mail, nom, prenom);
    	if(!u) {
    		u = new Utilisateur(mail, prenom, nom);
    	}
    	
    	Communication communication = new Communication(u);
    	communication.setTypeCommunication(conference.getTypeCommunication(ihm.getNomTypeCommunication()));
    	communication.setTitre(ihm.getTitreCommunication());
    	communication.addAuteur(ihm.getAuteursCommunication());
    	communication.setLienPDF(ihm.saisirLienPDF());
    	communication.setLienVideo(ihm.saisirLienVideo());
    	communication.setDate(ihm.saisirDate());
    	communication.setHoraires(ihm.saisirHeure(), ihm.saisirHeure());
    	
    	
    	int IDcomm = this.genererIDcommunication();
    	conference.addCommunication(communication, IDcomm);

    }

    public Utilisateur creerCompteGenConf() {
    	String nom = ihm.saisirNomUtilisateur();
    	String prenom = ihm.saisirPrenomUtilisateur();
    	String mail = ihm.saisirMailUtilisateur();

    	Utilisateur u = genconf.getUtilisateur(nom, prenom, mail);
    	if (u) {
    		ihm.notifier("Le compte existe déjà");
    	} else {
    		u = new Utilisateur(mail, nom, prenom);
    		genconf.addCompteConf(nom, prenom, mail);
    		ihm.notifier("Le compte a été créé");
    	}
    }
    
    
}
