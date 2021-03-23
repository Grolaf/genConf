package genconf.modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import genconf.controleur.*;
import genconf.modele.*;
import genconf.util.*;
import genconf.vue.*;
import java.util.Map;

public class Conference implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
    private String nomConf;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Set<String> theme;
    private Set<String> lieu;
    private LocalDate dateT1;
    private LocalDate dateT2;
    private LocalDate dateT3;
    private LocalDate dateT4;
    private String logo;
    private String texteAccueil;
    private GenConf genconf;
    private Map<String, Utilisateur> administrateurs;  // association qualifiée par l'email
    private Map<String, Utilisateur> inscrits;
    private Map<String, TypeCommunication> typesCommunication;
    private Map<Integer, Communication> communications;
    private Map<String, Track> tracks;
    private Map<String, Session> sessions;

   
}
