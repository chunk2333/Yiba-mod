package power;

import basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen.UpdateStats;
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

    private int useNum = 1;

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
        this.useNum = this.useNum + 1;
    }

    public void atStartOfTurn(){
        //回合开始时
        this.useNum = 1;
    }
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //使用卡片时触发
        //flash();
        //addToBot(new DrawCardAction(AbstractDungeon.player, this.amount));
        this.onDrawOrDiscard();
        UpdateStats.logger.info("诡谲使用卡牌触发 card.discard：" + card.discard);

        UpdateStats.logger.info("诡谲使用卡牌触发 card.draw：" + card.draw);

    }
    public void onDrawOrDiscard(){
        UpdateStats.logger.info("诡谲触发：onDrawOrDiscard" );
    }

}

