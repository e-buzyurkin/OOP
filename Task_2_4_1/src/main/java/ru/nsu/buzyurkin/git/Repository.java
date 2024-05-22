package ru.nsu.buzyurkin.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import ru.nsu.buzyurkin.model.Config;
import ru.nsu.buzyurkin.model.Student;

import java.io.File;

public class Repository {
    private File baseDir;
    private Git git;

    public Repository(Config config) {
        baseDir = new File(config.getRepositoriesDirectory());
    }

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
