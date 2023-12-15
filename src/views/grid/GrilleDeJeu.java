package views.grid;

//region NE PAS TOUCHER A CETTE CLASSE QUI EST FOURNIE ET FONCTIONNELLE !

import java.util.ArrayList;
import ctrl.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import models.Bateau;
import models.Coordonnee;
import models.Decalage;
import models.EtatSurfaceMaritime;
import models.PartieDeBateau;
import models.SurfaceDeJeu;

public class GrilleDeJeu extends Region {

    private final static int DESIRED_MARGIN_SIZE = 5;
    private final static boolean DEBUG_FORCE_VIEW_COMPUTER_SHIPS = false;

    private final Canvas canvas;
    private final Controller controller;
    private final SurfaceDeJeu surfaceDeJeu;
    private final boolean isPlayer;
    private final static ArrayList<GrilleDeJeu> toutesLesGrilles = new ArrayList<>();

    protected abstract class CellMouseEventListener implements EventHandler<MouseEvent> {

        private final GrilleDeJeu grilleDeJeu;

        public CellMouseEventListener(GrilleDeJeu surfaceMaritime) {
            this.grilleDeJeu = surfaceMaritime;
        }

        @Override
        public void handle(MouseEvent event) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            event.consume();

            int cellSize = grilleDeJeu.getCellSize();
            int realWidth = SurfaceDeJeu.SURFACE_DE_JEU_NBRE_COLONNES * cellSize;
            int realHeight = SurfaceDeJeu.SURFACE_DE_JEU_NBRE_LIGNES * cellSize;
            int shiftX = ((int) grilleDeJeu.getWidth() - realWidth) / 2;
            int shiftY = ((int) grilleDeJeu.getHeight() - realHeight) / 2;

            x = x - shiftX;
            y = y - shiftY;

            int gridX = x / cellSize;
            int gridY = y / cellSize;

            // ignore out of bounds
            if ((x >= 0) && (y >= 0) && (gridX <= (SurfaceDeJeu.SURFACE_DE_JEU_NBRE_COLONNES - 1))
                    && (gridY <= (SurfaceDeJeu.SURFACE_DE_JEU_NBRE_LIGNES - 1))) {
                doAction(event, gridX, gridY);
            }
        }

        public abstract void doAction(MouseEvent event, int gridX, int gridY);

        public GrilleDeJeu getGrilleDeJeu() {
            return grilleDeJeu;
        }
    }

    protected class CellClickListener extends CellMouseEventListener {

        public CellClickListener(GrilleDeJeu surfaceMaritime) {
            super(surfaceMaritime);
        }

        @Override
        public void doAction(MouseEvent event, int gridX, int gridY) {
            getGrilleDeJeu().onCellClick(event.getButton(), gridX, gridY);
        }
    }

    protected class CellHoverListener extends CellMouseEventListener {

        public CellHoverListener(GrilleDeJeu surfaceMaritime) {
            super(surfaceMaritime);
        }

        @Override
        public void doAction(MouseEvent event, int gridX, int gridY) {
            getGrilleDeJeu().onCellHover(gridX, gridY);
        }
    }

    public GrilleDeJeu(Controller controller, SurfaceDeJeu surfaceDeJeu, boolean isPlayer) {

        super();

        this.controller = controller;
        this.surfaceDeJeu = surfaceDeJeu;
        this.isPlayer = isPlayer;

        toutesLesGrilles.add(this);

        //
        // Créer le canvas qui sera utiilsé pour dessiner et le redimensionner au fur et
        // à mesure quand la région change elle-aussi
        //
        canvas = new Canvas(getWidth(), getHeight());
        getChildren().add(canvas);
        widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                canvas.setWidth(newValue.intValue());
                redraw();
            }
        });

        heightProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                canvas.setHeight(newValue.intValue());
                redraw();
            }
        });

        redraw();

        setOnMouseClicked(new CellClickListener(this));
       // setOnMouseDragged(new CellClickListener(this));
        setOnMouseMoved(new CellHoverListener(this));
    }

    public void redraw() {
        for (GrilleDeJeu grilleDeJeu : toutesLesGrilles) {
            grilleDeJeu.redrawSelf();
        }
    }

    private void redrawSelf() {

        if ((getWidth() <= 0) || (getHeight() <= 0)) {
            return;
        }

        // Clear all board
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());

        // Compute dimensions
        int cellSize = getCellSize();
        int realWidth = SurfaceDeJeu.SURFACE_DE_JEU_NBRE_COLONNES * cellSize;
        int realHeight = SurfaceDeJeu.SURFACE_DE_JEU_NBRE_LIGNES * cellSize;
        int shiftX = ((int) getWidth() - realWidth) / 2;
        int shiftY = ((int) getHeight() - realHeight) / 2;

        // Clear all cells background to blue
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(shiftX, shiftY, realWidth, realHeight);

        // Draw own ships
        if (isPlayer)
            for (Bateau bateau : surfaceDeJeu.getBateauxJoueur()) {
                if (bateau != null) {
                    Coordonnee positionPosteDePilotage = bateau.getPositionDuPosteDePilotage();
                    if (positionPosteDePilotage != null) {
                        for (PartieDeBateau partie : bateau.getPartiesDuBateau()) {
                            Decalage decalagePartie = partie.getDecalageParRapportAuPosteDePilotage();
                            if (decalagePartie != null) {
                                gc.setFill(partie.isTouchee() ? Color.RED : Color.BLACK);

                                int gridXBateau = positionPosteDePilotage.getX() + decalagePartie.getDeltaX();
                                int gridYBateau = positionPosteDePilotage.getY() + decalagePartie.getDeltaY();

                                int x1 = shiftX + gridXBateau * cellSize;
                                int y1 = shiftY + gridYBateau * cellSize;
                                gc.fillRect(x1, y1, cellSize, cellSize);
                            }
                        }
                    }
                }
            }

        // Draw computer ships
        if (!isPlayer)
            for (Bateau bateau : surfaceDeJeu.getBateauxOrdi()) {
                if (bateau != null) {
                    Coordonnee positionPosteDePilotage = bateau.getPositionDuPosteDePilotage();
                    if (positionPosteDePilotage != null) {
                        for (PartieDeBateau partie : bateau.getPartiesDuBateau()) {
                            Decalage decalagePartie = partie.getDecalageParRapportAuPosteDePilotage();
                            if (decalagePartie != null) {

                                if (DEBUG_FORCE_VIEW_COMPUTER_SHIPS)
                                    gc.setFill(partie.isTouchee() ? Color.RED : Color.LIGHTYELLOW);
                                else
                                    gc.setFill(partie.isTouchee() ? Color.RED : Color.LIGHTBLUE);

                                int gridXBateau = positionPosteDePilotage.getX() + decalagePartie.getDeltaX();
                                int gridYBateau = positionPosteDePilotage.getY() + decalagePartie.getDeltaY();

                                int x1 = shiftX + gridXBateau * cellSize;
                                int y1 = shiftY + gridYBateau * cellSize;
                                gc.fillRect(x1, y1, cellSize, cellSize);
                            }
                        }
                    }
                }
            }

        // Draw shots
        for (int gridX = 0; gridX < SurfaceDeJeu.SURFACE_DE_JEU_NBRE_COLONNES; gridX++) {
            for (int gridY = 0; gridY < SurfaceDeJeu.SURFACE_DE_JEU_NBRE_LIGNES; gridY++) {
                EtatSurfaceMaritime etat = surfaceDeJeu.getEtatDeSurfaceMaritime(gridX, gridY);
                if (!isPlayer)
                    if ((etat == EtatSurfaceMaritime.JOUEUR_A_TIRE)
                            || (etat == EtatSurfaceMaritime.JOUEUR_ET_ORDI_ONT_TIRE)) {
                        gc.setStroke(Color.BLUE);
                        gc.strokeLine(shiftX + gridX * cellSize, shiftY + gridY * cellSize,
                                shiftX + (gridX + 1) * cellSize,
                                shiftY + (gridY + 1) * cellSize);
                        gc.strokeLine(shiftX + gridX * cellSize, shiftY + (gridY + 1) * cellSize,
                                shiftX + (gridX + 1) * cellSize, shiftY + gridY * cellSize);
                    }
                if (isPlayer)
                    if ((etat == EtatSurfaceMaritime.ORDI_A_TIRE)
                            || (etat == EtatSurfaceMaritime.JOUEUR_ET_ORDI_ONT_TIRE)) {

                        gc.setStroke(Color.RED);
                        int circleWidth = (int) (cellSize * 0.7);
                        int centerX = shiftX + gridX * cellSize + cellSize / 2 - circleWidth / 2;
                        int centerY = shiftY + gridY * cellSize + cellSize / 2 - circleWidth / 2;
                        gc.strokeOval(centerX, centerY, circleWidth, circleWidth);
                    }
            }
        }

        // vertical and horizontal lines
        gc.setStroke(Color.BLUE);
        for (int x = 0; x <= SurfaceDeJeu.SURFACE_DE_JEU_NBRE_COLONNES; x++) {
            int posX = x * cellSize + shiftX;
            gc.strokeLine(posX, shiftY, posX, shiftY + realHeight);
        }
        for (int y = 0; y <= SurfaceDeJeu.SURFACE_DE_JEU_NBRE_LIGNES; y++) {
            int posY = y * cellSize + shiftY;
            gc.strokeLine(shiftX, posY, shiftX + realWidth, posY);
        }

    }

    public int getCellSize() {
        int maxCellSizeX = ((int) getWidth() - 2 * DESIRED_MARGIN_SIZE) / SurfaceDeJeu.SURFACE_DE_JEU_NBRE_COLONNES;
        int maxCellSizeY = ((int) getHeight() - 2 * DESIRED_MARGIN_SIZE) / SurfaceDeJeu.SURFACE_DE_JEU_NBRE_LIGNES;
        int cellSize = Math.min(maxCellSizeX, maxCellSizeY);
        return cellSize;
    }

    public void onCellClick(MouseButton button, int gridPosX, int gridPosY) {
        controller.actionCelluleCliquee( gridPosX,  gridPosY);
        redraw();
    }

    public void onCellHover(int gridX, int gridY) {
        // System.out.println("onCellHover(" + gridX + "/" + gridY + ")");

    }
}

//endregion
