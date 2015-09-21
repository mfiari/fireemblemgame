package mfiari.fireemblem.game.behaviour.move;

import mfiari.fireemblem.game.terrain.TypesCase;

public class KnightMovement extends MoveBehaviour {

    public KnightMovement() {
        this.movementRate = 4;
    }

    @Override
    public int getDeplacement(TypesCase type) {
        switch (type) {
            case fort:
                return 2;
            case montagne:
                return 100;
            default:
                return super.getDeplacement(type);
        }
    }
}
