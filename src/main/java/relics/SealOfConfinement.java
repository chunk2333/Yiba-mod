package relics;
//禁锢之符
import YibaMod.YibaMod;
import actions.WaitActionYibaAction;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.NoDrawPower;

public class SealOfConfinement extends CustomRelic {

    public static final String ID = YibaMod.makeModID("SealOfConfinement");

    public static int canDrawAmount = 0;

    private static final String IMG = "img/relics/SealOfConfinement.png";

    private static final String IMG_OTL = "img/relics/outline/null.png";

    public SealOfConfinement() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new DrawCardAction(BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.gameHandSize));
        if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE){
            addToBot(new DrawCardAction(BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.size()));
        }
        addToBot(new WaitActionYibaAction(0.1F));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (AbstractDungeon.player.hasRelic("RedPants") || AbstractDungeon.player.hasPower("EnchantedGoldenApplePower") || AbstractDungeon.player.hasPower("RedPantsPower")){
                    tickDuration();
                    return;
                }
                YibaMod.actionList.add(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new NoDrawPower(AbstractDungeon.player)));
                if (AbstractDungeon.player.hasPower("No Draw")){
                    tickDuration();
                    this.isDone = true;
                }
            }
        });

        //YibaMod.actionList.add();


    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

//    @SpirePatch2(clz = DrawCardAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCreature.class, int.class, boolean.class})
//    public static class DrawCardActionFix02{
//        //@SpireInsertPatch(loc = 27, localvars = {"amount", "endTurnDraw"})
//        @SpirePostfixPatch
//        public static void InsertFix(DrawCardAction __instance, int ___amount, boolean ___endTurnDraw){
//            YibaMod.logger.info("InsertFix:" + ___amount + " boolean:" +  ___endTurnDraw);
//            if (___endTurnDraw){
//                YiBaHelper.canDraw = true;
//                canDrawAmount = ___amount;
//            }
//
//        }
//    }
//
//
//    @SpirePatch(clz = DrawCardAction.class, method = "update")
//    public static class DrawCardActionFix{
//        @SpireInsertPatch(loc = 79, localvars = {"followUpAction"})
//        public static SpireReturn<Void> InsertFix(DrawCardAction __instance, AbstractGameAction ___followUpAction){
//            YibaMod.logger.info("canDrawAmount:" + canDrawAmount);
//            if (AbstractDungeon.player.hasRelic(YibaMod.makeModID("SealOfConfinement"))){
//                if (YiBaHelper.canDraw){
//                    if (canDrawAmount == 0){
//                        YiBaHelper.canDraw = false;
//                        return SpireReturn.Continue();
//                    }
//                    canDrawAmount -= 1;
//                }
//                __instance.isDone = true;
//                if (___followUpAction != null){
//                    AbstractDungeon.actionManager.addToBottom(___followUpAction);
//                }
//                AbstractDungeon.player.getRelic(YibaMod.makeModID("SealOfConfinement")).flash();
//                return SpireReturn.Return();
//            }
//            return SpireReturn.Continue();
//        }
//    }

}
