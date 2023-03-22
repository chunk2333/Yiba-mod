package power.abstracrt;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import power.*;
import relics.abstracrt.ElementRelic;

import java.util.ArrayList;

public class ArrayElementPower {

    public static ArrayList<ElementRelic> powers;

    public static ArrayList<ElementPower> getElementPower(){
        ArrayList<ElementPower> relics = new ArrayList<>();
        for (AbstractPower power : AbstractDungeon.player.powers){
            if(power.ID.equals("TestElementPower")){
                relics.add(new TestElementPower(AbstractDungeon.player,power.amount));
            }
            //魔力循环 能力
            if(power.ID.equals("MagicLoopPower")){
                relics.add(new MagicLoopPower(AbstractDungeon.player,power.amount));
            }
        }
        return relics;
    }
}
