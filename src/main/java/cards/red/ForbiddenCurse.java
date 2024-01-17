package cards.red;
//禁制诅咒
import YibaMod.YibaMod;
import actions.GetCardFromExhaustPile;
import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomSavable;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import power.ForbiddenCursePower;

@SpirePatch2(clz = AbstractPlayer.class, method = "useCard")
public class ForbiddenCurse extends CustomCard implements CustomSavable<String> {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("ForbiddenCurse");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/ForbiddenCurse.png";

    private static final int COST = 1;

    public static final String ID = "ForbiddenCurse";

    public static int amout = 0;

    public ForbiddenCurse() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        p.gainEnergy(this.magicNumber);
        addToBot(new ApplyPowerAction(p, p, new ForbiddenCursePower(p, this.magicNumber, this.upgraded), this.magicNumber));
    }


    @SpireInsertPatch(rloc = 0, localvars = {"c", "monster", "energyOnUse"})
    public static void ForbiddenCurseOnUseCard(@ByRef AbstractCard[] ___c, @ByRef AbstractMonster[] ___monster, @ByRef int[] ___energyOnUse){
        if (___c[0].type == CardType.ATTACK){
            //YibaMod.logger.info("你打出了其他的卡牌");
            amout += 1;
            if (amout == 2){
                AbstractDungeon.actionManager.addToBottom(new GetCardFromExhaustPile(new ForbiddenCurse()));
                amout = 0;
            }
        }
    }

    @Override
    public String onSave(){
        ForbiddenCurse.amout = 0;
        return "-1";
    }

    @Override
    public void onLoad(String s){
        ForbiddenCurse.amout = 0;
    }

    @Override
    public AbstractCard makeCopy() {
        return new ForbiddenCurse();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}

@SpirePatch2(clz = AbstractPlayer.class, method = "onVictory")
class ForbiddenCurseOnVictory{

    @SpirePostfixPatch
    public static void PostFix(){
        ForbiddenCurse.amout = 0;
    }

}
