package me.coelf.challenge.filesearcher.adapters;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.coelf.challenge.filesearcher.domain.File;
import me.coelf.challenge.filesearcher.domain.FilesRepository;
import me.coelf.challenge.filesearcher.usecase.PathException;

public class FileSystemRepository implements FilesRepository
{
    @Override
    public List<File> read(String path)
            throws PathException
    {

        List<Path> paths = new ArrayList<>();

        try
        {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(path),
                    file -> file.toFile().isFile()))
            {
                directoryStream.forEach(paths::add);
                return paths.stream().map(this::toFileDomain).collect(Collectors.toList());
            }

        }
        catch (IOException e)
        {
            throw new PathException();
        }
    }

    private File toFileDomain(Path path)
    {

        String content = null;
        try
        {
            content = new String(Files.readAllBytes(path));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return new File(path.getFileName().toString(), content);
    }

}
