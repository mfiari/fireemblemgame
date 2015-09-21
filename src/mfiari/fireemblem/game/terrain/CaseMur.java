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
public class CaseMur extends Case {

    public CaseMur() {
        this.def = 0;
        this.esq = 0;
        this.type = TypesCase.mur;
    }

    public CaseMur(Position p) {
        this.def = 0;
        this.esq = 0;
        this.position = p;
        this.type = TypesCase.mur;
    }

    @Override
    public String getNom() {
        return "mur";
    }

    @Override
    public void effect(Character character) {

    }
}
