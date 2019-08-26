package de.processred.ts3;


public class secureClient{ 
	
    private int _clientID;
    
    private TimeValueItem[] timeValueItems;
    

    public secureClient(int clientID) { 
    	timeValueItems = new TimeValueItem[] { 
    			new TimeValueItem(), //Channel Create
    			new TimeValueItem(), //Channel hopping
    	};
    	timeValueItems[calcTypes.CreateChannle.Id].Item = 0;
    	timeValueItems[calcTypes.HoppingChannle.Id].Item = 0;
    	
    	_clientID = clientID;
    }

    public int getClientId() { 
        return _clientID;
    }


    public boolean getRoomCreateValidation() { 
    	TimeValueItem item = timeValueItems[calcTypes.CreateChannle.Id];
    	return checkItem(item);
    }
    
    public boolean getChannelMoveValidation() { 
    	TimeValueItem item = timeValueItems[calcTypes.HoppingChannle.Id];
    	return checkItem(item);
    }
    
    private boolean checkItem(TimeValueItem item) { 
    	long current = System.currentTimeMillis();
        if((int) item.Item == 0) { 
        	 item.setTime(current); 
        } else if((int) item.Item > 1) { 
            if(current - item.getTime() < 3000) { 
                return false;
            } else { 
            	item.Item = 0;
            	item.setTime(current);
            }
        }
        item.Item = ((int) item.Item) + 1;
        return true;
    }
}
