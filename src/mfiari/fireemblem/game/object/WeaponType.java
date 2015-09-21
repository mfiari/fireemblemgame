/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiari.fireemblem.game.object;

/**
 *
 * @author mike
 */
public enum WeaponType {

    arc, epee, hache, lance, baton, feu, foudre, vent;

    public boolean isGood(WeaponType weaponType) {
        switch (this) {
            case epee:
                switch (weaponType) {
                    case hache:
                        return true;
                    default:
                        return false;
                }
            case hache:
                switch (weaponType) {
                    case lance:
                        return true;
                    default:
                        return false;
                }
            case lance:
                switch (weaponType) {
                    case epee:
                        return true;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }

    public boolean isBad(WeaponType weaponType) {
        switch (this) {
            case epee:
                switch (weaponType) {
                    case lance:
                        return true;
                    default:
                        return false;
                }
            case hache:
                switch (weaponType) {
                    case epee:
                        return true;
                    default:
                        return false;
                }
            case lance:
                switch (weaponType) {
                    case hache:
                        return true;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }

}
