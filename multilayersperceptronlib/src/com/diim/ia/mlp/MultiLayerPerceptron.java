/*
 *     mlp-java, Copyright (C) 2012 Davide Gessa
 * 
 * 	This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.diim.ia.mlp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MultiLayerPerceptron implements Cloneable {
	
	protected double			fLearningRate = 0.6;
	protected Layer[]			fLayers;
	protected TransferFunction 	fTransferFunction;		//funcion de transferencia
	
	/**
	 * Crea una rete neuronale mlp - Crea una red neuronal perceptron multicapa
	 * 
	 * @param layers Numero di neuroni per ogni layer - Número de neuronas para cada capa
	 * @param learningRate Costante di apprendimento - Tasa de aprendizaje
	 * @param fun Funzione di trasferimento - función de transferencia
	 */
	public MultiLayerPerceptron(int[] layers, double learningRate, TransferFunction fun) {
		
		fLearningRate = learningRate;
		fTransferFunction = fun;
		
		fLayers = new Layer[layers.length];
		
		for(int i = 0; i < layers.length; i++) {
			
			int previousSize = 0;
			
			if(i != 0)	previousSize = layers[i - 1];
			
			fLayers[i] = new Layer(layers[i], previousSize);
		}
		
	}
	
	/**
	 * Esegui la rete - Ejecutar la red
	 * 
	 * @param input Valori di input - Valor de entrada
	 * @return Valori di output restituiti dalla rete - Valor de salida devuelto de la red
	 */
	public double[] execute(double[] input) {
		
		double new_value;
		
		//Instanciando vector de salida con tamaño de la ultima capa de fLayers[]
		double output[] = new double[fLayers[fLayers.length - 1].length];
		
		// Put input - Establecer vector de entrada
		for(int i = 0; i < fLayers[0].length; i++) {
			fLayers[0].neurons[i].Value = input[i];
		}
		
		// Execute - hiddens + output
		for(int k = 1; k < fLayers.length; k++) {
			for(int i = 0; i < fLayers[k].length; i++) {
				new_value = 0.0;
				for(int j = 0; j < fLayers[k - 1].length; j++)
					new_value += fLayers[k].neurons[i].Weights[j] * fLayers[k - 1].neurons[j].Value;
				
				new_value += fLayers[k].neurons[i].Bias;
				fLayers[k].neurons[i].Value = fTransferFunction.evalute(new_value);
			}
		}
		
		for(int i = 0; i < fLayers[fLayers.length - 1].length; i++) {
			output[i] = fLayers[fLayers.length - 1].neurons[i].Value;
		}
		return output;
	}
	
	
	
	/**
	 * Algoritmo di backpropagation per il learning assistito
	 * (Versione multi threads)
	 * 
	 * Convergenza non garantita e molto lenta; utilizzare come criteri
	 * di stop una norma tra gli errori precedente e corrente, ed un
	 * numero massimo di iterazioni.
	 * 
	 * Wikipedia:
	 * 	The training data is broken up into equally large batches for each 
	 * 	of the threads. Each thread executes the forward and backward propagations. 
	 * 	The weight and threshold deltas are summed for each of the threads. 
	 * 	At the end of each iteration all threads must pause briefly for the 
	 * 	weight and threshold deltas to be summed and applied to the neural network. 
	 * 	This process continues for each iteration. 
	 * 
	 * @param input Valori in input
	 * @param output Valori di output atteso
	 * @param nthread Numero di thread da spawnare per il learning
	 * @return Errore delta tra output generato ed output atteso
	 */
	public double backPropagateMultiThread(double[] input, double[] output, int nthread)
	{
		return 0.0;
	}

	
	
	/**
	 * Algoritmo di backpropagation per il learning assistito
	 * (Versione single thread)
	 * 
	 * Convergenza non garantita e molto lenta; utilizzare come criteri
	 * di stop una norma tra gli errori precedente e corrente, ed un
	 * numero massimo di iterazioni.
	 * 
	 * @param input Valori in input (scalati tra 0 ed 1)
	 * @param output Valori di output atteso (scalati tra 0 ed 1)
	 * @return Errore delta tra output generato ed output atteso
	 */
	public double backPropagate(double[] input, double[] output)
	{
		double new_output[] = execute(input);
		double error;
		int i, j, k;
		
		Layer layer = fLayers[fLayers.length - 1];
		for(i = 0; i < layer.length; i++) {
			error = output[i] - new_output[i];
			layer.neurons[i].Delta = error * fTransferFunction.evaluteDerivate(new_output[i]);
		} 
	
		
		for(k = fLayers.length - 2; k >= 0; k--) {
			Layer layerk = fLayers[k];
			for(i = 0; i < layerk.length; i++) {
				error = 0.0;
				for(j = 0; j < fLayers[k + 1].length; j++)
					error += fLayers[k + 1].neurons[j].Delta * fLayers[k + 1].neurons[j].Weights[i];
								
				layerk.neurons[i].Delta = error * fTransferFunction.evaluteDerivate(layerk.neurons[i].Value);				
			}
			
			for(i = 0; i < fLayers[k + 1].length; i++){
				for(j = 0; j < layerk.length; j++)
					fLayers[k + 1].neurons[i].Weights[j] += fLearningRate * fLayers[k + 1].neurons[i].Delta * layerk.neurons[j].Value;
				fLayers[k + 1].neurons[i].Bias += fLearningRate * fLayers[k + 1].neurons[i].Delta;
			}
		}	
		
		error = 0.0;
		for(i = 0; i < output.length; i++) {
			error += Math.abs(new_output[i] - output[i]);
		}

		error = error / output.length;
		return error;
	}
	
	
	/**
	 * Salva una rete MLP su file
	 * 
	 * @param path Path nel quale salvare la rete MLP
	 * @return true se salvata correttamente
	 */
	public boolean save(String path)
	{
		try
		{
			FileOutputStream fout = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(this);
			oos.close();
		}
		catch (Exception e) 
		{ 
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * Carica una rete MLP da file
	 * @param path Path dal quale caricare la rete MLP
	 * @return Rete MLP caricata dal file o null
	 */
	public static MultiLayerPerceptron load(String path)
	{
		try
		{
			MultiLayerPerceptron net;
			
			FileInputStream fin = new FileInputStream(path);
			ObjectInputStream oos = new ObjectInputStream(fin);
			net = (MultiLayerPerceptron) oos.readObject();
			oos.close();
			
			return net;
		}
		catch (Exception e) 
		{ 
			return null;
		}
	}
	
	

	/**
	 * @return Costante di apprendimento
	 */
	public double getLearningRate()
	{
		return fLearningRate;
	}
	
	
	/**
	 * 
	 * @param rate
	 */
	public void	setLearningRate(double rate)
	{
		fLearningRate = rate;
	}
	
	
	/**
	 * Imposta una nuova funzione di trasferimento
	 * 
	 * @param fun Funzione di trasferimento
	 */
	public void setTransferFunction(TransferFunction fun)
	{
		fTransferFunction = fun;
	}
	
	
	
	/**
	 * @return Dimensione layer di input
	 */
	public int getInputLayerSize()
	{
		return fLayers[0].length;
	}
	
	
	/**
	 * @return Dimensione layer di output
	 */
	public int getOutputLayerSize()
	{
		return fLayers[fLayers.length - 1].length;
	}
}

