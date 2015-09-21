package mfiari.fireemblem.game.state;

import mfiari.fireemblem.game.controler.Chapter;

public class OrdreState extends DefaultState {

    public OrdreState(Chapter chapter) {
        super(chapter);
    }

    @Override
    public void cancel() {
        this.chapter.fire(this.chapter.HIDE_STATUS, null, null);
        this.chapter.setState(new FreeState(this.chapter));
    }

}
