package relics;
//开放性思维
import Tools.YiBaHelper;
import actions.GetRandomRelicAction;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;

public class OpenMindedness extends CustomRelic {

    public static final String ID = "OpenMindedness";

    private static final String IMG = "img/relics/OpenMindedness.png";

    private static final String IMG_OTL = "img/relics/outline/OpenMindedness.png";

    private Boolean isUsed = false;

    public OpenMindedness() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        if(!this.isUsed){
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GetRandomRelicAction());
            this.isUsed = true;
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发
        flash();
        int random;
        random = AbstractDungeon.relicRng.random(1,1000); //随机数
        AbstractPlayer p = AbstractDungeon.player;
        Color color = Color.GREEN.cpy();
        switch (AbstractDungeon.relicRng.random(1,10)){
            case 1:
                color = Color.GREEN.cpy();
                break;
            case 2:
                color = Color.RED.cpy();
                break;
            case 3:
                color = Color.YELLOW.cpy();
                break;
            case 4:
                color = Color.PINK.cpy();
                break;
            case 5:
                color = Color.BLACK.cpy();
                break;
            case 6:
                color = Color.BLUE.cpy();
                break;
            case 7:
                color = Color.BROWN.cpy();
                break;
            case 8:
                color = Color.WHITE.cpy();
                break;
            case 9:
                color = Color.GOLD.cpy();
                break;
            case 10:
                color = Color.PURPLE.cpy();
                break;
            default:
                color = Color.GREEN.cpy();
                break;
        }


        AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(p.drawX, p.drawY, "妈妈省的", color));

        addToBot(new TalkAction(true, "阿巴阿巴", 1.5F, 1.5F));
        if(random <= 10){

            //头顶遗物特效
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            //播放音频：妈妈省的
            addToBot(new SFXAction(YiBaHelper.MakeSoundPath("AbaAba")));

        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new OpenMindedness();
    }

}
