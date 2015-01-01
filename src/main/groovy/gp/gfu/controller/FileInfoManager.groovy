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

package gp.gfu.controller

import java.util.List;
import java.util.Map;
import java.util.Set;

import gp.gfu.domain.Data;
import gp.gfu.domain.FileInfo;
import gp.gfu.util.Calculations;

public class FileInfoManager extends Observable{

	private java.util.List<String> fileList
	private java.util.List<FileInfo> fileInfoList = new ArrayList<FileInfo>()
	private java.util.List<java.util.List<Object>> interimData = new ArrayList<java.util.List<Object>>()
	private Object[][] data
	private int percentComplete = 0
	private String status = "Initializing..."
	private long totalSize = 0
	private long filesProcessedCount = 0
	private Object[][] fileInfoData
	private java.util.List<java.util.List<Object>> fileInfoDataList = new ArrayList<java.util.List<Object>>()
	private Map<String, List<FileInfo>> uniqueDigestMap = new HashMap<String, List<FileInfo>>()
	private Map<FileInfo, FileInfo> uniqueFileInfoMap = new HashMap<FileInfo, FileInfo>()
	private boolean scanCanceled = false
	
	public FileInfoManager(List<String> fileList){
		this.fileList = fileList
		data = Data.getEmptyData()
	}
	
	public void processFiles(Map<String, FileInfo> pathToFileInfoMap){
		notifyObservers()
		for(int i = 0; i < fileList.size(); i++){
			if(scanCanceled){
				break
			}
			java.util.List<String> entry = new ArrayList<String>()
			
			FileInfo fileInfo = buildFileInfo(entry, i)
			addFileInfo(fileInfo, entry, pathToFileInfoMap)
			interimData.add(entry)
		}		
	}
	
	protected FileInfo buildFileInfo(java.util.List<String> entry, int i){
		entry.add(fileList.get(i))
		File f = new File(fileList.get(i))
		Double size = f.size() / 1D
		entry.add(size)
		totalSize += size
		percentComplete = (int)(((i + 1) / fileList.size()) * 100)
		status = "Processing: ${i+1} / ${fileList.size()} Total Progress: ${percentComplete}% " +
				 "Current File: ${new File(fileList.get(i)).name} " +
				 "(${Calculations.customFormat('###,###,###,###,###', size)} bytes)"
		notifyObservers()
		FileInfo fileInfo = new FileInfo(fileList.get(i), new File(fileList.get(i)).getName(), size)
		++filesProcessedCount
		return fileInfo
	}
	
	protected boolean addFileInfo(FileInfo fileInfo, java.util.List<String> entry, 
		Map<String, FileInfo> pathToFileInfoMap){
		
		pathToFileInfoMap.put(fileInfo.getPath(), fileInfo)
		
		if(uniqueFileInfoMap.containsKey(fileInfo)){
			entry.add(fileInfo.getHash())
			
			if(uniqueDigestMap.containsKey(fileInfo.getHash())){
				uniqueDigestMap.get(fileInfo.getHash()).add(fileInfo)
			}
			else{
				uniqueDigestMap.put(fileInfo.getHash(), new ArrayList<FileInfo>())
				uniqueDigestMap.get(fileInfo.getHash()).add(fileInfo)
				
				// At this point there is another file out there somewhere that needs
				// to be identified because it's the corresponding pair.  Right now there
				// are just two files that have the same digest, this one, and one 
				// other that was previously processed.
				uniqueDigestMap.get(fileInfo.getHash()).add(uniqueFileInfoMap.get(fileInfo))
			}
			return true
		}
		else{
			uniqueFileInfoMap.put(fileInfo, fileInfo)
			entry.add("Not necessary")
			return false
		}
	}
	
	public java.util.List<java.util.List<Object>> getInterimData(){
		return interimData
	}
	
	public Object[][] getData(){
		data = new Object[interimData.size()][3]
		
		for(int i = 0; i < interimData.size(); i++){
			data[i][0] = interimData.get(i).get(0)
			data[i][1] = interimData.get(i).get(1)
			data[i][2] = interimData.get(i).get(2)
		}
		return data
	}
	
	public java.util.List<java.util.List<Object>> getFileInfoDataList(){
		return fileInfoDataList
	}
	
	public Object[][] getData(java.util.List<java.util.List<Object>> interimData){
		data = new Object[interimData.size()][3]
		
		for(int i = 0; i < interimData.size(); i++){
			data[i][0] = interimData.get(i).get(0)
			data[i][1] = interimData.get(i).get(1)
			data[i][2] = interimData.get(i).get(2)
		}
		return data
	}
	
	public Object[][] getFileInfoData(java.util.List<java.util.List<Object>> fileInfoDataList){
		fileInfoData = new Object[fileInfoDataList.size()][3]
		for(int i = 0; i < fileInfoDataList.size(); i++){
			fileInfoData[i][0] = fileInfoDataList.get(i).get(0)
			fileInfoData[i][1] = fileInfoDataList.get(i).get(1)
			fileInfoData[i][2] = fileInfoDataList.get(i).get(2)
		}
		
		return fileInfoData
	}
	
	public void setfileInfoData(Object[][] fileInfoData){
		this.fileInfoData = fileInfoData
	}
	
	public long getTotalSize(){
		return totalSize
	}
	
	@Override
	public void notifyObservers() {
		setChanged();
		super.notifyObservers();
	}

	public String getStatus(){
		return status
	}
	
	public int getPercentComplete(){
		return percentComplete
	}
	
	public int getFilesProcessedCount(){
		return filesProcessedCount
	}
	
	public java.util.List<String> getFileList(){
		return fileList
	}
	
	public Map<String, List<FileInfo>> getUniqueDigestMap(){
		return uniqueDigestMap
	}
	
	public Map<FileInfo, FileInfo> getUniqueFileInfoMap(){
		return this.uniqueFileInfoMap
	}
	
	public void setScanCanceled(boolean scanCanceled){
		this.scanCanceled = scanCanceled
	}
	
	public boolean isScanCanceled(){
		return this.scanCanceled
	}
}