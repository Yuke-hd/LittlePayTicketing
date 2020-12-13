import Model.Record;
import Model.Graph.Stop;
import Model.Trip.BaseTrip;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    // region variables
    private static final String[] HEADERS = {
            "Started",
            "Finished",
            "DurationSecs",
            "FromStopId",
            "ToStopId",
            "ChargeAmount",
            "CompanyId",
            "BusID",
            "PAN",
            "Status"
    };
    private static final String COMMA_DELIMITER = ",";
    private static final ArrayList<Record> records = new ArrayList<>();
    private static final ArrayList<BaseTrip> trips = new ArrayList<>();
    private static final Stop priceTable = new Stop();
    private static final HashMap<String, List<Record>> map = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    // endregion
    public static void main(String[] args) throws IOException {
        buildPriceTable();
        try {
            readFile();
        } catch (IOException e) {
            System.out.println("File not found, make sure to place the input.csv in the same directory as the jar file");
            System.exit(0);
        }
        while (true) {
            System.out.println("============== MENU ==============");
            System.out.println("1. add stops");
            System.out.println("2. calculate result");
            System.out.println("3. print result");
            System.out.println("0. exit");
            int selection = scanner.nextInt();
            switch (selection) {
                case 1:
                    addTrip();
                    break;
                case 2:
                    for (List<Record> v : map.values()) {
                        recordsToTrips(v);
                    }
                    System.out.println("Success");
                    break;
                case 3:
                    output();
                    createCSVFile();
                    System.out.println("Success");
                    break;
                case 0:
                    System.out.println("Exit");
                    System.exit(0);
                default:
                    break;
            }
        }
    }

    private static void addTrip() {
        System.out.println("From stop: ");
        int s = scanner.nextInt();
        System.out.println("To stop: ");
        int w = scanner.nextInt();
        System.out.println("Price: ");
        float d = scanner.nextFloat();
        priceTable.add(s, w, d);
    }

    private static void output() {
        for (BaseTrip bt : trips) {
            bt.calculatePrice(priceTable);
            //System.out.println(bt);
        }
    }

    private static void sep() {
        for (Record record : records) {
            String customerPAN = record.getPAN();
            if (map.containsKey(customerPAN)) {
                map.get(customerPAN).add(record);
            }
        }
    }

    private static void recordsToTrips(List<Record> records) {
        int tripSize;
        for (Record record : records) {
            if (record.isTapOn()) { // if the record is tap on, creat a new trip entry with tap info
                trips.add(new BaseTrip(record.getDateTimeUTC(), record.getStopId(), record.getCompanyId(), record.getBusID(), record.getPAN()));
            } else {
                tripSize = trips.size();
                BaseTrip lastEntry = trips.get(tripSize - 1);
                boolean busIDMatch = record.getBusID().equals(lastEntry.getBusID()); // scenario 3 in readme
                if (tripSize == 0 || !busIDMatch) {
                    continue;
                }
                lastEntry.addInfo(record.getDateTimeUTC(), record.getStopId());
            }
        }
    }

    private static void readFile() throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File("input.csv"))) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                Record record = new Record(scanner.nextLine());
                String customerPAN = record.getPAN();
                if (!map.containsKey(customerPAN)) {
                    map.put(customerPAN, new ArrayList<>());
                }
                map.get(customerPAN).add(record);
            }
        }
    }

    private static void createCSVFile() throws IOException {
        FileWriter out = new FileWriter("trips.csv");
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
                .withHeader(HEADERS).withEscape(' ').withQuoteMode(QuoteMode.NONE))) {
            trips.forEach((baseTrip) -> {
                try {
                    printer.printRecord(baseTrip.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void buildPriceTable() {
        priceTable.init();
    }
}
