/**
 * @author Adina Smeu
 */
package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;

import filesystem.FileSystemSnapshot;

public class CommitOperation extends VcsOperation {
    /**
     * Commit operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public CommitOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the operation.
     *
     * @param vcs the vcs
     * @return the return code
     */
    public int execute(Vcs vcs) {
        if (vcs.getStaging().size() == 0) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }

        FileSystemSnapshot newSnapshot = vcs.getActiveSnapshot().cloneFileSystem();

        for (int i = 0; i < vcs.getStaging().size(); ++i) {
            vcs.getStaging().get(i).execute(newSnapshot);
            vcs.getStaging().remove(i);
            --i;
        }

        operationArgs.remove(0);

        Commit newCommit = new Commit(operationArgs, vcs.getHead(), newSnapshot);
        vcs.getCurrentBranch().addCommit(newCommit);
        vcs.setHead(newCommit);

        return ErrorCodeManager.OK;
    }
}
