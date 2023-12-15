package views;

//region NE PAS TOUCHER A L'IHM QUI EST FOURNIE ET FONCTIONNELLE !

import ctrl.Controller;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import views.grid.GrilleDeJeu;

public class View implements Initializable {

    public final static int APPLICATION_LARGEUR_MINIMALE = 700;
    public final static int APPLICATION_HAUTEUR_MINIMALE = 500;

    private final static int LARGEUR_ZONE_PLACEMENT_BATEAUX = 220;

    @FXML
    Button btnRestart;
    @FXML
    Button btnRestartAuto;
    @FXML
    Button btnStart;
    @FXML
    Button btnAbout;
    @FXML
    Button btnQuit;
    @FXML
    Label labelPlayerShots;
    @FXML
    Label labelPlayerTouched;
    @FXML
    Label labelPlayerSink;
    @FXML
    Label labelComputerShots;
    @FXML
    Label labelComputerTouched;
    @FXML
    Label labelComputerSink;
    @FXML
    BorderPane borderPaneZonePlayer;
    @FXML
    BorderPane borderPaneZoneComputer;
    @FXML
    MenuButton btnMenuPlaceShips;
    @FXML
    Button btnUp;
    @FXML
    Button btnRight;
    @FXML
    Button btnLeft;
    @FXML
    Button btnDown;
    @FXML
    Button btnRotate;
    @FXML
    Button btnValidatePosition;
    @FXML
    MenuItem menuItemPorteAvions;
    @FXML
    MenuItem menuItemCroiseur;
    @FXML
    MenuItem menuItemTorpilleur;
    @FXML
    MenuItem menuItemCuirasse;
    @FXML
    MenuItem menuItemSousMarin;
    @FXML
    HBox hBoxPlacementBateaux;

    private final Controller controller;
    private Stage mainStage;
    private GrilleDeJeu grilleDeJeuJoueur;
    private GrilleDeJeu grilleDeJeuOrdi;

    // private SurfaceMaritime regionPlayer;
    // private SurfaceMaritime regionComputer;

    public View(Controller controller) {
        this.controller = controller;
        this.mainStage = null;
    }

    public void start() {
        Platform.startup(() -> {
            try {
                mainStage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view.fxml"));
                fxmlLoader.setControllerFactory(type -> {
                    return this;
                });
                Parent root = (Parent) fxmlLoader.load();
                Scene principalScene = new Scene(root);
                mainStage.getIcons().add(new Image(getClass().getResourceAsStream("img/AppLogo.png")));

                //
                // Disponible dans les dernières versions de JavaFX pour correctement mettre
                // l'icône d'une application Mac dans le dock. Là il faudrait le faire dynamiquement.
                //
                // try {
                // java.awt.Image image = new
                // ImageIcon(getClass().getResource("img/AppLogo.png")).getImage();
                // com.apple.eawt.Application.getApplication().setDockIconImage(image);
                // } catch (Exception e) {
                // }

                principalScene.getStylesheets()
                        .add(getClass().getResource("css/custom-3d-borders.css").toExternalForm());

                mainStage.setScene(principalScene);
                mainStage.setTitle("Touché-Coulé / D400 - par Paul Friedli");
                mainStage.setMinWidth(APPLICATION_LARGEUR_MINIMALE); // Pour limiter la taille min
                mainStage.setMinHeight(APPLICATION_HAUTEUR_MINIMALE); // Pour limiter la taille min
                mainStage.setOnCloseRequest((WindowEvent e) -> {
                    if (afficherQuestionOuiNon("Voulez-vous vraiment quitter l'application ?")) {
                        // Permettre au contrôleur de faire un dernier truc avant de quitter
                        controller.actionQuitter();
                    } else {
                        e.consume(); // Ne va pas quitter, l'événement est "consommé"
                    }
                });
                mainStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
                Platform.exit();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        grilleDeJeuJoueur = new GrilleDeJeu(controller, controller.getSurfaceDeJeu(), true);
        grilleDeJeuOrdi = new GrilleDeJeu(controller, controller.getSurfaceDeJeu(), false);
        borderPaneZonePlayer.setCenter(grilleDeJeuJoueur);
        borderPaneZoneComputer.setCenter(grilleDeJeuOrdi);
        showHideShipPlacementZone(true);
    }

    @FXML
    private void onBtnRestart(ActionEvent event) {
        controller.actionRestart();
    }

    @FXML
    private void onBtnRestartAuto(ActionEvent event) {
        controller.actionRestartAuto();
    }

    @FXML
    private void onBtnStart(ActionEvent event) {
        controller.actionStart();
    }

    @FXML
    private void onBtnAbout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("A propos de...");
        alert.setHeaderText(null);
        alert.setContentText(
                "Buts : mettre progressivement en pratique les enseignements du D400 afin de compléter un projet Java ludique ressemblant au jeu 'Bataille navale'."
                        + System.getProperty("line.separator")
                        + System.getProperty("line.separator")
                        + "Ecrit par Paul Friedli - 2023");
        ImageView icon = new ImageView("file:src/views/img/programming-computer.gif");
        icon.setFitWidth(128);
        icon.setFitHeight(128);
        alert.getDialogPane().getStylesheets()
                .add(getClass().getResource("css/custom-3d-borders.css").toExternalForm());
        HBox pane = new HBox(icon);
        pane.getStyleClass().add("custom_3d_inset_border_nopadding");
        alert.setGraphic(pane);
        alert.showAndWait();
    }

    private void showHideShipPlacementZone(boolean mustHideIt) {
        if (mustHideIt) {
            if (hBoxPlacementBateaux.isVisible()) {
                double currentMainStageWidth = mainStage.getWidth();

                hBoxPlacementBateaux.setVisible(false);
                hBoxPlacementBateaux.setManaged(false);

                mainStage.setWidth(currentMainStageWidth - LARGEUR_ZONE_PLACEMENT_BATEAUX);
            }
        } else {
            if (!hBoxPlacementBateaux.isVisible()) {
                double currentMainStageWidth = mainStage.getWidth();

                hBoxPlacementBateaux.setVisible(true);
                hBoxPlacementBateaux.setManaged(true);

                mainStage.setWidth(currentMainStageWidth + LARGEUR_ZONE_PLACEMENT_BATEAUX);
            }
        }
    }

    @FXML
    private void onBtnQuit(ActionEvent event) {
        mainStage.fireEvent(new WindowEvent(mainStage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void afficherMessageInformation(String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setGraphic(new ImageView("file:src/views/img/icon-fine-64.png"));
        alert.showAndWait();
    }

    public void afficherMessageErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setGraphic(new ImageView("file:src/views/img/icon-error-64.png"));
        alert.showAndWait();
    }

    public boolean afficherQuestionOuiNon(String message) {
        boolean reponseOui = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Question");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setGraphic(new ImageView("file:src/views/img/icon-question-64.png"));
        ButtonType okButton = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("Non", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(okButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(noButton) == okButton) {
            reponseOui = true;
        }

        return reponseOui;
    }

    @FXML
    private void onBtnUp(ActionEvent event) {
        controller.actionBateauBougerHaut();
    }

    @FXML
    private void onBtnDown(ActionEvent event) {
        controller.actionBateauBougerBas();
    }

    @FXML
    private void onBtnLeft(ActionEvent event) {
        controller.actionBateauBougerGauche();
    }

    @FXML
    private void onBtnRight(ActionEvent event) {
        controller.actionBateauBougerDroite();
    }

    @FXML
    private void onBtnRotate(ActionEvent event) {
        controller.actionBateauBougerRotation();
    }

    @FXML
    private void onBtnValidatePosition(ActionEvent event) {
        controller.actionBateauValiderPosition();

    }

    @FXML
    private void onMenuItemPorteAvions(ActionEvent event) {
        controller.actionBateauNouveauPorteAvion();
    }

    @FXML
    private void onMenuItemCroiseur(ActionEvent event) {
        controller.actionBateauNouveauCroiseur();
    }

    @FXML
    private void onMenuItemTorpilleur(ActionEvent event) {
        controller.actionBateauNouveauTorpilleur();
    }

    @FXML
    private void onMenuItemCuirasse(ActionEvent event) {
        controller.actionBateauNouveauCuirasse();
    }

    @FXML
    private void onMenuItemSousMarin(ActionEvent event) {
        controller.actionBateauNouveauSousMarin();
    }

    public void messageInfo(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
        });
    }

    public void messageErreur(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
        });
    }

    public void afficherModeActuel(boolean jeuEnCours, /* boolean jeuTermine, */ boolean placementDesBateauxEnCours,
            boolean bateauEnCoursDePlacement, boolean estCeQueDesPartiesSontSuperposees) {
        btnStart.setDisable(jeuEnCours /* || jeuTermine */ || placementDesBateauxEnCours);
        btnRestart.setDisable(false); // On peut toujours redémarrer une nouvelle partie
        btnRestartAuto.setDisable(false); // On peut toujours redémarrer une nouvelle partie
        showHideShipPlacementZone(!placementDesBateauxEnCours);

        btnMenuPlaceShips.setDisable(!(!jeuEnCours && placementDesBateauxEnCours && !bateauEnCoursDePlacement));
        btnValidatePosition.setDisable(!(bateauEnCoursDePlacement && !estCeQueDesPartiesSontSuperposees));
    }

    public void rafraichirSurfaceDeJeu() {
        grilleDeJeuJoueur.redraw();
        grilleDeJeuOrdi.redraw();
    }

    public void mettreAJourMenuBateauxAPlacer(
            int nbreSousMarin,
            int nbreCroiseur,
            int nbreTorpilleur,
            int nbreCuirasse,
            int nbrePorteAvions) {

        // Commencer par vider le menu
        btnMenuPlaceShips.getItems().clear();

        for (int i = 0; i < nbrePorteAvions; i++) {
            ImageView imgViewPorteAvion = new ImageView("file:src/views/img/bateau-porte-avions.jpg");
            // imgViewPorteAvion.setFitWidth(32);
            imgViewPorteAvion.setFitHeight(64);
            MenuItem itemPorteAvion = new MenuItem("Porte-avions", imgViewPorteAvion);
            itemPorteAvion.setOnAction(event -> {
                btnMenuPlaceShips.getItems().remove(itemPorteAvion);
                controller.actionBateauNouveauPorteAvion();
            });
            btnMenuPlaceShips.getItems().add(itemPorteAvion);
        }

        for (int i = 0; i < nbreCuirasse; i++) {
            ImageView imgViewCuirasse = new ImageView("file:src/views/img/bateau-cuirasse.jpg");
            // imgViewCuirasse.setFitWidth(32);
            imgViewCuirasse.setFitHeight(64);
            MenuItem itemCuirasse = new MenuItem("Cuirassé", imgViewCuirasse);
            itemCuirasse.setOnAction(event -> {
                btnMenuPlaceShips.getItems().remove(itemCuirasse);
                controller.actionBateauNouveauCuirasse();
            });
            btnMenuPlaceShips.getItems().add(itemCuirasse);
        }

        for (int i = 0; i < nbreTorpilleur; i++) {
            ImageView imgViewTorpilleur = new ImageView("file:src/views/img/bateau-torpilleur.jpg");
            // imgViewTorpilleur.setFitWidth(32);
            imgViewTorpilleur.setFitHeight(64);
            MenuItem itemTorpilleur = new MenuItem("Torpilleur", imgViewTorpilleur);
            itemTorpilleur.setOnAction(event -> {
                btnMenuPlaceShips.getItems().remove(itemTorpilleur);
                controller.actionBateauNouveauTorpilleur();
            });
            btnMenuPlaceShips.getItems().add(itemTorpilleur);
        }

        for (int i = 0; i < nbreCroiseur; i++) {
            ImageView imgViewCroiseur = new ImageView("file:src/views/img/bateau-croiseur.jpg");
            // imgViewCroiseur.setFitWidth(32);
            imgViewCroiseur.setFitHeight(64);
            MenuItem itemCroiseur = new MenuItem("Croiseur", imgViewCroiseur);
            itemCroiseur.setOnAction(event -> {
                btnMenuPlaceShips.getItems().remove(itemCroiseur);
                controller.actionBateauNouveauCroiseur();
            });
            btnMenuPlaceShips.getItems().add(itemCroiseur);
        }

        for (int i = 0; i < nbreSousMarin; i++) {
            ImageView imgViewSousMarin = new ImageView("file:src/views/img/bateau-sous-marin.jpg");
            // imgViewSousMarin.setFitWidth(32);
            imgViewSousMarin.setFitHeight(64);
            MenuItem itemSousMarin = new MenuItem("Sous-marin", imgViewSousMarin);
            itemSousMarin.setOnAction(event -> {
                btnMenuPlaceShips.getItems().remove(itemSousMarin);
                controller.actionBateauNouveauSousMarin();
            });
            btnMenuPlaceShips.getItems().add(itemSousMarin);
        }
    }

    public int getNbreBateauxRestantsAPlacer() {
        return btnMenuPlaceShips.getItems().size();
    }

    public void afficherStatistiquesJoueur(int nbreTirs, int nbreTouchesUtilisateur, int nbreBateauxCoulesOrdi,
            int nbreBateaux) {
        afficherStatistiquesOrdi(nbreTirs, nbreTouchesUtilisateur, nbreBateauxCoulesOrdi, nbreBateaux, labelPlayerShots,
                labelPlayerTouched, labelPlayerSink);
    }

    public void afficherStatistiquesOrdi(int nbreTirs, int nbreTouchesUtilisateur, int nbreBateauxCoulesOrdi,
            int nbreBateaux) {
        afficherStatistiquesOrdi(nbreTirs, nbreTouchesUtilisateur, nbreBateauxCoulesOrdi, nbreBateaux,
                labelComputerShots, labelComputerTouched, labelComputerSink);
    }

    private void afficherStatistiquesOrdi(int nbreTirs, int nbreTouches, int nbreBateauxCoules, int nbreBateaux,
            Label a, Label b, Label c) {
        a.setText("" + nbreTirs);
        b.setText(nbreTouches + " / " + nbreTirs);
        c.setText(nbreBateauxCoules + " / " + nbreBateaux);
    }

}
// endregion