/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiari.fireemblem.game.terrain;

/**
 *
 * @author mike
 */
public class Access {

    private Case zoneA;
    private Case zoneB;

    public Access(Case zone1, Case zone2) {
        this.zoneA = zone1;
        this.zoneB = zone2;
    }

    public Case getZoneA() {
        return zoneA;
    }
    
    public void setZoneA (Case c) {
        this.zoneA = c;
    }

    public Case getZoneB() {
        return zoneB;
    }
    
    public void setZoneB (Case c) {
        this.zoneB = c;
    }

}
