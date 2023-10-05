package pathes;
//为遗物：钨合金棍添加触发时的音效
import Tools.YiBaHelper;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

//找到钨合金棍遗物的 onLoseHpLast 函数
@SpirePatch(cls = "com.megacrit.cardcrawl.relics.TungstenRod", method = "onLoseHpLast")
public class TungstenRodVoice {
    //函数的第18行之前加入代码
    @SpireInsertPatch(loc = 18)
    public static void Voice() {
        //播放 棍子掉落的声音
        AbstractDungeon.actionManager.addToTop(new SFXAction(YiBaHelper.MakeSoundPath("Fall_Steel")));
    }
}