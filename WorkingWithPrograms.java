import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class WorkingWithPrograms {
    public void printAllProgramsSorted(Map<BroadcastsTime, List<Program>> schedule) {
        List<Program> allPrograms = new ArrayList<>();
        for (List<Program> programs : schedule.values()) {
            allPrograms.addAll(programs);
        }

        allPrograms.sort(Comparator.comparing(Program::getChannel).thenComparing(Program::getTime));

        for (Program program : allPrograms) {
            System.out.println(program);
        }
    }

    public void printCurrentPrograms(Map<BroadcastsTime, List<Program>> schedule) {
        BroadcastsTime now = getCurrentTime();
        for (Map.Entry<BroadcastsTime, List<Program>> entry : schedule.entrySet()) {
            if (entry.getKey().equals(now)) {
                for (Program program : entry.getValue()) {
                    System.out.println(program);
                }
            }
        }
    }

    private BroadcastsTime getCurrentTime() {
        LocalTime now = LocalTime.now();
        return new BroadcastsTime(String.format("%02d:%02d", now.getHour(), now.getMinute()));
    }

    public void findProgramsByTitle(Map<BroadcastsTime, List<Program>> schedule, String title) {
        for (List<Program> programs : schedule.values()) {
            for (Program program : programs) {
                if (program.getTitle().equalsIgnoreCase(title)) {
                    System.out.println(program);
                }
            }
        }
    }

    public void findCurrentProgramsByChannel(Map<BroadcastsTime, List<Program>> schedule, String channel) {
        BroadcastsTime now = getCurrentTime();
        for (Map.Entry<BroadcastsTime, List<Program>> entry : schedule.entrySet()) {
            if (entry.getKey().equals(now)) {
                for (Program program : entry.getValue()) {
                    if (program.getChannel().equalsIgnoreCase(channel)) {
                        System.out.println(program);
                    }
                }
            }
        }
    }

    public void findProgramsByChannelAndTimeRange(Map<BroadcastsTime, List<Program>> schedule, String channel, BroadcastsTime start, BroadcastsTime end) {
        for (Map.Entry<BroadcastsTime, List<Program>> entry : schedule.entrySet()) {
            if (entry.getKey().between(start, end)) {
                for (Program program : entry.getValue()) {
                    if (program.getChannel().equalsIgnoreCase(channel)) {
                        System.out.println(program);
                    }
                }
            }
        }
    }

    public void saveProgramsToExcel(List<Program> programs, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Programs");

        int rowNum = 0;
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.createCell(0).setCellValue("Channel");
        headerRow.createCell(1).setCellValue("Time");
        headerRow.createCell(2).setCellValue("Title");

        for (Program program : programs) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(program.getChannel());
            row.createCell(1).setCellValue(program.getTime().toString());
            row.createCell(2).setCellValue(program.getTitle());
        }

        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
