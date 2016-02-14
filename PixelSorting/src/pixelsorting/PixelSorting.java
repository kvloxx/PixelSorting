package pixelsorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import processing.core.*;

public class PixelSorting extends PApplet {

	PImage img;
	PImage ref;
	// String imgFileName = "glitch_art_small";
	// String imgFileName = "outputthing";
	// String imgFileName = "cat";
//	String imgFileName = "Be-a-Communist-Step-20";
	 String imgFileName = "dumbDolphinCropped";
	String fileType = "jpg";

	public void settings() {
		size(10, 10);
	}

	public void setup() {
		img = loadImage(imgFileName + "." + fileType);
		ref = loadImage(imgFileName + "." + fileType);
		surface.setSize(img.width, img.height);
	}

	public void draw() {

		//		rangeRowSort(2);
//		rowSort();
		streakSort();
		image(img, 0, 0);
		noLoop();

		//		//Save every other frame as jpg (for gif making)
		//		if (frameCount % 2 == 0 && frameCount < 150)
		//			saveFrame(frameCount + ".jpg");
	}

	void streakSort() {
		img.loadPixels();
		ArrayList<CCielabPix> pixels = new ArrayList<>();	//pxs in active columns from latest row
		ArrayList<CCielabPix> prev = new ArrayList<>();	//pxs from row above (minus one that became locked)
		boolean[] locked=new boolean[img.width];		//whether column index still participates in sorting or is locked
		
		for (int i = 0; i < locked.length; i++) {	//all columns initially participate
			locked[i]=false;
		}
		
		for (int row = 0; row < img.height; row++) {
			//assign pixels array from last iteration as this generation's prev 
			//and prepare the old prev to recieve the current row of pxs
			ArrayList<CCielabPix> tmp = pixels;	
			pixels = prev;
			prev = tmp;
			pixels.clear();
			
			for (int i = 0; i < img.width; i++) {	//fill pixels array with pxs from participating columns only
				if(!locked[i]) {
					pixels.add(new CCielabPix(this, img.pixels[row * img.width + i]));
					
					if(row==0) {	//for first row, prev will store pxs in their orig order
						prev.add(new CCielabPix(this, img.pixels[row * img.width + i]));
					}
				}
			}
			
			if(pixels.isEmpty()) {	//return if no columns are active
				img.updatePixels();
				return;
			}

			pixels.sort(Comparator.comparing((c) -> (hue(c.rgb))));
			
			//calculate dE between current and prev color for all non-locked pxs
			//record the dE and index (in both lists) of the active pxl whose color changed least(lowest dE)
			double minDeltaE=pixels.get(0).deltaE(prev.get(0));	
			int indexToLock=0;
			int indexToRemove=0;
			int j=0;
			for (int i = 0; i < locked.length; i++) {
				if(!locked[i]){
					double dE=pixels.get(j).deltaE(prev.get(0));
					if(dE<minDeltaE){
						minDeltaE=dE;
						indexToLock=i;
						indexToRemove=j;
					}
					j++;
				}
			}
			
			//pass through row again, updating active pxl colors according to sorted order
			//skip over columns that are no longer participating
			j=0;
			for (int i = 0; i < locked.length; i++) {
				if(!locked[i]){
					img.pixels[row * img.width + i]=pixels.get(j).rgb;
					j++;
				}
			}
			
			locked[indexToLock]=true;	//lock column that changed least
			pixels.remove(indexToRemove);	//remove locked px from active pxs list
		}
		img.updatePixels();
	}

	void rowSort() {
		img.loadPixels();
		for (int row = 0; row < img.height; row++) {
			ArrayList<Integer> pixels = new ArrayList<Integer>();
			for (int i = 0; i < img.width; i++) {
				pixels.add(img.pixels[row * img.width + i]);
			}
			pixels.sort(Comparator.comparing(this::hue));
			for (int i = 0; i < img.width; i++) { // prime the subsequence array
				img.pixels[row * img.width + i] = pixels.get(i);
			}
		}
		img.updatePixels();
	}

	void rangeRowSort(int range) {
		//Moves through the row, looking at a <range>-long window of adjacent pixels
		//Once these pixels are sorted, the widndow shifts one px to the right, placing the highest-ranked
		//px from the previous window on the left, and bringing the next px on the right into the sort
		img.loadPixels();
		for (int row = 0; row < img.height; row++) {
			ArrayList<Integer> window = new ArrayList<>(range);

			for (int i = 0; i < range; i++) { // prime the subsequence array
				window.add(img.pixels[row * img.width + i]);
			}

			for (int x = 0; x < img.width; x++) {
				// Collections.sort(subseq,
				// Comparator.comparing(this::hue).thenComparing(this::saturation).thenComparing(this::brightness));
				window.sort(Comparator.comparing((c) -> (-this.brightness(c))));
				img.pixels[row * img.width + x] = window.get(0);
				if ((x + range) < img.width)
					window.set(0, img.pixels[row * img.width + (x + range) % img.width]);
				else
					window.remove(0);
			}
		}
		img.updatePixels();
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { pixelsorting.PixelSorting.class.getName() });
	}
}
