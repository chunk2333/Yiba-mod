package power.abstracrt;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import power.TestElementPower;
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
        }
        return relics;
    }
}
