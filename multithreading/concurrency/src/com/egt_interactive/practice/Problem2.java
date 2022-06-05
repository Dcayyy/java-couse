package com.egt_interactive.practice;

import java.math.BigInteger;
import java.util.Set;
import java.util.TreeSet;

public class Problem2 {
    public static void main(String[] args) throws InterruptedException {
        final long limit = 1_000_000;
        final int numberOfThreads = 5;

        int lowerBound = 0;
        int upperBound = (int) limit / numberOfThreads;
        final int granularity = (int) limit / numberOfThreads;

        PrimeNumbers primeNumbers = null;
        for (int i = 0; i < numberOfThreads; i++) {
            primeNumbers = new PrimeNumbers(lowerBound, upperBound);
            new Thread(primeNumbers).start();
            lowerBound = upperBound + 1;
            upperBound = upperBound + granularity;
        }

        Thread.sleep(1500);

        System.out.println(primeNumbers.getPrimeTreeSetSize());
    }
}

class PrimeNumbers implements Runnable {
    private final long limit;
    private final long lowerBound;
    private static PrimeTreeSet primeNumbers;

    public PrimeNumbers(long lowerBound, long upperBound) {
        this.lowerBound = lowerBound;
        this.limit = upperBound;
        primeNumbers = new PrimeTreeSet();
    }

    @Override
    public void run() {
        findPrimeNumbers();
    }

    void findPrimeNumbers() {
        BigInteger result;
        BigInteger currentNumber;

        for (long i = lowerBound; i <= limit; i++) {
            currentNumber = new BigInteger(Long.toString(i));

            result = currentNumber.nextProbablePrime();

            if (Integer.parseInt(result.toString()) < limit) {
                primeNumbers.addPrimeNumber(result);
            }

            i = Integer.parseInt(result.toString());
        }
    }

    public PrimeTreeSet getPrimeTreeSet() {
        return primeNumbers;
    }

    public int getPrimeTreeSetSize() {
        return primeNumbers.getPrimeNumbers().size();
    }
}


class PrimeTreeSet {
    private Set<BigInteger> primeNumbers;

    public PrimeTreeSet() {
        primeNumbers = new TreeSet<>();
    }

    public synchronized void addPrimeNumber(BigInteger primeNumber) {
        this.primeNumbers.add(primeNumber);
    }

    public synchronized void printPrimeNumbers() {
        primeNumbers.forEach(n -> System.out.print(n + " "));
    }

    public synchronized Set<BigInteger> getPrimeNumbers() {
        return primeNumbers;
    }
}
