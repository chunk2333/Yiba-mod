package panel;

import YibaMod.YibaMod;
import basemod.TopPanelItem;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;

public class Solarization extends TopPanelItem {
    private static final Texture IMG = new Texture("img/ui/Solarization.png");

    public static final String ID = "Solarization";

    public Solarization() {
        super(IMG, ID);
    }

    @Override
    protected void onClick() {
        YibaMod.logger.info("你点击了：Solarization");
        this.hitbox.move(10000.0F, this.hitbox.cY);
    }

    @Override
    protected void onHover(){
        this.angle = MathHelper.angleLerpSnap(this.angle, 15.0F);
        this.tint.a = 0.25F;
        TipHelper.renderGenericTip(this.getHitbox().x, this.getHitbox().y - 15F, "瓶装空气", "这只是一瓶 #r空气 ！");
    }
}
