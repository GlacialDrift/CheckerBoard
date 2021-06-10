package Checkerboard;

import java.util.concurrent.ThreadLocalRandom;

public class Main{
	
	public static void main(String[] args){
		int boardtype = ThreadLocalRandom.current().nextInt(16);
		Checkerboard board = new Checkerboard(boardtype,10);
		board.print();
	}
	
}
