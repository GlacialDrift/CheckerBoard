/*
 * Copyright (c) 2021.  Michael Harris
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * 'rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package NEAT;

import java.util.Arrays;
import Checkerboard.*;

public class Player{
	
	private Genome brain;
	private double fitness;
	private double scaleFit;
	private double[] inputs;
	private double[] outputs;
	private boolean living;
	private int lives;
	
	/**
	 * Standard constructor
	 *
	 * @param inSize  # of inputs provided
	 * @param outSize # of possible outputs expected
	 */
	public Player(int inSize, int outSize){
		inputs = new double[inSize];
		outputs = new double[outSize];
		living = true;
		fitness = 0d;
		brain = new Genome(inSize, outSize);
		lives = 10;
	}
	
	/**
	 * Constructor that feeds a provided genome, possibly useful for crossOver
	 *
	 * @param g the provided genome
	 */
	public Player(Genome g){
		brain = g;
		fitness = 0;
		living = true;
		inputs = new double[g.getInputSize()];
		outputs = new double[g.getOutputSize()];
		lives = 10;
	}
	
	public void Look(Checkerboard board){
		// set the values of inputs depending on the implementation
		
		for(int i=0;i<inputs.length;i++){
			inputs[i] = board.getBoard()[i];
		}
	}
	
	/**
	 * Propagate the inputs through the network and collect the outputs
	 */
	public void Think(){
		outputs = brain.runNetwork(inputs);
	}
	
	public void Move(){
		// update other fields/locations/info based on outputs
	}
	
	public void Update(Checkerboard board){
		// update other fields as necessary
		// update "living" based on implementation
		
		int decision = decide();
		if(decision != board.getBoard()[4]){
			lives --;
		}
		if(lives<=0) living = false;
	}
	
	private int decide(){
		//find the maximum output value and its index
		double max = 0;
		int index = 0;
		for(int i=0;i<outputs.length;i++){
			if(outputs[i]>max){
				max = outputs[i];
				index = i;
			}
		}
		
		// only accept the output value if it is greater than 0.8,
		// want to generate high confidence. If not accepted, indicate
		// by setting the index to -2.
		if(max < 0.8){
			index = -2;
		}
		return index;
	}
	
	public void Show(){
		// draw the player as necessary
	}
	
	public void calcFitness(Checkerboard board){
		// CHANGES BASED ON IMPLEMENTATION AND PURPOSE
		
		int decision = decide();
		
		//actual fitness calculation
		boolean checker = board.getBoard()[4] == 14 || board.getBoard()[4]==15;
		
		/* Each output node has a "residual". If the output should be off,
		 then the residual is the node's output. If the output should
		 be on, then the residual is 1-node's output. I'm going to invert
		 the definition of these residuals and then square them to calculate
		 fitness. Therefore, if an output should be off but the value is 0.8,
		 the fitness for that node is 0.2^2 = 0.16. However, if the output
		 value is 0.2, then the fitness for that node is 0.8^2 = 0.64.
		 Therefore, nodes that are closer to the correct value get a higher
		 fitness, with a maximum contribution of 1 for being fully correct.*/
		
		/* If the network actually picked the right answer, as indicated
		 by the max output node equalling the board's type, give that
		 network a bonus fitness of 50. This significantly rewards networks
		 that can correctly identify a board.*/
		if(checker){
			fitness += Math.pow(outputs[1]*10, 2);
			fitness += Math.pow((1-outputs[0])*10,2);
		}else{
			fitness += Math.pow(outputs[0]*10, 2);
			fitness += Math.pow((1-outputs[1])*10,2);
		}
	}
	
	public Player copy(){
		Player p = new Player(inputs.length, outputs.length);
		p.setBrain(brain.copy());
		p.setFitness(fitness);
		p.setLiving(living);
		return p;
	}
	
	public History mutate(History h){
		return brain.mutateGenome(h);
	}
	
	public Player crossOver(Player b){
		Genome newG = brain.crossOver(b.getBrain());
		return new Player(newG);
	}
	
	public Genome getBrain(){
		return brain;
	}
	
	public void setBrain(Genome brain){
		this.brain = brain;
	}
	
	@Override
	public int hashCode(){
		int result;
		long temp;
		result = getBrain().hashCode();
		temp = Double.doubleToLongBits(getFitness());
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + Arrays.hashCode(getInputs());
		result = 31 * result + Arrays.hashCode(getOutputs());
		result = 31 * result + (isLiving() ? 1 : 0);
		return result;
	}
	
	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		
		Player player = (Player) o;
		
		if(Double.compare(player.getFitness(), getFitness()) != 0) return false;
		if(isLiving() != player.isLiving()) return false;
		if(!getBrain().equals(player.getBrain())) return false;
		if(!Arrays.equals(getInputs(), player.getInputs())) return false;
		return Arrays.equals(getOutputs(), player.getOutputs());
	}
	
	public double getFitness(){
		return fitness;
	}
	
	public void setFitness(double fitness){
		this.fitness = fitness;
	}
	
	public double[] getInputs(){
		return inputs;
	}
	
	public void setInputs(double[] inputs){
		this.inputs = inputs;
	}
	
	public double[] getOutputs(){
		return outputs;
	}
	
	public void setOutputs(double[] outputs){
		this.outputs = outputs;
	}
	
	public boolean isLiving(){
		return living;
	}
	
	public void setLiving(boolean living){
		this.living = living;
	}
	
	public double getScaleFit(){
		return scaleFit;
	}
	
	public void setScaleFit(double scaleFit){
		this.scaleFit = scaleFit;
	}
}
