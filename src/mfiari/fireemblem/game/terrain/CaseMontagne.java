package mfiari.fireemblem.game.terrain;

import mfiari.lib.game.position.Position;
import mfiari.fireemblem.game.character.Character;

public class CaseMontagne extends Case {

    public CaseMontagne() {
        this.def = 1;
        this.esq = 30;
        this.type = TypesCase.montagne;
    }

    public CaseMontagne(Position p) {
        this.def = 1;
        this.esq = 30;
        this.position = p;
        this.type = TypesCase.montagne;
    }

    @Override
    public String getNom() {
        return "Montagne";
    }

    @Override
    public void effect(Character character) {

    }
}
