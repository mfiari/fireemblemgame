/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.terrain;

import mfiari.fireemblem.game.Laucher;
import mfiari.fireemblem.game.chapters.Chapters;
import mfiari.fireemblem.game.chapters.Difficulte;
import mfiari.fireemblem.game.parser.xml.ChapterParser;
import mfiari.fireemblem.game.parser.xml.Parser;
import mfiari.fireemblem.game.parser.xml.PlatformParser;
import mfiari.lib.game.position.Position;

/**
 *
 * @author mike
 */
public class Terrain {
    
    private final Chapters chapters;
    private final Difficulte difficulte;
    
    public Terrain (Chapters chapters, Difficulte difficult) {
        this.chapters = chapters;
        this.difficulte = difficult;
    }
    
    public GamePlatform createGamePlatform () {
    	GamePlatform plateauDeJeu = new GamePlatform();
        String mapLocation = "xml/map/";
        String characterLocation = "xml/character/";
        String map = mapLocation + this.chapters.name() + "_" + this.difficulte.name() + ".xml";
        String characters = characterLocation + this.chapters.name() + "_" + this.difficulte.name() + ".xml";
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
            default:
                return null;
        }
    }

    public Access creerAcces(Case z1, Case z2) {
        return new Access(z1, z2);
    }
    
}
