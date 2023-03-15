package relics;
//圣杯
import basemod.abstracts.CustomRelic;
import cards.colorless.ChoseMiracle;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.cards.optionCards.FameAndFortune;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import java.util.ArrayList;


public class Grail extends CustomRelic {
    public static final String ID = "Grail";

    private static final String IMG = "img/relics/Grail.png";

    private static final String IMG_OTL = "img/relics/outline/Grail.png";

    public Grail() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
        stanceChoices.add(new BecomeAlmighty());
        stanceChoices.add(new FameAndFortune());
        stanceChoices.add(new LiveForever());
        AbstractCard Miracle = new ChoseMiracle();
        Miracle.upgrade();
        stanceChoices.add(Miracle);
        addToBot(new ChooseOneAction(stanceChoices));
        flash();
        addToTop(new RelicAboveCreatureAction(p, this));
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发
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
        return new Grail();
    }
}