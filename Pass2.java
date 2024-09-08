package Macroprocessor;

import java.io.*;
import java.util.*;

class intWrapper {
    public int value;
    public intWrapper(int value) {
        this.value = value;
    }
}
class Pass2 {
    public static void main(String[] args) {
        List<String> MDT = new ArrayList<>();
        Map<String, Integer> MNT = new HashMap<>();
        List<String> ALA = new ArrayList<>();
        Map<Integer, String> actualALA = new HashMap<>();
        intWrapper actualala = new intWrapper(1);
        boolean canwrite = true;

        try {
            Scanner mdtScanner = new Scanner(new File("MDT.txt"));
            while (mdtScanner.hasNextLine()) {
                MDT.add(mdtScanner.nextLine().substring(2));
            }
            mdtScanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: MDT file not found.");
            e.printStackTrace();
        }

        try {
            Scanner mntScanner = new Scanner(new File("MNT.txt"));
            while (mntScanner.hasNextLine()) {
                String[] mntEntry = mntScanner.nextLine().trim().split("\\s+");
                MNT.put(mntEntry[1], Integer.parseInt(mntEntry[2]));
            }
            mntScanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: MNT file not found.");
            e.printStackTrace();
        }

        try {
            Scanner alaScanner = new Scanner(new File("ALA.txt"));
            while (alaScanner.hasNextLine()) {
                String[] alaEntry = alaScanner.nextLine().split("\\s+");
                ALA.add(alaEntry[1]);
            }
            alaScanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: ALA file not found.");
            e.printStackTrace();
        }

        try (BufferedWriter outputWriter = new BufferedWriter(new FileWriter("macro_expandedcode.txt"))) {
            Scanner scanner = new Scanner(new File("macro_intermediate.txt"));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] tokens = line.split("[,\\s]+");

                for (int j=0; j<tokens.length; j++) {
                    if (MNT.containsKey(tokens[j])) {
                        int mdtIndex = MNT.get(tokens[j]);

                        for (int i = 0; i < tokens.length; i++) {
                            if (!MNT.containsKey(tokens[i])) {
                                actualALA.put(actualala.value, tokens[i]);
                                actualala.value++;
                            }
                        }

                        for (int i = mdtIndex; i < MDT.size() - 1 && !MDT.get(i).equalsIgnoreCase("MEND"); i++) {
                            String[] mdtTokens = MDT.get(i).split("[,\\s]+");
                            StringBuilder expandedLine = new StringBuilder();

                            for (String mdtToken : mdtTokens) {
                                if (mdtToken.startsWith("#")) {
                                    int argIndex = Integer.parseInt(mdtToken.substring(1)) - 1;
                                    expandedLine.append(actualALA.get(argIndex + 1)).append(" "); 
                                } else {
                                    expandedLine.append(mdtToken).append(" ");
                                }
                            }
                            outputWriter.write(expandedLine.toString().trim());
                            outputWriter.newLine();
                        }
                    } 
                    else {
                        continue;
                    }
                }
                for (int j=0; j<tokens.length; j++) {
                    if (MNT.containsKey(tokens[j])) {
                        canwrite = false;
                        break;
                    }
                    canwrite = true;
                }
                if (canwrite) {
                    outputWriter.write(line);
                    outputWriter.newLine();
                }
                actualala.value = 1;
                
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: Intermediate file not found.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
