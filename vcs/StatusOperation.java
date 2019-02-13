/**
 * @author Adina Smeu
 */

package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;

import filesystem.FileSystemOperation;

public class StatusOperation extends VcsOperation {
    /**
     * Status operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public StatusOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the operation.
     *
     * @param vcs the vcs
     * @return the return code
     */
    public int execute(Vcs vcs) {
        ArrayList<FileSystemOperation> staging = vcs.getStaging();

        vcs.getOutputWriter().write("On branch: " + vcs.getCurrentBranch().getName() + "\n");
        vcs.getOutputWriter().write("Staged changes:\n");

        for (int i = 0; i < staging.size(); ++i) {
            switch (staging.get(i).getType()) {
                case MAKEDIR:
                    vcs.getOutputWriter().write("\tCreated directory "
                            + staging.get(i).getOperationArgs().get(1) + "\n");
                    break;
                case TOUCH:
                    vcs.getOutputWriter().write("\tCreated file "
                            + staging.get(i).getOperationArgs().get(1) + "\n");
                    break;
                case WRITETOFILE:
                    vcs.getOutputWriter().write("\tAdded \""
                            + staging.get(i).getOperationArgs().get(1) + "\" to file "
                            + staging.get(i).getOperationArgs().get(1)  + "\n");
                    break;
                case REMOVE:
                    vcs.getOutputWriter().write("\tRemoved "
                            + staging.get(i).getOperationArgs().get(1)  + "\n");
                    break;
                case CHANGEDIR:
                    vcs.getOutputWriter().write("\tChanged directory to "
                            + staging.get(i).getOperationArgs().get(1)  + "\n");
                    break;
                default:
            }
        }

        return ErrorCodeManager.OK;
    }
}
