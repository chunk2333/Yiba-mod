package monsters;

import basemod.abstracts.CustomMonster;
import cards.curse.DutifulSon;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.core.Settings;

public class NongPi extends CustomMonster {
    public static final String ID = "NongPi";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("NongPi");
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public static final String[] DIALOG = monsterStrings.DIALOG;

    private boolean firstMove = true;

    public int stolenGold = 0;

    public int goldAmt;


    public NongPi(float x, float y) {
        super(NAME, ID, 100, -8.0F, 10.0F, 230.0F, 240.0F, null, x, y);
        setHp(100);
        this.dialogX = -50.0F * Settings.scale;
        this.dialogY = 100.0F * Settings.scale;
        this.damage.add(new DamageInfo(this, 6));
        loadAnimation("images/monsters/theBottom/cultist/skeleton.atlas", "images/monsters/theBottom/cultist/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "waving", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.damage.add(new DamageInfo(this, 11));
        this.goldAmt = 20;

    }
    public void usePreBattleAction() {
        //玩家即将进入战斗
        //添加偷钱buff
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThieveryPower(this, this.goldAmt)));

    }
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                //第一回合：上debuff
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                //塞两张孝子牌到抽卡堆
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new DutifulSon(), 2,true,true));
                //随机下一次行动
                this.getMove(99);
                break;
            case 1:
                //第2回合及往后：纯粹攻击
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));


                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                    public void update() {
                        stolenGold = stolenGold + Math.min(goldAmt, AbstractDungeon.player.gold);
                        this.isDone = true;
                    }
                });
                //造成伤害

                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                        .get(1), goldAmt));



                //随机行动
                this.getMove(99);
                break;
        }
    }
    protected void getMove(int num) {
        if (this.firstMove) {
            this.firstMove = false;
            setMove((byte) 0, Intent.DEBUFF);
            return;
        }
        setMove((byte)1,Intent.ATTACK,this.damage.get(1).base);
    }
    public void die() {
        if (this.stolenGold > 0)
            AbstractDungeon.getCurrRoom().addStolenGoldToRewards(this.stolenGold);
        super.die();
    }
}

