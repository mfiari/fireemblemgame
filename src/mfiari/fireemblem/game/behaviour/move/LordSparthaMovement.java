package mfiari.fireemblem.game.behaviour.move;

import mfiari.fireemblem.game.terrain.TypesCase;

public class LordSparthaMovement extends MoveBehaviour {

    public LordSparthaMovement() {
        this.movementRate = 6;
    }

    @Override
    public int getDeplacement(TypesCase type) {
        switch (type) {
            case fort:
                return 2;
            case montagne:
                return 4;
            default:
                return super.getDeplacement(type);
        }
    }
}
