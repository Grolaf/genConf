package genconf.controleur;

import genconf.modele.*;
import genconf.util.Persisteur;
import genconf.vue.IHM;
import java.time.LocalDate;
import java.util.HashMap;

public class Controleur {

    private final GenConf genconf;
    private final IHM ihm;

    public Controleur(GenConf genconf) {
        this.genconf = genconf;

        // choisir la classe CLI ou GUI
//        this.ihm = new CLI(this);
        this.ihm = new IHM(this);
    }
    
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
        
        switch(conference.setDateT4(date));
        {
            case 0 :
                this.ihm.notifier("La dateT4 a correctement été modifiée")
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
