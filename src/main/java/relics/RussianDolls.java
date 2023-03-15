package relics;
//俄罗斯套娃
import basemod.abstracts.CustomRelic;
import basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen.UpdateStats;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.concurrent.TimeUnit;

@SpirePatch(cls = "basemod.BaseMod", method = "publishRelicGet")
public class RussianDolls extends CustomRelic {
    public static final String ID = "RussianDolls";

    private static final String IMG = "img/relics/RussianDolls.png";

    private static final String IMG_OTL = "img/relics/outline/RussianDolls.png";

    private static boolean isGive = false;

    private static boolean wasLoad = false;

    public RussianDolls() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.FLAT);
        wasLoad = false;
        isGive = false;
        //UpdateStats.logger.info("构造函数被触发是否已载入：" + wasLoad + "是否可额外获得!" + isGive);
    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "setEmeraldElite")
    public static class isload{

        @SpireInsertPatch(loc = 683)
        public static void Insert(){
            //玩家已初始化完毕
            AbstractPlayer p = AbstractDungeon.player;

//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            if(p.hasRelic("RussianDolls")){

                wasLoad = true;
                UpdateStats.logger.info("俄罗斯套娃：玩家已完全载入");
            }
        }
    }

    @SpireInsertPatch(loc = 2522, localvars = {"r"})
    public static void Insert(AbstractRelic r){
        AbstractPlayer p = AbstractDungeon.player;
        //UpdateStats.logger.info("是否已载入：" + wasLoad + "是否可额外获得!" + isGive);

        if(!wasLoad){
            //UpdateStats.logger.info("俄罗斯套娃触发，还未完全载入");
            return;
        }

        if(r.relicId.equals("AllInOneBag")          ||
                r.relicId.equals("BlightChest")     ||
                r.relicId.equals("BottledMonster")  ||
                r.relicId.equals("CardModifier")    ||
                r.relicId.equals("CardPrinter")     ||
                r.relicId.equals("CardShredder")    ||
                r.relicId.equals("EventfulCompass") ||
                r.relicId.equals("LoadoutBag")      ||
                r.relicId.equals("LoadoutCauldron") ||
                r.relicId.equals("OrbBox")          ||
                r.relicId.equals("PowerGiver")      ||
                r.relicId.equals("TildeKey")        ||
                r.relicId.equals("TheBin")

        ){
            UpdateStats.logger.info("俄罗斯套娃触发，是Loadout：" +  r.relicId);
            return;
        }

        if(isGive) {
            isGive = false;
            wasLoad = true;
        }else {
            if(p.hasRelic("RussianDolls")){
                isGive = true;
                wasLoad = true;
                //给随机遗物
                RussianDolls rd = new RussianDolls();
                rd.flash();
                rd.update();
                AbstractRelic abstractRelic = AbstractDungeon.returnRandomScreenlessRelic(
                        AbstractDungeon.returnRandomRelicTier());
                UpdateStats.logger.info("俄罗斯套有遗物获取：" +  r.relicId + "额外给的：" + abstractRelic.relicId);

                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(p.drawX, p.drawY, abstractRelic);
                UpdateStats.logger.info("俄罗斯套娃触发，额外获得：" +  abstractRelic.relicId);
                wasLoad = true;
            }
        }

    }


    @Override
    public void atBattleStart() {

    }

    @Override
    public void onVictory() {

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        isGive = false;
        wasLoad = true;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RussianDolls();
    }
}