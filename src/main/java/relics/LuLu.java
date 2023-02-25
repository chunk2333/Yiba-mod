

//LuLu

package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FlightPower;
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
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LuLu extends CustomRelic {
    public static final String ID = "LuLu";
    private static final String IMG = "img/relics_Seles/LuLu.png";
    private static final String IMG_OTL = "img/relics_Seles/outline/LuLu.png";
    private boolean used;
    private boolean getInShop;
    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public LuLu() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.SPECIAL, LandingSound.CLINK);
        this.used = false;
        this.getInShop = false;
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        AbstractPlayer p = AbstractDungeon.player;
        //华丽收场特效
        addToBot(new VFXAction(new GrandFinalEffect(), 1.0F));
        //边框金色闪烁效果
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
        //给予自身1层飞行
        addToTop(new ApplyPowerAction(p, p, new FlightPower(p, 1), 1));
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
        //回满血
        p.heal(p.maxHealth);
    }
    @Override
    public AbstractRelic makeCopy() {
        return new LuLu();
    }

    public void update() {
        super.update();
        if (AbstractDungeon.currMapNode != null &&
                AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.ShopRoom && (
                this.getInShop || !this.used)) {
            //商店打8折
            AbstractDungeon.shopScreen.applyDiscount(0.8F, true);
            this.used = true;
            this.getInShop = false;
        }
    }
}
