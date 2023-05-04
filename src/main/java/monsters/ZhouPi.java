package monsters;

//ZhouPi


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
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.core.Settings;

public class ZhouPi extends CustomMonster {
    public static final String ID = "ZhouPi";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("ZhouPi");
    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    public static final String[] DIALOG = monsterStrings.DIALOG;

    private boolean firstMove = true;

    private boolean secondMove = false;

    private boolean thirdMove = false;

    public ZhouPi(float x, float y) {
        super(NAME, "ZhouPi", 40, -8.0F, 10.0F, 230.0F, 240.0F, null, x, y);
        setHp(80);
        this.dialogX = -50.0F * Settings.scale;
        this.dialogY = 100.0F * Settings.scale;
        this.damage.add(new DamageInfo(this, 6));
        loadAnimation("images/monsters/theBottom/cultist/skeleton.atlas", "images/monsters/theBottom/cultist/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "waving", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.damage.add(new DamageInfo(this, 290));

    }
    public void usePreBattleAction() {
        //玩家即将进入战斗
        //添加10坚不可摧
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new InvinciblePower(this, 25), 25));

    }
    public void takeTurn() {
        switch (this.nextMove) {
            case 4:
                //第一回合：上debuff
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                //塞两张孝子牌到抽卡堆
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new DutifulSon(), 2,true,true));
                 //随机下一次行动
                this.getMove(99);
                break;
            case 3:
                //第2回合：降低你3点力量与敏捷，并出现：装死怎么了？yj就是我爹！
                //给自己上buff
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                //说话：装死怎么了？yj就是我爹！
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0]));
                //降低力量
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new StrengthPower(AbstractDungeon.player,-3), -3));
                //降低敏捷
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new DexterityPower(AbstractDungeon.player,-3), -3));
                //下1回合
                this.getMove(99);
                break;
            case 5:
                //第3回合 给予自身10力量
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 10), 10));
                this.getMove(99);
                break;
            case 1:
                //第4回合及往后：纯粹攻击
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                //说话：不就三百抽保底还不继承吗？
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[1]));
                //造成伤害
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
                        .get(1), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                //随机行动
                this.getMove(99);
                break;
        }
    }
    protected void getMove(int num) {
        if (this.firstMove) {
            this.firstMove = false;
            setMove((byte)4, Intent.DEBUFF);
            secondMove = true;
            return;
        }
        if (this.secondMove){
            setMove((byte)3, Intent.DEBUFF);
            secondMove = false;
            thirdMove = true;
            return;
        }
        if(thirdMove){
            thirdMove = false;
            setMove((byte)5, Intent.BUFF);

            return;
        }
        setMove((byte)1,Intent.ATTACK,this.damage.get(1).base);
    }
}

