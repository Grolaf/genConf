package genconf.modele;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.validator.routines.EmailValidator;


public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
    private final String email;
    private String nom;
    private String prenom;
    private final Map<String, Conference> conferencesAdministrees;  // association qualifiée par le nom
    private Session session;
    private Map<String, Conference> inscriptions;
    private Map<String,Communication> communications;
    private Map<String,Communication> communicationsCorrespondant;

    public Utilisateur(String email, String nom, String prenom) {
        assert EmailValidator.getInstance(false, false).isValid(email);
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        //this.conferencesAdministrees = new HashMap<>();
    }

    public String getEmail() {
        return this.email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    

    public void addConferenceAdministree(Conference conference) {
        assert !this.conferencesAdministrees.containsKey(conf.getNom());
        this.conferencesAdministrees.put(conference.getNom(), conference);
    }
    public void addInscriptionConference(String nomConf, Conference conference ){
}

    public void addSessionEnTantQueAnimateur(Session session ){

    }
    public addCommunicationEnTantQueOrateur(Communication communication){
        
    }
    public removeCommunicationEnTantQueOrateur(Communication communication){

    }
    public removeSessionEnTantQueAnimateur(Session session){

    }
    }
