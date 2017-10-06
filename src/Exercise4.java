import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by shangke on 8/23/17.
 */
public class Exercise4 {

    static int populationSize = 100;
    static int decisionVariableDimension = 9;
    static Random random = new Random(0);
    static int maxIteration = 10000;

    public static class Tuple {
        Population population;
        Individual individual;
        Tuple(Population population, Individual individual) {
            this.population = population;
            this.individual = individual;
        }
    }

    public static class Population {
        ArrayList<Individual> population;
        Population() {
            population = new ArrayList<>();
        }

        public void add(Individual individual) {
            population.add(individual);
        }

        public Individual getIndividual(int index) {
            return population.get(index);
        }

        public void setIndividual(int index, Individual child) {
            population.set(index, child);
        }
    }

    public static class Individual {
        ArrayList<Integer> individual;
        int objectiveValue;
        Individual() {
            individual = new ArrayList<>();
            objectiveValue = 0;
        }

        public void add(Integer element) {
            individual.add(element);
        }

        public Integer getElement(int index) {
            return individual.get(index);
        }

        public void setElement(int index, Integer element){
            individual.set(index, element);
        }

        public void evaluate () {
            int j = 0;
            int s = 0;
            while (j < decisionVariableDimension-1) {
                String tmp = "[" + individual.get(j) + ", " + individual.get(j+1) + ", " + individual.get(j+2) + "]";
                //System.out.println(tmp);
                s = s + getTmpObj(tmp);
                j = j + 3;
            }
            objectiveValue = s;
        }

        public int  getTmpObj (String tmp) {
            if (tmp.equals("[1, 1, 1]")) {
                return 30;
            }
            else if (tmp.equals("[1, 1, 0]")) {
                return 0;
            }
            else if (tmp.equals("[1, 0, 1]")) {
                return 0;
            }
            else if (tmp.equals("[0, 1, 1]")) {
                return 0;
            }
            else if (tmp.equals("[1, 0, 0]")) {
                return 14;
            }
            else if (tmp.equals("[0, 1, 0]")) {
                return 22;
            }
            else if (tmp.equals("[0, 0, 1]")) {
                return 26;
            }
            else {
                return 28;
            }
        }
    }

    public static void main(String[] args) {
        Tuple t = initialization();
        Population P = t.population;
        Individual bestSoFarIndividual = t.individual;
        //System.out.println(bestSoFarIndividual.individual.toString().equals("[1, 0, 1]"));
        int iteration = 1;
        while(iteration < maxIteration) {
            int[] parentIndex = matingSelection(P);
            Individual parent1 = P.getIndividual(parentIndex[0]);
            Individual parent2 = P.getIndividual(parentIndex[1]);
            Individual child = crossOver(parent1,parent2);
            mutation(child);
            child.evaluate();
            if (child.objectiveValue > bestSoFarIndividual.objectiveValue) {
                bestSoFarIndividual = child;
            }
            updatePopulation(P, child);
            iteration++;
        }
        System.out.println(bestSoFarIndividual.individual);
        System.out.println(bestSoFarIndividual.objectiveValue);
    }

    public static Tuple initialization() {

        Population population = new Population();
        Individual best = new Individual();

        for (int i=0; i<populationSize; i++) {
            Individual individual = new Individual();
            for (int j=0; j<decisionVariableDimension; j++) {
                if (random.nextDouble() < 0.5) {
                    individual.add(0);
                }
                else {
                    individual.add(1);
                }
            }
            individual.evaluate();
            if (individual.objectiveValue > best.objectiveValue) {
                best = individual;
            }
            population.add(individual);
        }

        return new Tuple(population,best);
    }


    public static int[] matingSelection(Population population) {
        int parent1Index, parent2Index;
        int index1 = (int) Math.floor(random.nextDouble() * populationSize);
        int index2 = (int) Math.floor(random.nextDouble() * populationSize);
        while(index2 == index1) {
            index2 = (int) Math.floor(random.nextDouble() * populationSize);
        }
        int index3 = (int) Math.floor(random.nextDouble() * populationSize);
        while(index3 == index1 || index3 == index2) {
            index3= (int) Math.floor(random.nextDouble() * populationSize);
        }
        int index4 = (int) Math.floor(random.nextDouble() * populationSize);
        while(index4 == index1 || index4 == index2 || index4 == index3) {
            index4 = (int) Math.floor(random.nextDouble() * populationSize);
        }

        if (population.getIndividual(index1).objectiveValue > population.getIndividual(index2).objectiveValue) {
            parent1Index = index1;
        }
        else {
            parent1Index = index2;
        }

        if (population.getIndividual(index3).objectiveValue > population.getIndividual(index4).objectiveValue) {
            parent2Index = index3;
        }
        else {
            parent2Index = index4;
        }

        return new int[]{parent1Index, parent2Index};
    }

    public static Individual crossOver(Individual individual1, Individual individual2) {

        Individual child = new Individual();
        for (int i=0; i<decisionVariableDimension; i++) {
            if (random.nextDouble()<0.5) {
                child.add(individual1.getElement(i));
            }
            else {
                child.add(individual2.getElement(i));
            }
        }

        return child;
    }

    public static void mutation(Individual child) {

        for (int i=0; i<decisionVariableDimension; i++) {
            if (random.nextDouble() < 1/decisionVariableDimension) {
                if (child.getElement(i) == 0) {
                    child.setElement(i,1);
                }
            }
        }
    }

    public static void updatePopulation(Population population, Individual child) {
        population.population.sort(new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                if (o1.objectiveValue <= o2.objectiveValue) {
                    return 1;
                }
                else {
                    return -1;
                }
            }
        });
        population.setIndividual(populationSize-1,child);

    }
}
