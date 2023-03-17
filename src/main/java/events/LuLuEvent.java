package events;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import YibaMod.YibaMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import relics.LuLu;

public class LuLuEvent extends AbstractImageEvent {
    public static final String ID = "LuLu";

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("LuLu");

    public static final String NAME = eventStrings.NAME;

    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;

    public static final String[] OPTIONS = eventStrings.OPTIONS;

    private static final String INTRO_MSG = DESCRIPTIONS[0];//进入事件的描述

    private static final String LEAVE_MSG = DESCRIPTIONS[2]; //离开

    private static final String FRIGHT_END_MSG = DESCRIPTIONS[3]; //离开

    private static final String Change = OPTIONS[0]; //交换

    private static final String Bye = OPTIONS[1]; //购买

    private static final String Steal = OPTIONS[2]; //偷

    private static final String Leave = OPTIONS[3]; //无视

    private LuLuEvent.CurScreen screen = LuLuEvent.CurScreen.INTRO;
    private enum CurScreen {
        INTRO, END, END_FIGHT;
    }
    public LuLuEvent() {
        super(NAME, INTRO_MSG, "img/events/LuLuEvent.png");
        //this.noCardsInRewards = true;
        YibaMod.logger.info("进入事件：LuLu");
        this.imageEventText.clearRemainingOptions();
        if(AbstractDungeon.player.hasRelic("RocoCoin")){
            this.imageEventText.setDialogOption(Change);
        }else{
            //[锁定] 需要洛克贝
            this.imageEventText.setDialogOption(OPTIONS[4],true);
        }
        if(AbstractDungeon.player.gold>=200){
            this.imageEventText.setDialogOption(Bye);
        }else {
            this.imageEventText.setDialogOption(OPTIONS[5],true);
        }
        this.imageEventText.setDialogOption(Steal);
        this.imageEventText.setDialogOption(Leave);
    }
    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                if (buttonPressed == 0){
                    //交换
                    AbstractDungeon.player.loseRelic("RocoCoin");
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), (AbstractRelic)new LuLu());
                    this.imageEventText.clearRemainingOptions();
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption("离开");
                    this.imageEventText.updateDialogOption(0,"离开");
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.removeDialogOption(2);
                    this.imageEventText.removeDialogOption(3);
                    this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                    this.screen = CurScreen.END;
                    break;
                }
                if (buttonPressed == 1){
                    //购买
                    AbstractDungeon.player.loseGold(200);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), new LuLu());
                    this.imageEventText.clearRemainingOptions();
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption("离开");
                    this.imageEventText.updateDialogOption(0,"离开");
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.removeDialogOption(2);
                    this.imageEventText.removeDialogOption(3);
                    this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                    this.screen = CurScreen.END;
                    break;
                }
                if (buttonPressed == 2){
                    //偷
                    //(int) (AbstractDungeon.player.maxHealth*0.3)
                    AbstractDungeon.player.damage(new DamageInfo(null, 35, DamageInfo.DamageType.HP_LOSS));
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), new LuLu());
                    this.imageEventText.clearRemainingOptions();
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption("离开");
                    this.imageEventText.updateDialogOption(0,"离开");
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.removeDialogOption(2);
                    this.imageEventText.removeDialogOption(3);
                    this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                    this.screen = CurScreen.END;
                    break;
                }
                if (buttonPressed == 3){
                    //无视
                    this.imageEventText.clearRemainingOptions();
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption("离开");
                    this.imageEventText.updateDialogOption(0,"离开");
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.removeDialogOption(2);
                    this.imageEventText.removeDialogOption(3);
                    this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                    this.screen = CurScreen.END;
                    break;
                }
                break;
            case END:
                this.imageEventText.updateDialogOption(0,"离开");
                this.imageEventText.removeDialogOption(1);
                this.imageEventText.removeDialogOption(2);
                this.imageEventText.removeDialogOption(3);
                openMap();
                YibaMod.logger.info("整个事件结束。打开地图了。");
                break;
        }
    }
}