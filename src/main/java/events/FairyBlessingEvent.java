package events;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import YibaMod.YibaMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import relics.FairyBlessing;


public class FairyBlessingEvent extends AbstractImageEvent {
    public static final String ID = "FairyBlessingEvent";

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("FairyBlessingEvent");

    public static final String NAME = eventStrings.NAME;

    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;

    public static final String[] OPTIONS = eventStrings.OPTIONS;

    private static final String INTRO_MSG = DESCRIPTIONS[0];//进入事件的描述

    private static final String LEAVE_MSG = DESCRIPTIONS[2]; //离开

    private static final String GET_MSG = DESCRIPTIONS[1]; //拔开瓶塞后的描述

    private static final String Get = OPTIONS[0]; //拔开瓶塞

    private static final String leave = OPTIONS[1]; //离开

    private static final String Locked = OPTIONS[2]; //锁定
    private AbstractPotion potionOption;


    private FairyBlessingEvent.CurScreen screen = FairyBlessingEvent.CurScreen.INTRO;

    private enum CurScreen {
        INTRO, END
    }

    public FairyBlessingEvent() {
        super(NAME, INTRO_MSG, "img/events/FairyBlessingEvent.png");
        //this.noCardsInRewards = true;
        YibaMod.logger.info("进入事件：FairyBlessingEvent");
        this.imageEventText.clearRemainingOptions();
        for (AbstractPotion potion : AbstractDungeon.player.potions) {
            if (potion.ID.equals("FairyPotion")) {
                this.potionOption = potion;
            }
        }
        if (AbstractDungeon.player.hasPotion("FairyPotion")) {
            this.imageEventText.setDialogOption(Get);
        } else {
            //[锁定] 瓶中精灵
            this.imageEventText.setDialogOption(Locked, true);
        }
        this.imageEventText.setDialogOption(leave);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                if (buttonPressed == 0) {
                    //拔开瓶塞
                    AbstractDungeon.player.removePotion(this.potionOption);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), new FairyBlessing());
                    this.imageEventText.clearRemainingOptions();
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption("离开");
                    this.imageEventText.updateDialogOption(0, "离开");
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.updateBodyText(GET_MSG);
                    this.screen = CurScreen.END;
                    break;
                }
                if (buttonPressed == 1) {
                    //离开
                    this.imageEventText.clearRemainingOptions();
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption("离开");
                    this.imageEventText.updateDialogOption(0, "离开");
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.updateBodyText(LEAVE_MSG);
                    this.screen = CurScreen.END;
                    break;
                }
            case END:
                this.imageEventText.updateDialogOption(0, "离开");
                this.imageEventText.removeDialogOption(1);
                openMap();
                YibaMod.logger.info("整个事件结束。打开地图了。");
                break;
        }
    }
}