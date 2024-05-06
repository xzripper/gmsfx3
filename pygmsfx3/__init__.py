"""Python GMSFX3 Wrapper."""

from pywebloader import PyLoader

from requests import get

from warnings import catch_warnings

from typing import Union


GMSFX3_VERSION: str = 'v1.0.0'
"""GMSFX3 Version."""

SOUNDS_BASE_VERSION_URL: str = 'https://raw.githubusercontent.com/xzripper/gmsfx3-sounds/main/sounds/BASE-VERSION'
"""Sounds Base Version URL."""

SFXName = str
"""SFX Name."""

GMSFX3DownloadResult = Union[str, tuple[str, str]]
"""`gmsfx3_download` return."""

def gmsfx3_sfx_url(sfxname: SFXName) -> str:
    """Generate SFX URL."""
    return f'https://github.com/xzripper/gmsfx3-sounds/blob/main/sounds/{sfxname}.wav?raw=true'

def gmsfx3_download(sfxname: SFXName) -> GMSFX3DownloadResult:
    """Download SFX."""
    with catch_warnings(record=True) as warning:
        webfile = PyLoader.webfile(gmsfx3_sfx_url(sfxname), 'wav')

        if warning:
            return (f'Failed to download SFX: `{sfxname}`.', sfxname)

    return webfile[0]

def gmsfx3_clear_cached_sfx() -> None:
    """Clear cached SFX."""
    PyLoader.clear_temp_files()

def gmsfx3_version() -> str:
    """Get GMSFX3 version."""
    return GMSFX3_VERSION

def gmsfx3_sounds_base_version() -> str:
    """Get sounds base version."""
    return get(SOUNDS_BASE_VERSION_URL).content.decode()[:-1]
