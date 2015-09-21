package mfiari.fireemblem.game.strategy;

import mfiari.fireemblem.game.character.Character;
import mfiari.fireemblem.game.controler.Chapter;
import mfiari.fireemblem.game.controler.Combat;
import mfiari.fireemblem.game.terrain.Case;
import mfiari.lib.game.position.Position;

public class NoMovementStrategy extends Strategy {

    /* Le personnage ne bouge pas mais attaque si un ennemie se trouve 
     * a port� d'attaque */
    public NoMovementStrategy(Character perso) {
        super(perso);
    }

    @Override
    public String name() {
        return "immobile";
    }

    @Override
    public void run(Chapter chapitre) {
        if (chapitre.getPlateauDeJeu().getEnnemies().contains(personnage)) {
            boolean combat = false;
            Character perso;
            Position p1 = new Position(personnage.getPosition().getPositionX() + 1, personnage.getPosition().getPositionY());
            perso = this.getPersonnage(p1, chapitre);
            Case square = this.getSquare(personnage.getPosition(), chapitre);
            if (perso != null && !combat) {
                this.combat(this.personnage, perso, square, chapitre);
            }
            Position p2 = new Position(personnage.getPosition().getPositionX() - 1, personnage.getPosition().getPositionY());
            perso = this.getPersonnage(p2, chapitre);
            if (perso != null && !combat) {
                this.combat(this.personnage, perso, square, chapitre);
            }
            Position p3 = new Position(personnage.getPosition().getPositionX(), personnage.getPosition().getPositionY() + 1);
            perso = this.getPersonnage(p3, chapitre);
            if (perso != null && !combat) {
                this.combat(this.personnage, perso, square, chapitre);
            }
            Position p4 = new Position(personnage.getPosition().getPositionX(), personnage.getPosition().getPositionY() - 1);
            perso = this.getPersonnage(p4, chapitre);
            if (perso != null && !combat) {
                this.combat(this.personnage, perso, square, chapitre);
            }
        }
    }

    public Character getPersonnage(Position p, Chapter chapitre) {
        for (Character c : chapitre.getPlateauDeJeu().getAnnexes()) {
            Character perso = (Character) c;
            if (perso.getPosition().equalsXY(p)) {
                return perso;
            }
        }
        for (Character c : chapitre.getPlateauDeJeu().getPersonnages()) {
            Character perso = (Character) c;
            if (perso.getPosition().equalsXY(p)) {
                return perso;
            }
        }
        return null;
    }

    private Case getSquare(Position position, Chapter chapitre) {
        for (Case zone : chapitre.getPlateauDeJeu().getZones()) {
            if (zone.getPosition().equalsXY(position)) {
                return zone;
            }
        }
        return null;
    }

    private void combat(Character p1, Character p2, Case square, Chapter chapitre) {
        chapitre.setPersoAttaquer(p2);
        Combat combat = new Combat(p1, p2, square, 1, chapitre);
        combat.run();
        chapitre.verifMort();
    }

}
