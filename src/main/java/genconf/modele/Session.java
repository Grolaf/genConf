package genconf.modele;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.validator.routines.EmailValidator;
import java.time.LocalDate;
import java.time.LocalTime;

public class Session {

    private String intituleSession;
    private String type;
    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private String lienVersVideo;
    private String salle;
    private Map<String, Utilisateur> animateurs;
    private Map<Integer, Communication> communications;
    private Map<String, Track> tracks;

    Session(String intitule, String type, LocalDate date, String heureDebut, String heureFin, String videoAssociee, String salle, Conference conference, Communcation communication) {
        this.intituleSession = intitule;
        this.type = type;
        this.date = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.videoAssociee = videoAssociee;
        this.salle = salle;
        this.;               // pas terminé
  } 

   /***********************************************************/
    /**     Getters     **/

    public Map<String, Utilisateur> getAnimateurs() {
        return this.animateurs;
    }

    public Map<String, Communication> getCommunications() {
        return this.communications;
    }
    
    public Communication getCommunication(int numero)
    {
        return this.communications.get(numero);
    }

    public LocalDate getDate() {
        return this.date;
    }

    public LocalTime getHoraireDebut() {
        return this.heureDebut;
    }

    public LocalTime getHoraireFin() {
        return this.heureFin;
    }

    public String getSalle() {
        return this.salle;
    }

    public Map<String, Track> getTracks() {
        return this.tracks;
    }

    
    /***********************************************************/
    /**     Setters     **/

    public int setHoraires(LocalTime nouvelleHeureDebut, LocalTime nouvelleHeureFin)
    {
        LocalTime heureDebutCommunication, heureFinCommunication;
        boolean valide = true;
        
        if(!isHoraireValide(nouvelleHeureDebut,nouvelleHeureFin))
        {
            return 1;
        }
        
        for(Communication comm : this.communciations && valide)
        {
            heureDebutCommunication = comm.getHorairesDebut();
            heureFinCommunication = comm.getHorairesFin();
            
            valide = nouvelleHeureDebut.isBefore(heureDebutCommunication) && nouvelleHeureFin.isAfter(heureFinCommunication);
        }
        
        if(valide)
        {
            this.heureDebut = nouvelleHeureDebut;
            this.heureFin = nouvelleHeureFin;
            return 0;
        }
        else if(nouvelleHeureDebut.isAfter(heureDebutCommunication))
        {
            return 2;
        }
        else
        {
            return 3;
        }
    }
    
    public int setDate(LocalDate nouvelleDate)
    {
        LocalDate dateDebutConf = this.conference.getDateDebut();
        LocalDate dateFinConf = this.conference.getDateFin();
        
        if(this.communication.size() != 0 && nouvelleDate != this.date)
        {
            return 1;
        }
        else if(this.date.isBefore(dateDebutConf))
        {
            return 2;
        }
        else if (this.date.isAfter(dateFinConf))
        {
            return 3;
        }
        else
        {
            this.date = nouvelleDate;
            return 0;
        }
    }
    
    public boolean setIntitule(String nouvelIntitule)
    {
       Session existe =  this.conference.getSession(nouvelIntitule);
       
       if(existe == null)
       {
           return false;
       }
       else
       {
           this.intituleSession = nouvelIntitule;
           
           return true;
       }
    }
    
    public boolean setVideo(String nouveauLienVideo)
    {
        this.lienVersVideo = nouveauLienVideo;
        
        return true;
    }
    
    public boolean setSalle(String nouvelleSalle)
    {
        if(this.conference.isSalleDisponible(nouvelleSalle))
        {
            this.salle = nouvelleSalle;
            
            return true;
        }
        else
        {
            return false;
        }
        
    }
    
    public boolean setType(String nouveauType)
    {
        this.type = nouveauType;
        
        return true;
    }
    
    
    public void addAnimateur(Utilisateur animateur) {
        this.animateurs.put(animateur.getEmail(), animateur);
        animateur.addSessionEnTantQueAnimateur(this);
    }
    
    public void removeAnimateur(Utilisateur animateur) {
        animateur.removeSessionEnTantQueAnimateur(this);
        this.animateurs.remove(animateur);
    }

    public boolean addCommunication(Communication communication) {
        this.communications.put(communication.getNumero(), communication);
        
        return true;
    }
    

    public boolean removeCommunication(Communication communication) {
       this.communications.remove(communication);
       
       return true;
    }

    /***********************************************************/
    /**     Usual functions     **/
    
    public boolean isHoraireValide(LocalTime heureDebut, LocalTime heureFin) {
        return heureDebut.isBefore(heureFin);
    }
    
    public String toString()
    {
        String str =  "Session " + this.intituleSession +" : \n";
        str += "\t Type : " + this.type + "\n";
        str += "\t Date : " + this.date+ "\n";
        str += "\t HeureDebut : " + this.heureDebut+ "\n";
        str += "\t HeureFin : " + this.heureFin+ "\n";
        str += "\t Video : " + this.lienVersVideo + "\n";
        str += "\t Salle : " + this.salle + "\n";
        
        str += "\t Tracks : \n";
        
        for(Track trk : this.tracks)
        {
            str += "\t\t " + trk.toString() + "\n";
        }
    }
}

© 2021 GitHub, Inc.
    Terms
    Privacy
    Security
    Status
    Docs

    Contact GitHub
    Pricing
    API
    Training
    Blog
    About
