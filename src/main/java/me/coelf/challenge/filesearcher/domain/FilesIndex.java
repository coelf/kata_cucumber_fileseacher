package me.coelf.challenge.filesearcher.domain;

import java.util.List;
import java.util.Set;

import me.coelf.challenge.filesearcher.usecase.NoMatchesFoundException;

public interface FilesIndex
{
    void add(List<File> files);

    Set<File> findMatchingFiles(String text)
            throws NoMatchesFoundException;
}
