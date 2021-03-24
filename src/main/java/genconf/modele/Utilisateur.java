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
    private HashMap<String, Conference> conferencesAdministrees;  // association qualifiée par le nom
    private Session session;
    private HashMap<String, Conference> inscriptions;
    private HashMap<Integer,Communication> communications;
    private HashMap<Integer,Communication> communicationsCorrespondant;

    public Utilisateur(String email, String nom, String prenom) {
        assert EmailValidator.getInstance(false, false).isValid(email);
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.conferencesAdministrees = new HashMap<>();
        this.inscriptions = new HashMap<>();
        this.communications = new HashMap<>();
        this.communicationsCorrespondant = new HashMap<>();
        this.session = null;
    }

    public boolean addCommunicationEnTantQueCorrespondant(Communication communication) {
    	this.communicationsCorrespondant.put(communication.getNumero(), communication);
    	return true;
    }
    
    public boolean addCommunicationEnTantQueOrateur(Communication communication) {
    	this.communications.put(communication.getNumero(), communication);
    	return true;
    }
    
    public boolean addConferenceAdministree(Conference conference) {
    	this.conferencesAdministrees.put(conference.getNomConf(), conference);
    	return true;
    }
    
    public boolean addConferenceInscrit(Conference conference) {
    	this.inscriptions.put(conference.getNomConf(), conference);
    	return true;
    }
    
    public boolean removeCommunicationEnTantQueCorrespondant(Communication communication) {
    	this.communicationsCorrespondant.remove(communication.getNumero());
    	return true;
    }
    
    public boolean removeCommunicationEnTantQueOrateur(Communication communication) {
    	this.communications.remove(communication.getNumero());
    	return true;
    }
    
    public boolean setSessionEnTantQueAnimateur(Session session) {
    	this.session = session;
    	return true;
    }
    
    public String toString() {
    	return this.email + this.nom + this.prenom;
    }
    
    
    
}