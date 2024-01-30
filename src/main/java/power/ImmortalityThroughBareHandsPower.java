package power;

import YibaMod.YibaMod;
import actions.PlayCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class ImmortalityThroughBareHandsPower extends AbstractPower {
    public static final String POWER_ID = YibaMod.makeModID("ImmortalityThroughBareHandsPower");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private final AbstractPlayer p = AbstractDungeon.player;

    public ImmortalityThroughBareHandsPower(AbstractCreature owner) {
        super();
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        updateDescription();
        loadRegion("darkembrace");
        this.type = AbstractPower.PowerType.BUFF;
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        if(!isPlayer){
            return;
        }
        ArrayList<AbstractCard> cardsToAutoPlay = new ArrayList<>();
        for(AbstractCard c : p.hand.group){
            if(c.cost > 0){
                cardsToAutoPlay.add(c);
            }
        }
        if(cardsToAutoPlay.isEmpty()){
            return;
        }
        flash();
        for(AbstractCard c : cardsToAutoPlay){
            addToBot(new AbstractGameAction() {
                public void update() {
                    addToBot(new PlayCardAction(c, (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng)));
                    this.isDone = true;
                }
            });
        }
    }

    private AbstractMonster getRandomMonster(){
        return AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }
}
