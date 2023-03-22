package power;

import YibaMod.YibaMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import power.abstracrt.ElementPower;

public class TestElementPower extends ElementPower {
    public static final String POWER_ID = "TestElementPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("TestElementPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas;

    public static TextureAtlas atlas_self;

    public TestElementPower(AbstractCreature owner, int amt) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        this.description = DESCRIPTIONS[0];
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/CoffeeBeanPower");
        this.region128 = atlas_self.findRegion("128/CoffeeBeanPower");
        this.type = AbstractPower.PowerType.BUFF;
    }





    @Override
    public void triggerElement(String reactionName){
        YibaMod.logger.info("TestElementPower:" + reactionName);
    }




    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }




}
