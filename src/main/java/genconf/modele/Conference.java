package modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
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
    	if (administrateurs.get(email) == null) {
    		administrateurs.put(email, utilisateur);
    	}
    	return true;
    }
    
    public boolean addCommunication(Communication communication) {
    	LocalDate dateComm = communication.getDate();
    	if (dateComm.equals())
    	this.communication.put(communication.getNumero(), communication);
    	return true;
    }
    
    public boolean addInscrit(Utilisateur inscrit) {
    	inscrit.addConferenceInscrit(this);
    	this.inscrit.put(inscrit.getEmail(), inscrit);
    	return true;
    }
    
    public boolean addSession(Session session) {
    	Session s = getSession(session.getIntituleSession());
    	if (s == null) {
        	this.session.put(session.getIntituleSession(), session);
    	}
    	return true;
    }
    
    public boolean addTrack(Track track) {
    	this.tracks.put(track.getLibelle(), track);
    	return true;
    }
    
    public boolean addTypeCommunication(TypeCommunication type) {
    	this.typeCommunication.put(type.getLibelle(), type);
    	return true;
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
    
    public String getNomConf() {
    	return this.nomConf;
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
    	int valeurDeRetour = 0;
    	Session session = getSession(intituleSession);
    	HashMap<Integer, Communication> communications = getCommunications();
    	if (communications == null) {
    		HashMap<String, Utilisateur> animateurs = session.getAnimateurs();
    		for(Utilisateur a : animateurs.values()) {
    			if (a.removeSessionEnTantQueAnimateur(session)) {
    				valeurDeRetour++;
    			}
    		}
    		HashMap<String, Track> tracks = session.getTracks();
    		for(Track t : tracks.values()) {
    			if (t.removeSession(session)) {
    				valeurDeRetour++;
    			}
    		}
        	this.session.remove(intituleSession);
    	}
    	return valeurDeRetour; 
    }
    
    public boolean removeTrack(Track track) {
    	this.tracks.remove(track.getLibelle());
    }
    
    public int setDateDebut(String nouvelleDate) {
    	LocalDate date = LocalDate.parse(nouvelleDate);
    	if (date.isAfter(dateT4) && date.isBefore(dateFin)) {
    		this.dateDebut = date;
    		return 0;
    	} else {
    		return 1;
    	}
    }
    
    public boolean setDateFin(String nouvelleDate) {
    	LocalDate date = LocalDate.parse(nouvelleDate);
    	if (date.isAfter(dateDebut)) {
    		this.dateFin = date;
    		return true;
    	} else {
    		return false;
    	}
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
    
    public int setDateT1(String nouvelleDateT1) {
    	LocalDate date = LocalDate.parse(nouvelleDateT1);
    	if (date.isBefore(dateT2)) {
    		this.dateT1 = date;
    		return 0;
    	} else {
    		return 1;
    	}
    }
    
    public int setDateT2(String nouvelleDateT2) {
    	LocalDate date = LocalDate.parse(nouvelleDateT2);
    	if (date.isAfter(dateT1) && date.isBefore(dateT3)) {
    		this.dateT2 = date;
    		return 0;
    	} else {
    		return 1;
    	}
    }
    
    public int setDateT3(String nouvelleDateT3) {
    	LocalDate date = LocalDate.parse(nouvelleDateT3);
    	if (date.isAfter(dateT2) && date.isBefore(dateT4)) {
    		this.dateT3 = date;
    		return 0;
    	} else {
    		return 1;
    	}
    }
    
    public int setDateT4(String nouvelleDateT4) {
    	LocalDate date = LocalDate.parse(nouvelleDateT4);
    	if (date.isAfter(dateT3) && date.isBefore(dateDebut)) {
    		this.dateT4 = date;
    		return 0;
    	} else {
    		return 1;
    	}
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
    	HashMap<String, Conference> conferences = genconf.getConferences();
    	for (Conference conference : conferences.values()) {
    		if (conference.getNomConf() == nouveauNom) {
    			
    		}
    	}
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
    	for(Communication c : this.communication.values()) {
    		if (c.getTypeCommunication().getLibelle() == typeCommunication.getLibelle()) {
    			c.removeTypeCommunication();
    		}
    	}
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
