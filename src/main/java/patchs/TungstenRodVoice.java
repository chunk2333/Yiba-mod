package patchs;
//为遗物：钨合金棍添加触发时的音效
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;

//找到钨合金棍遗物的 onLoseHpLast 函数   掉血时触发
@SpirePatch(cls = "com.megacrit.cardcrawl.relics.TungstenRod", method = "onLoseHpLast")
public class TungstenRodVoice {
    //函数的第18行之前加入代码
    @SpireInsertPatch(loc = 18)
    public static void Voice() {
        //播放 棍子掉落的声音
        //AbstractDungeon.actionManager.addToTop(new SFXAction(YiBaHelper.MakeSoundPath("Fall_Steel")));
        CardCrawlGame.sound.playA("Fall_Steel", 0F);
    }

    //实现当点开遗物页触发时
    //找到 SingleRelicViewPopup 中 initializeLargeImg函数
    @SpirePatch(cls = "com.megacrit.cardcrawl.screens.SingleRelicViewPopup", method = "initializeLargeImg")
    public static class TungstenRodVoiceOpen {
        //在124行代码中插入以下代码，并附带“relic”变量，这变量是类似c++的指针，会影响到原有的效果。
        @SpireInsertPatch(loc = 124, localvars = {"relic"})
        public static void fix(AbstractRelic ___relic) {
            if(___relic.relicId.equals("TungstenRod")){
                CardCrawlGame.sound.playA("Fall_Steel", 0F);
            }
        }
    }

    //拾起时触发
    @SpirePatch(cls = "com.megacrit.cardcrawl.relics.AbstractRelic", method = "onEquip")
    public static class TungstenRodVoiceOnEquip {
        @SpireInsertPatch(loc = 124, localvars = {"relicId"})
        public static void fix(String relicId) {
            if(relicId.equals("TungstenRod")){
                CardCrawlGame.sound.playA("Fall_Steel", 0F);
            }
        }
    }

    //掉落在奖励列表
    @SpirePatch(clz = RewardItem.class, method = SpirePatch.CONSTRUCTOR, paramtypez={AbstractRelic.class})
    public static class TungstenRodVoiceSpawn {
        @SpireInsertPatch(loc = 149, localvars = {"relic"})
        public static void fix(AbstractRelic relic) {
            if(relic.relicId.equals("TungstenRod")){
                CardCrawlGame.sound.playA("Fall_Steel", 0F);
            }
        }
    }


}