/**
 * @author Adina Smeu
 */

package vcs;

import java.util.ArrayList;
import java.util.Arrays;

import filesystem.FileSystemOperation;
import filesystem.FileSystemSnapshot;
import utils.OutputWriter;
import utils.Visitor;

public final class Vcs implements Visitor {
    private final OutputWriter outputWriter;
    private FileSystemSnapshot activeSnapshot;
    private Branch currentBranch;
    private ArrayList<Branch> branches = new ArrayList<Branch>();
    private ArrayList<FileSystemOperation> staging = new ArrayList<FileSystemOperation>();
    private Commit head = null;

    /**
     * Vcs constructor.
     *
     * @param outputWriter the output writer
     */
    public Vcs(OutputWriter outputWriter) {
        this.outputWriter = outputWriter;
    }

    /**
     * Gets the head.
     * @return the head
     */
    public Commit getHead() {
        return head;
    }

    /**
     * Sets the head.
     * @param newHead the new head
     */
    public void setHead(Commit newHead) {
        head = newHead;
    }

    /**
     * Gets the active snapshot.
     * @return the active snapshot
     */
    public FileSystemSnapshot getActiveSnapshot() {
        return activeSnapshot;
    }

    /**
     * Gets the current branch.
     * @return current branch
     */
    public Branch getCurrentBranch() {
        return currentBranch;
    }

    /**
     * Sets the branch.
     * @param newBranch the new branch
     */
    public void setCurrentBranch(Branch newBranch) {
        currentBranch = newBranch;
    }

    /**
     * Gets the branches.
     * @return the branches
     */
    public ArrayList<Branch> getBranches() {
        return branches;
    }

    /**
     * Gets the output writer.
     * @return the output writer
     */
    OutputWriter getOutputWriter() {
        return outputWriter;
    }

    /**
     * Gets the staging.
     * @return the staging
     */
    public ArrayList<FileSystemOperation> getStaging() {
        return staging;
    }

    /**
     * Adds an operation to the staging.
     * @param operation the operation that has to be added
     */
    public void addToStaging(FileSystemOperation operation) {
        staging.add(operation);
    }

    /**
     * Adds a branch.
     * @param branch the branch that has to be added
     */
    void addBranch(Branch branch) {
        branches.add(branch);
    }

    /**
     * Sets the snapshot.
     * @param snapshot the new snapshot
     */
    void revertSnapshot(FileSystemSnapshot snapshot) {
        activeSnapshot = snapshot;
    }

    /**
     * Does initialisations.
     */
    public void init() {
        this.activeSnapshot = new FileSystemSnapshot(outputWriter);

        Branch master = new Branch("master");
        Commit firstCommit = new Commit(new ArrayList<String>(
            Arrays.asList("First commit")), head, activeSnapshot.cloneFileSystem());
        master.addCommit(firstCommit);
        branches.add(master);

        head = firstCommit;
        currentBranch = master;
    }

    /**
     * Visits a file system operation.
     *
     * @param fileSystemOperation the file system operation
     * @return the return code
     */
    public int visit(FileSystemOperation fileSystemOperation) {
        return fileSystemOperation.execute(this.activeSnapshot);
    }

    /**
     * Visits a vcs operation.
     *
     * @param vcsOperation the vcs operation
     * @return return code
     */
    @Override
    public int visit(VcsOperation vcsOperation) {

        return vcsOperation.execute(this);
    }
}
