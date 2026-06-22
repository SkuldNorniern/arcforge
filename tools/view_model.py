"""Project wrapper around utils/model_viewer.py: previews Arcforge block models."""
import os
import sys

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "utils"))
from model_viewer import render_model  # noqa: E402

ASSETS_ROOT = os.path.join(os.path.dirname(__file__), "..", "src", "main", "resources")
MODEL_DIR = os.path.join(ASSETS_ROOT, "assets", "arcforge", "models", "block")
PREVIEW_DIR = os.path.join(os.path.dirname(__file__), "model_previews")


def main():
    if len(sys.argv) < 2:
        print("usage: python3 view_model.py <block_model_name>")
        sys.exit(1)
    name = sys.argv[1]
    os.makedirs(PREVIEW_DIR, exist_ok=True)
    model_path = os.path.join(MODEL_DIR, f"{name}.json")
    out_path = os.path.join(PREVIEW_DIR, f"{name}.png")
    render_model(model_path, ASSETS_ROOT, out_path)


if __name__ == "__main__":
    main()
