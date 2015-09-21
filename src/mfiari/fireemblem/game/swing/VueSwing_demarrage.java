/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.swing;

import mfiari.fireemblem.game.connexionBD.Partie;
import mfiari.fireemblem.game.controler.Demarrage;
import mfiari.fireemblem.game.media.image.util.ImageUtil;
import mfiari.fireemblem.game.texte.TexteVueDemarage;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
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
import mfiari.lib.game.texte.Langue;

/**
 *
 * @author mike
 */
public class VueSwing_demarrage extends VueSwing {
    
    private final Demarrage demarrage;
    private final TexteVueDemarage textes;
    private final ImageUtil imageUtil;
    
    public VueSwing_demarrage (Demarrage demarage) {
        super(TexteVueDemarage.getInstance(), demarage);
        this.demarrage = demarage;
        this.imageUtil = new ImageUtil();
        this.textes = TexteVueDemarage.getInstance();
        this.demarrage.ajouterEcouteur(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (demarrage.CHOIX_LANGUE.equals(evt.getPropertyName())) {
                    choixLangue((Langue[]) evt.getOldValue());
                } else if (demarrage.CHOIX_PARTIE.equals(evt.getPropertyName())) {
                    choixPartie((List<Partie>)evt.getOldValue());
                } else if (demarrage.MENU.equals(evt.getPropertyName())) {
                    menu((List<Partie>)evt.getOldValue());
                }
                attendre();
            }
        });
    }

    private void choixLangue(Langue[] langues) {
        Box box = Box.createVerticalBox();
        ListKeyAction actions = new ListKeyAction();
        actions.addKeyAction(CodeBouton.HAUT, ActionPerso.DECREMENTE);
        actions.addKeyAction(CodeBouton.BAS, ActionPerso.INCREMENTE);
        actions.addKeyAction(CodeBouton.ACTION, ActionPerso.ACTION);
        ListComponentNumber componentNumber = new ListComponentNumber();
        for (int i = 0 ; i < langues.length ; i++) {
            JPanel panel = new JPanel (new GridLayout(1, 2));
            PanelImage panelImage = new PanelImage(this.imageUtil.getDrapeau(langues[i]));
            panel.add(new JLabel(langues[i].toString()));
            panel.add(panelImage);
            componentNumber.addKeyAction(panel, (i+1));
            box.add(panel);
        }
        Ecran.panel.cacherNord();
        Ecran.panel.ajouterCentre(box);
        Ecran.fenetreDuJeu.addKeyBoardManager(new KeyDispatcher(new ActionPerso(this.demarrage, this, actions, componentNumber)));
    }
    
    private void choixPartie (List<Partie> parties) {
        Box box = Box.createVerticalBox();
        ListKeyAction actions = new ListKeyAction();
        actions.addKeyAction(CodeBouton.HAUT, ActionPerso.DECREMENTE);
        actions.addKeyAction(CodeBouton.BAS, ActionPerso.INCREMENTE);
        actions.addKeyAction(CodeBouton.ACTION, ActionPerso.ACTION);
        ListComponentNumber componentNumber = new ListComponentNumber();
        for (Partie partie : parties) {
            JButton buttonPartie = new JButton(partie.getName());
            componentNumber.addKeyAction(buttonPartie, partie.getId());
            box.add(buttonPartie);
        }
        Ecran.panel.cacherNord();
        Ecran.panel.ajouterCentre(box);
        Ecran.fenetreDuJeu.addKeyBoardManager(new KeyDispatcher(new ActionPerso(this.demarrage, this, actions, componentNumber)));
    }
    
    private void menu (List<Partie> parties) {
        Box box = Box.createVerticalBox();
        ListKeyAction actions = new ListKeyAction();
        actions.addKeyAction(CodeBouton.HAUT, ActionPerso.DECREMENTE);
        actions.addKeyAction(CodeBouton.BAS, ActionPerso.INCREMENTE);
        actions.addKeyAction(CodeBouton.ACTION, ActionPerso.ACTION);
        ListComponentNumber componentNumber = new ListComponentNumber();
        for (Partie partie : parties) {
            if (partie.isEmpty()) {
                JButton buttonNouvellePartie = new JButton("Nouvelle partie");
                componentNumber.addKeyAction(buttonNouvellePartie, this.demarrage.NOUVELLE_PARTIE_ACTION);
                box.add(buttonNouvellePartie);
                break;
            }
        }
        for (Partie partie : parties) {
            if (partie.isEnCours()) {
                JButton buttonContinuerChapitre = new JButton("Continuer chapitre");
                componentNumber.addKeyAction(buttonContinuerChapitre, this.demarrage.CONTINUER_ACTION);
                box.add(buttonContinuerChapitre);
                break;
            }
        }
        for (Partie partie : parties) {
            if (!partie.isEmpty()) {
                JButton buttonRecommencerChapitre = new JButton("Recommencer chapitre");
                componentNumber.addKeyAction(buttonRecommencerChapitre, this.demarrage.RECOMMENCER_ACTION);
                box.add(buttonRecommencerChapitre);
                JButton buttonEffacerDesDonnees = new JButton("Effacer des données");
                componentNumber.addKeyAction(buttonEffacerDesDonnees, this.demarrage.EFFACER_ACTION);
                box.add(buttonEffacerDesDonnees);
                break;
            }
        }
        JButton buttonExtras = new JButton("Extras");
        componentNumber.addKeyAction(buttonExtras, this.demarrage.EXTRA_ACTION);
        box.add(buttonExtras);
        Ecran.panel.cacherNord();
        Ecran.panel.ajouterCentre(box);
        Ecran.fenetreDuJeu.addKeyBoardManager(new KeyDispatcher(new ActionPerso(this.demarrage, this, actions, componentNumber)));
    }
    
}
