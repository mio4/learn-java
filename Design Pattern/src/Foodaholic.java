//抽象食物
interface Food{
	public String getFoodName();
}
//抽象餐具
interface Tableware{
	public String getToolName();
}
//抽象工厂
interface KitchenFactory {
	public Food getFood();
	public Tableware getTableware();
}
//具体食物
class Apple implements Food{
	public String getFoodName(){
		return "apple";
	}
}
//具体餐具
class Knife implements Tableware{
	public String getToolName(){
		return "knife";
	}
}
//具体工厂
class Akichen implements KitchenFactory {
	public Food getFood(){
		return new Apple();
	}
	public Tableware getTableware(){
		return new Knife();
	}
}
public class Foodaholic {
	public void eat(KitchenFactory k){
		System.out.println("A foodaholic is eating " +k.getFood().getFoodName() + " with " + k.getTableware().getToolName());
	}
	public static void main(String[] args){
		Foodaholic fa = new Foodaholic();
		Akichen ak = new Akichen();
		fa.eat(ak);
	}
}
