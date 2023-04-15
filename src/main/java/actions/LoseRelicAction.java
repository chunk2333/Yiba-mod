package actions;

import YibaMod.YibaMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LoseRelicAction extends AbstractGameAction {

    private String relicId;

    public LoseRelicAction(String relicId1){
        this.relicId = relicId1;
    }

    @Override
    public void update() {
        if(AbstractDungeon.player.loseRelic(this.relicId)){
            YibaMod.logger.info("移除遗物：" + this.relicId);
            tickDuration();
        }

    }

}
