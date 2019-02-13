/**
 * @author Adina Smeu
 */

package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;

public class LogOperation extends VcsOperation {
    /**
     * Log operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public LogOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the operation.
     *
     * @param vcs the vcs
     * @return the return code
     */
    public int execute(Vcs vcs) {
        ArrayList<Commit> commits = vcs.getCurrentBranch().getCommits();
        for (int i = 0; i < commits.size(); ++i) {
            vcs.getOutputWriter().write("Commit id: " + commits.get(i).getID() + "\n");
            vcs.getOutputWriter().write("Message:");

            for (String word : commits.get(i).getMessage()) {
                vcs.getOutputWriter().write(" " + word);
            }

            vcs.getOutputWriter().write("\n");

            if (i != (commits.size() - 1)) {
                vcs.getOutputWriter().write("\n");
            }
        }

        return ErrorCodeManager.OK;
    }
}
