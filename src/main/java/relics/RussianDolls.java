package relics;
//俄罗斯套娃
import Tools.YiBaHelper;
import basemod.abstracts.CustomRelic;
import YibaMod.YibaMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

@SpirePatch(cls = "basemod.BaseMod", method = "publishRelicGet")
public class RussianDolls extends CustomRelic {
    public static final String ID = "RussianDolls";

    private static final String IMG = "img/relics/RussianDolls.png";

    private static final String IMG_OTL = "img/relics/outline/RussianDolls.png";

    private static boolean isGive = false;

    public static boolean wasLoad = false;

    public RussianDolls() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.FLAT);
        wasLoad = false;
        isGive = false;
        //YibaMod.logger.info("构造函数被触发是否已载入：" + wasLoad + "是否可额外获得!" + isGive);
        YibaMod.logger.info("[RussianDolls]:spireTogether: " + YiBaHelper.hasMod("spireTogether"));
    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "loadSeeds")
    public static class isload{

        @SpireInsertPatch(loc = 511)
        public static void Insert(){
            //玩家已初始化完毕
            AbstractPlayer p = AbstractDungeon.player;

            if(p.hasRelic("RussianDolls")){

                wasLoad = true;
                YibaMod.logger.info("俄罗斯套娃：玩家已完全载入");
            }

            for(AbstractRelic re : p.relics){
                if(re.relicId.equals("RussianDolls")){
                    wasLoad = true;
                    YibaMod.logger.info("俄罗斯套娃：玩家已完全载入");
                    break;
                }
            }

            YibaMod.logger.info("SL(loadSeeds)：玩家已完全载入");
            if(p.hasRelic("BlindBox")){
                wasLoad = true;
                //AbstractDungeon.player.getRelic("BlindBox").atBattleStart();
            }

        }
    }

    @SpireInsertPatch(loc = 2522, localvars = {"r"})
    public static void Insert(AbstractRelic r){
        AbstractPlayer p = AbstractDungeon.player;
        //YibaMod.logger.info("是否已载入：" + wasLoad + "是否可额外获得!" + isGive);

        if(!wasLoad){
            //YibaMod.logger.info("俄罗斯套娃触发，还未完全载入");
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
            YibaMod.logger.info("俄罗斯套娃触发，是Loadout：" +  r.relicId);
            return;
        }

        if(isGive) {
            isGive = false;
            wasLoad = true;
        }else {
            if(p.hasRelic("RussianDolls")){
                isGive = true;
                wasLoad = true;

                if(BottledPoop.num_BottledPoop == 5){
                    BottledPoop.num_BottledPoop = 0;
                    YibaMod.logger.info("瓶装答辩层数：" + BottledPoop.num_BottledPoop);
                    isGive = false;
                    wasLoad = true;
                    return;
                }
                //给随机遗物
                RussianDolls rd = new RussianDolls();
                AbstractDungeon.player.getRelic("RussianDolls").flash();
                AbstractRelic abstractRelic = AbstractDungeon.returnRandomScreenlessRelic(
                        AbstractDungeon.returnRandomRelicTier());
                YibaMod.logger.info("俄罗斯套有遗物获取：" +  r.relicId + "额外给的：" + abstractRelic.relicId);

                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(p.drawX, p.drawY, abstractRelic);
                YibaMod.logger.info("俄罗斯套娃触发，额外获得：" +  abstractRelic.relicId);
                wasLoad = true;
            }
        }

    }

    @Override
    public boolean canSpawn() {
        return !YiBaHelper.hasMod("spireTogether");
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
