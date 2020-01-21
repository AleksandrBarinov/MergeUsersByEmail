package dao;

import dao.models.Email;

import java.util.ArrayList;

public class GenerateData {

    private GenerateData() {
    }

    private static final GenerateData instance = new GenerateData();

    public static GenerateData getInstance() {
        return instance;
    }

    private DAOImpl dao = DAOImpl.getInstance();

    public void generate() {
        //user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru
        ArrayList<Email> emails1 = new ArrayList<Email>();
        emails1.add(new Email("xxx@ya.ru"));
        emails1.add(new Email("foo@gmail.com"));
        emails1.add(new Email("lol@mail.ru"));
        dao.addNewUser("user1", emails1);

        //user2 -> foo@gmail.com, ups@pisem.net
        ArrayList<Email> emails2 = new ArrayList<Email>();
        emails2.add(new Email("foo@gmail.com"));
        emails2.add(new Email("ups@pisem.net"));
        dao.addNewUser("user2", emails2);

        //user3 -> xyz@pisem.net, vasya@pupkin.com
        ArrayList<Email> emails3 = new ArrayList<Email>();
        emails3.add(new Email("xyz@pisem.net"));
        emails3.add(new Email("vasya@pupkin.com"));
        dao.addNewUser("user3", emails3);

        //user4 -> ups@pisem.net, aaa@bbb.ru
        ArrayList<Email> emails4 = new ArrayList<Email>();
        emails4.add(new Email("ups@pisem.net"));
        emails4.add(new Email("aaa@bbb.ru"));
        dao.addNewUser("user4", emails4);

        //user5 -> xyz@pisem.net
        ArrayList<Email> emails5 = new ArrayList<Email>();
        emails5.add(new Email("xyz@pisem.net"));
        dao.addNewUser("user5", emails5);
    }
}
