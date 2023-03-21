package Tools;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

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
}