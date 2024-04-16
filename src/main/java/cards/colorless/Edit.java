package cards.colorless;
//编辑
import Tools.CreateChooseAndUseCard;
import actions.ChooseOneAndUseAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Edit extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Edit");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/Edit.png";

    private static final int COST = 1;

    public static final String ID = "Edit";

    public static AbstractMonster monster;

    public Edit() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.RARE, CardTarget.SELF_AND_ENEMY);
        this.magicNumber = this.baseMagicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(CreateChooseAndUseCard.cards.isEmpty()){
            CreateChooseAndUseCard.addCards();
        }
        monster = m;
        p.loseEnergy(p.energy.energyMaster);
        for (int i = 0; i < this.magicNumber; i++){
            addToBot(new ChooseOneAndUseAction(getRandomEditCards(CreateChooseAndUseCard.cards), m));
        }
    }

    public ArrayList<AbstractCard> getRandomEditCards(ArrayList<AbstractCard> cardsArray){
        ArrayList<AbstractCard> temp = new ArrayList<>();
        int random;
        do {
            temp.clear();  // 清空temp数组
            for (int i = 0; i < 3; i++) {
                do {
                    random = AbstractDungeon.cardRandomRng.random(1, cardsArray.size());
                } while (temp.contains(cardsArray.get(random - 1)));  // 检查是否已经包含该卡牌
                temp.add(cardsArray.get(random - 1));
            }

        } while (temp.size() != 3);

        return temp;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Edit();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
