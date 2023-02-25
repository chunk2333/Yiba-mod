//BloodiedWarhammer

package relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BloodiedWarhammer extends CustomRelic {
    public static final String ID = "BloodiedWarhammer";
    private static final String IMG = "img/relics_Seles/BloodiedWarhammer.png";
    private static final String IMG_OTL = "img/relics_Seles/outline/BloodiedWarhammer.png";
    private boolean isActive = false;

    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public BloodiedWarhammer() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        isActive = false;
    }
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发
        if(card.damage>0)
        {
            if(!isActive && action.target.currentBlock>0 ){

                isActive = true;
                AbstractMonster m =(AbstractMonster)action.target;
                AbstractPlayer p = AbstractDungeon.player;

                flash();
                addToTop(new RelicAboveCreatureAction(p, this));
                //移除所有格挡
                addToBot(new RemoveAllBlockAction(m, p));
                addToBot(new DamageAction(m,new DamageInfo(m,card.damage)));
                //AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)m, new DamageInfo((AbstractCreature)p, 1, ), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
        }
    }
    @Override
    public void onVictory() {
        //在胜利时触发

    }
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy() {
        return new BloodiedWarhammer();
    }
}
