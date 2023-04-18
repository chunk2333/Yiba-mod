package relics;
//不倒翁
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class Tumbler extends CustomRelic {

    public static final String ID = "Tumbler";

    private static final String IMG = "img/relics/Tumbler.png";

    private static final String IMG_OTL = "img/relics/outline/Tumbler.png";

    public Tumbler() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if(targetCard.type == AbstractCard.CardType.ATTACK){
            AbstractMonster m = (AbstractMonster) useCardAction.target;
            if(m == null){
                ArrayList<AbstractMonster> list = new ArrayList();
                for(AbstractMonster mo :AbstractDungeon.getCurrRoom().monsters.monsters){
                    if(!mo.isDead){
                        list.add(mo);
                    }
                }
                if(list.size() > 1 ){
                    int random = AbstractDungeon.relicRng.random(1,list.size());
                    random -= 1;
                    m = list.get(random);
                }else {
                    m = list.get(0);
                }

            }
            AbstractPlayer p = AbstractDungeon.player;
            flash();
            addToBot(new DamageAction(m, new DamageInfo(p, (int) (targetCard.damage * 0.25), useCardAction.damageType), useCardAction.attackEffect));
        }
    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Tumbler();
    }
}
