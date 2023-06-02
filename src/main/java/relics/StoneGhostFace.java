package relics;
//石鬼面
import Tools.YiBaHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;

public class StoneGhostFace extends CustomRelic {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("High");

    public static final String ID = "StoneGhostFace";

    private static final String IMG = "img/relics/StoneGhostFace.png";

    private static final String IMG_OTL = "img/relics/outline/StoneGhostFace.png";

    public StoneGhostFace() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        //high！
        int random;
        random = AbstractDungeon.relicRng.random(1,100); //随机数
        AbstractPlayer p = AbstractDungeon.player;
        if(random <=5 ){
            addToBot(new SFXAction(YiBaHelper.MakeSoundPath("Dio_wryyyy")));
            addToBot(new TalkAction(true, uiStrings.TEXT[1], 1.0F, 2.0F));

            if (Settings.FAST_MODE) {
                addToBot(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.1F));
            } else {
                addToBot(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.5F));
            }



        }else {
            addToBot(new SFXAction(YiBaHelper.MakeSoundPath("Dio_high")));
            addToBot(new TalkAction(true, uiStrings.TEXT[0], 1.0F, 2.0F));
        }


    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new StoneGhostFace();
    }

}
