import java.awt.Point;

public class Main {
    public static void main(String args[]){
        Map map = new Map();
        map.makemap();
//        map.testtime();
//        map.printmapinnumber();
//
//        long time = System.currentTimeMillis();
//        Point[] path = map.mypointbfs(new Point(0,0),new Point(79,79));
//        System.out.println(System.currentTimeMillis()-time);
//        for (int i=0;i<path.length;i++){
//            System.out.println(path[i]+"\t"+i);
//        }
        TaxiGUI taxiGUI = new TaxiGUI();
        Taxi[] taxis = new Taxi[100];
        taxiGUI.LoadMap(map.getMap(),80);
        for (int i=0;i<100;i++) {
            taxis[i] = new Taxi(String.valueOf(i),map,taxiGUI);
        }
        for (int i=0;i<100;i++) {
            taxis[i].waitforservice();
            taxis[i].start();
        }

        Test test = new Test(taxis);

//        taxis[7] = new Taxi(String.valueOf(7),map,taxiGUI);
//        taxis[7].start();

//        taxis[7].gotoservice();
//        taxis[7].move(map.mypointbfs(taxis[7].getPoint(),new Point(7,7)));
        Guest[] guests = new Guest[1000];
        for (int i=0;;i++) {
            Guest guest = new Guest(i, taxis, map, taxiGUI);
            guests[i] = guest;
            while (true) {
                if (guest.getrequest()) {
                    int j=0;
                    for (j=0;j<i;j++){
                        if (guest.equal(guests[j])){
                            break;
                        }
                    }
                    if (j<i){
                        System.out.println("SAME REQUEST");
                        continue;
                    }
//                    System.out.println(guest.getSrc()+"\t"+guest.getDst()+"\t"+guest.getTime());
                    guest.start();
                    break;
                }
                if (Guest.end){
                    if (guest.file.exists()&&(guest.file.length()==0)){
                        guest.file.delete();
                    }
                    break;
                }
            }
            if (Guest.end){
                break;
            }
        }
    }
}