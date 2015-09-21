package mfiari.fireemblem.game.swing;

import javax.swing.JComponent;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import mfiari.lib.game.swing.FenetreDuJeu;

public class MyPopup {

    private Popup popup;
    private int x;
    private int y;
    private final FenetreDuJeu window;
    private JComponent component;
    private final PopupFactory popupFactory;
    private final Alignement alignement;

    public enum Alignement {

        CENTER
    }

    public MyPopup(FenetreDuJeu window, Alignement alignement) {
        this.window = window;
        this.popupFactory = PopupFactory.getSharedInstance();
        this.alignement = alignement;
    }

    public MyPopup(int x, int y, FenetreDuJeu window) {
        this.x = x;
        this.y = y;
        this.window = window;
        this.popupFactory = PopupFactory.getSharedInstance();
        this.alignement = null;
    }

    public void addComponent(JComponent component, boolean show) {
        this.component = component;
        if (show) {
            this.show();
        }
    }

    public void show() {
        this.hide();
        if (this.alignement == null) {
            this.popup = this.popupFactory.getPopup(this.window, this.component, this.window.getX() + this.x, this.window.getY() + this.y);
        } else {
            switch (this.alignement) {
                case CENTER:
                    this.popup = this.popupFactory.getPopup(this.window, this.component,
                            this.window.getX() + ((this.window.getWidth() / 2) - (this.component.getWidth() / 2)),
                            this.window.getY() + ((this.window.getHeight() / 2) - (this.component.getHeight() / 2)));
                    break;
            }
        }
        this.popup.show();
    }

    public void hide() {
        if (this.popup != null) {
            this.popup.hide();
            this.popup = null;
        }
    }

}
