package pixelsorting;

import processing.core.PApplet;

public class ComparableColor implements Comparable<ComparableColor> {
	PApplet p;
	public int c;

	public ComparableColor(PApplet p, int c) {
		this.c = c;
		this.p = p;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return c + "";
	}

	/*
	 * /////////////////////// // p.hue SORT // /////////////////////// public
	 * int public int compareTo(ComparableColor cc) { int thisColor = this.c;
	 * int otherColor = cc.c;
	 * 
	 * if (p.hue(thisColor) > p.hue(otherColor)) return 1; else if
	 * (p.hue(thisColor) < p.hue(otherColor)) return -1; else { return 0; } }
	 */

	/*
	 * /////////////////////// // BHS SORT // ///////////////////////
	 * compareTo(ComparableColor cc) { color thisColor=this.c; color
	 * otherColor=cc.c; if (p.brightness(thisColor)>p.brightness(otherColor))
	 * return 1; else if (p.brightness(thisColor)<p.brightness(otherColor))
	 * return -1; else { if (p.hue(thisColor)>p.hue(otherColor)) return 1; else
	 * if (p.hue(thisColor)<p.hue(otherColor)) return -1; else { if
	 * (p.saturation(thisColor)>p.saturation(otherColor)) return 1; else if
	 * (p.saturation(thisColor)<p.saturation(otherColor)) return - 1; else {
	 * return 0; } } } }
	 */

	/////////////////////// // HSB SORT // ///////////////////////
	public int compareTo(ComparableColor cc) {
		int thisColor = this.c;
		int otherColor = cc.c;

		if (p.hue(thisColor) > p.hue(otherColor))
			return 1;
		else if (p.hue(thisColor) < p.hue(otherColor))
			return -1;
		else {
			if (p.saturation(thisColor) > p.saturation(otherColor))
				return 1;
			else if (p.saturation(thisColor) < p.saturation(otherColor))
				return -1;
			else {
				if (p.red(thisColor) > p.red(otherColor))
					return 1;
				else if (p.red(thisColor) < p.red(otherColor))
					return -1;
				else {
					return 0;
				}
			}
		}
	}
	

	/*
	 * /////////////////////// // RBG SORT // /////////////////////// public int
	 * compareTo(ComparableColor cc) { int thisColor = this.c; int otherColor =
	 * cc.c; if (p.red(thisColor) > p.red(otherColor)) return 1; else if
	 * (p.red(thisColor) < p.red(otherColor)) return -1; else { if
	 * (p.green(thisColor) > p.green(otherColor)) return 1; else if
	 * (p.green(thisColor) < p.green(otherColor)) return -1; else if
	 * (p.blue(thisColor) > p.blue(otherColor)) return 1; else if
	 * (p.blue(thisColor) < p.blue(otherColor)) return -1; else { return 0; } }
	 * } }
	 */
}
