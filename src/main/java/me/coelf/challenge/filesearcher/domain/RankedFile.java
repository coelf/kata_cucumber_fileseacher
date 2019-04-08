package me.coelf.challenge.filesearcher.domain;

public class RankedFile
{
    private final File file;
    private final int  rank;

    public RankedFile(
            File file,
            int rank
    )
    {
        this.file = file;
        this.rank = rank;
    }

    public int getRank()
    {
        return rank;
    }

    @Override
    public String toString()
    {
        return file.getName() + " : " + rank + "%";
    }
}
