//哈哈，刀！TM全是刀！
package cards.green;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;

public class Hahaknife extends CustomCard{
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Hahaknife");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/Hahaknife.png";

    private static final int COST = 2;

    public static final String ID = "Hahaknife";
    public Hahaknife() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int cardnum_hand;
        int cardnum_add;
        cardnum_hand = p.hand.size();
        cardnum_add = BaseMod.MAX_HAND_SIZE - cardnum_hand;
        //取小刀卡的对象
        AbstractCard s = (new Shiv()).makeCopy();
        //添加指定手牌数量的小刀卡           //添加剩余手牌数的小刀到手牌
        addToTop(new MakeTempCardInHandAction(s, cardnum_add));
    }

    @Override

    public AbstractCard makeCopy() {
        return new Hahaknife();
    }
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}
