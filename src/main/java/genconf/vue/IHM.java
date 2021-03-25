package genconf.vue;

import genconf.controleur.Commande;
import genconf.controleur.Controleur;
import genconf.modele.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.function.Function;
import org.apache.commons.validator.routines.EmailValidator;
import java.util.Map;

/**
 * La classe CLI est responsable des interactions avec l'utilisa·teur/trice en
 * mode texte.
 * <p>
 * C'est une classe qui n'est associée à aucun état : elle ne contient aucun
 * attribut d'instance.
 * <p>
 * Aucun méthode de cette classe n'est pas censée modifier ses paramètres, c'est
 * pourquoi les paramètres des méthodes sont tous marqués comme `final`.
 *
 */
public class IHM {
    // /**
    // /**
    // * Nombre maximum d'essais pour la lecture d'une saisie utilisa·teur/trice.
    // */
    // private static final int MAX_ESSAIS = 3;
    // private final Controleur controleur;

    // public IHM(Controleur controleur) {
    // this.controleur = controleur;
    // }

    // /** Classes conteneurs

    // * Classe conteneur pour les informations saisies à propos d'un
    // * {@link fr.uga.iut2.genconf.modele.Utilisateur}.
    // *
    // * <ul>
    // * <li>Tous les attributs sont `public` par commodité d'accès.</li>
    // * <li>Tous les attributs sont `final` pour ne pas être modifiables.</li>
    // * </ul>
    // */
    // public static class InfosUtilisateur {
    // public final String email;
    // public final String nom;
    // public final String prenom;

    // public InfosUtilisateur(final String email, final String nom, final String
    // prenom) {
    // this.email = email;
    // this.nom = nom;
    // this.prenom = prenom;
    // }
    // }

    // /**
    // * Classe conteneur pour les informations saisies pour une nouvelle
    // * {@link fr.uga.iut2.genconf.modele.Conference}.
    // *
    // * <ul>
    // * <li>Tous les attributs sont `public` par commodité d'accès.</li>
    // * <li>Tous les attributs sont `final` pour ne pas être modifiables.</li>
    // * </ul>
    // */
    // public static class InfosNouvelleConference {
    // public final String nom;
    // public final LocalDate dateDebut;
    // public final LocalDate dateFin;
    // public final InfosUtilisateur admin;

    // public InfosNouvelleConference(final String nom, final LocalDate dateDebut,
    // final LocalDate dateFin, final InfosUtilisateur admin) {
    // assert !dateDebut.isAfter(dateFin);
    // this.nom = nom;
    // this.dateDebut = dateDebut;
    // this.dateFin = dateFin;
    // this.admin = admin;
    // }
    // }

    // //----- Éléments du dialogue
    // -------------------------------------------------

    // private Commande dialogueSaisirCommande() {
    // IHM.afficher("===== GenConf: Générateur Site Conférence =====");
    // IHM.afficher(IHM.synopsisCommandes());
    // IHM.afficher("===============================================");
    // IHM.afficher("Saisir l'identifiant de l'action choisie :");
    // return IHM.lireAvecErreurs(IHM::parseCommande);
    // }

    // private InfosUtilisateur dialogueSaisirUtilisateur() {
    // String email, nom, prenom;

    // IHM.afficher("== Saisie d'un·e utilisa·teur/trice ==");
    // email = IHM.lireEmail();
    // IHM.afficher("Saisir le nom :");
    // nom = IHM.lireNom();
    // IHM.afficher("Saisir le prénom :");
    // prenom = IHM.lireNom();

    // return new InfosUtilisateur(email, nom, prenom);
    // }

    // private InfosNouvelleConference dialogueSaisirNouvelleConference(final
    // Set<String> nomsExistants) {
    // String nom;
    // LocalDate dateDebut, dateFin;
    // InfosUtilisateur admin;

    // IHM.afficher("== Saisie d'une nouvelle conférence ==");
    // IHM.afficher("Saisir le nom de la conférence :");
    // nom = IHM.lireNom(nomsExistants, true);
    // IHM.afficher("Date de début: ");
    // dateDebut = IHM.lireDate();
    // IHM.afficher("Date de fin: ");
    // dateFin = IHM.lireDate(dateDebut);

    // IHM.afficher("Saisir les informations à propos de l'administra·teur/trice de
    // la conférence.");
    // IHM.afficher("Un·e nouvel·lle utilisa·teur/trice sera créé·e si
    // nécessaire.");
    // admin = this.dialogueSaisirUtilisateur();

    // return new InfosNouvelleConference(nom, dateDebut, dateFin, admin);
    // }

    // //----- Implémentation des méthodes pubiques appelées par le controleur
    // -------------------------------

    // public void afficherInterface() {
    // Commande cmd;
    // do {
    // cmd = dialogueSaisirCommande();
    // controleur.gererDialogue(cmd);
    // } while (cmd != Commande.QUITTER);
    // }

    // public void informerUtilisateur(final String msg, final boolean succes) {
    // IHM.afficher((succes ? "[OK]" : "[KO]") + " " + msg);
    // }

    // public void saisirUtilisateur() {
    // InfosUtilisateur infos = dialogueSaisirUtilisateur();
    // controleur.creerUtilisateur(infos);
    // }

    // public void saisirNouvelleConference(final Set<String> nomsExistants) {
    // InfosNouvelleConference infos =
    // dialogueSaisirNouvelleConference(nomsExistants);
    // controleur.creerConference(infos);
    // }

    // //----- Primitives d'affichage
    // -----------------------------------------------

    // /**
    // * Construit le synopsis des commandes.
    // *
    // * @return Une chaîne de caractères contenant le synopsis des commandes.
    // */
    // private static String synopsisCommandes() {
    // StringBuilder builder = new StringBuilder();

    // for (Commande cmd: Commande.values()) {
    // builder.append(" "); // légère indentation
    // builder.append(cmd.synopsis());
    // builder.append(System.lineSeparator());
    // }

    // return builder.toString();
    // }

    // /**
    // * Affiche un message à l'attention de l'utilisa·teur/trice.
    // *
    // * @param msg Le message à afficher.
    // */
    // private static void afficher(final String msg) {
    // System.out.println(msg);
    // System.out.flush();
    // }

    // //----- Primitives de lecture
    // ------------------------------------------------

    // /**
    // * Essaie de lire l'entrée standard avec la fonction d'interprétation.
    // * <p>
    // * En cas d'erreur, la fonction essaie au plus {@value #MAX_ESSAIS} fois de
    // * lire l'entrée standard.
    // *
    // * @param <T> Le type de l'élément lu une fois interprété.
    // *
    // * @param parseFunction La fonction d'interprétation: elle transforme un
    // * token de type chaîne de caractère un un objet de type T.
    // * <p>
    // * La fonction doit renvoyer l'option vide en cas d'erreur, et une
    // * option contenant l'objet interprété en cas de succès.
    // * <p>
    // * La fonction d'interprétation est responsable d'afficher les messages
    // * d'erreur et de guidage utilisa·teur/trice.
    // *
    // * @return L'interprétation de la lecture de l'entrée standard.
    // */
    // private static <T> T lireAvecErreurs(final Function<String, Optional<T>>
    // parseFunction) {
    // Optional<T> result = Optional.empty();
    // Scanner in = new Scanner(System.in);
    // String token;

    // for (int i = 0; i < IHM.MAX_ESSAIS && result.isEmpty(); ++i) {
    // token = in.next();
    // result = parseFunction.apply(token);
    // }

    // return result.orElseThrow(() -> new Error("Erreur de lecture (" +
    // IHM.MAX_ESSAIS + " essais infructueux)."));
    // }

    // /**
    // * Interprète un token entier non signé comme une {@link Commande}.
    // *
    // * @param token La chaîne de caractère à interpréter.
    // *
    // * @return Une option contenant la {@link Commande} en cas de succès,
    // * l'option vide en cas d'erreur.
    // */
    // private static Optional<Commande> parseCommande(final String token) {
    // Optional<Commande> result;
    // try {
    // int cmdId = Integer.parseUnsignedInt(token); // may throw
    // NumberFormatException
    // Commande cmd = Commande.valueOfCode(cmdId); // may throw
    // IllegalArgumentException
    // result = Optional.of(cmd);
    // }
    // // NumberFormatException est une sous-classe de IllegalArgumentException
    // catch (IllegalArgumentException ignored) {
    // IHM.afficher("Choix non valide : merci d'entrer un identifiant existant.");
    // result = Optional.empty();
    // }
    // return result;
    // }

    // /**
    // * Interprète un token comme une chaîne de caractère et vérifie que la
    // * chaîne n'existe pas déjà.
    // *
    // * @param token La chaîne de caractère à interpréter.
    // *
    // * @param nomsConnus L'ensemble de chaîne de caractères qui ne sont plus
    // * disponibles.
    // *
    // * @return Une option contenant la chaîne de caractère en cas de succès,
    // * l'option vide en cas d'erreur.
    // */
    // private static Optional<String> parseNouveauNom(final String token, final
    // Set<String> nomsConnus) {
    // Optional<String> result;
    // if (nomsConnus.contains(token)) {
    // IHM.afficher("Le nom existe déjà dans l'application.");
    // result = Optional.empty();
    // } else {
    // result = Optional.of(token);
    // }
    // return result;
    // }

    // /**
    // * Interprète un token comme une chaîne de caractère et vérifie que la
    // * chaîne existe déjà.
    // *
    // * @param token La chaîne de caractère à interpréter.
    // *
    // * @param nomsConnus L'ensemble de chaîne de caractères valides.
    // *
    // * @return Une option contenant la chaîne de caractère en cas de succès,
    // * l'option vide en cas d'erreur.
    // */
    // private static Optional<String> parseNomExistant(final String token, final
    // Set<String> nomsConnus) {
    // Optional<String> result;
    // if (nomsConnus.contains(token)) {
    // result = Optional.of(token);
    // } else {
    // IHM.afficher("Le nom n'existe pas dans l'application.");
    // result = Optional.empty();
    // }
    // return result;
    // }

    // /**
    // * Lit sur l'entrée standard un nom en fonction des noms connus.
    // *
    // * @param nomsConnus L'ensemble des noms connus dans l'application.
    // *
    // * @param nouveau Le nom lu doit-il être un nom connu ou non.
    // * Si {@code true}, le nom lu ne doit pas exister dans
    // * {@code nomConnus}; sinon le nom lu doit exister dans
    // * {@code nomsConnus}.
    // *
    // * @return Le nom saisi par l'utilisa·teur/trice.
    // */
    // private static String lireNom(final Set<String> nomsConnus, final boolean
    // nouveau) {
    // if (nouveau) {
    // if (!nomsConnus.isEmpty()) {
    // IHM.afficher("Les noms suivants ne sont plus disponibles :");
    // IHM.afficher(" " + String.join(", ", nomsConnus) + ".");
    // }
    // return IHM.lireAvecErreurs(token -> IHM.parseNouveauNom(token, nomsConnus));
    // } else {
    // assert !nomsConnus.isEmpty();
    // IHM.afficher("Choisir un nom parmi les noms suivants :");
    // IHM.afficher(" " + String.join(", ", nomsConnus) + ".");
    // return IHM.lireAvecErreurs(token -> IHM.parseNomExistant(token, nomsConnus));
    // }
    // }

    // /**
    // * Lit sur l'entrée standard un nom.
    // *
    // * @return Le nom saisi par l'utilisa·teur/trice.
    // */
    // private static String lireNom() {
    // return IHM.lireNom(Collections.EMPTY_SET, true);
    // }

    // /**
    // * Interprète un token comme une chaîne de caractère et vérifie que la
    // * chaîne est une adresse mél valide.
    // *
    // * @param token La chaîne de caractère à interpréter.
    // *
    // * @return Une option contenant la chaîne de caractère si token est une
    // * adresse mél valide, l'option vide en cas d'erreur.
    // */
    // private static Optional<String> parseEmail(final String token) {
    // Optional<String> result;
    // EmailValidator validator = EmailValidator.getInstance(false, false);
    // if (validator.isValid(token)) {
    // result = Optional.of(token.toLowerCase());
    // } else {
    // IHM.afficher("L'adresse mél n'est pas valide.");
    // result = Optional.empty();
    // }
    // return result;
    // }

    // /**
    // * Lit une adresse mél.
    // *
    // * @return L'adresse mél saisie par l'utilisa·teur/trice.
    // */
    // private static String lireEmail() {
    // IHM.afficher("Saisir une adresse mél :");
    // return IHM.lireAvecErreurs(IHM::parseEmail);
    // }

    // /**
    // * Interprète un token comme une {@link LocalDate}.
    // *
    // * @param token La chaîne de caractère à interpréter.
    // *
    // * @param apres Si l'option contient une valeur, la date lue doit être
    // * ultérieure à {@code apres}; sinon aucune contrainte n'est présente.
    // *
    // * @return Une option contenant la {@link LocalDate} en cas de succès,
    // * l'option vide en cas d'erreur.
    // */
    // private static Optional<LocalDate> parseDate(final String token, final
    // Optional<LocalDate> apres) {
    // Optional<LocalDate> result;
    // try {
    // LocalDate date = LocalDate.parse(token);
    // if (apres.isPresent() && apres.get().isAfter(date)) {
    // // `apres.get()` est garanti de fonctionner grâce à la garde
    // `apres.isPresent()`
    // IHM.afficher("La date saisie n'est pas ultérieure à " +
    // apres.get().toString());
    // result = Optional.empty();
    // } else {
    // result = Optional.of(date);
    // }
    // }
    // catch (DateTimeParseException ignored) {
    // IHM.afficher("La date saisie n'est pas valide.");
    // result = Optional.empty();
    // }
    // return result;
    // }

    // /**
    // * Lit une date au format ISO-8601.
    // *
    // * @param apres Si l'option contient une valeur, la date lue doit être
    // * ultérieure à {@code apres}; sinon aucune contrainte n'est présente.
    // *
    // * @see java.time.format.DateTimeFormatter#ISO_LOCAL_DATE
    // *
    // * @return La date saisie par l'utilisa·teur/trice.
    // */
    // private static LocalDate lireDate(final Optional<LocalDate> apres) {
    // IHM.afficher("Saisir une date au format ISO-8601 :");
    // apres.ifPresent(date -> IHM.afficher("La date doit être ultérieure à " +
    // date.toString())
    // );
    // return IHM.lireAvecErreurs(token -> IHM.parseDate(token, apres));
    // }

    // /**
    // * Lit une date au format ISO-8601.
    // * <p>
    // * Alias pour {@code lireDate(Optional.empty())}.
    // *
    // * @return La date saisie par l'utilisa·teur/trice.
    // *
    // * @see #lireDate(java.util.Optional)
    // */
    // private static LocalDate lireDate() {
    // return IHM.lireDate(Optional.empty());
    // }

    // /**
    // * Lit une date au format ISO-8601.
    // * <p>
    // * Alias pour {@code lireDate(Optional.of(apres))}.
    // *
    // * @param apres La date saisie doit être ultérieure à {@code apres}.
    // *
    // * @return La date saisie par l'utilisa·teur/trice.
    // *
    // * @see #lireDate(java.util.Optional)
    // */
    // private static LocalDate lireDate(final LocalDate apres) {
    // return IHM.lireDate(Optional.of(apres));
    // }
    // */
    
    private Controleur controleur;
    
    public IHM(Controleur controleur)
    {
        this.controleur = controleur;
    }

    public void afficherCaracteristiquesTypeCommunication(TypeCommunication typeCommunication) {
        System.out.println(typeCommunication.toString());

    }

    public void afficherCaracteristiquesTypesCommunication(Map<String, TypeCommunication> typesCommunication) {
        for (Map.Entry<String, TypeCommunication> typC : typesCommunication.entrySet()) {
            System.out.println(typC.getValue().toString());
        }
    }

    public void afficherCommunications(Map<Integer, Communication> communications) {
        for (Map.Entry<Integer, Communication> Com : communications.entrySet()) {
            System.out.println(Com.getValue().toString());
        }
    }

    public void afficherInformationsConf(Conference conference) {
        System.out.println(conference.toString());
    }

    public void afficherInfosCommunication(Communication comm) {
        System.out.println(comm.toString());
    }

    public void afficherInfosSessions(Map<String, Session> s) {
        for (Map.Entry<String, Session> Str : s.entrySet()) {
            System.out.println(Str.getValue().toString()); 
        }
    }

    public void afficherInfosSession(Session session) {
        System.out.println(session.toString());
    }

    public void afficherLibelleSessions(Map<String, Session> session) {
        for (Map.Entry<String, Session> str : session.entrySet()) {
            System.out.println(str.getValue().getIntituleSession());
        }
    }

    public int afficherMenuCommunications() {
        System.out.println("Menu Gerer Communications");
        System.out.println("1. Créer une Communication");
        System.out.println("2. Modifier une Communication");
        System.out.println("3. Supprimer une Communication");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        while (choix < 0 || choix > 3) {
            System.out.println("Choix inconnu, veuillez choisir parmi ces options :");
            System.out.println("1. Créer une Communication");
            System.out.println("2. Modifier une Communication");
            System.out.println("3. Supprimer une Communication");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
        }
        return choix;
    }

    public int afficherMenuComptes() {
        System.out.println("Menu Gérer Comptes");
        System.out.println("1. Donner Droits Admin");
        System.out.println("2. Créer Compte GenConf");
        System.out.println("3. Donner Droits Inscrit");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        while (choix < 0 || choix > 3) {
            System.out.println("Choix inconnu, veuillez choisir parmi ces options :");
            System.out.println("Menu Gérer Comptes");
            System.out.println("1. Donner Droits Admin");
            System.out.println("2. Créer Compte GenConf");
            System.out.println("3. Donner Droits Inscrit");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
        }
        return choix;
    }

    public int afficherMenuGeneral() {
        System.out.println("Menu Général");
        System.out.println("1. Gérer Session");
        System.out.println("2. Gérer Communications");
        System.out.println("3. Gérer Types Communication");
        System.out.println("4. Gérer informations Conférence");
        System.out.println("5. Créer Track");
        System.out.println("6. Gérer Compte");
        System.out.println("7. Prévisualiser Conférence");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        while (choix < 0 || choix > 7) {
            System.out.println("Choix inconnu, veuillez choisir parmi ces options :");
            System.out.println("Menu Général");
            System.out.println("1. Gérer Session");
            System.out.println("2. Gérer Communications");
            System.out.println("3. Gérer Types Communication");
            System.out.println("4. Gérer informations Conférence");
            System.out.println("5. Créer Track");
            System.out.println("6. Gérer Compte");
            System.out.println("7. Prévisualiser Conférence");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
        }
        return choix;
    }

    public int afficherMenuInfosConference() {
        System.out.println("Menu Informations Conférence");
        System.out.println("1. Modifier métadonnées conférence");
        System.out.println("2. Consulter Infos Générales Conférence");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        while (choix < 0 || choix > 2) {
            System.out.println("Choix inconnu, veuillez choisir parmi ces options :");
            System.out.println("Menu Informations Conférence");
            System.out.println("1. Modifier métadonnées conférence");
            System.out.println("2. Consulter Infos Générales Conférence");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
        }
        return choix;
    }

    public int afficherMenuSessions() {
        System.out.println("Menu Gérer Sessions");
        System.out.println("1. Créer Session");
        System.out.println("2. Modifier Session");
        System.out.println("3. Supprimer Session");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        while (choix < 0 || choix > 3) {
            System.out.println("Choix inconnu, veuillez choisir parmi ces options :");
            System.out.println("Menu Gérer Sessions");
            System.out.println("1. Créer Session");
            System.out.println("2. Modifier Session");
            System.out.println("3. Supprimer Session");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
        }
        return choix;
    }

    public int afficherMenuTracks() {
        System.out.println("Menu Gérer Tracks");
        System.out.println("1. Créer Tracks");
        System.out.println("2. Supprimer Tracks");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        while (choix < 0 || choix > 2) {
            System.out.println("Choix inconnu, veuillez choisir parmi ces options :");
            System.out.println("Menu Gérer Tracks");
            System.out.println("1. Créer Tracks");
            System.out.println("2. Supprimer Tracks");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
        }
        return choix;
    }

    public int afficherMenuTypesCommunication() {
        System.out.println("Menu Gérer Type Communications");
        System.out.println("1. Créer TypeCommunication");
        System.out.println("2. Modifier un Type Communication");
        System.out.println("3. Supprimer un Type Communication");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        while (choix < 0 || choix > 3) {
            System.out.println("Choix inconnu, veuillez choisir parmi ces options :");
            System.out.println("Menu Gérer Type Communications");
            System.out.println("1. Créer TypeCommunication");
            System.out.println("2. Modifier un Type Communication");
            System.out.println("3. Supprimer un Type Communication");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
        }
        return choix;
    }

    public void afficherNomConference(Conference conference) {
        System.out.println("Nom Conférence : " + conference.getNomConf());
    }

    public void afficherNomConferences(Map<String, Conference> conferences) {
        for (Map.Entry<String, Conference> Str : conferences.entrySet()) {
            System.out.println(Str.getValue());
        }
    }

    public void afficherTitreCommunication(Communication communication) {
        System.out.println("titre : " + communication.getTitre());
        System.out.println("numero : " + communication.getNumero());
    }

    public void afficherUtilisateurs(Map<String, Utilisateur> utilisateurs) {
        for (Map.Entry<String, Utilisateur> Str : utilisateurs.entrySet()) {
            System.out.println(Str.getValue());
        }
    }

    public boolean demanderARemplacerSessionDeCommunication() {
        System.out.println("Cette communication est déja prévue pour une session, voulez-vous la remplacer?");
        System.out.println("1. Oui");
        System.out.println("2. Non");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        while (choix < 0 || choix > 2) {
            System.out.println("Choix inconnu, veuillez choisir parmi ces options :");
            System.out.println("Cette communication est déja prévue pour une session, voulez-vous la remplacer?");
            System.out.println("1. Oui");
            System.out.println("2. Non");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
        }
        if (choix == 1) {
            return true;
        } else {
            return false;
        }
    }

    public String demanderNomSession() {
        System.out.print("Nom session : ");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        return str;
    }

    public boolean demanderSiModifierCommunication() {
        System.out.println("Voulez-vous modifier la communication ?");
        System.out.println("1. Oui");
        System.out.println("2. Non");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        while (choix < 0 || choix > 2) {
            System.out.println("Choix inconnu, veuillez choisir parmi ces options :");
            System.out.println("Voulez-vous modifier la communication ?");
            System.out.println("1. Oui");
            System.out.println("2. Non");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
        }
        if (choix == 1) {
            return true;
        } else {
            return false;
        }

    }

    public boolean demanderSiPrevisualisationSession() {
        System.out.println("Voulez-vous prévisualiser une session ou quitter ?");
        System.out.println("1. Oui");
        System.out.println("2. Non");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        while (choix < 0 || choix > 2) {
            System.out.println("Choix inconnu, veuillez choisir parmi ces options :");
            System.out.println("Voulez vous modifier la communication ?");
            System.out.println("1. Oui");
            System.out.println("2. Non");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
        }
        if (choix == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void notifier(String notification) {
        System.out.println(notification);
    }

    public int optionsModifierCommunication() {
        System.out.println("Options modifiables : ");
        System.out.println("1. Titre");
        System.out.println("2. Auteurs");
        System.out.println("3. lien vers PDF");
        System.out.println("4. Lien Video");
        System.out.println("5. Date");
        System.out.println("6. Horaires");
        System.out.println("7. Type Communication");
        System.out.println("8. Orateurs");
        System.out.println("9. Correspondant");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        while (choix < 0 || choix > 9) {
            System.out.println("Choix inconnu, veuillez choisir parmi ces options :");
            System.out.println("Options modifiables : ");
            System.out.println("1. Titre");
            System.out.println("2. Auteurs");
            System.out.println("3. lien vers PDF");
            System.out.println("4. Lien Video");
            System.out.println("5. Date");
            System.out.println("6. Horaires");
            System.out.println("7. Type Communication");
            System.out.println("8. Orateurs");
            System.out.println("9. Correspondant");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
        }
        return choix;
    }

    public int optionsModifierConference() {
        System.out.println("Options modifiables : ");
        System.out.println("1. Nom de la Conférence");
        System.out.println("2. Thème");
        System.out.println("3. Lieu");
        System.out.println("4. Date de Début");
        System.out.println("5. Date de Fin");
        System.out.println("6. Date T1");
        System.out.println("7. Date T2");
        System.out.println("8. Date T3");
        System.out.println("9. Date T4");
        System.out.println("10. Modifier Toutes les Dates");
        System.out.println("11. Texte d'Accueil");
        System.out.println("12. Logo");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        while (choix < 0 || choix > 12) {
            System.out.println("Choix inconnu, veuillez choisir parmi ces options :");
            System.out.println("Options modifiables : ");
            System.out.println("1. Nom de la Conférence");
            System.out.println("2. Thème");
            System.out.println("3. Lieu");
            System.out.println("4. Date de Début");
            System.out.println("5. Date de Fin");
            System.out.println("6. Date T1");
            System.out.println("7. Date T2");
            System.out.println("8. Date T3");
            System.out.println("9. Date T4");
            System.out.println("10. Modifier Toutes les Dates");
            System.out.println("11. Texte d'Accueil");
            System.out.println("12. Logo");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
        }
        return choix;
    }

    public int optionsModifierSession() {
        System.out.println("Options Modifiables : ");
        System.out.println("1. Animateurs");
        System.out.println("2. Intitule");
        System.out.println("3. Type");
        System.out.println("4. Date");
        System.out.println("5. Horaires");
        System.out.println("6. Video Associée");
        System.out.println("7. Salle");
        System.out.println("8. Ajouter une Communication");
        System.out.println("9. Supprimer une Communication");
        System.out.println("10. Tracks");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        while (choix < 0 || choix > 9) {
            System.out.println("Choix inconnu, veuillez choisir parmi ces options :");
            System.out.println("Options Modifiables : ");
            System.out.println("1. Animateurs");
            System.out.println("2. Intitule");
            System.out.println("3. Type");
            System.out.println("4. Date");
            System.out.println("5. Horaires");
            System.out.println("6. Video Associée");
            System.out.println("7. Salle");
            System.out.println("8. Ajouter une Communication");
            System.out.println("9. Supprimer une Communication");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
        }
        return choix;
    }

    public HashSet<String[]> saisirAuteursCommunication() {
    	HashSet<String[]> auteurs= new HashSet<>();
        boolean continuer=true;
        while (continuer) {
            System.out.println("Entrez le nom, prenom, mail : ");
            auteurs.add(saisirUtilisateur());
            System.out.println("Souhaitez vous continuer ? ");
            System.out.println("1. Oui");
            System.out.println("2. Non");
            System.out.print("Votre choix : ");
            Scanner scanner= new Scanner(System.in);
            int choix= scanner.nextInt();
            while(choix < 1 || choix > 2){
                System.out.println("Choix inconnu, veuillez choisir parmi ces options :"); 
                System.out.println("Souhaitez vous continuer ?");
                System.out.println("1. Oui");
                System.out.println("2. Non");
                System.out.print("Votre choix : ");
                choix= scanner.nextInt();
            }
            if(choix==1){
                continuer=true;
            }else{
                continuer=false;
            }
        }
        return auteurs;
    }

    public String saisirCouleurtrack() {
        System.out.print("Entrez la couleur du track : ");
        Scanner scanner = new Scanner(System.in);
        String couleur = scanner.nextLine();
        return couleur;
    }

    public boolean saisirCreationConference(Map<String, Conference> conferences) {
        System.out.println("Voulez-vous créer une conférence ?");
        System.out.println("1. Oui");
        System.out.println("2. Non");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        while (choix < 0 || choix > 2) {
            System.out.println("Choix inconnu, veuillez choisir parmi ces options : ");
            System.out.println("Voulez-vous créer une conférence ?");
            System.out.println("1. Oui");
            System.out.println("2. Non");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
        }
        if (choix == 1) {
            return true;
        } else {
            return false;
        }
    }

    public String saisirDate() {
        System.out.print("Veuillez saisir la date (YYYY-MM-DD) : ");
        Scanner scanner = new Scanner(System.in);
        String date = scanner.nextLine();
        return date;
    }

    public String saisirHeure() {
        System.out.print("Saissiez l'heure (hh:mm:ss) : ");
        Scanner scanner = new Scanner(System.in);
        String heure=scanner.nextLine();
        return heure;
    }

    public String[] saisirInfosCommunication(TypeCommunication typeCommunication) {
        String str[]=new String[7];
        Scanner scanner = new Scanner(System.in);
        System.out.print("Saisir le titre : ");
        str[0] = scanner.nextLine();
        System.out.print("Saisir les auteurs : ");
        str[1] = scanner.nextLine();
        System.out.print("Saisir la Date (YYYY-MM-DD) : ");
        str[2] = scanner.nextLine();
        System.out.print("Saisir l'heure de début (hh:mm:ss) : ");
        str[3] = scanner.nextLine();
        System.out.print("Saisir l'heure de fin (hh:mm:ss) : ");
        str[4] = scanner.nextLine();
        if (typeCommunication.getLienVideoObligatoire()) {
            System.out.print("Saisir le lien de la vidéo : ");
            str[5] = scanner.nextLine();
        }
        if (typeCommunication.getLienPDFObligatoire()) {
            System.out.print("Saisir le lien PDF : ");
            str[6] = scanner.nextLine();
        }
        return str;
    }

    public int saisirInfosTypeCommunication() {
        System.out.println("Choisir si des documents sont obligatoires : ");
        System.out.println("1. Vidéo obligatoire");
        System.out.println("2. PDF obligatoire");
        System.out.println("3. Vidéo et PDF obligatoire");
        System.out.println("4. Pas de vidéo ni de PDF obligatoire obligatoire");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        while (choix < 0 || choix > 4) {
            System.out.println("Choix inconnu, veuillez choisir parmi ces options :");
            System.out.println("Choisir si des documents sont obligatoires");
            System.out.println("1. Vidéo obligatoire");
            System.out.println("2. PDF obligatoire");
            System.out.println("3. Vidéo et PDF obligatoire");
            System.out.println("4. pas de vidéo ni de PDF obligatoire obligatoire");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
        }
        return choix;
    }

    public String saisirLibelleTypeCommunication() {
        System.out.print("Saisir le libéllé de Type de Communication : ");
        Scanner scanner = new Scanner(System.in);
        String libelle = scanner.nextLine();
        return libelle;
    }

    public String saisirLienPDF() {
        System.out.print("Veuillez saisir le lien PDF : ");
        Scanner scanner = new Scanner(System.in);
        String lien = scanner.nextLine();
        return lien;
    }

    public String saisirLienVideo() {
        System.out.print("Veuillez saisir le lien Vidéo : ");
        Scanner scanner = new Scanner(System.in);
        String lien = scanner.nextLine();
        return lien;
    }

    public String saisirLieuConference() {
        System.out.print("Veuillez saisir le lieu : ");
        Scanner scanner = new Scanner(System.in);
        String lieu = scanner.nextLine();
        return lieu;
    }

    public String saisirLogo() {
        System.out.print("Veuillez saisir le lien du logo : ");
        Scanner scanner = new Scanner(System.in);
        String logo = scanner.nextLine();
        return logo;
    }

    public String saisirNomConference(Map<String, Conference> conferences) {
        System.out.println("Liste des conférences existantes : ");
        for (Map.Entry<String, Conference> Str : conferences.entrySet()) {
            System.out.println(Str.getValue().getNomConf());
        }
        return saisirNomConference();
    }
    
    public String saisirNomConference()
    {
        System.out.print("Entrez le nom de la conférence : ");
        Scanner scanner = new Scanner(System.in);
        String nom = scanner.nextLine();
        return nom;
    }

    public String saisirNomTypeCommunication() {
        System.out.print("Saisir le libelle du Type Communication : ");
        Scanner scanner = new Scanner(System.in);
        String nom = scanner.nextLine();
        return nom;
    }

    public String saisirNouveauTypeSession() {
        System.out.print("Entrez le nouveau type pour la session : ");
        Scanner scanner = new Scanner(System.in);
        String nom = scanner.nextLine();
        return nom;
    }

    public String[] saisirNouvellesSession(Conference conf) {
        String str[]=new String[7];
        Scanner scanner = new Scanner(System.in);
        System.out.print("Saisir nom de la Session : ");
        str[0] = scanner.nextLine();
        System.out.print("Saisir le type de Session : ");
        str[1] = scanner.nextLine();
        System.out.print("Saisir la date (YYYY-MM-DD) : ");
        str[2] = scanner.nextLine();
        System.out.print("Saisir l'heure de début (hh:mm:ss) : ");
        str[3] = scanner.nextLine();
        System.out.print("Saisir l'heure de fin (hh:mm:ss) : ");
        str[4] = scanner.nextLine();
        System.out.print("Saisir le lien de la vidéo : ");
        str[5] = scanner.nextLine();
        System.out.print("Saisir la salle : ");
        str[6] = scanner.nextLine();
        return str;
    }

    public int saisirNumeroCommunication() {
        System.out.print("Saisir le numéro de la Communication : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        return choix;
    }

    public int saisirNumeroCommunication(Map<Integer, Communication> communications) {
        for (Map.Entry<Integer, Communication> Str : communications.entrySet()) {
            System.out.println(Str.getValue().getNumero());
            System.out.println(Str.getValue().getTitre());
        }
        System.out.print(("Saisir le numéro de Communication à modifier : "));
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        return choix;
    }

    public int saisirOptionModifierTypeCommunication() {
        System.out.println("Options modifiables pour Type Communication");
        System.out.println("1. Libelle");
        System.out.println("2. Vidéo Obligatoire");
        System.out.println("3. PDF Obligatoire");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        while (choix < 0 || choix > 3) {
            System.out.println("Choix inconnu, veuillez choisir parmi ces options :");
            System.out.println("1. Libelle");
            System.out.println("2. Vidéo Obligatoire");
            System.out.println("3. PDF Obligatoire");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
        }
        return choix;
    }

    public int saisirOptionPrevisualiserSession() {
        System.out.println("Options de Session");
        System.out.println("1. Prévisualiser une Communication");
        System.out.println("2. Modifier la Session");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        while (choix < 0 || choix > 2) {
            System.out.println("Choix inconnu, veuillez choisir parmi ces options :");
            System.out.println("Options de Session");
            System.out.println("1. Prévisualiser une Communication");
            System.out.println("2. Modifier la Session");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
        }
        return choix;
    }

    public String saisirSalle() {
        System.out.print("Entrez la salle : ");
        Scanner scanner = new Scanner(System.in);
        String nom = scanner.nextLine();
        return nom;
    }

    public boolean saisirSiPDFObligatoire() {
        System.out.println("Le PDF est-il obligatoire ?");
        System.out.println("1.Oui");
        System.out.println("2.Non");
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        while (choix < 1 || choix > 2) {
            System.out.println("Choix inconnu, veuillez choisir parmi ces 3 options : ");
            System.out.println("Le PDF est-il obligatoire ?");
            System.out.println("1.Oui");
            System.out.println("2.Non");
            System.out.print("Votre choix : ");
        }
        if (choix == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean saisirSiVideoObligatoire() {
        System.out.println("La vidéo est-elle obligatoire ?");
        System.out.println("1.Oui");
        System.out.println("2.Non");
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        while (choix < 1 || choix > 2) {
            System.out.println("Choix inconnu, veuillez choisir parmi ces 3 options : ");
            System.out.println("Le PDF est-il obligatoire ?");
            System.out.println("1.Oui");
            System.out.println("2.Non");
            System.out.print("Votre choix : ");
        }
        if (choix == 1) {
            return true;
        } else {
            return false;
        }
    }

    public int saisirSupprimerOuAjouterUtilisateur(Map<String, Utilisateur> utilisateurs) {
        System.out.println("Existants : ");
        for (Map.Entry<String, Utilisateur> str : utilisateurs.entrySet()) {
            System.out.println(str.getValue().toString());
        }
        System.out.println("Voulez-vous ajouter ou supprimer un animateur ?");
        System.out.println("1. Ajouter");
        System.out.println("2. Supprimer");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        while (choix < 0 || choix > 2) {
        	System.out.println("Choix inconnu, veuillez choisir parmi ces 3 options : ");
        	System.out.println("Voulez-vous ajouter ou supprimer un animateur ?");
            System.out.println("1. Ajouter");
            System.out.println("2. Supprimer");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
        }
        return choix;
    }
    
    public int saisirSupprimerOuAjouterUtilisateur(HashSet<String[]> auteurs) {
        System.out.println("Existants : ");
        for(String[] auteur : auteurs)
        {
        	System.out.print(auteur[0] + ", ");
	        System.out.print(auteur[1] + ", ");
	        System.out.println(auteur[2]);
        }
        System.out.println("Voulez-vous ajouter ou supprimer un auteur ?");
        System.out.println("1. Ajouter");
        System.out.println("2. Supprimer");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        while (choix < 0 || choix > 2) {
        	System.out.println("Choix inconnu, veuillez choisir parmi ces 3 options : ");
        	System.out.println("Voulez-vous ajouter ou supprimer un auteur ?");
            System.out.println("1. Ajouter");
            System.out.println("2. Supprimer");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
        }
        return choix;
    }

    public int saisirSupprimerOuAjouterUtilisateur(Set<String> utilisateurs) {
        System.out.println("Existants : ");
        for (String str : utilisateurs) {
            System.out.println(str);
        }
        System.out.println("Voulez-vous ajouter ou supprimer un auteur ?");
        System.out.println("1. Ajouter");
        System.out.println("2. Supprimer");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        while (choix < 0 || choix > 2) {
        	System.out.println("Choix inconnu, veuillez choisir parmi ces 3 options : ");
        	System.out.println("Voulez-vous ajouter ou supprimer un auteur ?");
            System.out.println("1. Ajouter");
            System.out.println("2. Supprimer");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
        }
        return choix;
    }

    public String saisirTexteAccueilConference() {
        System.out.print("Entrez le texte d'accueil : ");
        Scanner scanner = new Scanner(System.in);
        String texte = scanner.nextLine();
        return texte;
    }

    public String saisirThemeConference() {
        System.out.print("Veuillez entrer le thème : ");
        Scanner scanner = new Scanner(System.in);
        String theme = scanner.nextLine();
        return theme;
    }

    public String saisirLibelleTrack(Map<String, Track> tracks) {
        for (Map.Entry<String, Track> str :tracks.entrySet()) {
            System.out.println(str.getValue().toString());
        }
        System.out.print("Entrez le libelle du track : ");
        Scanner scanner = new Scanner(System.in);
        String libelle = scanner.nextLine();
        return libelle;
    }

    
    public String saisirLibelleTrack()
    {
        Scanner sc = new Scanner(System.in);
        String libelle;
        System.out.print("Veuillez entrer le libelle du track : ");
        libelle = sc.nextLine();
        return libelle;
    }
    
    public int saisirAjouterOuSupprimerTrack(Map<String, Track> tracks)    
    {
        int choix = 0;
        System.out.println("Tracks de la session : ");
        
        for (Map.Entry<String, Track> str :tracks.entrySet()) {
            System.out.println(str.getValue().toString());
        }
	    Scanner scanner = new Scanner(System.in);
        do
        {
            System.out.println("Options : ");
            System.out.println("1) Ajouter Track");
            System.out.println("2) Supprimer Track");
            System.out.println("0) Quitter");
            System.out.println("Votre choix : ");
            
	    choix=scanner.nextInt();
        }while(choix < 0 || choix > 2);
        
        return choix;
    }

    public String[] saisirUtilisateur() {
        String str[]=new String[3];
        Scanner scanner = new Scanner(System.in);
        System.out.print("Saisir le nom : ");
        str[0] = scanner.nextLine();
        System.out.print("Saisir le prénom : ");
        str[1] = scanner.nextLine();
        System.out.print("Saisir le mail : ");
        str[2] = scanner.nextLine();
        return str;
    }

    public String selectionnerSession(Map<String, Session> sessions) {
        for (Map.Entry<String, Session> str : sessions.entrySet()) {
            System.out.println(str.getValue().toString());
        }
        System.out.print("Veuillez saisir l'intitulé de la session : ");
        Scanner scanner = new Scanner(System.in);
        String intitule = scanner.nextLine();
        return intitule;
    }
    
    public String saisirTitreCommunication()
    {
    	System.out.print("Veuillez saisir le titre de la communication : ");
    	Scanner sc = new Scanner(System.in);
    	return sc.nextLine();
    }
    


}
