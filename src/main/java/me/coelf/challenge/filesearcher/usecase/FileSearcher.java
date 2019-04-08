package me.coelf.challenge.filesearcher.usecase;

import java.util.List;
import java.util.Set;

import me.coelf.challenge.filesearcher.domain.File;
import me.coelf.challenge.filesearcher.domain.FilesIndex;
import me.coelf.challenge.filesearcher.domain.FilesRepository;
import me.coelf.challenge.filesearcher.domain.RankedFile;
import me.coelf.challenge.filesearcher.domain.Ranker;

public class FileSearcher
{
    private final FilesRepository filesRepository;
    private final FilesIndex      index;
    private final Ranker          ranker;

    public FileSearcher(
            FilesRepository filesRepository,
            FilesIndex index,
            Ranker ranker
    )
    {
        this.filesRepository = filesRepository;
        this.index = index;
        this.ranker = ranker;
    }

    public String read(String directoryName)
            throws PathException
    {
        List<File> files = filesRepository.read(directoryName);
        index.add(files);
        return printNbReadFiles(directoryName, files);
    }

    private String printNbReadFiles(
            String directoryName,
            List<File> files
    )
    {
        return files.size() + " file(s) read in directory \"" + directoryName + "\"";
    }

    public List<RankedFile> search(String textSearch)
            throws NoMatchesFoundException, ExitException
    {
        if (":quit".equals(textSearch.trim()))
            throw new ExitException();
        final Set<File> matchingFiles = index.findMatchingFiles(textSearch);
        return ranker.computeRanking(matchingFiles, textSearch);
    }

}
