package genconf.modele;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.validator.routines.EmailValidator;
import java.time.LocalDate;

public class Session {

    private String intituleSession;
    private String type;
    private LocalDate date;
    private String heureDebut;
    private String heureFin;
    private String videoAssociee;
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

    public String getHoraireDebut() {
        return this.heureDebut;
    }

    public String getHoraireFin() {
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

    public void addAnimateur(Utilisateur animateur) {
        this.animateurs.put(animateur.getEmail(), animateur);
        animateur.addSessionEnTantQueAnimateur(this);
    }
    
    public void removeAnimateur(Utilisateur animateur) {
        animateur.removeSessionEnTantQueAnimateur(this);
        this.animateurs.remove(animateur);
    }

    public addCommunication(Communication communication) {
        this.communications = new HashMap<>(communication);
    }
    
    

    public removeCommunication(Communication communication) {

    }

    /***********************************************************/
    /**     Usual functions     **/
    
    public bool isHoraireValide(String heureDebut, String heureFin) {

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
