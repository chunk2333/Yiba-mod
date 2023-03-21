package relics;
//染血战锤
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

public class BloodiedWarhammer extends CustomRelic {

    public static final String ID = "BloodiedWarhammer";

    private static final String IMG = "img/relics/BloodiedWarhammer.png";

    private static final String IMG_OTL = "img/relics/outline/BloodiedWarhammer.png";

    public BloodiedWarhammer() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发
        AbstractPlayer p = AbstractDungeon.player;
        //判断是否为攻击牌
        if(card.type== AbstractCard.CardType.ATTACK){
            flash();
            //给予1点临时力量
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, 1), 1));
        }
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

