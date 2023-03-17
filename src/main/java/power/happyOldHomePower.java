
//happyOldHomePower
package power;

import YibaMod.YibaMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class happyOldHomePower extends AbstractPower {
    public static final String POWER_ID = "happyOldHomePower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("happyOldHomePower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;

    public happyOldHomePower(AbstractCreature owner, int amt) {
        super();
        YibaMod.logger.info("暗箱操作被载入");
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        this.description = DESCRIPTIONS[0]+ amt + DESCRIPTIONS[1];
        this.ID = "happyOldHomePower";
        this.owner = owner;
        this.amount = amt;
        updateDescription();
        //this.img = ImageMaster.loadImage("img/powers/happyOldHomePower.png");
        YibaMod.logger.info("载入暗箱操作的图标");
        this.region48 = atlas_self.findRegion("48/happyOldHomePower");
        this.region128 = atlas_self.findRegion("128/happyOldHomePower");
        this.type = AbstractPower.PowerType.BUFF;

    }

    public void atStartOfTurn(){
        //回合开始时
    }
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //使用卡片时触发
        flash();
        addToBot(new DrawCardAction(AbstractDungeon.player, this.amount));
    }
}

