package relics;
//不曾存在的祭礼枪
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NonexistentSacrificialLance extends CustomRelic {

    public static final String ID = "NonexistentSacrificialLance";

    private static final String IMG = "img/relics/NonexistentSacrificialLance.png";

    private static final String IMG_OTL = "img/relics/outline/NonexistentSacrificialLance.png";

    public NonexistentSacrificialLance() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.HEAVY);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发
        //判断打出的是否为能力牌

        if(card.type == AbstractCard.CardType.POWER){
            int random;
            random = AbstractDungeon.relicRng.random(1,100); //随机数
            if(random<=30 && !card.purgeOnUse){
                flash();
                addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                AbstractMonster m = null;
                if (action.target != null)
                    m = (AbstractMonster)action.target;
                AbstractCard tmp = card.makeSameInstanceOf();
                AbstractDungeon.player.limbo.addToBottom(tmp);
                tmp.current_x = card.current_x;
                tmp.current_y = card.current_y;
                tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = Settings.HEIGHT / 2.0F;
                if (m != null)
                    tmp.calculateCardDamage(m);
                tmp.purgeOnUse = true;
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new NonexistentSacrificialLance();
    }

}
