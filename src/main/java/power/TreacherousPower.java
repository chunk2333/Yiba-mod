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
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TreacherousPower extends AbstractPower {
    public static final String POWER_ID = "TreacherousPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("TreacherousPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;

    public boolean isUsed;
    //BUG：诡谲能力在触发时，先把抽牌堆/弃牌堆的所有技能牌临时费用-1，然后再让你选择一张到你手牌
    //现在你查看抽牌堆/弃牌的牌组，发现技能牌的费用都比之前少了1费
    //这时，你打出任意一张牌都会把抽牌堆/弃牌的所有变过费用的技能牌恢复原样
    //这时你用必备工具丢了张本能反应。出bug了
    //因为你这回合刚开始，没有打出任何一张卡，所以，抽牌/弃牌堆的所有技能牌费用-1，还没恢复你就抽上来了
    //尖塔这游戏动画大于一切
    //所以在打开选牌界面，底下的代码就已经跑完了。导致我必须触发一个开关来恢复卡牌的费用
    //且因为原版能力牌没有与弃牌相关的接口，so，这怎么修？

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
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if(!isUsed){
            addToBot(new SetDrawPileSkill0CostActions());
            addToBot(new SetDrawPileSkill0CostActions(true));
        }
        isUsed = true;
    }
}