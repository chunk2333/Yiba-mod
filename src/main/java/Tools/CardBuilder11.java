package Tools;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class CardBuilder11 {
    private String id;
    private String name;
    private String imgUrl;
    private int cost;
    private String rawDescription;
    private AbstractCard.CardType type;
    private AbstractCard.CardColor color;
    private AbstractCard.CardRarity rarity;
    private AbstractCard.CardTarget target;
    public static ArrayList<AbstractGameAction> actions = new ArrayList<>();;

    public CardBuilder11(String id) {
        this.id = id;
    }

    public CardBuilder11 name(String name) {
        this.name = name;
        return this;
    }

    public CardBuilder11 imgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public CardBuilder11 cost(int cost) {
        this.cost = cost;
        return this;
    }

    public CardBuilder11 rawDescription(String rawDescription) {
        this.rawDescription = rawDescription;
        return this;
    }

    public CardBuilder11 type(AbstractCard.CardType type) {
        this.type = type;
        return this;
    }

    public CardBuilder11 color(AbstractCard.CardColor color) {
        this.color = color;
        return this;
    }

    public CardBuilder11 rarity(AbstractCard.CardRarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public CardBuilder11 target(AbstractCard.CardTarget target) {
        this.target = target;
        return this;
    }

    public CardBuilder11 target(ArrayList<AbstractGameAction> actions) {
        CardBuilder11.actions = actions;
        return this;
    }

    public AbstractCard create() {
        // 在这里创建并返回一个 AbstractCard 对象
        AbstractCard card = new AbstractCard(id, name, imgUrl, cost, rawDescription, type, color, rarity, target) {

            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
                for(AbstractGameAction act : actions){
                    addToBot(act);
                }
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };

        return card;
    }
}
