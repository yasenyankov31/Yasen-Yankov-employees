package org.pairfinder.apis;

import org.pairfinder.models.Employee;
import org.pairfinder.models.WorkingPair;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class PairFinderApiHelper {
    public WorkingPair getLongestWorkingPair(MultipartFile csvFile, String datePattern) {
        List<Employee> allEmployees = new ArrayList<>();
        Set<Long> allProjectIds = new HashSet<>();
        parseFileToEmployeeList(csvFile, datePattern, allEmployees, allProjectIds);

        return getLongestWorkingPair(allEmployees, allProjectIds);
    }

    private void parseFileToEmployeeList(MultipartFile csvFile, String datePattern, List<Employee> allEmployees, Set<Long> allProjectIds) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",", -1);

                if (!IsNumber(columns[0]))
                    continue;

                Long employeeId = Long.valueOf(columns[0]);
                Long projectId = Long.valueOf(columns[1]);

                allProjectIds.add(projectId);

                String stringFromDate = columns[2].toLowerCase();
                String stringToDate = columns[3].toLowerCase();

                Employee employee = new Employee(employeeId, projectId, toDate(stringFromDate, datePattern), toDate(stringToDate, datePattern));
                allEmployees.add(employee);
            }

        } catch (IOException | ParseException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private WorkingPair getLongestWorkingPair(List<Employee> allEmployees, Set<Long> allProjectIds) {
        WorkingPair longestWorkingPair = new WorkingPair();

        Iterator<Long> projectIdsIterator = allProjectIds.iterator();
        while (projectIdsIterator.hasNext()) {
            Long currentProjectId = projectIdsIterator.next();
            List<Employee> employeesInProject = allEmployees.stream().filter(x -> x.getProjectId() == currentProjectId).toList();

            for (int i = 0; i < employeesInProject.size(); i++) {
                Employee employee = employeesInProject.get(i);
                Date startDate = employee.getFromDate();
                Date endDate = employee.getToDate();

                List<Employee> employeesInTimePeriod = employeesInProject.stream()
                        .filter(x -> x.getId() != employee.getId() && x.getFromDate().after(startDate) && x.getFromDate().before(endDate)).toList();

                for (var employeeInPeriod :
                        employeesInTimePeriod) {
                    Date overlapStart = employeeInPeriod.getFromDate().after(employee.getFromDate()) ? employeeInPeriod.getFromDate() : employee.getFromDate();
                    Date overlapEnd = employeeInPeriod.getToDate().before(employee.getToDate()) ? employeeInPeriod.getToDate() : employee.getToDate();

                    if (overlapEnd.after(overlapStart)) {
                        long timeSpent = getDifferenceDays(overlapStart, overlapEnd);
                        if (longestWorkingPair.getTimeSpent() < timeSpent) {
                            longestWorkingPair = new WorkingPair(employee.getId(),employeeInPeriod.getId(),currentProjectId,timeSpent);
                        }
                    }
                }
            }
        }

        return longestWorkingPair;
    }

    private long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    private Date toDate(String dateString, String datePattern) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);

        if (!dateString.isEmpty() && !dateString.equals("null"))
            return formatter.parse(dateString);

        return new Date();
    }

    private boolean IsNumber(String stringNumber) {
        try {
            Long.valueOf(stringNumber);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
