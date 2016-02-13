package pixelsorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.IntUnaryOperator;

import processing.core.*;

public class PixelSorting extends PApplet {

	PImage img;
	// String imgFileName = "glitch_art_small";
	// String imgFileName = "outputthing";
	// String imgFileName = "cat";
	String imgFileName = "Be-a-Communist-Step-20";
	// String imgFileName = "dumbDolphinCropped";
	String fileType = "jpg";

	int row = 0;
	int column = 0;

	boolean saved = false;

	public void settings() {
		size(10, 10);
	}

	public void setup() {
		img = loadImage(imgFileName + "." + fileType);
		surface.setSize(img.width, img.height);
	}

	public void draw() {

		rangeSortRow(5);
		image(img, 0, 0);
		if (frameCount % 2 == 0 && frameCount < 150)
			saveFrame(frameCount + ".jpg");
		// noLoop();
	}

	void sortRow(int row) {

		ArrayList<Integer> unsorted = new ArrayList<Integer>(img.width);

		for (int i = 0; i < img.width; i++) {
			unsorted.add(i,img.pixels[i + row * img.width]);
		}
		Collections.sort(unsorted);
		for (int i = 0; i < img.width; i++)
			img.pixels[i + row * img.width] = unsorted.get(i);
	}

	void rangeSortRow(int range) {
		img.loadPixels();
		for (int row = 0; row < img.height; row++) {
			ArrayList<Integer> subseq = new ArrayList<>(range);

			for (int i = 0; i < range; i++) { // prime the subsequence array
				subseq.add(img.pixels[row * img.width + i]);
			}

			for (int x = 0; x < img.width; x++) {
				// Collections.sort(subseq,
				// Comparator.comparing(this::hue).thenComparing(this::saturation)
				// .thenComparing(this::brightness));

				Collections.sort(subseq, Comparator.comparing(this::red));
				// Collections.sort(subseq, Comparator.comparing(this::hue));
				img.pixels[row * img.width + x] = subseq.get(0);
				if ((x + range) < img.width)
					subseq.set(0, img.pixels[row * img.width + (x + range) % img.width]);
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
