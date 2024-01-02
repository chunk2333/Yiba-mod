package patchs;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.Random;

@SpirePatch(clz = PressEndTurnButtonAction.class, method = "update")
public class PressEndTurnButtonActionPatch {

    @SpirePostfixPatch
    public static void PostFix(){
        boolean isPlay = false;
        for(AbstractMonster m :AbstractDungeon.getCurrRoom().monsters.monsters){
            if(m.id.equals("Dio")){
                isPlay = true;
                break;
            }
        }
        Random random_ = new Random();
        int randomNumber = random_.nextInt(100) + 1;
        if (randomNumber <= 5){
            isPlay = true;
        }

        if (!isPlay){
            return;
        }

        int random;
        random = AbstractDungeon.cardRandomRng.random(1, 3); //随机数
        switch (random){
            case 1:
                CardCrawlGame.sound.playA("TimeStop", 0F);
                break;
            case 2:
                CardCrawlGame.sound.playA("TimeStop01", 0F);
                break;
            case 3:
                CardCrawlGame.sound.playA("TimeStop02", 0F);
                break;
            default:
                break;
        }

    }
}
