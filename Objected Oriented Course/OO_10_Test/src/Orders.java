import java.util.ArrayList;

public class Orders {
	/**
	 * @overview:Contains Order Queue. You can call the methods in Orders to modify Order Queue and get information for Order Queue.
	 * Besides, Orders contains a method to remove orders that had been finished while despatching orders that have gone through 7500 time window.
	 */
	public boolean repOK() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\retsult == (Ps.size==0 || Ps.size!=0);
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		return true;
	}
	private ArrayList<Passenger> Ps = new ArrayList<Passenger>();
	public synchronized void add(Passenger p) {
		/**
		 * @REQUIRES:p.instance Passenger;
		 * @MODIFIES:Ps.size;
		 * @EFFECTS:Ps.contain(p);
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		this.Ps.add(p);
	}
	public synchronized void remove(int i) {
		/**
		 * @REQUIRES:0<=i<Ps.size;
		 * @MODIFIES:Ps.size;
		 * @EFFECTS:Ps.size==\old(Ps.size)-1;
		 * 				!(Ps.contain(Ps[i]);
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		if(this.size()>i) this.Ps.remove(i);
		else System.out.println("index out of bound");
	}
	public synchronized Passenger get(int i) {
		/**
		 * @REQUIRES:i<Ps.size;
		 * @MODIFIES:
		 * @EFFECTS:\result==Ps[i];
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		return this.Ps.get(i);
	}
	public synchronized int size() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==Ps.size;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		return this.Ps.size();
	}
	public synchronized void reset() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:Ps.size;
		 * @EFFECTS:Ps.size==0;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		while(this.Ps.size()!=0) this.remove(0);
	}
	public synchronized void check() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:Ps.size;
		 * @EFFECTS:(\exist Passenger p,Ps,p is finished)==>(!Ps.contains(p));
		 *			(\exist Passenger p,Ps,7500ms window done)==>(select a taxi for p);
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked(\all Passenger p,Ps);
		 */
			//System.out.println("head's count time"+Ps.get(0).getCount());
		for(int i=0;i<this.size();i++) {
			//synchronized(Ps.get(i)) {
			//System.out.println("**************"+(gv.getTime()-Ps.get(i).getTime()));
			if( Ps.get(i).getEnd()) {
				this.remove(i);
				i--;
			}
			else if(gv.getTime()-Ps.get(i).getTime()>7500 && !Ps.get(i).getRecv()) {
				//System.out.println("select");
				Ps.get(i).select();
				//System.out.println("selected.....");
			}

			//}
		}
	}
}
/*


[CR,(78,78),(20,0)]
[CR,(1,1),(0,0)]
[CR,(9,9),(0,9)]
[CR,(78,78),(45,72)]
[CR,(78,78),(20,0)]
[CR,(78,78),(66,60)]
[CR,(78,78),(20,50)]
[CR,(12,13),(22,33)]
[CR,(10,10),(79,79)]
[CR,(1,13),(50,0)]
[CR,(19,39),(0,9)]
[CR,(78,68),(45,72)]
[CR,(78,48),(20,0)]
[CR,(18,78),(66,60)]
[CR,(38,78),(20,50)]
[CR,(42,13),(22,33)]
[CR,(50,10),(79,79)]

END

*/