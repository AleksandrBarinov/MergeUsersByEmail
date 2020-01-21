import dao.GenerateData;
import service.ServiceImpl;
import util.HibernateUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {

        GenerateData.getInstance().generate();

        ServiceImpl service = ServiceImpl.getInstance();
        service.printData();

        System.out.println("\nMerge users? ('Y' to merge)");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        reader.close();

        if (input.equals("Y")) {
            service.mergeUsers();
            service.printData();
        }

        HibernateUtil.getSessionFactory().close();
    }
}
