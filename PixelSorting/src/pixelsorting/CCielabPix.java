package pixelsorting;

import processing.core.PApplet;

/*
 * Code adapted from
 * https://forum.processing.org/two/discussion/2615/color-difference
 */

public class CCielabPix {
	PApplet p;
	int rgb;
	double L;
	double a;
	double b;

	public CCielabPix(PApplet p, int rgb) {
		this.p = p;
		this.rgb=rgb;

		// RGB -> XYZ
		double var_R = (p.red(rgb) / 255);
		double var_G = (p.green(rgb) / 255);
		double var_B = (p.blue(rgb) / 255);

		if (var_R > 0.04045)
			var_R = Math.pow((var_R + 0.055) / 1.055, 2.4);
		else
			var_R = var_R / 12.92;
		if (var_G > 0.04045)
			var_G = Math.pow((var_G + 0.055) / 1.055, 2.4);
		else
			var_G = var_G / 12.92;
		if (var_B > 0.04045)
			var_B = Math.pow((var_B + 0.055) / 1.055, 2.4);
		else
			var_B = var_B / 12.92;

		var_R = var_R * 100;
		var_G = var_G * 100;
		var_B = var_B * 100;

		double X = var_R * 0.4124 + var_G * 0.3576 + var_B * 0.1805;
		double Y = var_R * 0.2126 + var_G * 0.7152 + var_B * 0.0722;
		double Z = var_R * 0.0193 + var_G * 0.1192 + var_B * 0.9505;

		// XYZ -> CIE-L*ab

		double var_X = X / 95.047;
		double var_Y = Y / 100.000;
		double var_Z = Z / 108.883;

		if (var_X > 0.008856)
			var_X = Math.pow(var_X, 1. / 3);
		else
			var_X = (7.787 * var_X) + (16. / 116.);
		if (var_Y > 0.008856)
			var_Y = Math.pow(var_Y, 1. / 3);
		else
			var_Y = (7.787 * var_Y) + (16. / 116.);
		if (var_Z > 0.008856)
			var_Z = Math.pow(var_Z, 1. / 3);
		else
			var_Z = (7.787 * var_Z) + (16. / 116.);

		L = (116. * var_Y) - 16;
		a = 500. * (var_X - var_Y);
		b = 200. * (var_Y - var_Z);
	}

	double deltaE(CCielabPix col) {
		/*
		 * To use:
		 * CCielab ca = new CCielab(color(#ff4050));
		 * CCielab cb = new CCielab(color(#002134));
		 * double delta = ca.deltaE(cb));
		 */
		double whtl = 1.; // Weighting factors depending
		double whtc = 1.; // on the application (1 = default)
		double whth = 1.;

		double xC1 = Math.sqrt((a * a) + (b * b));
		double xC2 = Math.sqrt((col.a * col.a) + (col.b * col.b));
		double xDL = col.L - L;
		double xDC = xC2 - xC1;
		double xDE = Math.sqrt(((L - col.L) * (L - col.L)) + ((a - col.a) * (a - col.a)) + ((b - col.b) * (b - col.b)));
		double xDH = 0;

		if (Math.sqrt(xDE) > (Math.sqrt(Math.abs(xDL)) + Math.sqrt(Math.abs(xDC)))) {
			xDH = Math.sqrt((xDE * xDE) - (xDL * xDL) - (xDC * xDC));
		}

		double xSC = 1 + (0.045 * xC1);
		double xSH = 1 + (0.015 * xC1);

		xDL /= whtl;
		xDC /= whtc * xSC;
		xDH /= whth * xSH;
		double Delta_E94 = Math.sqrt(Math.pow(xDL, 2) + Math.pow(xDC, 2) + Math.pow(xDH, 2));

		return (Delta_E94);
	}
}
