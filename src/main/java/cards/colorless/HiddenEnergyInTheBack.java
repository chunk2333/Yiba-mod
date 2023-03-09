package cards.colorless;
//后背隐藏能源

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import power.NextTurnDiePower;

public class HiddenEnergyInTheBack extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("HiddenEnergyInTheBack");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards_Seles/HiddenEnergyInTheBack.png";

    private static final int COST = 3;

    public static final String ID = "HiddenEnergyInTheBack";

    public HiddenEnergyInTheBack() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.RARE, CardTarget.ALL_ENEMY);
        //添加消耗
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        //社保特效
        addToBot(new VFXAction(p, new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.7F));
        //对所有敌人造成伤害
        addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(2147483647, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
        //加下回合死亡的buff
        addToBot(new ApplyPowerAction(p, p, new NextTurnDiePower(p)));
        //移除牌库
        AbstractDungeon.player.masterDeck.removeCard(this.cardID);
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new HiddenEnergyInTheBack();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //添加名字升级
            upgradeName();
            //添加保留
            this.retain = true;
            //重载描述
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}