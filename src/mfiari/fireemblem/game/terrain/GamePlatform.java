/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiari.fireemblem.game.terrain;

import mfiari.fireemblem.game.character.Character;
import java.util.ArrayList;
import java.util.List;
import mfiari.lib.game.position.Position;

/**
 *
 * @author mike
 */
public class GamePlatform {

    private final List<Case> zones;
    private final List<Access> acces;
    private final List<Character> personnagesObligatoire;
    private final List<Position> positions;
    private final List<Character> personnages;
    private final List<Character> ennemies;
    private final List<Character> annexes;
    private int width;
    private int height;
    private int maxUnit;

    public GamePlatform() {
        this.zones = new ArrayList<>();
        this.acces = new ArrayList<>();
        this.personnagesObligatoire = new ArrayList<>();
        this.positions = new ArrayList<>();
        this.personnages = new ArrayList<>();
        this.ennemies = new ArrayList<>();
        this.annexes = new ArrayList<>();
        this.width = 20;
        this.height = 20;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return this.width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    public void setMaxUnits(int maxUnits) {
        this.maxUnit = maxUnits;
    }

    public int getMaxUnits() {
        return this.maxUnit;
    }

    public void ajouterZone(Case zone) {
        this.zones.add(zone);
    }

    public List<Case> getZones() {
        return this.zones;
    }
    
    public Case getZoneAtPosition (Position p) {
        for (Case c : this.zones) {
            if (c.getPosition().equalsXY(p)) {
                return c;
            }
        }
        return null;
    }
    
    public void setZoneAtPosition (Position p, Case zone) {
        for (Case c : this.zones) {
            if (c.getPosition().equalsXY(p)) {
                for (Access access : this.acces) {
                    if (access.getZoneA().equals(c)) {
                        access.setZoneA(zone);
                    } else if (access.getZoneB().equals(c)) {
                        access.setZoneB(zone);
                    }
                }
                this.zones.remove(c);
                this.zones.add(zone);
                return;
            }
        }
    }

    public void ajouterAcces(Access acces) {
        this.acces.add(acces);
    }

    public List<Access> getAcces() {
        return acces;
    }

    public void ajouterPersonnageObligatoire(Character p) {
        this.personnagesObligatoire.add(p);
    }

    public List<Character> getPersonnagesObligatoire() {
        return this.personnagesObligatoire;
    }
    
    public void ajouterPosition (Position p) {
        this.positions.add(p);
    }
    
    public List<Position> getPositions () {
        return this.positions;
    }

    public void ajouterPersonnage(Character p) {
        this.personnages.add(p);
    }

    public List<Character> getPersonnages() {
        return this.personnages;
    }

    public void ajouterEnnemie(Character p) {
        this.ennemies.add(p);
    }

    public List<Character> getEnnemies() {
        return this.ennemies;
    }

    public void ajouterAnnexe(Character p) {
        this.annexes.add(p);
    }

    public List<Character> getAnnexes() {
        return annexes;
    }
    
    public Character getCharacterAtPosition (Position position) {
        for (Character character : this.personnages) {
            if (character.getPosition().equalsXY(position)) {
                return character;
            }
        }
        for (Character character : this.annexes) {
            if (character.getPosition().equalsXY(position)) {
                return character;
            }
        }
        for (Character character : this.ennemies) {
            if (character.getPosition().equalsXY(position)) {
                return character;
            }
        }
        return null;
    }

}
