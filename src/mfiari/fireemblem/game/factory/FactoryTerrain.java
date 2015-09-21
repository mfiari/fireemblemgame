/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.factory;

import mfiari.fireemblem.game.chapters.Chapters;
import mfiari.fireemblem.game.chapters.Difficulte;
import mfiari.fireemblem.game.terrain.Terrain;

/**
 *
 * @author mike
 */
public class FactoryTerrain {
    
    private final Chapters chapters;
    private final Difficulte difficulte;
    
    public FactoryTerrain (Chapters chapters, Difficulte difficulte) {
        this.chapters = chapters;
        this.difficulte = difficulte;
    }
    
    public Terrain createGameEnvironnement () {
        return new Terrain(this.chapters, this.difficulte);
    }
    
}
