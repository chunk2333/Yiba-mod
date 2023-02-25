package monsters;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.unique.CannotLoseAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.SlimeAnimListener;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_M;
import com.megacrit.cardcrawl.powers.*;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import cards.*;

public class YuanPi extends CustomMonster {
    public static final String ID = "YuanPi";

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("YuanPi");

    public static final String NAME = monsterStrings.NAME;

    public static final String[] MOVES = monsterStrings.MOVES;

    private static final String WOUND_NAME = MOVES[0];

    private static final String SPLIT_NAME = MOVES[1];

    private static final String WEAK_NAME = MOVES[2];

    private boolean firstMove = true;
    private boolean secondMove = false;

    public YuanPi(float x, float y) {
        super(NAME, "YuanPi", 600, -8.0F, 10.0F, 230.0F, 240.0F, null, x, y);
        //"img/cards_Seles/desire_p.png"
        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(80, 86);
        } else {
            setHp(70, 76);
        }
        this.dialogX = -50.0F * Settings.scale;
        this.dialogY = 100.0F * Settings.scale;
        this.damage.add(new DamageInfo((AbstractCreature)this, 6));
        loadAnimation("images/monsters/theBottom/cultist/skeleton.atlas", "images/monsters/theBottom/cultist/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "waving", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }
    public void takeTurn() {
        switch (this.nextMove) {
            case 4:
                //上debuff
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                //塞两张孝子牌到抽卡堆
                //MakeTempCardInDrawPileAction
                //MakeTempCardInDiscardAction

                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new DutifulSon(), 2,true,true));
                //上两层易伤
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)this, (AbstractPower)new VulnerablePower((AbstractCreature)AbstractDungeon.player, 2, true), 2));
                //随机下一次行动
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;
            case 1:
                //纯粹攻击
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new AnimateSlowAttackAction((AbstractCreature)this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage
                        .get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;
            case 3:
                //给自己上buff
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction((AbstractCreature)this, (AbstractCreature)this, (AbstractPower)new StrengthPower((AbstractCreature)this, 10), 10));
                setMove(WOUND_NAME,(byte)1,Intent.ATTACK,this.damage.get(0).base);
                break;
        }
    }
    protected void getMove(int num) {
        if (this.firstMove) {
            this.firstMove = false;
            setMove(WOUND_NAME, (byte)4, Intent.DEBUFF);
            secondMove = true;
            return;
        }
        if (this.secondMove){
            setMove(WEAK_NAME, (byte)3, Intent.BUFF);
            secondMove = false;
            return;
        }
        //setMove((byte)1, Intent.ATTACK, this.damage.get(0).base);
        setMove(WOUND_NAME,(byte)1,Intent.ATTACK,this.damage.get(0).base);
    }
}
