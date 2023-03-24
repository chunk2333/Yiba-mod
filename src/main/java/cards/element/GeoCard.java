package cards.element;
//岩元素牌
import Tools.YiBaHelper;
import YibaMod.YibaMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import power.GeoPower;

public class GeoCard extends CustomCard{
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("GeoCard");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/witch/GeoCard.png";
    private static final int COST = 2;
    public static final String ID = "GeoCard";
    public GeoCard() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.exhaust = true;
        this.tags.add(YibaMod.ELEMENT);
        this.tags.add(YibaMod.GEO);
        this.selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        //给予岩元素buff
        addToBot(new ApplyPowerAction(m, m, new GeoPower(m, YiBaHelper.getPlayerMystery()), 1));
        //造成伤害
        //AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void onRetained() {
        this.modifyCostForCombat(-1);
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new GeoCard();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}