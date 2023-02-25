package events;

//public class Restaurant {

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;

public class RestaurantTest extends AbstractImageEvent {
    public static final String ID = "RestaurantTest";

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("RestaurantTest");

    public static final String NAME = eventStrings.NAME;

    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;

    public static final String[] OPTIONS = eventStrings.OPTIONS;

    private static final String INTRO_MSG = DESCRIPTIONS[0];//进入事件的描述

    private static final String READ_1 = DESCRIPTIONS[1]; //喝下后的描述

    private static final String READ_2 = DESCRIPTIONS[2]; //喝下后的剧情，开始吃炸酱面

    private static final String READ_3 = DESCRIPTIONS[3]; //吃下炸酱面的剧情

    private static final String READ_4 = DESCRIPTIONS[4]; //评价

    private static final String OBTAIN_MSG = DESCRIPTIONS[5]; //好评

    private static final String IGNORE_MSG = DESCRIPTIONS[6];  //不予置评

    private static final String STOP_MSG = DESCRIPTIONS[7];

    private static final String OPT_READ = OPTIONS[0]; //喝下

    private static final String OPT_CONTINUE_1 = OPTIONS[1]; //离开-无视发生

    private static final String OPT_CONTINUE_2 = OPTIONS[2]; //吃下炸酱面

    private static final String OPT_CONTINUE_3 = OPTIONS[3]; //不吃面离开，被诅咒

    private static final String OPT_STOP = OPTIONS[4]; //好评

    private static final String OPT_LEAVE = OPTIONS[7];  //不予置评

    private static final int DMG_BOOK_OPEN = 1;

    private static final int DMG_SECOND_PAGE = 2;

    private static final int DMG_THIRD_PAGE = 3;

    private static final int DMG_STOP_READING = 3;

    private static final int DMG_OBTAIN_BOOK = 10;

    private static final int A_2_DMG_OBTAIN_BOOK = 15;

    private int finalDmg;

    private int damageTaken;

    private CurScreen screen = CurScreen.INTRO;

    private enum CurScreen {
        INTRO, PAGE_1, PAGE_2, PAGE_3, LAST_PAGE, END;
    }

    public RestaurantTest() {
        super(NAME, INTRO_MSG, "images/events/cursedTome.jpg");
        this.noCardsInRewards = true;
        this.damageTaken = 0;
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.finalDmg = 15;
        } else {
            this.finalDmg = 10;
        }
        this.imageEventText.setDialogOption(OPT_READ);
        this.imageEventText.setDialogOption(OPT_LEAVE);
    }
    //按下按钮
    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                this.imageEventText.clearAllDialogs();
                if (buttonPressed == 0) {
                    CardCrawlGame.sound.play("EVENT_TOME");
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption(OPT_CONTINUE_1);
                    this.imageEventText.updateBodyText(READ_1);
                    this.screen = CurScreen.PAGE_1;
                    break;
                }
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPT_LEAVE);
                this.imageEventText.updateBodyText(IGNORE_MSG);
                this.screen = CurScreen.END;
                logMetricIgnored("Cursed Tome");
                break;
            case PAGE_1:
                CardCrawlGame.sound.play("EVENT_TOME");
                AbstractDungeon.player.damage(new DamageInfo(null, 1, DamageInfo.DamageType.HP_LOSS));
                this.damageTaken++;
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPT_CONTINUE_2);
                this.imageEventText.updateBodyText(READ_2);
                this.screen = CurScreen.PAGE_2;
                break;
            case PAGE_2:
                CardCrawlGame.sound.play("EVENT_TOME");
                AbstractDungeon.player.damage(new DamageInfo(null, 2, DamageInfo.DamageType.HP_LOSS));
                this.damageTaken += 2;
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPT_CONTINUE_3);
                this.imageEventText.updateBodyText(READ_3);
                this.screen = CurScreen.PAGE_3;
                break;
            case PAGE_3:
                CardCrawlGame.sound.play("EVENT_TOME");
                AbstractDungeon.player.damage(new DamageInfo(null, 3, DamageInfo.DamageType.HP_LOSS));
                this.damageTaken += 3;
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[5] + this.finalDmg + OPTIONS[6]);
                this.imageEventText.setDialogOption(OPT_STOP);
                this.imageEventText.updateBodyText(READ_4);
                this.screen = CurScreen.LAST_PAGE;
                break;
            case LAST_PAGE:
                if (buttonPressed == 0) {
                    AbstractDungeon.player.damage(new DamageInfo(null, this.finalDmg, DamageInfo.DamageType.HP_LOSS));
                    this.damageTaken += this.finalDmg;
                    this.imageEventText.updateBodyText(OBTAIN_MSG);
                    randomBook();
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption(OPT_LEAVE);
                    break;
                }
                AbstractDungeon.player.damage(new DamageInfo(null, 3, DamageInfo.DamageType.HP_LOSS));
                this.damageTaken += 3;
                this.imageEventText.updateBodyText(STOP_MSG);
                logMetricTakeDamage("Cursed Tome", "Stopped", this.damageTaken);
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPT_LEAVE);
                this.screen = CurScreen.END;
                break;
            case END:
                this.imageEventText.updateDialogOption(0, OPT_LEAVE);
                this.imageEventText.clearRemainingOptions();
                openMap();
                break;
        }
    }

    private void randomBook() {
        ArrayList<AbstractRelic> possibleBooks = new ArrayList<>();
        if (!AbstractDungeon.player.hasRelic("Necronomicon"))
            possibleBooks.add(RelicLibrary.getRelic("Necronomicon").makeCopy());
        if (!AbstractDungeon.player.hasRelic("Enchiridion"))
            possibleBooks.add(RelicLibrary.getRelic("Enchiridion").makeCopy());
        if (!AbstractDungeon.player.hasRelic("Nilry's Codex"))
            possibleBooks.add(RelicLibrary.getRelic("Nilry's Codex").makeCopy());
        if (possibleBooks.size() == 0)
            possibleBooks.add(RelicLibrary.getRelic("Circlet").makeCopy());
        AbstractRelic r = possibleBooks.get(AbstractDungeon.miscRng.random(possibleBooks.size() - 1));
        logMetricTakeDamage("Cursed Tome", "Obtained Book", this.damageTaken);
        (AbstractDungeon.getCurrRoom()).rewards.clear();
        AbstractDungeon.getCurrRoom().addRelicToRewards(r);
        (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
        AbstractDungeon.combatRewardScreen.open();
        this.screen = CurScreen.END;
    }
}

