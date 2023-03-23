package Tools;

import YibaMod.YibaMod;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.powers.AbstractPower;
import pathes.AbstractCardEnum;

import java.util.ArrayList;

public class YiBaHelper {
    public static String MakeSoundPath(String id){
        return "YiBa_Sound:" + id;
    }

    public static Boolean hasMod(String modId){
        return Loader.isModLoadedOrSideloaded(modId);
    }

    public static int getPlayerMystery(){
        int mystery = 0;
        AbstractPlayer p = AbstractDungeon.player;
        for(AbstractPower power:p.powers){
            if(power.ID.equals("MysteryPower")){
                mystery = power.amount;
            }
        }
        return mystery;
    }

    public static AbstractCard getRandomCurseCard(){
        //取随机诅咒牌
        ArrayList<AbstractCard> list = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.curseCardPool.group) {
            if(c.type== AbstractCard.CardType.CURSE){
                list.add(c);
            }
        }
        return list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1));
    }

    public static AbstractCard getRandomElementCard(){

        ArrayList<AbstractCard> list = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.srcRareCardPool.group) {
            if(c.hasTag(YibaMod.ELEMENT)){
                list.add(c);
            }
        }
        for (AbstractCard c : AbstractDungeon.srcUncommonCardPool.group) {
            if(c.hasTag(YibaMod.ELEMENT)){
                list.add(c);
            }
        }
        for (AbstractCard c : AbstractDungeon.srcCommonCardPool.group) {
            if(c.hasTag(YibaMod.ELEMENT)){
                list.add(c);
            }
        }
        for (AbstractCard c : AbstractDungeon.srcColorlessCardPool.group) {
            if(c.hasTag(YibaMod.ELEMENT)){
                list.add(c);
            }
        }
        for (AbstractCard c : AbstractDungeon.srcCurseCardPool.group) {
            if(c.hasTag(YibaMod.ELEMENT)){
                list.add(c);
            }
        }
        return list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1));
    }
}