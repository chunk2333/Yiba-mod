package monsters;

//public class Dio {


import basemod.abstracts.CustomMonster;
import cards.curse.DutifulSon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.BloodShotEffect;
import power.TimeStop;

public class Dio extends CustomMonster {
    public static final String ID = "Dio";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Dio");

    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    private static final String TheWorld_Power_Name = MOVES[0];

    public static final String[] DIALOG = monsterStrings.DIALOG;

    private static final String SPLIT_NAME = MOVES[1];

    private static final String WEAK_NAME = MOVES[2];

    private static final String The_World = DIALOG[0];

    private static final String Round_Pass = DIALOG[1];

    private static final String Go_To_Die = DIALOG[2];

    private static final String FinalTurn = DIALOG[3];

    private static final String MuDa = DIALOG[4];

    private static final String BuffUp = DIALOG[5];

    private int roundNum = 1 ;

    public Dio() {
        //super(NAME, "Dio", 600, -8.0F, 10.0F, 230.0F, 240.0F, null, x, y);
        super(NAME, "Dio", 300, 0.0F, 0.0F, 270.0F, 400.0F, null, -50.0F, 0.0F);
        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(420);
        } else {
            setHp(400);
        }
        this.dialogX = -50.0F * Settings.scale;
        this.dialogY = 100.0F * Settings.scale;
        this.damage.add(new DamageInfo(this, 5));
        this.damage.add(new DamageInfo(this, 20));
        this.damage.add(new DamageInfo(this, 30));
        this.damage.add(new DamageInfo(this, 999));
        this.img = new Texture(Gdx.files.internal("img/monsters/Dio.png"));
        //DungeonMap.boss = ImageMaster.loadImage("img/Ui_Seles/map/boss/Dio.png");
        //DungeonMap.bossOutline = ImageMaster.loadImage("img/Ui_Seles/map/bossOutline/Dio.png");
    }
    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_CITY");
    }
    public void takeTurn() {
        this.roundNum = this.roundNum + 1;
        String talkText;
        switch (this.nextMove) {
            case 1:
                //给玩家时停debuff
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                //说话
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, The_World));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new TimeStop(AbstractDungeon.player)));
                //行动
                getMove(999);
                break;

            case 2:
                //给予2虚弱
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
                //说话：木大...
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, MuDa));
                //造成3*5
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BloodShotEffect(this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, 5), 0.25F));

                for (int i = 0; i < 5; i++)
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                            .get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
                getMove(999);
                break;
            case 3:

                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                //说话：西内！xxx
                talkText = Go_To_Die + AbstractDungeon.player.name;
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, talkText));
                //造成伤害
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                        .get(1), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                getMove(999);
                break;
            case 4:
                //说话：xx回合过去了
                talkText = this.roundNum + Round_Pass;
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, talkText));
                getMove(999);
                break;
            case 5:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                        .get(2), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                getMove(999);
                break;
            case 123:
                //强化，自身获得5力量
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 5), 5));
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, BuffUp));
                getMove(999);
                break;
            case 9:
                //第11回合的究极攻击
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, FinalTurn));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                        .get(3), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                getMove(999);
                break;

            default:
                getMove(999);
                break;
        }
    }
    protected void getMove(int num) {
        int random;
        if(this.roundNum>=12){
            random = AbstractDungeon.monsterRng.random(9,10); //怪物随机数
            this.roundNum = random;
        }
        if(this.roundNum==1){
            setMove(TheWorld_Power_Name, (byte)1, Intent.DEBUFF);
        }
        if(this.roundNum==2){
            setMove((byte)2, Intent.ATTACK_DEBUFF, this.damage.get(0).base, 5, true);
        }
        if(this.roundNum==3){
            //强化或者打人

            random = AbstractDungeon.monsterRng.random(1,2); //怪物随机数
            if(random==1){
                //强化
                setMove((byte)123, Intent.BUFF);
            }
            if(random==2){
                //攻击
                setMove((byte)3, Intent.ATTACK,this.damage.get(1).base);
            }
        }
        if(this.roundNum==4){
            //强化或者打人
            random = AbstractDungeon.monsterRng.random(1,2); //怪物随机数
            if(random==1){
                //强化
                setMove((byte)123, Intent.BUFF);
            }
            if(random==2){
                //攻击
                setMove((byte)3, Intent.ATTACK,this.damage.get(1).base);
            }
        }
        if(this.roundNum==5){
            //时停
            setMove(TheWorld_Power_Name, (byte)1, Intent.DEBUFF);
        }
        if(this.roundNum==6){
            setMove((byte)4, Intent.BUFF);
        }
        if(this.roundNum==7){
            setMove((byte)5, Intent.ATTACK,this.damage.get(2).base);
        }
        if(this.roundNum==8){
            setMove((byte)4, Intent.BUFF);
        }
        if(this.roundNum==9){
            //时停
            setMove(TheWorld_Power_Name, (byte)1, Intent.DEBUFF);
        }
        if(this.roundNum==10){
            setMove((byte)4, Intent.BUFF);
        }
        if(this.roundNum==11){
            setMove((byte)9, Intent.ATTACK,this.damage.get(3).base);
        }
    }
    public void die() {
        useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        super.die();
        if (MathUtils.randomBoolean()) {
            CardCrawlGame.sound.play("VO_CHAMP_3A");
        } else {
            CardCrawlGame.sound.play("VO_CHAMP_3B");
        }
        AbstractDungeon.scene.fadeInAmbiance();
        onBossVictoryLogic();
    }

}