package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class PlayCardAction extends AbstractGameAction {

    private AbstractCard card;

    private AbstractMonster monster;

    public PlayCardAction(AbstractCard c, AbstractMonster m){
        this.card = c;
        this.monster = m;
    }

    @Override
    public void update(){
        if (this.card == null){
            this.isDone = true;
            tickDuration();
            return;
        }

        ArrayList<AbstractMonster>list = new ArrayList<>();

        if (this.monster == null){
            for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
                if (!mo.isDead){
                    list.add(mo);
                }
            }
            int random = AbstractDungeon.monsterRng.random(1, list.size()); //随机数
            this.monster = list.get(random - 1);
        }
        AbstractCard tmp = this.card.makeSameInstanceOf();
        AbstractDungeon.player.limbo.addToBottom(tmp);
        tmp.current_x = card.current_x;
        tmp.current_y = card.current_y;
        tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
        tmp.target_y = Settings.HEIGHT / 2.0F;
        if (this.monster != null)
            tmp.calculateCardDamage(this.monster);
        tmp.purgeOnUse = true;
        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, this.monster, card.energyOnUse, true, true), true);
        this.isDone = true;
        tickDuration();
    }
}
