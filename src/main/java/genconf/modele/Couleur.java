/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genconf.modele;

import genconf.controleur.Commande;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author antoine
 */
public enum Couleur {
    
    ROUGE("rouge"),
    BLEU("bleu"),
    VERT("vert"),
    ORANGE("orange"),
    JAUNE("jaune"),
    VIOLET("violet"),
    NOIR("noir"),
    ;
    
    public final String valeur;
    
     private static final Map<String, Couleur> valueByCode = new HashMap<>();

    static {
        // On initialise une fois pour la durée de vie de l'application le
        // cache de la fonction `valueOfCode`
        for (Couleur clr: Couleur.values()) {
            Couleur.valueByCode.put(clr.valeur, clr);
        }
    }
    
    public static final Couleur valueByCode(String couleurSting)
    {
        Couleur clr = Couleur.valueByCode.get(couleurSting);
        
        if (clr == null) {
            throw new IllegalArgumentException("Invalid couleur");
        }
        return clr;
    }
    
    private Couleur(String valeur)
    {
        this.valeur = valeur;
    }
}
