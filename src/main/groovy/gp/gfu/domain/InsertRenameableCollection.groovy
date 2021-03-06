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


public class InsertRenameableCollection extends AbstractRenameableCollection{
		
	private String value
	private int position
	
	public InsertRenameableCollection(String topDir, String regex, String value, int position){
		super(topDir, regex)
		this.value = value
		this.position = position
	}
	
	String applyChange(String name, File file){
		if(name.length() - 1 > position){
		    return name.substring(0, position) + value + name.substring(position, name.length())
		}
		else{
		    return name
		}
	}
}