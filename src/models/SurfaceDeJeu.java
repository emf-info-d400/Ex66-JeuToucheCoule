package models;

//region NE PAS TOUCHER AU CONTRÔLEUR QUI EST FOURNI ET FONCTIONNEL !

import services.BateauxEtat;

public class SurfaceDeJeu {

    public final static int SURFACE_DE_JEU_NBRE_COLONNES = 12;
    public final static int SURFACE_DE_JEU_NBRE_LIGNES = 12;

    private EtatSurfaceMaritime[][] etatSurfaceMaritime;
    private Bateau[] bateauxJoueur;
    private Bateau[] bateauxOrdi;

    public SurfaceDeJeu() {
        reinitialiser();
    }

    public void reinitialiser() {
        this.bateauxJoueur = new Bateau[0];
        this.bateauxOrdi = new Bateau[0];
        this.etatSurfaceMaritime = new EtatSurfaceMaritime[SURFACE_DE_JEU_NBRE_COLONNES][SURFACE_DE_JEU_NBRE_LIGNES];
        for (int i = 0; i < SURFACE_DE_JEU_NBRE_COLONNES; i++) {
            for (int j = 0; j < SURFACE_DE_JEU_NBRE_LIGNES; j++) {
                etatSurfaceMaritime[i][j] = EtatSurfaceMaritime.INTACT;
            }
        }
    }

    public void ajouterBateauJoueur(Bateau bateau) {
        Bateau[] nouvelleListe = new Bateau[bateauxJoueur.length + 1];
        for (int i = 0; i < bateauxJoueur.length; i++) {
            nouvelleListe[i] = bateauxJoueur[i];
        }
        nouvelleListe[bateauxJoueur.length] = bateau;
        bateauxJoueur = nouvelleListe;
    }

    public void definirBateauxOrdi(Bateau[] bateaux) {
        bateauxOrdi = bateaux;
    }

    public boolean coordonneDansLaSurfaceDeJeu(int gridX, int gridY) {
        return ((gridX >= 0) && (gridX < SURFACE_DE_JEU_NBRE_COLONNES)
                && (gridY >= 0) && (gridY < SURFACE_DE_JEU_NBRE_LIGNES));
    }

    public EtatSurfaceMaritime getEtatDeSurfaceMaritime(int gridX, int gridY) {

        EtatSurfaceMaritime etat = EtatSurfaceMaritime.INTACT;

        if (coordonneDansLaSurfaceDeJeu(gridX, gridY)) {
            etat = etatSurfaceMaritime[gridX][gridY];
        }

        return etat;
    }

    public void setEtatDeSurfaceMaritime(int gridX, int gridY, EtatSurfaceMaritime nouvelEtat) {
        if (coordonneDansLaSurfaceDeJeu(gridX, gridY)) {
            etatSurfaceMaritime[gridX][gridY] = nouvelEtat;
        }
    }

    public Bateau[] getBateauxJoueur() {
        return bateauxJoueur;
    }

    public Bateau[] getBateauxOrdi() {
        return bateauxOrdi;
    }

    public boolean joueurTire(int posX, int posY) {

        boolean aTouche = false;

        Coordonnee tir = new Coordonnee(posX, posY);

        for (int i = 0; i < bateauxOrdi.length; i++) {
            Bateau bateauOrdi = bateauxOrdi[i];
            PartieDeBateau[] parties = bateauOrdi.getPartiesDuBateau();
            for (int j = 0; j < parties.length; j++) {
                PartieDeBateau partieDuBateau = parties[j];
                Coordonnee posDeCettePartie = bateauOrdi.getPositionEffectivePartieDeBateau(partieDuBateau);
                if (posDeCettePartie.estIdentiqueA(tir) && !partieDuBateau.isTouchee()) {
                    partieDuBateau.setTouchee(true);
                    aTouche = true;
                    break; // Il n'y a forcément qu'une seule partie de bateau à cet endroit-là...
                }
            }
        }

        return aTouche;
    }

    public boolean ordinateurTire() {

        boolean aTouche = false;

        // Inventir une position tirée qui n'a pas encore été utilisée par
        // l'ordinateur...
        EtatSurfaceMaritime etat;
        int posX;
        int posY;
        do {
            posX = (int) (Math.random() * SURFACE_DE_JEU_NBRE_COLONNES);
            posY = (int) (Math.random() * SURFACE_DE_JEU_NBRE_LIGNES);
            etat = getEtatDeSurfaceMaritime(posX, posY);
        } while ((etat != EtatSurfaceMaritime.INTACT) && (etat != EtatSurfaceMaritime.JOUEUR_A_TIRE));

        // C'est là qu'à tiré l'ordi
        Coordonnee tir = new Coordonnee(posX, posY);

        // Changer le statut de surface
        switch (etat) {
            case INTACT:
                setEtatDeSurfaceMaritime(posX, posY, EtatSurfaceMaritime.ORDI_A_TIRE);
                break;
            case JOUEUR_A_TIRE:
                setEtatDeSurfaceMaritime(posX, posY, EtatSurfaceMaritime.JOUEUR_ET_ORDI_ONT_TIRE);
                break;
            default:
        }

        // Vérifier qu'on n'a pas touché qqch
        for (int i = 0; i < bateauxJoueur.length; i++) {
            Bateau bateauOrdi = bateauxJoueur[i];
            PartieDeBateau[] parties = bateauOrdi.getPartiesDuBateau();
            for (int j = 0; j < parties.length; j++) {
                PartieDeBateau partieDuBateau = parties[j];
                Coordonnee posDeCettePartie = bateauOrdi.getPositionEffectivePartieDeBateau(partieDuBateau);
                if (posDeCettePartie.estIdentiqueA(tir) && !partieDuBateau.isTouchee()) {
                    partieDuBateau.setTouchee(true);
                    aTouche = true;
                    break; // Il n'y a forcément qu'une seule partie de bateau à cet endroit-là...
                }
            }
        }

        return aTouche;
    }

    public int nbrePartiesRestantesOrdi() {
        return BateauxEtat.nbrePartiesRestantes(bateauxOrdi);
    }

    public int nbrePartiesRestantesJoueur() {
        return BateauxEtat.nbrePartiesRestantes(bateauxJoueur);
    }

    public boolean partieEstTerminee() {
        return (nbrePartiesRestantesOrdi() == 0) || (nbrePartiesRestantesJoueur() == 0);
    }

    public int nbreBateauxCoulesOrdi() {
        return BateauxEtat.nbreBateauxCoules(bateauxOrdi);
    }

    public int nbreBateauxCoulesJoueur() {
        return BateauxEtat.nbreBateauxCoules(bateauxJoueur);
    }

    public int nbreBateaux() {
        return bateauxOrdi.length;
    }
}

// endregion
