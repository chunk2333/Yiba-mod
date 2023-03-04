package cards;

//ThunderstormVientiane
//风雷万象

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

public class ThunderstormVientiane extends CustomCard {
    public static final String IMG_PATH = "img/cards_Seles/ThunderstormVientiane.png";
    public static final String ID = "ThunderstormVientiane";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("ThunderstormVientiane");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    AbstractCard lastPlayedCard;

    public ThunderstormVientiane() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.ENEMY);

        this.baseDamage = 6;

        this.damage = this.baseDamage;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        //造成伤害
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if (lastPlayedCard == null) {
            lastPlayedCard = this;
            return;
        }
        if (lastPlayedCard.type == CardType.ATTACK) {
            //力量
            addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
        }
        if (lastPlayedCard.type == CardType.SKILL) {
            //敏捷
            addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, 1), 1));
        }
        if (lastPlayedCard.type == CardType.POWER) {
            //给予1点能力
            p.gainEnergy(1);
        }
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        lastPlayedCard = c;
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new ThunderstormVientiane();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点伤害
            upgradeName();
            upgradeDamage(3);

        }
    }
}