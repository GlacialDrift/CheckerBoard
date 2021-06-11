package Checkerboard;

import java.util.concurrent.ThreadLocalRandom;

public class Checkerboard{
	
	private int[] board = new int[5];
	private int generation;
	
	public Checkerboard(int generation){
		
		this(ThreadLocalRandom.current().nextInt(16), generation);
	}
	public Checkerboard(int b, int generation){
		this.generation = generation;
		this.generation = this.generation/10;
		this.generation = 16*this.generation;
		
		switch(b) {
			case 0 ->{
				board[0] = fill();
				board[1] = fill();
				board[2] = fill();
				board[3] = fill();
				board[4] = 0;
			}
			case 1 -> {
				board[0] = empty();
				board[1] = fill();
				board[2] = fill();
				board[3] = fill();
				board[4] = 1;
			}
			case 2 -> {
				board[0] = fill();
				board[1] = empty();
				board[2] = fill();
				board[3] = fill();
				board[4] = 2;
			}
			case 3 -> {
				board[0] = fill();
				board[1] = fill();
				board[2] = empty();
				board[3] = fill();
				board[4] = 3;
			}
			case 4 -> {
				board[0] = fill();
				board[1] = fill();
				board[2] = fill();
				board[3] = empty();
				board[4] = 4;
			}
			case 5 -> {
				board[0] = fill();
				board[1] = empty();
				board[2] = fill();
				board[3] = empty();
				board[4] = 5;
			}
			case 6 -> {
				board[0] = fill();
				board[1] = fill();
				board[2] = empty();
				board[3] = empty();
				board[4] = 6;
			}
			case 7 -> {
				board[0] = empty();
				board[1] = empty();
				board[2] = fill();
				board[3] = fill();
				board[4] = 7;
			}
			case 8 -> {
				board[0] = empty();
				board[1] = fill();
				board[2] = empty();
				board[3] = fill();
				board[4] = 8;
			}
			case 9 -> {
				board[0] = empty();
				board[1] = empty();
				board[2] = empty();
				board[3] = empty();
				board[4] = 9;
			}
			case 10 -> {
				board[0] = fill();
				board[1] = empty();
				board[2] = empty();
				board[3] = empty();
				board[4] = 10;
			}
			case 11 -> {
				board[0] = empty();
				board[1] = fill();
				board[2] = empty();
				board[3] = empty();
				board[4] = 11;
			}
			case 12 -> {
				board[0] = empty();
				board[1] = empty();
				board[2] = fill();
				board[3] = empty();
				board[4] = 12;
			}
			case 13 -> {
				board[0] = empty();
				board[1] = empty();
				board[2] = empty();
				board[3] = fill();
				board[4] = 13;
			}
			case 14 -> {
				board[0] = fill();
				board[1] = empty();
				board[2] = empty();
				board[3] = fill();
				board[4] = 14;
			}
			case 15 -> {
				board[0] = empty();
				board[1] = fill();
				board[2] = fill();
				board[3] = empty();
				board[4] = 15;
			}
			default -> {
				board[0] = ThreadLocalRandom.current().nextInt(256);
				board[1] = ThreadLocalRandom.current().nextInt(256);
				board[2] = ThreadLocalRandom.current().nextInt(256);
				board[3] = ThreadLocalRandom.current().nextInt(256);
				board[4] = -1;
			}
		}
	}
	
	private int fill(){
		if(generation>127) generation = 127;
		return ThreadLocalRandom.current().nextInt(255-generation,256);
	}
	
	private int empty(){
		if(generation == 0) generation = 1;
		if(generation >128) generation = 128;
		return ThreadLocalRandom.current().nextInt(generation);
	}
	
	public void print(){
		System.out.println(board[0] + " " + board[1]);
		System.out.println(board[2] + " " + board[3]);
		System.out.println(board[4]);
	}
	
	public int[] getBoard(){
		return board;
	}
	
	public void setBoard(int[] board){
		this.board = board;
	}
}
