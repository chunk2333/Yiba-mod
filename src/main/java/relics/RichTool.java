package relics;
//致富神器
import basemod.abstracts.CustomRelic;
import YibaMod.YibaMod;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class RichTool extends CustomRelic {

    public static final String ID = "RichTool";

    private static final String IMG = "img/relics/RichTool.png";

    private static final String IMG_OTL = "img/relics/outline/RichTool.png";

    private static boolean isActive = false;

    public RichTool() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.HEAVY);
    }

    @Override
    public void onGainGold() {
        //获得金币
        if(!isActive){
            isActive = true;
            flash();
            AbstractPlayer p = AbstractDungeon.player;
            p.gainGold(5);
            YibaMod.logger.info("触发致富神器，获得5金币。");
        }
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        //进入任意一个房间就重置。
        isActive = false;
    }

    @Override
    public void atBattleStartPreDraw() {
        isActive = false;
    }

    @Override
    public void atTurnStart(){
        //每回合开始时触发
        isActive = false;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发
        isActive = false;
    }

    @Override
    public void onVictory() {
        //在胜利时触发
        isActive = false;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RichTool();
    }

}
