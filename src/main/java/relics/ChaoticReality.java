package relics;
//混乱现实
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class ChaoticReality extends CustomRelic {

    public static final String ID = "ChaoticReality";

    private static final String IMG = "img/relics/ChaoticReality.png";

    private static final String IMG_OTL = "img/relics/outline/ChaoticReality.png";

    public ChaoticReality() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card.damage <= 0){
            return;
        }

        if(card.type == AbstractCard.CardType.ATTACK){
            int random;
            random = AbstractDungeon.relicRng.random(1,100); //随机数
            if(card.damage >= random){
                flash();
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this)); //头顶出现遗物特效
                AbstractMonster m = (AbstractMonster) action.target;
                if(m == null){
                    ArrayList<AbstractMonster> list = new ArrayList();
                    for(AbstractMonster mo :AbstractDungeon.getCurrRoom().monsters.monsters){
                        if(!mo.isDead){
                            list.add(mo);
                        }
                    }
                    if(list.size() > 1 ){
                        random = AbstractDungeon.relicRng.random(1,list.size());
                        random -= 1;
                        m = list.get(random);
                    }else {
                        m = list.get(0);
                    }

                }
                AbstractPlayer p = AbstractDungeon.player;
                flash();
                addToBot(new DamageAction(m, new DamageInfo(p, card.damage , action.damageType), action.attackEffect));
            }
            }
        }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ChaoticReality();
    }
}
