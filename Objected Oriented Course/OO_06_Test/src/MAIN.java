
import java.io.File;


public class MAIN {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		summary mysum = new summary();
		detail myde = new detail();
		
		List mylist = new List();
		mylist.get_list();
		monitor []mymoni = new monitor[mylist.num];
		for(int i = 0; i < mylist.num; i++) {
			mymoni[i] = new monitor(myde, mysum, mylist.filepath[i], mylist.trigger[i], mylist.mission[i]);
			mymoni[i].start();
		}
		//add your operation//
		
	}
	
}
