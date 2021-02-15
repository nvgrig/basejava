package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamStrategy implements SerializationStrategy {

    @Override
    public void strategyWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, AbstractSection> sections = resume.getSections();
            writeWithException(dos, sections, (dos1, entry) -> sectionDataWrite(dos1, entry));
        }
    }

    private interface Writer<T, U> {
        void write(T t, U u) throws IOException;
    }

    private void writeWithException(DataOutputStream dos, Map<SectionType, AbstractSection> collection, Writer<DataOutputStream, Map.Entry<SectionType, AbstractSection>> writer) throws IOException {
        dos.writeInt(collection.size());
        for (Map.Entry<SectionType, AbstractSection> entry: collection.entrySet()) {
            writer.write(dos, entry);
        }
    }
    private void sectionDataWrite(DataOutputStream dos, Map.Entry<SectionType, AbstractSection> entry) throws IOException {
        dos.writeUTF(entry.getKey().name());
        AbstractSection section = entry.getValue();
        switch (entry.getKey()) {
            case OBJECTIVE, PERSONAL -> dos.writeUTF(((TextSection) section).getContent());
            case ACHIEVEMENT, QUALIFICATIONS -> {
                dos.writeInt(((ListSection) section).getItems().size());
                for (String content : ((ListSection) section).getItems()) {
                    dos.writeUTF(content);
                }
            }
            case EXPERIENCE, EDUCATION -> {
                dos.writeInt(((OrganizationSection) section).getOrganizations().size());
                for (Organization organization : ((OrganizationSection) section).getOrganizations()) {
                    Link homePage = organization.getHomePage();
                    dos.writeUTF(homePage.getName());
                    String url = homePage.getUrl();
                    dos.writeUTF((url == null) ? "null" : url);
                    List<Organization.Position> positions = organization.getPositions();
                    dos.writeInt(positions.size());
                    positions.forEach(position -> {
                        try {
                            dos.writeUTF(position.getBeginDate().toString());
                            dos.writeUTF(position.getFinishDate().toString());
                            dos.writeUTF(position.getTitle());
                            String description = position.getDescription();
                            dos.writeUTF((description == null) ? "null" : description);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    /*for (Organization.Position position : positions) {
                        dos.writeUTF(position.getBeginDate().toString());
                        dos.writeUTF(position.getFinishDate().toString());
                        dos.writeUTF(position.getTitle());
                        String description = position.getDescription();
                        dos.writeUTF((description == null) ? "null" : description);
                    }*/
                }
            }
        }
    }

    @Override
    public Resume strategyRead(InputStream inputStream) throws IOException {
        try (DataInputStream dis = new DataInputStream(inputStream)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, sectionDataRead(dis, sectionType));
            }
            return resume;
        }
    }

    private AbstractSection sectionDataRead(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case OBJECTIVE, PERSONAL -> {
                return new TextSection(dis.readUTF());
            }
            case ACHIEVEMENT, QUALIFICATIONS -> {
                int sectionSize = dis.readInt();
                List<String> items = new ArrayList<>(sectionSize);
                for (int i = 0; i < sectionSize; i++) {
                    items.add(dis.readUTF());
                }
                return new ListSection(items);
            }
            case EXPERIENCE, EDUCATION -> {
                int sectionSize = dis.readInt();
                List<Organization> organizations = new ArrayList<>(sectionSize);
                for (int i = 0; i < sectionSize; i++) {
                    String linkName = dis.readUTF();
                    String linkUrl = dis.readUTF();
                    Link homePage = new Link(linkName, (linkUrl.equals("null") ? null : linkUrl));
                    int positionsSize = dis.readInt();
                    List<Organization.Position> positions = new ArrayList<>(positionsSize);
                    for (int j = 0; j < positionsSize; j++) {
                        String beginDate = dis.readUTF();
                        String endDate = dis.readUTF();
                        String title = dis.readUTF();
                        String description = dis.readUTF();
                        positions.add(new Organization.Position(LocalDate.parse(beginDate),
                                LocalDate.parse(endDate), title, (description.equals("null")) ? null : description));
                    }
                    organizations.add(new Organization(homePage, positions));
                }
                return new OrganizationSection(organizations);
            }
        }
        return null;
    }
}
