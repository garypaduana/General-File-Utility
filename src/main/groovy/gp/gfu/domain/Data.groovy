/*
    General File Utility
    Copyright (C) 2012-2014, Gary Paduana, gary.paduana@gmail.com
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package gp.gfu.domain

import javax.swing.ImageIcon

public class Data{
	
	public static final ImageIcon X_MARK = new ImageIcon(Data.class.getResource("/resources/images/xmark.png"))
	public static final ImageIcon CHECK_MARK = new ImageIcon(Data.class.getResource("/resources/images/checkmark.png"))
	
	public static Object[][] convertListToArray(java.util.List<java.util.List<Object>> myList){
		Object[][] data = new Object[myList.size()][myList.get(0).size()]
		for(int i = 0; i < myList.size(); i++){
			for(int j = 0; j < myList.get(i).size(); j++){
				data[i][j] = myList.get(i).get(j)
			}
		}
		
		return data
	}
	
	public static Object[][] getEmptyData(){
		Object[][] data = new Object[1][3]
        data[0][0] = ""
        data[0][1] = ""
        data[0][2] = ""
		return data
	}
}