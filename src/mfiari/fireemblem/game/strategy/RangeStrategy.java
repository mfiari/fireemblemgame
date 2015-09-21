package mfiari.fireemblem.game.strategy;

import mfiari.fireemblem.game.character.Character;
import mfiari.fireemblem.game.controler.Chapter;
import mfiari.fireemblem.game.controler.Combat;
import mfiari.fireemblem.game.terrain.Access;
import mfiari.fireemblem.game.terrain.Case;
import java.util.List;
import mfiari.lib.game.position.Position;

public class RangeStrategy extends Strategy {

    /* le perso se deplace et attaque si un ennemie se trouve dans sa 
     * portee de deplacement + attaque */
    public RangeStrategy(Character perso) {
        super(perso);
    }

    @Override
    public String name() {
        return "portee";
    }

    @Override
    public void run(Chapter chapitre) {
        List<Case> zones = this.personnage.getMove().getCaseAvailable(chapitre.getPlateauDeJeu(), this.personnage);
        List<Case> zonesAtk = chapitre.getPorteAttaque(this.personnage, zones, chapitre.getPlateauDeJeu().getZones());
        boolean deplacement = verifCombat(zones, chapitre.getPlateauDeJeu().getPersonnages(), chapitre);
        if (!deplacement) {
            deplacement = verifCombat(zones, chapitre.getPlateauDeJeu().getAnnexes(), chapitre);
        }
        if (!deplacement) {
            deplacement = verifCombat2(zones, zonesAtk, chapitre.getPlateauDeJeu().getPersonnages(), chapitre);
        }
        if (!deplacement) {
            verifCombat2(zones, zonesAtk, chapitre.getPlateauDeJeu().getAnnexes(), chapitre);
        }
    }

    public boolean verifCombat(List<Case> zones, List<Character> personnages, Chapter chapitre) {
        for (Case zone : zones) {
            for (Character character : personnages) {
                if (zone.getPosition().equalsXY(character.getPosition())) {
                    if (this.personnage.getArme().getMinRange() == 1) {
                        for (Access acce : chapitre.getPlateauDeJeu().getAcces()) {
                            if (acce.getZoneA().equals(zone)) {
                                if (zones.contains(acce.getZoneB())) {
                                    chapitre.setPersoAttaquer(character);
                                    chapitre.deplacePerso(personnage, acce.getZoneB().getPosition());
                                    Combat combat = new Combat(personnage, character, acce.getZoneB(), 1, chapitre);
                                    combat.run();
                                    chapitre.verifMort();
                                    return true;
                                }
                            }
                        }
                    } else {
                        for (int i = this.personnage.getArme().getMinRange() ; i <= this.personnage.getArme().getMaxRange() ; i++) {
                            Position p1 = new Position(zone.getPosition().getPositionX() + i, zone.getPosition().getPositionY());
                            if (p1.equalsXY(character.getPosition())) {
                                chapitre.setPersoAttaquer(character);
                                chapitre.deplacePerso(personnage, zone.getPosition());
                                Combat combat = new Combat(personnage, character, zone, i, chapitre);
                                combat.run();
                                chapitre.verifMort();
                                return true;
                            }
                            Position p2 = new Position(zone.getPosition().getPositionX() - i, zone.getPosition().getPositionY());
                            if (p2.equalsXY(character.getPosition())) {
                                chapitre.setPersoAttaquer(character);
                                chapitre.deplacePerso(personnage, zone.getPosition());
                                Combat combat = new Combat(personnage, character, zone, i, chapitre);
                                combat.run();
                                chapitre.verifMort();
                                return true;
                            }
                            Position p3 = new Position(zone.getPosition().getPositionX(), zone.getPosition().getPositionY() + i);
                            if (p3.equalsXY(character.getPosition())) {
                                chapitre.setPersoAttaquer(character);
                                chapitre.deplacePerso(personnage, zone.getPosition());
                                Combat combat = new Combat(personnage, character, zone, i, chapitre);
                                combat.run();
                                chapitre.verifMort();
                                return true;
                            }
                            Position p4 = new Position(zone.getPosition().getPositionX(), zone.getPosition().getPositionY() - i);
                            if (p4.equalsXY(character.getPosition())) {
                                chapitre.setPersoAttaquer(character);
                                chapitre.deplacePerso(personnage, zone.getPosition());
                                Combat combat = new Combat(personnage, character, zone, i, chapitre);
                                combat.run();
                                chapitre.verifMort();
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean verifCombat2(List<Case> zones, List<Case> zonesAtk, List<Character> personnages, Chapter chapitre) {
        for (Case zone : zonesAtk) {
            for (Character character : personnages) {
                if (zone.getPosition().equalsXY(character.getPosition())) {
                    if (this.personnage.getArme().getMinRange() == 1) {
                        for (Access acce : chapitre.getPlateauDeJeu().getAcces()) {
                            if (acce.getZoneA().equals(zone)) {
                                if (zones.contains(acce.getZoneB())) {
                                    chapitre.setPersoAttaquer(character);
                                    chapitre.deplacePerso(personnage, acce.getZoneB().getPosition());
                                    Combat combat = new Combat(personnage, character, acce.getZoneB(), 1, chapitre);
                                    combat.run();
                                    chapitre.verifMort();
                                    return true;
                                }
                            }
                        }
                    } else {
                        for (Case zoneAvailable : zones) {
                            for (int i = this.personnage.getArme().getMinRange() ; i <= this.personnage.getArme().getMaxRange() ; i++) {
                                Position p1 = new Position(zoneAvailable.getPosition().getPositionX() + i, zoneAvailable.getPosition().getPositionY());
                                if (p1.equalsXY(character.getPosition())) {
                                    chapitre.setPersoAttaquer(character);
                                    chapitre.deplacePerso(personnage, zoneAvailable.getPosition());
                                    Combat combat = new Combat(personnage, character, zoneAvailable, i, chapitre);
                                    combat.run();
                                    chapitre.verifMort();
                                    return true;
                                }
                                Position p2 = new Position(zoneAvailable.getPosition().getPositionX() - i, zoneAvailable.getPosition().getPositionY());
                                if (p2.equalsXY(character.getPosition())) {
                                    chapitre.setPersoAttaquer(character);
                                    chapitre.deplacePerso(personnage, zoneAvailable.getPosition());
                                    Combat combat = new Combat(personnage, character, zoneAvailable, i, chapitre);
                                    combat.run();
                                    chapitre.verifMort();
                                    return true;
                                }
                                Position p3 = new Position(zoneAvailable.getPosition().getPositionX(), zoneAvailable.getPosition().getPositionY() + i);
                                if (p3.equalsXY(character.getPosition())) {
                                    chapitre.setPersoAttaquer(character);
                                    chapitre.deplacePerso(personnage, zoneAvailable.getPosition());
                                    Combat combat = new Combat(personnage, character, zoneAvailable, i, chapitre);
                                    combat.run();
                                    chapitre.verifMort();
                                    return true;
                                }
                                Position p4 = new Position(zoneAvailable.getPosition().getPositionX(), zoneAvailable.getPosition().getPositionY() - i);
                                if (p4.equalsXY(character.getPosition())) {
                                    chapitre.setPersoAttaquer(character);
                                    chapitre.deplacePerso(personnage, zoneAvailable.getPosition());
                                    Combat combat = new Combat(personnage, character, zoneAvailable, i, chapitre);
                                    combat.run();
                                    chapitre.verifMort();
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

}
