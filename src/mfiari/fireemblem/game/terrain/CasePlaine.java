package mfiari.fireemblem.game.terrain;

import mfiari.lib.game.position.Position;
import mfiari.fireemblem.game.character.Character;

public class CasePlaine extends Case {

    public CasePlaine() {
        this.def = 0;
        this.esq = 0;
        this.type = TypesCase.plaine;
    }

    public CasePlaine(Position p) {
        this.def = 0;
        this.esq = 0;
        this.position = p;
        this.type = TypesCase.plaine;
    }

    @Override
    public String getNom() {
        return "Plaine";
    }

    @Override
    public void effect(Character character) {

    }

}
