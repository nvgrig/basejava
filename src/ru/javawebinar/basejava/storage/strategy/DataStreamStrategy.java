package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamStrategy implements SerializationStrategy {

    @Override
    public void strategyWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeWithException(dos, resume.getContacts().entrySet(), DataStreamStrategy.this::contactDataWrite);
            writeWithException(dos, resume.getSections().entrySet(), DataStreamStrategy.this::sectionDataWrite);
        }
    }

    private interface Writer<T, U> {
        void write(T t, U u) throws IOException;
    }

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, Writer<DataOutputStream, T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T entry : collection) {
            writer.write(dos, entry);
        }
    }

    private void contactDataWrite(DataOutputStream dos, Map.Entry<ContactType, String> entry) throws IOException {
        dos.writeUTF(entry.getKey().name());
        dos.writeUTF(entry.getValue());
    }

    private void contentDataWrite(DataOutputStream dos, String content) throws IOException {
        dos.writeUTF(content);
    }

    private void positionDataWrite(DataOutputStream dos, Organization.Position position) throws IOException {
        dos.writeUTF(position.getBeginDate().toString());
        dos.writeUTF(position.getFinishDate().toString());
        dos.writeUTF(position.getTitle());
        String description = position.getDescription();
        dos.writeUTF((description == null) ? "null" : description);
    }

    private void organizationDataWrite(DataOutputStream dos, Organization organization) throws IOException {
        Link homePage = organization.getHomePage();
        dos.writeUTF(homePage.getName());
        String url = homePage.getUrl();
        dos.writeUTF((url == null) ? "null" : url);
        writeWithException(dos, organization.getPositions(), this::positionDataWrite);
    }

    private void sectionDataWrite(DataOutputStream dos, Map.Entry<SectionType, AbstractSection> entry) throws IOException {
        dos.writeUTF(entry.getKey().name());
        AbstractSection section = entry.getValue();
        switch (entry.getKey()) {
            case OBJECTIVE, PERSONAL -> dos.writeUTF(((TextSection) section).getContent());
            case ACHIEVEMENT, QUALIFICATIONS -> writeWithException(dos, ((ListSection) section).getItems(), this::contentDataWrite);
            case EXPERIENCE, EDUCATION -> writeWithException(dos, ((OrganizationSection) section).getOrganizations(), this::organizationDataWrite);
        }
    }

    @Override
    public Resume strategyRead(InputStream inputStream) throws IOException {
        try (DataInputStream dis = new DataInputStream(inputStream)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readWithException(dis, resume, "Contacts reading", this::contactDataRead);
            readWithException(dis, resume, "Sections reading", this::sectionDataRead);
            return resume;
        }
    }

    private interface Reader<DataInputStream, T, U> {
        void read(DataInputStream dis, T t, U u) throws IOException;
    }

    private <T, U> void readWithException(DataInputStream dis, T t, U u, Reader<DataInputStream, T, U> reader) throws IOException {
        reader.read(dis, t, u);
    }

    private void contactDataRead(DataInputStream dis, Resume resume, String string) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        }
    }

    private void contentDataRead(DataInputStream dis, Resume resume, SectionType sectionType) throws IOException {
        int sectionSize = dis.readInt();
        List<String> items = new ArrayList<>(sectionSize);
        for (int i = 0; i < sectionSize; i++) {
            items.add(dis.readUTF());
        }
        resume.addSection(sectionType, new ListSection(items));
    }

    private void positionDataRead(DataInputStream dis, List<Organization> organizations, Link homePage) throws IOException {
        int positionsSize = dis.readInt();
        List<Organization.Position> positions = new ArrayList<>(positionsSize);
        for (int i = 0; i < positionsSize; i++) {
            String beginDate = dis.readUTF();
            String endDate = dis.readUTF();
            String title = dis.readUTF();
            String description = dis.readUTF();
            positions.add(new Organization.Position(LocalDate.parse(beginDate),
                    LocalDate.parse(endDate), title, (description.equals("null")) ? null : description));
        }
        organizations.add(new Organization(homePage, positions));
    }

    private void organizationDataRead(DataInputStream dis, Resume resume, SectionType sectionType) throws IOException {
        int sectionSize = dis.readInt();
        List<Organization> organizations = new ArrayList<>(sectionSize);
        for (int i = 0; i < sectionSize; i++) {
            String linkName = dis.readUTF();
            String linkUrl = dis.readUTF();
            Link homePage = new Link(linkName, (linkUrl.equals("null") ? null : linkUrl));
            readWithException(dis, organizations, homePage, this::positionDataRead);
        }
        resume.addSection(sectionType, new OrganizationSection(organizations));
    }

    private void sectionDataRead(DataInputStream dis, Resume resume, String string) throws IOException {
        int sectionSize = dis.readInt();
        for (int i = 0; i < sectionSize; i++) {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            switch (sectionType) {
                case OBJECTIVE, PERSONAL -> resume.addSection(sectionType, new TextSection(dis.readUTF()));
                case ACHIEVEMENT, QUALIFICATIONS -> readWithException(dis, resume, sectionType, this::contentDataRead);
                case EXPERIENCE, EDUCATION -> readWithException(dis, resume, sectionType, this::organizationDataRead);
            }
        }
    }
}
