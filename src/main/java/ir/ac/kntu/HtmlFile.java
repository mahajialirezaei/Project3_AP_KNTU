package ir.ac.kntu;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlFile {
    public static void makeTableHtml(List<Transaction> transactions) throws IOException {
        String[] headers = { "amount", "source", "destination", "firstNameDest", "lastNameDest", "type" };
        String[][] tran = new String[transactions.size()][6];
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            tran[i] = new String[] { Double.toString(transaction.getAmount()), transaction.getSource(),
                    transaction.getDestination(), transaction.getFirstNameDest(), transaction.getLastNameDest(),
                    transaction.getType().toString() };
        }
        StringBuilder htmlTable = new StringBuilder();
        htmlAppend(htmlTable);
        htmlTable.append("<tr>");
        for (String header : headers) {
            htmlTable.append("<th>").append(header).append("</th>");
        }
        htmlTable.append("</tr>");
        for (String[] row : tran) {
            htmlTable.append("<tr>");
            for (String cell : row) {
                htmlTable.append("<td>").append(cell).append("</td>");
            }
            htmlTable.append("</tr>");
        }
        htmlTable.append("</table>");
        System.out.println("hi");
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("C:\\Users\\farhadalirezaei\\OneDrive\\Desktop\\bankInfo\\Table.Html"))) {
            writer.write(htmlTable.toString());
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private static void htmlAppend(StringBuilder htmlTable) {
        htmlTable.append("<table border=\"1\">");
        htmlTable.append("<!DOCTYPE html>\n" + "<html lang=\"en\">\n" + "<head>\n" + "<meta charset=\"UTF-8\">\n"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + "<title></title>\n"
                + "<style>\n" + "    table {\n" + "        font-family: \"Comic Sans MS\", cursive, sans-serif;\n"
                + "        border-collapse: collapse;\n" + "        width: 75%;\n" + "    }\n" + "    \n"
                + "    th, td {\n" + "        border: 1px solid black;\n" + "        padding: 8px;\n"
                + "        text-align: center;\n" + "    }\n" + "    \n" + "    th {\n"
                + "        background-color: lightblue;\n" + "    }\n" + "    \n" + "    td {\n"
                + "        background-color: lightgreen;\n" + "    }\n" + "</style>\n" + "</head>");
    }
}
