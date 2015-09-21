package mfiari.fireemblem.game.state;

import mfiari.fireemblem.game.controler.Chapter;

public class FightSimulationState extends DefaultState {

    public FightSimulationState(Chapter chapter) {
        super(chapter);
    }

    @Override
    public void action() {
        this.chapter.combat();
    }

    @Override
    public void cancel() {
        this.chapter.fightSimulationStateCancel();
    }

}
