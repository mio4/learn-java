import java.io.*;
import java.awt.Point;

public class Map {
    private final int SIZE = 80;
    private final String root = "map.txt";
    final int WINDOWS = 3000;

    private int[][] map = new int[SIZE][SIZE];

    final int up = 8;
    final int left = 4;
    final int down = 2;
    final int right = 1;
    private int[][] link = new int[SIZE][SIZE];
    private int[][] request = new int[SIZE][SIZE];

    guiInfo guiInfo = new guiInfo();
//    int[][][][] alldis = new int[SIZE][SIZE][SIZE][SIZE];

    Map(){
        for (int i=0;i<SIZE;i++){
            for (int j=0;j<SIZE;j++){
                map[i][j] = 0;
                link[i][j] = 0;
                request[i][j] = 0;
//                for (int m=0;m<SIZE;m++){
//                    for (int n=0;n<SIZE;n++){
//                        alldis[i][j][m][n] = -1;
//                    }
//                }
            }
        }
    }

    public void makemap(){
        File file = new File(root);
        Reader in = null;
        try{
            in = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            char temp;
            int i=0,j=0,count=0;
            boolean border = false;
            while((tempchar = in.read())!=-1){
                temp = (char) tempchar;
                if (border){
                    System.out.println("INVALID INPUT");
                    break;
                }
                if ((temp==' ')||(temp=='\t')){
                    continue;
                }
                if (temp=='\n'){
                    if (count!=80){
                        System.out.println("INPUT HAS WRONG '\\n'");
                        break;
                    }
                    count = 0;
                }
                else {
                    if ((temp>='0')&&(temp<'4')){
                        int t = temp - '0';
                        map[i][j] = t;
                        link[i][j] += t;
                        if ((t&1)==1){
                            link[i][j+1] += 4;
                        }
                        if ((t&2)==2){
                            link[i+1][j] += 8;
                        }
                        j++;
                        count++;
                        if (j==SIZE){
                            j = 0;
                            i++;
                        }
                        if (i==SIZE){
                            border = true;
                        }
                    }
                    else if (temp=='\r'){
                        continue;
                    }
                    else{
                        System.out.println("INPUT HAS WRONG CHAR");
                        break;
                    }
//                    System.out.println((i++)+":\t"+temp);
                }
//                if (temp=='\n'){
//                    System.out.println(i+":\t\\n");
//                }
            }
        }catch (IOException e){System.out.println("Map.make()");}
        guiInfo.map = map;
        guiInfo.initmatrix();
//        System.out.println("finish read map");
//        guiInfo.map = map;
//        guiInfo.initmatrix();
//        for (int i=0;i<80;i++){
//            for (int j=0;j<80;j++){
//                guiInfo.pointbfs((i*80+j));
//                alldis[i][j] = guiInfo.D;
//                System.out.println("finish "+i+","+j);
//            }
//        }
    }

//    public void printmap(){
//        int i=0,j=0;
//        for (i=0;i<SIZE;i++){
//            for (j=0;j<SIZE;j++){
////                System.out.print(map[i][j]);
//                System.out.print("@");
//                if ((map[i][j]&1)==1){
//                    System.out.print(" - ");
//                }
//                else {
//                    System.out.print("   ");
//                }
//            }
//            System.out.println();
//            for (j=0;j<SIZE;j++){
//                if ((map[i][j]&2)==2){
//                    System.out.print("|");
//                }
//                else {
//                    System.out.print(" ");
//                }
//                System.out.print("   ");
//            }
//            System.out.println();
//        }
//    }   //TaxiGUI也实现了
//    public void printmapinnumber(){
//        int i=0,j=0;
//        for (i=0;i<SIZE;i++) {
//            for (j = 0; j < SIZE; j++) {
//                System.out.print(map[i][j]+" ");
//            }
//            System.out.println();
//        }
//    }

    public int[][] getMap(){
        return map;
    }

    public int[][] getLink(){
        return link;
    }

    public boolean Linkup(Point point){
        if ((point.x>=80)||(point.x<0)||(point.y<0)||(point.y>=80)){
            return false;
        }
        else if ((link[point.x][point.y]&up)==up){
            return true;
        }
        else return false;
    }
    public boolean Linkleft(Point point){
        if ((point.x>=80)||(point.x<0)||(point.y<0)||(point.y>=80)){
            return false;
        }
        else if ((link[point.x][point.y]&left)==left){
            return true;
        }
        else return false;
    }
    public boolean Linkdown(Point point){
        if ((point.x>=80)||(point.x<0)||(point.y<0)||(point.y>=80)){
            return false;
        }
        else if ((link[point.x][point.y]&down)==down){
            return true;
        }
        else return false;
    }
    public boolean Linkright(Point point){
        if ((point.x>=80)||(point.x<0)||(point.y<0)||(point.y>=80)){
            return false;
        }
        else if ((link[point.x][point.y]&right)==right){
            return true;
        }
        else return false;
    }

    public Point[] mypointbfs(Point start,Point aim){
        guiInfo.pointbfs(start.x*80+start.y);
        int length = guiInfo.D[start.x*80+start.y][aim.x*80+aim.y];
        Point[] path = new Point[length];
        for (int i=0;i<path.length;i++){
            path[i] = new Point();
        }
        Point next = new Point();
        next.setLocation(aim);
        for (int i=length-1;i>=0;i--) {
            path[i].setLocation(next);
            if (Linkup(next) && (guiInfo.D[start.x*80+start.y][next.x*80+next.y-80] == i)) {
                next.translate(-1, 0);
            } else if (Linkleft(next) && (guiInfo.D[start.x*80+start.y][next.x*80+next.y-1] == i)) {
                next.translate(0, -1);
            } else if (Linkdown(next) && (guiInfo.D[start.x*80+start.y][next.x*80+next.y+80] == i)) {
                next.translate(1, 0);
            } else if (Linkright(next) && (guiInfo.D[start.x*80+start.y][next.x*80+next.y+1] == i)) {
                next.translate(0, 1);
            }
        }
        System.out.println("mypointbfs length:\t"+length);
        System.out.println(start+"\t"+aim);
        return path;
    }

    public void getrequest(Point src,Point dst){
        for (int i=src.x-2;i<=src.x+2;i++){
            if (i<0){
                continue;
            }
            else if (i>79){
                break;
            }
            for (int j=src.y-2;j<=src.y+2;j++){
                if (j<0){
                    continue;
                }
                else if (j>79){
                    break;
                }
                request[i][j] = 1;
            }
        }
    }

    public void testtime(){
        long start,end;
        start = System.currentTimeMillis();
        guiInfo.pointbfs(600);
        end = System.currentTimeMillis();
        System.out.println(end-start);
    }
}