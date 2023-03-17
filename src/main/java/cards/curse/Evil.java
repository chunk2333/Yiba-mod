package cards.curse;
//恶德
import basemod.abstracts.CustomCard;
import YibaMod.YibaMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;

public class Evil extends CustomCard{
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Evil");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/test.png";
    private static final int COST = -2;
    public static final String ID = "Evil";
    public Evil() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
        //不能被打出
        this.dontTriggerOnUseCard = true;
        //虚无
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
    }

    public void insertCurseCard(){
        for (AbstractCard c : AbstractDungeon.curseCardPool.group) {
            if(c.type== AbstractCard.CardType.CURSE && !c.name.equals(this.name)){
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (Settings.WIDTH / 2), (Settings.HEIGHT / 2)));
            }
        }
    }


    @Override
    public void triggerWhenCopied() {
        //复制时触发


    }

    @Override
    public CustomCard makeCopy() {
        //复制卡牌时触发
        //if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            //YibaMod.logger.info("恶德被复制，在战斗中，什么不触发。" );
            //return new Evil();
        //}
        //塞诅咒到主牌堆
        //YibaMod.logger.info("恶德被加入牌库，开始复制。" );
        //insertCurseCard();
        return new Evil();
    }
    @Override
    public void upgrade() {
        //卡牌升级后的效果
    }
}