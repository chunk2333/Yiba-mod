package relics;
//homo头(田所浩二的头)
import Tools.YiBaHelper;
import YibaMod.YibaMod;
import basemod.abstracts.CustomRelic;
import cards.curse.YouAreOne_OneByOne;
import cards.curse.desire;
import cards.curse.snowman;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class HomoHead extends CustomRelic {

    public static final String ID = "HomoHead";

    private static final String IMG = "img/relics/HomoHead.png";

    private static final String IMG_OTL = "img/relics/outline/HomoHead.png";

    public HomoHead() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, LandingSound.MAGICAL);

    }

    @Override
    public void atBattleStart() {
        //TempMusic tempMusic = new TempMusic("HomoVoice.ogg", true, true);
        //在战斗开始时触发
        AbstractCard card;
        int random;
        random = AbstractDungeon.relicRng.random(1,3); //随机数
        YibaMod.logger.info("homo头随机数：" + random);
        switch (random){
            case 1 :
                card = new snowman();
                break;
            case 2 :
                card = new desire();
                break;
            case 3:
                card = new YouAreOne_OneByOne();
                break;
            default :
                card = new snowman();
        }
        flash();
        //遗物特效，在头顶显示
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        int random_play;
        random_play = AbstractDungeon.relicRng.random(1,100); //随机数
        if(random_play<=5){
            //s.play("HomoVoice",true);
            addToBot(new SFXAction(YiBaHelper.MakeSoundPath("HomoVoice")));
            addToBot(new TalkAction(true, "哼！哼！哼！啊啊啊啊啊啊啊啊啊啊啊啊！！！！", 1.0F, 2.0F));
        }
        //往抽牌堆里塞一张“食雪汉”诅咒
        addToBot(new MakeTempCardInDrawPileAction(card, 1, true, true));

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        //拾取时触发
        AbstractDungeon.player.energy.energyMaster++;
    }

    @Override
    public void onUnequip() {
        //丢弃时触发
        AbstractDungeon.player.energy.energyMaster--;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new HomoHead();
    }

}
