package cards.curse;
//色欲
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.screens.options.SettingsScreen;

@SpirePatch(clz = SettingsScreen.class, method = "update")
public class Lust extends CustomCard {

    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Lust");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/Lust.png";

    private static final int COST = -2;

    public static final String ID = "Lust";

    public Lust() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
        //固有
        this.isInnate = true;
        //不能被打出
        this.dontTriggerOnUseCard = true;
    }

    public static boolean hasLust(){
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group){
            if (c.cardID.equals("Lust")){
                return true;
            }
        }
        return false;
    }

    @SpirePostfixPatch
    public static void Postfix(SettingsScreen __instance) {
        if (AbstractDungeon.player != null &&
                AbstractDungeon.getCurrMapNode() != null &&
                AbstractDungeon.getCurrRoom() != null) {
            boolean shouldHide = false;
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !(AbstractDungeon.getCurrRoom()).isBattleOver && !(AbstractDungeon.getCurrRoom() instanceof EventRoom)) {
                if (Lust.hasLust()) {
                    shouldHide = true;
                }
            } else {
                if (Lust.hasLust()) {
                    shouldHide = true;
                }
            }
            if (shouldHide) {
                __instance.exitPopup.hide();
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) { }

    @Override
    public AbstractCard makeCopy() {
        return new Lust();
    }
    @Override
    public void upgrade() { }
}
