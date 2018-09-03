import java.awt.Point;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Taxi extends Thread {
    private final int MOVE = 200;
    private final int OPENDOOR = 1000;
    private final int REST = 1000;
    private final int WAITROUND = 100;
    private final int WAITROUNDTIME = 20000;

    private int status = -1;
    private volatile Point point;
    private int credit=0;
    Point[] thispath;
    private boolean even;
    private long faketime = 0;

    private int count2rest = 0;
    private long startrest;

    private Map map;
    private TaxiGUI taxiGUI;

    Taxi(String number,Map map,TaxiGUI taxiGUI){
        super(number);
        point = new Point((int)(Math.random()*80),(int)(Math.random()*80));
        credit = 0;
        this.map = map;
        this.taxiGUI = taxiGUI;
        this.taxiGUI.SetTaxiStatus(Integer.valueOf(number),point,status);
    }

    public void updatetogui(){
        taxiGUI.SetTaxiStatus(Integer.valueOf(getName()),point,status);
    }

    public int getStatus() {
        return status;
    }

    public Point getPoint() {
        return point;
    }

    public int getCredit() {
        return credit;
    }

    public void setStatus(int status) {
        this.status = status;
        updatetogui();
    }
    public boolean iswaitforservice(){
        return (status==3);
    }

    public void setPoint(Point point) {
        this.point.setLocation(point);
        updatetogui();
    }
    public void setPoint(int x,int y) {
        point.x = x;
        point.y = y;
        updatetogui();
    }

    public void jointhelist(){
        credit++;
    }

    public void finishservice(){
        credit += 3;
    }

    public void takearest(){
        long start=System.currentTimeMillis();
        stopservice();
        try {
            Thread.sleep(REST-20);
            while ((System.currentTimeMillis()-start)<REST){
                Thread.sleep(1);
            }
        }catch (Exception e){}
        waitforservice();
        count2rest = 0;
        startrest = System.currentTimeMillis();
    }

    public void moveup(){
        long t=System.currentTimeMillis();
//        if ((map.getLink()[point.x][point.y]&map.up)!=map.up){
//            System.out.println(getName()+" can't move up");
//            return;
//        }
        try {
            Thread.sleep(MOVE-20);
            while((System.currentTimeMillis()-t)<MOVE){
                Thread.sleep(1);
            }
        }catch (Exception e){}

        point.x -= 1;
        updatetogui();
        count2rest++;
        faketime += MOVE;
    }
    public void movedown(){
        long t=System.currentTimeMillis();
//        if ((map.getLink()[point.x][point.y]&map.down)!=map.down){
//            System.out.println(getName()+" can't move down");
//            return;
//        }
        try {
            Thread.sleep(MOVE -20);
            while((System.currentTimeMillis()-t)<MOVE){
                Thread.sleep(1);
            }
        }catch (Exception e){}
        point.x += 1;
        updatetogui();
        count2rest++;
        faketime += MOVE;
    }
    public void moveleft(){
        long t=System.currentTimeMillis();
//        if ((map.getLink()[point.x][point.y]&map.left)!=map.left){
//            System.out.println(getName()+" can't move left");
//            return;
//        }
        try {
            Thread.sleep(MOVE-20);
            while((System.currentTimeMillis()-t)<MOVE){
                Thread.sleep(1);
            }
        }catch (Exception e){}
        point.y -= 1;
        updatetogui();
        count2rest++;
        faketime += MOVE;
    }
    public void moveright(){
        long t=System.currentTimeMillis();
//        if ((map.getLink()[point.x][point.y]&map.right)!=map.right){
//            System.out.println(getName()+" can't move right");
//            return;
//        }
        try {
            Thread.sleep(MOVE-20);
            while((System.currentTimeMillis()-t)<MOVE){
                Thread.sleep(1);
            }
        }catch (Exception e){}
        point.y += 1;
        updatetogui();
        count2rest++;
        faketime += MOVE;
    }
    public void move(Point point){
        if (point.x==this.point.x){
            if (point.y==this.point.y+1){
                moveright();
            }
            else if (point.y==this.point.y-1){
                moveleft();
            }
            else if (point.y==this.point.y){
                return;
            }
            else {
                System.out.print("Taxi.move is wrong 1\t");
                System.out.println(this.point.toString()+"\t"+point.toString());
                return;
            }
        }
        else if (point.y==this.point.y){
            if (point.x==this.point.x+1){
                movedown();
            }
            else if (point.x==this.point.x-1){
                moveup();
            }
            else {
                System.out.print("Taxi.move is wrong 2\t");
                System.out.println(this.point.toString()+"\t"+point.toString());
                return;
            }
        }
        else {
            System.out.print("Taxi.move is wrong 3\t");
            System.out.println(this.point.toString()+"\t"+point.toString());
            return;
        }
    }
    public void move(int i){
        switch (i){
            case 0: moveup();   break;
            case 1: moveleft(); break;
            case 2: movedown(); break;
            case 3: moveright();break;
        }
    }
    public void move(){
//        if (count2rest>=WAITROUND){
        if ((System.currentTimeMillis()-startrest)>=WAITROUNDTIME){
            stopservice();
            takearest();
            return;
        }
        int num = 0;
        int[] able = new int[4];
        if ((map.getLink()[point.x][point.y]&map.up)==map.up){
            able[num++] = 0;
        }
        if ((map.getLink()[point.x][point.y]&map.left)==map.left){
            able[num++] = 1;
        }
        if ((map.getLink()[point.x][point.y]&map.down)==map.down){
            able[num++] = 2;
        }
        if ((map.getLink()[point.x][point.y]&map.right)==map.right){
            able[num++] = 3;
        }
        int branch = (int)(Math.random()*num);
        move(able[branch]);
    }
    public int move(Point[] path){
        thispath = path;
        System.out.println(path.length);
        if (thispath.length==1000000){
            System.out.println(thispath[0]);
            return 0;
        }
        for (Point next : thispath){
            move(next);
            if (status==2) {
                fileout(toString());
            }
        }
        return path.length;
    }

    public Point[] findpathto(Point dst){
        try {
            Thread.sleep(200);
        }catch (Exception e){}
        return map.mypointbfs(point,dst);
    }

    public void waitforservice(){
        status = 3;
        updatetogui();
    }
    public void onservice(){
        status = 2;
        updatetogui();
    }
    public void gotoservice(){
        status = 1;
        updatetogui();
        System.out.println("taxi"+getName()+" comes to service");
    }
    public void stopservice(){
        status = 0;
        updatetogui();
    }

    public boolean aroundpoint(Point src){
        if ((point.x>=src.x-2)&&(point.x<=src.x+2)&&(point.y>=src.y-2)&&(point.y<=src.y+2)){
            return true;
        }
        return false;
    }

    public void opendoor(){
        try {
            Thread.sleep(OPENDOOR);
        }catch (Exception e){}
    }

    public void run(){
        startrest = System.currentTimeMillis();
        even = (((startrest/100)%2)==1);
        while (true) {
            if (iswaitforservice()) {
                long start = System.currentTimeMillis();
                move();
//            System.out.println(getName()+"\t"+(System.currentTimeMillis()-start));
            }
            else {
                count2rest = 0;
                startrest = System.currentTimeMillis();
                even = (((startrest/100)%2)==1);
                try {
                    Thread.sleep(MOVE-20);
                } catch (Exception e) {}
            }
        }
    }

    File file;
    FileOutputStream fos;

    public void newfile(File file,FileOutputStream fos,long faketime){
        this.file = file;
        this.fos = fos;
        this.faketime = faketime;
        if (even&&(((faketime/100)%2)==1)){
            faketime += 100;
        }
        else if (!even&&(((faketime/100)%2)==0)){
            faketime += 100;
        }
    }

    public void fileout(String string){
        byte[] data = string.getBytes();
        try {
            fos.write(data);
            data = "\r\n".getBytes();
            fos.write(data);
        }catch (IOException e){}
    }

    public String toString(){
        String res = "途径地点:(";
        res += point.x;
        res += ",";
        res += point.y;
        res += ")\t途径时间:";
        long time = System.currentTimeMillis();
        if ((time%100)!=0){
            time /= 100;
            time++;
            time *= 100;
        }

        time -= 200;

        res += faketime;
        return res;
    }

    public void exit(){
        System.exit(2);
    }
}
