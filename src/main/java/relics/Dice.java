package relics;
//骰子
import YibaMod.YibaMod;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;

public class Dice extends CustomRelic {

    public static final String ID = "Dice";

    private static final String IMG = "img/relics/Dice.png";

    private static final String IMG_OTL = "img/relics/outline/Dice.png";

    public Dice() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        int random;
        random = AbstractDungeon.relicRng.random(1,6); //随机数
        this.counter = random;
        AbstractPlayer p = AbstractDungeon.player;
        YibaMod.logger.info("随机数："+ random);
        flash();
        if(random == 1){
            //回3血
            p.heal(3);
        }
        if(random == 2){
            //力量
            addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
        }
        if(random == 3){
            //敏捷
            addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, 1), 1));
        }if(random == 4) {
            //金属化
            addToTop(new ApplyPowerAction(p, p, new MetallicizePower(p, 1), 1));
        }
        if(random == 5){
            //从抽牌堆选一张卡加入手牌
            addToBot(new BetterDrawPileToHandAction(1));
        }
        if(random == 6){
            //名利双收掉金币动画
            AbstractDungeon.effectList.add(new RainingGoldEffect(100, true));
            AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
            //获得100金币
            p.gainGold(100);
        }
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this)); //头顶出现遗物特效
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onVictory() {
        this.counter = 0;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Dice();
    }

}
