package Checkerboard;

import NEAT.*;

public class Main{
	
	public static void main(String[] args){
		
		Checkerboard board;
		Population pop = new Population(4,2,10);
		int generation = 0;
		while(generation<200){
			
			board = new Checkerboard(generation);
			int boardCount = 0;
			while(!pop.allDead() && boardCount<50){
				System.out.println(pop.countLiving());
				pop.updateLiving(board);
				boardCount++;
			}
			System.out.println("Generation Number" + generation);
			pop.calculateFitness(board);
			System.out.println("fitness done");
			pop.printbest();
			pop.speciate();
			System.out.println("speciate done");
			pop.cullAndRePop();
			System.out.println("culling done");
			pop.resetFitness();
			System.out.println("reset done");
			pop.mutate();
			System.out.println("mutate done");
			generation++;
		}
	}
	
}
