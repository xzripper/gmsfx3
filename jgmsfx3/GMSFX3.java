// Java GMSFX3 Wrapper.

package jgmsfx3;

import java.net.URL;

import java.net.MalformedURLException;

import java.nio.channels.Channels;

import java.nio.channels.ReadableByteChannel;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.io.FileOutputStream;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.IOException;

import java.net.HttpURLConnection;

import java.util.Random;

/**
 * GMSFX3 Class.
 */
public class GMSFX3 {
    protected static Random random = new Random();

    protected static CachedSFXBuffer cachedSFX = new CachedSFXBuffer();

    /**
     * Version.
     */
    public static final String GMSFX3_VERSION = "v1.2.0";

    /**
     * Static base version. Use for more fast access to version, but may be older than actual version.
     */
    public static final String BASE_VERSION_STATIC = "10.05.2024";

    /**
     * Generate URL to SFX.
     */
    public static String gmsfx3GenerateUrl(String sfxName) {
        return String.format("https://github.com/xzripper/gmsfx3-sounds/blob/main/sounds/%s.wav?raw=true", sfxName);
    }

    /**
     * Get SFX (<code>GMSFX3DownloadResult</code>).
     */
    public static GMSFX3DownloadResult gmsfx3Get(String sfxName) {
        URL sfxUrl;

        try {
            sfxUrl = new URL(gmsfx3GenerateUrl(sfxName));
        } catch(MalformedURLException mException) {
            return new GMSFX3DownloadResult(null, new String[] {"MalformedURLException-URL", "Unknown exception source.", null});
        }

        ReadableByteChannel channel;

        try {
            channel = Channels.newChannel(sfxUrl.openStream());
        } catch(IOException ioException) {
            return new GMSFX3DownloadResult(null, new String[] {"IOException-URL.openStream", "Probably invalid SFX name.", null});
        }

        String temporaryDirectory = System.getProperty("java.io.tmpdir");

        String delimiter = "";

        if(!temporaryDirectory.endsWith("\\") && !temporaryDirectory.endsWith("/")) {
            delimiter = System.getProperty("file.separator");
        }

        String sfxPath = String.format(
            "%s%s%s_%d.wav",

            temporaryDirectory,
            delimiter,
            sfxName,
            random.nextInt(9999)
        );

        FileOutputStream output;

        try {
            output = new FileOutputStream(sfxPath);
        } catch(FileNotFoundException notFoundException) {
            return new GMSFX3DownloadResult(null, new String[] {"FileNotFoundException-FileOutputStream", "Unknown exception source.", null});
        }

        try {
            output.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);

            output.close();
        } catch(IOException ioException) {
            return new GMSFX3DownloadResult(null, new String[] {"IOException-transferFrom/close", "Unknown exception source.", null});
        }

        cachedSFX.cacheSFX(sfxPath);

        return new GMSFX3DownloadResult(sfxPath, null);
    }

    /**
     * Clear cached SFX.
     */
    public static void gmsfx3ClearCachedSFX() {
        for(String sfxPath : cachedSFX.getBuffer()) {
            new File(sfxPath).delete();

            cachedSFX.removeSFX(sfxPath);
        }
    }

    /**
     * Get cached SFX.
     */
    public static CachedSFXBuffer getCachedSFXBuffer() {
        return cachedSFX;
    }

    /**
     * Get sounds base version. Use <code>gmsfx3StaticBaseVersion</code> for faster access to base version.
     */
    public static String gmsfx3BaseVersion() {
        StringBuilder GETOutput = new StringBuilder();

        HttpURLConnection connection;

        try {
            connection = (HttpURLConnection) new URL("https://raw.githubusercontent.com/xzripper/gmsfx3-sounds/main/sounds/BASE-VERSION").openConnection();

            connection.setRequestMethod("GET");

            try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                for(String line; (line = reader.readLine()) != null;) {
                    GETOutput.append(line);
                }
            }

            return GETOutput.toString();
        } catch(IOException ioException) {
            return "undefined";
        }
    }

    /**
     * Get sounds base static version. Use for more fast access to version, but may be older than actual version.
     */
    public static String gmsfx3StaticBaseVersion() {
        return BASE_VERSION_STATIC;
    }

    /**
     * Get GMSFX3 version.
     */
    public static String gmsfx3Version() {
        return GMSFX3_VERSION;
    }
}
