// GMSFX3 Download Result. Java GMSFX3 Wrapper.

package jgmsfx3;

/**
 * Download result.
 */
public class GMSFX3DownloadResult {
    protected String sfxPath;

    protected String[] error;

    /**
     * Initialize download result.
     */
    public GMSFX3DownloadResult(String sfxPath_, String[] error_) {
        sfxPath = sfxPath_;

        error = error_;
    }

    /**
     * Get path to SFX.
     */
    public String getSFX() {
        return sfxPath;
    }

    /**
     * Get error (check <code>isSuccess</code>).
     */
    public String[] getError() {
        return error;
    }

    /**
     * Is SFX downloaded successfully.
     */
    public boolean isSuccess() {
        return sfxPath != null && error == null;
    }
}
