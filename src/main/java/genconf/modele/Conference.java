package modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Iterator;

import genconf.controleur.*;
import genconf.modele.*;
import genconf.util.*;
import genconf.vue.*;

public class Conference implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
    private String nomConf;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private HashSet<String> theme;
    private HashSet<String> lieu;
    private LocalDate dateT1;
    private LocalDate dateT2;
    private LocalDate dateT3;
    private LocalDate dateT4;
    private String logo;
    private String texteAccueil;
    private GenConf genconf;
    private HashMap<String, Utilisateur> administrateurs;  // association qualifiée par l'email
    private HashMap<String, Utilisateur> inscrit;
    private HashMap<String, TypeCommunication> typeCommunication;
    private HashMap<Integer, Communication> communication;
    private HashMap<String, Track> tracks;
    private HashMap<String, Session> session;

    public boolean addAdminConf(Utilisateur utilisateur, String email) {
    	administrateurs.put(email, utilisateur);
    	return true;
    }
    
    public boolean addCommunication(Communication communication) {
    	this.communication.put(communication.getNumero(), communication);
    	return true;
    }
    
    public boolean addInscrit(Utilisateur inscrit) {
    	this.inscrit.put(inscrit.getEmail(), inscrit);
    	return true;
    }
    
    public boolean addSession(Session session) {
    	this.session.put(session.getIntituleSession(), session);
    	return true;
    }
    
    public boolean addTrack(Track track) {
    	this.tracks.put(track.getLibelle(), track);
    	return true;
    }
    
    public boolean addTypeCommunication(TypeCommunication type) {
    	this.typeCommunication.put(type.getLibelle(), type);
    }
    
    Conference(String nomConf, LocalDate dateDebut, LocalDate dateFin) {
    	this.nomConf = nomConf;
    	this.dateDebut = dateDebut;
    	this.dateFin = dateFin;
    	this.administrateurs = new HashMap<>();
    	this.inscrit = new HashMap<>();
    	this.typeCommunication = new HashMap<>();
    	this.communication = new HashMap<>();
    	this.tracks = new HashMap<>();
    	this.session = new HashMap<>();
    }
    
    public Communication getCommunication(int numeroComm) {
    	return this.communication.get(numeroComm);
    }
    
    public HashMap<Integer, Communication> getCommunications() {
    	return this.communication;
    }
    
    public LocalDate getDateDebut() {
    	return this.dateDebut;
    }
    
    public LocalDate getDateFin() {
    	return this.dateFin;
    }
    
    public Session getSession(String intituleSession) {
    	return this.session.get(intituleSession);
    }
    
    public HashMap<String, Session> getSessions() {
    	return this.session;
    }
    
    public Track getTrack(String libelle) {
    	return this.tracks.get(libelle);
    }
    
    public HashMap<String, Track> getTracks() {
    	return this.tracks;
    }
    
    public TypeCommunication getTypeCommunication(String libelle) {
    	return this.typeCommunication.get(libelle);
    }
    
    public HashMap<String, TypeCommunication> getTypeCommunications() {
    	return this.typeCommunication;
    }
    
    public boolean isSalleDisponible(String salle, Session session) {
    	for (Session s : this.session.values()) {
    		if (s.getSalle() == salle) {
    			if (s.getDate().equals(session.getDate())) {
    				if (s.getHoraireFin().isAfter(session.getHoraireDebut()) || session.getHoraireFin().isAfter(s.getHoraireDebut())) {
    					return false;
    				}
    			}
    		}
    	}
    	return true;
    }
    
    public boolean removeCommunication(Communication communication) {
    	this.communication.remove(communication.getNumero());
    	return true;
    }
    
    public int removeSession(String intituleSession) {
    	this.session.remove(intituleSession);
    	return 0; 
    }
    
    public boolean removeTrack(Track track) {
    	this.tracks.remove(track.getLibelle());
    }
    
    public int setDateDebut(String nouvelleDate) {
    	this.dateDebut = LocalDate.parse(nouvelleDate);
    	return 0;
    }
    
    public boolean setDateFin(String nouvelleDate) {
    	this.dateFin = LocalDate.parse(nouvelleDate);
    	return true;
    }
    
    public int setDates(String dateT1, String dateT2, String dateT3, String dateT4, String dateDebut, String dateFin) {
    	setDateT1(dateT1);
    	setDateT2(dateT2);
    	setDateT3(dateT3);
    	setDateT4(dateT4);
    	setDateDebut(dateDebut);
    	setDateFin(dateFin);
    	return 0;
    }
    
    public boolean setDateT1(String nouvelleDateT1) {
    	this.dateT1 = LocalDate.parse(nouvelleDateT1);
    	return true;
    }
    
    public int setDateT2(String nouvelleDateT2) {
    	this.dateT2 = LocalDate.parse(nouvelleDateT2);
    	return 0;
    }
    
    public int setDateT3(String nouvelleDateT3) {
    	this.dateT3 = LocalDate.parse(nouvelleDateT3);
    	return 0;
    }
    
    public int setDateT4(String nouvelleDateT4) {
    	this.dateT4 = LocalDate.parse(nouvelleDateT4);
    	return 0;
    }
    
    public boolean setLieu(String nouveauLieu) {
    	this.lieu.add(nouveauLieu);
    	return true;
    }
    
    public boolean setLogo(String nouveauLogo) {
    	this.logo = nouveauLogo;
    	return true;
    }
    
    public boolean setNom(String nouveauNom) {
    	this.nomConf = nouveauNom;
    	return true;
    }
    
    public boolean setTexteAccueil(String nouveauTexteAccueil) {
    	this.texteAccueil = nouveauTexteAccueil;
    	return true;
    }
    
    public boolean setTheme(String nouveauTheme) {
    	this.theme.add(nouveauTheme);
    	return true;
    }

    public boolean supprimerTypeCommunication(TypeCommunication typeCommunication) {
    	this.typeCommunication.remove(typeCommunication.getLibelle());
    	return true;
    }

    public String toString() {
    	String infosConference =  this.nomConf + this.dateDebut.toString() + this.dateFin.toString();
    	
    	String[] themes = (String[]) this.theme.toArray();
    	for (int i = 0 ; i < this.theme.size() ; i++) {
    		infosConference += themes[i];
    	}
    	
    	String[] lieux = (String[]) this.lieu.toArray();
    	for (int i = 0 ; i < this.lieu.size() ; i++) {
    		infosConference += lieux[i];
    	}
    	
    	infosConference += this.dateT1.toString() + this.dateT2.toString() + this.dateT3.toString() + this.dateT4.toString();
    	infosConference += this.logo + this.texteAccueil;
    	return infosConference;
    }

}
