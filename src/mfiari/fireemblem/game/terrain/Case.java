/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiari.fireemblem.game.terrain;

import mfiari.lib.game.position.Position;
import mfiari.fireemblem.game.character.Character;

/**
 *
 * @author mike
 */
public abstract class Case {

    protected int def;
    protected int esq;
    protected Position position;
    protected TypesCase type;

    public Position getPosition() {
        return this.position;
    }

    public abstract String getNom();

    public int getDef() {
        return def;
    }

    public int getEsq() {
        return esq;
    }

    public TypesCase getType() {
        return type;
    }

    public abstract void effect(Character character);

}
