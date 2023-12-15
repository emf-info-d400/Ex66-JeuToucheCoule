package services;

import models.Bateau;
import models.PartieDeBateau;

public class BateauxEtat {

    /**
     * Détermine le nombre total de parties de bateaux qui n'ont pas encore été
     * touchées, tous bateaux confondus.
     * 
     * @param bateaux la liste de bateaux à utiliser
     * @return le nombre total de parties de bateau qui n'ont pas encore été
     *         touchées
     */
    public static int nbrePartiesRestantes(Bateau[] bateaux) {

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
     * Détermine le nombre total de bateaux qui ont été coulés, c-à-d le nombre de
     * bateaux dont toutes leurs parties ont été touchées.
     * 
     * @param bateaux la liste de bateaux à utiliser
     * @return le nombre total de bateaux qui ont été coulés
     */
    public static int nbreBateauxCoules(Bateau[] bateaux) {

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
