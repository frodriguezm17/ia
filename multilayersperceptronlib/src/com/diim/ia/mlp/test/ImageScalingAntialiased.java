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
package com.diim.ia.mlp.test;

import com.diim.ia.mlp.MultiLayerPerceptron;
import com.diim.ia.mlp.transferfunctions.SigmoidalTransfer;
import com.diim.ia.mlp.utility.ImageProcessing;

public class ImageScalingAntialiased 
{
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		int sizex = 32;
		int sizey = 32;
		int newsizex = 64;
		int newsizey = 64;
		int nimages = 13;
		
		int[] layers = new int[]{ sizex*sizey, newsizex*newsizey, newsizex*newsizey };
		
		MultiLayerPerceptron net = new MultiLayerPerceptron(layers, 0.6, new SigmoidalTransfer());
		
		/* Learning */
		int i = 0;
		double error = 0.0;
		double accouracy = 0.01;
		double maxit = 1000;
		
		/* Ciclo di learning, finche' la distanza tra i due risultati non e' minore di una
		 * certa accurattezza e finche' non raggiunge un numero massimo di iterazioni. */
		while(/*(error > accouracy) && */(i < maxit))
		{
			//System.out.println("Step "+i+" img/"+(i%nimages)+".png");
			
			// Carico l'immagine di input
			double[] inputs = ImageProcessing.loadImage("/home/dak/workspace/MultiLayersPerceptronLib/img/"+(i%nimages)+"_blur.png", sizex, sizey, false);
			
			if(inputs == null)
			{
				i++;
				continue;
			}
			// Carico l'immagine di output
			double[] output = ImageProcessing.loadImage("/home/dak/workspace/MultiLayersPerceptronLib/img/"+(i%nimages)+".png", sizex, sizey, false);
			
			if(output == null)
			{
				i++;
				continue;
			}
			
			// Training
			error = net.backPropagate(inputs, output);
			System.out.println("Error at step "+i+" is "+error);
			
			i++;
		}
		
		System.out.println("Learning completed!");
		
		/* Test */
		double[] inputs = ImageProcessing.loadImage("/home/dak/workspace/MultiLayersPerceptronLib/img/5_blur.png", sizex, sizey, false);
		double[] output = net.execute(inputs);

		ImageProcessing.saveImage("/home/dak/workspace/MultiLayersPerceptronLib/img/test_5deblurred.png", output, sizex, sizey, false);
		System.out.println("Test saved!");
	}

}
