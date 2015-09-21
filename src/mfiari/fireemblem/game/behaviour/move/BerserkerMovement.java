/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.behaviour.move;

import mfiari.fireemblem.game.terrain.TypesCase;

/**
 *
 * @author mike
 */
public class BerserkerMovement extends MoveBehaviour {

    public BerserkerMovement() {
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
