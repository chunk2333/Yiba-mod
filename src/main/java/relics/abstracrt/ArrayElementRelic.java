package relics.abstracrt;

import java.util.ArrayList;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import relics.*;
import relics.Witch.*;

public class ArrayElementRelic {

    public static ArrayList<ElementRelic> relics;

    public static ArrayList<ElementRelic> getElementRelic(){
        ArrayList<ElementRelic> relics = new ArrayList<ElementRelic>();
        for (AbstractRelic r : AbstractDungeon.player.relics){
            if(r.relicId.equals("TestTriggerElement")){
                relics.add(new TestTriggerElement());
            }
            if(r.relicId.equals("TheLastCoin")){
                relics.add(new TheLastCoin());
            }
        }

        return relics;
    }

}
