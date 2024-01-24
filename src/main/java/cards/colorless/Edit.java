package cards.colorless;

import Tools.CreateChooseAndUseCard;
import YibaMod.YibaMod;
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

    public static final String IMG_PATH = "img/cards/test.png";

    private static final int COST = 0;

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

        ArrayList<AbstractCard> costCard = new ArrayList<>();
//        for(int i = 0; i < p.energy.energyMaster; i++){
//            costCard.add(CreateEnergyCards(i + 1));
//        }
        for (int i = 0; i < this.magicNumber; i++){
            addToBot(new ChooseOneAndUseAction(getRandomEditCards(CreateChooseAndUseCard.cards), false));
        }
    }

    public ArrayList<AbstractCard> getRandomEditCards(ArrayList<AbstractCard> cardsArray){
        ArrayList<AbstractCard> temp = new ArrayList<>();
        int random;
        for (int i=0; i<3; i++){
            random = AbstractDungeon.cardRandomRng.random(1, cardsArray.size());
            temp.add(cardsArray.get(random-1));
        }
        return temp;
    }


    public AbstractCard CreateEnergyCards(int cost){
        String num = "";
        for(int i = 0; i < cost; i++){
            num += "[E] ";
        }
        AbstractCard temp = new AbstractCard("选择费用", "选择费用", "img/cards/test.png", cost, "使用 " + num + "。", CardType.SKILL, CardColor.COLORLESS, CardRarity.BASIC, CardTarget.SELF) {

            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
                ChooseOneAndUseAction.cost = this.cost;
                if(CreateChooseAndUseCard.cards.isEmpty()){
                    CreateChooseAndUseCard.addCards();
                }

                addToBot(new ChooseOneAndUseAction(CreateChooseAndUseCard.cards, monster));
                YibaMod.logger.info(this.rawDescription + "被使用了");
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };

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
