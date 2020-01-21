package service;

import dao.DAOImpl;
import dao.models.Email;
import dao.models.User;

import java.util.*;

public class ServiceImpl implements Service {

    private DAOImpl dao = DAOImpl.getInstance();

    private ServiceImpl() {
    }

    private static final ServiceImpl instance = new ServiceImpl();

    public static ServiceImpl getInstance() {
        return instance;
    }

    public void printData() {
        List<User> users = dao.getUsers();

        for (User user: users) {
            System.out.print("\n" + user.getName() + " -> ");
            for (Email email: user.getEmails()) {
                System.out.print(email.getValue() + " ");
            }
        }
        System.out.print("\n\n");
    }

    public void mergeUsers() {

        List<User> users = dao.getUsers();
        List<Email> allEmailAddresses = dao.getEmailAddresses();

        Set<String> uniqueAddresses = new HashSet<>();
        for (Email email: allEmailAddresses) uniqueAddresses.add(email.getValue());

        /*(1) get sets of users to merge*/
        List<List<String>> usersToMerge = new ArrayList<>();

        for (String address: uniqueAddresses) {
            List<String> toMerge = new ArrayList<>();
            for (User user: users){

                //for each user email address
                List<Email> emails = user.getEmails();
                for (Email e: emails) {
                    if (address.equals(e.getValue())) {
                        toMerge.add(user.getName());
                    }
                }
            }
            //add to list if count of repeats > 1
            if (toMerge.size() > 1) {
                usersToMerge.add(toMerge);
            }
        }

        /*(2) merge users*/
        while (usersToMerge.size() > 0) {

            //usersToMerge iterator
            Iterator outerIterator = usersToMerge.iterator();
            while (outerIterator.hasNext()) {

                //iterator from each usersToMerge item
                Iterator innerIterator = ((List<String>) outerIterator.next()).iterator();

                while (innerIterator.hasNext()) {
                    User to = dao.getUserByName((String) innerIterator.next());
                    innerIterator.remove();

                    if (innerIterator.hasNext()) {
                        User from = dao.getUserByName((String) innerIterator.next());
                        innerIterator.remove();

                        //merge email addresses to one user
                        to.getEmails().addAll(from.getEmails());

                        if (to.getId()!=from.getId()) {
                            //merge users
                            dao.removeUser(from.getName());
                            dao.editUser(to.getId(),to.getName(),to.getEmails());
                        }
                    }
                }
                outerIterator.remove();
            }
        }

        /*(3) remove email address duplicates*/
        users = dao.getUsers();

        for (User user: users) {
            List<Email> userEmails = user.getEmails();
            Set<Email> emailTempSet = new HashSet<>(userEmails);

            int repCount;

            for (Email emailFromTempSet: emailTempSet) {

                Iterator iterator = userEmails.iterator();
                repCount = 0;

                while (iterator.hasNext()) {

                    String emailFromIterator = ((Email) iterator.next()).getValue();

                    if (emailFromIterator.equals(emailFromTempSet.getValue())) {
                        repCount++;
                    }
                    //remove if count of repeats > 1
                    if (repCount > 1) {
                        iterator.remove();
                        repCount = 0;
                    }
                }
            }
            dao.editUser(user.getId(), user.getName(), userEmails);
        }

    }
}
