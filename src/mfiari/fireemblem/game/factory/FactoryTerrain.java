/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.factory;

import mfiari.fireemblem.game.chapters.Chapters;
import mfiari.fireemblem.game.chapters.Level;
import mfiari.fireemblem.game.terrain.Terrain;

/**
 *
 * @author mike
 */
public class FactoryTerrain {
    
    private final Level level;
    private final Chapters chapter;
    
    public FactoryTerrain (Level level, Chapters chapter) {
        this.level = level;
        this.chapter = chapter;
    }
    
    public Terrain createGameEnvironnement () {
        return new Terrain(this.level, this.chapter);
    }
    
}
