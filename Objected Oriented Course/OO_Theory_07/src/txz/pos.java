package txz;

class pos
{
	pos(int val_x, int val_y) 
	{
		x = val_x;
		y = val_y;
	}
	
	pos(pos next)
	{
		x = next.x;
		y = next.y;
	}
	
	int x, y;
	
}