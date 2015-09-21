/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.swing;

import mfiari.fireemblem.game.character.Character;
import mfiari.fireemblem.game.controler.Menu;
import mfiari.fireemblem.game.keyevent.MenuKeyAction;
import mfiari.fireemblem.game.media.character.CharacterImage;
import mfiari.fireemblem.game.object.Objet;
import mfiari.fireemblem.game.texte.TexteVueMenu;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
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
public class VueSwing_menu extends VueSwing {
    
    private final Menu menu;
    private final TexteVueMenu textes;
    
    public VueSwing_menu (final Menu menu) {
        super(TexteVueMenu.getInstance(), menu);
        this.menu = menu;
        this.textes = TexteVueMenu.getInstance();
        this.menu.ajouterEcouteur(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (menu.MENU.equals(evt.getPropertyName())) {
                    menu();
                } else if (menu.PICK_UNIT.equals(evt.getPropertyName())) {
                    pickUnits(0);
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
        ListComponentNumber componentNumber = new ListComponentNumber();
        JButton buttonPickUnits = new JButton("Choisir unités");
        componentNumber.addKeyAction(buttonPickUnits, 1);
        box.add(buttonPickUnits);
        JButton buttonEchange = new JButton("Echange");
        componentNumber.addKeyAction(buttonEchange, 2);
        box.add(buttonEchange);
        JButton buttonSauvegarder = new JButton("Sauvegarder");
        componentNumber.addKeyAction(buttonSauvegarder, 3);
        box.add(buttonSauvegarder);
        JButton buttonCarte = new JButton("Carte");
        componentNumber.addKeyAction(buttonCarte, 4);
        box.add(buttonCarte);
        JButton buttonPlayChapter = new JButton("Lancer chapitre");
        componentNumber.addKeyAction(buttonPlayChapter, 5);
        box.add(buttonPlayChapter);
        Ecran.panel.cacherNord();
        Ecran.panel.ajouterCentre(box);
        Ecran.fenetreDuJeu.addKeyBoardManager(new KeyDispatcher(new ActionPerso(this.menu, this, actions, componentNumber)));
    }
    
    private void pickUnits (final int indice) {
        List<Character> characters = this.menu.getPartie().getPersonnages();
        JComponent[] components = new JComponent[characters.size()];
        Box box = Box.createHorizontalBox();
        Box leftBox = Box.createVerticalBox();
        Box rightBox = Box.createVerticalBox();
        JPanel panelInfoPerso = new JPanel();
        JPanel panelArme = new JPanel();
        JPanel panelInfo = new JPanel();
        JPanel panelPerso = new JPanel(new GridLayout(characters.size()/2, 2));
        leftBox.add(panelInfoPerso);
        leftBox.add(panelArme);
        rightBox.add(panelInfo);
        rightBox.add(panelPerso);
        box.add(leftBox);
        box.add(rightBox);
        
        panelInfo.add(new JLabel("PRESS START"));
        panelInfo.add(new JLabel("UNITS : " + this.menu.getChapter().getPlateauDeJeu().getPersonnages().size() + " / " + this.menu.getChapter().getMaxCharacterUnits()));
        
        for (int i = 0 ; i < characters.size() ; i++) {
            Character character = characters.get(i);
            Box panel = Box.createHorizontalBox();
            panel.add(new PanelImage(CharacterImage.getImageIconMapFromPersonnage(character), 30, 30));
            panel.add(new JLabel(character.getName()));
            panelPerso.add(panel);
            components[i] = panel;
        }
        Character character = characters.get(indice);
        /* info perso */
        Box boxPerso = Box.createHorizontalBox();
        PanelImage panelImage = new PanelImage(CharacterImage.getImageMenuFromPersonnage(character), 70, 50);
        boxPerso.add(panelImage);
        Box boxStat = Box.createVerticalBox();
        JLabel labelPersoName = new JLabel(character.getName());
        boxStat.add(labelPersoName);
        JLabel labelPersoPV = new JLabel("PV  " + character.getPv() + "/" + character.getPvMax());
        boxStat.add(labelPersoPV);
        boxPerso.add(boxStat);
        panelInfoPerso.add(boxPerso);
        /* arme */
        JPanel panel = new JPanel(new GridLayout(5, 1));
        for (Objet obj : character.getObjets()) {
            if (obj != null) {
                panel.add(new JLabel(obj.getName()));
            }
        }
        panelArme.add(panel);
        Ecran.panel.ajouterCentre(box);

        MenuKeyAction menuKeyAction = new MenuKeyAction(components, indice);
        menuKeyAction.ajouterEcouteur(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(MenuKeyAction.UP)) {
                    pickUnitsUp(indice);
                } else if (evt.getPropertyName().equals(MenuKeyAction.DOWN)) {
                    pickUnitsDown(indice);
                } else if (evt.getPropertyName().equals(MenuKeyAction.LEFT)) {
                    pickUnitsLeft(indice);
                } else if (evt.getPropertyName().equals(MenuKeyAction.RIGHT)) {
                    pickUnitsRight(indice);
                } else if (evt.getPropertyName().equals(MenuKeyAction.ACTION)) {
                    pickUnitsAction(indice);
                } else if (evt.getPropertyName().equals(MenuKeyAction.CANCEL)) {
                    pickUnitsCancel();
                }
            }
        });
        Ecran.fenetreDuJeu.addKeyBoardManager(new mfiari.fireemblem.game.keyevent.KeyDispatcher(menuKeyAction));
    }
    
    private void pickUnitsUp (int indice) {
        if (indice > 1) {
            indice -= 2;
            this.pickUnits(indice);
        }
    }
    
    private void pickUnitsDown (int indice) {
        List<Character> characters = this.menu.getPartie().getPersonnages();
        if (indice < characters.size()-2) {
            indice += 2;
            this.pickUnits(indice);
        }
    }
    
    private void pickUnitsLeft (int indice) {
        if (indice > 0) {
            indice--;
            this.pickUnits(indice);
        }
    }
    
    private void pickUnitsRight (int indice) {
        List<Character> characters = this.menu.getPartie().getPersonnages();
        if (indice < characters.size()-1) {
            indice++;
            this.pickUnits(indice);
        }
    }
    
    private void pickUnitsAction (int indice) {
        
    }
    
    private void pickUnitsCancel () {
        this.reprendre();
    }
    
}
