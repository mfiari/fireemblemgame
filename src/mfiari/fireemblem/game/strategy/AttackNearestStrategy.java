package mfiari.fireemblem.game.strategy;

import mfiari.fireemblem.game.character.Character;
import mfiari.fireemblem.game.controler.Chapter;
import mfiari.fireemblem.game.controler.Combat;
import mfiari.fireemblem.game.terrain.Access;
import mfiari.fireemblem.game.terrain.Case;
import java.util.List;

public class AttackNearestStrategy extends Strategy {

    /* se dirige et attaque le perso le plus proche */
    public AttackNearestStrategy(Character perso) {
        super(perso);
    }

    @Override
    public String name() {
        return "plusProche";
    }

    @Override
    public void run(Chapter chapitre) {
        Character perso = this.getPlusProchePerso(chapitre);
        List<Case> zones = this.personnage.getMove().getCaseAvailable(chapitre.getPlateauDeJeu(), this.personnage);
        List<Case> zonesAtk = chapitre.getPorteAttaque(this.personnage, zones, chapitre.getPlateauDeJeu().getZones());
        boolean deplacement = false;
        for (Case zone : zones) {
            if (zone.getPosition().equalsXY(perso.getPosition())) {
                for (Access acce : chapitre.getPlateauDeJeu().getAcces()) {
                    if (acce.getZoneA().equals(zone)) {
                        if (zones.contains(acce.getZoneB())) {
                            chapitre.setPersoAttaquer(perso);
                            chapitre.deplacePerso(personnage, (acce.getZoneB()).getPosition());
                            Combat combat = new Combat(personnage, perso, acce.getZoneB(), 1, chapitre);
                            combat.run();
                            chapitre.verifMort();
                            deplacement = true;
                            break;
                        }
                    }
                }
            }
            if (deplacement) {
                break;
            }
        }
        if (!deplacement) {
            for (Case zone : zonesAtk) {
                if (zone.getPosition().equalsXY(perso.getPosition())) {
                    for (Access acce : chapitre.getPlateauDeJeu().getAcces()) {
                        if (acce.getZoneA().equals(zone)) {
                            if (zonesAtk.contains(acce.getZoneB())) {
                                chapitre.setPersoAttaquer(perso);
                                chapitre.deplacePerso(personnage, (acce.getZoneB()).getPosition());
                                Combat combat = new Combat(personnage, perso, acce.getZoneB(), 1, chapitre);
                                combat.run();
                                chapitre.verifMort();
                                deplacement = true;
                                break;
                            }
                        }
                    }
                }
                if (deplacement) {
                    break;
                }
            }
        }
        if (!deplacement) {
            double distance = 100;
            double tmpDistance;
            Case caseProche = null;
            for (Case zone : zones) {
                tmpDistance = this.distanceEuclidienne(zone.getPosition().getPositionX(), zone.getPosition().getPositionY(), perso.getPosition().getPositionX(), perso.getPosition().getPositionY());
                if (tmpDistance < distance) {
                    distance = tmpDistance;
                    caseProche = zone;
                }
            }
            chapitre.deplacePerso(personnage, caseProche.getPosition());
        }
    }

    public double distanceEuclidienne(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public Character getPlusProchePerso(Chapter chapitre) {
        double distance = 100;
        double tmpDistance;
        Character p = null;
        if (chapitre.getPlateauDeJeu().getEnnemies().contains(this.personnage)) {
            for (Character c : chapitre.getPlateauDeJeu().getAnnexes()) {
                tmpDistance = this.distanceEuclidienne(this.personnage.getPosition().getPositionX(), this.personnage.getPosition().getPositionY(), c.getPosition().getPositionX(), c.getPosition().getPositionY());
                if (tmpDistance < distance) {
                    distance = tmpDistance;
                    p = c;
                }
            }
            for (Character c : chapitre.getPlateauDeJeu().getPersonnages()) {
                tmpDistance = this.distanceEuclidienne(this.personnage.getPosition().getPositionX(), this.personnage.getPosition().getPositionY(), c.getPosition().getPositionX(), c.getPosition().getPositionY());
                if (tmpDistance < distance) {
                    distance = tmpDistance;
                    p = c;
                }
            }
        } else if (chapitre.getPlateauDeJeu().getAnnexes().contains(this.personnage)) {
            for (Character c : chapitre.getPlateauDeJeu().getEnnemies()) {
                tmpDistance = this.distanceEuclidienne(this.personnage.getPosition().getPositionX(), this.personnage.getPosition().getPositionY(), c.getPosition().getPositionX(), c.getPosition().getPositionY());
                if (tmpDistance < distance) {
                    distance = tmpDistance;
                    p = c;
                }
            }
        }
        return p;
    }

}
