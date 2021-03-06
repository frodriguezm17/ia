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
package com.diim.ia.mlp.transferfunctions;

import com.diim.ia.mlp.TransferFunction;

public class HeavysideTransfer implements TransferFunction 
{
	@Override
	public double evalute(double value) 
	{
		if(value >= 0.0)
			return 1.0;
		else
			return 0.0;
	}

	@Override
	public double evaluteDerivate(double value) 
	{
		return 1.0;
	}

}
