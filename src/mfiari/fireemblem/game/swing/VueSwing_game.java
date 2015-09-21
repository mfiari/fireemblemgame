/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.swing;

import mfiari.fireemblem.game.chapters.Chapters;
import mfiari.fireemblem.game.chapters.Difficulte;
import mfiari.fireemblem.game.controler.Game;
import mfiari.fireemblem.game.media.image.util.ImageUtil;
import mfiari.fireemblem.game.texte.TexteVueGame;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import mfiari.lib.game.clavier.ActionPerso;
import mfiari.lib.game.clavier.CodeBouton;
import mfiari.lib.game.clavier.KeyDispatcher;
import mfiari.lib.game.clavier.ListComponentNumber;
import mfiari.lib.game.clavier.ListKeyAction;
import mfiari.lib.game.swing.BoutonImage;
import mfiari.lib.game.swing.Ecran;
import mfiari.lib.game.swing.PanelImage;
import mfiari.lib.game.swing.VueSwing;

/**
 *
 * @author mike
 */
public class VueSwing_game extends VueSwing {
    
    private final Game game;
    private final TexteVueGame textes;
    
    public VueSwing_game (Game g) {
        super(TexteVueGame.getInstance(), g);
        this.game = g;
        this.textes = TexteVueGame.getInstance();
        this.game.ajouterEcouteur(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (game.CHOIX_MAP.equals(evt.getPropertyName())) {
                    choixMap();
                } else if (game.CHOIX_DIFFICULTE.equals(evt.getPropertyName())) {
                    choixDifficulte((Chapters) evt.getOldValue(), (Difficulte[]) evt.getNewValue());
                }
                attendre();
            }
        });
    }
    
    private void choixMap () {
        JPanel panel = new JPanel(new GridLayout(1, 3));
        ImageUtil imageUtil = new ImageUtil();
        BoutonImage boutonBindingBlade = new BoutonImage(imageUtil.getBindingBlade());
        BoutonImage boutonBlazingsword = new BoutonImage(imageUtil.getBlazingSword());
        BoutonImage boutonPathOfRadiance = new BoutonImage(imageUtil.getPathOfRadiance());
        panel.add(boutonBindingBlade);
        panel.add(boutonBlazingsword);
        panel.add(boutonPathOfRadiance);
        ListKeyAction actions = new ListKeyAction();
        actions.addKeyAction(CodeBouton.GAUCHE, ActionPerso.DECREMENTE);
        actions.addKeyAction(CodeBouton.DROITE, ActionPerso.INCREMENTE);
        actions.addKeyAction(CodeBouton.ACTION, ActionPerso.ACTION);
        ListComponentNumber componentNumber = new ListComponentNumber();
        componentNumber.addKeyAction(boutonBindingBlade, 1);
        componentNumber.addKeyAction(boutonBlazingsword, 2);
        componentNumber.addKeyAction(boutonPathOfRadiance, 3);
        Ecran.panel.ajouterCentre(panel);
        Ecran.fenetreDuJeu.addKeyBoardManager(new KeyDispatcher(new ActionPerso(this.game, this, actions, componentNumber)));
    }
    
    private void choixDifficulte (Chapters chapters, Difficulte[] difficultes) {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        Box boxLeft = Box.createVerticalBox();
        ListComponentNumber componentNumber = new ListComponentNumber();
        for (int i = 0 ; i < difficultes.length ; i++) {
            JButton button = new JButton(difficultes[i].name());
            componentNumber.addKeyAction(button, i+1);
            boxLeft.add(button);
        }
        Box boxRight = Box.createVerticalBox();
        ImageUtil imageUtil = new ImageUtil();
        switch (chapters) {
            case binding_blade :
                boxRight.add(new JLabel("Binding blade"));
                boxRight.add(new PanelImage(imageUtil.getBindingBlade()));
                break;
            case blazing_sword :
                boxRight.add(new JLabel("Blazing sword"));
                boxRight.add(new PanelImage(imageUtil.getBlazingSword()));
                break;
            case path_of_radiance :
                boxRight.add(new JLabel("Path of radiance"));
                boxRight.add(new PanelImage(imageUtil.getPathOfRadiance()));
                break;
        }
        ListKeyAction actions = new ListKeyAction();
        actions.addKeyAction(CodeBouton.HAUT, ActionPerso.DECREMENTE);
        actions.addKeyAction(CodeBouton.BAS, ActionPerso.INCREMENTE);
        actions.addKeyAction(CodeBouton.ACTION, ActionPerso.ACTION);
        panel.add(boxLeft);
        panel.add(boxRight);
        Ecran.panel.ajouterCentre(panel);
        Ecran.fenetreDuJeu.addKeyBoardManager(new KeyDispatcher(new ActionPerso(this.game, this, actions, componentNumber)));
    }
    
}
