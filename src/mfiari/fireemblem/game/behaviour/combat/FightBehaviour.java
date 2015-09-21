package mfiari.fireemblem.game.behaviour.combat;

import mfiari.fireemblem.game.object.Weapon;
import mfiari.fireemblem.game.object.WeaponType;

public class FightBehaviour {

    protected WeaponType[] weaponTypes;

    public boolean isWeaponAvailable(Weapon weapon) {
        for (WeaponType weaponType : this.weaponTypes) {
            if (weapon.getWeaponType() == weaponType) {
                return true;
            }
        }
        return false;
    }

    public WeaponType[] getWeaponTypes() {
        return this.weaponTypes;
    }
    
    public boolean uses (WeaponType type) {
        for (WeaponType weaponType : weaponTypes) {
            if (weaponType == type) {
                return true;
            }
        }
        return false;
    }

}
