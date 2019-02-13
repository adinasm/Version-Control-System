/**
 * @author Adina Smeu
 */

package vcs;

import java.util.ArrayList;

import filesystem.FileSystemSnapshot;
import utils.IDGenerator;

public class Commit {
    private Branch branch;
    private Commit parent;
    private ArrayList<String>  message;
    private int id;
    private FileSystemSnapshot snapshot;

    /**
     * Commit constructor.
     * @param mess        the message
     * @param par         the parent
     * @param newSnapshot the snapshot
     */
    Commit(ArrayList<String> mess, Commit par, FileSystemSnapshot newSnapshot) {
        message = mess;
        parent = par;
        id = IDGenerator.generateCommitID();
        snapshot = newSnapshot;
    }

    /**
     * Gets the id.
     * @return the id
     */
    public final int getID() {
        return id;
    }

    /**
     * Gets the message.
     * @return the message
     */
    public final ArrayList<String> getMessage() {
        return message;
    }

    /**
     * Gets the snapshot.
     * @return the snapshot
     */
    public final FileSystemSnapshot getSnapshot() {
        return snapshot;
    }
}
