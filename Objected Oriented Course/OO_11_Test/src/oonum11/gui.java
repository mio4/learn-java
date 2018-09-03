package oonum11;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;







class gv {// 常用工具
	public static int MAXNUM = 1000000;

	
	public static long getTime() {// 获得当前系统时间
		// Requires:无
		// Modifies:无
		// Effects:返回long类型的以毫秒计的系统时间
		return System.currentTimeMillis();
	}

	@SuppressWarnings("static-access")
	public static void stay(long time) {
		// Requires:long类型的以毫秒计的休眠时间
		// Modifies:无
		// Effects:使当前线程休眠time的时间
		try {
			Thread.currentThread().sleep(time);
		} catch (InterruptedException e) {

		}
	}

	public static void printTime() {
		// Requires:无
		// Modifies:System.out
		// Effects:在屏幕上打印HH:mm:ss:SSS格式的当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
		System.out.println(sdf.format(new Date(getTime())));
	}

	public static String getFormatTime() {
		// Requires:无
		// Modifies:无
		// Effects:返回String类型的HH:mm:ss格式的时间
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(new Date(getTime()));
	}
}

class node {// 结点信息
	int NO;
	int depth;

	public node(int _NO, int _depth) {
		// Requires:int类型的结点号,int类型的深度信息
		// Modifies:创建一个新的node对象，修改了此对象的NO,depth属性
		// Effects:创建了一个新的node对象并初始化
		NO = _NO;
		depth = _depth;
	}
}

class guiInfo {
	public int[][] map;
	public static int[][] graph = new int[6405][6405];// 邻接矩阵
	public static int[][] graph1 = new int[6405][6405];
	int[][] D = new int[6405][6405];// 保存从i到j的最小路径值
	int[][] D1 = new int[6405][6405];
	public static void changeroad(int x1,int y1, int x2, int y2,int status) { 
		graph[x1*80+y1][x2*80+y2] = status;
		graph[x2*80+y2][x1*80+y1] = status;
	}
	public void initmatrix() {// 初始化邻接矩阵
		// Requires:无
		// Modifies:graph[][]
		// Effects:对邻接矩阵赋初值
		int MAXNUM = gv.MAXNUM;
		for (int i = 0; i < 6400; i++) {
			for (int j = 0; j < 6400; j++) {
				if (i == j)
					graph[i][j] = 0;
				else
					graph[i][j] = MAXNUM;
			}
		}
		for (int i = 0; i < 80; i++) {
			for (int j = 0; j < 80; j++) {
				if (map[i][j] == 1 || map[i][j] == 3) {
					graph[i * 80 + j][i * 80 + j + 1] = 1;
					graph[i * 80 + j + 1][i * 80 + j] = 1;
				}
				if (map[i][j] == 2 || map[i][j] == 3) {
					graph[i * 80 + j][(i + 1) * 80 + j] = 1;
					graph[(i + 1) * 80 + j][i * 80 + j] = 1;
				}
			}
		}
	   for(int i = 0; i<6405;i++) {
		   graph1[i] = graph[i].clone();
	   }
	}
	public int[][] getpoint(Point p){
		   int[][] term = new int[4][3];
		   int i = 0;
		   if(p.x>0) {
		   if(map[p.x-1][p.y] >= 2) {
				   term[i][0]= p.x-1;
				   term[i][1]= p.y;
				   term[i][2]= 1;
		   }
		   }
		   i++;
		   if(map[p.x][p.y] == 1||map[p.x][p.y] == 3) {
				   term[i][0] = p.x;
				   term[i][1] = p.y+1;
				   term[i][2] = 1;
		   }
		   i++;
		   if(map[p.x][p.y]>=2) {
			   term[i][0] = p.x+1;
			   term[i][1] = p.y;
			   term[i][2] = 1;
		   }
		   i++;
		   if(p.y>0) {
			 
		    if(map[p.x][p.y-1] == 1||map[p.x][p.y-1] == 3) {
			   term[i][0] = p.x;
			   term[i][1] = p.y-1;
			   term[i][2] = 1;
			   
		    }
		   }
		   return term;
			
		}
	public int[][] getpoint1(Point p){
		   int[][] term = new int[4][3];
		   int i = 0;
		   if(p.x>0) {
		   if(graph[(p.x-1)*80+p.y][p.x*80+p.y] ==1) {
				   term[i][0]= p.x-1;
				   term[i][1]= p.y;
				   term[i][2]= 1;
		   }
		   }
		   i++;
		   if(p.y<79) {
		   if(graph[p.x*80+p.y][p.x*80+p.y+1] == 1) {
				   term[i][0] = p.x;
				   term[i][1] = p.y+1;
				   term[i][2] = 1;
		   }}
		   i++;
		   if(p.x<79) {
		   if(graph[(p.x+1)*80+p.y][p.x*80+p.y] == 1 ) {
			   term[i][0] = p.x+1;
			   term[i][1] = p.y;
			   term[i][2] = 1;
		   }
		   }
		   i++;
		   if(p.y>0) {
		   if(graph[p.x*80+p.y][p.x*80+p.y-1] == 1) {
			   term[i][0] = p.x;
			   term[i][1] = p.y-1;
			   term[i][2] = 1;
			   
		   }}
		   return term;
			
		}
	public void pointbfs(int root) {// 单点广度优先搜索
		// Requires:int类型的点号root
		// Modifies:D[][],System.out
		// Effects:对整个地图进行广度优先搜索，获得任意点到root之间的最短路信息，储存在D[][]中
		int[] offset = new int[] { 0, 1, -1, 80, -80 };
		Vector<node> queue = new Vector<node>();
		boolean[] view = new boolean[6405];
		for (int i = 0; i < 6400; i++) {
			for (int j = 0; j < 6400; j++) {
				if (i == j) {
					D[i][j] = 0;
				} else {
					D[i][j] = graph[i][j];// 赋初值
				}
			}
		}
		int x = root;// 开始进行单点bfs
		for (int i = 0; i < 6400; i++)
			view[i] = false;
		queue.add(new node(x, 0));
		while (queue.size() > 0) {
			node n = queue.get(0);
			view[n.NO] = true;
			for (int i = 1; i <= 4; i++) {
				int next = n.NO + offset[i];
				if (next >= 0 && next < 6400 && view[next] == false && graph[n.NO][next] == 1) {
					view[next] = true;
					queue.add(new node(next, n.depth + 1));// 加入遍历队列
					D[x][next] = n.depth + 1;
					D[next][x] = n.depth + 1;
				}
			}
			queue.remove(0);// 退出队列
		}
		// 检测孤立点
		int count = 0;
		for (int i = 0; i < 6400; i++) {
			if (D[i][x] == gv.MAXNUM) {
				count++;
			}
		}
		if (count > 0) {
			//System.out.println("地图并不是连通的,程序退出");
			//System.exit(1);
		}
	}
	public void pointbfs1(int root) {// 单点广度优先搜索
		// Requires:int类型的点号root
		// Modifies:D[][],System.out
		// Effects:对整个地图进行广度优先搜索，获得任意点到root之间的最短路信息，储存在D[][]中
		int[] offset = new int[] { 0, 1, -1, 80, -80 };
		Vector<node> queue = new Vector<node>();
		boolean[] view = new boolean[6405];
		for (int i = 0; i < 6400; i++) {
			for (int j = 0; j < 6400; j++) {
				if (i == j) {
					D1[i][j] = 0;
				} else {
					D1[i][j] = graph1[i][j];// 赋初值
				}
			}
		}
		int x = root;// 开始进行单点bfs
		for (int i = 0; i < 6400; i++)
			view[i] = false;
		queue.add(new node(x, 0));
		while (queue.size() > 0) {
			node n = queue.get(0);
			view[n.NO] = true;
			for (int i = 1; i <= 4; i++) {
				int next = n.NO + offset[i];
				if (next >= 0 && next < 6400 && view[next] == false && graph1[n.NO][next] == 1) {
					view[next] = true;
					queue.add(new node(next, n.depth + 1));// 加入遍历队列
					D1[x][next] = n.depth + 1;
					D1[next][x] = n.depth + 1;
				}
			}
			queue.remove(0);// 退出队列
		}
		// 检测孤立点
		int count = 0;
		for (int i = 0; i < 6400; i++) {
			if (D1[i][x] == gv.MAXNUM) {
				count++;
			}
		}
		if (count > 0) {
			//System.out.println("地图并不是连通的,程序退出");
			//System.exit(1);
		}
	}
	public ArrayList<Point> way(int x1, int y1, int x2, int y2,int dis) {
		ArrayList<Point> ar = new ArrayList<Point>();
		Point p  = new Point(x2,y2);
		ar.add(p);
		int i;
		for(i = dis-1 ;i>0;i--) {
			if(p.x>0&&D[x1*80+y1][(p.x-1)*80+p.y] ==i&&graph[p.x*80+p.y][(p.x-1)*80+p.y] == 1) {
				p = new Point(p.x-1,p.y);
				ar.add(p);
			}
			else if(p.y<80&&D[x1*80+y1][p.x*80+p.y+1] == i&&graph[p.x*80+p.y][p.x*80+p.y+1] == 1) {
				p = new Point(p.x,p.y+1);
				ar.add(p);
			}
			else if(p.x<80&&D[x1*80+y1][(p.x+1)*80+p.y] ==i&&graph[p.x*80+p.y][(p.x+1)*80+p.y] == 1) {
				p = new Point(p.x+1,p.y);
				ar.add(p);
			}
			else {
				p = new Point (p.x,p.y-1);
				ar.add(p);
			}
			
		}
		
		return ar;
	}
	public ArrayList<Point> way1(int x1, int y1, int x2, int y2,int dis) {
		ArrayList<Point> ar = new ArrayList<Point>();
		Point p  = new Point(x2,y2);
		ar.add(p);
		int i;
		for(i = dis-1 ;i>0;i--) {
			if(p.x>0&&D1[x1*80+y1][(p.x-1)*80+p.y] ==i&&graph1[p.x*80+p.y][(p.x-1)*80+p.y] == 1) {
				p = new Point(p.x-1,p.y);
				ar.add(p);
			}
			else if(p.y<80&&D1[x1*80+y1][p.x*80+p.y+1] == i&&graph1[p.x*80+p.y][p.x*80+p.y+1] == 1) {
				p = new Point(p.x,p.y+1);
				ar.add(p);
			}
			else if(p.x<80&&D1[x1*80+y1][(p.x+1)*80+p.y] ==i&&graph1[p.x*80+p.y][(p.x+1)*80+p.y] == 1) {
				p = new Point(p.x+1,p.y);
				ar.add(p);
			}
			else {
				p = new Point (p.x,p.y-1);
				ar.add(p);
			}
			
		}
		
		return ar;
	}
	public int distance(int x1, int y1, int x2, int y2) {// 使用bfs计算两点之间的距离
		pointbfs(x1 * 80 + y1);
		return D[x1 * 80 + y1][x2 * 80 + y2];
	}
	public int distance1(int x1, int y1, int x2, int y2) {// 使用bfs计算两点之间的距离
		pointbfs1(x1 * 80 + y1);
		return D1[x1 * 80 + y1][x2 * 80 + y2];
	}
}

class guitaxi {
	public int x = 1;
	public int y = 1;
	public int status = -1;
	public int type = 0;// 0是普通出租车，1是特殊车
}

class guigv {
	public static guiInfo m = new guiInfo();// 地图备份
	public static CopyOnWriteArrayList<guitaxi> taxilist = new CopyOnWriteArrayList<guitaxi>();// 出租车列表
	public static CopyOnWriteArrayList<Point> srclist = new CopyOnWriteArrayList<Point>();// 出发点列表
	public static HashMap<String,Integer> flowmap = new HashMap<String,Integer>();//当前流量
	public static HashMap<String,Integer> memflowmap = new HashMap<String,Integer>();//之前统计的流量
	/* GUI */
	public static JPanel drawboard;
	public static int[][] colormap;
	public static int[][] lightmap;
	public static boolean redraw = false;
	public static int xoffset = 0;
	public static int yoffset = 0;
	public static int oldxoffset = 0;
	public static int oldyoffset = 0;
	public static int mousex = 0;
	public static int mousey = 0;
	public static double percent = 1.0;
	public static boolean drawstr = false;
	public static boolean drawflow=false;//是否绘制流量信息
	private static String Key(int x1,int y1,int x2,int y2){//生成唯一的Key
		return ""+x1+","+y1+","+x2+","+y2;
	}
	public static void AddFlow(int x1,int y1,int x2,int y2){//增加一个道路流量
		synchronized (guigv.flowmap) {
			//查询之前的流量数量
			int count=0;
			count=guigv.flowmap.get(Key(x1,y1,x2,y2))==null ? 0 :guigv.flowmap.get(Key(x1,y1,x2,y2));
			//添加流量
			guigv.flowmap.put(Key(x1,y1,x2,y2), count+1);
			guigv.flowmap.put(Key(x2,y2,x1,y1), count+1);
		}
	}
	public static void SetFlow(int x1,int y1,int x2,int y2,int num){//增加一个道路流量
		synchronized (guigv.flowmap) {
			//查询之前的流量数量
			int count=0;
			count=guigv.flowmap.get(Key(x1,y1,x2,y2))==null ? 0 :guigv.flowmap.get(Key(x1,y1,x2,y2));
			//添加流量
			guigv.flowmap.put(Key(x1,y1,x2,y2), count+num);
			guigv.flowmap.put(Key(x2,y2,x1,y1), count+num);
		}
	}
	public static int GetFlow(int x1,int y1,int x2,int y2){//查询流量信息
		synchronized (guigv.memflowmap) {
			return guigv.memflowmap.get(Key(x1,y1,x2,y2))==null ? 0 :guigv.memflowmap.get(Key(x1,y1,x2,y2));
		}
	}
	@SuppressWarnings("unchecked")
	public static void ClearFlow(){//清空流量信息
		synchronized (guigv.flowmap) {
			synchronized(guigv.memflowmap){
				guigv.memflowmap=(HashMap<String, Integer>) guigv.flowmap.clone();
				guigv.flowmap=new HashMap<String, Integer>();
				
			}
		}
	}
}

class DrawBoard extends JPanel {// 绘图板类
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		brush.draw(g2D);
	}
}

class myform extends JFrame {// 我的窗体程序
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int left = 100;
	private int top = 100;
	private int width = 630;
	private int height = 600;

	public myform() {
		super();
		/* 设置按钮属性 */
		// button1
		JButton button1 = new JButton();// 创建一个按钮
		button1.setBounds(10, 515, 100, 40);// 设置按钮的位置
		button1.setText("重置");// 设置按钮的标题
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guigv.xoffset = 0;
				guigv.yoffset = 0;
				guigv.oldxoffset = 0;
				guigv.oldyoffset = 0;
				guigv.percent = 1.0;
				guigv.drawboard.repaint();
			}
		});
		// button2
		JButton button2 = new JButton();// 创建一个按钮
		button2.setBounds(120, 515, 100, 40);// 设置按钮的位置
		button2.setText("放大");// 设置按钮的标题
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guigv.percent += 0.1;
				guigv.drawboard.repaint();
			}
		});
		// button3
		JButton button3 = new JButton();// 创建一个按钮
		button3.setBounds(230, 515, 100, 40);// 设置按钮的位置
		button3.setText("缩小");// 设置按钮的标题
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (guigv.percent > 0.1)
					guigv.percent -= 0.1;
				guigv.drawboard.repaint();
			}
		});
		// button4
		JButton button4 = new JButton();
		button4.setBounds(340, 515, 100, 40);
		button4.setText("清除轨迹");
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 清除colormap
				for (int i = 0; i < 85; i++) {
					for (int j = 0; j < 85; j++) {
						guigv.colormap[i][j] = 0;
					}
				}
				guigv.drawboard.repaint();
			}
		});
		/* 设置复选框属性 */
		JCheckBox check1 = new JCheckBox("显示位置");
		check1.setBounds(450, 515, 80, 40);
		check1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guigv.drawstr = check1.isSelected();
				guigv.drawboard.repaint();
			}
		});
		JCheckBox check2 = new JCheckBox("显示流量");
		check2.setBounds(530, 515, 80, 40);
		check2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guigv.drawflow = check2.isSelected();
				guigv.drawboard.repaint();
			}
		});
		/* 设置绘图区属性 */
		DrawBoard drawboard = new DrawBoard();// 创建新的绘图板
		drawboard.setBounds(10, 10, 500, 500);// 设置大小
		drawboard.setBorder(BorderFactory.createLineBorder(Color.black, 1));// 设置边框
		drawboard.setOpaque(true);
		drawboard.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) {// 按下鼠标
				guigv.redraw = true;
				guigv.mousex = e.getX();
				guigv.mousey = e.getY();
			}

			public void mouseReleased(MouseEvent e) {// 松开鼠标
				guigv.oldxoffset = guigv.xoffset;
				guigv.oldyoffset = guigv.yoffset;
				guigv.redraw = false;
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		drawboard.addMouseMotionListener(new MouseMotionAdapter() {// 添加鼠标拖动
			public void mouseDragged(MouseEvent e) {
				if (guigv.redraw == true) {
					guigv.xoffset = guigv.oldxoffset + e.getX() - guigv.mousex;
					guigv.yoffset = guigv.oldyoffset + e.getY() - guigv.mousey;
					guigv.drawboard.repaint();
				}
			}
		});
		drawboard.addMouseWheelListener(new MouseWheelListener() {// 添加鼠标滚轮
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.getWheelRotation() == 1) {// 滑轮向前
					if (guigv.percent > 0.1)
						guigv.percent -= 0.1;
					guigv.drawboard.repaint();
				} else if (e.getWheelRotation() == -1) {// 滑轮向后
					guigv.percent += 0.1;
					guigv.drawboard.repaint();
				}
			}
		});
		guigv.drawboard = drawboard;// 获得一份drawboard的引用

		/* 设置窗体属性 */
		setTitle("实时查看");// 设置窗体标题
		setLayout(null);// 使用绝对布局
		setBounds(left, top, width, height);// 设置窗体位置大小

		/* 添加控件，显示窗体 */
		Container c = getContentPane();// 获得一个容器
		c.add(button1);// 添加button1
		c.add(button2);
		c.add(button3);
		c.add(button4);
		c.add(check1);
		c.add(check2);
		c.add(drawboard);
		setVisible(true);// 使窗体可见
		setAlwaysOnTop(true);// 设置窗体置顶
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置默认关闭方式
	}
}

class brush {// 画笔
	public static int[][] colormap = new int[85][85];

	public static void draw(Graphics2D g) {
		boolean drawcolor = true;
		int factor = (int) (35 * guigv.percent);
		int width = (int) (20 * guigv.percent);
		int xoffset = -5;
		int yoffset = 3;
		// 检索一遍出租车位置信息，将有出租车的位置标上1
		int[][] taximap = new int[85][85];
		// 获得colormap的引用
		guigv.colormap = colormap;
		// 设置出租车位置
		for (int i = 0; i < 80; i++)
			for (int j = 0; j < 80; j++) {
				taximap[i][j] = -1;
			}
		for (int i = 0; i < guigv.taxilist.size(); i++) {
			guitaxi gt = guigv.taxilist.get(i);
			if (gt.status > -1) {
				// System.out.println("####"+gt.x+" "+gt.y);
				// 加入对type的判断,如果type=1，则taximap=4
				if (gt.type == 1) {
					taximap[gt.x][gt.y] = 4;
				} else {
					taximap[gt.x][gt.y] = gt.status;
				}
				if (gt.status == 0) {
					colormap[gt.x][gt.y] = 1;// 路线染色
				}
			}
		}
		// synchronized (guigv.m.taxilist) {
		// for (taxiInfo i : guigv.m.taxilist) {
		// taximap[i.nowPoint.x][i.nowPoint.y] = 1;
		// if (i.state == STATE.WILL || i.state == STATE.RUNNING) {
		// taximap[i.nowPoint.x][i.nowPoint.y] = 2;
		// colormap[i.nowPoint.x][i.nowPoint.y] = 1;// 路线染色
		// }
		// }
		// }
		// ...

		for (int i = 0; i < 80; i++) {
			for (int j = 0; j < 80; j++) {
				if (j < 10) {
					xoffset = -5;
					yoffset = 3;
				} else {
					xoffset = -7;
					yoffset = 3;
				}
				g.setStroke(new BasicStroke(2));
				g.setFont(new Font("Arial", Font.PLAIN, (int) (10 * guigv.percent)));
				if (guigv.m.map[i][j] == 2 || guigv.m.map[i][j] == 3) {
					if (drawcolor && colormap[i][j] == 1 && colormap[i + 1][j] == 1)
						g.setColor(Color.RED);
					else
						g.setColor(Color.BLACK);
					int memj=(int) ((j * factor + guigv.xoffset) * guigv.percent);
					g.drawLine(memj,
							(int) ((i * factor + guigv.yoffset) * guigv.percent),
							memj,
							(int) (((i + 1) * factor + guigv.yoffset) * guigv.percent));
					//绘制道路流量
					if(guigv.drawflow){
						g.setColor(Color.BLUE);
						g.drawString(""+guigv.GetFlow(i, j, i+1, j), memj,
								(int) (((i + 0.5) * factor + guigv.yoffset) * guigv.percent));
					}
				}
				if (guigv.m.map[i][j] == 1 || guigv.m.map[i][j] == 3) {
					if (drawcolor && colormap[i][j] == 1 && colormap[i][j + 1] == 1)
						g.setColor(Color.RED);
					else
						g.setColor(Color.BLACK);
					int memi=(int) ((i * factor + guigv.yoffset) * guigv.percent);
					g.drawLine((int) ((j * factor + guigv.xoffset) * guigv.percent),
							memi,
							(int) (((j + 1) * factor + guigv.xoffset) * guigv.percent),
							memi);
					//绘制道路流量
					if(guigv.drawflow){
						g.setColor(Color.BLUE);
						g.drawString(""+guigv.GetFlow(i, j, i, j+1), (int) (((j + 0.5) * factor + guigv.xoffset) * guigv.percent),
								memi);
					}
				}
				int targetWidth;
				// 加入对type的判断
				if (taximap[i][j] == 4) {
					g.setColor(Color.MAGENTA);
					targetWidth = 2;
				} else if (taximap[i][j] == 3) {
					g.setColor(Color.GREEN);
					targetWidth = 2;
				} else if (taximap[i][j] == 2) {
					g.setColor(Color.RED);
					targetWidth = 2;
				} else if (taximap[i][j] == 1) {
					g.setColor(Color.BLUE);
					targetWidth = 2;
				} else if (taximap[i][j] == 0) {
					g.setColor(Color.YELLOW);
					targetWidth = 2;
				} else {
					g.setColor(Color.BLACK);
					targetWidth = 1;
				}
				int cleft = (int) ((j * factor - width / 2 + guigv.xoffset) * guigv.percent);
				int ctop = (int) ((i * factor - width / 2 + guigv.yoffset) * guigv.percent);
				int cwidth = (int) (width * guigv.percent) * targetWidth;
				if (targetWidth > 1) {
					cleft = cleft - (int) (cwidth / 4);
					ctop = ctop - (int) (cwidth / 4);
				}
				// g.fillOval((int)((j*factor-width/2+guigv.xoffset)*guigv.percent),(int)((i*factor-width/2+guigv.yoffset)*guigv.percent),(int)(width*guigv.percent)*targetWidth,(int)(width*guigv.percent)*targetWidth);
				g.fillOval(cleft, ctop, cwidth, cwidth);// 绘制点
				//绘制红绿灯
				if(guigv.lightmap[i][j]==1){//东西方向为绿灯
					g.setColor(Color.GREEN);
					g.fillRect(cleft-cwidth/4, ctop+cwidth/4, cwidth/2, cwidth/8);
					g.setColor(Color.RED);
					g.fillRect(cleft+cwidth/8, ctop-cwidth/4, cwidth/8, cwidth/2);
				}
				else if(guigv.lightmap[i][j]==2){//东西方向为红灯
					g.setColor(Color.RED);
					g.fillRect(cleft-cwidth/4, ctop+cwidth/4, cwidth/2, cwidth/8);
					g.setColor(Color.GREEN);
					g.fillRect(cleft+cwidth/8, ctop-cwidth/4, cwidth/8, cwidth/2);
				}
				// 标记srclist中的点
				for (Point p : guigv.srclist) {
					g.setColor(Color.RED);
					int x = p.x;
					int y = p.y;
					g.drawLine((int) (((y - 2) * factor + guigv.xoffset) * guigv.percent),
							(int) (((x - 2) * factor + guigv.yoffset) * guigv.percent),
							(int) (((y + 2) * factor + guigv.xoffset) * guigv.percent),
							(int) (((x - 2) * factor + guigv.yoffset) * guigv.percent));
					g.drawLine((int) (((y + 2) * factor + guigv.xoffset) * guigv.percent),
							(int) (((x - 2) * factor + guigv.yoffset) * guigv.percent),
							(int) (((y + 2) * factor + guigv.xoffset) * guigv.percent),
							(int) (((x + 2) * factor + guigv.yoffset) * guigv.percent));
					g.drawLine((int) (((y + 2) * factor + guigv.xoffset) * guigv.percent),
							(int) (((x + 2) * factor + guigv.yoffset) * guigv.percent),
							(int) (((y - 2) * factor + guigv.xoffset) * guigv.percent),
							(int) (((x + 2) * factor + guigv.yoffset) * guigv.percent));
					g.drawLine((int) (((y - 2) * factor + guigv.xoffset) * guigv.percent),
							(int) (((x + 2) * factor + guigv.yoffset) * guigv.percent),
							(int) (((y - 2) * factor + guigv.xoffset) * guigv.percent),
							(int) (((x - 2) * factor + guigv.yoffset) * guigv.percent));
				}
				if (guigv.drawstr == true) {
					g.setColor(Color.WHITE);
					g.setFont(new Font("Arial", Font.PLAIN, (int) (8 * guigv.percent)));
					g.drawString("" + i + "," + j, (int) ((j * factor + xoffset + guigv.xoffset) * guigv.percent),
							(int) ((i * factor + yoffset + guigv.yoffset) * guigv.percent));
				}
				
			}
		}
	}
}

class processform extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JProgressBar progressBar = new JProgressBar();
	JLabel label1 = new JLabel("BFS进度", SwingConstants.CENTER);

	public processform() {
		super();
		// 将进度条设置在窗体最北面
		getContentPane().add(progressBar, BorderLayout.NORTH);
		getContentPane().add(label1, BorderLayout.CENTER);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setStringPainted(true);
		// 设置窗体各种属性方法
		setBounds(100, 100, 100, 100);
		setAlwaysOnTop(true);// 设置窗体置顶
		setVisible(true);
	}
}

class debugform extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea text1 = new JTextArea();

	public debugform() {
		super();
		getContentPane().add(text1);
		setBounds(0, 100, 500, 100);
		setAlwaysOnTop(true);
		setVisible(true);
	}
}

class TaxiGUI {// GUI接口类
	public void LoadMap(int[][] map, int size) {
		guigv.m.map = new int[size + 5][size + 5];
		guigv.lightmap=new int[size+5][size+5];//初始化红绿灯
		// 复制地图
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				guigv.m.map[i][j] = map[i][j];
			}
		}
		// 开始绘制地图,并每100ms刷新
		new myform();
		Thread th = new Thread(new Runnable() {
			public void run() {
				int timewindow=500;//时间窗设置为200ms
				int timecount=0;//计时
				while (true) {
					gv.stay(100);
					timecount+=100;
					if(timecount>timewindow){
						timecount=0;
						//重新记录流量信息
						guigv.ClearFlow();
					}
					guigv.drawboard.repaint();
				}
			}
		});
		th.start();
		guigv.m.initmatrix();// 初始化邻接矩阵
	}
	public void SetTaxiStatus(int index, Point point, int status) {
		guitaxi gt = guigv.taxilist.get(index);
		guigv.AddFlow(gt.x, gt.y, point.x, point.y);//统计流量
		gt.x = point.x;
		gt.y = point.y;
		gt.status = status;
	}
	public void SetTaxiType(int index, int type) {
		guitaxi gt = guigv.taxilist.get(index);
		gt.type = type;
	}
	public void RequestTaxi(Point src, Point dst) {
		// 将src周围标红
		guigv.srclist.add(src);
		// 计算最短路径的值,通过一个窗口弹出
		//int distance = guigv.m.distance(src.x, src.y, dst.x, dst.y);
		//debugform form1 = new debugform();
		//form1.text1.setText("从(" + src.x + "," + src.y + ")到(" + dst.x + "," + dst.y + ")的最短路径长度是" + distance);
	}
	public void SetLightStatus(Point p,int status){//设置红绿灯 status 0 没有红绿灯 1 东西方向为绿灯 2 东西方向为红灯
		guigv.lightmap[p.x][p.y]=status;
	}
	public void SetRoadStatus(Point p1, Point p2, int status) {// status 0关闭 1打开
		synchronized(guigv.m.map){
			int di = p1.x - p2.x;
			int dj = p1.y - p2.y;
			Point p = null;
			guiInfo.changeroad(p1.x,p1.y,p2.x,p2.y,status);
			if (di == 0) {// 在同一水平线上
				if (dj == 1) {// p2-p1
					p = p2;
				} else if (dj == -1) {// p1-p2
					p = p1;
				} else {
					return;
				}
				if (status == 0) {// 关闭
					if (guigv.m.map[p.x][p.y] == 3) {
						guigv.m.map[p.x][p.y] = 2;
					} else if (guigv.m.map[p.x][p.y] == 1) {
						guigv.m.map[p.x][p.y] = 0;
					}
				} else if (status == 1) {// 打开
					if (guigv.m.map[p.x][p.y] == 2) {
						guigv.m.map[p.x][p.y] = 3;
					} else if (guigv.m.map[p.x][p.y] == 0) {
						guigv.m.map[p.x][p.y] = 1;
					}
				}
			} else if (dj == 0) {// 在同一竖直线上
				if (di == 1) {// p2-p1
					p = p2;
				} else if (di == -1) {// p1-p2
					p = p1;
				} else {
					return;
				}
				if (status == 0) {// 关闭
					if (guigv.m.map[p.x][p.y] == 3) {
						guigv.m.map[p.x][p.y] = 1;
					} else if (guigv.m.map[p.x][p.y] == 2) {
						guigv.m.map[p.x][p.y] = 0;
					}
				} else if (status == 1) {// 打开
					if (guigv.m.map[p.x][p.y] == 1) {
						guigv.m.map[p.x][p.y] = 3;
					} else if (guigv.m.map[p.x][p.y] == 0) {
						guigv.m.map[p.x][p.y] = 2;
					}
				}
			}
			return;
		}
	}

	public TaxiGUI() {
		// 初始化taxilist
		for (int i = 0; i < 101; i++) {
			guitaxi gt = new guitaxi();
			guigv.taxilist.add(gt);
		}
	}
}