import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by mkarlsru on 9/29/17.
 *
 *
9
Biology
Wednesday 3
World History
Monday 1
Maths
Wednesday 1
Social Studies
Friday 2
Painting
Friday 4
Marine Biology
Monday 4
Algebra
Friday 1
Accounting
Wednesday 2
Economics
Wednesday 4

 */
public class CodeAndSolve3 {
    private static final int ROWS = 4;
    private static final int COLUMNS = Day.values().length;
    private static final int ROW_LENGTH = 10;
    private static final String ROW_DIVIDER = getRowDivider();
    private static final String COLUMN_DIVIDER = "|";
    private static final String EMPTY = toRowLength("");

    private static String getRowDivider() {
        StringBuilder stringBuffer = new StringBuilder("+");
        for (int column = 0; column < COLUMNS; column ++) {
            for (int i = 0; i < ROW_LENGTH; i++) {
                stringBuffer.append("=");
            }
            stringBuffer.append("+");
        }
        return stringBuffer.toString();
    }

    private enum Day {
        Monday(0), Wednesday(1), Friday(2);

        private final int column;

        Day(int column) {
            this.column = column;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int numCourses = scan.nextInt();
        scan.nextLine();
        List<List<String[]>> agenda = new ArrayList<>();
        for (int i = 0; i < ROWS; i ++) {
            agenda.add(new ArrayList<>());
        }

        for (int i = 0; i < numCourses; i ++) {
            String courseName = scan.nextLine();
            String[] dayAndTime = scan.nextLine().split(" ");
            Day day = Day.valueOf(dayAndTime[0]);
            int slot = Integer.parseInt(dayAndTime[1]) - 1; //Row of agenda
            formatCourseName(courseName, day.column, agenda.get(slot));
        }
        printAgenda(agenda);
    }

    private static void formatCourseName(String courseName, int column, List<String[]> agendaRow) {
        String[] tokens = courseName.split(" ");
        String rowName = tokens[0];
        for (int i = 1; i < tokens.length; i ++) {
            String newRowName = rowName + " " + tokens[i];
            if (newRowName.length() > ROW_LENGTH) {
                if (agendaRow.size() <= i - 1) {
                    agendaRow.add(new String[COLUMNS]);
                }
                agendaRow.get(i - 1)[column] = toRowLength(rowName);
                rowName = tokens[i];
            } else {
                rowName = newRowName;
            }
        }
        if (agendaRow.size() <= tokens.length - 1) {
            agendaRow.add(new String[COLUMNS]);
        }
        agendaRow.get(tokens.length - 1)[column] = toRowLength(rowName);
    }

    private static String toRowLength(String str) {
        StringBuilder strBuilder = new StringBuilder(str);
        for (int i = 0; i < ROW_LENGTH - str.length(); i ++) {
            strBuilder.append(" ");
        }
        return strBuilder.toString();
    }

    private static void printAgenda(List<List<String[]>> agenda) {
        for (List<String[]> row : agenda) {
            System.out.println(ROW_DIVIDER);
            for (String[] rowLine : row) {
                for (String name : rowLine) {
                    System.out.print(COLUMN_DIVIDER);
                    System.out.print(name == null ? EMPTY : name);
                }
                System.out.println(COLUMN_DIVIDER);
            }
        }
        System.out.println(ROW_DIVIDER);
    }
}
