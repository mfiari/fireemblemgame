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
public class ObjetFactory {

    public Objet createObjet(String name, ObjetType typeObjet) {
        switch (typeObjet) {
            /* Objet */
            case potion:
                return new Potion(name, 3, 300, typeObjet, 10);
            case cle_porte:
                return new ClassicObject(name, 3, 300, typeObjet);
            /* Arme */
            case epee_fer:
                return new ClassicWeapon(name, 46, 460, Weapon.Rang.E, 1, 1, 5, 5, 90, 0, WeaponType.epee, typeObjet);
            case epee_acier:
                return new ClassicWeapon(name, 30, 600, Weapon.Rang.D, 1, 1, 10, 8, 75, 0, WeaponType.epee, typeObjet);
            case epee_argent:
                return new ClassicWeapon(name, 20, 1500, Weapon.Rang.A, 1, 1, 8, 13, 80, 0, WeaponType.epee, typeObjet);
            case hache_fer:
                return new ClassicWeapon(name, 45, 270, Weapon.Rang.E, 1, 1, 10, 8, 75, 0, WeaponType.hache, typeObjet);
            case hache_acier:
                return new ClassicWeapon(name, 30, 360, Weapon.Rang.E, 1, 1, 15, 11, 65, 0, WeaponType.hache, typeObjet);
            case hache_argent:
                return new ClassicWeapon(name, 20, 1000, Weapon.Rang.A, 1, 1, 12, 15, 70, 0, WeaponType.hache, typeObjet);
            case lance_fer:
                return new ClassicWeapon(name, 45, 360, Weapon.Rang.E, 1, 1, 8, 7, 80, 0, WeaponType.lance, typeObjet);
            case lance_acier:
                return new ClassicWeapon(name, 30, 480, Weapon.Rang.D, 1, 1, 13, 10, 70, 0, WeaponType.lance, typeObjet);
            case lance_argent:
                return new ClassicWeapon(name, 20, 1200, Weapon.Rang.A, 1, 1, 10, 14, 75, 0, WeaponType.lance, typeObjet);
            case arc_fer:
                return new ClassicWeapon(name, 46, 460, Weapon.Rang.E, 2, 2, 5, 5, 90, 0, WeaponType.arc, typeObjet);
            /* magie anima */
            case feu:
                return new ClassicWeapon(name, 40, 560, Weapon.Rang.E, 1, 2, 4, 5, 90, 0, WeaponType.feu, typeObjet);
            case tonnerre:
                return new ClassicWeapon(name, 35, 700, Weapon.Rang.D, 1, 2, 6, 8, 80, 5, WeaponType.foudre, typeObjet);
            /* baton */
            case soin:
                return new HealWeapon(name, 30, 600, Weapon.Rang.E, 1, 1, 5, 10, 100, 11, WeaponType.baton, typeObjet);
            default:
                return null;
        }
    }

}
