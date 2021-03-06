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

import java.util.ArrayList;
import java.util.List;

import com.diim.ia.mlp.MultiLayerPerceptron;
import com.diim.ia.mlp.transferfunctions.SigmoidalTransfer;
import com.diim.ia.mlp.utility.DataProcessing5x5;

/** Pattern recognition of characters (OCR) */
public class PatternRecognitionWithData10x10Test
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		/**
		 * fLearningRate: tasa de aprendizaje, para el ejemplo se deben recalcular el valor de la tasa de aprendizaje
		 * dependiendo de la cantidad de imagenes por patron
		 * si fLearningRate es 16 el rango a probar sera i = 3.7; i < 3.9; i+=0.1
		 * si fLearningRate es 6 el rango a probar sera i = 4.0; i < 5.0; i+=0.1
		 */
		/*for (double i = 0.0; i < 10.0; i+=0.1) {
			TestPrecision(50,Math.round(i * 100.0) / 100.0);
		}*/
		//El error varia en base a fLearningRate, por lo mismo se debe evaluar una lista de casos,
		//y ver los picos mas altos, de esta manera definir que valor de fLearningRate conviene para el caso
		TestPrecision(50, 2.0);//calibrado
	}
	
	public static double TestPrecision(int maxit, double fLearningRate)
	{
		int size = 5;
		double error = 0.0;
		int nimagesxpatt = 3;//numero de imagenes por patron
		int npatt = 3;//numero de patrones
		
		int[] layers = new int[]{ size*size, size, npatt };
		
		MultiLayerPerceptron net = new MultiLayerPerceptron(layers, fLearningRate/*0.6*/, new SigmoidalTransfer());
		
		/* Learning */
		for(int i = 0; i < maxit; i++)
		{
			for(int k = 0; k < nimagesxpatt; k++)
			{
				for(int j = 0; j < npatt; j++)
				{
					//System.out.println("j, k: "+ j +", "+k);
					double[] inputs = DataProcessing5x5.loadData(DataProcessing5x5.data[j][k], size, size);
					//matriz.add(inputs);
					
					double[] output = new double[npatt];

					for(int l = 0; l < npatt; l++)
						output[l] = 0.0;
					
					output[j] = 1.0;
					
					
					// Training
					error = net.backPropagate(inputs, output);
					//System.out.println("Error is "+error+" ("+i+" "+j+" "+k+" )");
				}
			}
		}
		
		/* Print in console the matriz data
		for (double[] ds : matriz) {
			System.out.print("{");
			for (int i = 0; i < ds.length-1; i++) {
				System.out.print(ds[i]+",");
			}
			System.out.println(ds[ds.length-1]+"}");
		}*/
		
		/* Test */
		int correct = 0;
		
		double[] inputs = DataProcessing5x5.loadData(DataProcessing5x5.dataTest, size, size);
		double[] output = net.execute(inputs);

		int max = 0;
		for(int i = 0; i < npatt; i++)
			if(output[i] > output[max])
				max = i;
		
		System.out.println("fLearningRate "+fLearningRate+" - Il valore massimo e' "+output[max]+" pattern "+(max));
		double data = (double) (npatt*nimagesxpatt);
		return (double) (data - (double) correct) / data;
	}
}
