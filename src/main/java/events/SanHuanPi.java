package events;

//public class SanHuanPi {

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import YibaMod.YibaMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.FairyPotion;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import potions.reborn;

import java.util.ArrayList;

public class SanHuanPi extends AbstractImageEvent {
    public static final String ID = "SanHuanPi";

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("SanHuanPi");

    public static final String NAME = eventStrings.NAME;

    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;

    public static final String[] OPTIONS = eventStrings.OPTIONS;

    private static final String INTRO_MSG = DESCRIPTIONS[0];//进入事件的描述

    private static final String LEAVE_MSG = DESCRIPTIONS[1]; //离开

    private static final String FRIGHT_END_MSG = DESCRIPTIONS[2]; //离开

    private static final String FRIGHT = OPTIONS[0]; //战斗

    private static final String LEAVE = OPTIONS[1]; //离开

    private SanHuanPi.CurScreen screen = SanHuanPi.CurScreen.INTRO;
    Random random = new Random();
    private enum CurScreen {
        INTRO, END, END_FIGHT;
    }
    public SanHuanPi() {
        super(NAME, INTRO_MSG, "img/events/SanHuanPi.png");
        //this.noCardsInRewards = true;
        YibaMod.logger.info("进入事件：三幻批");
        this.imageEventText.clearRemainingOptions();
        this.imageEventText.setDialogOption(FRIGHT);
        this.imageEventText.setDialogOption(LEAVE);



    }
    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                if (buttonPressed == 0){
                    //TODO:进入战斗
                    YibaMod.logger.info("选择进入战斗");
                    //设置怪物
                    (AbstractDungeon.getCurrRoom()).rewardAllowed = false;
                    (AbstractDungeon.getCurrRoom()).monsters = MonsterHelper.getEncounter("SanHuanPi");


                    this.screen = CurScreen.END_FIGHT;
                    //enterCombat();
                    //开始进入战斗
                    this.enterCombatFromImage();
                    //this.enterImageFromCombat();
                    AbstractDungeon.lastCombatMetricKey = "SanHuanPi";
                    break;
                }
                if (buttonPressed == 1){
                    //TODO：离开
                    YibaMod.logger.info("选择离开");
                    //清除事件所有描述文本
                    this.imageEventText.clearAllDialogs();
                    //清除事件所有选项
                    this.imageEventText.clearRemainingOptions();
                    //设置事件描述文本
                    this.imageEventText.updateBodyText(LEAVE_MSG);
                    this.screen = CurScreen.END;
                    this.imageEventText.setDialogOption("离开");
                    this.imageEventText.updateDialogOption(0,"离开");
                    this.imageEventText.removeDialogOption(1);
                    break;
                }
                break;
            case END_FIGHT:
                (AbstractDungeon.getCurrRoom()).rewardAllowed = true;

                YibaMod.logger.info("拾取奖励");

                //添加奖励：金币
                AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(100, 300));
                //添加奖励：药水-起死回骸
                AbstractDungeon.getCurrRoom().addPotionToRewards(new reborn());
                //添加奖励：药水-精灵药
                AbstractDungeon.getCurrRoom().addPotionToRewards(new FairyPotion());
                //添加奖励：遗物-户口本
                AbstractDungeon.getCurrRoom().addRelicToRewards(RelicLibrary.getRelic("HouseholdRegister").makeCopy());
                //添加奖励：遗物-稀有
                AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.RARE);
                //添加奖励：遗物-罕见
                AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.UNCOMMON);
                //添加奖励：遗物-普通
                AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.COMMON);
                //初始化卡片奖励
                RewardItem cardReward = new RewardItem();
                //卡片奖励清空。
                cardReward.cards.clear();
                //添加金卡
                AbstractPlayer player = AbstractDungeon.player;

                int numCards = 3;
                for (AbstractRelic r : player.relics)
                    numCards = r.changeNumberOfCardsInReward(numCards);
                if (ModHelper.isModEnabled("Binary"))
                    numCards--;
                YibaMod.logger.info("掉落卡牌数：" + numCards);
                for (int i = 0; i < numCards; i++) {
                    cardReward.cards.add(AbstractDungeon.getCard(AbstractCard.CardRarity.RARE,random));
                }

                AbstractDungeon.getCurrRoom().addCardReward(cardReward);
                //AbstractDungeon.getCurrRoom().addCardToRewards();
                //置房间为完成
                (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
                //打开奖励界面
                AbstractDungeon.combatRewardScreen.open();
                this.screen = CurScreen.END;
                this.imageEventText.updateDialogOption(0,"离开");
                break;
            case END:
                this.imageEventText.updateDialogOption(0,"离开");
                openMap();
                YibaMod.logger.info("整个事件结束。打开地图了。");
                break;
        }
    }
    @Override
    public void reopen() {
        if (this.screen != CurScreen.END) {
            AbstractDungeon.resetPlayer();
            AbstractDungeon.player.drawX = Settings.WIDTH * 0.25F;
            AbstractDungeon.player.preBattlePrep();
            enterImageFromCombat();
            YibaMod.logger.info("战斗结束，进入拾取奖励界面");
            this.imageEventText.clearAllDialogs();
            //清除事件所有选项
            this.imageEventText.clearRemainingOptions();
            //设置事件描述文本
            this.imageEventText.updateBodyText(FRIGHT_END_MSG);
            this.screen = CurScreen.END_FIGHT;
            this.imageEventText.setDialogOption("离开");
            this.imageEventText.updateDialogOption(0,"离开");
            this.imageEventText.removeDialogOption(1);
            this.imageEventText.update();
            buttonEffect(10086);
        }
    }
}