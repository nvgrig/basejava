package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        SK searchKey = getExistSearchKey(resume);
        doUpdate(resume, searchKey);
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK searchKey = getNotExistSearchKey(resume);
        doSave(resume, searchKey);
    }

    @Override
    public Resume get(Resume resume) {
        LOG.info("Get " + resume);
        SK searchKey = getExistSearchKey(resume);
        return doGet(searchKey);
    }

    @Override
    public void delete(Resume resume) {
        LOG.info("Delete " + resume);
        SK searchKey = getExistSearchKey(resume);
        doDelete(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("Get all sorted");
        List<Resume> resumeList = getAll();
        Collections.sort(resumeList);
        return resumeList;
    }

    // getting exist searchKey
    private SK getExistSearchKey(Resume resume) {
        SK searchKey = getSearchKey(resume);
        if (!isResumeExist(searchKey)) {
            throw new NotExistStorageException(resume.getUuid());
        }
        return searchKey;
    }

    // getting not exist searchKey
    private SK getNotExistSearchKey(Resume resume) {
        SK searchKey = getSearchKey(resume);
        if (isResumeExist(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }
        return searchKey;
    }

    // simple update operation
    protected abstract void doUpdate(Resume resume, SK searchKey);

    // simple save operation
    protected abstract void doSave(Resume resume, SK searchKey);

    // simple get operation
    protected abstract Resume doGet(SK searchKey);

    // simple delete operation
    protected abstract void doDelete(SK searchKey);

    // simple get all resumes operation
    protected abstract List<Resume> getAll();

    // simple get searchKey operation
    protected abstract SK getSearchKey(Resume resume);

    // resume exist check
    protected abstract boolean isResumeExist(SK searchKey);
}
