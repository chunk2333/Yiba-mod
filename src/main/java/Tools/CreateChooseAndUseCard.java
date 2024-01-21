package Tools;

import YibaMod.YibaMod;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class CreateChooseAndUseCard {
    public static ArrayList<AbstractCard> cards = new ArrayList<>();
    public static final String defaultImg = "img/cards/test.png";

    public CreateChooseAndUseCard(){
        addCards();
    }

    public static void addCards() {
        cards.clear();
        YibaMod.logger.info("3.添加选择卡牌：抽牌");
        AbstractCard temp = new AbstractCard("抽牌", "抽牌",defaultImg, 0, "抽 !M! 张牌", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 3;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
                addToBot(new DrawCardAction(this.magicNumber));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);

        temp = new AbstractCard("神抽", "神抽",defaultImg, 0, "将手牌抽满", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
                addToBot(new DrawCardAction(BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.size() + 1));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };

        cards.add(temp);
        temp = new AbstractCard("格挡", "格挡",defaultImg, 0, "获得 !B! 点 格挡 。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseBlock = 5;
                this.block = this.baseBlock;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
                addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);

    }

}
