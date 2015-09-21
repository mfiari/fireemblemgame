package mfiari.fireemblem.game.behaviour.move;

import mfiari.fireemblem.game.terrain.TypesCase;

public class LordEquusMovement extends MoveBehaviour {

    public LordEquusMovement() {
        this.movementRate = 7;
    }

    @Override
    public int getDeplacement(TypesCase type) {
        switch (type) {
            case fort:
                return 2;
            case montagne:
                return 6;
            default:
                return super.getDeplacement(type);
        }
    }
}
