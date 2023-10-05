package patchs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AbstractPower_Self {

    public static TextureAtlas atlas;

    public TextureAtlas.AtlasRegion region48;

    public TextureAtlas.AtlasRegion region128;
    public String name;

    public String description;

    public String ID;

    public Texture img;

    public int amount = -1;

    public int priority = 5;

    public PowerType type = PowerType.BUFF;

    protected boolean isTurnBased = false;

    public boolean isPostActionPower = false;

    public boolean canGoNegative = false;

    public static String[] DESCRIPTIONS;

    public enum PowerType {
        BUFF, DEBUFF;
    }

    public static void initialize() {
        atlas = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
    }

    public void loadRegion(String fileName) {
        this.region48 = atlas.findRegion("48/" + fileName);
        this.region128 = atlas.findRegion("128/" + fileName);
    }

}
