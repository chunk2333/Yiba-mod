package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class MyAttackDamageRandomEnemyAction extends AbstractGameAction {
    private AbstractCard card;

    private AbstractGameAction.AttackEffect effect;

    private int finalDamage;

    public MyAttackDamageRandomEnemyAction(AbstractCard card, AbstractGameAction.AttackEffect effect, int damage) {
        this.card = card;
        this.effect = effect;
        this.finalDamage = damage;
    }

    public MyAttackDamageRandomEnemyAction(AbstractCard card, int damage) {
        this(card, AbstractGameAction.AttackEffect.NONE, damage);
    }

    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            this.card.calculateCardDamage((AbstractMonster)this.target);
            if (AbstractGameAction.AttackEffect.LIGHTNING == this.effect) {
                addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, this.finalDamage, this.card.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
                addToTop(new SFXAction("ORB_LIGHTNING_EVOKE", 0.1F));
                addToTop(new VFXAction(new LightningEffect(this.target.hb.cX, this.target.hb.cY)));
            } else {
                addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, this.finalDamage, this.card.damageTypeForTurn), this.effect));
            }
        }
        this.isDone = true;
    }
}
