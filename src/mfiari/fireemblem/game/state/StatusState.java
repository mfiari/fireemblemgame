package mfiari.fireemblem.game.state;

import mfiari.fireemblem.game.controler.Chapter;

public class StatusState extends DefaultState {

    public StatusState(Chapter chapter) {
        super(chapter);
    }

    @Override
    public void cancel() {
        this.chapter.fire(this.chapter.HIDE_STATUS, null, null);
        this.chapter.setState(new FreeState(this.chapter));
    }

}
