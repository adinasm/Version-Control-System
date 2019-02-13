/**
 * @author Adina Smeu
 */

package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;

public class CheckoutOperation extends VcsOperation {
    /**
     * Checkout operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public CheckoutOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the operation.
     *
     * @param vcs the vcs
     * @return the return code
     */
    public int execute(Vcs vcs) {
        if (vcs.getStaging().size() != 0) {
            return ErrorCodeManager.VCS_STAGED_OP_CODE;
        }

        if (operationArgs.get(0).equals("-c")) {
            return checkoutCommit(vcs);
        } else {
            return checkoutBranch(vcs);
        }
    }

    /**
     * Moves the head pointer on another branch.
     * @param vcs the vcs
     * @return the return code
     */
    private int checkoutBranch(Vcs vcs) {
        boolean found = false;

        for (Branch branch : vcs.getBranches()) {
            if (branch.getName().equals(operationArgs.get(0))) {
                vcs.setCurrentBranch(branch);
                vcs.setHead(branch.getCommits().get(branch.getCommits().size() - 1));
                found = true;
            }
        }

        if (!found) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }

        return ErrorCodeManager.OK;
    }

    /**
     * Moves the head pointer to a previous commit from the current branch.
     * @param vcs the vcs
     * @return the return code
     */
    private int checkoutCommit(Vcs vcs) {
        boolean found = false;

        Branch currBranch = vcs.getCurrentBranch();

        for (int i = 0; i < currBranch.getCommits().size(); ++i) {
            if (currBranch.getCommits().get(i).getID()
                    == Integer.parseInt(operationArgs.get(1))) {
                vcs.setHead(currBranch.getCommits().get(i));
                vcs.revertSnapshot(vcs.getHead().getSnapshot());
                found = true;

                i = currBranch.getCommits().size();
            }
        }

        if (!found) {
            return ErrorCodeManager.VCS_BAD_PATH_CODE;
        }

        removeCommits(vcs);

        return ErrorCodeManager.OK;
    }

    /**
     * Removes the commits given after the one from the argument.
     * @param vcs the vcs
     */
    private void removeCommits(Vcs vcs) {
        for (Branch branch : vcs.getBranches()) {
            for (int i = 0; i < branch.getCommits().size(); ++i) {
                if (branch.getCommits().get(i).getID()
                        > Integer.parseInt(operationArgs.get(1))) {
                    branch.getCommits().remove(i);
                    --i;
                }
            }
        }
    }
}
