package relics;
//赤石
import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.FeedAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

@SpirePatch2(clz = FeedAction.class, method = "update")
public class Akaishi extends CustomRelic {

    public static final String ID = "Akaishi";

    private static final String IMG = "img/relics/Akaishi.png";

    private static final String IMG_OTL = "img/relics/outline/Akaishi.png";

    public Akaishi() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @SpireInsertPatch(loc = 39)
    public static void InsertFix(FeedAction __instance){
        if (!AbstractDungeon.player.hasRelic("Akaishi")){
            if (__instance.target.id.equals("AcidSlime_L") ||
                    __instance.target.id.equals("AcidSlime_M") ||
                    __instance.target.id.equals("AcidSlime_S") ||
                    __instance.target.id.equals("SlimeBoss") ||
                    __instance.target.id.equals("SpikeSlime_L") ||
                    __instance.target.id.equals("SpikeSlime_M") ||
                    __instance.target.id.equals("SpikeSlime_S"))
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(AbstractDungeon.player.drawX, AbstractDungeon.player.drawY, new Akaishi());
        }
    }

    @Override
    public int onPlayerHeal(int healAmount) {
        if (healAmount > 0){
            flash();
            addToBot(new GainEnergyAction(1));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
        return healAmount;
    }

    @Override
    public int onLoseHpLast(int damageAmount) {
        if (damageAmount > 0) {
            flash();
            addToBot(new GainEnergyAction(1));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
        return damageAmount;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Akaishi();
    }
}
