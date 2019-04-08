package me.coelf.challenge.filesearcher.adapters;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import me.coelf.challenge.filesearcher.domain.File;
import me.coelf.challenge.filesearcher.domain.RankedFile;
import me.coelf.challenge.filesearcher.domain.Ranker;

public class WordRanker implements Ranker
{
    private static final Comparator<? super RankedFile> RANK_COMPARATOR = new RankComparator();

    @Override
    public List<RankedFile> computeRanking(
            Set<File> matchingFiles,
            String text
    )
    {
        return matchingFiles.stream().map(file -> computeRankFile(file, text))
                .sorted(RANK_COMPARATOR)
                .collect(Collectors.toList());
    }

    private RankedFile computeRankFile(
            File file,
            String text
    )
    {
        if (isPerfectMatch(file, text))
        {
            return new RankedFile(file, 100);
        }
        return computeNonPerfectMatch(file, text);

    }

    private RankedFile computeNonPerfectMatch(
            File file,
            String text
    )
    {

        Set<String> textWords = Stream.of(text.split(" ")).collect(Collectors.toSet());
        long count = textWords.stream()
                .filter(word -> file.getContent().toLowerCase().contains(word))
                .count();

        return new RankedFile(file, calculateRank(count, textWords));

    }

    private int calculateRank(
            long count,
            Set<String> textWords
    )
    {
        return (int) count * 90 / textWords.size();
    }

    private boolean isPerfectMatch(
            File file,
            String text
    )
    {
        return file.getContent().contains(text);
    }

    private static class RankComparator implements Comparator<RankedFile>
    {
        @Override
        public int compare(
                RankedFile o1,
                RankedFile o2
        )
        {
            return Integer.compare(o2.getRank(), o1.getRank());
        }
    }
}
