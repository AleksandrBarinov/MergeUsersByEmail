import dao.DAOImpl;
import dao.models.Email;
import dao.models.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import service.ServiceImpl;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    private DAOImpl dao = DAOImpl.getInstance();
    private ServiceImpl service = ServiceImpl.getInstance();

    @BeforeMethod
    public void before() {
        List<User> users = dao.getUsers();
        if (users.size() > 0) {
            for (User user: users) dao.removeUser(user.getName());
        }
    }

    //create user
    @org.testng.annotations.Test
    public void create() {
        List<Email> emails = new ArrayList<>();
        emails.add(new Email("testEmail1@xx"));
        emails.add(new Email("testEmail2@xx"));
        dao.addNewUser("testUser", emails);

        User user = dao.getUserByName("testUser");
        List<Email> emailList = user.getEmails();

        Assert.assertEquals("testUser", user.getName());
        Assert.assertEquals("testEmail1@xx", emailList.get(0).getValue());
        Assert.assertEquals("testEmail2@xx", emailList.get(1).getValue());
    }

    //edit user
    @org.testng.annotations.Test
    public void edit() {
        List<Email> emails = new ArrayList<>();
        emails.add(new Email("testEmail1@xx"));
        emails.add(new Email("testEmail2@xx"));
        dao.addNewUser("testUser", emails);

        User user = dao.getUserByName("testUser");
        List<Email> emailList = user.getEmails();

        Assert.assertEquals("testUser", user.getName());
        Assert.assertEquals("testEmail1@xx", emailList.get(0).getValue());
        Assert.assertEquals("testEmail2@xx", emailList.get(1).getValue());

        //edit
        emails.get(0).setValue("editedEmail1@xx");
        emails.get(1).setValue("editedEmail2@xx");

        dao.editUser(user.getId(),"editedTestUser", emails);

        User editedUser = dao.getUserByName("editedTestUser");
        List<Email> editedUserEmailList = editedUser.getEmails();

        Assert.assertEquals(editedUser.getName(), "editedTestUser");
        Assert.assertEquals(editedUserEmailList.get(0).getValue(), "editedEmail1@xx");
        Assert.assertEquals(editedUserEmailList.get(1).getValue(), "editedEmail2@xx");

    }

    //remove user
    @org.testng.annotations.Test
    public void remove() {
        dao.addNewUser("testUser", new ArrayList<Email>());
        dao.removeUser("testUser");

        boolean noResult = false;

        try {
            User user = dao.getUserByName("testUser");
        } catch (NoResultException e) {
            noResult = true;
        }

        Assert.assertTrue(noResult);
    }

    //merge users
    @org.testng.annotations.Test
    public void merge() {
        /*create users*/
        List<Email> emails = new ArrayList<>();
        emails.add(new Email("bingo!@xx"));
        emails.add(new Email("339866@xx"));
        emails.add(new Email("446789@xx"));
        dao.addNewUser("testUser", emails);

        List<Email> emails1 = new ArrayList<>();
        emails1.add(new Email("bingo5!@xx"));
        emails1.add(new Email("1545766@xx"));
        emails1.add(new Email("1267896@xx"));
        dao.addNewUser("testUser1", emails1);

        List<Email> emails2 = new ArrayList<>();
        emails2.add(new Email("378908@xx"));
        emails2.add(new Email("435345@xx"));
        emails2.add(new Email("bingo!@xx"));
        dao.addNewUser("testUser2", emails2);

        List<Email> emails3 = new ArrayList<>();
        emails3.add(new Email("2551324@xx"));
        emails3.add(new Email("bingo5!@xx"));
        emails3.add(new Email("2213454@xx"));
        dao.addNewUser("testUser3", emails3);

        /*merge users*/
        service.mergeUsers();

        /*assert list of users*/
        List<User> users = dao.getUsers();
        Assert.assertEquals(2, users.size());
        Assert.assertEquals("testUser", users.get(0).getName());
        Assert.assertEquals("testUser1", users.get(1).getName());

        List<Email> testUserEmails = users.get(0).getEmails();
        List<Email> testUser1Emails = users.get(1).getEmails();

        Assert.assertEquals(5, testUserEmails.size());
        Assert.assertEquals("bingo!@xx", testUserEmails.get(0).getValue());
        Assert.assertEquals("339866@xx", testUserEmails.get(1).getValue());
        Assert.assertEquals("446789@xx", testUserEmails.get(2).getValue());
        Assert.assertEquals("378908@xx", testUserEmails.get(3).getValue());
        Assert.assertEquals("435345@xx", testUserEmails.get(4).getValue());

        Assert.assertEquals(5, testUser1Emails.size());
        Assert.assertEquals("bingo5!@xx", testUser1Emails.get(0).getValue());
        Assert.assertEquals("1545766@xx", testUser1Emails.get(1).getValue());
        Assert.assertEquals("1267896@xx", testUser1Emails.get(2).getValue());
        Assert.assertEquals("2551324@xx", testUser1Emails.get(3).getValue());
        Assert.assertEquals("2213454@xx", testUser1Emails.get(4).getValue());
    }
}
