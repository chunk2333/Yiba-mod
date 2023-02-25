package CardModifier;
//TestCardModifier


import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TestCardModifier extends AbstractCardModifier {
    private int uses;
    private int damage;

    public TestCardModifier(int uses, int damage) {
        this.uses = uses;
        this.damage = damage;
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        --uses;
        return uses == 0;
    }
    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage + this.damage;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new TestCardModifier(uses, damage);
    }
}