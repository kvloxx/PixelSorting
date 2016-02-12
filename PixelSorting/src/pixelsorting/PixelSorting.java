package pixelsorting;

import java.util.ArrayList;
import java.util.Collections;

import processing.core.*;

public class PixelSorting extends PApplet {

	PImage img;
	// String imgFileName = "glitch_art_small";
	// String imgFileName = "outputthing";
	// String imgFileName = "cat";
	String imgFileName = "dumbDolphinCropped";
	String fileType = "jpg";
	
	int row = 0;
	int column = 0;

	boolean saved = false;

	public void settings() {
		size(470, 711);
	}

	public void setup() {
		img = loadImage(imgFileName + "." + fileType);
		surface.setSize(img.width, img.height);
	}

	public void draw() {

		rangeSortRow(10);
		image(img, 0, 0);
		// noLoop();
	}

	void sortRow(int row) {

		ArrayList<ComparableColor> unsorted = new ArrayList<ComparableColor>(img.width);
		ComparableColor[] sorted = new ComparableColor[img.width];

		for (int i = 0; i < img.width; i++) {
			unsorted.add(i, new ComparableColor(this, img.pixels[i + row * img.width]));

		}
		Collections.sort(unsorted);
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
				Collections.sort(subseq);
				img.pixels[row * img.width + x] = subseq.get(0).c;
				subseq.set(0, new ComparableColor(this, img.pixels[row * img.width + (x + range) % img.width]));
			}
		}
		img.updatePixels();
		image(img, 0, 0);
	}

	// void rangeSortRow(int range) {
	// img.loadPixels();
	// for (int row = 0; row < img.height; row++) {
	// ArrayList<ComparableColor> subsort = new
	// ArrayList<ComparableColor>(range);
	//
	// for (int x = 0; x < img.width; x++) {
	// for (int i = 0; i < range; i++) {
	// subsort.add(new ComparableColor(this, img.pixels[row * img.width + (x +
	// i) % img.width]));
	// }
	// Collections.sort(subsort);
	//// for (int i = 0; i < range; i++) {
	//// img.pixels[row * img.width + (x + i) % img.width]=subsort.get(i).c;
	//// }
	// img.pixels[row * img.width + x]=subsort.get(0).c;
	// }
	// }
	// img.updatePixels();
	// image(img, 0, 0);
	// }

	// void rangeSortRow(int range) {
	// int x = 0;
	// row = 0;
	// if (range > img.width) {
	// System.out.println("Error: sorting range must be less than width of row
	// for rangeSort");
	// return;
	// }
	//
	// while (row < img.height - 1) {
	// ComparableColor[] sorted = new ComparableColor[range];
	//
	// for (x = 0; x < img.width; x++) {
	// ArrayList<ComparableColor> unsorted = new
	// ArrayList<ComparableColor>(range);
	// img.loadPixels();
	//
	// for (int i = 0; i < range; i++)
	// unsorted.add(i, new ComparableColor(this, img.pixels[row * img.width + (x
	// + i) % img.width]));
	//
	// Collections.sort(unsorted);
	// unsorted.toArray(sorted);
	//
	// for (int i = 0; i < range; i++)
	// img.pixels[row * img.width + (x + i) % img.width] = sorted[i].c;
	// img.updatePixels();
	// }
	//
	// row++;
	// image(img, 0, 0);
	// }
	//
	// if (!saved) {
	// saveFrame();
	// saved = true;
	// }
	//
	// }
	public static void main(String _args[]) {
		PApplet.main(new String[] { pixelsorting.PixelSorting.class.getName() });
	}
}
