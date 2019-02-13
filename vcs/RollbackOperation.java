/**
 * @author Adina Smeu
 */

package vcs;

import utils.OperationType;

import java.util.ArrayList;

public class RollbackOperation extends VcsOperation {
    /**
     * Rollback operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public RollbackOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the operation.
     *
     * @param vcs the vcs
     * @return the return code
     */
    public int execute(Vcs vcs) {
        for (int i = 0; i < vcs.getStaging().size(); ++i) {
            vcs.getStaging().remove(i);
            --i;
        }

        int lastCommitID = 1;

        for (Branch branch : vcs.getBranches()) {
            for (Commit commit : branch.getCommits()) {
                if (commit.getID() > lastCommitID) {
                    vcs.revertSnapshot(commit.getSnapshot().cloneFileSystem());
                    vcs.setHead(commit);
                }
            }
        }

        return 0;
    }
}
