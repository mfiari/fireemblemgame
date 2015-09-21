package mfiari.fireemblem.game.strategy;

import mfiari.fireemblem.game.character.Character;
import mfiari.fireemblem.game.controler.Chapter;

public abstract class Strategy {

    protected Character personnage;

    public Strategy(Character personnage) {
        this.personnage = personnage;
    }

    public abstract void run(Chapter chapitre);

    public abstract String name();

}
