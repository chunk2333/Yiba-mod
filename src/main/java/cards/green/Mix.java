package cards.green;
//调配 ---- 改成直接给的就是本回合0费，升级后0费
import YibaMod.YibaMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Mix extends CustomCard {
    public static final String ID = YibaMod.makeModID("Mix");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/Mix.png";

    private static ArrayList<AbstractCard> MixCards = new ArrayList<>();

    private static final int COST = 1;

    public Mix() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
        MixCards.add(new DeadlyPoison());
        MixCards.add(new NoxiousFumes());
        MixCards.add(new CripplingPoison());
        MixCards.add(new BouncingFlask());
        MixCards.add(new CorpseExplosion());
        MixCards.add(new Catalyst());
        MixCards.add(new Envenom());
        //MixCards.add();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int random = AbstractDungeon.cardRandomRng.random(1, 100);
                AbstractCard c;
                if (random <= 15){
                    c = new Alchemize();
                } else {
                    c = MixCards.get(AbstractDungeon.cardRandomRng.random(0, MixCards.size()) - 1);
                }
                if (upgraded){
                    c.costForTurn = 0;
                }
                addToBot(new MakeTempCardInHandAction(c));
                tickDuration();
            }
        });
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mix();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
