package testEqual;

class Cat{
	int color,weight,height;

	public Cat(int color, int weight, int height){
		this.color = color;
		this.weight = weight;
		this.height = height;
	}

	@Override
	public boolean equals(Object obj){
		if(obj == null)
			return false;
		else{
			if(obj instanceof Cat){
				Cat c = (Cat) obj;
				if(c.color == this.color && c.height == this.height && c.weight == this.weight)
					return true;
			}
		}
		return false;
	}
}
public class TestEquals {
	public static void main(String[] args){
		Cat c1 = new Cat(1,1,1);
		Cat c2 = new Cat(1,1,1);
		System.out.println(c1 == c2);
		System.out.println(c1.equals(c2));
	}
}
