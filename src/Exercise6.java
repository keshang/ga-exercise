import java.util.*;

/**
 * Created by shangke on 8/23/17.
 */
public class Exercise6 {

    static int populationSize = 200;
    static int childrenNum = 100;
    static int decisionVariableDimension;
    static Random random = new Random(0);
    static int maxIteration = 10000;

    public static class TSPdata {
        public static double[] tsp_data_x;
        public static double[] tsp_data_y;
        public static double[][] distance;

        TSPdata(String problemName) {

            if (problemName == "wi29") {
                decisionVariableDimension = 29;
                tsp_data_x = new double[]{20833.3333,
                        20900.0000,
                        21300.0000,
                        21600.0000,
                        21600.0000,
                        21600.0000,
                        22183.3333,
                        22583.3333,
                        22683.3333,
                        23616.6667,
                        23700.0000,
                        23883.3333,
                        24166.6667,
                        25149.1667,
                        26133.3333,
                        26150.0000,
                        26283.3333,
                        26433.3333,
                        26550.0000,
                        26733.3333,
                        27026.1111,
                        27096.1111,
                        27153.6111,
                        27166.6667,
                        27233.3333,
                        27233.3333,
                        27266.6667,
                        27433.3333,
                        27462.5000};
                tsp_data_y = new double[]{17100.0000,
                        17066.6667,
                        13016.6667,
                        14150.0000,
                        14966.6667,
                        16500.0000,
                        13133.3333,
                        14300.0000,
                        12716.6667,
                        15866.6667,
                        15933.3333,
                        14533.3333,
                        13250.0000,
                        12365.8333,
                        14500.0000,
                        10550.0000,
                        12766.6667,
                        13433.3333,
                        13850.0000,
                        11683.3333,
                        13051.9444,
                        13415.8333,
                        13203.3333,
                        9833.3333,
                        10450.0000,
                        11783.3333,
                        10383.3333,
                        12400.0000,
                        12992.2222};
                distance = new double[decisionVariableDimension][decisionVariableDimension];
                for (int i=0; i<decisionVariableDimension; i++) {
                    for (int j=0; j<decisionVariableDimension; j++) {
                        distance[i][j] = Math.sqrt(Math.pow(tsp_data_x[i] - tsp_data_x[j],2)
                                + Math.pow(tsp_data_y[i] - tsp_data_y[j],2));
                    }
                }
            }
            else if (problemName == "dj38") {
                decisionVariableDimension = 38;
                tsp_data_x = new double[]{11003.611100,
                        11108.611100,
                        11133.333300,
                        11155.833300,
                        11183.333300,
                        11297.500000,
                        11310.277800,
                        11416.666700,
                        11423.888900,
                        11438.333300,
                        11461.111100,
                        11485.555600,
                        11503.055600,
                        11511.388900,
                        11522.222200,
                        11569.444400,
                        11583.333300,
                        11595.000000,
                        11600.000000,
                        11690.555600,
                        11715.833300,
                        11751.111100,
                        11770.277800,
                        11785.277800,
                        11822.777800,
                        11846.944400,
                        11963.055600,
                        11973.055600,
                        12058.333300,
                        12149.444400,
                        12286.944400,
                        12300.000000,
                        12355.833300,
                        12363.333300,
                        12372.777800,
                        12386.666700,
                        12421.666700,
                        12645.000000};
                tsp_data_y = new double[]{42102.500000,
                        42373.888900,
                        42885.833300,
                        42712.500000,
                        42933.333300,
                        42853.333300,
                        42929.444400,
                        42983.333300,
                        43000.277800,
                        42057.222200,
                        43252.777800,
                        43187.222200,
                        42855.277800,
                        42106.388900,
                        42841.944400,
                        43136.666700,
                        43150.000000,
                        43148.055600,
                        43150.000000,
                        42686.666700,
                        41836.111100,
                        42814.444400,
                        42651.944400,
                        42884.444400,
                        42673.611100,
                        42660.555600,
                        43290.555600,
                        43026.111100,
                        42195.555600,
                        42477.500000,
                        43355.555600,
                        42433.333300,
                        43156.388900,
                        43189.166700,
                        42711.388900,
                        43334.722200,
                        42895.555600,
                        42973.333300};
                distance = new double[decisionVariableDimension][decisionVariableDimension];
                for (int i=0; i<decisionVariableDimension; i++) {
                    for (int j=0; j<decisionVariableDimension; j++) {
                        distance[i][j] = Math.sqrt(Math.pow(tsp_data_x[i] - tsp_data_x[j],2)
                                + Math.pow(tsp_data_y[i] - tsp_data_y[j],2));
                    }
                }
            }
            else {
                System.out.println("Problem not exist!");
            }
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

        public void evaluate() {

            for (int i=0; i<decisionVariableDimension-1; i++) {
                objectiveValue += TSPdata.distance[individual.get(i)][individual.get(i+1)];
            }
            objectiveValue += TSPdata.distance[individual.get(decisionVariableDimension-1)][individual.get(0)];
        }
    }

    public static void main(String[] args) {
        TSPdata tspData = new TSPdata("dj38");
        Tuple t = initialization();
        Population P = t.population;
        Individual bestSoFarIndividual = t.individual;
        int iteration = 1;
        while(iteration < maxIteration) {
            Population children = new Population();
            for (int i=0; i<childrenNum; i++) {
                int[] parentIndex = matingSelection(P);
                Individual parent1 = P.getIndividual(parentIndex[0]);
                Individual parent2 = P.getIndividual(parentIndex[1]);
                Individual child = crossOver(parent1,parent2);
                mutation(child);
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
    }

    public static Tuple initialization() {

        Population population = new Population();
        Individual best = new Individual();
        best.objectiveValue = Integer.MAX_VALUE;

        for (int i=0; i<populationSize; i++) {
            Individual individual = new Individual();
            ArrayList<Integer> cities = new ArrayList<>();
            for (int j=0; j<decisionVariableDimension; j++) {
                cities.add(j);
            }
            for (int j=0; j<decisionVariableDimension; j++) {
                int city = (int) Math.floor(random.nextDouble()*cities.size());
                individual.add(cities.get(city));
                cities.remove(city);
            }

            individual.evaluate();
            if (individual.objectiveValue < best.objectiveValue) {
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
            child.add(i);
        }
        //child.individual = new ArrayList<>(decisionVariableDimension);

        int m1 = (int) Math.floor(random.nextDouble()*decisionVariableDimension);
        int m2 = (int) Math.floor(random.nextDouble()*decisionVariableDimension);
        while(m1 == m2) {
            m2 = (int) Math.floor(random.nextDouble()*decisionVariableDimension);
        }
        int l = Math.min(m1,m2);
        int u = Math.max(m1,m2);

        for (int i=l; i<=u; i++) {
            //System.out.println(child.individual.size());
            child.setElement(i,individual2.getElement(i));
        }

        int index1 = u+1;
        int index2 = u+1;
        int t=0;
        boolean isok = true;
        while(t!=decisionVariableDimension) {
            if (index1 == decisionVariableDimension) {
                index1 = 0;
            }
            if (index2 == decisionVariableDimension) {
                index2 = 0;
            }
            int city = individual1.getElement(index1);
            for (int i=l; i<=u; i++) {
                if (city == individual2.getElement(i)) {
                    isok = false;
                    break;
                }
            }
            if (isok == true) {
                child.setElement(index2,individual1.getElement(index1));
                index1++;
                index2++;
            }
            else {
                index1++;
                isok = true;
            }
            t++;
        }

        return child;
    }

    public static void mutation(Individual child) {

        if (random.nextDouble() < 1/decisionVariableDimension) {
            int m1 = (int) Math.floor(random.nextDouble()*decisionVariableDimension);
            int m2 = (int) Math.floor(random.nextDouble()*decisionVariableDimension);
            while(m1 == m2) {
                m2 = (int) Math.floor(random.nextDouble()*decisionVariableDimension);
            }
            int l = Math.min(m1,m2);
            int u = Math.max(m1,m2);

            List<Integer> sublist = child.individual.subList(l,u);
            Collections.reverse(sublist);

            for (int i=l; i<u; i++) {
                child.setElement(i,sublist.get(i-l));
            }
        }
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
