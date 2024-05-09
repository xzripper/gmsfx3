// Cached SFX Buffer. Java GMSFX3 Wrapper.

package jgmsfx3;

import java.util.ArrayList;

/**
 * Cached SFX Buffer.
 */
public class CachedSFXBuffer {
    protected ArrayList<String> buffer = new ArrayList<>();

    /**
     * Cache SFX.
     */
    public void cacheSFX(String sfx) {
        buffer.add(sfx);
    }

    /**
     * Remove SFX from buffer.
     */
    public void removeSFX(String sfx) {
        buffer.remove(sfx);
    }

    /**
     * Is SFX cached.
     */
    public boolean isCached(String sfx) {
        return buffer.contains(sfx);
    }

    /**
     * Get buffer.
     */
    public String[] getBuffer() {
        return buffer.toArray(new String[] {});
    }

    /**
     * Get buffer size.
     */
    public int getBufferSize() {
        return buffer.size();
    }
}
