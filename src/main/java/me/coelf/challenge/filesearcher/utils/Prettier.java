package me.coelf.challenge.filesearcher.utils;

import java.util.List;
import java.util.stream.Collectors;

import me.coelf.challenge.filesearcher.domain.RankedFile;

public class Prettier
{
    private Prettier()
    {
        //no access
    }

    public static String print(List<RankedFile> rankedFiles)
    {
        return rankedFiles.stream()
                .map(RankedFile::toString)
                .collect(Collectors.joining("\n"));
    }
}
