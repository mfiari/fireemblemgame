/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.terrain;

import mfiari.fireemblem.game.Laucher;
import mfiari.fireemblem.game.chapters.Chapters;
import mfiari.fireemblem.game.chapters.Level;
import mfiari.fireemblem.game.parser.xml.ChapterParser;
import mfiari.fireemblem.game.parser.xml.Parser;
import mfiari.fireemblem.game.parser.xml.PlatformParser;
import mfiari.lib.game.position.Position;

/**
 *
 * @author mike
 */
public class Terrain {
    
    private final Level level;
    private final Chapters chapter;
    
    public Terrain (Level level, Chapters chapter) {
        this.level = level;
        this.chapter = chapter;
    }
    
    public GamePlatform createGamePlatform () {
    	GamePlatform plateauDeJeu = new GamePlatform();
        String mapLocation = "xml/map/";
        String characterLocation = "xml/character/";
        String map = mapLocation + this.level.name() + "_" + this.chapter.name() + ".xml";
        String characters = characterLocation + this.level.name() + "_" + this.chapter.name() + ".xml";
        Parser.Parse(Laucher.class.getResourceAsStream(map), new PlatformParser(this, plateauDeJeu));
        Parser.Parse(Laucher.class.getResourceAsStream(characters), new ChapterParser(plateauDeJeu));
    	return plateauDeJeu;
    }
    
    public Case creerZone (TypesCase type, Position p) {
        switch (type) {
            case chateau :
        	return new CaseChateau(p);
            case fort :
                return new CaseFort(p);
            case montagne :
                return new CaseMontagne(p);
            case mur :
                return new CaseMur(p);
            case plaine :
                return new CasePlaine(p);
            case porte :
                return new CasePorte(p);
            case forest :
                return new CaseForest(p);
            default:
                return null;
        }
    }

    public Access creerAcces(Case z1, Case z2) {
        return new Access(z1, z2);
    }
    
}
