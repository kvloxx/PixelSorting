package pixelsorting;

import java.util.ArrayList;
import java.util.Collections;

import processing.core.*;

public class PixelSorting extends PApplet {

	PImage img;
	// String imgFileName = "glitch_art_small";
	// String imgFileName = "outputthing";
	String imgFileName = "cat";
	// String imgFileName = "dumbDolphinCropped";
	String fileType = "jpg";

	int row = 0;
	int column = 0;

	boolean saved = false;

	public void settings() {
		size(10,10);
	}

	public void setup() {
		img = loadImage(imgFileName + "." + fileType);
		surface.setSize(img.width, img.height);
	}

	public void draw() {

		rangeSortRow(5);
		image(img, 0, 0);
		// noLoop();
	}

	void sortRow(int row) {

		ArrayList<ComparableColor> unsorted = new ArrayList<ComparableColor>(img.width);
		ComparableColor[] sorted = new ComparableColor[img.width];

		for (int i = 0; i < img.width; i++) {
			unsorted.add(i, new ComparableColor(this, img.pixels[i + row * img.width]));

		}
		// Collections.sort(unsorted);
		unsorted.toArray(sorted);
		for (int i = 0; i < img.width; i++)
			img.pixels[i + row * img.width] = sorted[i].c;
	}

	void rangeSortRow(int range) {
		img.loadPixels();
		for (int row = 0; row < img.height; row++) {
			ArrayList<ComparableColor> subseq = new ArrayList<ComparableColor>(range);

			for (int i = 0; i < range; i++) { // prime the subsequence array
				subseq.add(new ComparableColor(this, img.pixels[row * img.width + i]));
			}

			for (int x = 0; x < img.width; x++) {
				// Collections.sort(subseq, ComparableColor.COMP_HUE);
				Collections.sort(subseq, ComparableColor.newMultiComp(ComparableColor.COMP_BRI,
						ComparableColor.COMP_HUE, ComparableColor.COMP_BRI));
				img.pixels[row * img.width + x] = subseq.get(0).c;
				if ((x + range) < img.width)
					subseq.set(0, new ComparableColor(this, img.pixels[row * img.width + (x + range) % img.width]));
				else
					subseq.remove(0);
			}
		}
		img.updatePixels();
		image(img, 0, 0);
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { pixelsorting.PixelSorting.class.getName() });
	}
}
