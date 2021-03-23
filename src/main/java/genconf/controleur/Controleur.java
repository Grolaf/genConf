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
        LocalDate date = LocalDate.parse(infosCommunication[3]);
    	Communication communication = Communication(correspondant, infosCommunication[0], auteurs, infosCommunication[1], infosCommunication[2], date, infosCommunication[4], infosCommunication[5], orateurs, type, conference);
    	boolean r = conference.addCommunication(communication, communication.getNumero());
    	if (r) {
    		ihm.notifier("La communication a correctement été créée");
    	} else {
    		ihm.notifier("La communication n'a pu être créée");
    	}
    	return communication;
    }

    public Utilisateur creerCompteGenConf() {
    	String[3] infosUtilisateur = ihm.saisirUtilisateur();

    	Utilisateur u = genconf.getUtilisateur(infosUtilisateur[0], infosUtilisateur[1], infosUtilisateur[2]);
    	if (u) {
    		ihm.notifier("Le compte existe déjà");
    	} else {
    		u = new Utilisateur(infosUtilisateur[2], infosUtilisateur[0], infosUtilisateur[1]);
    		genconf.addCompteConf(infosUtilisateur[0], infosUtilisateur[1], infosUtilisateur[2]);
    		ihm.notifier("Le compte a été créé");
    	}
    	return u;
    }
    
    public Session creerSession(Conference conference) {
    	HashMap<String, Session> sessions = conference.getSessions();
    	ihm.afficherInfosSessions(sessions);
    	
    	String[7] infosNouvelleSession = ihm.saisirNouvelleSession(conference);
    	Session nouvelleSession = new Session(infosNouvelleSession[0], infosNouvelleSession[1], infosNouvelleSession[2], infosNouvelleSession[3], infosNouvelleSession[4], infosNouvelleSession[5], infosNouvelleSession[6], conference);
    	
    	boolean r = conference.addSession(nouvelleSession);
    	ihm.notifier("La session a correctmeent été crée");
    	return session;
    }

    public void creerTypeCommunication(Conference conference) {
    	HashMap<String, TypeCommunication> typesCommunication = conference.getTypeCommunications();
    	ihm.afficherCaracteristiquesTypesCommunications(typesCommunication);
    	String libelle = ihm.saisirNomTypeCommunication();
    	TypeCommunication type = conference.getTypeCommunication(libelle);
    	if (type) {
    		ihm.notifier("Ce type existe déjà");
    	} else {
    		boolean videoObligatoire, pdfObligatoire;
    		videoObligatoire = pdfObligatoire = false;
    		switch(ihm.saisirInfosTypeCommunication()) {
    		case 1:
    			videoObligatoire = true;
    			break;
    		case 2:
    			pdfObligatoire = true;
    			break;
    		case 3:
    			videoObligatoire = pdfObligatoire = true;
    			break;
    		case 4:
    			break;
    		}
    		TypeCommunication type = new TypeCommunication(libelle, videoObligatoire, pdfObligatoire);
    		if (conference.addTypeCommunication(type)) {
    			ihm.notifier("Le type de communication a été ajouté à la conférence");
    		} else {
    			ihm.notifier("Le type de communication n'a pu être ajouté à la conférence");
    		}
    	}
    }

    public void donnerDroitsAdmin(Conference conference) {
    	HashMap<String, Utilisateur> utilisateurs = genconf.getUtilisateurs();
    	ihm.afficherUtilisateurs(utilisateurs);
    	String[3] infosUtilisateur = ihm.saisirUtilisateur();
    	Utilisateur u = genconf.getUtilisateur(infosUtilisateur[0], infosUtilisateur[1], infosUtilisateur[2]);
    	if (!u) {
    		u = creerCompteGenConf();
    	}
    	boolean r1 = conference.addAdminConf(u, infosUtilisateur[2]);
    	boolean r2 = u.addConferenceAdministree(conference);
    	if (r1 && r2) {
    		ihm.notifier("Le compte a été promu admin conf");
    	} else {
    		ihm.notifier("Le compte n'a pu être promu");
    	}
    }
    

    
    public void modifierSession(Conference conference, Session session) {
    	while (session == NULL) {
    		HashMap<String, Session> sessions = conference.getSessions();
    		String intituleSession = ihm.selectionnerSession(sessions);
    		Session session = conference.getSesession(intituleSession);
    	}
    	int choix = 1;
    	while (choix != 0) { // choix == 0 == quitter
    		ihm.afficherInfosSession(session);
    		choix = ihm.optionsModifierSession();
    		switch (choix) {
    		case 0:
    			break;
    		case 1:
    			modifierAnimateursSession(session);
    			break;
    		case 2:
    			modifierIntituleSession(session, conference);
    			break;
    		case 3:
    			modifierTypeSession(session);
    			break;
    		case 4:
    			modifierDateSession(session);
    			break;
    		case 5:
    			modifierHorairesSession(session);
    			break;
    		case 6:
    			modifierVideoAssociee(session);
    			break;
    		case 7:
    			modifierSalleSession(session);
    			break;
    		case 8:
    			addCommunicationASession(communication, session, conference);
    			break;
    		case 9:
    			removeCommunicationASession(communication, conference, session);
    			break;
    		}
    	}
    }
    
    public void modifierTypeCommunication(Conference conference) {
    	HashMap<String, TypeCommunication> typesCommunication = conference.getTypesCommunications();
    	ihm.afficherCaracteristiquesTypesCommunication(typesCommunication);
    	String libelle = ihm.saisirLibelleTypeCommunication();
    	TypeCommunication e = conference.getTypeCommunication(libelle);
    	if (e == NULL) {
    		ihm.notifier("Ce type n'existe pas");
    	} else {
    		int choix = 1;
    		while (choix != 0) {
    			ihm.afficherCaracteristiquesTypeCommunication(e);
    			choix = ihm.saisirOptionModifierTypeCommunication();
    			switch (choix) {
    			case 1:
    				String nLibelle = ihm.saisirLibelleTypeCommunication();
    				boolean r = e.setLibelle(nLibelle);
    				if (r) {
    					ihm.notifier("Le libellé a correctement été modifié");
    				} else {
    					ihm.notifier("Le libellé n'a pu être modifié");
    				}
    				break;
    			case 2:
    				boolean videoObligatoire = ihm.saisirSiVideoObligatoire();
    				boolean r = e.setVideoObligatoire(videoObligatoire);
    				if (r) {
    					ihm.notifier("L'attribut video obligatoire a correctement été modifié");
    				} else {
    					ihm.notifier("L'attribut video obligatoire n'a pu être modifié");
    				}
    				break;
    			case 3:
    				boolean pdfObligatoire = ihm.saisirSiPDFbligatoire();
    				boolean r = e.setPDFObligatoire(pdfObligatoire);
    				if (r) {
    					ihm.notifier("L'attribut PDF obligatoire a correctement été modifié");
    				} else {
    					ihm.notifier("L'attribut PDF obligatoire n'a pu être modifié");
    				}
    				break;
    			case 4:
    				break;
    			}
    		}
    	}
    }
    
    public void previsualiserCommunication(Communication communication, Conference conference) {
    	ihm.afficherInfosCommunication(communication);
    	boolean modifier = ihm.demanderSiModifierCommunication();
    	if (modifier) {
    		modifierCommunication(conference, communication);
    	}
    }
    
    private void modifierAnimateursSession(Session session) {
    	int option = 1;
    	while (option != 0) {
    		HashMap<String, Utilisateur> animateurs = session.getAnimateurs();
    		option = ihm.saisirSupprimerOuAjouterUtilisateur(animateurs);
    		switch (option) {
    		case 0:
    			break;
    		case 1:	// ajouter
    			String[3] infosUtilisateur = ihm.saisirUtilisateur();
    			Utilisateur utilisateur = genconf.getUtilisateur(infosUtilisateur[0], infosUtilisateur[1], infosUtilisateur[2]);
    			if (utilisateur == NULL) {
    				utilisateur = creerCompteGenConf();
    			}
    			
    			boolean r = session.addAnimateur(utilisateur);
    			if (r) {
    				ihm.notifier("L'animateur a correctement été ajouté");
    			} else {
    				ihm.notifier("L'animateur n'a pu être ajouté");
    			}
    			break;
    		case 2:	// supprimer
    			String[3] infosUtilisateur = ihm.saisirUtilisateur();
    			Utilisateur utilisateur = genconf.getUtilisateur(infosUtilisateur[0], infosUtilisateur[1], infosUtilisateur[2]);
    			
    			boolean r = false;
    			if (utilisateur) {
    				r = session.removeAnimateur(utilisateur);
    			}
    			if (r) {
    				ihm.notifier("L'animateur a correctement été supprimé");
    			} else {
    				ihm.notifier("L'animateur n'a pu être supprimé");
    			}
    			break;
    		}
    	}
    }

    private void modifierIntituleSession(Session session, Conference conference) {
    	Session existe;
    	String nouvelIntitule;
    	do {
	    	nouvelIntitule = ihm.demanderNomSession();
	    	existe = conference.getSession(nouvelIntitule);
	    	if (existe) {
	    		ihm.notifier("La session existe déjà");
	    	}
    	} while (existe);
    	boolean r = session.setIntituleSession(nouvelIntitule);
    	if (r) {
    		ihm.notifier("L'intitulé a correctement été modifié");
    	} else {
    		ihm.notifier("L'intitulé n'a pu être changé");
    	}
    }

    private void modifierTypeSession(Session session) {
    	String nouveauType = ihm.saisirNouveauTypeSession();
    	boolean r = session.setType(nouveauType);
    	if (r) {
    		ihm.notifier("Le type a correctement été modifié");
    	} else {
    		ihm.notifier("Le type n'a pu être modifié");
    	}
    }
    
    private void modifierDateSession(Session session) {
    	String nouvelleDate = ihm.saisirDate();
    	int r = session.setDate(nouvelleDate);
    	if (r == 0) {
    		ihm.notifier("La date a correctement été modifiée");
    	} else {
    		ihm.notifier("La date n'a pu être modifiée");
    	}
    }

    private void modifierHorairesSession(Session session) {
    	ihm.notifier("Entrez la nouvelle heure de début");
    	String nouvelleHeureDebut = ihm.saisirHeure();
    	
    	ihm.notifier("Entrez la nouvelle heure de fin");
    	String nouvelleHeureFin = ihm.saisirHeure();
    	
    	int r = session.setHoraires(nouvelleHeureDebut, nouvelleHeureFin);
    	if (r == 0) {
    		ihm.notifier("L'horaire a été changé");
    	} else {
    		ihm.notifier("L'horaire n'a pu être changé");
    	}
    }

    private void modifierVideoAssociee(Session session) {
    	String nouveauLien = ihm.saisirLienVideo();
    	boolean r = session.setLienVideo(nouveauLien);
    	if (r) {
    		ihm.notifier("Lien vidéo changé");
    	} else {
    		ihm.notifier("Le lien vidéo n'a pu être changé");
    	}
    }

    private void modifierSalleSession(Session session) {
    	String nouvelleSalle = ihm.saisirSalle();
    	boolean r = session.setSalle(nouvelleSalle);
    	if (r) {
    		ihm.notifier("La salle a correctement été modifiée");
    	} else {
    		ihm.notifier("La salle n'a pu être changée");
    	}
    }


}
