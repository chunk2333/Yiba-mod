package relics;
//瓶装答辩
import basemod.abstracts.CustomRelic;
import YibaMod.YibaMod;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import java.util.ArrayList;

public class BottledPoop extends CustomRelic {

    public static final String ID = "BottledPoop";

    private static final String IMG = "img/relics/BottledPoop.png";

    private static final String IMG_OTL = "img/relics/outline/BottledAir.png";

    public static int num_BottledPoop;

    public BottledPoop() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        flash();
        //在战斗开始时触发
        //头顶出现遗物特效
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        //给予随机诅咒
        addToBot(new MakeTempCardInDrawPileAction(getRadomCurseCard(), 1, true, true));
        num_BottledPoop = this.counter;
    }
    public AbstractCard getRadomCurseCard(){
        ArrayList<AbstractCard> list = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.curseCardPool.group) {
            if(c.type== AbstractCard.CardType.CURSE){
                list.add(c);
            }
        }
        YibaMod.logger.info("全部诅咒牌数："+ (list.size() - 1));
        return list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1));
    }

    @Override
    public void onVictory() {
        //在胜利时触发
        this.counter = this.counter + 1;
        num_BottledPoop = this.counter;
        if(this.counter==5){
            int relicAtIndex = 0;
            for (int i = 0; i < AbstractDungeon.player.relics.size(); i++) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals("BottledPoop")) {
                    relicAtIndex = i;
                    break;
                }
            }
            AbstractDungeon.player.relics.get(relicAtIndex).onUnequip();
            AbstractRelic bloodyIdol = RelicLibrary.getRelic("BottledAir").makeCopy();
            bloodyIdol.instantObtain(AbstractDungeon.player, relicAtIndex, false);
            num_BottledPoop = 0;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    //拾取时触发
    public void onEquip() {
        this.counter=0;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BottledPoop();
    }

}
