/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.swing;

import mfiari.fireemblem.game.controler.Extra;
import mfiari.fireemblem.game.media.image.util.ImageUtil;
import mfiari.fireemblem.game.texte.TexteVueExtra;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import mfiari.lib.game.clavier.ActionPerso;
import mfiari.lib.game.clavier.CodeBouton;
import mfiari.lib.game.clavier.KeyDispatcher;
import mfiari.lib.game.clavier.ListComponentNumber;
import mfiari.lib.game.clavier.ListKeyAction;
import mfiari.lib.game.swing.Ecran;
import mfiari.lib.game.swing.PanelImage;
import mfiari.lib.game.swing.VueSwing;

/**
 *
 * @author mike
 */
public class VueSwing_extra extends VueSwing {
    
    private final Extra extra;
    private final TexteVueExtra textes;
    private final ImageUtil imageUtil;
    
    public VueSwing_extra (final Extra extra) {
        super(TexteVueExtra.getInstance(), extra);
        this.extra = extra;
        this.imageUtil = new ImageUtil();
        this.textes = TexteVueExtra.getInstance();
        this.extra.ajouterEcouteur(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (extra.AFFICHE_MENU.equals(evt.getPropertyName())) {
                    menu();
                }
                attendre();
            }
        });
    }
    
    private void menu () {
        Box box = Box.createVerticalBox();
        ListKeyAction actions = new ListKeyAction();
        actions.addKeyAction(CodeBouton.HAUT, ActionPerso.DECREMENTE);
        actions.addKeyAction(CodeBouton.BAS, ActionPerso.INCREMENTE);
        actions.addKeyAction(CodeBouton.ACTION, ActionPerso.ACTION);
        actions.addKeyAction(CodeBouton.ANNULATION, 0);
        ListComponentNumber componentNumber = new ListComponentNumber();
        
        JPanel panelEnLigne = new JPanel (new GridLayout(1, 2));
        PanelImage enLigne = new PanelImage(this.imageUtil.getImageEnLigne());
        panelEnLigne.add(new JLabel(this.textes.enLigne));
        panelEnLigne.add(enLigne);
        componentNumber.addKeyAction(panelEnLigne, 1);
        box.add(panelEnLigne);
        
        JPanel panelMap = new JPanel (new GridLayout(1, 2));
        PanelImage map = new PanelImage(this.imageUtil.getImageMap());
        panelMap.add(new JLabel(this.textes.map));
        panelMap.add(map);
        componentNumber.addKeyAction(panelMap, 2);
        box.add(panelMap);
        
        JPanel panelArene = new JPanel (new GridLayout(1, 2));
        PanelImage arene = new PanelImage(this.imageUtil.getImageArene());
        panelArene.add(new JLabel(this.textes.arene));
        panelArene.add(arene);
        componentNumber.addKeyAction(panelArene, 3);
        box.add(panelArene);
        
        JPanel panelMusique = new JPanel (new GridLayout(1, 2));
        PanelImage musique = new PanelImage(this.imageUtil.getImageMusique());
        panelMusique.add(new JLabel("musique"));
        panelMusique.add(musique);
        componentNumber.addKeyAction(panelMusique, 4);
        box.add(panelMusique);
        
        JPanel panelVideo = new JPanel (new GridLayout(1, 2));
        PanelImage video = new PanelImage(this.imageUtil.getImageVideo());
        panelVideo.add(new JLabel("video"));
        panelVideo.add(video);
        componentNumber.addKeyAction(panelVideo, 5);
        box.add(panelVideo);
        
        JPanel panelConfiguration = new JPanel (new GridLayout(1, 2));
        PanelImage configuration = new PanelImage(this.imageUtil.getImageConfiguration());
        panelConfiguration.add(new JLabel(this.textes.configuration));
        panelConfiguration.add(configuration);
        componentNumber.addKeyAction(panelConfiguration, 6);
        box.add(panelConfiguration);
        
        Ecran.panel.ajouterCentre(box);
        Ecran.panel.cacherNord();
        Ecran.fenetreDuJeu.addKeyBoardManager(new KeyDispatcher(new ActionPerso(this.extra, this, actions, componentNumber)));
    }
    
}
