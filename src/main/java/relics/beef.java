package relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class beef extends CustomRelic {
    public static final String ID = "beef";
    private static final String IMG = "img/relics_Seles/beef.png";
    private static final String IMG_OTL = "img/relics_Seles/outline/beef.png";

    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public beef() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.SPECIAL, AbstractRelic.LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发
        //打出一张牌回复1生命
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new RelicAboveCreatureAction(p, this));
        p.heal(1);
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

    public void onEquip() {
        //拾取时触发
    }
    @Override
    public AbstractRelic makeCopy() {
        return new beef();
    }
}
