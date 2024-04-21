import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
	
		Scanner sc = new Scanner(System.in);
		String cmd = "";
		String command = "";
        String fileName = "db.csv";
        String fullStr = "";

		while (!cmd.equals("exit")) {
			cmd = sc.nextLine();
            if (cmd.equals("exit")) {
                break;
            }
			String[] words = cmd.split("\\s+");
			if(words.length==2){		
				command = words[0];
                fullStr = words[1];
			} else if (words.length==1){
                command = words[0];
            }
			switch (command) {
			case "add":
				add(fileName,fullStr);
				break;

			case "print":
				print(fileName);
				break;

            case "del":
				del(fileName, fullStr);
				break;
                 
            case "edit":
				edit(fileName, fullStr);
				break;
            
            case "sort":
				sort(fileName);
				break;
                 
            case "find":
				find(fileName, fullStr);
				break;
            
            case "avg":
				avg(fileName);
				break;

			default:
				System.out.println("unknown command");
				break;
			}
		}

		sc.close();
	}
    public static void add(String fileName, String fullStr) {
       String[] strMas = fullStr.split(";");
       try {
       Scanner scanner = new Scanner(new FileReader(fileName));
       String s1;
       if(strMas.length == 6){
        String idLen = strMas[0];  
        if(idLen.length() !=3){
            System.out.println("wrong id");
            scanner.close();
            return; 
        } else{
            try{
                int id =  Integer.parseInt(strMas[0]);
                while (scanner.hasNextLine()) {
                    s1 = scanner.nextLine();
                    String[] strMas1 = s1.split(";");
                    int id1 = Integer.parseInt(strMas1[0]);
                    if(id==id1) {
                        System.out.println("wrong id");
                        scanner.close();
                        return;
                    }
                }
            } catch (Exception ex){
                System.out.println("wrong id");
                return;
            }
        scanner.close();
        }
        String[] city = strMas[1].toLowerCase().trim().split("\\s+");
        String cityName = "";
        for(int i=0;i<city.length;i++){
        city[i] = city[i].substring(0,1).toUpperCase() + city[i].substring(1);
        cityName = city[i]+" ";
        }
        cityName = cityName.trim();
        strMas[1] = cityName;
        String[] dateMas = strMas[2].split("/");
        int[] dateMas1 = new int[3];
        String dateStr = "";
        if(dateMas.length != 3){
            System.out.println("wrong date");
            return;
        } else {
            for(int i=0;i<3;i++){
                dateStr = dateMas[i];
                dateMas1[i] = Integer.parseInt(dateStr);
            }
            if (dateMas1[0] < 1 || dateMas1[0] > 31 || dateMas1[1] < 1 || dateMas1[1] > 12) {
                System.out.println("wrong date");
                return;
            } else {
                String date = strMas[2];
            }
        }
        try{
        int days = Integer.parseInt(strMas[3]);
        } catch(Exception ex){
            System.out.println("wrong day count");
            return;
        }
        try{
            double price = Double.parseDouble(strMas[4]);
            String formattedPrice = String.format("%.2f", price);
            strMas[4] = formattedPrice;
            } catch(Exception ex){
                System.out.println("wrong price");
                return;
            }
        String vehicle = strMas[5].toUpperCase();

        switch (vehicle) {
			case "BUS":
				break;
			case "PLANE":
				break;
            case "BOAT":
				break;
            case "TRAIN":
				break;
            default:
				System.out.println("wrong vehicle");
				return;
        }
        strMas[5] = vehicle;

       } else{
        System.out.println("wrong field count");
        scanner.close();
        return;
       }

        Scanner scanner1 = new Scanner(new FileReader(fileName));
        String s;
        PrintWriter out = new PrintWriter(new FileWriter("tmp.csv"));
        
        while (scanner1.hasNextLine()) {
            s = scanner1.nextLine();
            out.println(s);
        }
        out.println(strMas[0]+";"+strMas[1]+";"+strMas[2]+";"+strMas[3]+";"+strMas[4]+";"+strMas[5]);
        scanner1.close();
        out.close();

        File sourceFile = new File(fileName);
		File tempFile = new File("tmp.csv");
			
		sourceFile.delete();
		tempFile.renameTo(sourceFile);
        System.out.println("added");
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    
    }

    public static void del(String fileName, String id) {
        boolean f = false;
        try{
        String idLen = id;  
        Scanner scanner = new Scanner(new FileReader(fileName));
        PrintWriter out = new PrintWriter(new FileWriter("tmp.csv"));
        String s;
        

        if(idLen.length() !=3){
            System.out.println("wrong id");
            scanner.close();
            out.close();
            return; 
        } else{
        int id2 = Integer.parseInt(idLen);
        while (scanner.hasNextLine()) {
            s = scanner.nextLine();
            String[] strMas = s.split(";");
            int id1 = Integer.parseInt(strMas[0]);
            if(id2==id1) {
                continue;
            } else{
                out.println(s);
            }
        }
        scanner.close();
        out.close();
        Scanner scanner1 = new Scanner(new FileReader(fileName));
        while (scanner1.hasNextLine()) {
            s = scanner1.nextLine();
            String[] strMas1 = s.split(";");
            int id1 = Integer.parseInt(strMas1[0]);
            if(id1==id2) {
                f = true;
            }
        }
        scanner1.close();
        }
        } catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
        File sourceFile = new File(fileName);
		File tempFile = new File("tmp.csv");
			
		sourceFile.delete();
		tempFile.renameTo(sourceFile);
        if(f == true){
            System.out.println("deleted");
        } else {
            System.out.println("wrong id");
            return;
        }
    }

    public static void print(String fileName) {
        try {
			Scanner scanner = new Scanner(new FileReader(fileName));
			String s;
			System.out.println("------------------------------------------------------------");
            System.out.printf("%-4s%-21s%-11s%6s%10s%-8s\n", "ID", "City", "Date", "Days", "Price", " Vehicle");
            System.out.println("------------------------------------------------------------");
			while (scanner.hasNextLine()) {
				s = scanner.nextLine();
                String[] strMas = s.split(";");
				System.out.printf("%-4s%-21s%-11s%6s%10s%-8s\n", strMas[0],strMas[1],strMas[2],strMas[3],strMas[4]," "+strMas[5]);
			}
			scanner.close();
            System.out.println("------------------------------------------------------------");
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
    }

    public static void find(String fileName, String price) {
        try {
            try{
                double price1 = Double.parseDouble(price);
                Scanner scanner = new Scanner(new FileReader(fileName));
                String s;
                boolean bool = false;
                while (scanner.hasNextLine()) {
                    s = scanner.nextLine();
                    String[] strMas = s.split(";");
                    double price2 = Double.parseDouble(strMas[4]);
                    if(price1>=price2){
                        bool = true;
                    }
                }
                scanner.close();
                if(bool == true){
                    Scanner scanner1 = new Scanner(new FileReader(fileName));
                    String s1;
                    System.out.println("------------------------------------------------------------");
                    System.out.printf("%-4s%-21s%-11s%6s%10s%-8s\n", "ID", "City", "Date", "Days", "Price", " Vehicle");
                    System.out.println("------------------------------------------------------------");
                    while (scanner1.hasNextLine()) {
                        s1 = scanner1.nextLine();
                        String[] strMas = s1.split(";");
                        double price2 = Double.parseDouble(strMas[4]);
                        if(price1>=price2)
                            System.out.printf("%-4s%-21s%-11s%6s%10s%-8s\n", strMas[0],strMas[1],strMas[2],strMas[3],strMas[4]," "+strMas[5]);
                    }
                    scanner1.close();
                    System.out.println("------------------------------------------------------------");
                } else {
                    return;
                }
            } catch(Exception ex){
                    System.out.println("wrong price");
                    return;
            }
        }
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
    }

    public static void avg(String fileName){
        try {
			Scanner scanner = new Scanner(new FileReader(fileName));
			String s;
            double sum = 0;
            int i = 0;
			while (scanner.hasNextLine()) {
				s = scanner.nextLine();
                String[] strMas = s.split(";");
				double price = Double.parseDouble(strMas[4]);
                sum+=price;
                i++;
			}
			scanner.close();
            double average = sum/i;
            System.out.println("average=" + String.format( "%.2f", average ));
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
    }

    public static void sort(String fileName){
        try {
			Scanner scanner = new Scanner(new FileReader(fileName));
			String s;
            ArrayList<String> dateList = new ArrayList<String>();
            PrintWriter out = new PrintWriter(new FileWriter("tmp.csv"));
			while (scanner.hasNextLine()) {
				s = scanner.nextLine();
                dateList.add(s);
			}
			scanner.close();

            Collections.sort(dateList, new Comparator<String>() {
                public int compare(String x1, String x2) {
                    try{
                        String[] mas = x1.split(";");
                        String[] mas2 = x2.split(";");
                        DateFormat f = new SimpleDateFormat("dd/MM/yyyy");  
                        String date = mas[2];
                        String date2 = mas2[2];
                        return f.parse(date).compareTo(f.parse(date2));
                    }  catch (ParseException e) {  
                        throw new IllegalArgumentException(e);  
                    }  
                }
            });

            for(int i=0;i<dateList.size();i++){
                out.println(dateList.get(i));
            }
            out.close();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        File sourceFile = new File(fileName);
		File tempFile = new File("tmp.csv");
			
		sourceFile.delete();
		tempFile.renameTo(sourceFile);
        System.out.println("sorted");
    }

    public static void edit(String fileName, String fullStr){
        String[] strMas = fullStr.split(";");
        boolean b1 = false, b2 = false, b3 = false, b4 = false, b5 = false;
       try {
       Scanner scanner = new Scanner(new FileReader(fileName));
       String s1;
       if(strMas.length == 6){
        String idLen = strMas[0];  
        if(idLen.length() !=3){
            System.out.println("wrong id");
            scanner.close();
            return; 
        } else{
        int id =  Integer.parseInt(strMas[0]);
        boolean found = false;

        while (scanner.hasNextLine()) {
            s1 = scanner.nextLine();
            String[] strMas1 = s1.split(";");
            int id1 = Integer.parseInt(strMas1[0]);
            if(id==id1) {
                found = true;
            }
        }
        scanner.close();
        if(found == false){
            System.out.println("wrong id");
            scanner.close();
            return;
        } 
        }
        if(!strMas[1].isEmpty()){
            String[] city = strMas[1].toLowerCase().trim().split("\\s+");
            String cityName = "";
            for(int i=0;i<city.length;i++){
            city[i] = city[i].substring(0,1).toUpperCase() + city[i].substring(1);
            cityName = city[i]+" ";
            }
            cityName = cityName.trim();
            strMas[1] = cityName;
            b1 = true;
        }
        if(!strMas[2].isEmpty()){
            String[] dateMas = strMas[2].split("/");
            int[] dateMas1 = new int[3];
            String dateStr = "";
            if(dateMas.length != 3){
                System.out.println("wrong date");
                return;
            } else {
                for(int i=0;i<3;i++){
                    dateStr = dateMas[i];
                    dateMas1[i] = Integer.parseInt(dateStr);
                }
                if (dateMas1[0] < 1 || dateMas1[0] > 31 || dateMas1[1] < 1 || dateMas1[1] > 12) {
                    System.out.println("wrong date");
                    return;
                } else {
                    String date = strMas[2];
                }
            }
            b2 = true;
        }
        if(!strMas[3].isEmpty()){
            try{
            int days = Integer.parseInt(strMas[3]);
            b3 = true;
            } catch(Exception ex){
                System.out.println("wrong day count");
                return;
            }
        }
        if(!strMas[4].isEmpty()){
            try{
                double price = Double.parseDouble(strMas[4]);
                String formattedPrice = String.format("%.2f", price);
                strMas[4] = formattedPrice;
                b4 = true;
                } catch(Exception ex){
                    System.out.println("wrong price");
                    return;
                }
        }
        if(!strMas[5].isEmpty()){
            String vehicle = strMas[5].toUpperCase();

            switch (vehicle) {
                case "BUS":
                    break;
                case "PLANE":
                    break;
                case "BOAT":
                    break;
                case "TRAIN":
                    break;
                default:
                    System.out.println("wrong vehicle");
                    return;
            }
            strMas[5] = vehicle;
            b5 = true;
        }

        } else{
            System.out.println("wrong field count");
            scanner.close();
            return;
        }

       Scanner scanner1 = new Scanner(new FileReader(fileName));
        String s;
        PrintWriter out = new PrintWriter(new FileWriter("tmp.csv"));
        int id2 = Integer.parseInt(strMas[0]);
        while (scanner1.hasNextLine()) {
            s = scanner1.nextLine();
            String[] strMas1 = s.split(";");
            int id1 = Integer.parseInt(strMas1[0]);
            if(id1 == id2){
                if(b1 == true){
                    strMas1[1] = strMas[1];
                }
                if(b2 == true){
                    strMas1[2] = strMas[2];
                }
                if(b3 == true){
                    strMas1[3] = strMas[3];
                }
                if(b4 == true){
                    strMas1[4] = strMas[4];
                }
                if(b5 == true){
                    strMas1[5] = strMas[5];
                }
                out.println(strMas1[0]+";"+strMas1[1]+";"+strMas1[2]+";"+strMas1[3]+";"+strMas1[4]+";"+strMas1[5]);
            } else{
                out.println(s);
            }
        }
        scanner1.close();
        out.close();

        File sourceFile = new File(fileName);
		File tempFile = new File("tmp.csv");
			
		sourceFile.delete();
		tempFile.renameTo(sourceFile);
        System.out.println("changed");
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
