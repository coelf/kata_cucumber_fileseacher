Feature: File searcher

  As a user, I want to see the number of file of the directory and search words in the files

  Background:
    Given Files in directory "/foo/bar" exist
      | path     | filename   | content                                                                 |
      | /foo/bar | file1.txt  | to be or not to be                                                      |
      | /foo/bar | file2.txt  | to be or not to ...                                                     |
      | /foo/bar | file3.txt  | Lorem ipsum dolor sit amet, consectetur adipiscing elit.                |
      | /foo/bar | file4.txt  | In venenatis felis ut lectus porttitor                                  |
      | /foo/bar | file5.txt  | nec pellentesque enim tincidunt                                         |
      | /foo/bar | file6.txt  | Cras malesuada ligula nec est molestie, eget tempus nisi sagittis       |
      | /foo/bar | file7.txt  | Integer nec nisi vitae nisl dictum consequat sit amet at arcu.          |
      | /foo/bar | file8.txt  | Curabitur eget risus vitae ex sagittis pharetra. Suspendisse leo nulla, |
      | /foo/bar | file9.txt  | sollicitudin sed suscipit vel, semper id mi                             |
      | /foo/bar | file10.txt | Vestibulum scelerisque sapien ut augue tincidunt                        |
      | /foo/bar | file11.txt | sed tempor lorem tincidunt                                              |
      | /foo/bar | file12.txt | Quisque a odio ac eros tempus porta non sed est                         |
      | /foo/bar | file13.txt | Morbi mi mi, laoreet eu lorem sit amet                                  |
      | /foo/bar | file14.txt | sodales ultricies ipsum                                                 |


  Scenario: read files in directory
    Given directory "/foo/bar" accessible
    When  the directory "/foo/bar" is read
    Then  print 14 files read in directory "/foo/bar"

  Scenario: search files containing sentence
    Given directory "/foo/bar" accessible
    When  the directory "/foo/bar" is read
    And searching sentence "to be or not to be"
    Then print matching files

  Scenario: Quit
    Given directory "/foo/bar" accessible
    When  the directory "/foo/bar" is read
    And ":quit" is entered
    Then Exit the Application

