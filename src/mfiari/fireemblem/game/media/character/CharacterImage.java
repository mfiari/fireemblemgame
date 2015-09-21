package mfiari.fireemblem.game.media.character;

import mfiari.fireemblem.game.behaviour.character.CharacterBehaviour;
import mfiari.fireemblem.game.behaviour.character.LordHappiaBehaviour;
import mfiari.fireemblem.game.behaviour.character.FighterBehaviour;
import mfiari.fireemblem.game.character.Character;
import mfiari.fireemblem.game.character.Character.Etat;
import mfiari.fireemblem.game.swing.ImageManager;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import mfiari.lib.game.image.Image;

public class CharacterImage extends Image {

    private static final CharacterImage _ImagePersonnage;

    private static final String COMBAT = "combat/";
    private static final String MAP = "map/";
    private static final String MENU = "menu/";
    private static final String DEFAULT_IMAGE = "default";

    static {
        _ImagePersonnage = new CharacterImage();
    }

    private String getNameFromComportement(CharacterBehaviour cp) {
        if (cp instanceof FighterBehaviour) {
            return "combattant_ennemie";
        } else if (cp instanceof LordHappiaBehaviour) {
            return "lord_happia";
        }
        return cp.getName();
    }

    public static BufferedImage getImageMenuFromPersonnage(Character perso) {
        return _ImagePersonnage.getImageMenu(perso);
    }

    public static BufferedImage getImageCombatFromPersonnage(Character perso) {
        return _ImagePersonnage.getImageCombat(perso);
    }

    public static Map<Integer, ImageManager> getImageCombatAttaqueFromPersonnage(Character perso) {
        return _ImagePersonnage.getImageCombatAttaque(perso);
    }

    public static Map<Integer, ImageManager> getImageCombatAttaqueCritiqueFromPersonnage(Character perso) {
        return _ImagePersonnage.getImageCombatAttaqueCritique(perso);
    }

    public static Map<Integer, ImageManager> getImageCombatDistantFromPersonnage(Character perso) {
        return _ImagePersonnage.getImageCombatDistant(perso);
    }

    public static Map<Integer, ImageManager> getImageCombatDistantCritiqueFromPersonnage(Character perso) {
        return _ImagePersonnage.getImageCombatDistantCritique(perso);
    }

    public static Map<Integer, ImageManager> getImageCombatEsquiveFromPersonnage(Character perso) {
        return _ImagePersonnage.getImageCombatEsquive(perso);
    }

    public static Map<Integer, ImageManager> getImageCombatSoinFromPersonnage(Character perso) {
        return _ImagePersonnage.getImageCombatSoin(perso);
    }

    public BufferedImage getImageMenu(Character perso) {
        if (this.aImage(perso.getName(), MENU)) {
            return this.getImage(this.getInputStreamImage(perso.getName(), MENU));
        } else if (this.aImage(this.getNameFromComportement(perso.getComportementPersonnage()), MENU)) {
            return this.getImage(this.getInputStreamImage(this.getNameFromComportement(perso.getComportementPersonnage()), MENU));
        }
        return this.getImage(DEFAULT_IMAGE, MENU);
    }

    public BufferedImage getImageCombat(Character perso) {
        if (perso.hasArme() && this.aImage(perso.getName() + "_" + this.getNameFromComportement(perso.getComportementPersonnage()) + "_" + perso.getArme().getWeaponType().name() + "1", COMBAT)) {
            return this.getImage(this.getInputStreamImage(perso.getName() + "_" + this.getNameFromComportement(perso.getComportementPersonnage()) + "_" + perso.getArme().getWeaponType().name() + "1", COMBAT));
        } else if (this.aImage(perso.getName() + "_" + this.getNameFromComportement(perso.getComportementPersonnage()) + "1", COMBAT)) {
            return this.getImage(this.getInputStreamImage(perso.getName() + "_" + this.getNameFromComportement(perso.getComportementPersonnage()) + "1", COMBAT));
        } else if (this.aImage(perso.getName() + "1", COMBAT)) {
            return this.getImage(this.getInputStreamImage(perso.getName() + "1", COMBAT));
        } else if (this.aImage(this.getNameFromComportement(perso.getComportementPersonnage()) + "1", COMBAT)) {
            return this.getImage(this.getInputStreamImage(this.getNameFromComportement(perso.getComportementPersonnage()) + "1", COMBAT));
        }
        return this.getImage(DEFAULT_IMAGE, COMBAT);
    }

    public Map<Integer, ImageManager> getImageCombat(Character perso, String type) {
        Map<Integer, ImageManager> images = new HashMap<>();
        int indice = 1;
        String persoName = "";
        if (perso.hasArme() && this.aImage(perso.getName() + "_" + this.getNameFromComportement(perso.getComportementPersonnage()) + "_" + perso.getArme().getWeaponType().name() + type + indice, COMBAT)) {
            persoName = perso.getName() + "_" + this.getNameFromComportement(perso.getComportementPersonnage()) + "_" + perso.getArme().getWeaponType().name() + type;
        } else if (this.aImage(perso.getName() + "_" + this.getNameFromComportement(perso.getComportementPersonnage()) + type + indice, COMBAT)) {
            persoName = perso.getName() + "_" + this.getNameFromComportement(perso.getComportementPersonnage()) + type;
        } else if (this.aImage(perso.getName() + type + indice, COMBAT)) {
            persoName = perso.getName() + type;
        } else if (this.aImage(this.getNameFromComportement(perso.getComportementPersonnage()) + type + indice, COMBAT)) {
            persoName = this.getNameFromComportement(perso.getComportementPersonnage()) + type;
        }
        while (this.aImage(persoName + indice, COMBAT)) {
            ImageManager imageManager = new ImageManager();
            imageManager.setImage(this.getImage(this.getInputStreamImage(persoName + indice, COMBAT)));
            imageManager.setDisplayTime(120);
            imageManager.setDeplacement(0);
            images.put(indice, imageManager);
            indice++;
        }
        if (!persoName.equals("")) {
            ImageManager imageManager = new ImageManager();
            imageManager.setImage(this.getImage(this.getInputStreamImage(persoName + "1", COMBAT)));
            imageManager.setDisplayTime(120);
            imageManager.setDeplacement(0);
            images.put(indice, imageManager);
        }
        if (images.isEmpty() && !"".equals(type) && !"_esquive".equals(type)) {
            images = this.getImageCombat(perso, "");
        }
        return images;
    }

    public Map<Integer, ImageManager> getImageCombatAttaque(Character perso) {
        Map<Integer, ImageManager> images = this.getImageCombat(perso, "");
        return images;
    }

    public Map<Integer, ImageManager> getImageCombatAttaqueCritique(Character perso) {
        Map<Integer, ImageManager> images = this.getImageCombat(perso, "_critique");
        return images;
    }

    public Map<Integer, ImageManager> getImageCombatDistant(Character perso) {
        Map<Integer, ImageManager> images = this.getImageCombat(perso, "_distant");
        return images;
    }

    public Map<Integer, ImageManager> getImageCombatDistantCritique(Character perso) {
        Map<Integer, ImageManager> images = this.getImageCombat(perso, "_distant_critique");
        return images;
    }

    public Map<Integer, ImageManager> getImageCombatEsquive(Character perso) {
        Map<Integer, ImageManager> images = this.getImageCombat(perso, "_esquive");
        return images;
    }

    public Map<Integer, ImageManager> getImageCombatSoin(Character perso) {
        Map<Integer, ImageManager> images = this.getImageCombat(perso, "_soin");
        return images;
    }

    public static ImageIcon getImageIconMapFromPersonnage(Character perso) {
        return _ImagePersonnage.getImageIconMap(perso);
    }

    public static ImageIcon getImageIconMapFocusFromPersonnage(Character perso) {
        return _ImagePersonnage.getImageIconMapFocus(perso);
    }

    public ImageIcon getImageIconMap(Character perso) {
        if (perso.getEtat() == Etat.attendre) {
            if (this.aImage(perso.getName() + "_" + this.getNameFromComportement(perso.getComportementPersonnage()) + "_attente", MAP)) {
                return this.getImageIcon(this.getInputStreamImage(perso.getName() + "_" + this.getNameFromComportement(perso.getComportementPersonnage()) + "_attente", MAP));
            } else if (this.aImage(perso.getName() + "_attente", MAP)) {
                return this.getImageIcon(this.getInputStreamImage(perso.getName() + "_attente", MAP));
            } else if (this.aImage(this.getNameFromComportement(perso.getComportementPersonnage()) + "_attente", MAP)) {
                return this.getImageIcon(this.getInputStreamImage(this.getNameFromComportement(perso.getComportementPersonnage()) + "_attente", MAP));
            }
        } else {
            if (this.aImage(perso.getName() + "_" + this.getNameFromComportement(perso.getComportementPersonnage()), MAP)) {
                return this.getImageIcon(this.getInputStreamImage(perso.getName() + "_" + this.getNameFromComportement(perso.getComportementPersonnage()), MAP));
            } else if (this.aImage(perso.getName(), MAP)) {
                return this.getImageIcon(this.getInputStreamImage(perso.getName(), MAP));
            } else if (this.aImage(this.getNameFromComportement(perso.getComportementPersonnage()), MAP)) {
                return this.getImageIcon(this.getInputStreamImage(this.getNameFromComportement(perso.getComportementPersonnage()), MAP));
            }
        }
        return this.getImageIcon(this.getInputStreamImage(DEFAULT_IMAGE, MAP));
    }

    public ImageIcon getImageIconMapFocus(Character perso) {
        Character p = (Character) perso;
        if (p.getEtat() == Etat.attendre) {
            return this.getImageIconMap(p);
        } else {
            if (this.aImage(perso.getName() + "_" + this.getNameFromComportement(p.getComportementPersonnage()) + "_focus", MAP)) {
                return this.getImageIcon(this.getInputStreamImage(perso.getName() + "_" + this.getNameFromComportement(p.getComportementPersonnage()) + "_focus", MAP));
            } else if (this.aImage(perso.getName() + "_focus", MAP)) {
                return this.getImageIcon(this.getInputStreamImage(perso.getName() + "_focus", MAP));
            } else if (this.aImage(this.getNameFromComportement(p.getComportementPersonnage()), MAP)) {
                return this.getImageIcon(this.getInputStreamImage(this.getNameFromComportement(p.getComportementPersonnage()), MAP));
            }
        }
        return this.getImageIcon(this.getInputStreamImage(DEFAULT_IMAGE, MAP));
    }

}
