package events;

import YibaMod.YibaMod;
import cards.colorless.BullAndHorseMorphology;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class JueZeEvent extends AbstractImageEvent {

    public static final String ID = YibaMod.makeModID("JueZe") ;

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    public static final String NAME = eventStrings.NAME;

    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;

    public static final String[] OPTIONS = eventStrings.OPTIONS;

    private JueZeEvent.CurScreen screen = CurScreen.INTRO;

    private int loseHp = 13;

    private enum CurScreen {
        INTRO, END;
    }
    public JueZeEvent(){
        super(NAME, DESCRIPTIONS[0], "img/events/JueZe.png");
        this.imageEventText.clearRemainingOptions();
        this.imageEventText.setDialogOption(OPTIONS[0], new BullAndHorseMorphology());//GETCARD
        this.imageEventText.setDialogOption(OPTIONS[1]);//GETGOLD
        this.imageEventText.setDialogOption(OPTIONS[2]);//LEAVE
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                if (buttonPressed == 0){ //GETCARD
                    CardCrawlGame.screenShake.mildRumble(5.0F);
                    CardCrawlGame.sound.play("BLUNT_HEAVY");
                    AbstractDungeon.player.damage(new DamageInfo(null, loseHp, DamageInfo.DamageType.HP_LOSS));
                    //获得无色牌
                    AbstractCard NiuMa = new BullAndHorseMorphology();
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(NiuMa, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    this.imageEventText.clearRemainingOptions();
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption("离开");
                    this.imageEventText.updateDialogOption(0,"离开");
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.removeDialogOption(2);
                    this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                    this.screen = CurScreen.END;
                    break;
                }
                if (buttonPressed == 1){
                    CardCrawlGame.screenShake.mildRumble(5.0F);
                    CardCrawlGame.sound.play("BLUNT_HEAVY");
                    AbstractDungeon.player.damage(new DamageInfo(null, loseHp, DamageInfo.DamageType.HP_LOSS));
                    AbstractDungeon.effectList.add(new RainingGoldEffect(150));
                    AbstractDungeon.player.gainGold(150);
                    this.imageEventText.clearRemainingOptions();
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption("离开");
                    this.imageEventText.updateDialogOption(0,"离开");
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.removeDialogOption(2);
                    this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                    this.screen = CurScreen.END;
                    break;
                }
                if (buttonPressed == 2){
                    AbstractDungeon.player.loseGold(AbstractDungeon.player.gold);
                    this.imageEventText.clearRemainingOptions();
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.setDialogOption("离开");
                    this.imageEventText.updateDialogOption(0,"离开");
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.removeDialogOption(2);
                    this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                    this.screen = CurScreen.END;
                    break;
                }
                break;
            case END:
                this.imageEventText.updateDialogOption(0,"离开");
                this.imageEventText.removeDialogOption(1);
                this.imageEventText.removeDialogOption(2);
                openMap();
        }
    }
}
