package mfiari.fireemblem.game.terrain;

import mfiari.lib.game.position.Position;
import mfiari.fireemblem.game.character.Character;

public class CaseChateau extends Case {

    public CaseChateau() {
        this.def = 0;
        this.esq = 0;
        this.type = TypesCase.chateau;
    }

    public CaseChateau(Position p) {
        this.def = 0;
        this.esq = 0;
        this.position = p;
        this.type = TypesCase.chateau;
    }

    @Override
    public String getNom() {
        return "Château";
    }

    @Override
    public void effect(Character character) {

    }
}
