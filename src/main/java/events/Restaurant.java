package events;

import basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen.UpdateStats;
import cards.curse.desire;
import cards.curse.snowman;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class Restaurant extends AbstractImageEvent {
    public static final String ID = "Restaurant";

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("Restaurant");

    public static final String NAME = eventStrings.NAME;

    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;

    public static final String[] OPTIONS = eventStrings.OPTIONS;

    private static final String INTRO_MSG = DESCRIPTIONS[0];//进入事件的描述

    private static final String Drink_MSG = DESCRIPTIONS[1]; //喝下后的描述

    private static final String Eat_MSG = DESCRIPTIONS[2]; //喝下后的剧情，开始吃炸酱面

    private static final String EatAfter_MSG = DESCRIPTIONS[3]; //吃下炸酱面的剧情

    private static final String Evaluate_MSG = DESCRIPTIONS[4]; //评价

    private static final String Evaluate_GOOD_MSG = DESCRIPTIONS[5]; //好评

    private static final String Evaluate_NO_MSG = DESCRIPTIONS[6];  //不予置评

    private static final String LEAVE = DESCRIPTIONS[7];  //开始离开的剧情描述

    private static final String DRINK = OPTIONS[0]; //喝下

    private static final String LEAVE_NO_DRINK = OPTIONS[1]; //离开-无视发生

    private static final String EAT= OPTIONS[2]; //吃下炸酱面

    private static final String LEAVE_NO_EAT = OPTIONS[3]; //不吃面离开，被诅咒

    private static final String Evaluate_GOOD = OPTIONS[4]; //好评

    private static final String Evaluate_NO = OPTIONS[5];  //不予置评

    private int finalDmg;

    private int damageTaken;

    private CurScreen screen = CurScreen.INTRO;
    boolean isIn;
    private enum CurScreen {
        INTRO, PAGE_1, PAGE_2, PAGE_3, LAST_PAGE, END, HeJiu;
    }
    public Restaurant() {
        super(NAME, INTRO_MSG, "img/events/Restaurant.png");
        this.noCardsInRewards = true;
        //初次进入
        UpdateStats.logger.info("进入事件：餐厅");
        //进入事件
        this.imageEventText.setDialogOption("[进入] #r快点端上来罢 ！");
        //离开事件
        this.imageEventText.setDialogOption(LEAVE_NO_DRINK);
        UpdateStats.logger.info("设置开场白");

    }
    public void leave(){
        this.imageEventText.clearRemainingOptions();
        this.imageEventText.setDialogOption("离开");
        this.imageEventText.updateDialogOption(0,"离开");
        this.imageEventText.removeDialogOption(1);
        //打开地图
        openMap();
        UpdateStats.logger.info("整个事件结束。打开地图了。");
    }
    //按下按钮
    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                //一开始进入事件触发的
                if(isIn){
                    //buttonPressed=-1;
                    isIn=false;
                    break;
                }
                if (buttonPressed == 0) {
                    UpdateStats.logger.info("选择端上来");
                    this.imageEventText.clearAllDialogs();
                    UpdateStats.logger.info("设置喝酒的信息");
                    this.imageEventText.updateBodyText("“咳，先生，这是我们的 #y迎宾酒 ” NL 你看着面前这杯 #y@泛黄浮着泡沫的酒@ ，你的选择是？");
                    this.imageEventText.clearRemainingOptions();
                    this.imageEventText.setDialogOption(DRINK);
                    this.imageEventText.updateDialogOption(0,DRINK);
                    this.imageEventText.setDialogOption(LEAVE_NO_DRINK);
                    this.imageEventText.updateDialogOption(1,LEAVE_NO_DRINK);
                    UpdateStats.logger.info("准备进入HeJiu");
                    this.screen=CurScreen.HeJiu;
                    break;
                }else {
                    //离开事件
                    UpdateStats.logger.info("选择离开");
                    this.imageEventText.updateBodyText("你无视了眼前的景象");
                    this.screen = CurScreen.END;
                    leave();
                    break;
                }
            case HeJiu:
                if(isIn){
                    //buttonPressed=-1;
                    isIn=false;
                    break;
                }
                this.imageEventText.clearAllDialogs();
                if (buttonPressed == 0) {
                    //喝下酒
                    UpdateStats.logger.info("选择喝下酒");
                    CardCrawlGame.sound.play("EVENT_TOME");
                    this.imageEventText.clearAllDialogs();
                    //设置喝下选项
                    //更新喝下后的文本
                    this.imageEventText.updateBodyText(Drink_MSG);
                    //给予玩家10点伤害
                    UpdateStats.logger.info("喝下酒：受到 10 点伤害");
                    AbstractDungeon.player.damage(new DamageInfo(null, 10, DamageInfo.DamageType.HP_LOSS));
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption("继续");
                    isIn=true;
                    UpdateStats.logger.info("设置接下来为：PAGE_1");
                    this.screen = Restaurant.CurScreen.PAGE_1;
                    break;
                }
            case PAGE_1:
                //喝下之后触发的
                UpdateStats.logger.info("进入PAGE_1:喝下酒后");
                //CardCrawlGame.sound.play("EVENT_TOME");
                this.imageEventText.clearAllDialogs();
                this.imageEventText.clearRemainingOptions();
                //this.imageEventText.removeDialogOption(1);
                //吃下选项
                this.imageEventText.setDialogOption(EAT);
                //离开选项（被诅咒-三大欲望）
                this.imageEventText.setDialogOption(LEAVE_NO_EAT);
                this.imageEventText.updateBodyText(Eat_MSG);
                if(isIn){
                    //buttonPressed=-1;
                    isIn=false;
                    break;
                }
                if(buttonPressed==0){
                    isIn=true;
                    //继续，前往第二页，触发吃下的剧情
                    UpdateStats.logger.info("选择吃下");
                    //更新文本-准备吃面
                    CardCrawlGame.sound.play("EVENT_TOME");
                    //受到15点伤害
                    UpdateStats.logger.info("吃下：受到15点伤害");
                    AbstractDungeon.player.damage(new DamageInfo(null, 15, DamageInfo.DamageType.HP_LOSS));
                    //this.damageTaken += 2;
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.clearRemainingOptions();
                    //吃完了，开始评价
                    //更新文本
                    this.imageEventText.updateBodyText(EatAfter_MSG);
                    //this.imageEventText.clearAllDialogs();
                    this.imageEventText.clearRemainingOptions();
                    this.imageEventText.setDialogOption("继续");

                    UpdateStats.logger.info("前往PAGE_3");
                    this.screen = Restaurant.CurScreen.PAGE_3;
                    break;
                }else {
                    isIn=true;
                    //更新文本提示。
                    UpdateStats.logger.info("[离开] #r被诅咒-食雪汉。");
                    this.imageEventText.updateBodyText("服务员看起来非常的#y~沮丧~。。。");
                    //清除选项
                    this.imageEventText.clearRemainingOptions();
                    //TODO:塞一张诅咒牌。
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new desire(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    //打开地图
                    this.screen = CurScreen.END;
                    this.imageEventText.clearRemainingOptions();
                    this.imageEventText.setDialogOption("离开");
                    this.imageEventText.updateDialogOption(0,"离开");
                    this.imageEventText.removeDialogOption(1);
                    //leave();
                    break;
                }
            case PAGE_3:
                //评价
                UpdateStats.logger.info("进入PAGE_3");
                this.imageEventText.clearAllDialogs();
                this.imageEventText.clearRemainingOptions();
                this.imageEventText.setDialogOption(Evaluate_GOOD);
                this.imageEventText.setDialogOption(Evaluate_NO);
                this.imageEventText.updateBodyText(Evaluate_MSG);
                if(isIn){
                    //buttonPressed=-1;
                    isIn=false;
                    break;
                }
                isIn=true;
                if(buttonPressed==0) {
                    //好评
                    UpdateStats.logger.info("好评");
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.updateBodyText(Evaluate_GOOD_MSG);
                    //给牛排
                    AbstractRelic r = (RelicLibrary.getRelic("beef").makeCopy());
                    (AbstractDungeon.getCurrRoom()).rewards.clear();
                    AbstractDungeon.getCurrRoom().addRelicToRewards(r);
                    (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
                    AbstractDungeon.combatRewardScreen.open();
                    this.screen = CurScreen.END;


                }else {
                    //不予置评
                    UpdateStats.logger.info("不予置评");
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.updateBodyText(Evaluate_NO_MSG);
                    //被诅咒-三大欲望
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new snowman(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    this.screen = CurScreen.END;
                }
                //leave();
                this.imageEventText.clearRemainingOptions();
                this.imageEventText.setDialogOption("离开");
                this.imageEventText.updateDialogOption(0,"离开");
                this.imageEventText.removeDialogOption(1);
                break;
            case END:
                leave();
                break;
        }
    }
}
