package mfiari.fireemblem.game.behaviour.move;

import mfiari.fireemblem.game.terrain.TypesCase;

public class FighterMovement extends MoveBehaviour {

    public FighterMovement() {
        this.movementRate = 5;
    }

    @Override
    public int getDeplacement(TypesCase type) {
        switch (type) {
            case fort:
                return 2;
            case montagne:
                return 3;
            default:
                return super.getDeplacement(type);
        }
    }
}
