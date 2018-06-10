class Derived extends Base {

}
public class TestInstance {
	public static void main(String[] args){
		Base base = new Derived();
		if(base instanceof Derived){
			System.out.println("ok");
		} else {
			System.out.println("not ok");
		}
	}
}
