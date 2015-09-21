package mfiari.fireemblem.game.keyevent;

import mfiari.fireemblem.game.terrain.Case;
import mfiari.fireemblem.game.terrain.GamePlatform;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import mfiari.lib.game.position.Position;

public class MapKeyAction implements KeyListener {

    private Border oldBorder;
    private Border newBorder;
    private GamePlatform plateauDeJeu;
    private Map<Case, JComponent> componentZone;
    private Case currentZone;
    protected PropertyChangeSupport pcsControlleurVue;

    public MapKeyAction() {
        this.pcsControlleurVue = new PropertyChangeSupport(this);
    }

    public MapKeyAction(GamePlatform plateauDeJeu, Map<Case, JComponent> componentZone) {
        this.plateauDeJeu = plateauDeJeu;
        this.componentZone = componentZone;
        this.currentZone = this.plateauDeJeu.getZones().get(0);
        this.oldBorder = this.componentZone.get(this.currentZone).getBorder();
        this.newBorder = new LineBorder(Color.yellow, 10);
        this.componentZone.get(this.currentZone).setBorder(this.newBorder);
        this.pcsControlleurVue = new PropertyChangeSupport(this);
    }

    public void init(GamePlatform plateauDeJeu, Map<Case, JComponent> componentZone) {
        this.plateauDeJeu = plateauDeJeu;
        this.componentZone = componentZone;
        this.currentZone = this.plateauDeJeu.getZones().get(0);
        this.oldBorder = this.componentZone.get(this.currentZone).getBorder();
        this.newBorder = new LineBorder(Color.yellow, 10);
        this.componentZone.get(this.currentZone).setBorder(this.newBorder);
    }
    
    public void changeZone (Case zone, JComponent component, Position p) {
        Case c = this.getZoneByPosition(p);
        this.componentZone.remove(c);
        this.componentZone.put(zone, component);
    }

    public Case getZoneByPosition(Position p) {
        for (Case zone : this.plateauDeJeu.getZones()) {
            if (zone.getPosition().equalsXY(p)) {
                return zone;
            }
        }
        return null;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    private void modifyCursor(Case c, Case z) {
        if (z != null) {
            Position oldPosition = c.getPosition();
            this.componentZone.get(this.currentZone).setBorder(this.oldBorder);
            this.currentZone = z;
            this.componentZone.get(this.currentZone).setBorder(this.newBorder);
            Position newPosition = this.currentZone.getPosition();
            this.pcsControlleurVue.firePropertyChange("cursorPosition", oldPosition, newPosition);
        }
    }

    public void setCursorPosition(Position pos) {
        Case zone = this.getZoneByPosition(pos);
        this.componentZone.get(this.currentZone).setBorder(this.oldBorder);
        this.currentZone = zone;
        this.componentZone.get(this.currentZone).setBorder(this.newBorder);
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case ButtonCode.BAS:
                Case c1 = this.currentZone;
                Case zone1 = this.getZoneByPosition(new Position(c1.getPosition().getPositionX() + 1, c1.getPosition().getPositionY()));
                this.modifyCursor(c1, zone1);
                break;
            case ButtonCode.DROITE:
                Case c2 = this.currentZone;
                Case zone2 = this.getZoneByPosition(new Position(c2.getPosition().getPositionX(), c2.getPosition().getPositionY() + 1));
                this.modifyCursor(c2, zone2);
                break;
            case ButtonCode.GAUCHE:
                Case c3 = this.currentZone;
                Case zone3 = this.getZoneByPosition(new Position(c3.getPosition().getPositionX(), c3.getPosition().getPositionY() - 1));
                this.modifyCursor(c3, zone3);
                break;
            case ButtonCode.HAUT:
                Case c4 = this.currentZone;
                Case zone4 = this.getZoneByPosition(new Position(c4.getPosition().getPositionX() - 1, c4.getPosition().getPositionY()));
                this.modifyCursor(c4, zone4);
                break;
            case ButtonCode.ACTION:
                this.pcsControlleurVue.firePropertyChange("action", null, null);
                break;
            case ButtonCode.ANNULATION:
                this.pcsControlleurVue.firePropertyChange("annulation", null, null);
                break;
            case ButtonCode.R:
                this.pcsControlleurVue.firePropertyChange("info", null, null);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void ajouterEcouteur(PropertyChangeListener ecouteur) {
        this.pcsControlleurVue.addPropertyChangeListener(ecouteur);
    }

}
