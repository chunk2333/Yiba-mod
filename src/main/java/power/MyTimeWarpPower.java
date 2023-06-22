package power;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;

public class MyTimeWarpPower extends AbstractPower {
    public static final String POWER_ID = "MyTimeWarp";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("MyTimeWarp");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESC = powerStrings.DESCRIPTIONS;

    private static final int STR_AMT = 2;

    private static final int COUNTDOWN_AMT = 12;

    public MyTimeWarpPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "MyTimeWarp";
        this.owner = owner;
        this.amount = 0;
        updateDescription();
        loadRegion("time");
        this.type = AbstractPower.PowerType.BUFF;
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
    }

    public void updateDescription() {
        this.description = DESC[0] + "12" + DESC[1] + '2' + DESC[2];
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        flashWithoutSound();
        this.amount++;
        if (this.amount == 12) {
            this.amount = 0;
            AbstractPlayer p = AbstractDungeon.player;
            playApplyPowerSfx();
            AbstractDungeon.actionManager.callEndTurnEarlySequence();
            CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
            AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 2), 2));
        }
        updateDescription();
    }
}
