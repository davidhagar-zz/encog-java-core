/*
 * Encog Artificial Intelligence Framework v2.x
 * Java Version
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2009, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of 
 * individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.encog.neural.pattern;

import org.encog.neural.activation.ActivationFunction;
import org.encog.neural.activation.ActivationLinear;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.layers.Layer;
import org.encog.neural.networks.logic.ART1Logic;
import org.encog.neural.networks.logic.ARTLogic;
import org.encog.neural.networks.synapse.Synapse;
import org.encog.neural.networks.synapse.WeightedSynapse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Pattern to create an ART-1 neural network.
 */
public class ART1Pattern implements NeuralNetworkPattern {

	/**
	 * The logging object.
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * The number of input neurons.
	 */
	private int inputNeurons;
	
	/**
	 * The number of output neurons.
	 */
	private int outputNeurons;
	
	/**
	 * A parameter for F1 layer.
	 */
	private double a1 = 1;

	/**
	 * B parameter for F1 layer.
	 */
	private double b1 = 1.5;

	/**
	 * C parameter for F1 layer.
	 */
	private double c1 = 5;

	/**
	 * D parameter for F1 layer.
	 */
	private double d1 = 0.9;

	/**
	 * L parameter for net.
	 */
	private double l = 3;

	/**
	 * The vigilance parameter.
	 */
	private double vigilance = 0.9;
	
	/**
	 * This will fail, hidden layers are not supported for this type of
	 * network.
	 * @param count Not used.
	 */
	public void addHiddenLayer(int count) {
		final String str = "A ART1 network has no hidden layers.";
		if (this.logger.isErrorEnabled()) {
			this.logger.error(str);
		}
		throw new PatternError(str);
	}

	/**
	 * Clear any properties set for this network.
	 */
	public void clear() {
		this.inputNeurons = this.outputNeurons = 0;
		
	}

	/**
	 * Generate the neural network.
	 * @return The generated neural network.
	 */
	public BasicNetwork generate() {
		BasicNetwork network  = new BasicNetwork(new ART1Logic());
		
		int y = PatternConst.START_Y;
		
		Layer layerF1 = new BasicLayer(new ActivationLinear(), false, this.inputNeurons);
		Layer layerF2 = new BasicLayer(new ActivationLinear(), false, this.outputNeurons);
		Synapse synapseF1toF2 = new WeightedSynapse(layerF1, layerF2);
		Synapse synapseF2toF1 = new WeightedSynapse(layerF2, layerF1);
		layerF1.getNext().add(synapseF1toF2);
		layerF2.getNext().add(synapseF2toF1);
		network.setInputLayer(layerF1);
		network.setOutputLayer(layerF2);		
		
		layerF1.setX(PatternConst.START_X);
		layerF1.setY(y);
		y += PatternConst.INC_Y;
		
		layerF2.setX(PatternConst.START_X);
		layerF2.setY(y);
		
		network.setProperty(ARTLogic.PROPERTY_A1, this.a1);
		network.setProperty(ARTLogic.PROPERTY_B1, this.b1);
		network.setProperty(ARTLogic.PROPERTY_C1, this.c1);
		network.setProperty(ARTLogic.PROPERTY_D1, this.d1);
		network.setProperty(ARTLogic.PROPERTY_L, this.l);
		network.setProperty(ARTLogic.PROPERTY_VIGILANCE, this.vigilance);
		
		network.getStructure().finalizeStructure();
				
		return network;
	}

	/**
	 * This method will throw an error, you can't set the activation function
	 * for an ART1. type network.
	 */
	public void setActivationFunction(ActivationFunction activation) {
		final String str = "Can't set the activation function for an ART1.";
		if (this.logger.isErrorEnabled()) {
			this.logger.error(str);
		}
		throw new PatternError(str);
	}

	/**
	 * Set the input neuron (F1 layer) count.
	 */
	public void setInputNeurons(int count) {
		this.inputNeurons = count;
	}

	/**
	 * Set the output neuron (F2 layer) count.
	 */
	public void setOutputNeurons(int count) {
		this.outputNeurons = count;
	}

	/**
	 * @return The A1 parameter.
	 */
	public double getA1() {
		return a1;
	}

	/**
	 * Set the A1 parameter.
	 * @param a1 The new value.
	 */
	public void setA1(double a1) {
		this.a1 = a1;
	}

	/**
	 * @return The B1 parameter.
	 */
	public double getB1() {
		return b1;
	}

	/**
	 * Set the B1 parameter.
	 * @param b1 The new value.
	 */
	public void setB1(double b1) {
		this.b1 = b1;
	}

	/**
	 * @return The C1 parameter.
	 */
	public double getC1() {
		return c1;
	}

	/**
	 * Set the C1 parameter.
	 * @param c1 The new value.
	 */
	public void setC1(double c1) {
		this.c1 = c1;
	}

	/**
	 * @return The D1 parameter.
	 */
	public double getD1() {
		return d1;
	}

	/**
	 * Set the D1 parameter.
	 * @param d1 The new value.
	 */
	public void setD1(double d1) {
		this.d1 = d1;
	}

	/**
	 * @return The L parameter.
	 */
	public double getL() {
		return l;
	}

	/**
	 * Set the L parameter.
	 * @param l The new value.
	 */
	public void setL(double l) {
		this.l = l;
	}

	/**
	 * @return The vigilance for the network.
	 */
	public double getVigilance() {
		return vigilance;
	}

	/**
	 * Set the vigilance for the network.
	 * @param vigilance The new value.
	 */
	public void setVigilance(double vigilance) {
		this.vigilance = vigilance;
	}
}