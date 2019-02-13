# Version Control System

The project contains 3 packages:

### filesystem
- contains unix commands for adding, modifying and deleting files;

### utils
- contains helper classes and interfaces for creating operations, generating
  commit ids, creating filesystem entities;

### vcs
- contains the following classes:
  - Branch:
    - contains an array of commits;
  - Commit:
    - contains a filesystem snapshot;
- the Visitor Pattern is used for the following classes:
  - Vcs:
    - contains the active filesystem snapshot, the branches and the head
      pointer;
    - it has the role of the visitor, visits each operation;
  - VcsOperation:
    - abstract class, extends AbstractOperation;
    - has the role of the visitable element, accepts the vcs;
- all the following classes extend the VcsOperation:
  - BranchOperation:
    - creates a new branch that has the same filesystem structure as the
      current commit;
  - CheckoutOperation:
    - depending on the arguments, it can either move the head pointer to
      another branch or to another commit;
  - CommitOperation:
    - generates a new commit;
  - InvalidVcsOperation:
    - when visited, returns the error code corresponding to an invalid
      vcs operation;
  - LogOperation:
    - displays the commits from the current branch;
  - RollbackOperation:
    - reverts the active snapshot to the one corresponding to the last
      commit and empties the staging;
  - StatusOperation
    - lists the commands from the staging.
