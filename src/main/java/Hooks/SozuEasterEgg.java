package Hooks;
//需要在：CombatRewardScreen中的positionRewards来实现
import YibaMod.YibaMod;
import basemod.BaseMod;
import basemod.interfaces.PotionGetSubscriber;
import basemod.interfaces.StartGameSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.Sozu;

import java.util.ArrayList;
import java.util.Iterator;

@SpireInitializer
public class SozuEasterEgg implements PotionGetSubscriber, StartGameSubscriber {

    private boolean isLoadGame = false;

    public SozuEasterEgg(){
        BaseMod.subscribe(this);
    }

    public static void initialize(){
        new SozuEasterEgg();
    }

    @Override
    public void receiveStartGame(){
        this.isLoadGame = true;
    }

    @Override
    public void receivePotionGet(AbstractPotion p){
//        if (!this.isLoadGame){
//            return;
//        }
//        int number = 0;
//        int potionsNum = AbstractDungeon.player.potionSlots;
//        Iterator var1 = AbstractDungeon.player.potions.iterator();
//        YibaMod.logger.info("获得药水：" + p.name);
//
//        while(var1.hasNext()){
//            AbstractPotion potion = (AbstractPotion) var1.next();
//            YibaMod.logger.info("当前药水：" + potion.name);
//            if(potion.ID.equals("FairyPotion") || potion.ID.equals("GhostInAJar")){
//                number += 1;
//            }
//        }
//
//        if (p.ID.equals("FairyPotion") || p.ID.equals("GhostInAJar")){
//            number += 1;
//        }
//
//        YibaMod.logger.info("当前药水槽位被占用：" + number);
//
//        if(!AbstractDungeon.player.hasRelic("Sozu") && number == potionsNum){
//            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(AbstractDungeon.player.drawX, AbstractDungeon.player.drawY, new Sozu());
//        }
    }
}
