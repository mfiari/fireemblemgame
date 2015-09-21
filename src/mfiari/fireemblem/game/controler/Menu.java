/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.controler;

import mfiari.fireemblem.game.connexionBD.Partie;
import mfiari.lib.game.controlleur.ControlleurVue;

/**
 *
 * @author mike
 */
public class Menu extends ControlleurVue {
    
    public final String MENU = "menu";
    public final String PICK_UNIT = "pickUnit";
    private final Chapter chapter;
    private final Partie partie;
    
    public Menu (Chapter chapter, Partie partie) {
        super(true);
        this.chapter = chapter;
        this.partie = partie;
    }
    
    public Chapter getChapter () {
        return this.chapter;
    }
    
    public Partie getPartie () {
        return this.partie;
    }
    
    public void start () {
        do {
            this.pcsControlleurVue.firePropertyChange(MENU, null, null);
            switch (this.choix) {
                case 1 :
                    pickUnits();
                    break;
                case 2 :
                    trade();
                    break;
                case 3 :
                    save();
                    break;
                case 4 :
                    map();
                    break;
                case 5 :
                    playChapter();
                    break;
            }
        } while (true);
    }
    
    private void pickUnits () {
        this.pcsControlleurVue.firePropertyChange(PICK_UNIT, null, null);
    }
    
    private void trade () {
        
    }
    
    private void save () {
        
    }
    
    private void map () {
        
    }
    
    private void playChapter () {
        this.chapter.run();
    }
    
}
