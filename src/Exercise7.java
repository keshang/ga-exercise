import javax.xml.ws.soap.MTOM;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Random;

/**
 * Created by shangke on 8/23/17.
 */
public class Exercise7 {

    static int populationSize = 100;
    static int childrenNum = 100;
    static int decisionVariableDimension = 5;
    static Random random = new Random(0);
    static int maxIteration = 10000;
    static double alpha = 0.8;

    public static class Problem {

        static double upperbound;
        static double lowerbound;
        static String problem;

        Problem(String problemName) {
            if (problemName == "Sphere") {
                upperbound = 100;
                lowerbound = -100;
                problem = "Sphere";
            }
            else if (problemName == "Rastrigin") {
                upperbound = 5.12;
                lowerbound = -5.12;
                problem = "Rastrigin";
            }
            else if (problemName == "Rosenbrock") {
                upperbound = 30;
                lowerbound = -30;
                problem = "Rosenbrock";
            }
            else {
                System.out.println("problem not exist!");
            }
        }

        public static double objectiveFunction(Individual individual) {
            double objectiveValue = 0;
            if (problem.equals("Sphere")) {
                //System.out.println("entered");
                for (int i=0; i<decisionVariableDimension; i++) {
                    objectiveValue += Math.pow(individual.getElement(i),2);
                }
            }
            else if (problem.equals("Rastrigin")) {
                for (int i=0; i<decisionVariableDimension; i++) {
                    objectiveValue += Math.pow(individual.getElement(i),2)-10*Math.cos(2*Math.PI*individual.getElement(i))+10;
                }
            }
            else if (problem.equals("Rosenbrock")) {
                for (int i=0; i<decisionVariableDimension-1; i++) {
                    objectiveValue += 100*Math.pow(individual.getElement(i+1)-Math.pow(individual.getElement(i),2),2)+Math.pow(individual.getElement(i)-1,2);
                }
            }
            return objectiveValue;
        }

    }
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

        public void remove(int individualIndex) { population.remove(individualIndex); }

        public Individual getIndividual(int index) {
            return population.get(index);
        }

        public void setIndividual(int index, Individual child) {
            population.set(index, child);
        }
    }

    public static class Individual {
        ArrayList<Double> individual;
        double objectiveValue;
        Individual() {
            individual = new ArrayList<>();
            objectiveValue = 0;
        }

        public void add(Double element) {
            individual.add(element);
        }

        public Double getElement(int index) {
            return individual.get(index);
        }

        public void setElement(int index, Double element){
            individual.set(index, element);
        }

        public void evaluate() {
            objectiveValue = Problem.objectiveFunction(this);
        }
    }

    public static void main(String[] args) {
        Problem problem = new Problem("Rosenbrock");//Sphere //Rastrigin //Rosenbrock
        Tuple t = initialization();
        Population P = t.population;
        Individual bestSoFarIndividual = t.individual;
        //System.out.println(bestSoFarIndividual.objectiveValue);
        int iteration = 1;
        while(iteration < maxIteration) {
            Population children = new Population();
            for (int i=0; i<childrenNum; i++) {
                int[] parentIndex = matingSelection(P);
                Individual parent1 = P.getIndividual(parentIndex[0]);
                Individual parent2 = P.getIndividual(parentIndex[1]);
                Individual child = crossOver(parent1,parent2);
                child.evaluate();
                children.add(child);
                if (child.objectiveValue < bestSoFarIndividual.objectiveValue) {
                    bestSoFarIndividual = child;
                }
            }

            updatePopulation(P, children);
            iteration++;
        }
        System.out.println(bestSoFarIndividual.individual);
        System.out.println(bestSoFarIndividual.objectiveValue);

        //for (int i=0; i<populationSize; i++) {
            //for (int j=0; j<decisionVariableDimension; j++) {
            //    System.out.print();
            //}
        //    System.out.println(P.getIndividual(i).objectiveValue);
        //}
    }

    public static Tuple initialization() {

        Population population = new Population();
        Individual best = new Individual();
        best.objectiveValue = Double.MAX_VALUE;

        for (int i=0; i<populationSize; i++) {
            Individual individual = new Individual();
            for (int j=0; j<decisionVariableDimension; j++) {
                individual.add((Problem.upperbound-Problem.lowerbound)*random.nextDouble()+Problem.lowerbound);
            }
            //for (int j=0; j<decisionVariableDimension; j++) {
            //    System.out.print(individual.getElement(j));
            //}
            //System.out.println();
            individual.evaluate();
            if (individual.objectiveValue < best.objectiveValue) {
                best = individual;
            }
            population.add(individual);
            //System.out.println(individual.objectiveValue);
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

            double upperbound = Math.max(individual1.getElement(i),individual2.getElement(i))+alpha*Math.abs(individual1.getElement(i)-individual2.getElement(i));
            double lowerbound = Math.min(individual1.getElement(i),individual2.getElement(i))-alpha*Math.abs(individual1.getElement(i)-individual2.getElement(i));

            double u = random.nextDouble()*(upperbound-lowerbound) + lowerbound;

            if (u > Problem.upperbound) {
                u = Problem.upperbound;
            }
            else if (u < Problem.lowerbound) {
                u = Problem.lowerbound;
            }
            child.add(u);
        }

        return child;
    }



    public static void updatePopulation(Population population, Population children) {

        for (int i=0; i<childrenNum; i++) {
            population.add(children.getIndividual(i));
        }

        population.population.sort(new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                if (o1.objectiveValue > o2.objectiveValue) {
                    return 1;
                }
                else if (o1.objectiveValue < o2.objectiveValue) {
                    return -1;
                }
                else {
                    return 0;
                }
            }
        });

        for (int i=0; i<childrenNum; i++) {
            population.remove(populationSize);
        }
    }
}
