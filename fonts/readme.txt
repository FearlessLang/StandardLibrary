The fonts the GUI draws text with. They are packed into _base.jar next to the rt classes,
and rt/gui/Sk.java lists them in lookup order: a character is drawn with the first font
that has it, never with a font installed on the machine.

## Noto (SIL Open Font License 1.1, OFL-Noto.txt)
Hinted Regular TTFs, one file per script, from
https://github.com/notofonts/notofonts.github.io/tree/main/fonts/<Family>/hinted/ttf/
for example
https://github.com/notofonts/notofonts.github.io/raw/main/fonts/NotoSans/hinted/ttf/NotoSans-Regular.ttf
NotoSans covers Latin, Greek and Cyrillic; NotoSansSymbols, NotoSansSymbols2 and NotoSansMath
cover arrows, shapes, dingbats, box drawing and mathematics; Tibetan only exists as Serif.

## Noto Sans CJK SC (SIL Open Font License 1.1, OFL-NotoCJK.txt)
The full Simplified Chinese OTF: all of Han (URO and Extension A), Kana, Hangul and Bopomofo.
https://github.com/notofonts/noto-cjk/raw/main/Sans/OTF/SimplifiedChinese/NotoSansCJKsc-Regular.otf

## Twemoji Mozilla (graphics CC BY 4.0, code Apache 2.0, LICENSE-twemoji-colr.txt)
Colour emoji as COLRv0 vector glyphs, the one colour format every Skia backend renders.
https://github.com/mozilla/twemoji-colr/releases/download/v0.7.0/Twemoji.Mozilla.ttf
