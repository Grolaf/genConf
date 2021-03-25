package genconf.modele;

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
    
    public Conference(String nomConf, LocalDate dateDebut, LocalDate dateFin) {
    	this.nomConf = nomConf;
    	this.dateDebut = dateDebut;
    	this.dateFin = dateFin;
    	this.administrateurs = new HashMap<>();
    	this.inscrit = new HashMap<>();
    	this.typeCommunication = new HashMap<>();
    	this.communication = new HashMap<>();
    	this.tracks = new HashMap<>();
    	this.session = new HashMap<>();
    	this.theme = new HashSet<>();
    	this.lieu = new HashSet<>();
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
    
    public HashMap<String, TypeCommunication> getTypesCommunications() {
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
    			if (a.setSessionEnTantQueAnimateur(null)) {
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
        return true;
    }
    
    public int setDateDebut(LocalDate nouvelleDate) {
    	if (nouvelleDate.isAfter(dateT4) && nouvelleDate.isBefore(this.dateFin)) {
    		this.dateDebut = nouvelleDate;
    		return 0;
    	} else {
    		return 1;
    	}
    }
    
    public boolean setDateFin(LocalDate nouvelleDate) {
    	if (nouvelleDate.isAfter(this.dateDebut)) {
    		this.dateFin = nouvelleDate;
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public int setDates(LocalDate dateT1, LocalDate dateT2, LocalDate dateT3, LocalDate dateT4, LocalDate dateDebut, LocalDate dateFin) {
    	setDateT1(dateT1);
    	setDateT2(dateT2);
    	setDateT3(dateT3);
    	setDateT4(dateT4);
    	setDateDebut(dateDebut);
    	setDateFin(dateFin);
    	return 0;
    }
    
    public int setDateT1(LocalDate nouvelleDateT1) {
    	if (this.dateT2 == null ||  nouvelleDateT1.isBefore(this.dateT2)) {
    		this.dateT1 = nouvelleDateT1;
    		return 0;
    	} else {
    		return 1;
    	}
    }
    
    public int setDateT2(LocalDate nouvelleDateT2) {
    	if ((this.dateT1 == null || nouvelleDateT2.isAfter(this.dateT1)) && (this.dateT3 == null || nouvelleDateT2.isBefore(this.dateT3))) {
    		this.dateT2 = nouvelleDateT2;
    		return 0;
    	} else {
    		return 1;
    	}
    }
    
    public int setDateT3(LocalDate nouvelleDateT3) {
    	if ((this.dateT2 == null || nouvelleDateT3.isAfter(this.dateT2)) && (this.dateT4 == null || nouvelleDateT3.isBefore(this.dateT4))) {
    		this.dateT3 = nouvelleDateT3;
    		return 0;
    	} else {
    		return 1;
    	}
    }
    
    public int setDateT4(LocalDate nouvelleDateT4) {
    	if ((this.dateT3 == null || nouvelleDateT4.isAfter(this.dateT2)) && nouvelleDateT4.isBefore(this.dateDebut)) {
    		this.dateT4 = nouvelleDateT4;
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
    	Conference conferenceExiste = genconf.getConference(nouveauNom);
    	if (conferenceExiste == null) {
    		genconf.removeConference(getNomConf());
    		this.nomConf = nouveauNom;
    		genconf.addConference(this);
    		return true;
    	} else {
    		return false;
    	}
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
    	if(typeCommunication != null)
    	{
	    	for(Communication c : this.communication.values()) {
	    		if (c.getTypeCommunication().getLibelle() == typeCommunication.getLibelle()) {
	    			c.removeTypeCommunication();
	    		}
	    	}
	    	this.typeCommunication.remove(typeCommunication.getLibelle());
	    	return true;
    	}
    	else
    	{
    		return false;
    	}
    }

    public String toString() {
    	String infosConference =  this.nomConf + " du  " + this.dateDebut.toString() + " au " + this.dateFin.toString() +"\n";
    	infosConference += "\tLogo : " +  this.logo + "\n";
    	infosConference += "\tTexteAccueil : " +  this.texteAccueil + "\n";
    	
    	for(String t : this.theme)
    	{
    		infosConference += "\t\t" + t + "\n";
    	}
    	
    	
    	infosConference += "\tLieux : \n";
    	for(String l : this.lieu)
    	{
    		infosConference += "\t\t" + l + "\n";
    	}
    	
    	if(this.dateT1 != null)
    	{
    		infosConference += "\tDateT1 : " + this.dateT1.toString() + "\n";
    	}
    	if(this.dateT2 != null)
    	{
    		infosConference += "\tDateT2 : " +this.dateT2.toString()+ "\n";
    	}
    	if(this.dateT3 != null)
    	{
    		infosConference += "\tDateT3 : " +this.dateT3.toString()+ "\n";
    	}
    	if(this.dateT4 != null)
    	{
    		infosConference += "\tDateT4 : " +this.dateT4.toString()+ "\n";
    	}
    	return infosConference;
    }

}
