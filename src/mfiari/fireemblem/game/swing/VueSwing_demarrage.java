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
import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import mfiari.fireemblem.game.media.character.CharacterImage;
import mfiari.lib.game.clavier.ActionPerso;
import mfiari.lib.game.clavier.CodeBouton;
import mfiari.lib.game.clavier.KeyDispatcher;
import mfiari.lib.game.clavier.ListComponentNumber;
import mfiari.lib.game.clavier.ListKeyAction;
import mfiari.lib.game.swing.AfficheurSaisieTexte;
import mfiari.lib.game.swing.Ecran;
import mfiari.lib.game.swing.PanelDeTexteAffichage;
import mfiari.lib.game.swing.PanelDeTexteSuivant;
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
        this.demarrage.ajouterEcouteur((PropertyChangeEvent evt) -> {
            if (demarrage.CHOIX_NOM.equals(evt.getPropertyName())) {
                saisieNom ();
            } else if (demarrage.PROLOGUE.equals(evt.getPropertyName())) {
                prologue ();
            } else {
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
    
    private void saisieNom () {
        PanelDeTexteAffichage texteAffichage = new PanelDeTexteAffichage("Entrez votre nom");
        AfficheurSaisieTexte saisieTexte = new AfficheurSaisieTexte(this, "");
        Ecran.panel.ajouterNord(texteAffichage);
        Ecran.panel.ajouterCentre(saisieTexte);
        this.attendre();
        this.demarrage.setNom(saisieTexte.getTexte());
    }
    
    private void prologue() {
        Ecran.panel.cacherEst();
        Ecran.panel.cacherSud();
        CharacterImage characterImage = new CharacterImage();
        PanelImage imageAthos = new PanelImage(characterImage.getImageDialogueRight("athos"));
        PanelImage imageEliwood = new PanelImage(characterImage.getImageDialogueRight("eliwood"));
        PanelImage imageLyn = new PanelImage(characterImage.getImageDialogueRight("lyn"));
        PanelImage imageHector = new PanelImage(characterImage.getImageDialogueRight("hector"));
        PanelImage imageRoy = new PanelImage(characterImage.getImageDialogueRight("roy"));
        PanelImage imageIke = new PanelImage(characterImage.getImageDialogueRight("ike"));
        PanelImage imageMist = new PanelImage(characterImage.getImageDialogueRight("mist"));
        PanelDeTexteSuivant panelTexteSuivant1 = new PanelDeTexteSuivant(this);
        panelTexteSuivant1.setAffichageTexte(false);
        panelTexteSuivant1.ajouterTexte("Bonjour jeune stratège.");
        panelTexteSuivant1.ajouterTexte("Je m'appelle Athos, je suis un archimage.");
        panelTexteSuivant1.ajouterTexte("Un nécromancien à résuussiter les énnemis du passé sur les continents de Tellius et d'elibe et en a pris le contrôle.");
        panelTexteSuivant1.ajouterTexte("Son but est de réveiller les esprits maléfiques passé.");
        panelTexteSuivant1.ajouterTexte("Je suis aujourd'hui prisonier de ce nécromancien.");
        panelTexteSuivant1.ajouterTexte("C'est pourquoi je te demande de bien vouloir partir en quête pour l'arrêter.");
        panelTexteSuivant1.ajouterTexte("Mais ne t'inquiète pas, j'ai réussi à sauver quelques héros pour t'aider dans ta quêtes.");
        Ecran.panel.ajouterCentre(imageAthos);
        Ecran.panel.ajouterNord(panelTexteSuivant1);
        panelTexteSuivant1.lancerAffichage();
        
        JPanel panelImage = new JPanel(new GridLayout(1, 2));
        PanelDeTexteSuivant panelTexteSuivant2 = new PanelDeTexteSuivant(this);
        panelTexteSuivant2.setAffichageTexte(false);
        panelTexteSuivant2.ajouterTexte("Voici Eliwood de pherae, qui vient d'Elibe.");
        panelImage.add(imageEliwood);
        panelImage.add(imageAthos);
        Ecran.panel.ajouterCentre(panelImage);
        Ecran.panel.ajouterNord(panelTexteSuivant2);
        panelTexteSuivant2.lancerAffichage();
        
        PanelDeTexteSuivant panelTexteSuivant3 = new PanelDeTexteSuivant(this);
        panelTexteSuivant3.setAffichageTexte(false);
        panelTexteSuivant3.ajouterTexte("Voici Lyndis de Caelin, qui vient d'Elibe.");
        panelImage.removeAll();
        panelImage.add(imageLyn);
        panelImage.add(imageAthos);
        Ecran.panel.ajouterCentre(panelImage);
        Ecran.panel.ajouterNord(panelTexteSuivant3);
        panelTexteSuivant3.lancerAffichage();
        
        PanelDeTexteSuivant panelTexteSuivant4 = new PanelDeTexteSuivant(this);
        panelTexteSuivant4.setAffichageTexte(false);
        panelTexteSuivant4.ajouterTexte("Voici Hector d'Ositia, qui vient d'Elibe.");
        panelImage.removeAll();
        panelImage.add(imageHector);
        panelImage.add(imageAthos);
        Ecran.panel.ajouterCentre(panelImage);
        Ecran.panel.ajouterNord(panelTexteSuivant4);
        panelTexteSuivant4.lancerAffichage();
        
        PanelDeTexteSuivant panelTexteSuivant5 = new PanelDeTexteSuivant(this);
        panelTexteSuivant5.setAffichageTexte(false);
        panelTexteSuivant5.ajouterTexte("Voici Roy de pherae, qui vient d'Elibe.");
        panelImage.removeAll();
        panelImage.add(imageRoy);
        panelImage.add(imageAthos);
        Ecran.panel.ajouterCentre(panelImage);
        Ecran.panel.ajouterNord(panelTexteSuivant5);
        panelTexteSuivant5.lancerAffichage();
        
        PanelDeTexteSuivant panelTexteSuivant6 = new PanelDeTexteSuivant(this);
        panelTexteSuivant6.setAffichageTexte(false);
        panelTexteSuivant6.ajouterTexte("Voici Ike des mercenaires de Greil, qui vient de Tellius.");
        panelImage.removeAll();
        panelImage.add(imageIke);
        panelImage.add(imageAthos);
        Ecran.panel.ajouterCentre(panelImage);
        Ecran.panel.ajouterNord(panelTexteSuivant6);
        panelTexteSuivant6.lancerAffichage();
        
        PanelDeTexteSuivant panelTexteSuivant7 = new PanelDeTexteSuivant(this);
        panelTexteSuivant7.setAffichageTexte(false);
        panelTexteSuivant7.ajouterTexte("Voici Mist des mercenaires de Greil, qui vient de Tellius.");
        panelImage.removeAll();
        panelImage.add(imageMist);
        panelImage.add(imageAthos);
        Ecran.panel.ajouterCentre(panelImage);
        Ecran.panel.ajouterNord(panelTexteSuivant7);
        panelTexteSuivant7.lancerAffichage();
        
        PanelDeTexteSuivant panelTexteSuivant8 = new PanelDeTexteSuivant(this);
        panelTexteSuivant7.setAffichageTexte(false);
        panelTexteSuivant8.ajouterTexte("Grâces à mes pouvoir, vous serez capable de voyager dans les différents mondes pour combattre le necromancien.");
        panelTexteSuivant8.ajouterTexte("Au cours de votre quêtes, il se peut que vous ayez à combattre d'ancien ennemies.");
        panelTexteSuivant8.ajouterTexte("Vous trouverez aussi des alliées capturer.");
        panelTexteSuivant8.ajouterTexte("Sauvez les pour qu'ils vous aident dans votre quêtes.");
        panelTexteSuivant8.ajouterTexte("Parcourrez les mondes et sauvez les du necromancien.");
        panelTexteSuivant8.ajouterTexte("Notre destin est entre vos main.");
        Ecran.panel.ajouterCentre(imageAthos);
        Ecran.panel.ajouterNord(panelTexteSuivant8);
        panelTexteSuivant8.lancerAffichage();
    }
    
}
