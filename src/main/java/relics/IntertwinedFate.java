package relics;
//纠缠之缘
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;

public class IntertwinedFate extends CustomRelic {
    public static final String ID = "IntertwinedFate";
    private static final String IMG = "img/relics/IntertwinedFate.png";
    private static final String IMG_OTL = "img/relics/outline/IntertwinedFate.png";
    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public IntertwinedFate() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.HEAVY);
    }

    @Override
    public void atTurnStart(){
        //每回合开始时触发
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发
        int random;
        random = AbstractDungeon.relicRng.random(1,1000); //随机数
        AbstractPlayer p = AbstractDungeon.player;
        if(random<=6){
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            //名利双收掉金币动画
            AbstractDungeon.effectList.add(new RainingGoldEffect(999, true));
            AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
            p.gainGold(999);
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
    //拾取时触发
    public void onEquip() {
    }
    @Override
    public AbstractRelic makeCopy() {
        return new IntertwinedFate();
    }
}