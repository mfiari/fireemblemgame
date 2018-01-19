/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiari.fireemblem.game.terrain;

import mfiari.lib.game.position.Position;

/**
 *
 * @author mike
 */
public class CaseForest extends Case {

    public CaseForest() {
        this.def = 1;
        this.esq = 20;
        this.type = TypesCase.forest;
    }

    public CaseForest(Position p) {
        this.def = 1;
        this.esq = 20;
        this.position = p;
        this.type = TypesCase.forest;
    }

    @Override
    public String getNom() {
        return "Forêt";
    }

    @Override
    public void effect(mfiari.fireemblem.game.character.Character character) {
        
    }
}
