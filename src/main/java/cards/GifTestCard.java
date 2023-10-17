//package cards;
////测试GIF卡牌
//import animation.AbstractGIFCard;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.core.CardCrawlGame;
//import com.megacrit.cardcrawl.localization.CardStrings;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//
//public class GifTestCard extends AbstractGIFCard {
//    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("FleshShock");
//    public static final String NAME = cardStrings.NAME;
//    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
//    public static final String IMG_PATH = "img/cards/gif/test.gif";
//    private static final int COST = 0;
//    public static final String ID = "GifTestCard";
//
//    public GifTestCard(){
//        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.COMMON, CardTarget.NONE);
//    }
//
//    @Override
//    public void use(AbstractPlayer p, AbstractMonster m) {
//
//    }
//
//    @Override
//    public AbstractCard makeCopy() {
//        return new GifTestCard();
//    }
//
//    @Override
//    public void upgrade() {
//    }
//}
