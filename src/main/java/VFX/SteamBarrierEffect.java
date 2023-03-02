package VFX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class SteamBarrierEffect extends AbstractGameEffect {
    private float x;

    private float y;

    private int amt;

    public SteamBarrierEffect(float x, float y, int amt) {
        this.x = x;
        this.y = y;
        this.amt = amt;
        this.duration = 0.2F;
    }

    public SteamBarrierEffect(float x, float y) {
        this(x, y, 60);
    }

    public void update() {
        if (this.duration == 0.2F) {
            CardCrawlGame.sound.play("ATTACK_WHIFF_2");
            for (int i = 0; i < this.amt; i++)
                AbstractDungeon.effectsQueue.add(new SteamEffect(this.x, this.y));
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F)
            this.isDone = true;
    }

    public void render(SpriteBatch sb) {}

    public void dispose() {}
}
