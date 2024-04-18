package cards.curse;
//紧张(没有提示)
import YibaMod.YibaMod;
import actions.WaitActionYibaAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Nervous extends CustomCard {
    public static final String ID = YibaMod.makeModID("Nervous");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/Nervous.png";

    private static final int COST = -2;

    public Nervous() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
        this.dontTriggerOnUseCard = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
    }

    @Override
    public void triggerWhenDrawn() {
        YibaMod.actionList.add(new WaitActionYibaAction(5.0F));
        YibaMod.actionList.add(new PressEndTurnButtonAction());
    }

    @Override
    public AbstractCard makeCopy() {
        return new Nervous();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }
}
