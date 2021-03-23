package genconf.modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class Conference implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
    private final GenConf genconf;
    private String nom;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Map<String, Utilisateur> administrateurs;  // association qualifiée par l'email
    private Map<String, TypeCommunication> typeCommunication;
    private Map<String, Communication> communication;
    private Map<String, Track> track;
    private Map<String, Session> session;
    private Map<String, Utilisateur> inscrit;
    private String theme;
    private String lieu;
    private LocalDate dateT1;
    private LocalDate dateT2;
    private LocalDate dateT3;
    private LocalDate dateT4;
    private String logo;
    private String texteAccueil;
        
    
    // Invariant de classe : !dateDebut.isAfter(dateFin)
    //     On utilise la négation ici pour exprimer (dateDebut <= dateFin), ce
    //     qui est équivalent à !(dateDebut > dateFin).

    

    public Conference(GenConf genconf, String nom, LocalDate dateDebut, LocalDate dateFin) {
        assert !dateDebut.isAfter(dateFin);
        this.genconf = genconf;
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        
    }

    public void setAdmin(Utilisateur u){
        administrateurs.add(u.email,u);
        u.addConferenceAdministree(this);
    }
    public String getNom() {
        return this.nom;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        assert !dateDebut.isAfter(this.dateFin);
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        assert !this.dateDebut.isAfter(dateFin);
        this.dateFin = dateFin;
    }
    
    
    public Collection <Utilisateur> getAdmin(){
        return this.administrateurs.values();
    } 

    public void ajouteAdministrateur(Utilisateur admin) {
        assert !this.administrateurs.containsKey(admin.getEmail());
        this.administrateurs.put(admin.getEmail(), admin);
        admin.ajouteConferenceAdministree(this);
    }
    public boolean addCommunication(Communication communication, int IDcomm){

    }

    public void addInscrit(Utilisateur inscrit, String email){

    }
    public void addSession(Session session){

    }

    public void addTypeCommunication(TypeCommunication type){

    }
    public Communication getCommunication(int numeroComm){

    }
    public Map<int,Communication> getCommunications(){

    }
    public String getDates(){

    }
    public Session getSession(String intituleSession){

    }
    public Map<String,Sessions> (){

    }
    public TypeCommunication (String libelle){

    }
    public Map<String,Session>(){

    }
    public boolean is SalleDisponible(String salle, Session session){

    }

    public void setAdminConf(Utilisateur utilisateur, String email){

    }
    public void setDateT1(String dateT1){

    }
    public void setDateT2(String dateT2){

    }
    public void setDateT3(String dateT3){

    }
    public void setDateT4(String dateT4){

    }
    public void setLieu(String lieu){

    }
    public void setLogo(String logo){

    }
    public void setTexteAccueil(String texteAccueil){

    }
    public void setTheme(String theme){

    }
    public boolean supprimerCommunication(Communication communication){

    }
    public boolean supprimerSession(String intituleSession){

    }
    public void supprimerTypeCommunication(String libelle){

    }


}
