package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.mainMenu.MenuCancelButton;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBar;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBarListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RelicSelectScreen implements ScrollBarListener {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RelicSelectScreen");

    public static final String[] TEXT = uiStrings.TEXT;

    private static final float SPACE = 80.0F * Settings.scale;

    private static final float START_X = 600.0F * Settings.scale;

    private static final float START_Y = Settings.HEIGHT - 300.0F * Settings.scale;

    private float scrollY;

    private float targetY;

    private final float scrollLowerBound;

    private final float scrollUpperBound;

    private int row;

    private int col;

    public MenuCancelButton button;

    private AbstractRelic hoveredRelic;

    private AbstractRelic clickStartedRelic;

    private List<AbstractRelic> relicGroup;

    private boolean grabbedScreen;

    private float grabStartY;

    private final ScrollBar scrollBar;

    private Hitbox controllerRelicHb;

    public boolean isDone = true;

    private Consumer<AbstractRelic> relicSelectAction;

    private Runnable endSelectAction;

    public RelicSelectScreen() {
        this.scrollY = START_Y;
        this.targetY = this.scrollY;
        this.scrollLowerBound = Settings.HEIGHT - 100.0F * Settings.scale;
        this.scrollUpperBound = 3000.0F * Settings.scale;
        this.row = 0;
        this.col = 0;
        this.button = new MenuCancelButton();
        this.hoveredRelic = null;
        this.clickStartedRelic = null;
        this.grabbedScreen = false;
        this.grabStartY = 0.0F;
        this.controllerRelicHb = null;
        this.scrollBar = new ScrollBar(this);
    }

    public void open(List<AbstractRelic> relicGroup, Consumer<AbstractRelic> relicSelectAction, Runnable endSelectAction) {
        this.controllerRelicHb = null;
        RelicLibrary.unlockAndSeeAllRelics();
        this.button.show(TEXT[0]);
        this.targetY = this.scrollLowerBound;
        this.scrollY = Settings.HEIGHT - 400.0F * Settings.scale;
        this.relicGroup = new ArrayList<>(relicGroup);
        for (AbstractRelic relic : relicGroup)
            relic.isSeen = true;
        this.isDone = false;
        this.relicSelectAction = relicSelectAction;
        this.endSelectAction = endSelectAction;
    }

    public void update() {
        if (this.isDone)
            return;
        if (Settings.isControllerMode && this.controllerRelicHb != null)
            if (Gdx.input.getY() > Settings.HEIGHT * 0.7F) {
                this.targetY += Settings.SCROLL_SPEED;
                if (this.targetY > this.scrollUpperBound)
                    this.targetY = this.scrollUpperBound;
            } else if (Gdx.input.getY() < Settings.HEIGHT * 0.3F) {
                this.targetY -= Settings.SCROLL_SPEED;
                if (this.targetY < this.scrollLowerBound)
                    this.targetY = this.scrollLowerBound;
            }
        if (this.hoveredRelic == null) {
            this.clickStartedRelic = null;
        } else {
            CardCrawlGame.cursor.changeType(GameCursor.CursorType.INSPECT);
            if (InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed())
                this.clickStartedRelic = this.hoveredRelic;
            if (InputHelper.justReleasedClickLeft || CInputActionSet.select.isJustPressed()) {
                CInputActionSet.select.unpress();
                if (this.hoveredRelic == this.clickStartedRelic) {
                    this.relicSelectAction.accept(this.clickStartedRelic);
                    this.clickStartedRelic = null;
                }
            }
        }
        this.button.update();
        if (this.button.hb.clicked || InputHelper.pressedEscape) {
            InputHelper.pressedEscape = false;
            this.button.hide();
            this.isDone = true;
        }
        boolean isScrollingScrollBar = this.scrollBar.update();
        if (!isScrollingScrollBar)
            updateScrolling();
        InputHelper.justClickedLeft = false;
        this.hoveredRelic = null;
        for (AbstractRelic r : this.relicGroup) {
            r.hb.move(r.currentX, r.currentY);
            r.update();
            if (r.hb.hovered) {
                this.hoveredRelic = r;
                break;
            }
        }
        if (this.isDone)
            this.endSelectAction.run();
    }

    private void updateScrolling() {
        if (!CardCrawlGame.relicPopup.isOpen) {
            int y = InputHelper.mY;
            if (!this.grabbedScreen) {
                if (InputHelper.scrolledDown) {
                    this.targetY += Settings.SCROLL_SPEED;
                } else if (InputHelper.scrolledUp) {
                    this.targetY -= Settings.SCROLL_SPEED;
                }
                if (InputHelper.justClickedLeft) {
                    this.grabbedScreen = true;
                    this.grabStartY = y - this.targetY;
                }
            } else if (InputHelper.isMouseDown) {
                this.targetY = y - this.grabStartY;
            } else {
                this.grabbedScreen = false;
            }
            this.scrollY = MathHelper.scrollSnapLerpSpeed(this.scrollY, this.targetY);
            resetScrolling();
            updateBarPosition();
        }
    }

    public void render(SpriteBatch sb) {
        if (this.isDone)
            return;
        this.row = -1;
        this.col = 0;
        sb.setColor(new Color(0.0F, 0.0F, 0.0F, 0.8F));
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        renderList(sb, TEXT[1], TEXT[2], this.relicGroup);
    }

    private void renderList(SpriteBatch sb, String msg, String desc, List<AbstractRelic> list) {
        this.row += 2;
        FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, msg, START_X - 50.0F * Settings.scale, this.scrollY + 4.0F * Settings.scale - SPACE * this.row, 99999.0F, 0.0F, Settings.GOLD_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.cardDescFont_N, desc, START_X - 50.0F * Settings.scale + FontHelper.getSmartWidth(FontHelper.buttonLabelFont, msg, 99999.0F, 0.0F), this.scrollY - 0.0F * Settings.scale - SPACE * this.row, 99999.0F, 0.0F, Settings.CREAM_COLOR);
        this.row++;
        this.col = 0;
        for (AbstractRelic r : list) {
            if (this.col == 10) {
                this.col = 0;
                this.row++;
            }
            r.currentX = START_X + SPACE * this.col;
            r.currentY = this.scrollY - SPACE * this.row;
            r.render(sb, false, Settings.TWO_THIRDS_TRANSPARENT_BLACK_COLOR);
            this.col++;
        }
    }

    private void resetScrolling() {
        if (this.targetY < this.scrollLowerBound) {
            this.targetY = MathHelper.scrollSnapLerpSpeed(this.targetY, this.scrollLowerBound);
        } else if (this.targetY > this.scrollUpperBound) {
            this.targetY = MathHelper.scrollSnapLerpSpeed(this.targetY, this.scrollUpperBound);
        }
    }

    private void updateBarPosition() {
        if (!CardCrawlGame.relicPopup.isOpen) {
            float percent = MathHelper.percentFromValueBetween(this.scrollLowerBound, this.scrollUpperBound, this.scrollY);
            this.scrollBar.parentScrolledToPercent(percent);
        }
    }

    public void scrolledUsingBar(float newPercent) {
        float newPosition = MathHelper.valueFromPercentBetween(this.scrollLowerBound, this.scrollUpperBound, newPercent);
        this.scrollY = newPosition;
        this.targetY = newPosition;
        updateBarPosition();
    }
}

