`gradle run -Pfiles=War3.w3x,War3.mpq`

MpqDiff works by naively iterating over files in a pair of directories that contain all extracted MPQ contents.
Therefore MpqDiff will immediately spew diffs if one of the archives is missing a file.

* Be sure both archive have a listfile and an attributes file.
* Suggest piping output to a file as JMPQ extract function can be quite spammy.
