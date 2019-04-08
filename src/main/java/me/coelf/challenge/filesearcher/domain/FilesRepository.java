package me.coelf.challenge.filesearcher.domain;

import java.io.IOException;
import java.util.List;

import me.coelf.challenge.filesearcher.usecase.PathException;

public interface FilesRepository
{
    List<File> read(String path)
            throws PathException;
}
