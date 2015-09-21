package mfiari.fireemblem.game.state;

import mfiari.fireemblem.game.character.Character;
import mfiari.fireemblem.game.controler.Chapter;
import mfiari.lib.game.position.Position;

public class ActionMenuState extends DefaultState {

    public ActionMenuState(Chapter chapter) {
        super(chapter);
    }

    @Override
    public void cancel() {
        Character perso = this.chapter.getPersoEnCours();
        Position pos = new Position(perso.getPosition());
        perso.setPosition(this.chapter.getOldPosition());
        this.chapter.fire(this.chapter.DEPLACE_PERSO, perso, pos);
        this.chapter.fire(this.chapter.AFFICHE_DEPLACEMENT_DISPONIBLE, this.chapter.getZonesSelectionner(), null);
        this.chapter.fire(this.chapter.AFFICHE_ATTAQUE_DISPONIBLE, this.chapter.getZonesAtkSelectionner(), null);
        this.chapter.fire(this.chapter.CANCEL_ACTION_PERSO, null, null);
        this.chapter.setState(new MoveState(this.chapter));
    }

}
