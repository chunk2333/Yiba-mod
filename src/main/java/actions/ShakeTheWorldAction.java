package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ExpungeVFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ShakeTheWorldAction extends AbstractGameAction {

    public int Damage;

    private boolean freeToPlayOnce = false;

    private DamageInfo.DamageType damageType;

    private AbstractPlayer p;

    private int energyOnUse = -1;

    private AbstractMonster m;

    public ShakeTheWorldAction(AbstractPlayer p, AbstractMonster m, int Damage, DamageInfo.DamageType damageType, boolean freeToPlayOnce, int energyOnUse){
        this.m = m;
        this.Damage = Damage;
        this.damageType = damageType;
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;

    }

    @Override
    public void update(){

        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1)
            effect = this.energyOnUse;
        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }
        if (effect > 0) {
            for (int i = 0; i < effect; i++) {
                addToBot(new ExpungeVFXAction(m));
                addToBot(new DamageAction(this.m, new DamageInfo(this.p, this.Damage, this.damageType), AbstractGameAction.AttackEffect.NONE));
            }
            if (!this.freeToPlayOnce)
                this.p.energy.use(EnergyPanel.totalCount);
        }
        this.isDone = true;
    }

}
