import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;
    /**
     * patern matches the index line of the diff comparison
     * paren.1 matches the first file
     * paren.2 matches the 2nd file
     */
    private static final String DIFF_FILES_PATTERN = "^diff --git\\sa/(.*)\\sb/(.*)";
    private static final String INDEX_LINE_TOKEN = "index ";

    private static final String NEW_FILE_MODE_TOKEN = "new file mode ";

    private static final String DELETED_FILE_MODE_TOKEN = "deleted file mode ";
    
    private StringBuffer currentDifference;

    private ScmFile currentScmFile;
    private List changedFiles = new ArrayList();
    private Map differences = new HashMap();
    /**
     * @see #DIFF_FILES_PATTERN
     */
    private RE filesRegexp;

        try
        {
            filesRegexp = new RE( DIFF_FILES_PATTERN );
        }
        catch ( RESyntaxException ex )
        {
            throw new RuntimeException(
                                        "INTERNAL ERROR: Could not create regexp to parse hg log file. Something is probably wrong with the oro installation.",
                                        ex );
        }
        if ( filesRegexp.match( line ) )
            currentFile = filesRegexp.getParen( 1 );
            currentScmFile = new ScmFile( currentFile, ScmFileStatus.MODIFIED );
            changedFiles.add( currentScmFile );
            currentDifference = new StringBuffer();
        else if ( line.startsWith( INDEX_LINE_TOKEN ) )
        {
            // skip, though could parse to verify start revision and end revision
            patch.append( line ).append( "\n" );
        }
        else if ( line.startsWith( NEW_FILE_MODE_TOKEN ) )
        {
            changedFiles.remove( currentScmFile );
            currentScmFile = new ScmFile( currentFile, ScmFileStatus.ADDED );
            changedFiles.add( currentScmFile );

            // could parse to verify file mode
            patch.append( line ).append( "\n" );
        }
        else if ( line.startsWith( DELETED_FILE_MODE_TOKEN ) )
        {
            changedFiles.remove( currentScmFile );
            currentScmFile = new ScmFile( currentFile, ScmFileStatus.DELETED );
            changedFiles.add( currentScmFile );

            // could parse to verify file mode
            patch.append( line ).append( "\n" );
        }
    public List getChangedFiles()
    public Map getDifferences()