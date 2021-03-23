package genconf.modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class GenConf implements Serializable {

    /*
     * private static final long serialVersionUID = 1L; // nécessaire pour la
     * sérialisation private final Map<String, Utilisateur> utilisateurs; //
     * association qualifiée par l'email private final Map<String, Conference>
     * conferences; // association qualifiée par le nom
     * 
     * public GenConf() { this.utilisateurs = new HashMap<>(); this.conferences =
     * new HashMap<>(); }
     * 
     * public boolean ajouteUtilisateur(String email, String nom, String prenom) {
     * if (this.utilisateurs.containsKey(email)) { return false; } else {
     * this.utilisateurs.put(email, new Utilisateur(email, nom, prenom)); return
     * true; } }
     * 
     * public Map<String, Conference> getConferences() { return this.conferences; }
     * 
     * public Map<String, Utilisateur> getUtilisateurs() { return this.utilisateurs;
     * }
     * 
     * public void nouvelleConference(String nom, LocalDate dateDebut, LocalDate
     * dateFin, String adminEmail) { assert !this.conferences.containsKey(nom);
     * assert this.utilisateurs.containsKey(adminEmail); Utilisateur admin =
     * this.utilisateurs.get(adminEmail); Conference conf =
     * Conference.initialiseConference(this, nom, dateDebut, dateFin, admin);
     * this.conferences.put(nom, conf); }
     */
    private HashMap<String, Utilisateur> utilisateurs;
    private HashMap<String, Utilisateur> adminGenConf;
    private HashMap<String, Conference> conference;

    public boolean addCompteConf(String nom, String prenom, String email) {
        Utilisateur utilisateur = new Utilisateur(email, nom, prenom);
        utilisateurs.put(email, utilisateur);
        return true;
    }

    public boolean addConference(Conference conf) {
        conference.put(conf.getNom(), conf);
        return true;
    }

    public Conference getConference(String nomConf) {
        return conference.get(nomConf);
    }

    public HashMap<String, Conference> getConferences() {
        return conference;
    }

    public Utilisateur getUtilisateur(String mail) {
        return utilisateurs.get(mail);
    }

    public HashMap<String, Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

}
