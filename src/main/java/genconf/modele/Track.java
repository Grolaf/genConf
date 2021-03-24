/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genconf.modele;

import java.util.HashMap;

/**
 *
 * @author antoine
 */
public class Track {
    
    private String libelle;
    private HashMap<String, Session> sessions;
    private Couleur couleur;
    
    public Track(String libelle, Couleur couleur)
    {
        this.libelle = libelle;
        this.couleur = couleur;
        this.sessions = new HashMap<>();
    }
    
    /***********************************************************/
    /**     Getters     **/
    
    public Couleur getCouleur()
    {
        return this.couleur;
    }
    public HashMap<String, Session> getSessions()
    {
        return this.sessions;
    }
    
    public String getLibelle()
    {
        return this.libelle;
    }
   
    /***********************************************************/
    /**     Setters     **/
    
    public boolean removeSession(Session session)
    {
        this.sessions.remove(session);
        
        return true;
    }
    
    /***********************************************************/
    /**     Usual functions     **/
    
    public String toString()
    {
        return "Track " + this.libelle + " ( " + this.couleur + " )"; 
    }
    
   
}
