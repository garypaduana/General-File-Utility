
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

import java.awt.event.MouseEvent
import javax.swing.JTable

public class ToolTipJTable extends JTable{
	
	//Implement table cell tool tips.
	@Override
	public String getToolTipText(MouseEvent e) {
		java.awt.Point p = e.getPoint();
		int rowIndex = rowAtPoint(p);
		int colIndex = columnAtPoint(p);
		int realColumnIndex = convertColumnIndexToModel(colIndex);

		String hash = (String) getValueAt(rowIndex, 2)

		if(Data.getInstance().getUniqueFilesMap().containsKey(hash)){
			return Data.getInstance().getUniqueFilesMap().get(hash).getPath()
		}
		// Returning null prevents a tool tip from appearing if nothing is found		
		return null
	}
}