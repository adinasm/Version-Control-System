/**
 * @author Adina Smeu
 */

package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;

public class BranchOperation extends VcsOperation {
    /**
     * Branch operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public BranchOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the operation.
     *
     * @param vcs the vcs
     * @return the return code
     */
    public int execute(Vcs vcs) {
        for (Branch branch : vcs.getBranches()) {
            if (operationArgs.get(0).equals(branch.getName())) {
                return ErrorCodeManager.VCS_BAD_CMD_CODE;
            }
        }

        Branch newBranch = new Branch(operationArgs.get(0));
        newBranch.addCommit(vcs.getHead());
        vcs.addBranch(newBranch);

        return ErrorCodeManager.OK;
    }
}
