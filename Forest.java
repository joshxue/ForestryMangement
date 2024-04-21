package ForestryManagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Forest implements Serializable {
    private String name;
    private ArrayList<Tree> trees = new ArrayList<>();

    public Forest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addTree(Tree tree) {
        trees.add(tree);
    }

    public void cutTree(int index) {
        if (index >= 0 && index < trees.size()) {
            trees.remove(index);
        } else {
            System.out.println("Invalid tree index.");
        }
    }

    public void simulateYear() {
        for (Tree tree : trees) {
            tree.simulateGrowth();
        }
    }
    public void reapTrees(double heightToReap) {
        ArrayList<Tree> treesToRemove = new ArrayList<>();
        for (Tree tree : trees) {
            if (tree.getHeight() > heightToReap) {
                treesToRemove.add(tree);
            }
        }
        // Remove trees taller than the specified height
        for (Tree treeToRemove : treesToRemove) {
            System.out.println("Reaping the tall tree  " + treeToRemove);
            trees.remove(treeToRemove);
            // Replace reaped tree with a new one
            Tree newTree = generateRandomTree();
            System.out.println("Replaced with new tree " + newTree);
            trees.add(newTree);
        }
    }

    public Forest getNextForest(ArrayList<Forest> allForests) {
        int currentIndex = allForests.indexOf(this);
        if (currentIndex == -1 || currentIndex == allForests.size() - 1) {
            System.out.println("No next forest available.");
            return null;
        }
        return allForests.get(currentIndex + 1);
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }



    public Tree generateRandomTree() {
        TreeSpecies[] species = TreeSpecies.values();
        TreeSpecies randomSpecies = species[(int) (Math.random() * species.length)];
        int yearOfPlanting = (int) (Math.random() * 25) + 2000;
        double height = Math.random() * 10 + 10;
        double growthRate = Math.random() * 10 + 10;
        return new Tree(randomSpecies, yearOfPlanting, height, growthRate);
    }

    public void printForest() {
        System.out.println("Forest name: " + name);
        for (int i = 0; i < trees.size(); i++) {
            System.out.println("    " + i + " " + trees.get(i));
        }
        System.out.println("There are " + trees.size() + " trees, with an average height of " + String.format("%.2f", calculateAverageHeight()) + ".");
    }

    public double calculateAverageHeight() {
        double totalHeight = 0;
        for (Tree tree : trees) {
            totalHeight += tree.getHeight();
        }
        return trees.isEmpty() ? 0 : totalHeight / trees.size();

    }

    public static Forest loadForestFromFile(String fileName) {
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            return (Forest) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading forest from file: " + fileName);
            return null;
        }
    }

    public void saveForestToFile() {
        String fileName = name + ".db";
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(this);
        } catch (IOException e) {
            System.out.println("Error saving forest to file: " + fileName);
        }
    }

}

