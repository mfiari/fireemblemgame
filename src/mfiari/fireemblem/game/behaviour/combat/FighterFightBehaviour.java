package mfiari.fireemblem.game.behaviour.combat;

import mfiari.fireemblem.game.object.WeaponType;

public class FighterFightBehaviour extends FightBehaviour {

    public FighterFightBehaviour() {
        this.weaponTypes = new WeaponType[1];
        this.weaponTypes[0] = WeaponType.hache;
    }

}
