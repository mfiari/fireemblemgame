package mfiari.fireemblem.game.strategy;

import mfiari.fireemblem.game.character.Character;
import mfiari.fireemblem.game.controler.Chapter;


public class NoActionStrategy extends Strategy {

    /* Le personnage ne fait rien */
    public NoActionStrategy(Character perso) {
        super(perso);
    }

    @Override
    public String name() {
        return "rien";
    }

    @Override
    public void run(Chapter chapitre) {

    }

}
