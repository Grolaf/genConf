package modele;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Hashset;
import java.util.Map;
import genconf.modele.*;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author vleb
 */

 /**
 * The class Utilisateur
 */ 
public class Communication {

    private static int nextID;

    private int numero;
    private String titre;
    private String lienVersPDF;
    private String lienVersVideo;
    private Utilisateur Correspondant;
    private TypeCommunication type;
    private Conference conference;

    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;

    private Set<String[]> auteurs;
    private Session session;
    private Utilisateur correspondant;
    private HashMap<String, Utilisateur> orateurs;

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
        public Communication(Utilisateur correspondant, String titre, Set<String[]> auteurs, String lienPDF, String lienVideo, LocalDate date,LocalTime heureDebut, LocalTime heureFin, HashMap<String, Utilisateur> orateurs, TypeCommunication type, Conference conference) 
        { 
            LocalDate dateDebutConf, dateFinConf;
            boolean ras = true;

            this.type = type;
            this.titre = titre;
            
            if(this.type.getLienPDFObligatoire())
            {
                this.lienVersPDF = lienPDF;
            }
            if(this.type.getLienVideoObligatoire())
            {
                this.lienVersVideo = lienVideo;
            }
            

            
            if(!setHoraires(heureDebut, heureFin))
            {
               throw new Error("L'heure de début est ultérieure à la date de fin : Abort");
            }
            
            dateDebutConf = conference.getDateDebut();
            dateFinConf = conference.getDateFin();      
            
            if(!setDate(date))
            {
                throw new Error("La date de la communication n'est pas dans les dates de la conférence : Abort");
            }
            
            this.conference = conference;
            
            this.orateurs = new HashMap<>();
            
            for(HashMap.Entry<String, Utilisateur> orateur: orateurs.entrySet())    
            {
               addOrateur(orateur.getValue());
            }
            
            setCorrespondant(correspondant);
                          
            this.numero = nextID;
            nextID++;  
        }


        // ---------------------------Getters :


    /** 
     *
     * Gets the auteurs
     *
     * @return the auteurs
     */
        public Set<String[]>  getAuteurs(){ 

            return  this.auteurs;
        }
    /** 
     *
     * Gets the orateurs
     *
     * @return the orateurs
     */
        public HashMap<String, Utilisateur> getOrateurs(){ //ALED 180

           return this.orateurs;
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
        public LocalTime getHorairesDebut(){ 

            return  this.heureDebut;

        }

    /** 
     *
     * Gets the horaires fin
     *
     * @return the horaires fin
     */
        public LocalTime getHorairesFin(){ 

            return  this.heureFin;
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
        
        
         // ---------------------------Setters :


    /** 
     *
     * Sets the titre
     *
     * @param titre  the titre
     */
        public boolean setTitre(String titre) { 

            this.titre = titre;
            
            return true;
        }
        
        public boolean setTypeCommunication(TypeCommunication type)
        {
            this.type = type;
            
            return true;
        }


    /** 
     *
     * Sets the correspondant
     *
     * @param correspondant  the correspondant
     * @return boolean
     */
        public boolean setCorrespondant(Utilisateur nouveauCorrespondant){ 
            
            boolean egal = false;

            for (HashMap.Entry<String, Utilisateur> orateur : this.orateurs.entrySet() ) 
            {
                if(nouveauCorrespondant == orateur.getValue())
                {
                    egal = true;
                }
            } 
            

            if(egal)
            {
                if(this.correspondant != null)
                {
                    this.correspondant.removeCommunicationEnTantQueCorrespondant(this);
                }
                nouveauCorrespondant.addCommunicationEnTantQueCorrespondant(this);
                this.correspondant=nouveauCorrespondant;
                return true;
            }
            else
            {
                return false;
            }
        }

    /* Valeur de retour :

    0 : RAS
    1 : Erreur : echec dans l'attribution
    2 : Erreur : le correspondant choisi n'est pas un orateur de la communication


    /** 
     *
     * Sets the date
     *
     * @param date  the date
     */
        public boolean setDate(LocalDate date){ 

            LocalDate dateSession = date;
            
            if(this.session == null)
            {
                dateSession = this.session.getDate();
            }
            
            if(date == dateSession)
            {
                this.date=date;
                return true;
            }
            else
            {
                return false;
            }
        }


    /** 
     *
     * Sets the horaires
     *
     * @param nouvelleHeureDebut  the nouvelle heure debut
     * @param nouvelleHeureFin  the nouvelle heure fin
     */
        public int setHoraires(LocalTime heureDebut,LocalTime heureFin){ 

            LocalTime heureDebutSession, heureFinSession;

            if(this.session != null)
            {
                heureDebutSession = this.session.getHoraireDebut();
                heureFinSession = this.session.getHoraireFin();
                
                if(heureDebut.isBefore(heureDebutSession) || heureFin.isAfter(heureFinSession))
                {
                    return 2;
                }
            }
            
            if(!heureDebut.isBefore(heureFin))
            {
               return 1;
            }
            
            this.heureDebut = heureDebut;
            this.heureFin = heureFin;
            return 0;

        }


    /** 
     *
     * Sets the lien vers PDF
     *
     * @param lienVersPDF  the lien vers PDF
     */
        public boolean setLienVersPDF( String lienVersPDF){ 

            if(this.type != null && this.type.getLienPDFObligatoire())
            {
                this.lienVersPDF = lienVersPDF;
                return true;
            }
           
            return false;
        }


    /** 
     *
     * Sets the lien vers video
     *
     * @param lienVersVideo  the lien vers video
     */
        public boolean setLienVersVideo( String lienVersVideo){ 

            if(this.type != null && this.type.getLienVideoObligatoire())
            {
                this.lienVersVideo = lienVersVideo;
                return true;
            }
           
            return false;
        }


    /** 
     *
     * Sets the session
     *
     * @param session  the session
     *
     */
        public boolean setSession(Session session){ 

            this.session=session;
            
            return session.addCommunication(this);
        }



    /** 
     *
     * Add auteur
     *
     * @param auteur  the auteur
     */
        public boolean addAuteur(String[] auteur){ 

            this.auteurs.add(auteur); 
            
            return true;
        }


        //------- Fonction Suprrimer REMOVE:


    /** 
     *
     * Remove un auteur
     *
     * @param auteur  the auteur
     */
        public boolean removeAuteur(String auteur){ 

            auteurs.remove(auteur);
            return true;

        }
        
        public boolean addOrateur(Utilisateur orateur)
        {
            boolean ras;
         
            this.orateurs.put(orateur.getEmail(), orateur);
            return orateur.addCommunicationEnTantQueOrateur(this);

        }

    /** 
     *
     * Remove orateurs
     *
     * @param orateurs  the orateurs 
     */
        public boolean removeOrateur(Utilisateur orateurASupprimer){ // ALED  : 294
            orateurASupprimer.removeCommunicationEnTantQueOrateur(this);
            orateurs.remove(orateurASupprimer.getEmail());
            return true;

        }

    /** 
     *
     * Remove session
     *
     * @param session  the session
     */
        public boolean removeSession(){ 

            this.session=null;
            return true;

        }



    /** 
     *
     * Remove type communication
     *
     * @param libelle  the libelle
     */
        public boolean removeTypeCommunication(){ 

            this.type=null;
            return true;

        }






}