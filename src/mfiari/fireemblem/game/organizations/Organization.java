/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiari.fireemblem.game.organizations;

import mfiari.fireemblem.game.controler.Chapter;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author mike
 */
public class Organization extends ObservedSubjectAbstract {

    public String operatingMode;
    public Organization parent;
    protected PropertyChangeSupport pcsControlleurVue;

    public Organization() {
        this.pcsControlleurVue = new PropertyChangeSupport(this);
    }

    public String getOperatingMode() {
        return operatingMode;
    }

    public void setOperatingMode(String operatingMode) {
        this.operatingMode = operatingMode;
        notifyObservers();
    }

    public Organization getParent() {
        return parent;
    }

    public void setParent(Organization parent) {
        this.parent = parent;
    }

    public void addListener(PropertyChangeListener ecouteur) {
        this.pcsControlleurVue.addPropertyChangeListener(ecouteur);
    }

    public void setOrder(Chapter.Ordre order) {
        this.pcsControlleurVue.firePropertyChange("ordre", order, null);
    }

}
