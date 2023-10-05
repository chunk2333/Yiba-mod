package patchs;
//SoundPlay

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.audio.SoundInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SoundPlay {
    private static final Logger logger = LogManager.getLogger(SoundPlay.class.getName());

    private HashMap<String, Sfx> map = new HashMap<>();

    private ArrayList<SoundInfo> fadeOutList = new ArrayList<>();

    private static final String SFX_DIR = "audio/";

    public SoundPlay() {
        long startTime = System.currentTimeMillis();
        Settings.SOUND_VOLUME = Settings.soundPref.getFloat("Sound Volume", 0.5F);
        //this.map.put("AMBIANCE_BOTTOM", load("SOTE_Level1_Ambience_v6.ogg"));
        this.map.put("HomoVoice", load("HomoVoice.ogg"));
        logger.info("自定音频：音量: " + Settings.SOUND_VOLUME);
        logger.info("自定音频Loaded " + this.map.size() + " Sound Effects");
        logger.info("自定音频SFX load time: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    private Sfx load(String filename) {
        return load(filename, false);
    }

    private Sfx load(String filename, boolean preload) {
        return new Sfx("audio/" + filename, preload);
    }

    public void update() {
        for (Iterator<SoundInfo> i = this.fadeOutList.iterator(); i.hasNext(); ) {
            SoundInfo e = i.next();
            e.update();
            Sfx sfx = this.map.get(e.name);
            if (sfx != null) {
                if (e.isDone) {
                    sfx.stop(e.id);
                    i.remove();
                    continue;
                }
                sfx.setVolume(e.id, Settings.SOUND_VOLUME * Settings.MASTER_VOLUME * e.volumeMultiplier);
            }
        }
    }

    public void preload(String key) {
        if (this.map.containsKey(key)) {
            logger.info("Preloading: " + key);
            long id = ((Sfx)this.map.get(key)).play(0.0F);
            ((Sfx)this.map.get(key)).stop(id);
        } else {
            logger.info("Missing: " + key);
        }
    }

    public long play(String key, boolean useBgmVolume) {
        if (CardCrawlGame.MUTE_IF_BG && Settings.isBackgrounded)
            return 0L;
        if (this.map.containsKey(key)) {
            if (useBgmVolume)
                return ((Sfx)this.map.get(key)).play(Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME);
            return ((Sfx)this.map.get(key)).play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME);
        }
        logger.info("Missing: " + key);
        return 0L;
    }

    public long play(String key) {
        if (CardCrawlGame.MUTE_IF_BG && Settings.isBackgrounded)
            return 0L;
        return play(key, false);
    }

    public long play(String key, float pitchVariation) {
        if (CardCrawlGame.MUTE_IF_BG && Settings.isBackgrounded)
            return 0L;
        if (this.map.containsKey(key))
            return ((Sfx)this.map.get(key)).play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME, 1.0F +

                    MathUtils.random(-pitchVariation, pitchVariation), 0.0F);
        logger.info("Missing: " + key);
        return 0L;
    }

    public long playA(String key, float pitchAdjust) {
        if (CardCrawlGame.MUTE_IF_BG && Settings.isBackgrounded)
            return 0L;
        if (this.map.containsKey(key))
            return ((Sfx)this.map.get(key)).play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME, 1.0F + pitchAdjust, 0.0F);
        logger.info("Missing: " + key);
        return 0L;
    }

    public long playV(String key, float volumeMod) {
        if (CardCrawlGame.MUTE_IF_BG && Settings.isBackgrounded)
            return 0L;
        if (this.map.containsKey(key))
            return ((Sfx)this.map.get(key)).play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME * volumeMod, 1.0F, 0.0F);
        logger.info("Missing: " + key);
        return 0L;
    }

    public long playAV(String key, float pitchAdjust, float volumeMod) {
        if (CardCrawlGame.MUTE_IF_BG && Settings.isBackgrounded)
            return 0L;
        if (this.map.containsKey(key))
            return ((Sfx)this.map.get(key)).play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME * volumeMod, 1.0F + pitchAdjust, 0.0F);
        logger.info("Missing: " + key);
        return 0L;
    }

    public long playAndLoop(String key) {
        if (this.map.containsKey(key))
            return ((Sfx)this.map.get(key)).loop(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME);
        logger.info("Missing: " + key);
        return 0L;
    }

    public long playAndLoop(String key, float volume) {
        if (this.map.containsKey(key))
            return ((Sfx)this.map.get(key)).loop(volume);
        logger.info("Missing: " + key);
        return 0L;
    }

    public void adjustVolume(String key, long id, float volume) {
        ((Sfx)this.map.get(key)).setVolume(id, volume);
    }

    public void adjustVolume(String key, long id) {
        ((Sfx)this.map.get(key)).setVolume(id, Settings.SOUND_VOLUME * Settings.MASTER_VOLUME);
    }

    public void fadeOut(String key, long id) {
        this.fadeOutList.add(new SoundInfo(key, id));
    }

    public void stop(String key, long id) {
        ((Sfx)this.map.get(key)).stop(id);
    }

    public void stop(String key) {
        if (this.map.get(key) != null)
            ((Sfx)this.map.get(key)).stop();
    }
}


