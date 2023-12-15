package services;

import models.Bateau;
import models.Coordonnee;
import models.Decalage;
import models.PartieDeBateau;
import models.SurfaceDeJeu;

public class BateauxDeplacement {

    /**
     * Bouge un bateau d'un certain décalage seulement si cette opération ne fait
     * pas sortir l'une de ses parties de la surface de jeu.<br>
     * Avant de procéder au décalage du bateau, c-à-d au décalage des coordonnées de
     * son poste de pilotage, il faut vérifier si l'une ou l'autre des parties du
     * bateau sortira des limites du terrain de jeu.<br>
     * En largeur, le terrain va de 0 (compris) à SURFACE_DE_JEU_NBRE_COLONNES-1
     * (compris), et en hauteur de 0 (compris) à SURFACE_DE_JEU_NBRE_LIGNES-1
     * (compris). Il faut donc vérifier qu'aucune des parties du bateau n'aurait
     * une coordonnée qui sortirait de ces limites.<br>
     * Une fois cette vérification faite, si le bateau restera bien dans ces
     * limites, alors on pourra définitivement appliquer ce décalage à chacune de
     * ses parties.
     * 
     * @param bateauABouger le bateau a déplacer
     * @param decalage      le décalage a appliquer au bateau, c-à-d à son poste de
     *                      pilotage
     * @return vrai si le déplacement a été réalisé
     */
    public static boolean bougerBateau(Bateau bateauABouger, Decalage decalage) {

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
     * Tourne le bateau de 90° à droite seulement si cette opération ne fait pas
     * sortir l'une de ses parties de la surface de jeu.<br>
     * La rotation d'un bateau n'influence pas les coordonnées de son poste de
     * pilotage qui ne changeront pas. Seuls les décalages des parties du bateau par
     * rapport au poste de pilotage seront affectés par une rotation (pour la
     * formule a appliquer à chacune des parties du bateau veuillez vous référez à
     * l'image "doc/RotationDecalages.png" qui explique cela visuellement).<br>
     * Il va donc falloir d'abord vérifier chacune des parties du bateau pour voir
     * si celle-ci sortirait de la surface de jeu avant de pouvoir décider
     * d'appliquer la rotation au bateau uniquement si aucune de ses parties ne
     * sortirait des limites du terrain de jeu.<br>
     * En largeur, le terrain va de 0 (compris) à SURFACE_DE_JEU_NBRE_COLONNES-1
     * (compris), et en hauteur de 0 (compris) à SURFACE_DE_JEU_NBRE_LIGNES-1
     * (compris). *
     * 
     * @param bateauATourner le bateau a tourner
     * @return vrai si la rotation a été effectuée
     */
    public static boolean rotationBateau(Bateau bateauATourner) {

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
