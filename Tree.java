package ForestryManagement;

import java.io.Serializable;

enum TreeSpecies {
    BIRCH, MAPLE, FIR
}

public class Tree implements Serializable {
    private TreeSpecies species;
    private int yearOfPlanting;
    private double height;
    private double growthRate;

    public Tree(TreeSpecies species, int yearOfPlanting, double height, double growthRate) {
        this.species = species;
        this.yearOfPlanting = yearOfPlanting;
        this.height = height;
        this.growthRate = growthRate;
    }

    public TreeSpecies getSpecies() {
        return species;
    }

    public double getHeight() {
        return height;
    }

    public void simulateGrowth() {
        double growth = height * (growthRate / 100);
        height += growth;
    }

    @Override
    public String toString() {
        return String.format("%s %d %.2f' %.1f%%", species, yearOfPlanting, height, growthRate);
    }
}
