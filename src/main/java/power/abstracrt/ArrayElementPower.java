package power.abstracrt;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import power.*;
import relics.abstracrt.ElementRelic;

import java.util.ArrayList;

public class ArrayElementPower {

    public static ArrayList<ElementRelic> powers;

    public static ArrayList<ElementPower> getElementPower(){
        ArrayList<ElementPower> list = new ArrayList<>();
        for (AbstractPower power : AbstractDungeon.player.powers){
            if(power.ID.equals("TestElementPower")){
                list.add(new TestElementPower(AbstractDungeon.player,power.amount));
            }
            //魔力循环 能力
            if(power.ID.equals("MagicLoopPower")){
                list.add(new MagicLoopPower(AbstractDungeon.player,power.amount));
            }
            //魔力精进 能力
            if(power.ID.equals("MagicalProgressPower")){
                list.add(new MagicalProgressPower(AbstractDungeon.player,power.amount));
            }
            //正反馈 能力
            if(power.ID.equals("PositiveFeedbackPower")){
                list.add(new PositiveFeedbackPower(AbstractDungeon.player,power.amount));
            }
        }
        return list;
    }
}
