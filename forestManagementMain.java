package ForestryManagement;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class forestManagementMain {
    public static void main(String[] args) {
        ArrayList<Forest> forests = new ArrayList<>();
        for (String forestName : args) {
            Forest forest = loadForestFromCSV(forestName + ".csv");
            if (forest != null) {
                forests.add(forest);
            } else {
                System.out.println("Error loading forest: " + forestName);
            }
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Forestry Simulation\n" +
                "----------------------------------");

        for (Forest forest : forests) {
            System.out.println("Initializing from " + forest.getName());
            while (true) {
                System.out.println("(P)rint, (A)dd, (C)ut, (G)row, (R)eap, (S)ave, (L)oad, (N)ext, e(X)it : ");
                String input = scanner.nextLine().toLowerCase();
                switch (input) {
                    case "p":
                        forest.printForest();
                        break;
                    case "a":
                        Tree randomTree = forest.generateRandomTree(); // Call the method from the Forest class
                        forest.addTree(randomTree);
                        System.out.println("Randomly generated tree added to the forest.");
                        break;
                    case "c":
                        System.out.print("Tree number to cut down: ");
                        try {
                            int indexToCut = Integer.parseInt(scanner.nextLine());
                            if (indexToCut >= 0 && indexToCut < forest.getTrees().size()) {
                                forest.cutTree(indexToCut);
                            } else {
                                System.out.println("Tree number " + indexToCut + " does not exist.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("That is not an integer");
                        }
                        break;
                    case "r":
                        // Reap trees
                        System.out.print("Height to reap from: ");
                        try {
                            double heightToReap = Double.parseDouble(scanner.nextLine());
                            forest.reapTrees(heightToReap);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid height.");
                        }
                        break;
                    case "g":
                        forest.simulateYear();
                        break;
                    case "s":
                        forest.saveForestToFile();
                        break;
                    case "l":
                        System.out.print("Enter forest name: ");
                        String forestNameToLoad = scanner.nextLine();
                        Forest loadedForest = Forest.loadForestFromFile(forestNameToLoad + ".db");
                        if (loadedForest != null) {
                            System.out.println("Forest loaded successfully: " + loadedForest.getName());
                            forest = loadedForest;
                        } else {
                            System.out.println("Old forest retained");
                        }
                        break;
                    case "n":
                        int currentIndex = forests.indexOf(forest);
                        if (currentIndex == -1 || currentIndex == forests.size() - 1) {
                            System.out.println("No next forest available.");
                        } else {
                            forest = forests.get(currentIndex + 1);
                            System.out.println("Moving to the next forest: " + forest.getName());
                            System.out.println("Initializing from Acadian");
                        }
                        break;
                    case "x":
                        // Exit program
                        System.out.println("Exiting the Forestry Simulation");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid menu option, try again");
                }
            }
        }
    }

    // Method to load forest from CSV file
    private static Forest loadForestFromCSV(String fileName) {
        Forest forest = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            forest = new Forest(fileName.replace(".csv", ""));
            while ((line = reader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ",");

                if (tokenizer.countTokens() == 4) {
                    String species = tokenizer.nextToken();
                    int yearOfPlanting = Integer.parseInt(tokenizer.nextToken());
                    double height = Double.parseDouble(tokenizer.nextToken());
                    double growthRate = Double.parseDouble(tokenizer.nextToken());
                    Tree tree = new Tree(TreeSpecies.valueOf(species.toUpperCase()), yearOfPlanting, height, growthRate);
                    forest.addTree(tree);
                } else {
                    System.out.println("Invalid line in CSV file: " + line);
                }

            }
        } catch (IOException e) {
            System.out.println("Error opening/reading " + fileName);
        }
        return forest;
    }

}

