/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.behaviour.move;

import mfiari.fireemblem.game.character.Character;
import mfiari.fireemblem.game.terrain.Access;
import mfiari.fireemblem.game.terrain.Case;
import mfiari.fireemblem.game.terrain.GamePlatform;
import mfiari.fireemblem.game.terrain.TypesCase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mfiari.lib.game.position.Position;

/**
 *
 * @author mike
 */
public abstract class MoveBehaviour {
    
    protected int movementRate;
    
    public int getDeplacement(TypesCase type) {
        switch (type) {
            case chateau:
                return 100;
            case mur:
                return 100;
            case porte:
                return 100;
            default:
                return 1;
        }
    }
    
    public int getDeplacement (List<Character> personnages, Position position) {
        for (Character perso : personnages) {
            if (perso.getPosition().equalsXY(position)) {
                return 100;
            }
        }
        return 0;
    }
    
    public List<Case> getCaseAvailable (GamePlatform plateauDeJeu, Character perso) {
        List<Case> list = new ArrayList<>();
        Map<Case, List<Integer>> couts = new HashMap<>();
        Position positionPerso = perso.getPosition();
        for (Case zone : plateauDeJeu.getZones()) {
            if (zone.getPosition().equalsXY(positionPerso)) {
                list.add(zone);
                couts.put(zone, new ArrayList<Integer>());
                couts.get(zone).add(0);
                break;
            }
        }
        for (Access acce : plateauDeJeu.getAcces()) {
            if (acce.getZoneA().equals(list.get(0))) {
                this.addChemin(list, plateauDeJeu.getEnnemies(), couts, plateauDeJeu.getAcces(), acce.getZoneB(), this.movementRate);
            }
        }
        
        return list;
    }
    
    public void addChemin (List<Case> list, List<Character> personnages, Map<Case, List<Integer>> couts, List<Access> acces, Case z, int deplacementRestant) {
        deplacementRestant = deplacementRestant - this.getDeplacement(z.getType()) - this.getDeplacement(personnages, z.getPosition());
        if (deplacementRestant >= 0) {
            if (!list.contains(z)) {
                list.add(z);
                couts.put(z, new ArrayList<Integer>());
            }
            if (!couts.get(z).contains(deplacementRestant)) {
                couts.get(z).add(deplacementRestant);
                for (Access acce : acces) {
                    if (acce.getZoneA().equals(z)) {
                        this.addChemin(list, personnages, couts, acces, acce.getZoneB(), deplacementRestant);
                    }
                }
            }
        }
    }

    public int getNbDeplacement() {
        return movementRate;
    }
    
}