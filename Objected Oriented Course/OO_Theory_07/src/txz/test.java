package txz;



	public class test {

		/**
		 * @param args
		 * @throws Exception 
		 */
		public static void main(String[] args) throws Exception {
			// TODO Auto-generated method stub
	      PushBox pb=new PushBox();
	      if(pb.readmap()){
	    	  System.out.println("Valid Input!");  
	      }
	      else{
	    	  System.out.println("Illegal Input!"); 
	      }
	      int a=pb.bfs();
	      if(a==-1){
	    	  System.out.println("No solutions!");}
	      else{
	    	  System.out.println("The total steps is:");
		  System.out.println(a);}
	      
		}
	}



