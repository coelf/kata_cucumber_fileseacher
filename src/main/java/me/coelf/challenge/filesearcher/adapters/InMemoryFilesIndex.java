package me.coelf.challenge.filesearcher.adapters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import me.coelf.challenge.filesearcher.domain.File;
import me.coelf.challenge.filesearcher.domain.FilesIndex;
import me.coelf.challenge.filesearcher.usecase.NoMatchesFoundException;

public class InMemoryFilesIndex implements FilesIndex
{

    private Map<String, List<File>> wordIndex = new HashMap<>();

    @Override
    public void add(List<File> files)
    {
        files.forEach(this::addWordsToIndex);
    }

    @Override
    public Set<File> findMatchingFiles(String text)
            throws NoMatchesFoundException
    {
        Set<String> wordsSearch = computeUniqueWordsFromSentence(text);
        Set<File> allMatchingFiles = findWordsInIndex(wordsSearch);
        if (allMatchingFiles.isEmpty())
        {
            throw new NoMatchesFoundException();
        }
        return allMatchingFiles;
    }

    private Set<File> findWordsInIndex(Set<String> wordsSearch)
    {
        Set<File> allMatchingFiles = new HashSet<>();
        wordsSearch.forEach(word -> findMatchingWordFiles(allMatchingFiles, word));
        return allMatchingFiles;
    }

    private void findMatchingWordFiles(
            Set<File> allMatchingFiles,
            String word
    )
    {
        Set<File> filesMatchTheWord = wordIndex.entrySet().stream()
                .filter(entry -> entry.getKey().equals(word))
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        if (!filesMatchTheWord.isEmpty())
        {
            allMatchingFiles.addAll(filesMatchTheWord);
        }
    }

    private Set<String> computeUniqueWordsFromSentence(String text)
    {
        return Stream.of(text.split(" "))
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    private void addWordsToIndex(File file)
    {
        Set<String> words = computeUniqueWordsFromSentence(file.getContent());
        words.forEach(word -> addWordAndFile(word, file));
    }

    private void addWordAndFile(
            String word,
            File file
    )
    {

        if (wordIndex.containsKey(word))
        {
            List<File> files = wordIndex.get(word);
            files.add(file);
            wordIndex.put(word, files);
        }
        else
        {
            List<File> files = new ArrayList<>();
            files.add(file);
            wordIndex.put(word, files);
        }
    }
}
