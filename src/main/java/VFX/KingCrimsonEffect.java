package VFX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;

public class KingCrimsonEffect extends AbstractGameEffect {

    private int count = 0;

    private float timer = 0.0F;

    private boolean reverse = false;

    public KingCrimsonEffect(Color setColor, boolean reverse) {
        this.color = setColor.cpy();
        this.reverse = reverse;
    }

    public KingCrimsonEffect() {
        this(new Color(0.9F, 0.9F, 1.0F, 1.0F), false);
    }

    public void update() {
        this.timer -= Gdx.graphics.getDeltaTime();
        if (this.timer < 0.0F) {
            this.timer += 0.05F;
            if (this.count == 0)
                AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(this.color.cpy()));
            AbstractDungeon.effectsQueue.add(new KingWindyParticleEffect(this.color, this.reverse));
            this.count++;
            if (this.count == 18)
                this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {}

    public void dispose() {}
}
