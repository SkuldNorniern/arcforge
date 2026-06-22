"""Project wrapper around utils/pixel_art.py: generates Arcforge item/block textures."""
import os
import sys

from PIL import ImageDraw

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "utils"))
from pixel_art import new_canvas, save  # noqa: E402

ITEM_DIR = os.path.join(
    os.path.dirname(__file__), "..", "src", "main", "resources", "assets", "arcforge", "textures", "item"
)
BLOCK_DIR = os.path.join(
    os.path.dirname(__file__), "..", "src", "main", "resources", "assets", "arcforge", "textures", "block"
)

OUTLINE = (35, 20, 45, 255)


def gen_raw_mana_crystal():
    img = new_canvas()
    d = ImageDraw.Draw(img)
    d.polygon([(7, 2), (10, 5), (9, 13), (6, 13), (5, 5)], fill=(124, 77, 196, 255), outline=OUTLINE)
    d.line([(7, 2), (7, 13)], fill=(176, 139, 230, 255))
    return img


def gen_refined_mana_crystal():
    img = new_canvas()
    d = ImageDraw.Draw(img)
    d.polygon([(8, 1), (12, 6), (8, 14), (4, 6)], fill=(94, 173, 235, 255), outline=OUTLINE)
    d.polygon([(8, 1), (10, 6), (8, 14), (8, 6)], fill=(168, 222, 250, 255))
    return img


def gen_runic_dust():
    img = new_canvas()
    d = ImageDraw.Draw(img)
    dots = [(3, 4), (6, 2), (9, 5), (12, 3), (4, 9), (8, 10), (11, 8), (6, 12), (10, 12), (2, 11)]
    for x, y in dots:
        d.point((x, y), fill=(190, 158, 230, 255))
        d.point((x + 1, y), fill=(150, 110, 200, 255))
    return img


def gen_brass_frame():
    img = new_canvas()
    d = ImageDraw.Draw(img)
    d.rectangle([2, 2, 13, 13], outline=(168, 130, 58, 255), width=2)
    d.rectangle([4, 4, 11, 11], outline=(214, 176, 96, 255), width=1)
    return img


def gen_arcana_coil():
    img = new_canvas()
    d = ImageDraw.Draw(img)
    for i, y in enumerate(range(2, 14, 3)):
        color = (196, 130, 70, 255) if i % 2 == 0 else (224, 160, 96, 255)
        d.ellipse([3, y, 12, y + 2], outline=color, width=2)
    return img


def gen_rune_plate():
    img = new_canvas()
    d = ImageDraw.Draw(img)
    d.rectangle([2, 2, 13, 13], fill=(120, 120, 130, 255), outline=OUTLINE)
    d.polygon([(7, 4), (11, 7), (7, 11), (3, 7)], outline=(200, 200, 210, 255))
    return img


def gen_heat_rune():
    img = gen_rune_plate()
    d = ImageDraw.Draw(img)
    d.polygon([(7, 4), (11, 7), (7, 11), (3, 7)], outline=(235, 110, 40, 255))
    d.point((7, 7), fill=(255, 170, 60, 255))
    return img


def gen_light_rune():
    img = gen_rune_plate()
    d = ImageDraw.Draw(img)
    d.polygon([(7, 4), (11, 7), (7, 11), (3, 7)], outline=(245, 230, 140, 255))
    d.point((7, 7), fill=(255, 250, 210, 255))
    return img


def gen_basic_spell_core():
    img = new_canvas()
    d = ImageDraw.Draw(img)
    d.ellipse([3, 3, 12, 12], outline=(168, 130, 58, 255), width=2)
    d.ellipse([5, 5, 10, 10], fill=(124, 77, 196, 255), outline=(176, 139, 230, 255))
    return img


def gen_raw_mana_crystal_ore():
    img = new_canvas()
    d = ImageDraw.Draw(img)
    d.rectangle([0, 0, 15, 15], fill=(110, 110, 116, 255))
    for x, y in [(2, 3), (12, 2), (5, 8), (10, 11), (3, 13), (13, 7)]:
        d.point((x, y), fill=(124, 77, 196, 255))
        d.point((x + 1, y), fill=(176, 139, 230, 255))
    return img


def gen_arcana_cell():
    img = new_canvas()
    d = ImageDraw.Draw(img)
    d.rectangle([0, 0, 15, 15], fill=(96, 72, 40, 255))
    d.rectangle([1, 1, 14, 14], outline=(168, 130, 58, 255), width=1)
    d.rectangle([3, 3, 12, 12], fill=(94, 173, 235, 255), outline=(214, 176, 96, 255), width=1)
    d.rectangle([5, 5, 10, 10], fill=(168, 222, 250, 255))
    d.rectangle([7, 7, 8, 8], fill=(230, 245, 255, 255))
    return img


def main():
    os.makedirs(ITEM_DIR, exist_ok=True)
    os.makedirs(BLOCK_DIR, exist_ok=True)

    items = {
        "raw_mana_crystal": gen_raw_mana_crystal,
        "refined_mana_crystal": gen_refined_mana_crystal,
        "runic_dust": gen_runic_dust,
        "brass_frame": gen_brass_frame,
        "arcana_coil": gen_arcana_coil,
        "rune_plate": gen_rune_plate,
        "heat_rune": gen_heat_rune,
        "light_rune": gen_light_rune,
        "basic_spell_core": gen_basic_spell_core,
    }
    for name, gen in items.items():
        save(gen(), os.path.join(ITEM_DIR, f"{name}.png"))

    save(gen_raw_mana_crystal_ore(), os.path.join(BLOCK_DIR, "raw_mana_crystal_ore.png"))
    save(gen_arcana_cell(), os.path.join(BLOCK_DIR, "arcana_cell.png"))


if __name__ == "__main__":
    main()
