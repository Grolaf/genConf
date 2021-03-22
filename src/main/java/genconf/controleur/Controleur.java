package genconf.controleur;

import genconf.modele.GenConf;
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
            cmd = this.ihm.afficherMenuGeneral();
            
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
                            supprimerSession(conferenceSelectionnee);
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
                            supprimerCommunication(conferenceSelectionnee);
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
                            supprimerTypeCommunication(conferenceSelectionnee);
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
                            supprimerTrack(conferenceSelectionnee);
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
}
