package relics;
//科尔瓦克斯外壳
import YibaMod.YibaMod;
import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

@SpirePatch(clz = AbstractPlayer.class, method = "increaseMaxOrbSlots")
public class KorvaxCasing extends CustomRelic {
    public static final String ID = YibaMod.makeModID("KorvaxCasing");

    private static final String IMG = "img/relics/KorvaxCasing.png";

    private static final String IMG_OTL = "img/relics/outline/null.png";

    public KorvaxCasing() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), AbstractRelic.RelicTier.UNCOMMON, LandingSound.FLAT);
    }


    @SpireInsertPatch(loc = 2940)
    public static void InsertFix(){
        if(AbstractDungeon.player.hasRelic(YibaMod.makeModID("KorvaxCasing"))){
            AbstractRelic r = AbstractDungeon.player.getRelic(YibaMod.makeModID("KorvaxCasing"));
            r.counter += 1;
            if (r.counter == 3){
                r.flash();
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, r));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, 1), 1));
                r.counter = 0;
            }
        }
    }

    @Override
    public void atBattleStartPreDraw() {
        this.counter = 0;
    }

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        this.counter = -1;
    }
}
