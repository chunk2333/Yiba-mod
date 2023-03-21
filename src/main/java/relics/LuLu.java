package relics;
//LuLu
import power.MyFlightPower;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;

public class LuLu extends CustomRelic {

    public static final String ID = "LuLu";

    private static final String IMG = "img/relics/LuLu.png";

    private static final String IMG_OTL = "img/relics/outline/LuLu.png";

    private boolean used;

    private boolean getInShop;

    public LuLu() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.SPECIAL, LandingSound.CLINK);
        this.used = false;
        this.getInShop = false;
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        AbstractPlayer p = AbstractDungeon.player;
        //华丽收场特效
        addToBot(new VFXAction(new GrandFinalEffect(), 1.0F));
        //边框金色闪烁效果
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
        //给予自身1层飞行
        addToTop(new ApplyPowerAction(p, p, new MyFlightPower(p, 1), 1));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        //拾取时触发
        AbstractPlayer p = AbstractDungeon.player;
        //回满血
        p.heal(p.maxHealth);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LuLu();
    }

    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.currMapNode != null &&
                AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.ShopRoom && (
                this.getInShop || !this.used)) {
            //商店打8折
            AbstractDungeon.shopScreen.applyDiscount(0.8F, true);
            this.used = true;
            this.getInShop = false;
        }
    }

}
