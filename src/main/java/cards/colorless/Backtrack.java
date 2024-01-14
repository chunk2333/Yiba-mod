package cards.colorless;
//回溯
import Tools.YiBaHelper;
import actions.PlayCardAction;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@SpirePatch2(clz = AbstractPlayer.class, method = "useCard")
public class Backtrack extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Backtrack");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/Backtrack.png";

    private static final int COST = 1;

    public static final String ID = "Backtrack";

    public Backtrack() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.NONE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PlayCardAction(YiBaHelper.LastPlayedCard, YiBaHelper.LastAttackMonster));
    }

    @SpireInsertPatch(rloc = 0, localvars = {"c", "monster", "energyOnUse"})
    public static void BacktrackOnUseCard(@ByRef AbstractCard[] ___c, @ByRef AbstractMonster[] ___monster, @ByRef int[] ___energyOnUse){
        if(___c[0].cardID.equals("Backtrack")){
            return;
        }
        YiBaHelper.LastPlayedCard = ___c[0];
        YiBaHelper.LastAttackMonster = ___monster[0];
    }

    @Override
    public void triggerOnGlowCheck() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + YiBaHelper.LastPlayedCard.name;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }


    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Backtrack();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
