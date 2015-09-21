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
public class CasePorte extends Case {

    public CasePorte() {
        this.def = 0;
        this.esq = 0;
        this.type = TypesCase.porte;
    }

    public CasePorte(Position p) {
        this.def = 0;
        this.esq = 0;
        this.position = p;
        this.type = TypesCase.porte;
    }

    @Override
    public String getNom() {
        return "porte";
    }

    @Override
    public void effect(Character character) {

    }
}
