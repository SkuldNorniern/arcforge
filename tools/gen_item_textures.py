"""Project wrapper around utils/pixel_art.py: generates Arcforge item/block textures."""
import os
import sys

from PIL import Image, ImageDraw

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "utils"))
from pixel_art import new_canvas, save  # noqa: E402

ITEM_DIR = os.path.join(
    os.path.dirname(__file__), "..", "src", "main", "resources", "assets", "arcforge", "textures", "item"
)
BLOCK_DIR = os.path.join(
    os.path.dirname(__file__), "..", "src", "main", "resources", "assets", "arcforge", "textures", "block"
)
GUI_DIR = os.path.join(
    os.path.dirname(__file__), "..", "src", "main", "resources", "assets", "arcforge", "textures", "gui"
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


def gen_pull_rune():
    img = gen_rune_plate()
    d = ImageDraw.Draw(img)
    color = (150, 110, 235, 255)
    d.polygon([(7, 4), (8, 6), (6, 6)], fill=color)
    d.polygon([(11, 7), (9, 8), (9, 6)], fill=color)
    d.polygon([(7, 11), (6, 9), (8, 9)], fill=color)
    d.polygon([(3, 7), (5, 6), (5, 8)], fill=color)
    d.point((7, 7), fill=(210, 190, 255, 255))
    return img


def gen_push_rune():
    img = gen_rune_plate()
    d = ImageDraw.Draw(img)
    color = (70, 190, 200, 255)
    d.polygon([(7, 2), (8, 4), (6, 4)], fill=color)
    d.polygon([(13, 7), (11, 6), (11, 8)], fill=color)
    d.polygon([(7, 13), (6, 11), (8, 11)], fill=color)
    d.polygon([(1, 7), (3, 6), (3, 8)], fill=color)
    d.point((7, 7), fill=(170, 230, 235, 255))
    return img


def gen_touch_glyph():
    img = gen_rune_plate()
    d = ImageDraw.Draw(img)
    d.polygon([(5, 4), (11, 7), (5, 10)], outline=(200, 200, 215, 255))
    d.point((10, 7), fill=(235, 235, 250, 255))
    return img


def gen_self_glyph():
    img = gen_rune_plate()
    d = ImageDraw.Draw(img)
    d.ellipse([5, 5, 10, 10], outline=(200, 200, 215, 255))
    d.point((7, 7), fill=(235, 235, 250, 255))
    return img


def gen_area_glyph():
    img = gen_rune_plate()
    d = ImageDraw.Draw(img)
    d.ellipse([2, 2, 13, 13], outline=(200, 200, 215, 255))
    d.point((7, 7), fill=(235, 235, 250, 255))
    return img


def gen_block_sigil():
    img = gen_rune_plate()
    d = ImageDraw.Draw(img)
    color = (235, 190, 90, 255)
    d.rectangle([5, 5, 10, 10], outline=color)
    return img


def gen_item_sigil():
    img = gen_rune_plate()
    d = ImageDraw.Draw(img)
    color = (235, 190, 90, 255)
    d.polygon([(7, 4), (10, 7), (7, 10), (4, 7)], outline=color)
    d.point((7, 7), fill=(255, 225, 150, 255))
    return img


def gen_machine_sigil():
    img = gen_rune_plate()
    d = ImageDraw.Draw(img)
    color = (235, 190, 90, 255)
    d.ellipse([5, 5, 10, 10], outline=color)
    for x, y in [(7, 3), (7, 12), (3, 7), (12, 7)]:
        d.point((x, y), fill=color)
    return img


def gen_spell_assembler_base():
    img = new_canvas()
    d = ImageDraw.Draw(img)
    d.rectangle([0, 0, 15, 15], fill=(54, 38, 70, 255))
    d.rectangle([1, 1, 14, 14], outline=(168, 130, 58, 255), width=1)
    return img


def gen_spell_assembler_socket():
    img = new_canvas()
    d = ImageDraw.Draw(img)
    d.rectangle([0, 0, 15, 15], fill=(40, 26, 52, 255))
    d.ellipse([3, 3, 12, 12], outline=(168, 130, 58, 255), width=1)
    d.ellipse([5, 5, 10, 10], fill=(124, 77, 196, 255), outline=(190, 158, 230, 255))
    return img


def gen_spell_assembler_glyph():
    img = new_canvas()
    d = ImageDraw.Draw(img)
    d.rectangle([0, 0, 15, 15], fill=(70, 50, 90, 255))
    d.polygon([(7, 2), (12, 7), (7, 12), (2, 7)], outline=(190, 158, 230, 255))
    d.point((7, 7), fill=(230, 210, 255, 255))
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


def gen_rune_scriber_desk():
    img = new_canvas()
    d = ImageDraw.Draw(img)
    d.rectangle([0, 0, 15, 15], fill=(92, 62, 38, 255))
    for y in range(0, 16, 4):
        d.line([(0, y), (15, y)], fill=(74, 48, 28, 255))
    return img


def gen_rune_scriber_tablet():
    img = new_canvas()
    d = ImageDraw.Draw(img)
    d.rectangle([0, 0, 15, 15], fill=(46, 28, 64, 255))
    d.polygon([(7, 3), (12, 7), (7, 11), (2, 7)], outline=(190, 158, 230, 255))
    d.point((7, 7), fill=(230, 210, 255, 255))
    return img


def gen_rune_scriber_stylus():
    img = new_canvas()
    d = ImageDraw.Draw(img)
    d.rectangle([0, 0, 15, 15], fill=(168, 130, 58, 255))
    d.rectangle([2, 2, 13, 13], outline=(214, 176, 96, 255), width=1)
    return img


def _draw_slot(d, x, y, size=18):
    d.rectangle([x, y, x + size - 1, y + size - 1], fill=(139, 139, 139, 255), outline=(55, 55, 55, 255))
    d.rectangle([x + 1, y + 1, x + size - 2, y + size - 2], outline=(139, 139, 139, 255))


def gen_spell_assembler_gui():
    img = Image.new("RGBA", (176, 166), (198, 198, 198, 255))
    d = ImageDraw.Draw(img)
    d.rectangle([0, 0, 175, 165], outline=(55, 55, 55, 255))

    slot_positions = [
        (8, 53),  # core
        (26, 17), (44, 17), (62, 17),  # delivery, effect, target
        (80, 17), (98, 17), (116, 17), (134, 17),  # modifiers 1-4
        (152, 53),  # output
    ]
    for x, y in slot_positions:
        _draw_slot(d, x, y)

    for row in range(3):
        for col in range(9):
            _draw_slot(d, 8 + col * 18, 84 + row * 18)
    for col in range(9):
        _draw_slot(d, 8 + col * 18, 142)

    return img


def main():
    os.makedirs(ITEM_DIR, exist_ok=True)
    os.makedirs(BLOCK_DIR, exist_ok=True)
    os.makedirs(GUI_DIR, exist_ok=True)

    items = {
        "raw_mana_crystal": gen_raw_mana_crystal,
        "refined_mana_crystal": gen_refined_mana_crystal,
        "runic_dust": gen_runic_dust,
        "brass_frame": gen_brass_frame,
        "arcana_coil": gen_arcana_coil,
        "rune_plate": gen_rune_plate,
        "heat_rune": gen_heat_rune,
        "light_rune": gen_light_rune,
        "pull_rune": gen_pull_rune,
        "push_rune": gen_push_rune,
        "touch_glyph": gen_touch_glyph,
        "self_glyph": gen_self_glyph,
        "area_glyph": gen_area_glyph,
        "block_sigil": gen_block_sigil,
        "item_sigil": gen_item_sigil,
        "machine_sigil": gen_machine_sigil,
        "basic_spell_core": gen_basic_spell_core,
    }
    for name, gen in items.items():
        save(gen(), os.path.join(ITEM_DIR, f"{name}.png"))

    save(gen_raw_mana_crystal_ore(), os.path.join(BLOCK_DIR, "raw_mana_crystal_ore.png"))
    save(gen_arcana_cell(), os.path.join(BLOCK_DIR, "arcana_cell.png"))
    save(gen_rune_scriber_desk(), os.path.join(BLOCK_DIR, "rune_scriber_desk.png"))
    save(gen_rune_scriber_tablet(), os.path.join(BLOCK_DIR, "rune_scriber_tablet.png"))
    save(gen_rune_scriber_stylus(), os.path.join(BLOCK_DIR, "rune_scriber_stylus.png"))
    save(gen_spell_assembler_base(), os.path.join(BLOCK_DIR, "spell_assembler_base.png"))
    save(gen_spell_assembler_socket(), os.path.join(BLOCK_DIR, "spell_assembler_socket.png"))
    save(gen_spell_assembler_glyph(), os.path.join(BLOCK_DIR, "spell_assembler_glyph.png"))

    save(gen_spell_assembler_gui(), os.path.join(GUI_DIR, "spell_assembler.png"))


if __name__ == "__main__":
    main()
