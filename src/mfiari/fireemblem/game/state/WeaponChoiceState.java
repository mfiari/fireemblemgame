package mfiari.fireemblem.game.state;

import mfiari.fireemblem.game.character.Character;
import mfiari.fireemblem.game.controler.Chapter;
import mfiari.fireemblem.game.controler.Chapter.actionPerso;
import java.util.ArrayList;
import java.util.List;

public class WeaponChoiceState extends DefaultState {

    public WeaponChoiceState(Chapter chapter) {
        super(chapter);
    }

    @Override
    public void cancel() {
        List<actionPerso> list = new ArrayList<>();
        List<Character> ennemies = this.chapter.getEnnemies();
        if (!ennemies.isEmpty()) {
            list.add(actionPerso.attaquer);
        }
        list.add(actionPerso.objet);
        List<Character> alies = this.chapter.getAlies();
        if (!alies.isEmpty()) {
            list.add(actionPerso.echange);
        }
        list.add(actionPerso.attendre);
        actionPerso actions[] = new actionPerso[list.size()];
        for (int i = 0; i < list.size(); i++) {
            actions[i] = list.get(i);
        }
        this.chapter.fire(this.chapter.AFFICHE_ACTION_PERSO, actions, null);
        this.chapter.setState(new ActionMenuState(this.chapter));

    }

}
