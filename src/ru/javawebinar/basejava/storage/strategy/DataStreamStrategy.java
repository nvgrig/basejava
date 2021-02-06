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
            // TODO implements sections
            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                sectionDataWrite(dos, entry);
            }
        }
    }

    private void sectionDataWrite(DataOutputStream dos, Map.Entry<SectionType, AbstractSection> entry) throws IOException {
        dos.writeUTF(entry.getKey().name());
        AbstractSection section = entry.getValue();
        dos.writeUTF(entry.getValue().getClass().getCanonicalName());
        if (section instanceof TextSection) {
            dos.writeUTF(((TextSection) section).getContent());
        }
        if (section instanceof ListSection) {
            dos.writeInt(((ListSection) section).getItems().size());
            for (String content : ((ListSection) section).getItems()) {
                dos.writeUTF(content);
            }
        }
        if (section instanceof OrganizationSection) {
            dos.writeInt(((OrganizationSection) section).getOrganizations().size());
            for (Organization organization : ((OrganizationSection) section).getOrganizations()) {
                Link homePage = organization.getHomePage();
                dos.writeUTF(homePage.getName());
                dos.writeUTF(homePage.getUrl());
                List<Organization.Position> positions = organization.getPositions();
                dos.writeInt(positions.size());
                for (Organization.Position position : positions) {
                    dos.writeUTF(position.getBeginDate().toString());
                    dos.writeUTF(position.getFinishDate().toString());
                    dos.writeUTF(position.getTitle());
                    dos.writeUTF(position.getDescription());
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
            // TODO implements sections
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addSection(SectionType.valueOf(dis.readUTF()), sectionDataRead(dis));
            }
            return resume;
        }
    }

    private AbstractSection sectionDataRead(DataInputStream dis) throws IOException {
        String sectionClassName = dis.readUTF();
        if (sectionClassName.equals("ru.javawebinar.basejava.model.TextSection")) {
            return new TextSection(dis.readUTF());
        }
        if (sectionClassName.equals("ru.javawebinar.basejava.model.ListSection")) {
            int sectionSize = dis.readInt();
            List<String> items = new ArrayList<>(sectionSize);
            for (int i = 0; i < sectionSize; i++) {
                items.add(dis.readUTF());
            }
            return new ListSection(items);
        }
        if (sectionClassName.equals("ru.javawebinar.basejava.model.OrganizationSection")) {
            int sectionSize = dis.readInt();
            List<Organization> organizations = new ArrayList<>(sectionSize);
            for (int i = 0; i < sectionSize; i++) {
                Link homePage = new Link(dis.readUTF(), dis.readUTF());
                int positionsSize = dis.readInt();
                List<Organization.Position> positions = new ArrayList<>(positionsSize);
                for (int j = 0; j < positionsSize; j++) {
                    positions.add(new Organization.Position(LocalDate.parse(dis.readUTF()),
                            LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()));
                }
                organizations.add(new Organization(homePage, positions));
            }
            return new OrganizationSection(organizations);
        }
        return null;
    }
}
