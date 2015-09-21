package mfiari.fireemblem.game.swing;

import mfiari.lib.game.position.Position;

public class Camera {

    private final int width;
    private final int height;
    private final int ligne;
    private final int colone;
    private Position centerPoint;

    public Camera() {
        this.width = 700;
        this.height = 500;
        this.ligne = 10;
        this.colone = 10;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getLigne() {
        return this.ligne;
    }

    public int getColone() {
        return this.colone;
    }

    public int getParcelleWidth() {
        return this.width / this.colone;
    }

    public int getParcelleHeight() {
        return this.height / this.ligne;
    }

    public void setCenterPoint(Position center) {
        this.centerPoint = center;
    }

    public boolean move(Position oldPos, Position newPos, int width, int height) {
        int xRayon = this.colone / 2;
        int yRayon = this.ligne / 2;
        int minX = this.centerPoint.getPositionX() - xRayon;
        int minY = this.centerPoint.getPositionY() - yRayon;
        int maxX = this.centerPoint.getPositionX() + xRayon;
        int maxY = this.centerPoint.getPositionY() + yRayon;
        if (newPos.getPositionX() == minX + 1 && minX > 0) {
            this.centerPoint.setPositionX(this.centerPoint.getPositionX() - 1);
            return true;
        } else if (newPos.getPositionX() == maxX - 1 && maxX < width) {
            this.centerPoint.setPositionX(this.centerPoint.getPositionX() + 1);
            return true;
        } else if (newPos.getPositionY() == minY + 1 && minY > 0) {
            this.centerPoint.setPositionY(this.centerPoint.getPositionY() - 1);
            return true;
        } else if (newPos.getPositionY() == maxY - 1 && maxY < height) {
            this.centerPoint.setPositionY(this.centerPoint.getPositionY() + 1);
            return true;
        }
        return false;
    }

    public void refresh(Panel panel, Position position, int niveau, Position centre,
            ComponentView components[][][], ComponentView viewComponent) {
        int xRayon = this.colone / 2;
        int yRayon = this.ligne / 2;
        int minX = centre.getPositionX() - xRayon;
        int minY = centre.getPositionY() - yRayon;
        int maxX = centre.getPositionX() + xRayon;
        int maxY = centre.getPositionY() + yRayon;
        if (minX < 0) {
            minX = 0;
        } else if (maxX > components.length) {
            minX = minX - (maxX - components.length);
        }
        if (minY < 0) {
            minY = 0;
        } else if (maxY > components[0].length) {
            minY = minY - (maxY - components[0].length);
        }
        components[position.getPositionX()][position.getPositionY()][niveau] = viewComponent;
        for (int i = 0; i < this.colone; i++) {
            for (int j = 0; j < this.ligne; j++) {
                if (position.equalsXY(new Position(minX + i, minY + j))) {
                    if (niveau == 0) {
                        panel.getPanelCentre().ajouterViewBackground(viewComponent, i, j);
                    } else {
                        panel.getPanelCentre().ajouterViewContent(viewComponent, i, j, niveau);
                    }
                }
            }
        }
    }

    public Panel getCameraView(Position centre, ComponentView components[][][]) {
        Panel panel = new Panel();
        int xRayon = this.colone / 2;
        int yRayon = this.ligne / 2;
        int minX = this.centerPoint.getPositionX() - xRayon;
        int minY = this.centerPoint.getPositionY() - yRayon;
        int maxX = this.centerPoint.getPositionX() + xRayon;
        int maxY = this.centerPoint.getPositionY() + yRayon;
        if (minX < 0) {
            maxX = maxX - minX;
            minX = 0;
        } else if (maxX > components.length) {
            minX = minX - (maxX - components.length);
            maxX = components.length;
        }
        if (minY < 0) {
            maxY = maxY - minY;
            minY = 0;
        } else if (maxY > components[0].length) {
            minY = minY - (maxY - components[0].length);
            maxY = components[0].length;
        }
        PanelCentre newPanelCentre = new PanelCentreParcelle(this.colone, this.ligne, this.width, this.height);
        for (int i = 0; i < this.colone; i++) {
            for (int j = 0; j < this.ligne; j++) {
                for (int k = 0; k < 4; k++) {
                    if (components[minX + i][minY + j][k] != null) {
                        if (k == 0) {
                            newPanelCentre.ajouterViewBackground(components[minX + i][minY + j][k], i, j);
                        } else {
                            newPanelCentre.ajouterViewContent(components[minX + i][minY + j][k], i, j, k);
                        }
                    }
                }
            }
        }
        panel.changerCentre(newPanelCentre, maxX - minX, maxY - minY, this.width, this.height);
        return panel;
    }

}
