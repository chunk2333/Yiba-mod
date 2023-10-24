package events;
//猫党？狗党？
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import relics.Cat;
import relics.Dog;

public class CatOrDogEvent extends AbstractImageEvent {
    public static final String ID = "CatOrDogEvent";

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("CatOrDogEvent");

    public static final String NAME = eventStrings.NAME;

    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;

    public static final String[] OPTIONS = eventStrings.OPTIONS;

    private static final String INTRO_MSG = DESCRIPTIONS[0];//进入事件的描述

    private static final String Chose_MSG = DESCRIPTIONS[1]; //选择狗或猫

    private static final String LEAVE_MSG = DESCRIPTIONS[2]; //离开

    private static final String Cat = OPTIONS[0]; //猫

    private static final String Dog = OPTIONS[1]; //狗

    private static final String Leave = OPTIONS[2]; //锁定

    private CatOrDogEvent.CurScreen screen = CatOrDogEvent.CurScreen.INTRO;

    private enum CurScreen {
        INTRO, END
    }

    public CatOrDogEvent() {
        super(NAME, INTRO_MSG, "img/events/CatOrDogEvent.png");
        this.imageEventText.setDialogOption(Cat);
        this.imageEventText.setDialogOption(Dog);
        this.imageEventText.setDialogOption(Leave);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                if (buttonPressed == 0) {
                    //选择猫
                    AbstractDungeon.player.loseGold(AbstractDungeon.player.gold);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), new Cat());
                    this.imageEventText.clearRemainingOptions();
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption("离开");
                    this.imageEventText.updateDialogOption(0, "离开");
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.updateBodyText(Chose_MSG);
                    this.screen = CatOrDogEvent.CurScreen.END;
                    break;
                }
                if (buttonPressed == 1) {
                    //选择狗
                    AbstractDungeon.player.loseGold(AbstractDungeon.player.gold);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), new Dog());
                    this.imageEventText.clearRemainingOptions();
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption("离开");
                    this.imageEventText.updateDialogOption(0, "离开");
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.updateBodyText(Chose_MSG);
                    this.screen = CatOrDogEvent.CurScreen.END;
                    break;
                }
                if (buttonPressed == 2){
                    this.imageEventText.clearRemainingOptions();
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption("离开");
                    this.imageEventText.updateDialogOption(0, "离开");
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.removeDialogOption(2);
                    this.imageEventText.updateBodyText(LEAVE_MSG);
                    this.screen = CatOrDogEvent.CurScreen.END;
                    break;
                }
            case END:
                this.imageEventText.updateDialogOption(0, "离开");
                this.imageEventText.removeDialogOption(1);
                this.imageEventText.removeDialogOption(2);
                openMap();
                break;
        }
    }
}
