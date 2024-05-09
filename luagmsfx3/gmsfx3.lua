-- Lua GMSFX3 Wrapper.

---@alias SFXName string
---@alias GMSFX3DownloadResult string | table

--- GMSFX3 Version.
---@type string
GMSFX3_VERSION = "1.1.0"

--- Sounds Base Version URL.
---@type string
SOUNDS_BASE_VERSION_URL = "https://raw.githubusercontent.com/xzripper/gmsfx3-sounds/main/sounds/BASE-VERSION"

--- Generate SFX URL.
---@param sfxname SFXName
---@return string
function gmsfx3_sfx_url(sfxname)
    return string.format("https://github.com/xzripper/gmsfx3-sounds/blob/main/sounds/%s.wav?raw=true", sfxname)
end

--- Get SFX.
---@param sfxname SFXName
---@return GMSFX3DownloadResult
function gmsfx3_get(sfxname)
    local temporaryDirectory = os.getenv("TMPDIR") or os.getenv("TEMP") or os.getenv("TMP") or "/tmp"

    local tempDirLastChar_ = string.sub(temporaryDirectory, #temporaryDirectory, #temporaryDirectory)

    local pathSplit = ""

    if tempDirLastChar_ ~= "\\" or tempDirLastChar_ ~= "/" then
        pathSplit = package.config:sub(1, 1)
    end

    local outPath = string.format("%s%s%s_%s.wav", temporaryDirectory,  pathSplit, sfxname, math.random(1000, 9999))

    local code = tonumber(io.popen(string.format("curl -s -L -o %s %s -w '%s{http_code}'", outPath, gmsfx3_sfx_url(sfxname), "%")):read("*a"):match("%d+"))

    if code and code == 200 and code < 300 then
        return outPath
    else
        return {string.format('Failed to download SFX: `%s`.', sfxname), sfxname}
    end
end

--- Clear cached SFX. Not implemented in Lua (temporary).
---@return nil
function gmsfx3_clear_cached_sfx() end

--- Get sounds base version. Not implemented in Lua (temporary).
---@return nil
function gmsfx3_sounds_base_version() end

--- Get GMSFX3 version.
---@return string
function gmsfx3_version()
    return GMSFX3_VERSION
end
