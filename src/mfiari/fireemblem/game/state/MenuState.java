package mfiari.fireemblem.game.state;

import mfiari.fireemblem.game.controler.Chapter;

public class MenuState extends DefaultState {

    public MenuState(Chapter chapter) {
        super(chapter);
    }

    @Override
    public void cancel() {
        this.chapter.fire(this.chapter.HIDE_MENU, null, null);
        this.chapter.setState(new FreeState(this.chapter));
    }

}
