package relics.ClickRelic;
//撒币
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import relics.abstracrt.ClickableRelic;

public class ScatterCoins extends ClickableRelic {

    public static final String ID = "ScatterCoins";

    private static final String IMG = "img/relics/ScatterCoins.png";

    private static final String IMG_OTL = "img/relics/outline/ScatterCoins.png";

    public ScatterCoins() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.SOLID);
    }
    @Override
    public void onRightClick(){
        //判断是否能支付5金币
        if (AbstractDungeon.getMonsters() != null && (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)){
            AbstractPlayer p = AbstractDungeon.player;
            int loseHp;
            if(p.gold<5){
                loseHp = 5 - p.gold;
                //掉血
                addToBot(new LoseHPAction(p, p, loseHp));
                //掉钱
                p.loseGold(p.gold);
            }else {
                //掉钱
                p.loseGold(5);
            }
            //给予全部敌人5点伤害
            show();
            addToBot(new DamageAllEnemiesAction(null,

                    DamageInfo.createDamageMatrix(5, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }

    }
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ScatterCoins();
    }

}