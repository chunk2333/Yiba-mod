package animation;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Vector;

public class GifDecoder {
    public static final int STATUS_OK = 0;

    public static final int STATUS_FORMAT_ERROR = 1;

    public static final int STATUS_OPEN_ERROR = 2;

    protected static final int MAX_STACK_SIZE = 4096;

    protected InputStream in;

    protected int status;

    protected int width;

    protected int height;

    protected boolean gctFlag;

    protected int gctSize;

    protected int loopCount = 1;

    protected int[] gct;

    protected int[] lct;

    protected int[] act;

    protected int bgIndex;

    protected int bgColor;

    protected int lastBgColor;

    protected int pixelAspect;

    protected boolean lctFlag;

    protected boolean interlace;

    protected int lctSize;

    protected int ix;

    protected int iy;

    protected int iw;

    protected int ih;

    protected int lrx;

    protected int lry;

    protected int lrw;

    protected int lrh;

    protected DixieMap image;

    protected DixieMap lastPixmap;

    protected byte[] block = new byte[256];

    protected int blockSize = 0;

    protected int dispose = 0;

    protected int lastDispose = 0;

    protected boolean transparency = false;

    protected int delay = 0;

    protected int transIndex;

    protected short[] prefix;

    protected byte[] suffix;

    protected byte[] pixelStack;

    protected byte[] pixels;

    protected Vector<GifFrame> frames;

    protected int frameCount;

    private static class DixieMap extends Pixmap {
        DixieMap(int w, int h, Pixmap.Format f) {
            super(w, h, f);
        }

        DixieMap(int[] data, int w, int h, Pixmap.Format f) {
            super(w, h, f);
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    int pxl_ARGB8888 = data[x + y * w];
                    int pxl_RGBA8888 = pxl_ARGB8888 >> 24 & 0xFF | pxl_ARGB8888 << 8 & 0xFFFFFF00;
                    drawPixel(x, y, pxl_RGBA8888);
                }
            }
        }

        void getPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height) {
            ByteBuffer bb = getPixels();
            for (int k = y; k < y + height; k++) {
                int _offset = offset;
                for (int l = x; l < x + width; l++) {
                    int pxl = bb.getInt(4 * (l + k * width));
                    pixels[_offset++] = pxl >> 8 & 0xFFFFFF | pxl << 24 & 0xFF000000;
                }
                offset += stride;
            }
        }
    }

    private static class GifFrame {
        public GifDecoder.DixieMap image;

        public int delay;

        public GifFrame(GifDecoder.DixieMap im, int del) {
            this.image = im;
            this.delay = del;
        }
    }

    public int getDelay(int n) {
        this.delay = -1;
        if (n >= 0 && n < this.frameCount)
            this.delay = ((GifFrame)this.frames.elementAt(n)).delay;
        return this.delay;
    }

    public int getFrameCount() {
        return this.frameCount;
    }

    public Pixmap getPixmap() {
        return getFrame(0);
    }

    public int getLoopCount() {
        return this.loopCount;
    }

    protected void setPixels() {
        int[] dest = new int[this.width * this.height];
        if (this.lastDispose > 0) {
            if (this.lastDispose == 3) {
                int n = this.frameCount - 2;
                if (n > 0) {
                    this.lastPixmap = getFrame(n - 1);
                } else {
                    this.lastPixmap = null;
                }
            }
            if (this.lastPixmap != null) {
                this.lastPixmap.getPixels(dest, 0, this.width, 0, 0, this.width, this.height);
                if (this.lastDispose == 2) {
                    int c = 0;
                    if (!this.transparency)
                        c = this.lastBgColor;
                    for (int j = 0; j < this.lrh; j++) {
                        int n1 = (this.lry + j) * this.width + this.lrx;
                        int n2 = n1 + this.lrw;
                        for (int k = n1; k < n2; k++)
                            dest[k] = c;
                    }
                }
            }
        }
        int pass = 1;
        int inc = 8;
        int iline = 0;
        for (int i = 0; i < this.ih; i++) {
            int line = i;
            if (this.interlace) {
                if (iline >= this.ih) {
                    pass++;
                    switch (pass) {
                        case 2:
                            iline = 4;
                            break;
                        case 3:
                            iline = 2;
                            inc = 4;
                            break;
                        case 4:
                            iline = 1;
                            inc = 2;
                            break;
                    }
                }
                line = iline;
                iline += inc;
            }
            line += this.iy;
            if (line < this.height) {
                int k = line * this.width;
                int dx = k + this.ix;
                int dlim = dx + this.iw;
                if (k + this.width < dlim)
                    dlim = k + this.width;
                int sx = i * this.iw;
                while (dx < dlim) {
                    int index = this.pixels[sx++] & 0xFF;
                    int c = this.act[index];
                    if (c != 0)
                        dest[dx] = c;
                    dx++;
                }
            }
        }
        this.image = new DixieMap(dest, this.width, this.height, Pixmap.Format.RGBA8888);
    }

    public DixieMap getFrame(int n) {
        if (this.frameCount <= 0)
            return null;
        n %= this.frameCount;
        return ((GifFrame)this.frames.elementAt(n)).image;
    }

    public int read(InputStream is) {
        init();
        if (is != null) {
            this.in = is;
            readHeader();
            if (!err()) {
                readContents();
                if (this.frameCount < 0)
                    this.status = 1;
            }
        } else {
            this.status = 2;
        }
        try {
            is.close();
        } catch (Exception exception) {}
        return this.status;
    }

    protected void decodeBitmapData() {
        int nullCode = -1;
        int npix = this.iw * this.ih;
        if (this.pixels == null || this.pixels.length < npix)
            this.pixels = new byte[npix];
        if (this.prefix == null)
            this.prefix = new short[4096];
        if (this.suffix == null)
            this.suffix = new byte[4096];
        if (this.pixelStack == null)
            this.pixelStack = new byte[4097];
        int data_size = read();
        int clear = 1 << data_size;
        int end_of_information = clear + 1;
        int available = clear + 2;
        int old_code = nullCode;
        int code_size = data_size + 1;
        int code_mask = (1 << code_size) - 1;
        int code;
        for (code = 0; code < clear; code++) {
            this.prefix[code] = 0;
            this.suffix[code] = (byte)code;
        }
        int bi = 0, pi = bi, top = pi, first = top, count = first, bits = count, datum = bits;
        int i;
        for (i = 0; i < npix; ) {
            if (top == 0) {
                if (bits < code_size) {
                    if (count == 0) {
                        count = readBlock();
                        if (count <= 0)
                            break;
                        bi = 0;
                    }
                    datum += (this.block[bi] & 0xFF) << bits;
                    bits += 8;
                    bi++;
                    count--;
                    continue;
                }
                code = datum & code_mask;
                datum >>= code_size;
                bits -= code_size;
                if (code > available || code == end_of_information)
                    break;
                if (code == clear) {
                    code_size = data_size + 1;
                    code_mask = (1 << code_size) - 1;
                    available = clear + 2;
                    old_code = nullCode;
                    continue;
                }
                if (old_code == nullCode) {
                    this.pixelStack[top++] = this.suffix[code];
                    old_code = code;
                    first = code;
                    continue;
                }
                int in_code = code;
                if (code == available) {
                    this.pixelStack[top++] = (byte)first;
                    code = old_code;
                }
                while (code > clear) {
                    this.pixelStack[top++] = this.suffix[code];
                    code = this.prefix[code];
                }
                first = this.suffix[code] & 0xFF;
                if (available >= 4096)
                    break;
                this.pixelStack[top++] = (byte)first;
                this.prefix[available] = (short)old_code;
                this.suffix[available] = (byte)first;
                available++;
                if ((available & code_mask) == 0 && available < 4096) {
                    code_size++;
                    code_mask += available;
                }
                old_code = in_code;
            }
            top--;
            this.pixels[pi++] = this.pixelStack[top];
            i++;
        }
        for (i = pi; i < npix; i++)
            this.pixels[i] = 0;
    }

    protected boolean err() {
        return (this.status != 0);
    }

    protected void init() {
        this.status = 0;
        this.frameCount = 0;
        this.frames = new Vector<>();
        this.gct = null;
        this.lct = null;
    }

    protected int read() {
        int curByte = 0;
        try {
            curByte = this.in.read();
        } catch (Exception e) {
            this.status = 1;
        }
        return curByte;
    }

    protected int readBlock() {
        this.blockSize = read();
        int n = 0;
        if (this.blockSize > 0) {
            try {
                int count = 0;
                while (n < this.blockSize) {
                    count = this.in.read(this.block, n, this.blockSize - n);
                    if (count == -1)
                        break;
                    n += count;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (n < this.blockSize)
                this.status = 1;
        }
        return n;
    }

    protected int[] readColorTable(int ncolors) {
        int nbytes = 3 * ncolors;
        int[] tab = null;
        byte[] c = new byte[nbytes];
        int n = 0;
        try {
            n = this.in.read(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (n < nbytes) {
            this.status = 1;
        } else {
            tab = new int[256];
            int i = 0;
            int j = 0;
            while (i < ncolors) {
                int r = c[j++] & 0xFF;
                int g = c[j++] & 0xFF;
                int b = c[j++] & 0xFF;
                tab[i++] = 0xFF000000 | r << 16 | g << 8 | b;
            }
        }
        return tab;
    }

    protected void readContents() {
        boolean done = false;
        while (!done && !err()) {
            String app;
            int i, code = read();
            switch (code) {
                case 44:
                    readBitmap();
                    continue;
                case 33:
                    code = read();
                    switch (code) {
                        case 249:
                            readGraphicControlExt();
                            continue;
                        case 255:
                            readBlock();
                            app = "";
                            for (i = 0; i < 11; i++)
                                app = app + (char)this.block[i];
                            if (app.equals("NETSCAPE2.0")) {
                                readNetscapeExt();
                                continue;
                            }
                            skip();
                            continue;
                        case 254:
                            skip();
                            continue;
                        case 1:
                            skip();
                            continue;
                    }
                    skip();
                    continue;
                case 59:
                    done = true;
                    continue;
            }
            this.status = 1;
        }
    }

    protected void readGraphicControlExt() {
        read();
        int packed = read();
        this.dispose = (packed & 0x1C) >> 2;
        if (this.dispose == 0)
            this.dispose = 1;
        this.transparency = ((packed & 0x1) != 0);
        this.delay = readShort() * 10;
        this.transIndex = read();
        read();
    }

    protected void readHeader() {
        String id = "";
        for (int i = 0; i < 6; i++)
            id = id + (char)read();
        if (!id.startsWith("GIF")) {
            this.status = 1;
            return;
        }
        readLSD();
        if (this.gctFlag && !err()) {
            this.gct = readColorTable(this.gctSize);
            this.bgColor = this.gct[this.bgIndex];
        }
    }

    protected void readBitmap() {
        this.ix = readShort();
        this.iy = readShort();
        this.iw = readShort();
        this.ih = readShort();
        int packed = read();
        this.lctFlag = ((packed & 0x80) != 0);
        this.lctSize = (int)Math.pow(2.0D, ((packed & 0x7) + 1));
        this.interlace = ((packed & 0x40) != 0);
        if (this.lctFlag) {
            this.lct = readColorTable(this.lctSize);
            this.act = this.lct;
        } else {
            this.act = this.gct;
            if (this.bgIndex == this.transIndex)
                this.bgColor = 0;
        }
        int save = 0;
        if (this.transparency) {
            save = this.act[this.transIndex];
            this.act[this.transIndex] = 0;
        }
        if (this.act == null)
            this.status = 1;
        if (err())
            return;
        decodeBitmapData();
        skip();
        if (err())
            return;
        this.frameCount++;
        this.image = new DixieMap(this.width, this.height, Pixmap.Format.RGBA8888);
        setPixels();
        this.frames.addElement(new GifFrame(this.image, this.delay));
        if (this.transparency)
            this.act[this.transIndex] = save;
        resetFrame();
    }

    protected void readLSD() {
        this.width = readShort();
        this.height = readShort();
        int packed = read();
        this.gctFlag = ((packed & 0x80) != 0);
        this.gctSize = 2 << (packed & 0x7);
        this.bgIndex = read();
        this.pixelAspect = read();
    }

    protected void readNetscapeExt() {
        do {
            readBlock();
            if (this.block[0] != 1)
                continue;
            int b1 = this.block[1] & 0xFF;
            int b2 = this.block[2] & 0xFF;
            this.loopCount = b2 << 8 | b1;
        } while (this.blockSize > 0 && !err());
    }

    protected int readShort() {
        return read() | read() << 8;
    }

    protected void resetFrame() {
        this.lastDispose = this.dispose;
        this.lrx = this.ix;
        this.lry = this.iy;
        this.lrw = this.iw;
        this.lrh = this.ih;
        this.lastPixmap = this.image;
        this.lastBgColor = this.bgColor;
        this.dispose = 0;
        this.transparency = false;
        this.delay = 0;
        this.lct = null;
    }

    protected void skip() {
        do {
            readBlock();
        } while (this.blockSize > 0 && !err());
    }

    public Animation<TextureRegion> getAnimation(Animation.PlayMode playMode) {
        int nrFrames = getFrameCount();
        Pixmap frame = getFrame(0);
        int width = frame.getWidth();
        int height = frame.getHeight();
        int vzones = (int)Math.sqrt(nrFrames);
        int hzones = vzones;
        for (; vzones * hzones < nrFrames; vzones++);
        Pixmap target = new Pixmap(width * hzones, height * vzones, Pixmap.Format.RGBA8888);
        int h;
        for (h = 0; h < hzones; h++) {
            for (int v = 0; v < vzones; v++) {
                int frameID = v + h * vzones;
                if (frameID < nrFrames) {
                    frame = getFrame(frameID);
                    target.drawPixmap(frame, h * width, v * height);
                }
            }
        }
        Texture texture = new Texture(target);
        Array<TextureRegion> texReg = new Array();
        for (h = 0; h < hzones; h++) {
            for (int v = 0; v < vzones; v++) {
                int frameID = v + h * vzones;
                if (frameID < nrFrames) {
                    TextureRegion tr = new TextureRegion(texture, h * width, v * height, width, height);
                    texReg.add(tr);
                }
            }
        }
        float frameDuration = getDelay(0);
        frameDuration /= 1000.0F;
        Animation<TextureRegion> result = new Animation(frameDuration, texReg, playMode);
        return result;
    }

    public static Animation<TextureRegion> loadGIFAnimation(Animation.PlayMode playMode, InputStream is) {
        GifDecoder gdec = new GifDecoder();
        gdec.read(is);
        return gdec.getAnimation(playMode);
    }

    public static Animation<TextureRegion> loadGIFAnimation(Animation.PlayMode playMode, InputStream is, GifAnimation animation) {
        GifDecoder gdec = new GifDecoder();
        gdec.read(is);
        animation.gdec = gdec;
        return gdec.getAnimation(playMode);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
