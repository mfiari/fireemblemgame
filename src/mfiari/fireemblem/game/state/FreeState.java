package mfiari.fireemblem.game.state;

import mfiari.fireemblem.game.controler.Chapter;
import mfiari.fireemblem.game.character.Character;

public class FreeState extends DefaultState {

    public FreeState(Chapter chapter) {
        super(chapter);
    }

    @Override
    public void action() {
        this.chapter.freeStateAction();
    }

    @Override
    public void info() {
        /* Si perso, afffiche info du perso */
        for (Character perso : this.chapter.getPlateauDeJeu().getPersonnages()) {
            Character p = (Character) perso;
            if (p.getPosition().equalsXY(this.chapter.getCurrentPosition())) {
                this.chapter.setPersoEnCours(p);
                this.chapter.setState(new CharacterViewState(this.chapter));
                break;
            }
        }
        for (Character perso : this.chapter.getPlateauDeJeu().getAnnexes()) {
            if (perso.getPosition().equalsXY(this.chapter.getCurrentPosition())) {
                this.chapter.setPersoEnCours(perso);
                this.chapter.setState(new CharacterViewState(this.chapter));
                break;
            }
        }
        for (Character perso : this.chapter.getPlateauDeJeu().getEnnemies()) {
            if (perso.getPosition().equalsXY(this.chapter.getCurrentPosition())) {
                this.chapter.setPersoEnCours(perso);
                this.chapter.setState(new CharacterViewState(this.chapter));
                break;
            }
        }
    }

}
