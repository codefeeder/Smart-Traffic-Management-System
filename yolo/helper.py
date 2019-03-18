import matplotlib.pyplot as plt
import numpy as np
from matplotlib import patches, patheffects


def isdicom(fn):
    '''True if the fn points to a DICOM image'''
    fn = str(fn)
    if fn.endswith('.dcm'):
        return True
    # Dicom signature from the dicom spec.
    with open(fn, 'rb') as fh:
        fh.seek(0x80)
        return fh.read(4) == b'DICM'


def show_img(im, figsize=None, ax=None):
    if not ax: fig, ax = plt.subplots(figsize=figsize)
    ax.imshow(im)
    ax.get_xaxis().set_visible(False)
    ax.get_yaxis().set_visible(False)
    return ax


def draw_outline(o, lw):
    o.set_path_effects([patheffects.Stroke(
        linewidth=lw, foreground='black'), patheffects.Normal()])


def draw_rect(ax, b):
    patch = ax.add_patch(patches.Rectangle(b[:2], *b[-2:], fill=False, edgecolor='white', lw=1))
    draw_outline(patch, 3)


def bb_hw(a): return np.array([a[1], a[0], a[3] - a[1] + 1, a[2] - a[0] + 1])


def hw_bb(bb): return np.array([bb[1], bb[0], bb[3] + bb[1] - 1, bb[2] + bb[0] - 1])


def draw_im(im, ann):
    ax = show_img(im, figsize=(16, 8))
    for b in ann:
        b = bb_hw(b)
        draw_rect(ax, b)
