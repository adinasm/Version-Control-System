/**
 * @author Adina Smeu
 */

package vcs;

import java.util.ArrayList;

public class Branch {
    private String name;
    private ArrayList<Commit> commits = new ArrayList<Commit>();

    /**
     * Constructor.
     * @param newName - the name of the branch
     */
    Branch(String newName) {
        name = newName;
    }

    /**
     * Gets the name of the branch.
     * @return the name of the branch
     */
    public final String getName() {
        return name;
    }

    /**
     * Gets the commits.
     * @return the commits from the branch
     */
    public final ArrayList<Commit> getCommits() {
        return commits;
    }

    /**
     * Adds a commit.
     * @param commit - the commit that has to be added to the branch
     */
    final void addCommit(Commit commit) {
        commits.add(commit);
    }
}
