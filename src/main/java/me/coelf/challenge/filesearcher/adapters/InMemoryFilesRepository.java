package me.coelf.challenge.filesearcher.adapters;

import java.util.List;

import me.coelf.challenge.filesearcher.domain.File;
import me.coelf.challenge.filesearcher.domain.FilesRepository;
import me.coelf.challenge.filesearcher.usecase.PathException;

public class InMemoryFilesRepository implements FilesRepository
{

    private final List<File> files;
    private final String     directoryName;

    public InMemoryFilesRepository(
            String directoryName,
            List<File> files
    )
    {
        this.directoryName = directoryName;
        this.files = files;
    }

    @Override
    public List<File> read(String path)
            throws PathException
    {
        if (directoryName.equals(path))
        {
            return files;
        }
        throw new PathException();
    }
}
