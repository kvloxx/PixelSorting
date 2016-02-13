package pixelsorting;

import java.util.Comparator;

import processing.core.PApplet;

public class ComparableColor{
	static PApplet p;
	public int c;
	// public final Comparator<ComparableColor> COMP_HUE, COMP_BRI, COMP_R,
	// COMP_G, COMP_B;
	
	public static <T> Comparator<T> newMultiComp(Comparator<T> comp1, Comparator<T> comp2, Comparator<T> comp3){
		Comparator<T> res=(T o1, T o2) ->{
			int r1=comp1.compare(o1,o2);
			
			if(r1!=0)
				return r1;
			else
			{
				int r2=comp2.compare(o1,o2);
				
				if(r2!=0)
					return r2;
				else
				{
					return comp3.compare(o1, o2);
				}
			}
		};
		return res;
	}
	
/*	public int comp(Runnable test, T o1, T o2){
		if (p.hue(thisColor) > p.hue(otherColor))
			return 1;
		else if (p.hue(thisColor) < p.hue(otherColor))
			return -1;
		else 
			return 0;
	}*/

	public final static Comparator<ComparableColor> COMP_HUE = (ComparableColor c1, ComparableColor c2) -> {
		int thisColor = c1.c;
		int otherColor = c2.c;
		if (p.hue(thisColor) > p.hue(otherColor))
			return 1;
		else if (p.hue(thisColor) < p.hue(otherColor))
			return -1;
		else 
			return 0;
	};
	public final static Comparator<ComparableColor> COMP_SAT = (ComparableColor c1, ComparableColor c2) -> {
		int thisColor = c1.c;
		int otherColor = c2.c;
		if (p.saturation(thisColor) > p.saturation(otherColor))
			return 1;
		else if (p.saturation(thisColor) < p.saturation(otherColor))
			return -1;
		else 
			return 0;
	};
	public final static Comparator<ComparableColor> COMP_BRI = (ComparableColor c1, ComparableColor c2) -> {
		int thisColor = c1.c;
		int otherColor = c2.c;
		if (p.brightness(thisColor) > p.brightness(otherColor))
			return 1;
		else if (p.brightness(thisColor) < p.brightness(otherColor))
			return -1;
		else 
			return 0;
	};

	public ComparableColor(PApplet _p, int c) {
		this.c = c;
		p = _p;
	}

	@Override
	public String toString() {
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

	/////////////////////// // HSB SORT // ///////////////////////

//	public int compareTo(ComparableColor cc) {
//		int thisColor = this.c;
//		int otherColor = cc.c;
//
//		if (p.hue(thisColor) > p.hue(otherColor))
//			return 1;
//		else if (p.hue(thisColor) < p.hue(otherColor))
//			return -1;
//		else {
//			if (p.saturation(thisColor) > p.saturation(otherColor))
//				return 1;
//			else if (p.saturation(thisColor) < p.saturation(otherColor))
//				return -1;
//			else {
//				if (p.red(thisColor) > p.red(otherColor))
//					return 1;
//				else if (p.red(thisColor) < p.red(otherColor))
//					return -1;
//				else {
//					return 0;
//				}
//			}
//		}
//	}
	
}
