package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.CardRewardScreen;

import java.util.ArrayList;

public class ChooseOneAndUseAction extends AbstractGameAction {

    public static int cost = -114514;

    private ArrayList<AbstractCard> c = new ArrayList<>();

    private AbstractMonster m;

    private boolean retrieveCard = false;

    private boolean costCard = false;

    private AbstractPlayer p = AbstractDungeon.player;

    public ChooseOneAndUseAction(ArrayList<AbstractCard> CARD, boolean COSTCard){
        this.costCard = COSTCard;
        this.c = CARD;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public ChooseOneAndUseAction(ArrayList<AbstractCard> CARD, AbstractMonster abstractMonster){
        this.c = CARD;
        this.m = abstractMonster;
        if (this.m == null){
            this.m = getRandomMonster();
        }
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update(){
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(this.c, CardRewardScreen.TEXT[1], true);
            tickDuration();
            return;
        }

        if (!this.retrieveCard) {

            if (AbstractDungeon.cardRewardScreen.discoveryCard != null){
                AbstractCard useCard = AbstractDungeon.cardRewardScreen.discoveryCard;
                if(useCard.target == AbstractCard.CardTarget.ENEMY || useCard.target == AbstractCard.CardTarget.SELF_AND_ENEMY){
                    if(this.m == null){
                        return;
                    }
                }
                if(this.costCard){
                    cost = useCard.cost;
                    this.p.loseEnergy(cost);
                }
                useCard.use(p, this.m);
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
                this.retrieveCard = true;
            }
        }
        tickDuration();
    }

    private AbstractMonster getRandomMonster(){
        return AbstractDungeon.getRandomMonster();
    }
}
