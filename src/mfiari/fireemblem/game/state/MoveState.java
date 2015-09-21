package mfiari.fireemblem.game.state;

import mfiari.fireemblem.game.character.Character;
import mfiari.fireemblem.game.controler.Chapter;
import mfiari.fireemblem.game.controler.Chapter.actionPerso;
import mfiari.fireemblem.game.object.ObjetType;
import mfiari.fireemblem.game.object.WeaponType;
import mfiari.fireemblem.game.terrain.Case;
import mfiari.fireemblem.game.terrain.TypesCase;
import java.util.ArrayList;
import java.util.List;
import mfiari.lib.game.position.Position;

public class MoveState extends DefaultState {

    public MoveState(Chapter chapter) {
        super(chapter);
    }

    @Override
    public void action() {
        boolean inZone = false;
        for (Case zone : this.chapter.getZonesSelectionner()) {
            if (zone.getPosition().equalsXY(this.chapter.getCurrentPosition())) {
                inZone = true;
                break;
            }
        }
        if (!inZone) {
            return;
        }
        for (Character perso : this.chapter.getPlateauDeJeu().getPersonnages()) {
            if (perso.getPosition().equalsXY(this.chapter.getCurrentPosition()) && !perso.equals(this.chapter.getPersoEnCours())) {
                return;
            }
        }
        Character p = (Character) this.chapter.getPersoEnCours();
        List<actionPerso> list = new ArrayList<>();
        this.chapter.deplacePerso(p, this.chapter.getCurrentPosition());
        Position position = new Position(p.getPosition().getPositionX()-1, p.getPosition().getPositionY());
        Character character = this.chapter.getPlateauDeJeu().getCharacterAtPosition(position);
        this.chapter.setPersoAttaquer(character);
        System.out.println("MoveState");
        if (this.chapter.hasEvenement()) {
            list.add(actionPerso.parler);
        }
        Case c = this.chapter.getPlateauDeJeu().getZoneAtPosition(position);
        if (c.getType() == TypesCase.porte) {
            if (p.hasObjet(ObjetType.cle_porte)) {
                list.add(actionPerso.porte);
            }
        }
        List<Character> ennemies = this.chapter.getEnnemies();
        if (!ennemies.isEmpty()) {
            list.add(actionPerso.attaquer);
        }
        List<Character> allies = this.chapter.getAlies();
        if (!allies.isEmpty() && p.getFightBehaviour().uses(WeaponType.baton)) {
            list.add(actionPerso.baton);
        }
        list.add(actionPerso.objet);
        List<Character> alies = this.chapter.getAlies();
        if (!alies.isEmpty()) {
            list.add(actionPerso.echange);
        }
        list.add(actionPerso.attendre);
        actionPerso actions[] = new actionPerso[list.size()];
        for (int i = 0; i < list.size(); i++) {
            actions[i] = list.get(i);
        }
        this.chapter.fire(this.chapter.AFFICHE_ACTION_PERSO, actions, null);
        this.chapter.fire(this.chapter.EFFACE_DEPLACEMENT_DISPONIBLE, this.chapter.getZonesSelectionner(), null);
        this.chapter.fire(this.chapter.EFFACE_ATTAQUE_DISPONIBLE, this.chapter.getZonesAtkSelectionner(), null);
        this.chapter.setState(new ActionMenuState(this.chapter));
    }

    @Override
    public void cancel() {
        this.chapter.fire(this.chapter.EFFACE_DEPLACEMENT_DISPONIBLE, this.chapter.getZonesSelectionner(), null);
        this.chapter.fire(this.chapter.EFFACE_ATTAQUE_DISPONIBLE, this.chapter.getZonesAtkSelectionner(), null);
        this.chapter.setState(new FreeState(this.chapter));
    }

}
