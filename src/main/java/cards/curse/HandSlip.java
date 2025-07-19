package cards.curse;
//手滑
import YibaMod.YibaMod;
import actions.PlayHandCardAction;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;


@SpirePatch2(clz = AbstractPlayer.class, method = "useCard")
public class HandSlip extends CustomCard {
    public static final String ID = YibaMod.makeModID("HandSlip");
    public static final String IMG_PATH = "img/cards/HandSlip.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = -2;

    public static boolean isInHand = false;
    public static boolean isPlaying = false;
    public static AbstractMonster monster = null;

    AbstractPlayer p = AbstractDungeon.player;
    public HandSlip() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
        this.dontTriggerOnUseCard = true;
    }

    @SpireInsertPatch(loc = 1681, localvars={"c", "monster", "energyOnUse"})

    public static SpireReturn<Void> useCardFix(@ByRef AbstractCard[] ___c, @ByRef AbstractMonster[] ___monster, @ByRef int[] ___energyOnUse){
        YibaMod.logger.info("HandSlip 开始触发");
        isInHand = false;

        boolean canUse = true;
        int random;
        for(AbstractCard c : AbstractDungeon.player.hand.group){
            if(c.cardID.equals(ID)){
                isInHand = true;
            }
        }

        if (!isInHand){
            return SpireReturn.Continue();
        }

        if(isPlaying){
            isPlaying = false;
            return SpireReturn.Continue();
        }

        YibaMod.logger.info("HandSlip 在手里");

        ArrayList<AbstractCard> temp = new ArrayList<>();

        for(AbstractCard c : AbstractDungeon.player.hand.group){

            if(c.cardID.equals(ID)){
                continue;
            }

            if (c.cost == ___c[0].cost){
                canUse = false;
                temp.add(c);
            }
        }

        int sameCostCard = 0;

        for(AbstractCard c : AbstractDungeon.player.hand.group){
            if (c.cost == ___c[0].cost){
                sameCostCard += 1;
            }

            if (c.cost <0) {
                sameCostCard += 1;
            }

        }

        if (sameCostCard == AbstractDungeon.player.hand.group.size()){

            return SpireReturn.Continue();

        }


        random = AbstractDungeon.cardRandomRng.random(0, temp.size()-1); //随机数

        YibaMod.logger.info(random);


        if(canUse){
            return SpireReturn.Continue();
        } else {
            AbstractCard temp_c = temp.get(random);

            isPlaying = true;

            if (___monster[0] == null){
                monster = AbstractDungeon.getRandomMonster();
            }

            AbstractDungeon.actionManager.addToBottom(new PlayHandCardAction(monster, temp_c, temp_c.exhaust));
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "'手滑了~'", 1.0F, 2.0F));
            AbstractDungeon.actionManager.addToBottom(new LoseEnergyAction(temp_c.cost));
            return SpireReturn.Return(null);
        }


    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
    }

    @Override
    public void triggerWhenDrawn() {
        isInHand = true;
    }



    @Override
    public AbstractCard makeCopy() {
        return new HandSlip();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }
}
