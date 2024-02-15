package power;

import YibaMod.YibaMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;

public class UnstableTime extends AbstractPower {
    public static final String POWER_ID = YibaMod.makeModID("UnstableTime");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;

    public UnstableTime(AbstractCreature owner) {
        super();
        atlas = new TextureAtlas(Gdx.files.internal("powers/powers.atlas"));
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 0;
        this.region48 = atlas.findRegion("48/time");
        this.region48.flip(false, true);
        this.region128 = atlas.findRegion("128/time");
        this.region128.flip(false, true);
        updateDescription();
        this.type = PowerType.BUFF;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        flashWithoutSound();
        this.amount++;
        if (this.amount == 12) {
            this.amount = 0;
            playApplyPowerSfx();
            CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
            AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                if (!m.isDead && !m.isDying){
                    m.takeTurn();
                    m.applyPowers();
                }
            }
        }
        updateDescription();
    }
}
