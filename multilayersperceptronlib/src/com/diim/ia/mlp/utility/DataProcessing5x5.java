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
package com.diim.ia.mlp.utility;

public class DataProcessing5x5
{
	public static int dataTest[][] =
		{		   //i
			{1,1,1,1,0},//j
			{1,1,0,1,0},
			{1,1,0,1,0},
			{0,1,0,1,0},
			{0,1,1,1,0}
		};
				
	public static int data[][][][] =
		{//k
			{	//l
				{		   //i
					{0,1,1,1,0},//j
					{0,1,0,1,0},
					{0,1,0,1,0},
					{0,1,0,1,0},
					{0,1,1,1,0}
				},{
					{1,1,1,1,1},
					{1,1,0,1,1},
					{1,1,0,1,1},
					{1,1,0,1,1},
					{1,1,1,1,1}
				},{
					{1,1,1,1,0},
					{1,1,0,1,0},
					{1,1,0,1,0},
					{1,1,0,1,0},
					{1,1,1,1,0}
				}
			},
			//k
			{	//l
				{		   //i
					{0,0,1,1,0},//j
					{0,1,1,1,0},
					{1,0,1,1,0},
					{0,0,1,1,0},
					{0,0,1,1,0}
				},{
					{0,1,1,1,0},
					{1,1,1,1,0},
					{1,0,1,1,0},
					{0,0,1,1,0},
					{0,0,1,1,0}
				},{
					{0,0,1,1,0},
					{0,1,0,1,0},
					{1,0,0,1,0},
					{0,0,0,1,0},
					{0,0,0,1,0}
				}
			},
			//k
			{	//l
				{		   //i
					{0,1,1,1,1},//j
					{1,0,0,1,1},
					{0,0,1,0,0},
					{0,1,0,0,0},
					{1,1,1,1,1}
				},{
					{0,1,1,1,1},
					{1,0,0,1,1},
					{0,0,1,1,0},
					{0,1,1,0,0},
					{1,1,1,1,1}
				},{
					{0,1,1,1,0},
					{1,0,0,1,0},
					{0,0,1,0,0},
					{0,1,0,0,0},
					{1,1,1,1,1}
				}
			}
	};

	public static double[] loadData(int[][] matriz, int sizex, int sizey) {
		
		double[] data = new double[sizex*sizey];
		
		for(int i = 0; i < sizex; i++){
			for(int j = 0; j < sizey; j++){
				data[i * sizex + j] = matriz[i][j] == 0 ? 0.0 : 1.0;
			}
		}
		return data;
	}
	
}
