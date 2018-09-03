package txz;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/*
箱子的移动主要思想是BFS，移动的时候要判断人能不能从当前点走到与箱子移动方向相反的那个相邻点，这里用的也是BFS，
注意，人不能穿过箱子。判重需要开4维数组，分别保存人的坐标和箱子的坐标。预处理起点后可以标记成空的地板，方便操作。
0-空地  1-障碍物  2-箱子起始位置  3-箱子终止位置  4-搬运工起始位置
*/
public class PushBox

{
	public static int[][] map = new int[20][20];
	public static boolean[][][][] vis= new boolean[20][20][20][20];
	public static final int [] dx = {0, 0, 1, -1};
	public static final int [] dy = {1, -1, 0, 0};
    int m,n;
    Queue<node> q = new LinkedList<node>();
	node start = new node(0,0,0,0,0,null);
	
	static void pre(Queue<node> q)
    {
    	if(!q.isEmpty())
    		q.clear();
    	for(int i = 0; i < 20; i++)
    	{
    		for(int j = 0; j < 20; j++)
    		{
    			for(int k = 0; k < 20; k++)
    			{
    				for(int l = 0; l < 20; l++)
    					vis[i][j][k][l] = false;
    			}
    		}
    	}
    }
	
   public boolean check(int x, int y)
    {
    	//if (x <= 0 || x > m || y <= 0 || y > n || map[x][y] == 1)
    	//	return false;
    	//return true;
	   if(x >=n || y >=m || x < 0 || y < 0 || map[x][y] == 1)
		   return false;
	   return true;
    }
    
   //判断人能否从当前点走到与箱子移动方向相反的那个相邻点
   public boolean bfs_man(int sx, int sy, int ex, int ey, int bx, int by)
    {
    	Queue<pos> qq = new LinkedList<pos>();
    	boolean vis2[][] = new boolean[20][20];
    	vis2[sx][sy] = vis2[bx][by] = true;
    	qq.add(new pos(sx,sy));
    	while (!qq.isEmpty())
    	{
    		pos u = qq.poll();
    		if (u.x == ex && u.y == ey)
    			return true;
    		pos next = new pos(0,0);
    		for (int i = 0; i < 4; i++)
    		{
    			next.x = u.x + dx[i];
    			next.y = u.y + dy[i];
    			if (!check(next.x, next.y) || vis2[next.x][next.y])
    				continue;
    			vis2[next.x][next.y] = true;
    			qq.add(new pos(next));
    		}
    	}
    	return false;
    }
    
	public int bfs()
	{
	    pre(q);
		q.add(start); //start记录的是箱子的初始位置		
		vis[start.bx][start.by][start.mx][start.my] = true;
		while (!q.isEmpty())
		{
		    node u = q.poll(); //u是从队列中取出第一个元素
			if (map[u.bx][u.by] == 3)
			{
				node cur = new node(u);
			    Stack<node> stack = new Stack<node>();
			    while(cur != null)
			    {
			    	stack.push(cur);
			    	cur = cur.prev;
			    }
			    System.out.println("The trace of box is:");
			    while(!stack.isEmpty())
			    {
			    	cur = stack.pop();
			    	System.out.println("("+cur.bx+","+cur.by+")");
			    }			     
				return u.step;
			}
			node next = new node(0,0,0,0,0,null);
			for (int i = 0; i < 4; i++)
			{
				next.bx = u.bx + dx[i];
				next.by = u.by + dy[i];
				next.mx = u.bx;
				next.my = u.by;
				next.step = u.step + 1;
				next.prev = u;
			    if (!check(next.bx, next.by) || vis[next.bx][next.by][next.mx][next.my] || !bfs_man(u.mx, u.my, u.bx - dx[i], u.by - dy[i], u.bx, u.by))
			        continue;
		        vis[next.bx][next.by][next.mx][next.my] = true;
		        q.add(new node(next));
			}
		}
		return -1;
	}
	
	
	public boolean readmap(){
		Scanner input = new Scanner(System.in);
		n = input.nextInt();
		m = input.nextInt();
			for(int i = 0; i < n; i++)
			{
				for(int j = 0; j < m; j++)
				{
					map[i][j] = input.nextInt();
					if(map[i][j] == 2)
					{
						map[i][j] = 0;
						start.bx = i;
						start.by = j;
					}
					else if(map[i][j] == 4)
					{
						map[i][j] = 0;
						start.mx = i;
						start.my = j;
					}
					
				}			
		}
		return true;	
	}

}
