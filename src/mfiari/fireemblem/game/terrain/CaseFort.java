package mfiari.fireemblem.game.terrain;

import mfiari.lib.game.position.Position;
import mfiari.fireemblem.game.character.Character;

public class CaseFort extends Case {

    public CaseFort() {
        this.def = 2;
        this.esq = 20;
        this.type = TypesCase.fort;
    }

    public CaseFort(Position p) {
        this.def = 2;
        this.esq = 20;
        this.position = p;
        this.type = TypesCase.fort;
    }

    @Override
    public String getNom() {
        return "Fort";
    }

    @Override
    public void effect(Character character) {
        if (character != null) {
            character.setPv(character.getPv() + ((character.getPvMax() * 20) / 100));
        }
    }
}
