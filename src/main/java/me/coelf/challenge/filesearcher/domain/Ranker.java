package me.coelf.challenge.filesearcher.domain;

import java.util.List;
import java.util.Set;

public interface Ranker
{
    List<RankedFile> computeRanking(
            Set<File> matchingFiles,
            String text
    );
}
