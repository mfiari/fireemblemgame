package mfiari.fireemblem.game.keyevent;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;


public class MenuKeyAction implements KeyListener  {
    
    public final static String UP = "up";
    public final static String DOWN = "down";
    public final static String LEFT = "left";
    public final static String RIGHT = "right";
    public final static String ACTION = "action";
    public final static String CANCEL = "cancel";
    
    private Border oldBorder;
    private Border newBorder;
    private JComponent[] components;
    protected PropertyChangeSupport pcsControlleurVue;
    private int cursor;
    
    public MenuKeyAction () {
    	this.cursor = 0;
        this.pcsControlleurVue = new PropertyChangeSupport(this);
    }
    
    public MenuKeyAction (JComponent[] components) {
        this(components, 0);
    }
    
    public MenuKeyAction (JComponent[] components, int indice) {
        this.components = components;
        this.cursor = indice;
        this.newBorder = new LineBorder(Color.yellow, 5);
        if (this.components.length > 0) {
            this.oldBorder = this.components[this.cursor].getBorder();
            this.components[this.cursor].setBorder(this.newBorder);
        }
        this.pcsControlleurVue = new PropertyChangeSupport(this);
    }
    
    public void init (JComponent[] components) {
    	this.components = components;
        this.oldBorder = this.components[this.cursor].getBorder();
        this.newBorder = new LineBorder(Color.yellow, 10);
        this.components[this.cursor].setBorder(this.newBorder);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case ButtonCode.BAS :
            	if (this.cursor < this.components.length -1) {
            		this.components[this.cursor].setBorder(this.oldBorder);
            		this.cursor++;
                    this.components[this.cursor].setBorder(this.newBorder);
                    this.pcsControlleurVue.firePropertyChange(DOWN, null, null);
            	}
                break;
            case ButtonCode.DROITE :
                this.pcsControlleurVue.firePropertyChange(RIGHT, null, null);
                break;
            case ButtonCode.GAUCHE :
                this.pcsControlleurVue.firePropertyChange(LEFT, null, null);
                break;
            case ButtonCode.HAUT :
            	if (this.cursor > 0) {
            		this.components[this.cursor].setBorder(this.oldBorder);
            		this.cursor--;
                    this.components[this.cursor].setBorder(this.newBorder);
                    this.pcsControlleurVue.firePropertyChange(UP, null, null);
            	}
                break;
            case ButtonCode.ACTION :
                if (((JButton)this.components[this.cursor]).getActionListeners().length == 0) {
                    this.pcsControlleurVue.firePropertyChange(ACTION, null, null);
                } else {
                    ((JButton)this.components[this.cursor]).getActionListeners()[0].actionPerformed(null);
                }
            	
                break;
            case ButtonCode.ANNULATION :
                this.pcsControlleurVue.firePropertyChange(CANCEL, null, null);
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