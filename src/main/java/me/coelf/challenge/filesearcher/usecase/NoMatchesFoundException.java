package me.coelf.challenge.filesearcher.usecase;

public class NoMatchesFoundException extends Exception
{

    public NoMatchesFoundException()
    {
        super("no matches found");
    }
}
