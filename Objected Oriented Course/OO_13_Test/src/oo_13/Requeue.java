package oo_13; 
class Requeue {
	/**@OVERVIEW:包括一个请求队列
	 */
	private Request[] queue=new Request[150];
	private int head;
	public boolean repOK() {
		if(queue!=null) return true;
		return false;
	}
	public void add(Request r) {
		/**@REQUIRES:r!=null
		 * @MODIFIES:this.queue;
		 * @EFFECTS: \old(queue).size+1=queue.size && queue[head-1]==r;
		 */
		queue[head++]=r;
	}
	public int returnNum() {
		/**@REQUIRES:NONE
		 * @MODIFIES:NONE
		 * @EFFECTS: \result=this.head;
		 */
		return this.head;
	}
	public void setNull(int i) {
		/**@REQUIRES:i<head;
		 * @MODIFIES:this.queue
		 * @EFFECTS: this.queue[i]==null;
		 */
		this.queue[i]=null;
	}
	public Request visit(int i) {
		/**@REQUIRES:i<head
		 * @MODIFIES:NONE
		 * @EFFECTS: \result=this.queue[i];
		 */
		return this.queue[i];
	}
	public void setRequest(Request r,int i) {
		/**@REQUIRES:r!=null && i<head
		 * @MODIFIES:this.queue
		 * @EFFECTS: this.queue[i]==r;
		 */
		this.queue[i]=r;
	}
}
