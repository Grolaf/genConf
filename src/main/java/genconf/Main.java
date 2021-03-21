package genconf;

import genconf.controleur.Controleur;
import genconf.modele.GenConf;
import genconf.util.Persisteur;
import java.io.IOException;


public class Main {

    public static final int EXIT_ERR_LOAD = 2;
    public static final int EXIT_ERR_SAVE = 3;

    public static void main(String[] args) {
        GenConf genconf = null;

        try {
            genconf = Persisteur.lireEtat();
        }
        catch (ClassNotFoundException | IOException ignored) {
            System.err.println("Erreur irrécupérable pendant le chargement de l'état : fin d'exécution !");
            System.err.flush();
            System.exit(Main.EXIT_ERR_LOAD);
        }

        Controleur controleur = new Controleur(genconf);
        // `Controleur.demarrer` garde le contrôle de l'exécution tant que
        // l'utilisa·teur/trice n'a pas saisi la commande QUITTER.
        controleur.demarrer();

        try {
            Persisteur.sauverEtat(genconf);
        }
        catch (IOException ignored) {
            System.err.println("Erreur irrécupérable pendant la sauvegarde de l'état : fin d'exécution !");
            System.err.flush();
            System.exit(Main.EXIT_ERR_SAVE);
        }
    }
}
