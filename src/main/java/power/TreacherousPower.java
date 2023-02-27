package power;

import actions.BetterDiscardPileToHandSkillAction;
import actions.BetterDrawPileToHandSkillAction;
import actions.SetDrawPileSkill0CostActions;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TreacherousPower extends AbstractPower {
    public static final String POWER_ID = "TreacherousPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("TreacherousPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;

    public boolean isUsed;

    public TreacherousPower(AbstractCreature owner, int amt) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        this.description = DESCRIPTIONS[0]+ amt + DESCRIPTIONS[1];
        this.ID = "TreacherousPower";
        this.owner = owner;
        this.amount = amt;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/TreacherousPower");
        this.region128 = atlas_self.findRegion("128/TreacherousPower");
        this.type = AbstractPower.PowerType.BUFF;

    }

    public void atStartOfTurn(){
        //回合开始时
        isUsed=false;

    }
    public void atStartOfTurnPostDraw(){
        //抽卡后
        flash();
        addToBot(new BetterDrawPileToHandSkillAction(this.amount));
        addToBot(new BetterDiscardPileToHandSkillAction(this.amount));
    }
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(!isUsed){
            addToBot(new SetDrawPileSkill0CostActions());
            addToBot(new SetDrawPileSkill0CostActions(true));
        }
        isUsed = true;
    }
}