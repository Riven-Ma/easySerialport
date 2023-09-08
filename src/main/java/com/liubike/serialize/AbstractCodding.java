package com.liubike.serialize;

public abstract class AbstractCodding {

	
	protected byte[] data;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) throws Exception {
        this.data = data;
    }
	
    
    
}
