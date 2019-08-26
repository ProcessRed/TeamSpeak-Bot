package de.processred.ts3;

public enum calcTypes { 
	CreateChannle(0),
	HoppingChannle(1);
	
	public int Id;
	
	private calcTypes(int id) {
		Id = id;
	}
}
