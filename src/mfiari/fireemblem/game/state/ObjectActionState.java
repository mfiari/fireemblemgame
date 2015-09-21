package mfiari.fireemblem.game.state;

import mfiari.fireemblem.game.controler.Chapter;
import mfiari.fireemblem.game.controler.Chapter.actionPerso;

public class ObjectActionState extends DefaultState {

    public ObjectActionState(Chapter chapter) {
        super(chapter);
    }

    @Override
    public void cancel() {
        this.chapter.fire(this.chapter.CANCEL_OBJET_ACTION, null, null);
        this.chapter.actionPerso(actionPerso.objet);
    }
}
