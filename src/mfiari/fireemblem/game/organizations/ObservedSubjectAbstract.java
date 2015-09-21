/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiari.fireemblem.game.organizations;

import mfiari.fireemblem.game.character.Character;
import java.util.ArrayList;

/**
 *
 * @author mike
 */
public abstract class ObservedSubjectAbstract {

    private final ArrayList<Character> observerList = new ArrayList<>();

    public void attach(Character observer) {
        observerList.add(observer);
    }

    public void detach(Character observer) {
        observerList.remove(observer);
    }

    public void notifyObservers() {
        for (Character o : observerList) {
            o.update();
        }
    }

}
