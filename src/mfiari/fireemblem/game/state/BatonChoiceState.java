/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.state;

import mfiari.fireemblem.game.controler.Chapter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mike
 */
public class BatonChoiceState extends DefaultState {

    public BatonChoiceState(Chapter chapter) {
        super(chapter);
    }
    
    @Override
    public void action() {
        this.chapter.combatSoin();
    }

    @Override
    public void cancel() {
        List<Chapter.actionPerso> list = new ArrayList<>();
        List<mfiari.fireemblem.game.character.Character> ennemies = this.chapter.getEnnemies();
        if (!ennemies.isEmpty()) {
            list.add(Chapter.actionPerso.attaquer);
        }
        list.add(Chapter.actionPerso.objet);
        List<mfiari.fireemblem.game.character.Character> alies = this.chapter.getAlies();
        if (!alies.isEmpty()) {
            list.add(Chapter.actionPerso.echange);
        }
        list.add(Chapter.actionPerso.attendre);
        Chapter.actionPerso actions[] = new Chapter.actionPerso[list.size()];
        for (int i = 0; i < list.size(); i++) {
            actions[i] = list.get(i);
        }
        this.chapter.fire(this.chapter.AFFICHE_ACTION_PERSO, actions, null);
        this.chapter.setState(new ActionMenuState(this.chapter));

    }
}
