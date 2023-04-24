package actions;

import Tools.YiBaHelper;
import YibaMod.YibaMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class ExplosionDaiLogAction extends AbstractGameAction {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("Explosion01");

    boolean playBgm = false;

    int num = 0;

    public ExplosionDaiLogAction(int num1, float dur1){
        this.duration = dur1;
        this.num = num1;
        this.actionType = AbstractGameAction.ActionType.WAIT;
    }

    @Override
    public void update(){
        YibaMod.logger.info("开始：ExplosionDaiLogAction");
        if(!this.playBgm){
            this.playBgm = true;
            //addToBot(new SFXAction(YiBaHelper.MakeSoundPath("Explosion")));
            addToBot(new TalkAction(true, uiStrings.TEXT[1], 2.0F, 2.0F));
        }
        tickDuration();
        YibaMod.logger.info("完成：ExplosionDaiLogAction");
        //this.isDone = true;
    }
}
