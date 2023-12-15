package services;

import models.Bateau;
import models.Coordonnee;
import models.Decalage;
import models.PartieDeBateau;
import models.SurfaceDeJeu;

public class BateauxNouveaux {

    /**
     * Crée un nouveau bateau du type mentionné.<br>
     * <br>
     * Le poste de pilotage du bateau sera centré au milieu de la surface de jeu (la
     * classe SurfaceDeJeu vous indiquera la taille de la surface de jeu).
     * Afin de correctement créer les parties de bateau nécessaires pour chaque
     * bateau avec les bons décalages par rapport à son poste de pilotage,
     * référez-vous à l'image "bateau-torpilleur.jpg" qui montre toutes les
     * parties correspondantes à ce bateau. Pour comprendre le système de
     * coordonnées utilisé, consultez l'image "RotationDecalages.png" qui montre
     * comment effectuer une rotation d'un bateau.
     * 
     * @return le bateau nouvellement créé
     */
    public Bateau creerTorpilleur() {
        PartieDeBateau[] partiesDuTorpilleur = new PartieDeBateau[] {
                new PartieDeBateau(new Decalage(-1, 0)),
                new PartieDeBateau(new Decalage(0, 0)),
                new PartieDeBateau(new Decalage(0, -1)),
                new PartieDeBateau(new Decalage(0, 1)),
        };
        Bateau torpilleur = new Bateau(
                new Coordonnee(SurfaceDeJeu.SURFACE_DE_JEU_NBRE_COLONNES / 2,
                        SurfaceDeJeu.SURFACE_DE_JEU_NBRE_LIGNES / 2),
                partiesDuTorpilleur);
        return torpilleur;
    }

    /**
     * Crée un nouveau bateau du type mentionné.<br>
     * <br>
     * Le poste de pilotage du bateau sera centré au milieu de la surface de jeu (la
     * classe SurfaceDeJeu vous indiquera la taille de la surface de jeu).
     * Afin de correctement créer les parties de bateau nécessaires pour chaque
     * bateau avec les bons décalages par rapport à son poste de pilotage,
     * référez-vous à l'image "bateau-sous-marin.jpg" qui montre toutes les
     * parties correspondantes à ce bateau. Pour comprendre le système de
     * coordonnées utilisé, consultez l'image "RotationDecalages.png" qui montre
     * comment effectuer une rotation d'un bateau.
     * 
     * @return le bateau nouvellement créé
     */
    public Bateau creerSousMarin() {

        //
        //              )        (                 ) (           (         (
        //           ( /(   *   ))\ )        (  ( /( )\ )        )\ )  (   )\ )
        //     (   ( )\())` )  /(()/((       )\ )\()|()/(  (    (()/(  )\ (()/(
        //     )\  )((_)\  ( )(_))(_))\    (((_|(_)\ /(_)) )\    /(_)|((_) /(_))
        //    ((_)((_)((_)(_(_()|_))((_)   )\___ ((_|_))_ ((_)  (_)) )\___(_))
        //    __   _____ _____ ___ ___    ___ ___  ___  ___   ___ ___ ___   _
        //    \ \ / / _ \_   _| _ \ __|  / __/ _ \|   \| __| |_ _/ __|_ _| | |
        //     \ V / (_) || | |   / _|  | (_| (_) | |) | _|   | | (__ | |  |_|
        //      \_/ \___/ |_| |_|_\___|  \___\___/|___/|___| |___\___|___| (_)
        //

    }

    /**
     * Crée un nouveau bateau du type mentionné.<br>
     * <br>
     * Le poste de pilotage du bateau sera centré au milieu de la surface de jeu (la
     * classe SurfaceDeJeu vous indiquera la taille de la surface de jeu).
     * Afin de correctement créer les parties de bateau nécessaires pour chaque
     * bateau avec les bons décalages par rapport à son poste de pilotage,
     * référez-vous à l'image "bateau-croiseur.jpg" qui montre toutes les
     * parties correspondantes à ce bateau. Pour comprendre le système de
     * coordonnées utilisé, consultez l'image "RotationDecalages.png" qui montre
     * comment effectuer une rotation d'un bateau.
     * 
     * @return le bateau nouvellement créé
     */
    public Bateau creerCroiseur() {

        //
        //              )        (                 ) (           (         (
        //           ( /(   *   ))\ )        (  ( /( )\ )        )\ )  (   )\ )
        //     (   ( )\())` )  /(()/((       )\ )\()|()/(  (    (()/(  )\ (()/(
        //     )\  )((_)\  ( )(_))(_))\    (((_|(_)\ /(_)) )\    /(_)|((_) /(_))
        //    ((_)((_)((_)(_(_()|_))((_)   )\___ ((_|_))_ ((_)  (_)) )\___(_))
        //    __   _____ _____ ___ ___    ___ ___  ___  ___   ___ ___ ___   _
        //    \ \ / / _ \_   _| _ \ __|  / __/ _ \|   \| __| |_ _/ __|_ _| | |
        //     \ V / (_) || | |   / _|  | (_| (_) | |) | _|   | | (__ | |  |_|
        //      \_/ \___/ |_| |_|_\___|  \___\___/|___/|___| |___\___|___| (_)
        //

    }

    /**
     * Crée un nouveau bateau du type mentionné.<br>
     * <br>
     * Le poste de pilotage du bateau sera centré au milieu de la surface de jeu (la
     * classe SurfaceDeJeu vous indiquera la taille de la surface de jeu).
     * Afin de correctement créer les parties de bateau nécessaires pour chaque
     * bateau avec les bons décalages par rapport à son poste de pilotage,
     * référez-vous à l'image "bateau-cuirasse.jpg" qui montre toutes les
     * parties correspondantes à ce bateau. Pour comprendre le système de
     * coordonnées utilisé, consultez l'image "RotationDecalages.png" qui montre
     * comment effectuer une rotation d'un bateau.
     * 
     * @return le bateau nouvellement créé
     */
    public Bateau creerCuirasse() {

        //
        //              )        (                 ) (           (         (
        //           ( /(   *   ))\ )        (  ( /( )\ )        )\ )  (   )\ )
        //     (   ( )\())` )  /(()/((       )\ )\()|()/(  (    (()/(  )\ (()/(
        //     )\  )((_)\  ( )(_))(_))\    (((_|(_)\ /(_)) )\    /(_)|((_) /(_))
        //    ((_)((_)((_)(_(_()|_))((_)   )\___ ((_|_))_ ((_)  (_)) )\___(_))
        //    __   _____ _____ ___ ___    ___ ___  ___  ___   ___ ___ ___   _
        //    \ \ / / _ \_   _| _ \ __|  / __/ _ \|   \| __| |_ _/ __|_ _| | |
        //     \ V / (_) || | |   / _|  | (_| (_) | |) | _|   | | (__ | |  |_|
        //      \_/ \___/ |_| |_|_\___|  \___\___/|___/|___| |___\___|___| (_)
        //

    }

    /**
     * Crée un nouveau bateau du type mentionné.<br>
     * <br>
     * Le poste de pilotage du bateau sera centré au milieu de la surface de jeu (la
     * classe SurfaceDeJeu vous indiquera la taille de la surface de jeu).
     * Afin de correctement créer les parties de bateau nécessaires pour chaque
     * bateau avec les bons décalages par rapport à son poste de pilotage,
     * référez-vous à l'image "bateau-porte-avions.jpg" qui montre toutes les
     * parties correspondantes à ce bateau. Pour comprendre le système de
     * coordonnées utilisé, consultez l'image "RotationDecalages.png" qui montre
     * comment effectuer une rotation d'un bateau.
     * 
     * @return le bateau nouvellement créé
     */
    public Bateau creerPorteAvions() {

        //
        //              )        (                 ) (           (         (
        //           ( /(   *   ))\ )        (  ( /( )\ )        )\ )  (   )\ )
        //     (   ( )\())` )  /(()/((       )\ )\()|()/(  (    (()/(  )\ (()/(
        //     )\  )((_)\  ( )(_))(_))\    (((_|(_)\ /(_)) )\    /(_)|((_) /(_))
        //    ((_)((_)((_)(_(_()|_))((_)   )\___ ((_|_))_ ((_)  (_)) )\___(_))
        //    __   _____ _____ ___ ___    ___ ___  ___  ___   ___ ___ ___   _
        //    \ \ / / _ \_   _| _ \ __|  / __/ _ \|   \| __| |_ _/ __|_ _| | |
        //     \ V / (_) || | |   / _|  | (_| (_) | |) | _|   | | (__ | |  |_|
        //      \_/ \___/ |_| |_|_\___|  \___\___/|___/|___| |___\___|___| (_)
        //

    }
}
