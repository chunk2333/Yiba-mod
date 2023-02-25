package power;
//HarmonyOfLightAndDustPower
import basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen.UpdateStats;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class HarmonyOfLightAndDustPower extends AbstractPower {
    public static final String POWER_ID = "HarmonyOfLightAndDustPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("HarmonyOfLightAndDustPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;

    private int useNum = 1;

    public HarmonyOfLightAndDustPower(AbstractCreature owner, int amt) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        updateDescription();
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/HarmonyOfLightAndDustPower");
        this.region128 = atlas_self.findRegion("128/HarmonyOfLightAndDustPower");
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
        UpdateStats.logger.info("和光同尘：当前回合打出的牌数：" + AbstractDungeon.actionManager.cardsPlayedThisTurn.size());
        UpdateStats.logger.info("和光同尘：已经触发次数：" + this.useNum);
        if (!card.purgeOnUse && this.amount > 0 && this.useNum <= this.amount){
            this.useNum = this.useNum + 1;
            flash();
            //返回费用
            addToTop(new GainEnergyAction(card.cost));
            UpdateStats.logger.info("和光同尘触发，返回费用");
        }
    }
}