/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.connexionBD;

import mfiari.fireemblem.game.character.Character;
import java.util.List;

/**
 *
 * @author mike
 */
public class Partie {
    
    private final int id;
    private int hour;
    private int minute;
    private int seconde;
    private String name;
    private boolean empty;
    private boolean enCours;
    private List<Character> personnages;
    
    public Partie (int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSeconde() {
        return seconde;
    }

    public void setSeconde(int seconde) {
        this.seconde = seconde;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public boolean isEnCours() {
        return enCours;
    }

    public void setEnCours(boolean enCours) {
        this.enCours = enCours;
    }
    
    public List<Character> getPersonnages () {
        return this.personnages;
    }
    
    public void setCharacters (List<Character> characters) {
        this.personnages = characters;
    }
    
}
