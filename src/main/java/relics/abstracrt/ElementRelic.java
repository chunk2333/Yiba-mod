package relics.abstracrt;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public abstract class ElementRelic extends CustomRelic {
    public ElementRelic(String id, Texture texture, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx) {
        super(id, texture, tier, sfx);
    }

    public ElementRelic(String id, Texture texture, Texture outline, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx) {
        super(id, texture, outline, tier, sfx);
    }

    public ElementRelic(String id, String imgName, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx) {
        super(id, imgName, tier, sfx);
    }


    public void show() {
        super.flash();
    }

    public void onEquip() {
        super.onEquip();
    }

    public abstract void triggerElement(String reactionName);

    public void triggerElement_Fuc(String reactionName) {
        triggerElement(reactionName);
    }
}
