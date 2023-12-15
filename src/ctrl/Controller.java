package ctrl;

//region NE PAS TOUCHER AU CONTRÔLEUR QUI EST FOURNI ET FONCTIONNEL !

import models.Bateau;
import models.Decalage;
import models.EtatSurfaceMaritime;
import models.SurfaceDeJeu;
import services.BateauxDeplacement;
import services.BateauxNouveaux;
import services.BateauxPlacement;
import views.View;

public class Controller {

    private final View view;
    private final SurfaceDeJeu surfaceDeJeu;
    private boolean partieEnCours;
    private boolean placementEnCours;
    private Bateau bateauAPlacer;
    private final BateauxNouveaux serviceBateaux;
    private final BateauxPlacement servicePlacement;
    private int nbreTirs;
    private int nbreTouchesUtilisateur;
    private int nbreTouchesOrdinateur;

    public Controller() {
        partieEnCours = false;
        placementEnCours = false;
        bateauAPlacer = null;
        nbreTirs = 0;
        nbreTouchesOrdinateur = 0;
        nbreTouchesUtilisateur = 0;
        serviceBateaux = new BateauxNouveaux();
        servicePlacement = new BateauxPlacement();
        surfaceDeJeu = new SurfaceDeJeu();
        view = new View(this);
    }

    public void start() {
        view.start();
    }

    public SurfaceDeJeu getSurfaceDeJeu() {
        return surfaceDeJeu;
    }

    public void actionRestart() {
        partieEnCours = false;
        placementEnCours = true;
        bateauAPlacer = null;
        nbreTirs = 0;
        nbreTouchesOrdinateur = 0;
        nbreTouchesUtilisateur = 0;
        surfaceDeJeu.reinitialiser();
        view.mettreAJourMenuBateauxAPlacer(
                BateauxPlacement.BATEAUX_INITIAL_NBRE_SOUS_MARINS,
                BateauxPlacement.BATEAUX_INITIAL_NBRE_CROISEURS,
                BateauxPlacement.BATEAUX_INITIAL_NBRE_TORPILLEURS,
                BateauxPlacement.BATEAUX_INITIAL_NBRE_CUIRASSE,
                BateauxPlacement.BATEAUX_INITIAL_NBRE_PORTE_AVIONS);
        view.afficherModeActuel(partieEnCours, placementEnCours, bateauAPlacer != null, false);
        view.rafraichirSurfaceDeJeu();
        view.afficherStatistiquesJoueur(0, 0, 0, surfaceDeJeu.nbreBateaux());
        view.afficherStatistiquesOrdi(0, 0, 0, surfaceDeJeu.nbreBateaux());
    }

    public void actionRestartAuto() {
        partieEnCours = false;
        placementEnCours = false;
        bateauAPlacer = null;
        nbreTirs = 0;
        nbreTouchesOrdinateur = 0;
        nbreTouchesUtilisateur = 0;
        surfaceDeJeu.reinitialiser();

        Bateau[] bateaux = servicePlacement.genererBateauxAleatoires();
        for (int i = 0; i < bateaux.length; i++) {
            surfaceDeJeu.ajouterBateauJoueur(bateaux[i]);
        }
        view.mettreAJourMenuBateauxAPlacer(
                0,
                0,
                0,
                0,
                0);
        view.afficherModeActuel(partieEnCours, placementEnCours, bateauAPlacer != null, false);
        view.rafraichirSurfaceDeJeu();
        view.afficherStatistiquesJoueur(0, 0, 0, surfaceDeJeu.nbreBateaux());
        view.afficherStatistiquesOrdi(0, 0, 0, surfaceDeJeu.nbreBateaux());
    }

    public void actionStart() {
        Bateau[] bateauxOrdinateur = servicePlacement.genererBateauxAleatoires();
        surfaceDeJeu.definirBateauxOrdi(bateauxOrdinateur);
        partieEnCours = true;
        placementEnCours = false;
        bateauAPlacer = null;
        view.afficherMessageInformation("L'ordinateur a placé ses bateaux, la partie commence ! A vous de jouer !");

        view.afficherModeActuel(partieEnCours, placementEnCours, bateauAPlacer != null, false);
        view.rafraichirSurfaceDeJeu();
        view.afficherStatistiquesJoueur(0, 0, 0, surfaceDeJeu.nbreBateaux());
        view.afficherStatistiquesOrdi(0, 0, 0, surfaceDeJeu.nbreBateaux());
    }

    public void actionQuitter() {
        // On pourrait sauvegarder la position/taille de la fenêtre
        // On pourrait aussi savegarder l'état actuel afin de pouvoir continuer la
        // partie
    }

    private void bougerBateau(Decalage decalage) {
        boolean bateauABouge = BateauxDeplacement.bougerBateau(bateauAPlacer, decalage);
        if (bateauABouge) {
            boolean touches = servicePlacement.marquerTouchesBateauxSuperposes(surfaceDeJeu.getBateauxJoueur());
            view.rafraichirSurfaceDeJeu();
            view.afficherModeActuel(partieEnCours, placementEnCours, bateauAPlacer != null, touches);
        } else {
            view.afficherMessageErreur("Déplacement impossible ! Le bateau sortirait de la surface de jeu !");
        }
    }

    public void actionBateauBougerHaut() {
        bougerBateau(new Decalage(0, -1));
    }

    public void actionBateauBougerBas() {
        bougerBateau(new Decalage(0, 1));
    }

    public void actionBateauBougerGauche() {
        bougerBateau(new Decalage(-1, 0));
    }

    public void actionBateauBougerDroite() {
        bougerBateau(new Decalage(1, 0));
    }

    public void actionBateauBougerRotation() {
        boolean bateauATourne = BateauxDeplacement.rotationBateau(bateauAPlacer);
        if (bateauATourne) {
            boolean touches = servicePlacement.marquerTouchesBateauxSuperposes(surfaceDeJeu.getBateauxJoueur());
            view.rafraichirSurfaceDeJeu();
            view.afficherModeActuel(partieEnCours, placementEnCours, bateauAPlacer != null, touches);
        } else {
            view.afficherMessageErreur("Rotation impossible ! Le bateau sortirait de la surface de jeu !");
        }
    }

    public void actionBateauValiderPosition() {
        bateauAPlacer = null;

        int nbreBateauxRestantsAPlacer = view.getNbreBateauxRestantsAPlacer();
        placementEnCours = (nbreBateauxRestantsAPlacer > 0);

        view.rafraichirSurfaceDeJeu();
        view.afficherModeActuel(partieEnCours, placementEnCours, bateauAPlacer != null, false);
    }

    private void placerNouveauBateau(Bateau bateau) {
        bateauAPlacer = bateau;
        surfaceDeJeu.ajouterBateauJoueur(bateauAPlacer);
        boolean touches = servicePlacement.marquerTouchesBateauxSuperposes(surfaceDeJeu.getBateauxJoueur());
        view.rafraichirSurfaceDeJeu();
        view.afficherModeActuel(partieEnCours, placementEnCours, bateauAPlacer != null, touches);
    }

    public void actionBateauNouveauPorteAvion() {
        placerNouveauBateau(serviceBateaux.creerPorteAvions());
    }

    public void actionBateauNouveauCroiseur() {
        placerNouveauBateau(serviceBateaux.creerCroiseur());
    }

    public void actionBateauNouveauTorpilleur() {
        placerNouveauBateau(serviceBateaux.creerTorpilleur());
    }

    public void actionBateauNouveauCuirasse() {
        placerNouveauBateau(serviceBateaux.creerCuirasse());
    }

    public void actionBateauNouveauSousMarin() {
        placerNouveauBateau(serviceBateaux.creerSousMarin());
    }

    public void actionCelluleCliquee(int gridPosX, int gridPosY) {

        // La partie est-elle en cours ? Sinon ignorer le clic...
        if (partieEnCours) {

            // Marquer la région pour qu'on sache qu'on a déjà tiré à cet endroit !
            switch (surfaceDeJeu.getEtatDeSurfaceMaritime(gridPosX, gridPosY)) {
                case INTACT:
                    surfaceDeJeu.setEtatDeSurfaceMaritime(gridPosX, gridPosY, EtatSurfaceMaritime.JOUEUR_A_TIRE);
                    break;
                case ORDI_A_TIRE:
                    surfaceDeJeu.setEtatDeSurfaceMaritime(gridPosX, gridPosY,
                            EtatSurfaceMaritime.JOUEUR_ET_ORDI_ONT_TIRE);
                    break;

                case JOUEUR_A_TIRE:
                case JOUEUR_ET_ORDI_ONT_TIRE:
                    view.afficherMessageErreur("Vous aviez déjà tiré à cet endroit !!!");
                    break;
            }

            // Déterminer s'il y a un bateau à cet endroit... et si oui changer son statut
            boolean touche = surfaceDeJeu.joueurTire(gridPosX, gridPosY);

            // Mettre à jour l'affichage suite à ce tir
            view.rafraichirSurfaceDeJeu();

            // Mettre à jour les statistiques
            nbreTirs++;
            if (touche) {
                nbreTouchesUtilisateur++;
            }
            int nbreBateauxCoulesOrdi = surfaceDeJeu.nbreBateauxCoulesOrdi();
            int nbreBateauxCoulesJoueur = surfaceDeJeu.nbreBateauxCoulesJoueur();
            int nbreBateaux = surfaceDeJeu.nbreBateaux();
            view.afficherStatistiquesJoueur(nbreTirs, nbreTouchesUtilisateur, nbreBateauxCoulesOrdi, nbreBateaux);
            view.afficherStatistiquesOrdi(nbreTirs, nbreTouchesOrdinateur, nbreBateauxCoulesJoueur, nbreBateaux);

            // Partie terminée ?
            if (surfaceDeJeu.partieEstTerminee()) {
                partieTerminee();
            } else {
                // L'ordinateur tire aussi automatiquement après ça...
                touche = surfaceDeJeu.ordinateurTire();

                // Mettre à jour l'affichage suite à ce tir
                view.rafraichirSurfaceDeJeu();

                // Mettre à jour les statistiques
                if (touche) {
                    nbreTouchesOrdinateur++;
                }
                nbreBateauxCoulesOrdi = surfaceDeJeu.nbreBateauxCoulesOrdi();
                nbreBateauxCoulesJoueur = surfaceDeJeu.nbreBateauxCoulesJoueur();
                nbreBateaux = surfaceDeJeu.nbreBateaux();
                view.afficherStatistiquesJoueur(nbreTirs, nbreTouchesUtilisateur, nbreBateauxCoulesOrdi, nbreBateaux);
                view.afficherStatistiquesOrdi(nbreTirs, nbreTouchesOrdinateur, nbreBateauxCoulesJoueur, nbreBateaux);

                // Partie terminée ?
                if (surfaceDeJeu.partieEstTerminee()) {
                    partieTerminee();
                }
            }
        }
    }

    private void partieTerminee() {
        partieEnCours = false;
        view.afficherModeActuel(partieEnCours, placementEnCours, bateauAPlacer != null, false);
        if (surfaceDeJeu.nbrePartiesRestantesOrdi() > 0) {
            view.afficherMessageInformation("Vous avez perdu ! L'ordinateur a coulé tous vos bateaux !");
        } else {
            view.afficherMessageInformation(
                    "Vous avez gagné ! Tous les bateaux de l'ordinateur sont coulés ! BRAVO !!!");
        }
    }
}

//endregion
