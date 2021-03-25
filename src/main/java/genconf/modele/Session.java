package genconf.modele;

import genconf.modele.*;
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
    private HashMap<String, Utilisateur> animateurs;
    private HashMap<Integer, Communication> communications;
    private HashMap<String, Track> tracks;
    private Conference conference;

    public Session(String intitule, String type, LocalDate date, LocalTime heureDebut, LocalTime heureFin, String videoAssociee, String salle, Conference conference) {
        
    	this.conference = conference;
    	this.communications = new HashMap<>();
    	this.animateurs = new HashMap<>();
    	this.tracks = new HashMap<>();
    	
    	this.lienVersVideo = videoAssociee;
    	this.type = type;
    	
        if(setIntitule(intitule) && setDate(date) == 0 && setSalle(salle) && heureDebut.isBefore(heureFin))
        {
             System.out.println("Veuillez modifier les données de la session : elles sont invalides !");
        }
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.conference = conference;          
    } 

   /***********************************************************/
   /**     Getters     **/
    
    public String getIntituleSession()
    {
        return this.intituleSession;
    }

    public HashMap<String, Utilisateur> getAnimateurs() {
        return this.animateurs;
    }

    public HashMap<Integer, Communication> getCommunications() {
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

    public HashMap<String, Track> getTracks() {
        return this.tracks;
    }
    
    public Track getTrack(String libelle)
    {
        return this.tracks.get(libelle);
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
        
        for(Communication comm : this.communications.values())
        {
            heureDebutCommunication = comm.getHorairesDebut();
            heureFinCommunication = comm.getHorairesFin();
            
            valide = nouvelleHeureDebut.isBefore(heureDebutCommunication) && nouvelleHeureFin.isAfter(heureFinCommunication);
            if (!valide) {
            	if(nouvelleHeureDebut.isAfter(heureDebutCommunication))
                {
                    return 2;
                }
            }
        }
        
        if(valide)
        {
            this.heureDebut = nouvelleHeureDebut;
            this.heureFin = nouvelleHeureFin;
            return 0;
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
        
        if(this.communications.size() != 0 && nouvelleDate != this.date)
        {
            return 1;
        }
        else if(this.date != null && this.date.isBefore(dateDebutConf))
        {
            return 2;
        }
        else if (this.date != null && this.date.isAfter(dateFinConf))
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
       Session existe = this.conference.getSession(nouvelIntitule);
       
       if(existe == null) {
    	   this.conference.removeSession(getIntituleSession());
    	   this.intituleSession = nouvelIntitule;
    	   this.conference.addSession(this);
           return true;
       } else {
           return false;
       }
    }
    
    public boolean setLienVideo(String nouveauLienVideo)
    {
        this.lienVersVideo = nouveauLienVideo;
        
        return true;
    }
    
    public boolean setSalle(String nouvelleSalle)
    {
        if(this.conference.isSalleDisponible(nouvelleSalle, this))
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
    
    
    public boolean addAnimateur(Utilisateur animateur) {
        this.animateurs.put(animateur.getEmail(), animateur);
        animateur.setSessionEnTantQueAnimateur(this);
        
        return true;
    }
    
    public boolean removeAnimateur(Utilisateur animateur) {
        animateur.setSessionEnTantQueAnimateur(null);
        this.animateurs.remove(animateur.getEmail());
        
        return true;
    }

    public boolean addCommunication(Communication communication) {
        this.communications.put(communication.getNumero(), communication);
        
        return true;
    }
    

    public boolean removeCommunication(Communication communication) {
       this.communications.remove(communication.getNumero());
       
       return true;
    }
    
    public boolean addTrack(Track track)
    {
        this.tracks.put(track.getLibelle(), track);
        
        return true;
    }
    public boolean removeTrack(Track track)
    {
        this.tracks.remove(track.getLibelle());
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
        
        for(HashMap.Entry<String, Track> trk : this.tracks.entrySet())
        {
            str += "\t\t " + trk.getValue().toString() + "\n";
        }
        
        return str;
    }
}
/*
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
*/