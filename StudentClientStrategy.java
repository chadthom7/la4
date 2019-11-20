import java.util.*;

public class StudentClientStrategy implements ClientStrategy{
	ArrayList<String> file;
	int RTT = 0;

	public StudentClientStrategy(){
		  reset();
	}

	public void reset(){
		  file = new ArrayList<String>();
	}

	public List<String> getFile(){
		  return file;
	}

	public List<Message> sendRcv(List<Message> serverMsgs){
		RTT++;
		System.out.println("=================RTT " +RTT + "====================" );
		for(Message m : serverMsgs){
			while(file.size() < m.num+1) file.add(null);
			file.set(m.num,m.msg);
			System.out.println(m.num+","+m.msg);
		}
		int nextNeeded = 0;
		while(nextNeeded <file.size() && file.get(nextNeeded)!=null)
			++nextNeeded;
		
		// nextNeeded = the first missing message
				
		// TODO send all the acks
		List<Message> ack = new ArrayList<Message>();
		if (serverMsgs.size() >0){
		for(int i  = serverMsgs.get(0).num +1; i <= nextNeeded; i++) {
			//System.out.println(i+ "-----" + serverMsgs.size() +"-----" + file.size());
			
			ack.add(new Message(i,"ACK"));
			System.out.println(i);
		}
		}
		return ack;
	
	}
}
