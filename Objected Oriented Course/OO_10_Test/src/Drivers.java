import java.util.ArrayList;

public class Drivers {
	/**
	 * @overview:Drivers contains Taxi List.
	 * you can call the methods in Drivers to modify Taxi List and get message about Taxi List.
	 */
	private ArrayList<Taxi> Tx= new ArrayList<Taxi>();;
	public boolean repOK() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result == (Tx.size==0 || Tx.size!=0);
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		return true;
	}
	public synchronized void add(Taxi p) {
		/**
		 * @REQUIRES:p.instanceof Taxi
		 * @MODIFIES:Tx.size;
		 * @EFFECTS:Tx.comtains(Tx[i]);
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked()
		 */
		this.Tx.add(p);
	}
	public synchronized void add(int i,Taxi p) {
		/**
		 * @REQUIRES:0<=i<=Tx.size && p.instanceof Taxi;
		 * @MODIFIES:Tx.size;
		 * @EFFECTS:Tx.comtains(p);
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked()
		 */
		this.Tx.add(i,p);
	}
	public synchronized void remove(Taxi t) {
		/**
		 * @REQUIRES:0<=i<Tx.size && Tx.contains(t);
		 * @MODIFIES:Tx;
		 * @EFFECTS:!Tx.comtains(t);
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked()
		 */
		if(Tx.contains(t)) this.Tx.remove(t);
		else System.out.println("no"+t);
	}
	//public synchronized ArrayList getTx
	public synchronized void remove(int i) {
		/**
		 * @REQUIRES:0<=i<Tx.size && Tx.contains(t);
		 * @MODIFIES:Tx;
		 * @EFFECTS:!Tx.comtains(Tx[i]);
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked()
		 */
		if(this.size()>i) this.Tx.remove(i);
		else System.out.println("index out of bound");
	}
	public synchronized Taxi get(int i) {
		/**
		 * @REQUIRES:i>=0 && i<Tx.size;
		 * @MODIFIES:
		 * @EFFECTS:\result==Tx[i];
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked()
		 */
		return this.Tx.get(i);
	}
	public synchronized void reset() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:Tx;
		 * @EFFECTS:Tx.size==0;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked()
		 */
		while(this.Tx.size()!=0) this.remove(0);
	}
	public synchronized int size() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==Tx.size
		 * @THREAD_REQUIRES:\locked(\this)
		 * @THREAD_EFFECTS:\locked()
		 */
		return this.Tx.size();
	}
}
