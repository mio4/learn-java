import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.awt.Point;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Guest extends Thread {
    Scanner in = new Scanner(System.in);

    private long time;
    private Point src,dst;
    private Taxi[] taxis = new Taxi[100];
    private boolean[] waitforservice = new boolean[100];
    private int[] waitlist = new int[100];
    private int waitlistnum = 0;

    static volatile boolean end = false;

    File file;
    FileOutputStream fos;

    Map map;
    TaxiGUI taxiGUI;

    Guest(int i,Taxi[] taxis,Map map,TaxiGUI taxiGUI){
        super("Guest"+i);
        src = new Point(-3,-3);
        dst = new Point(-3,-3);
        this.taxis = taxis;
        for (int j=0;j<100;j++){
            waitforservice[j] = false;
        }
        this.map = map;
        this.taxiGUI = taxiGUI;
        newfile();
    }
//    Guest(int i,Taxi[] taxis,Map map,Point src,Point dst,TaxiGUI taxiGUI){
//        super("Guest"+i);
//        this.src = src;
//        this.dst = dst;
//        this.taxis = taxis;
//        for (int j=0;j<100;j++){
//            waitforservice[j] = false;
//        }
//        this.map = map;
//        this.taxiGUI = taxiGUI;
//    }
//    Guest(int i,Taxi[] taxis,Map map,int src_x,int src_y,int dst_x,int dst_y,TaxiGUI taxiGUI){
//        super("Guest"+i);
//        src.x = src_x;
//        src.y = src_y;
//        dst.x = dst_x;
//        dst.y = dst_y;
//        this.taxis = taxis;
//        for (int j=0;j<100;j++){
//            waitforservice[j] = false;
//        }
//        this.map = map;
//        this.taxiGUI = taxiGUI;
//    }

    public void addtowaitlist(String str){
        int num=0;
        num = Integer.parseInt(str);
        waitforservice[num] = true;
    }

    public void updatetogui(){
        taxiGUI.RequestTaxi(src,dst);
    }
    public void updatetomap(){
        map.getrequest(src,dst);
    }

    public boolean getrequest(){
        String str = in.nextLine();
        time = System.currentTimeMillis();
        String reg = "\\[CR,\\((\\+)?\\d+,(\\+)?\\d+\\),\\((\\+)?\\d+,(\\+)?\\d+\\)\\]";
        str = str.replaceAll(" ","");
//        System.out.println(str);
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        if (str.equals("EXIT")){
            end = true;
            try{
                fos.close();
            }catch (IOException e){}
            return false;
        }
        if (m.matches()){
            String[] buffer = str.split("\\(|,|\\)");
            int x1,y1,x2,y2;
            try {
                x1 = Integer.valueOf(buffer[2]);
                y1 = Integer.valueOf(buffer[3]);
                x2 = Integer.valueOf(buffer[6]);
                y2 = Integer.valueOf(buffer[7]);
            }catch (Exception e){
                System.out.println("input is wrong\ninput integer is too large");
                return false;
            }
            if ((x1>79)||(x1<0)||(y1>79)||(y1<0)||(x2>79)||(x2<0)||(y2>79)||(y2<0)){
                System.out.println("input integer is out of range");
                return false;
            }
            src.setLocation(x1,y1);
            dst.setLocation(x2,y2);
            if (src.equals(dst)){
                System.out.println("your src and dst is the same");
                return false;
            }
        }
        else {
            System.out.println(str);
            return false;
        }
        if ((time%100)!=0){
            time /= 100;
            time++;
            time *= 100;
        }
        fileout("发出时刻:"+time);
        fileout("请求坐标:("+src.x+","+src.y+")");
        fileout("目的地坐标:("+dst.x+","+dst.y+")");
        fileout("");
        updatetogui();
//        updatetomap();
        return true;
    }

    public void scanthearea(){
        for (int i=0;i<100;i++){
            if (waitforservice[i]){
                continue;
            }
            if (taxis[i].aroundpoint(src)){
                taxis[i].jointhelist();
                waitforservice[i] = true;
                waitlist[waitlistnum++] = i;
            }
        }
    }

    public int getthetaxi(){
        if (waitlistnum==0){
            return -1;
        }
        int maxcredit = 0,creditlistnum = 0;
        int[] creditlist = new int[100];
        fileout("抢单车辆信息:");
        for (int i=0;i<waitlistnum;i++){
            fileouttaxidstail(taxis[waitlist[i]]);
            if (!taxis[waitlist[i]].iswaitforservice()){
                continue;
            }
            if (taxis[waitlist[i]].getCredit()>maxcredit){
                creditlistnum = 0;
                creditlist[creditlistnum++] = waitlist[i];
                maxcredit = taxis[waitlist[i]].getCredit();
            }
            else if (taxis[waitlist[i]].getCredit()==maxcredit) {
                creditlist[creditlistnum++] = waitlist[i];
            }
        }
        fileout("");
        if (creditlistnum==1){
            return creditlist[0];
        }
        int[] distancelist = new int[creditlistnum];
//        if (map.alldis[src.x][src.y][0][0]==-1){
            map.guiInfo.pointbfs(src.x*80+src.y);
//            for (int i=0;i<80;i++) {
//                for (int j=0;j<80;j++) {
//                    map.alldis[src.x][src.y][i][j] = map.guiInfo.D[src.x*80+src.y][i*80+j];
//                }
//            }
//        }
        for (int i=0;i<creditlistnum;i++){
            distancelist[i] = map.guiInfo.D[src.x*80+src.y][taxis[creditlist[i]].getPoint().x*80+taxis[creditlist[i]].getPoint().y];
        }
        int distancelistnum = 0;
        int mindistance = 6400;
        for (int i=0;i<creditlistnum;i++){
            if (distancelist[i]<mindistance){
                distancelistnum = 0;
                creditlist[distancelistnum++] = creditlist[i];
                mindistance = distancelist[i];
            }
            else if (distancelist[i]==mindistance){
                creditlist[distancelistnum++] = creditlist[i];
            }
        }
        if (distancelistnum==1){
            return creditlist[0];
        }
        int random = (int)(Math.random()*distancelistnum);
        return creditlist[random];
    }

    @Override
    public String toString() {
        return String.valueOf("time:"+time+"\tsrc:("+src.x+","+src.y+")\tdst:("+dst.x+","+dst.y+")");
    }

    public long getTime() {
        return time;
    }

    public Point getSrc() {
        return src;
    }

    public Point getDst() {
        return dst;
    }

    public boolean equal(Guest guest){
        if ((src.equals(guest.getSrc()))&&(dst.equals(guest.getDst()))&&(time==guest.getTime())){
            return true;
        }
        return false;
    }

    public long standardization(long time){
        long res = time;
        res /= 100;
        res++;
        res *= 100;
        return res;
    }

    public void run(){
//        getrequest();
//        newfile();
        long start = System.currentTimeMillis();
//        int flag=0;
        while (true){
            scanthearea();
            if ((System.currentTimeMillis() - start)>=map.WINDOWS){
                break;
            }
//            if (flag==0){
//                flag=1;
//                map.guiInfo.pointbfs(src.x*80+src.y);
//            }
            try {
                Thread.sleep(190);
            }catch (Exception e){}
        }
        int index = 0;
        index = getthetaxi();
        long time = System.currentTimeMillis();
        time = standardization(time);
        if (index!=-1) {
            fileout("派单时刻:"+(this.time+3000));
            fileout("派单车辆编号:"+index);
            fileout("派单时车辆位置:("+taxis[index].getPoint().x+","+taxis[index].getPoint().y+")");
            taxis[index].gotoservice();
            int pathlong/* = map.guiInfo.distance(taxis[index].getPoint().x,taxis[index].getPoint().y,src.x,src.y)*/;
            pathlong = taxis[index].move(taxis[index].findpathto(src));
//            System.out.println("Guest line 192:\t"+src+"\t"+dst);
            time = System.currentTimeMillis();
            time = standardization(time);
            fileout("到达乘客时间:"+(this.time+3000+pathlong*200));
            fileout("乘客位置:("+src.x+","+src.y+")");
            fileout("目的地位置:("+dst.x+","+dst.y+")");
            taxis[index].opendoor();
            time = System.currentTimeMillis();
            time = standardization(time);
            fileout("关门时间:"+(this.time+4000+pathlong*200));
            taxis[index].newfile(file,fos,(this.time+4000+pathlong*200));
            taxis[index].onservice();
            long pathlong2/* = map.guiInfo.distance(taxis[index].getPoint().x,taxis[index].getPoint().y,dst.x,dst.y)*/;
            pathlong2 = taxis[index].move(taxis[index].findpathto(dst));
            time = System.currentTimeMillis();
            time = standardization(time);
            fileout("到达目的地时间:"+(this.time+4000+pathlong*200+pathlong2*200));
            taxis[index].opendoor();
            taxis[index].finishservice();
            taxis[index].waitforservice();
            System.out.println("guest "+getName()+" reach the dst, serviced by taxi"+index);
            System.out.println("this taxi's credit is "+taxis[index].getCredit());
        }
        else {
            fileout("there is no taxi could come to service");
            System.out.println("there is no taxi could come to service guest "+getName());
        }
    }

    public void newfile(){
        file = new File(getName()+".txt");
        fos = null;
        try {
            if (!file.exists()){
                boolean b = file.createNewFile();
                if (!b){
                    System.out.println("File existed");
                }
            }
            fos = new FileOutputStream(file);
        }catch (Exception ignore){}
    }

    public void fileout(String string){
        byte[] data = string.getBytes();
        try {
            fos.write(data);
            data = "\r\n".getBytes();
            fos.write(data);
        }catch (IOException e){}
    }

    public void fileouttaxidstail(Taxi taxi){
//        fileout("车辆编号:"+taxi.getName());
//        fileout("车辆位置:("+taxi.getPoint().x+","+taxi.getPoint().y+")");
//        fileout("车辆状态:"+taxi.getStatus());
//        fileout("车辆信用信息:"+taxi.getCredit());
        String taxistatus = "";
        switch (taxi.getStatus()){
            case 0: taxistatus+="停止服务"; break;
            case 1: taxistatus+="前往服务"; break;
            case 2: taxistatus+="正在服务"; break;
            case 3: taxistatus+="等待服务"; break;
            default:break;
        }
        fileout("车辆编号:"+taxi.getName()+"\t车辆位置:("+taxi.getPoint().x+","+taxi.getPoint().y+")\t车辆状态:"+taxistatus+"\t车辆信用信息:"+taxi.getCredit());
    }
}























//[CR,(40,40),(79,79)]
//[CR,(40,40),(50,50)]
//[CR,(5,5),(7,7)]
//[CR,(00,00),(+79,79)]

//[CR,(5,5),(5,5)]
//[CR,(5,5),(7,7)]
//[CR,(5,5),(7,7)]
//[CR,(5,5),(7,7)]