package com.example.mantom2132.myapplication; /**
 * Created by mantom2132 on 14/02/2018.
 */

import java.math.BigInteger;
import java.util.Random;

//shamir's secret share class.
public final class ShamirSecretSharing
{

    //Split method, of output type SecretShare.
    public static SecretShare[] split(final BigInteger secret, int needed, int available, BigInteger prime, Random random)
    {
        //Prints out the prime number, which is esentially the range.
        System.out.println("Prime Number: " + prime);
        //creates coeff array of type BigInteger and lenth neeeded.
        final BigInteger[] coeff = new BigInteger[needed];
        //the 0th coefficient is equal to 0.
        coeff[0] = secret;
        //for loop, which repeats itself needed number of times.
        for (int i = 1; i < needed; i++)
        {
            BigInteger r;
            while (true)
            {
                //r is initialised into a random number of bit length equal to random.
                r = new BigInteger(prime.bitLength(), random);
                // if r is greaten than zero and smaller than the prime number
                if (r.compareTo(BigInteger.ZERO) > 0 && r.compareTo(prime) < 0)
                {
                    break;
                }
            }
            //r is assigned to the coefficient.
            coeff[i] = r;
        }
        //Array shares[] of secretShare[] type and of length equal to the
        //available number of shares
        final SecretShare[] shares = new SecretShare[available];
        //for loops in order to increase x and the exponential
        for (int x = 1; x <= available; x++)
        {
            BigInteger accum = secret;
            for (int exp = 1; exp < needed; exp++)
            {
                accum = accum.add(coeff[exp].multiply(BigInteger.valueOf(x).pow(exp).mod(prime))).mod(prime);
            }
            // the share of position x-1 is assigned the
            shares[x - 1] = new SecretShare(x, accum);

        }

        return shares;
    }

    //method to combine the shares
    public static BigInteger combine(final SecretShare[] shares, final BigInteger prime)
    {
        BigInteger accum = BigInteger.ZERO;

        for(int formula = 0; formula < shares.length; formula++)
        {
            BigInteger numerator = BigInteger.ONE;
            BigInteger denominator = BigInteger.ONE;

            for(int count = 0; count < shares.length; count++)
            {
                if(formula == count)
                    continue; // If not the same value

                int startposition = shares[formula].getNumber();
                int nextposition = shares[count].getNumber();

                numerator = numerator.multiply(BigInteger.valueOf(nextposition).negate()).mod(prime); // (numerator * -nextposition) % prime;
                denominator = denominator.multiply(BigInteger.valueOf(startposition - nextposition)).mod(prime); // (denominator * (startposition - nextposition)) % prime;
            }
            BigInteger value = shares[formula].getShare();
            BigInteger tmp = value.multiply(numerator) . multiply(modInverse(denominator, prime));
            accum = prime.add(accum).add(tmp) . mod(prime); //  (prime + accum + (value * numerator * modInverse(denominator))) % prime;
        }

        System.out.println("The secret is: " + accum + "\n");

        return accum;
    }

    private static BigInteger[] gcdD(BigInteger a, BigInteger b)
    {
        if (b.compareTo(BigInteger.ZERO) == 0)
            return new BigInteger[] {a, BigInteger.ONE, BigInteger.ZERO};
        else
        {
            BigInteger n = a.divide(b);
            BigInteger c = a.mod(b);
            BigInteger[] r = gcdD(b, c);
            return new BigInteger[] {r[0], r[2], r[1].subtract(r[2].multiply(n))};
        }
    }

    private static BigInteger modInverse(BigInteger k, BigInteger prime)
    {
        k = k.mod(prime);
        BigInteger r = (k.compareTo(BigInteger.ZERO) == -1) ? (gcdD(prime, k.negate())[2]).negate() : gcdD(prime,k)[2];
        return prime.add(r).mod(prime);
    }

    public static void main()
    {
      /*  boolean hasChosen = false;
        final int CERTAINTY = 256;
        final SecureRandom random = new SecureRandom();
        InputSecretGUI gui = new InputSecretGUI();
        gui.setVisible(true);

        System.out.println("Please input a secret");

        BigInteger sec = BigInteger.valueOf(1);
        gui.setVisible(false);
        Scanner sc = new Scanner(System.in);
        */

    }
}
