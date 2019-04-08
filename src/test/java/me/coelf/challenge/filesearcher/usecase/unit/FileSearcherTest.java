package me.coelf.challenge.filesearcher.usecase.unit;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import me.coelf.challenge.filesearcher.adapters.InMemoryFilesIndex;
import me.coelf.challenge.filesearcher.adapters.InMemoryFilesRepository;
import me.coelf.challenge.filesearcher.adapters.WordRanker;
import me.coelf.challenge.filesearcher.domain.File;
import me.coelf.challenge.filesearcher.domain.FilesRepository;
import me.coelf.challenge.filesearcher.domain.RankedFile;
import me.coelf.challenge.filesearcher.usecase.ExitException;
import me.coelf.challenge.filesearcher.usecase.FileSearcher;
import me.coelf.challenge.filesearcher.usecase.NoMatchesFoundException;
import me.coelf.challenge.filesearcher.usecase.PathException;
import me.coelf.challenge.filesearcher.utils.Prettier;

public class FileSearcherTest
{
    private List<File> fooBarfilesMock;
    private List<File> barFoofilesMock;

    @Before
    public void setup()
    {
        fooBarfilesMock = Arrays.asList(
                new File("file1.txt", "to be or not to be                                                      "),
                new File("file2.txt", "to be or not to ...                                                     "),
                new File("file3.txt", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.                "),
                new File("file4.txt", "In venenatis felis ut lectus porttitor                                  "),
                new File("file5.txt", "nec pellentesque enim tincidunt                                         "),
                new File("file6.txt", "Cras malesuada ligula nec est molestie, eget tempus nisi sagittis       "),
                new File("file7.txt", "Integer nec nisi vitae nisl dictum consequat sit amet at arcu.          "),
                new File("file8.txt", "Curabitur eget risus vitae ex sagittis pharetra. Suspendisse leo nulla, "),
                new File("file9.txt", "sollicitudin sed suscipit vel, semper id mi                             "),
                new File("file10.txt", "Vestibulum scelerisque sapien ut augue tincidunt                        "),
                new File("file11.txt", "sed tempor lorem tincidunt                                              "),
                new File("file12.txt", "Quisque a odio ac eros tempus porta non sed est                         "),
                new File("file13.txt", "Morbi mi mi, laoreet eu lorem sit amet                                  "),
                new File("file14.txt", "sodales ultricies ipsum                                                 ")
        );

        barFoofilesMock = Arrays.asList(
                new File("file1.txt", "to be or not to be                                                      "),
                new File("file2.txt", "to be or not to ...                                                     ")
        );
    }

    @Test
    public void whenReadDirectoryFooBarThenPrint14Files()
            throws PathException
    {
        final String directoryName = "/foo/bar";
        final FileSearcher fileSearcher = initFileSearcher(directoryName, fooBarfilesMock);
        assertThat(fileSearcher.read(directoryName), equalTo("14 file(s) read in directory \"/foo/bar\""));
    }

    @Test
    public void whenReadDirectoryBarFooThenPrint2Files()
            throws PathException
    {
        final String directoryName = "/bar/foo";
        final FileSearcher fileSearcher = initFileSearcher(directoryName, barFoofilesMock);
        assertThat(fileSearcher.read(directoryName), equalTo("2 file(s) read in directory \"/bar/foo\""));
    }

    @Test
    public void whenSearchToBeOrNotToBeThenFindMatchingFiles()
            throws PathException, NoMatchesFoundException, ExitException
    {
        final String directoryName = "/foo/bar";
        final FileSearcher fileSearcher = initFileSearcher(directoryName, fooBarfilesMock);
        fileSearcher.read(directoryName);
        List<RankedFile> rankedFiles = fileSearcher.search("to be or not to be");
        assertThat(Prettier.print(rankedFiles), equalTo(
                String.join("\n", Arrays.asList("file1.txt : 100%", "file2.txt : 90%")
                )));
    }

    @Test(expected = NoMatchesFoundException.class)
    public void whenSearchCantThenFindNoMatchingFiles()
            throws PathException, NoMatchesFoundException, ExitException
    {
        final String directoryName = "/foo/bar";
        final FileSearcher fileSearcher = initFileSearcher(directoryName, fooBarfilesMock);
        fileSearcher.read(directoryName);
        fileSearcher.search("cat");
    }

    @Test(expected = ExitException.class)
    public void whenQuitEnteredExit()
            throws PathException, ExitException, NoMatchesFoundException
    {
        final String directoryName = "/foo/bar";
        final FileSearcher fileSearcher = initFileSearcher(directoryName, fooBarfilesMock);
        fileSearcher.read(directoryName);
        fileSearcher.search(":quit");
    }

    private FileSearcher initFileSearcher(
            String directoryName,
            List<File> files
    )
    {
        FilesRepository filesRepository = new InMemoryFilesRepository(directoryName, files);
        return new FileSearcher(filesRepository, new InMemoryFilesIndex(), new WordRanker());
    }

}
