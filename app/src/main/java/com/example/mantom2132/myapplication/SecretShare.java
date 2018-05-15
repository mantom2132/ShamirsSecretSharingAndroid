package com.example.mantom2132.myapplication; /**
 * Created by mantom2132 on 14/02/2018.
 */

import java.math.BigInteger;

//Secret share class
public class SecretShare
{
    //constructor class using two arguements, share and number.
    public SecretShare(final int number, final BigInteger share)
    {
        this.number = number;
        this.share = share;
    }
    //method to retrieve the SecretShare's number
    public int getNumber()
    {
        return number;
    }
    //method to retrieve the secretShare's share.
    public BigInteger getShare()
    {
        return share;
    }
    //overide method to print out the number and share.
    @Override
    public String toString()
    {
        return "SecretShare [num=" + number + ", share=" + share + "]";
    }

    private final int number;
    private final BigInteger share;
}