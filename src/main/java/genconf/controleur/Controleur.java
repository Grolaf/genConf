package genconf.controleur;

import java.util.Iterator;
import genconf.modele.*;
import genconf.util.Persisteur;
import genconf.vue.IHM;
import java.time.LocalDate;
import java.util.HashMap;


public class Controleur {

    private GenConf genconf;
    private IHM ihm;

    public Controleur(GenConf genconf) {
        this.genconf = genconf;
        this.ihm = new IHM(this);
    }


    public void addCommunicationASession(Communication communication, Session session, Conference conference) {
    	while (communication == null) {
    		HashMap<String, Communication> communications = session.getCommunications();
    		int numero = ihm.saisirNumeroCommunication(communications);
    		Communication communication = session.getCommunication(numero);
    	}
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
        } else {
        	ihm.notifier("La communication n'a pu être ajoutée à la session");
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
    	if (correspondant == null) {
    		correspondant = creerCompteGenConf();
    	}
    	
    	ihm.notifier("Veuillez saisir les auteurs de la communication");
    	Set<String[3]> auteurs = ihm.saisirAuteursCommunication();
    	
    	HashMap<String, Utilisateur> orateurs;
    	Iterator<String[3]> it = auteurs.iterator;
    	while (it.hasNext()) {
    		String[3] auteur = it.next();
    		Utilisateur auteurU = getUtilisateur(infosAuteur[0], infosAuteur[1], infosAuteur[2]);
    		
    		if (auteurU) {
    			orateurs.put(auteur[2], auteurU);
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
    
    /**************************************************************************************/
    /**************************************************************************************/
    /**************************************************************************************/
    
    public void demarrerApplication()
    {
        Persisteur.lireEtat();
        boolean choix;
        int choixSwitch;
        Commande cmd = Commande.PREVISUALISER_CONFERENCE;
        Conference conferenceSelectionnee;
        
        HashMap<String, Conference> conferences = this.genconf.getConferences();
        choix = this.ihm.saisirCreationConference(conferences);
        
        if(choix) // on souhaite créer une conf
        {
            conferenceSelectionnee = creerConference();
        }
        else
        {
            conferenceSelectionnee = selectionnerConference();
        }
      
        
        while(cmd != Commande.QUITTER)
        {
            cmd = Commande.valueofCode(this.ihm.afficherMenuGeneral());
            
            switch(cmd)
            {
                case GERER_SESSIONS :
                    choixSwitch = this.ihm.afficherMenuSessions();
                    
                    switch(choixSwitch)
                    {
                        case 1:
                            creerSession(conferenceSelectionnee);
                            break;
                        case 2:
                            modifierSessiono(conferenceSelectionnee, null);
                            break;
                        case 3:
                            removeSession(conferenceSelectionnee);
                            break;
                        default :
                            this.ihm.notifier("Mauvaise selection");
                            break;
                    }
                    
                    break; 
                
                case GERER_COMMUNICATIONS:
                    choixSwitch = this.ihm.afficherMenuCommunications();
                    
                    switch(choixSwitch)
                    {
                        case 1:
                            creerCommunication(conferenceSelectionee);
                            break;
                        case 2:
                            modifierCommunication(conferenceSelectionnee, null);
                            break;
                        case 3:
                            removeCommunication(conferenceSelectionnee);
                            break;
                        default :
                            this.ihm.notifier("Mauvaise selection");
                            break;
                    }
                    
                    break;
                
                case GERER_TYPES_COMMUNCATIONS:
                    choixSwitch = this.ihm.afficherMenuTypesCommunication();
                    
                    switch(choixSwitch)
                    {
                        case 1:
                            creerTypeCommunication(conferenceSelectionnee);
                            break;
                        case 2: 
                            modifierTypeCommunication(conferenceSelectionnee);
                            break;
                        case 3:
                            removeTypeCommunication(conferenceSelectionnee);
                            break;
                        default :
                            this.ihm.notifier("Mauvaise selection");
                            break;    
                    }
                    break;
                    
                case GERER_INFOS_CONFERENCE:
                    choixSwitch = this.ihm.afficherMenuInfosConference();
                    
                    switch(choixSwitch)
                    {
                        case 1:
                            modifierMetadonneesConference(conferenceSelectionnee);
                            break;
                        case 2:
                            consulterInfosConf(conferenceSelectionnee);
                            break;
                        default :
                            this.ihm.notifier("Mauvaise selection");
                            break;        
                    }
                    break;
                    
                case GERER_TRACKS:
                    choixSwitch = this.ihm.afficherMenuTracks();
                    
                    switch(choixSwitch)
                    {
                        case 1:
                            creerTrack(conferenceSelectionnee);
                            break;
                        case 2:
                            removeTrack(conferenceSelectionnee);
                            break;
                        default :
                            this.ihm.notifier("Mauvaise selection");
                            break;
                    }
                    
                    break;
                    
                case GERER_COMPTES:
                    choixSwitch = this.ihm.afficherMenuComptes();
                    
                    switch(choixSwitch)
                    {
                        case 1:
                            creerCompteGenConf(conferenceSelectionnee);
                            break;
                        case 2:
                            donnerDroitsAdminConf(conferenceSelectionnee);
                            break;
                        case 3:
                            donnerDroitsInscrits(conferenceSelectionnee);
                            break;
                        default :
                            this.ihm.notifier("Mauvaise selection");
                            break; 
                    }
                    break;
                
                case PREVISUALISER_CONFERENCE:
                    previsualiserConference(conferenceSelectionnee);
                    break;
                 
                default :
                    this.ihm.notifier("Mauvaise selection");
                    break;
            }
        }
        
        this.ihm.notifier("Au revoir et à bientot ! ");
        
        Persisteur.sauverEtat(genconf);        
    }
    
    public Conference selectionnerConference()
    {
        HashMap<String, Conference> conferences;
        Conference conf;
        String nom;
        
        conferences = this.genconf.getConferences();
        nom = this.ihm.saisirNomConference(conferences);
        
        conf = this.genconf.getConference(nom);
        
        if(conf == null)
        {
            this.ihm.notifier("Cette conférence n'existe pas");
            return null;
        }
        return conf;

    }
    
    /**************************************************************************************/
    /**************************************************************************************/
    /**************************************************************************************/
    


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
    	if (r) {
    		ihm.notifier("La session a correctement été crée");
    	} else {
    		ihm.notifier("La session n'a pu être crée");
    	}
    	
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

    public Conference creerConference()
    {
        String nomNouvelleConf;
        LocalDate dateDebut, dateFin;
        Conference nouvelleConference;
        
        HashMap<String, Conference> conferences = this.genconf.getConferences();
        
        nomNouvelleConf = this.ihm.saisirNomConference(conferences);

        this.ihm.notifier("Veuillez entrer la date de début");
        
        dateDebut = this.ihm.saisirDate();
        
        this.ihm.notifier("Veuillez entrer la date de fin");
        
        dateFin = this.ihm.saisirDate();
        
        if(!this.genconf.getConference(nomNouvelleConf))
        {
            nouvelleConference = new Conference(nomNouvelleConf, dateDebut, dateFin);
        }
        else
        {
            this.ihm.notifier("Cette conférence existe déjà !");
        }
        
        if(nouvelleConference != null && this.genconf.addConference(nouvelleConference))
        {
            this.ihm.notifier("La conference a correctement été créée");
        }
        else
        {
            this.ihm.notifier("La conférence n'a pas pu être ajoutée a genConf ");
        }
        
        return nouvelleConference;
    }
    
    /**************************************************************************************/
    
    public void saisirMetadonneesConference(Conference conference)
    {
        int choix = 1;
        
        while(choix != 0)
        {
            consulterInfosConf(conference);
            
            choix = this.ihm.optionsModifierConference();
            
            switch(choix)
            {
                case 1: 
                    modifierNomConf(conference);
                    break;
                case 2:
                    modifierThemeConf(conference);
                    break;
                case 3:
                    modifierLieuConf(conference);
                    break;
                case 4:
                    modifierDateDebutConf(conference);
                    break;
                case 5:
                    modifierDateFinConf(conference);
                    break;
                case 6:
                    modifierDateT1Conf(conference);
                    break;
                case 7:
                    modifierDateT2Conf(conference);
                    break;    
                case 8:
                    modifierDateT3Conf(conference);
                    break;
                case 9:
                    modifierDateT4Conf(conference);
                    break;  
                case 10:
                    modifierToutesLesDates(conference);
                    break;
                case 11:
                    modifierTexteAccueil(conference);
                    break;
                case 12:
                    modifierLogoConference(conference);
                    break;    
                default:
                    break;
            }
        }
    }
    
    private void modifierNomConf(Conference conference)
    {
        String nom = this.ihm.saisirNomConference();
        
        if(conference.setNom(nom))
        {
            this.ihm.notifier("Le nom a correctement été modifié");
        }
        else
        {
            this.ihm.notifier("Le nom n'a pu être modifié car une autre conférence porte ce nom");
        }
    }
    
    private void modifierThemeConf(Conference conference)
    {
        String theme = this.ihm.saisirThemeConference();
        
        if(conference.setTheme(theme))
        {
            this.ihm.notifier("Le theme a correctement été modifié");
        }
        else
        {
            this.ihm.notifier("Le theme n'a pu être modifié");
        }
    }
    
    private void modifierLieuConf(Conference conference)
    {
        String lieu = this.ihm.saisirLieuConference();
        
        if(conference.setLieu(lieu))
        {
            this.ihm.notifier("Le lieu a correctement été modifié");
        }
        else
        {
            this.ihm.notifier("Le lieu n'a pu être modifié");
        }
    }
    
    private void modifierDateDebutConf(conference)
    {
        LocalDate date;
        this.ihm.notifier("Veuillez saisir la nouvelle date de début");
        
        date = parse(this.ihm.saisirDate());
        
        switch(conference.setDateDebut(date));
        {
            case 0 :
                this.ihm.notifier("La date de début a correctement été modifiée")
                break;
            case 1 : 
                this.ihm.notifier("La date de début est antérieure à la date T4");
                break;
            case 2 : 
                this.ihm.notifier("La date de début est ultérieure à la date de fin");
                break;
            default:
                break;
        }
    }
    
    private void modifierDateFinConf(Conference conference)
    {
        LocalDate date;
        this.ihm.notifier("Veuillez saisir la nouvelle date de fin");
        
        date = parse(this.ihm.saisirDate());
        
        if(conference.setDateFin(date));
        {
            this.ihm.notifier("La date de fin a correctement été modifiée");
        }
        else
        {
            this.ihm.notifier("La date de fin est antérieure à la date de début")
        }

    }
    private void modifierDateT1Conf(Conference conference)
    {
        LocalDate date;
        this.ihm.notifier("Veuillez saisir la nouvelle dateT1");
        
        date = parse(this.ihm.saisirDate());
        
        if(conference.setDateT1(date));
        {
            this.ihm.notifier("La dateT1 a correctement été modifiée");
        }
        else
        {
            this.ihm.notifier("La dateT1 est ultérieure à la dateT2")
        }
    }
    
    private void modifierDateT2Conf(Conference conference)  
    {
        LocalDate date;
        this.ihm.notifier("Veuillez saisir la nouvelle dateT2");
        
        date = parse(this.ihm.saisirDate());
        
        switch(conference.setDateT2(date));
        {
            case 0 :
                this.ihm.notifier("La dateT2 a correctement été modifiée")
                break;
            case 1 : 
                this.ihm.notifier("La dateT2 est antérieure à la date T1");
                break;
            case 2 : 
                this.ihm.notifier("La dateT2 est ultérieure à la dateT3");
                break;
            default:
                break;
        }
    }
    private void modifierDateT3Conf(Conference conference)
    {
        LocalDate date;
        this.ihm.notifier("Veuillez saisir la nouvelle dateT3");
        
        date = parse(this.ihm.saisirDate());
        
        switch(conference.setDateT3(date));
        {
            case 0 :
                this.ihm.notifier("La dateT3 a correctement été modifiée")
                break;
            case 1 : 
                this.ihm.notifier("La dateT3 est antérieure à la date T2");
                break;
            case 2 : 
                this.ihm.notifier("La dateT3 est ultérieure à la dateT4");
                break;
            default:
                break;
        }
    }
    
    private void modifierDateT4Conf(Conference conference)
    {
        LocalDate date;
        this.ihm.notifier("Veuillez saisir la nouvelle dateT4");
        
        date = parse(this.ihm.saisirDate());
        
        switch(conference.setDateT4(date)) {
            case 0 :
                this.ihm.notifier("La dateT4 a correctement été modifiée");
                break;
            case 1 : 
                this.ihm.notifier("La dateT4 est antérieure à la date T3");
                break;
            case 2 : 
                this.ihm.notifier("La dateT4 est ultérieure à la date de début");
                break;
            default:
                break;
        }
    }
    
    private void modifierToutesLesDates(Conference conference)
    {
        LocalDate dateDebut, dateFin, dateT1, dateT2, dateT3, dateT4;
        
        this.ihm.notifier("Veuillez saisir la nouvelle date de debut");
        dateDebut = this.ihm.saisirDate();
        
        this.ihm.notifier("Veuillez saisir la nouvelle date de fin");
        dateFin = this.ihm.saisirDate();
        
        this.ihm.notifier("Veuillez saisir la nouvelle dateT1");
        dateT1 = this.ihm.saisirDate();
        
        this.ihm.notifier("Veuillez saisir la nouvelle dateT2");
        dateT2 = this.ihm.saisirDate();
        
        this.ihm.notifier("Veuillez saisir la nouvelle dateT3");
        dateT3 = this.ihm.saisirDate();
        
        this.ihm.notifier("Veuillez saisir la nouvelle dateT4");
        dateT4 = this.ihm.saisirDate();
        
        switch(conference.setDates(dateDebut, dateFin, dateT1, dateT2, dateT3, dateT4))
        {
            case 0:
                this.ihm.notifier("Les dates ont correctement été modifiée");
                break;
            case 1:
                this.ihm.notifier("La dateT1 n'est pas la plus petite date");
                break;
            case 2:
                this.ihm.notifier("La dateT2 n'est pas située entre dateT1 et dateT3");
                break;
            case 3:
                this.ihm.notifier("La dateT3 n'est pas située entre dateT2 et dateT4");
                break;
            case 4:
                this.ihm.notifier("La dateT4 n'est pas située entre dateT3 et la date de début");
                break;
            case 5:
                this.ihm.notifier("La date de début n'est pas située entre dateT4 et la date de fin");
                break;
            case 6:
                this.ihm.notifier("La date de fin n'est pas la plus grande date");
                break;
        }
    }
    
    private void modifierTexteAccueil(Conference conference)
    {
        String texteAccueil;
        
        texteAccueil = this.ihm.saisirTexteAccueilConference();


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
    
    public void modifiercommunication(Conference conference, Communication communication) {
    	while (communication == null) {
    		HashMap<String, Communication> communications = conference.getCommunications();
    		int saisir = ihm.saisirNumeroCommunication(communications);
    		communication = conference.getCommunication(saisir);
    	}
    	ihm.afficherInfosCommunication(communication);
    	
    	int choix = 1;
    	while (choix != 0) {
    		choix = ihm.optionsModifierCommunication();
    		switch (choix) {
    		case 0:
    			break;
    		case 1:
    			modifierTitreCommunication(communication);
    			break;
    		case 2:
    			modifierAuteursCommunication(communication);
    			break;
    		case 3:
    			modifierLienPDFCommunication(communication);
    			break;
    		case 4:
    			modifierLienVideoCommunication(communication);
    			break;
    		case 5:
    			modifierDateCommunication(communication);
    			break;
    		case 6:
    			modifierHorairesCommunication(communication);
    			break;
    		case 7:
    			modifierTypeCommunicationDeCommunication(communication, conference);
    			break;
    		case 8:
    			modifierOrateursCommunication(communication);
    			break;
    		case 9:
    			modifierCorrespondantCommunication(communication);
    			break;
    		}
    	}
    }
    
    public void modifierSession(Conference conference, Session session) {
    	while (session == null) {
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
    			addCommunicationASession(null, session, conference);
    			break;
    		case 9:
    			removeCommunicationASession(null, conference, session);
    			break;
    		}
    	}
    }
    
    public void modifierTypeCommunication(Conference conference) {
    	HashMap<String, TypeCommunication> typesCommunication = conference.getTypesCommunications();
    	ihm.afficherCaracteristiquesTypesCommunication(typesCommunication);
    	String libelle = ihm.saisirLibelleTypeCommunication();
    	TypeCommunication e = conference.getTypeCommunication(libelle);
    	if (e == null) {
    		ihm.notifier("Ce type n'existe pas");
    	} else {
    		int choix = 1;
    		while (choix != 0) {
    			ihm.afficherCaracteristiquesTypeCommunication(e);
    			choix = ihm.saisirOptionModifierTypeCommunication();
    			switch (choix) {
    			case 0:
    				break;
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
    
    public void previsualiserConference(Conference conference) {
    	int choix = 1;
    	while (choix != 0) {
    		ihm.afficherNomConference(conference);
    		HashMap<String, Session> sessions = conference.getSessions();
    		ihm.afficherLibelleSessions(sessions);
    		boolean choix = ihm.demandeSiPrevisualisationSession();
    		if (choix) {
    			String intitule = ihm.demanderNomSession();
    			Session sessionAPrevisualiser = conference.getSession(intitule);
    			if (sessionAPrevisualiser) {
    				previsualiserSession(session);
    			} else {
    				ihm.notifier("Cette session n'existe pas");
    			}
    		}
    	}
    }
    
    public void previsualiserSession(Session session, Conference conference) {
    	int choix = 1;
    	while (choix != 0) {
    		ihm.afficherInfosSession(this);
    		HashMap<String, Communication> communications = session.getCommunications();
    		
    		Iterator<Communication> it = communications.iterator();
    		while (it.hasNext()) {
    			Communication c = it.next();
    			ihm.afficherTitreCommunication(c);
    		}
    	}
    	choix = ihm.saisirOptionPrevisualiserSession();
    	switch (choix) {
    	case 0:
    		break;
    	case 1:
    		int numero = ihm.saisirNumeroCommunication();
    		Communication communication = session.getCommunication(numero);
    		if (communication) {
    			previsualiserCommunication(communication);
    		} else {
    			ihm.notifier("Cette communication n'existe pas");
    		}
    		break;
    	case 2:
    		modifierSession(conference, session)
    		break;
    	}
    }
    
    public void removeCommunication(Conference conference) {
    	HashMap<int, Communication> communications = conference.getCommunications();
    	int numero = ihm.saisirNumeroCommunication(communications);
    	Communication communication = conference.getCommunication(numero);
    	if (communication) {
    		Session session = communication.getSession();
    		boolean r = session.removeCommunication(communication);
    		if (r) {
    			HashMap<String, Utilisateur> orateurs = communication.getOrateurs();
    			Iterator<Utilisateur> it = orateurs.iterator();
    			while (it.hasNext()) {
    				Utilisateur u = it.next();
    				if (u.removeCommunicationEnTantQueOrateur(communication) == false) {
    					ihm.notifier("L'orateur n'a pas pu être retiré de la communication");
    				}
    			}
    			Utilisateur correspondant = communication.getCorrespondant();
    			if (correspondant.removeCommunicationEnTantQueCorrespondant(communication)) {
    				if (conference.removeCommunication(communication)) {
    					ihm.notifier("La communication a correctement été supprimée de la conférence");
    				} else {
    					ihm.notifier("La communication n'a pas pu être supprimée de la conférence");
    				}
    			} else {
    				ihm.notifier("La communication n'a pu être retirée du correspondant");
    			}
    		} else {
    			ihm.notifier("La communication n'a pu être retirée de la session ");
    		}
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
    			if (utilisateur == null) {
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

    private void modifierTitreCommunication(Communication communication) {
    	// manque méthode ihm
    	String nouveauTitre = ihm.;
    	boolean r = communication.setTitre(nouveauTitre);
    	if (r) {
    		ihm.notifier("Le titre a correctement été changé");
    	} else {
    		ihm.notifier("Le titre n'a pu être changé");
    	}
    }
    
    private void modifierAuteursCommunication(Communication communication) {
    	int option = 1;
    	while (option != 0) {
    		HashSet<String> auteurs = communication.getAuteurs();
    		option = saisirSupprimerOuAjouterUtilisateur(auteurs);
    		switch (option) {
    		case 0:
    			break;
    		case 1:
    			String[3] infosUtilisateur = ihm.saisirUtilisateur();
    			
    			boolean r = communication.addAuteur(infosUtilisateur[0], infosUtilisateur[1], infosUtilisateur[2]);
    			if (r) {
    				ihm.notifier("L'auteur a correctement été ajouté");
    			} else {
    				ihm.notifier("L'auteur n'a pu être ajouté");
    			}
    			break;
    		case 2:
    			String[3] infosUtilisateur = ihm.saisirUtilisateur();
    			
    			boolean r = communication.removeAuteur(infosUtilisateur[0], infosUtilisateur[1], infosUtilisateur[2]);
    			if (r) {
    				ihm.notifier("L'auteur a correctement été supprimé");
    			} else {
    				ihm.notifier("L'auteur n'a pu être supprimé");
    			}
    			break;
    		}
    	}
    }
    
    private void modifierLienPDFCommunication(Communication communication) {
    	String lienPDF = ihm.saisirLienPDF();
    	boolean r = communication.setLienPDF(lienPDF);
    	if (r) {
    		ihm.notifier("Le lien vers le PDF a correctement été modifié");
    	} else {
    		ihm.notifier("Le lien vers le PDF n'a pu être modifié");
    	}
    }
    
    private void modifierLienVideoCommunication(Communication communication) {
    	String lienVideo = ihm.saisirLienVideo();
    	boolean r = communication.setLienVideo(lienVideo);
    	if (r) {
    		ihm.notifier("Le lien vers la vidéo a correctement été modifié");
    	} else {
    		ihm.notifier("Le lien vers la vidéo n'a pu être modifié");
    	}
    }
    
    private void modifierDateCommunication(Communication communication) {
    	String date = ihm.saisirDate();
    	boolean r = communication.setDate(date);
    	if (r) {
    		ihm.notifier("La date a correctement été modifiée");
    	} else {
    		ihm.notifier("La date n'a pu être modifiée");
    	}
    }
    
    private void modifierHorairesCommunication(Communication communication) {
    	ihm.notifier("Entrez la nouvelle heure de début");
    	String nouvelleHeureDebut = ihm.saisirHeure();
    	
    	ihm.notifier("Entrez la nouvelle heure de fin");
    	String nouvelleHeureFin = ihm.saisirHeure();
    	
    	int r = communication.setHoraires(nouvelleHeureDebut, nouvelleHeureFin);
    	if (r == 0) {
    		ihm.notifier("Les horaires ont correctement été modifiés");
    	} else {
    		ihm.notifier("Les horaires n'ont pu être modifiés");
    	}
    }
    
    private void modifierTypeCommunicationDeCommunication(Communication communication, Conference conference) {
    	HashMap<String, TypeCommunication> types = conference.getTypesCommunications();
    	String libelleNouveauType = ihm.saisirLibelleTypeCommunication();
    	TypeCommunication nouveauType = conference.getTypeCommunication(libelleNouveauType);
    	boolean r = false;
    	if (nouveauType) {
    		r = communication.setTypeCommunication(nouveauType);
    	}
    	if (r) {
    		ihm.notifier("Le type de communication a correctmeent été ajouté");
    	} else {
    		ihm.notifier("Le type de communication n'a pu être ajouté");
    	}
    }
    
    private void modifierOrateursCommunication(Communication communication) {
    	int option = 1;
    	while (option != 0) {
    		HashMap<String, Utilisateur> orateurs = communication.getOrateurs();
    		option = saisirSupprimerOuAjouterUtilisateur(orateurs);
    		switch (option) {
    		case 0:
    			break;
    		case 1:
    			String[3] infosUtilisateur = ihm.saisirUtilisateur();
    			Utilisateur nouvelOrateur = genconf.getUtilisateur(infosUtilisateur[0], infosUtilisateur[1], infosUtilisateur[2]);
    			if (nouvelOrateur == null) {
    				nouvelOrateur = creerCompteGenConf();
    			}
    			
    			boolean r = communication.setOrateur(nouvelOrateur);
    			if (r) {
    				ihm.notifier("L'orateur a correctement été ajouté");
    			} else {
    				ihm.notifier("L'orateur n'a pu être ajouté");
    			}
    			break;
    		case 2:
    			String[3] infosUtilisateur = ihm.saisirUtilisateur();
    			Utilisateur orateurASupprimer = genconf.getUtilisateur(infosUtilisateur[0], infosUtilisateur[1], infosUtilisateur[2]);
    			
    			boolean r = false;
    			if (orateurASupprimer) {
    				r = communication.removeOrateur(orateurASupprimer);
    			}
    			if (r) {
    				ihm.notifier("L'orateur a correctement été supprimé");
    			} else {
    				ihm.notifier("L'orateur n'a pu être supprimé");
    			}
    			break;
    		}
    	}
    }
    
    private void modifierCorrespondantCommunication(Communication communication) {
    	String[3] infosUtilisateur = ihm.saisirUtilisateur();
		Utilisateur correspondant = genconf.getUtilisateur(infosUtilisateur[0], infosUtilisateur[1], infosUtilisateur[2]);
		boolean r = communication.setCorrespondant(correspondant);
		if (r) {
			ihm.notifier("Le correspondant a correctement été ajouté");
		} else {
			ihm.notifier("Le correspondant n'a pu être supprimé");
		}
    }
    
    
    
    
    
    
    
    
    

        if(conference.setTexteAccueil(texteAccueil))
        {
            this.ihm.notifier("Le texte d'accueil a correctement été modifié");
        }
        else
        {
            this.ihm.notifier("Le texte d'accueil n'a pu être modifié");
        }
    }
    private void modifierLogoConference(Conference conference)
    {
        String logo;
        
        logo = this.ihm.saisirLogo();

        if(conference.setLogo(logo))
        {
            this.ihm.notifier("Le logo a correctement été modifié");
        }
        else
        {
            this.ihm.notifier("Le logo n'a pu être modifié");
        }
    }
    /**************************************************************************************/
    /**************************************************************************************/
    /**************************************************************************************/
    
    public void removeSession(Conference conf)
    {
        HashMap<String, Session> sessions;
        String intituleSession;
        
        sessions = conference.getSessions();
        
        this.ihm.afficherInfosSessions(sessions);
        intituleSession = this.ihm.demanderNomSession();
        
        switch(conf.removeSession())
        {
            case 0 : 
                this.ihm.notifier("La session a correctement été modifiée");
                break;
            case 1 : 
                this.ihm.notifier("Des communications sont liées à la session, supprimez d'abord les communications");
                break;
            case 2 : 
                this.ihm.notifier("Une erreur est survenue lors de la suppression de la session dans un animateur");
                break;
            case 3 : 
                this.ihm.notifier("Une erreur est survenue dans la suppression d'une session dans un track");
                break;
            default:
                break;
        }
    }
    /**************************************************************************************/
    /**************************************************************************************/
    /**************************************************************************************/
    
    public void removeCommunicationASession(Communication communication, Session session)
    {
        HashMap<Integer, Communication> communications;
        int numero;
        
        while(communication == null)
        {
            communications = session.getCommunications();
            numero = this.ihm.saisirNumeroCommunication(communciations);
            
            communication = session.getCommunication(numero);
;       }
        
        if(communication.removeSession() && session.removeCommunication(communication))
        {
            this.ihm.notifier("La communication a correctement été retirée de la session");
        }
        else
        {
            this.ihm.notifier("Une erreur est survenue dans la suppression de la communication");
        }
        
    }
    
    /**************************************************************************************/
    /**************************************************************************************/
    /**************************************************************************************/
    
    public void creerTrack(Conference conf)
    {
        String libelle;
        Couleur couleur;
        Track existe;
        HashMap<String, Track> tracks = conf.getTracks();
        
        libelle = this.ihm.saisirTracks();
        couleur = Couleur.valueByCode(this.ihm.saisirCouleurTrack());
        
        existe = conf.getTrack(libelle);
        
        if(existe == null)
        {
            existe = new Track(conf, libelle, couleur);
            conf.addTrack(existe);
        }
        else
        {
            this.ihm.notifier("Ce track existe déjà !");
        }
        
    }
    
    public void removeTrack(Conference conf)
    {
        String libelle;
        HashMap<String, Session> sessions;
        HashMap<String, Track> tracks = conf.getTracks();
        boolean r;
        
        libelle = this.ihm.saisirTrack(tracks);
        
        Track track = conf.getTrack(libelle);
        
        if(track != null)
        {
            sessions = track.getSessions();
            
            for(Session s : sessions && r)
            {
                r = s.removeTrack();
            }
            
            if(r)
            {
                if(conferenceSelectionnee.removeTrack())
                {
                    this.ihm.notifier("Le track a correctement été modifié");
                }
                else
                {
                    this.ihm.notifier("Le track n'a pu être modifié");
                }
            }
            else
            {
                this.ihm.notifier("Une erreur s'est produite dans la suppression du track dans les sessions");
            }
        }
        else
        {
            this.ihm.notifier("Ce track n'existe pas");
        }
    }
    
    /**************************************************************************************/
    /**************************************************************************************/
    /**************************************************************************************/
    
    public void removeTypeCommunication(Conference conference)
    {
        HashMap<String, TypeCommunication> types = conference.getTypesCommunication();
        TypeCommunication typeASupprrimer;
        String libelle;
        
        this.ihm.afficherCaracteristiquesTypesCommunication(types);
        
        do
        {
            libelle = this.ihm.saisirNomTypeCommunication();
            typeASupprimer = types.get(libelle);
            
        }while(typeASupprimer != null);
        
        if(supprimerTypeCommunication(typeASupprimer))
        {
            this.ihm.notifier("Le type a correctement été supprimé");
        }
        else
        {
            this.ihm.notifier("Le type n'a pu être supprimé");
        }
    }
    
}
