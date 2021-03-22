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
    
    public Communication creerCommunication(Conference conference) {
    	HashMap<int, Communication> communications = conference.getCommunications();
    	ihm.afficherCommunications(communications);
    	
    	String libelle = ihm.saisirNomTypeCommunication();
    	TypeCommuincation type = conference.getTypeCommunication(libelle);
    	
    	String[6] infosCommunication = ihm.saisirInfosCommunication(type);
    	
    	ihm.notifier("Veuillez saisir le correspondant de la communication");
    	String[3] infosCorrespondant = ihm.saisirUtilisateur();
    	Utilisateur correspondant = genconf.getUtilisateur(infosCorrespondant[0], infosCorrespondant[1], infosCorrespondant[2]);
    	if (correspondant == NULL) {
    		correspondant = creerCompteGenConf();
    	}
    	
    	ihm.notifier("Veuillez saisir les auteurs de la communication");
    	Set<Set<String>> auteurs = ihm.saisirAuteursCommunication();
    	
    	HashMap<String, Utilisateur> orateurs;
    	Iterator<Set<String>> it = auteurs.iterator;
    	while (it.hasNext()) {
    		Set<String> auteur = it.next();
    		
    		String[3] infosAuteur = auteur.toArray();
    		Utilisateur auteurU = getUtilisateur(infosAuteur[0], infosAuteur[1], infosAuteur[2]);
    		
    		if (auteurU) {
    			orateurs.put(infosAuteur[2], auteurU);
    		}
    	}
    	
    	Communication communication = Communication(correspondant, infosCommunication[0], auteurs, infosCommunication[1], infosCommunication[2], infosCommunication[3], infosCommunication[4], infosCommunication[5], orateurs, type, conference);
    	boolean r = conference.addCommunication(communication, communication.getNumero());
    	if (r) {
    		ihm.notifier("La communication a correctement été créée");
    	} else {
    		ihm.notifier("La communication n'a pu être créée");
    	}
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
