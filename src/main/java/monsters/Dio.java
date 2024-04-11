package monsters;
//迪奥·布兰度
import Tools.YiBaHelper;
import YibaMod.YibaMod;
import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.combat.BloodShotEffect;
import power.TimeStop;
import com.badlogic.gdx.audio.Music;

public class Dio extends CustomMonster {
    public static final String ID = "Dio";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Dio");

    private final UIStrings GetIntoDioRoom = CardCrawlGame.languagePack.getUIString("GetIntoDioRoom");

    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    private static final String TheWorld_Power_Name = MOVES[0];

    public static final String[] DIALOG = monsterStrings.DIALOG;

    private static final String The_World = DIALOG[0];

    private static final String Round_Pass = DIALOG[1];

    private static final String Go_To_Die = DIALOG[2];

    private static final String FinalTurn = DIALOG[3];

    private static final String MuDa = DIALOG[4];

    private static final String BuffUp = DIALOG[5];

    public static int roundNum = 1 ;

    private final int bellowBlock;

    private final int forgeAmt;

    private boolean isHeal;

    private int TimeStopAmount;

    public Dio() {
        super(NAME, "Dio", 300, 0.0F, 0.0F, 270.0F, 400.0F, null, -50.0F, 0.0F);
        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(420);
            this.bellowBlock = 30;
            this.forgeAmt = 15;
        } else {
            setHp(400);
            this.bellowBlock = 25;
            this.forgeAmt = 12;
        }
        this.dialogX = -50.0F * Settings.scale;
        this.dialogY = 100.0F * Settings.scale;
        this.damage.add(new DamageInfo(this, 5));
        this.damage.add(new DamageInfo(this, 20));
        this.damage.add(new DamageInfo(this, 30));
        this.damage.add(new DamageInfo(this, 55));
        this.img = new Texture(Gdx.files.internal("img/monsters/Dio.png"));
        this.type = AbstractMonster.EnemyType.BOSS;
        this.isHeal = false;
        this.TimeStopAmount = 1;
    }

    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        YibaMod.logger.info("播放Dio_BGM");
        AbstractDungeon.getCurrRoom().playBgmInstantly("Dio_BGM");
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BarricadePower(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 7)));
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, GetIntoDioRoom.TEXT[0], 1.0F, 2.0F));
    }
    public void takeTurn() {
        roundNum = roundNum + 1;
        String talkText;
        switch (this.nextMove) {
            case 1:
                //给玩家时停debuff
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                //说话
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, The_World));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new TimeStop(AbstractDungeon.player, this.TimeStopAmount)));
                //时停声音
                int randomNum;
                randomNum = AbstractDungeon.monsterRng.random(1,2);
                if(randomNum == 1){
                    addToBot(new SFXAction(YiBaHelper.MakeSoundPath("Dio_The_World_Voice01")));
                }
                if(randomNum == 2){
                    addToBot(new SFXAction(YiBaHelper.MakeSoundPath("Dio_The_World_Voice02")));
                }
                getMove(999);
                break;
            case 2:
                //给予2虚弱
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
                //说话：木大...
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, MuDa));
                //获得30格挡
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.bellowBlock));
                //造成3*5
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BloodShotEffect(this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, 5), 0.25F));

                for (int i = 0; i < 5; i++)
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                            .get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
                //最后给予1层易伤
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 1, true), 1));
                //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true), 1));
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
                talkText = GameActionManager.turn + Round_Pass;
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, talkText));
                if(roundNum==9){
                    //获得格挡  第8回合
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.bellowBlock + 10));
                }
                getMove(999);
                break;
            case 5:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                //获得金属化
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, this.forgeAmt), this.forgeAmt));
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

            case 73:
                //愚蠢
                AbstractDungeon.actionManager.addToBottom(new SFXAction(YiBaHelper.MakeSoundPath("Dio_wryyyy")));
                AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(this));
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this, this, "Shackled"));
                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, this.maxHealth / 2 - this.currentHealth));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 30));
                TimeStopAmount += 1;
                getMove(999);
                break;

        }
    }
    protected void getMove(int num) {
        if (this.currentHealth <= 150 && !this.isHeal){
            this.isHeal = true;
            setMove((byte)73, Intent.BUFF);
            return;
        }
        int random;
        roundNum = GameActionManager.turn;
        if(roundNum>=12){
            random = AbstractDungeon.monsterRng.random(9,10); //怪物随机数
            roundNum = random;
        }
        if(roundNum==1){
            setMove(TheWorld_Power_Name, (byte)1, Intent.STRONG_DEBUFF);
        }
        if(roundNum==2){
            setMove((byte)2, Intent.ATTACK_DEFEND, this.damage.get(0).base, 5, true);
        }
        if(roundNum==3){
            //25%强化  75%打人

            random = AbstractDungeon.monsterRng.random(1,6); //怪物随机数
            if(random==1){
                //强化
                setMove((byte)123, Intent.BUFF);
            }
            if(random>1){
                //攻击
                setMove((byte)3, Intent.ATTACK,this.damage.get(1).base);
            }
        }
        if(roundNum==4){
            //强化或者打人
            random = AbstractDungeon.monsterRng.random(1,4); //怪物随机数
            if(random == 1){
                //强化
                setMove((byte)123, Intent.BUFF);
            }
            if(random > 2){
                //攻击
                setMove((byte)3, Intent.ATTACK,this.damage.get(1).base);
            }
        }
        if(roundNum==5){
            //时停
            setMove(TheWorld_Power_Name, (byte)1, Intent.DEBUFF);
        }
        if(roundNum==6){
            setMove((byte)4, Intent.BUFF);
        }
        if(roundNum==7){
            setMove((byte)5, Intent.ATTACK_BUFF,this.damage.get(2).base);
        }
        if(roundNum==8){
            setMove((byte)4, Intent.BUFF);
        }
        if(roundNum==9){
            //时停
            setMove(TheWorld_Power_Name, (byte)1, Intent.DEBUFF);
        }
        if(roundNum==10){
            setMove((byte)4, Intent.BUFF);
        }
        if(roundNum==11){
            setMove((byte)9, Intent.ATTACK,this.damage.get(3).base);
        }
    }
    public void die() {
        useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        super.die();
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, GetIntoDioRoom.TEXT[1], 1.0F, 2.0F));


        if (MathUtils.randomBoolean()) {
            CardCrawlGame.sound.play("VO_CHAMP_3A");
        } else {
            CardCrawlGame.sound.play("VO_CHAMP_3B");
        }
        AbstractDungeon.scene.fadeInAmbiance();
        onBossVictoryLogic();
    }
}