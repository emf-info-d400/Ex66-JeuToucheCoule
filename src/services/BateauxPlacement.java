package services;

//region NE PAS TOUCHER AU SERVICE DE PLACEMENT QUI EST FOURNI ET FONCTIONNEL !

import models.Bateau;
import models.Coordonnee;
import models.Decalage;
import models.PartieDeBateau;
import models.SurfaceDeJeu;

public class BateauxPlacement {

    public final static int BATEAUX_INITIAL_NBRE_PORTE_AVIONS = 1;
    public final static int BATEAUX_INITIAL_NBRE_CROISEURS = 1;
    public final static int BATEAUX_INITIAL_NBRE_CUIRASSE = 1;
    public final static int BATEAUX_INITIAL_NBRE_TORPILLEURS = 1;
    public final static int BATEAUX_INITIAL_NBRE_SOUS_MARINS = 3;

    private final BateauxNouveaux serviceBateaux;

    public BateauxPlacement() {
        serviceBateaux = new BateauxNouveaux();
    }

    public boolean marquerTouchesBateauxSuperposes(Bateau[] bateaux) {

        boolean estCeQueDesPartiesSontSuperposees = false;

        if (bateaux != null) {

            // Commencer par marquer comme "non touché" toutes les parties de tous les
            // bateaux
            for (int i = 0; i < bateaux.length; i++) {
                Bateau bateauCourant = bateaux[i];
                if (bateauCourant != null) {
                    PartieDeBateau[] partiesDuBateauCourant = bateauCourant.getPartiesDuBateau();
                    for (int k = 0; k < partiesDuBateauCourant.length; k++) {
                        PartieDeBateau partieCourante = partiesDuBateauCourant[k];
                        partieCourante.setTouchee(false);
                    }

                }
            }

            // Ensuite, contrôler chaque bateau face à tous les autres bateaux
            for (int i = 0; i < bateaux.length - 1; i++) {

                Bateau bateauCourant = bateaux[i];
                if (bateauCourant != null) {

                    PartieDeBateau[] partiesDuBateauCourant = bateauCourant.getPartiesDuBateau();

                    // Prendre tous les autres bateaux
                    for (int j = i + 1; j < bateaux.length; j++) {

                        Bateau bateauAutre = bateaux[j];
                        if (bateauAutre != null) {

                            PartieDeBateau[] partiesDuBateauAutre = bateauAutre.getPartiesDuBateau();

                            // Pour chaque partie du bateau courant
                            for (int k1 = 0; k1 < partiesDuBateauCourant.length; k1++) {

                                PartieDeBateau partieCourante = partiesDuBateauCourant[k1];

                                // Vérifier s'il touche une partie de l'autre bateau...
                                for (int k2 = 0; k2 < partiesDuBateauAutre.length; k2++) {

                                    PartieDeBateau partieAutre = partiesDuBateauAutre[k2];

                                    Coordonnee coordPartieCourante = bateauCourant
                                            .getPositionEffectivePartieDeBateau(partieCourante);
                                    Coordonnee coordPartieAutre = bateauAutre
                                            .getPositionEffectivePartieDeBateau(partieAutre);

                                    if (coordPartieCourante.estIdentiqueA(coordPartieAutre)) {
                                        partieCourante.setTouchee(true);
                                        partieAutre.setTouchee(true);
                                        estCeQueDesPartiesSontSuperposees = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return estCeQueDesPartiesSontSuperposees;
    }

    private void rotationAleatoireBateau(Bateau bateau) {
        if (NbresAleatoires.uneChanceSurDeux()) {
            bateau.rotationDroite();
            if (NbresAleatoires.uneChanceSurDeux()) {
                bateau.rotationDroite();
                if (NbresAleatoires.uneChanceSurDeux()) {
                    bateau.rotationDroite();
                }
            }
        }
    }

    private void emplacementAleatoireBateau(Bateau bateau) {

        // On va chercher les parties les plus décalées de ce bateau pour éviter de
        // placer ensuite le poste de pilotage à un endroit qui ferait sortir l'une ou
        // l'autre partie de la surface de jeu
        int plusGrandDecalageGauche = Integer.MAX_VALUE;
        int plusGrandDecalageDroite = Integer.MIN_VALUE;
        int plusGrandDecalageHaut = Integer.MAX_VALUE;
        int plusGrandDecalageBas = Integer.MIN_VALUE;

        PartieDeBateau[] parties = bateau.getPartiesDuBateau();
        for (int i = 0; i < parties.length; i++) {
            PartieDeBateau partie = parties[i];
            Decalage decalagePartie = partie.getDecalageParRapportAuPosteDePilotage();
            if (decalagePartie.getDeltaX() > plusGrandDecalageDroite) {
                plusGrandDecalageDroite = decalagePartie.getDeltaX();
            }
            if (decalagePartie.getDeltaX() < plusGrandDecalageGauche) {
                plusGrandDecalageGauche = decalagePartie.getDeltaX();
            }
            if (decalagePartie.getDeltaY() > plusGrandDecalageBas) {
                plusGrandDecalageBas = decalagePartie.getDeltaY();
            }
            if (decalagePartie.getDeltaY() < plusGrandDecalageHaut) {
                plusGrandDecalageHaut = decalagePartie.getDeltaY();
            }
        }

        // Maintenant qu'on connait les décalages maximum de ce bateau, définir les
        // limites de placement du poste de pilotage par rapport à la surface de jeu
        int minX = 0 - plusGrandDecalageGauche;
        int maxX = SurfaceDeJeu.SURFACE_DE_JEU_NBRE_COLONNES - plusGrandDecalageDroite - 1;
        int minY = 0 - plusGrandDecalageHaut;
        int maxY = SurfaceDeJeu.SURFACE_DE_JEU_NBRE_LIGNES - plusGrandDecalageBas - 1;

        // Générer aléatoirement une position du bateau dans ces limites
        int newX = NbresAleatoires.nbreAleatoireEntreLimitesComprise(minX, maxX);
        int newY = NbresAleatoires.nbreAleatoireEntreLimitesComprise(minY, maxY);

        // Positionner le bateau à cet endroit-là
        bateau.setPositionDuPosteDePilotage(new Coordonnee(newX, newY));
    }

    public Bateau[] genererBateauxAleatoires() {
        Bateau[] bateaux = new Bateau[BATEAUX_INITIAL_NBRE_PORTE_AVIONS +
                BATEAUX_INITIAL_NBRE_CROISEURS +
                BATEAUX_INITIAL_NBRE_CUIRASSE +
                BATEAUX_INITIAL_NBRE_TORPILLEURS +
                BATEAUX_INITIAL_NBRE_SOUS_MARINS];

        int positionProchainBateau = 0;

        for (int i = 0; i < BATEAUX_INITIAL_NBRE_PORTE_AVIONS; i++) {
            Bateau bateau = serviceBateaux.creerPorteAvions();
            rotationAleatoireBateau(bateau);
            bateaux[positionProchainBateau++] = bateau;
        }

        for (int i = 0; i < BATEAUX_INITIAL_NBRE_CROISEURS; i++) {
            Bateau bateau = serviceBateaux.creerCroiseur();
            rotationAleatoireBateau(bateau);
            bateaux[positionProchainBateau++] = bateau;
        }

        for (int i = 0; i < BATEAUX_INITIAL_NBRE_CUIRASSE; i++) {
            Bateau bateau = serviceBateaux.creerCuirasse();
            rotationAleatoireBateau(bateau);
            bateaux[positionProchainBateau++] = bateau;
        }

        for (int i = 0; i < BATEAUX_INITIAL_NBRE_TORPILLEURS; i++) {
            Bateau bateau = serviceBateaux.creerTorpilleur();
            rotationAleatoireBateau(bateau);
            bateaux[positionProchainBateau++] = bateau;
        }

        for (int i = 0; i < BATEAUX_INITIAL_NBRE_SOUS_MARINS; i++) {
            Bateau bateau = serviceBateaux.creerSousMarin();
            rotationAleatoireBateau(bateau);
            bateaux[positionProchainBateau++] = bateau;
        }

        // On va maintenant aléatoirement disposer les bateaux et ce jusqu'à ce que leur
        // position ne provoque jamais une superposition
        while (marquerTouchesBateauxSuperposes(bateaux)) {
            for (int i = 0; i < bateaux.length; i++) {
                emplacementAleatoireBateau(bateaux[i]);
            }
        }

        return bateaux;
    }

}

//endregion
