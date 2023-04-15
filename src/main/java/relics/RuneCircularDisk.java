package relics;
//符文圆盘
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

public class RuneCircularDisk extends CustomRelic {

    public static final String ID = "RuneCircularDisk";

    private static final String IMG = "img/relics/RuneCircularDisk.png";

    private static final String IMG_OTL = "img/relics/outline/RuneCircularDisk.png";

    public RuneCircularDisk() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStartPreDraw() {
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group){
            c.rawDescription = " ";
            c.description.clear();
            c.name = " ";
            c.assetUrl = "img/cards/test.png";
            c.initializeDescription();
        }

        for(AbstractCard c : AbstractDungeon.player.hand.group){
            c.rawDescription = " ";
            c.description.clear();
            c.name = " ";
            c.assetUrl = "img/cards/test.png";
            c.initializeDescription();
        }

        for(AbstractCard c : AbstractDungeon.player.drawPile.group){
            c.rawDescription = " ";
            c.description.clear();
            c.name = " ";
            c.assetUrl = "img/cards/test.png";
            c.initializeDescription();
        }
    }

    @Override
    public void atTurnStart() {
        for(AbstractCard c : AbstractDungeon.player.hand.group){
            c.rawDescription = " ";
            c.description.clear();
            c.name = " ";
            c.assetUrl = "img/cards/test.png";
            c.initializeDescription();
        }
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        drawnCard.rawDescription = " ";
        drawnCard.description.clear();
        drawnCard.name = " ";
        drawnCard.assetUrl = "img/cards/test.png";
        drawnCard.initializeDescription();
    }


    @Override
    public void onEquip() {
        //拾取时触发
        AbstractDungeon.player.energy.energyMaster++;
    }

    @Override
    public void onUnequip() {
        //丢弃时触发
        AbstractDungeon.player.energy.energyMaster--;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RuneCircularDisk();
    }
}
