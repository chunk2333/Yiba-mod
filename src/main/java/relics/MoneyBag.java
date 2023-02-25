//MoneyBag
package relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
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

public class MoneyBag extends CustomRelic {
    public static final String ID = "MoneyBag";
    private static final String IMG = "img/relics_Seles/MoneyBag.png";
    private static final String IMG_OTL = "img/relics_Seles/outline/MoneyBag.png";
    private boolean isActive = false;

    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public MoneyBag() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
    }
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发

    }
    @Override
    public void onVictory() {
        //在胜利时触发:清楚激活

    }
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    @Override
    //拾取时触发
    public void onEquip() {
        AbstractPlayer p = AbstractDungeon.player;
        p.gainGold(150);
    }
    @Override
    public AbstractRelic makeCopy() {
        return new MoneyBag();
    }
    @Override
    public boolean canSpawn() {
        return ((Settings.isEndless || AbstractDungeon.floorNum <= 48) &&
                !(AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.ShopRoom));
    }
}
