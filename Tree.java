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
    } //tree Species method

    public double getHeight() {
        return height;
    } //get height method

    public void simulateGrowth() {
        double growth = height * (growthRate / 100);
        height += growth;
    }// simulate growth method

    @Override
    public String toString() {
        return String.format("%s %d %.2f' %.1f%%", species, yearOfPlanting, height, growthRate);
    } // to string method

}//end of main
