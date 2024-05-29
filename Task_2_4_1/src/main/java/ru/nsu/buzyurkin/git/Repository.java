package ru.nsu.buzyurkin.git;

import java.io.File;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import ru.nsu.buzyurkin.model.Config;
import ru.nsu.buzyurkin.model.Student;


/**
 * The Repository class handles cloning and managing student repositories using JGit.
 */
public class Repository {
    private File baseDir;
    private Git git;

    /**
     * Constructs a Repository instance with the specified configuration.
     *
     * @param config the configuration object containing the setup details.
     */
    public Repository(Config config) {
        baseDir = new File(config.getRepositoriesDirectory());
    }

    /**
     * Clones the repository of the specified student.
     *
     * @param student the student whose repository needs to be cloned.
     * @return the directory where the repository is cloned, or null if cloning fails.
     */
    public File cloneRepo(Student student) {
        File repoDir = new File(baseDir, student.getId());
        if (repoDir.exists()) {
            removeRecursive(repoDir);
        }

        try {
            git = Git.cloneRepository()
                    .setURI(student.getRepoURL())
                    .setDirectory(repoDir)
                    .setCloneAllBranches(true)
                    .call();

            return repoDir;
        } catch (GitAPIException e) {
            return null;
        }
    }

    /**
     * Checks out the specified branch in the cloned repository.
     *
     * @param branch the name of the branch to check out.
     * @return true if the branch is successfully checked out, false otherwise.
     */
    public boolean checkoutBranch(String branch) {
        try {
            git.checkout().setName("origin/" + branch).call();
            return true;
        } catch (GitAPIException e) {
            return false;
        }
    }

    private void removeRecursive(File file) {
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                removeRecursive(subFile);
            }
        }
        file.delete();
    }
}
