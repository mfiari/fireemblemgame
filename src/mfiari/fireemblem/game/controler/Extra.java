/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.controler;

import mfiari.fireemblem.game.media.son.MusiqueUtil;
import mfiari.lib.video.ListeDeVideo;
import mfiari.lib.video.Video;
import mfiari.lib.game.controlleur.ControlleurVue;
import mfiari.lib.game.liste.ListeDeMusique;
import mfiari.lib.game.media.Musique;

/**
 *
 * @author mike
 */
public class Extra extends ControlleurVue {
    
    public final String AFFICHE_MENU = "afficherMenu";
    
    private ListeDeMusique musiques;
    private int musiqueEnCour;
    private ListeDeVideo videos;
    private int videoEnCour;

    public Extra() {
        super(true);
    }
    
    public void menu () {
        do {
            this.pcsControlleurVue.firePropertyChange("afficherMenu", null, null);
            switch (this.choix) {
                case (1):
                    this.enLigne();
                    break;
                case (2):
                    this.map();
                    break;
                case (3):
                    this.arene();
                    break;
                case (4):
                    this.musique();
                    break;
                case (5):
                    this.video();
                    break;
                case (6):
                    this.configuration();
                    break;
            }
        } while (choix != 0);
    }
    
    private ListeDeMusique getMusiques () {
        return this.musiques;
    }
    
    private Musique getMusique () {
        return this.musiques.getMusique(this.musiqueEnCour);
    }
    
    private ListeDeVideo getVideos () {
        return this.videos;
    }
    
    private Video getVideo () {
        return this.videos.getVideo(this.videoEnCour);
    }
    
    private void enLigne() {
        
    }
    
    private void map() {
        
    }
    
    private void arene () {
        
    }

    private void musique() {
        MusiqueUtil musiqueUtil = new MusiqueUtil();
        this.musiques = musiqueUtil.getAll();
        do {
            this.pcsControlleurVue.firePropertyChange("musique", null, null);
            if (this.choix != -1) {
                this.musiqueEnCour = this.choix -1;
                if (this.getMusique() != null) {
                    this.getMusique().start(true);
                    do {
                        this.pcsControlleurVue.firePropertyChange("playMusique", null, null);
                        if (this.choix == 1) {
                            this.getMusique().stop();
                        }
                    } while (this.choix != 0);
                    this.getMusique().terminer();
                }
            }
        } while (this.choix != -1);
    }

    private void video() {
        //this.videos = Videos.getListeDeVideo();
        do {
            this.pcsControlleurVue.firePropertyChange("video", null, null);
            if (this.choix != -1) {
                this.videoEnCour = this.choix -1;
                this.pcsControlleurVue.firePropertyChange("playVideo", null, null);
            }
        } while (this.choix != -1);
    }
    
    private void configuration() {
        
    }
    
}
