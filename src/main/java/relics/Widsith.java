package relics;
//流浪乐章
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import YibaMod.YibaMod;

public class Widsith extends CustomRelic {
    public static final String ID = "widsith";

    private static final String IMG = "img/relics/widsith.png";

    private static final String IMG_OTL = "img/relics/outline/widsith.png";

    public Widsith() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        int random;
        random = AbstractDungeon.relicRng.random(1,3); //随机数
        AbstractPlayer p = AbstractDungeon.player;
        YibaMod.logger.info("随机数："+ random);
        flash();
        if(random==1){
         //力量 StrengthPower
            addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
        }
        if(random==2){
            //敏捷 DexterityPower
            addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, 1), 1));
        }if(random==3) {


            //集中 FocusPower
            addToTop(new ApplyPowerAction(p, p, new FocusPower(p, 1), 1));
        }
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this)); //头顶出现遗物特效
    }

    @Override
    public void atTurnStart(){
        //每回合开始时触发
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
        return new Widsith();
    }
}