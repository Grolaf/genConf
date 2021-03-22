package modele;

import java.time.LocalDate;
import java.util.Hashset;
import java.util.Map;

/**
 *
 * @author vleb
 */

 /**
 * The class Utilisateur
 */ 
public class Utilisateur {


     private int numero;
     private String titre;
     private String auteurs;
     private String lienVersPDF;
     private String lienVersVideo;

    private LocalDate date;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    private HashSet<String> auteurs;
    private Session session;
    private Utilisateur correspondant;
    private HashMap<String> orateurs;

    // ALED LIGNE 33
    private Conference conference;

    


    // Constructeur : 

/** 
 *
 * Communication
 *
 * @param numero  the numero
 * @param titre  the titre
 * @param auteurs  the auteurs
 * @param lienVersPDF  the lien vers PDF
 * @param lienVersVideo  the lien vers video
 * @param date  the date
 * @param dateDebut  the date debut
 * @param dateFin  the date fin
 * @return public
 */
    public Communication(int numero, String titre, String auteurs, String lienVersPDF,String lienVersVideo, LocalDate date, LocalDate dateDebut, LocalDate dateFin) { 


      this.numero=numero;
      this.titre=titre;
      this.auteurs=auteurs;
      this.lienVersPDF=lienVersPDF;
      this.lienVersVideo=lienVideo;
      this.date=date;
      this.dateDebut=dateDebut;
      this.dateFin=dateFin;


    }

  // ---------------------------Setter :


/** 
 *
 * Sets the titre
 *
 * @param titre  the titre
 */
    public void setTitre(String titre) { 

        this.titre = titre;
    }


/** 
 *
 * Sets the correspondant
 *
 * @param correspondant  the correspondant
 * @return boolean
 */
    public boolean setCorrespondant(Utilisateur correspondant){ 

          this.correspondant=correspondant;
    }


/** 
 *
 * Sets the date
 *
 * @param date  the date
 */
    public void setDate(LocalDate date){ 

        this.date=date;
    }


/** 
 *
 * Sets the horaires
 *
 * @param nouvelleHeureDebut  the nouvelle heure debut
 * @param nouvelleHeureFin  the nouvelle heure fin
 */
    public void setHoraires(String nouvelleHeureDebut,String nouvelleHeureFin){ 

        this.nouvelleHeureDebut=nouvelleHeureDebut;
        this.nouvelleHeureFin=nouvelleHeureFin;
    }


/** 
 *
 * Sets the lien vers PDF
 *
 * @param lienVersPDF  the lien vers PDF
 */
    public void setLienVersPDF( String lienVersPDF){ 

        this.lienVersPDF=lienVersPDF;
    }


/** 
 *
 * Sets the lien vers video
 *
 * @param lienVersVideo  the lien vers video
 */
    public void setLienVersVideo( String lienVersVideo){ 

        this.lienVersVideo=lienVersVideo;
    }


/** 
 *
 * Sets the session
 *
 * @param session  the session
 */
    public void setSession( Session session){ 

        this.session=session;
    }

  

    // ---------------------------Getter :


/** 
 *
 * Gets the auteurs
 *
 * @return the auteurs
 */
    public HashSet <String>  getAuteurs(){ 

        return  this.auteurs;
    }
/** 
 *
 * Gets the orateurs
 *
 * @return the orateurs
 */
    public HashMap <String Utilisateur> getOrateurs(){ //ALED 180

       return Utilisateur.orateurs;
    }


  /** 
 *
 * Gets the correspondant
 *
 * @return the correspondant
 */
    public Utilisateur getCorrespondant(){ 

        return  this.correspondant;
    }



/** 
 *
 * Gets the horaires debut
 *
 * @return the horaires debut
 */
    public LocalDate getHorairesDebut(){ 

        return  this.dateDebut;

    }

/** 
 *
 * Gets the horaires fin
 *
 * @return the horaires fin
 */
    public LocalDate getHorairesFin(){ 

        return  this.dateFin;
    }


/** 
 *
 * Gets date
 *
 * @return the date
 */
    public LocalDate getDate(){ 

        return  this.date;
    }


/** 
 *
 * Gets the session
 *
 * @return the session
 */
    public Session getSession(){ 

        return  this.session;
    }


/** 
 *
 * Gets the titre
 *
 * @return the titre
 */
    public String getTitre(){ 

        return this.titre;
    }
    
    // Fonction Ajouter ADD:



/** 
 *
 * Add auteur
 *
 * @param auteur  the auteur
 */
    public void addAuteur(String auteur){ 

        this.auteur.add(auteur); 
    }
   

    // Fonction Suprrimer REMOVE:


/** 
 *
 * Remove auteurs
 *
 * @param auteur  the auteur
 */
    public boolean removeAuteurs(String auteur){ 

        auteurs.remove(auteur);
    }

/** 
 *
 * Remove orateurs
 *
 * @param auteur  the orateurs 
 */
    public boolean removeOrateur(String orateursASupprimer){ // ALED  : 294
        removeCommunicationEnTantQueOrateur(this)
        orateurs.remove(orateursASupprimer);
    }



/** 
 *
 * Remove type communication
 *
 * @param libelle  the libelle
 */
    public void removeTypeCommunication(String libelle){ 


    }



   
  
    
}