package cards.red;
//灭族之痛
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class genocidePain extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("genocidePain");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/genocidePain.png";

    private static final int COST = -2;

    public static final String ID = "genocidePain";

    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public genocidePain() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.NONE);
        AbstractCard c = new Madness();
        if(this.upgraded){
            c.upgrade();
        }
        this.cardsToPreview = c;
        //this.dontTriggerOnUseCard = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) { }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void triggerOnExhaust() {
        AbstractCard c = new Madness();
        if(this.upgraded){
            c.upgrade();
        }
        addToBot(new MakeTempCardInHandAction(c, 1, false));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new genocidePain();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            AbstractCard c = new Madness();
            c.upgrade();
            this.cardsToPreview = c;
        }
    }
}
