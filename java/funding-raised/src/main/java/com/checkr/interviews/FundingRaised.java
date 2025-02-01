package com.checkr.interviews;

import java.util.*;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;

public class FundingRaised {
    public static List<Map<String, String>> where(Map<String, String> options) throws IOException {
        List<String[]> csvData = new ArrayList<String[]>();
        CSVReader reader = new CSVReader(new FileReader("startup_funding.csv"));
        String[] row = null;

        while((row = reader.readNext()) != null) {
            csvData.add(row);
        }

        reader.close();
        csvData.remove(0);

        csvData = getOption(options, csvData);

        List<Map<String, String>> output = new ArrayList<Map<String, String>>();

        for(int i = 0; i < csvData.size(); i++) {
            Map<String, String> mapped = new HashMap<String, String> ();
            addToMap(mapped, csvData, i);
            output.add(mapped);
        }

        return output;
    }

    private static List<String[]> getOption(Map<String, String> options, List<String[]> csvData) {
        if(isCompanyName(options)) {
            List<String[]> results = new ArrayList<String[]> ();

            for(int i = 0; i < csvData.size(); i++) {
                if(csvData.get(i)[1].equals(options.get("company_name"))) {
                    results.add(csvData.get(i));
                }
            }
            csvData = results;
        }

        if(options.containsKey("city")) {
            List<String[]> results = new ArrayList<String[]> ();

            for(int i = 0; i < csvData.size(); i++) {
                if(csvData.get(i)[4].equals(options.get("city"))) {
                    results.add(csvData.get(i));
                }
            }
            csvData = results;
        }

        if(options.containsKey("state")) {
            List<String[]> results = new ArrayList<String[]> ();

            for(int i = 0; i < csvData.size(); i++) {
                if(csvData.get(i)[5].equals(options.get("state"))) {
                    results.add(csvData.get(i));
                }
            }
            csvData = results;
        }

        if(options.containsKey("round")) {
            List<String[]> results = new ArrayList<String[]> ();

            for(int i = 0; i < csvData.size(); i++) {
                if(csvData.get(i)[9].equals(options.get("round"))) {
                    results.add(csvData.get(i));
                }
            }
            csvData = results;
        }
        return csvData;
    }

    public static Map<String, String> findByMapAndString(Map<String, String> options) throws IOException, NoSuchEntryException {
        List<String[]> csvData = new ArrayList<String[]>();
        CSVReader reader = new CSVReader(new FileReader("startup_funding.csv"));
        String[] row = null;

        while((row = reader.readNext()) != null) {
            csvData.add(row);
        }

        reader.close();
        csvData.remove(0);
        Map<String, String> mapped = new HashMap<String, String> ();

        for(int i = 0; i < csvData.size(); i++) {
            if(isCompanyName(options)) {
                if(csvData.get(i)[1].equals(options.get("company_name"))) {
                    addToMap(mapped, csvData, i);
                } else {
                    continue;
                }
            }

            if(options.containsKey("city")) {
                if(csvData.get(i)[4].equals(options.get("city"))) {
                    addToMap(mapped, csvData, i);
                } else {
                    continue;
                }
            }

            if(options.containsKey("state")) {
                if(csvData.get(i)[5].equals(options.get("state"))) {
                    addToMap(mapped, csvData, i);
                } else {
                    continue;
                }
            }

            if(options.containsKey("round")) {
                if(csvData.get(i)[9].equals(options.get("round"))) {
                    addToMap(mapped, csvData, i);
                } else {
                    continue;
                }
            }

            return mapped;
        }

        throw new NoSuchEntryException();
    }

    private static boolean isCompanyName(Map<String, String> options) {
        return options.containsKey("company_name");
    }

    private static void addToMap(Map<String, String> mapped, List<String[]> csvData, int i) {
        mapped.put("permalink", csvData.get(i)[0]);
        mapped.put("company_name", csvData.get(i)[1]);
        mapped.put("number_employees", csvData.get(i)[2]);
        mapped.put("category", csvData.get(i)[3]);
        mapped.put("city", csvData.get(i)[4]);
        mapped.put("state", csvData.get(i)[5]);
        mapped.put("funded_date", csvData.get(i)[6]);
        mapped.put("raised_amount", csvData.get(i)[7]);
        mapped.put("raised_currency", csvData.get(i)[8]);
        mapped.put("round", csvData.get(i)[9]);
    }

    public static void main(String[] args) {
        try {
            Map<String, String> options = new HashMap<String, String> ();
            options.put("company_name", "Facebook");
            options.put("round", "a");
            System.out.print(FundingRaised.where(options).size());
        } catch(IOException e) {
            System.out.print(e.getMessage());
            System.out.print("error");
        }
    }
}

class NoSuchEntryException extends Exception {}
