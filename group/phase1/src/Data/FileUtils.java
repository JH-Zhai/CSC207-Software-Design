package Data;
/**
 * The type File utils.
 */

import org.omg.PortableInterceptor.INACTIVE;

import java.io.*;
import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;


public class FileUtils {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Map readUser() {
        String fileName = "phase1/res/Users.txt";
        String line = null;
        Map<String,String> map = new HashMap<String,String>();
//        Dictionary dic = new Hashtable();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                String[] UserInfo = line.split("\\\\");
//                dic.put(UserInfo[0],UserInfo[1]);
                map.put(UserInfo[0],UserInfo[1]);
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '" + fileName + "'");
        }
        return map;
    }

    public static void saveDatabaseToFile(UserFacade U){
        cleanFile("phase1/res/" + U.getUserAccount() + ".txt");
        saveEventsToFile(U);
        saveTagsToFile(U);
        saveMemosToFile(U);
        saveSeriesToFile(U);
        saveEventAndTagToFile(U);
        saveEventAndMemoToFile(U);
        saveEventAndSeriesToFile(U);
        saveAlertsToFile(U);
    }

    public static void loadDatabaseFromFile(UserFacade U){
        String filePath = "phase1/res/" + U.getUserAccount() + ".txt";
        String totalFile = read(filePath);
        if (totalFile.isEmpty()){
            return;
        }
        String[] segment = totalFile.split("%\\\\%\\\\%");
        try{
            String a = segment[7];
        }catch (IndexOutOfBoundsException ex){
            System.out.println("Data corruption!!!");
        }
        loadEvents(U, segment[0]);
        loadTags(U, segment[1]);
        loadMemos(U, segment[2]);
        loadSeries(U, segment[3]);
        loadEventAndTag(U, segment[4]);
        loadEventAndMemo(U, segment[5]);
        loadEventAndSeries(U, segment[6]);
        loadAlerts(U, segment[7]);
    }

    private static void loadEvents(UserFacade U, String totalData){
        String[] pieces = totalData.split("\\\\\\\\");
        ArrayList<String> events = new ArrayList<String>(Arrays.asList(pieces));
        events.remove(0);
        for(String eventInfo: events){
            String[] infoParts = eventInfo.split("\\\\");
            int id = Integer.parseInt(infoParts[0]);
            String name = infoParts[1];
            LocalDateTime starTime = LocalDateTime.parse(infoParts[2], formatter);
            LocalDateTime endTime = LocalDateTime.parse(infoParts[3], formatter);
            U.readEvent(name, starTime, endTime, id);
        }
    }

    private static void saveEventsToFile(UserFacade U){
        ArrayList<Event> allEvents = U.getAllEvents();
        StringBuilder toWrite = new StringBuilder();
        toWrite.append("\n");
        if(allEvents.isEmpty()){
            toWrite.append("----------");
        }else {
            for(Event e: allEvents){
                toWrite.append("\\\\");
                toWrite.append(e.getEid());
                toWrite.append("\\");
                toWrite.append(e.getName());
                toWrite.append("\\");
                toWrite.append(e.getStartTime().format(formatter));
                toWrite.append("\\");
                toWrite.append(e.getEndTime().format(formatter));
            }
        }
        toWrite.append("%\\%\\%");
        write(toWrite.toString(), U.getUserAccount());
    }

    private static void loadTags(UserFacade U, String totalData){
        System.out.println(totalData);
        if(totalData.contains("----------")){
            return;
        }
        String[] pieces = totalData.split("\\\\\\\\");
        ArrayList<String> tags = new ArrayList<String>(Arrays.asList(pieces));
        tags.remove(0);
        for(String tagtInfo: tags){
            String[] infoParts = tagtInfo.split("\\\\");
            int id = Integer.parseInt(infoParts[0]);
            String name = infoParts[1];
            U.readTag(name, id);
        }

    }

    private static void saveTagsToFile(UserFacade U){
        ArrayList<Tag> allTags = U.getAllTag();
        StringBuilder toWrite = new StringBuilder();
        toWrite.append("\n");
        if(allTags.isEmpty()){
            toWrite.append("----------");
        }else {
            for(Tag t: allTags){
                toWrite.append("\\\\");
                toWrite.append(t.getId());
                toWrite.append("\\");
                toWrite.append(t.getTitle());
            }
        }
        toWrite.append("%\\%\\%");
        write(toWrite.toString(), U.getUserAccount());
    }

    private static void loadMemos(UserFacade U, String totalData){
        if(totalData.contains("----------")){
            return;
        }
        String[] pieces = totalData.split("\\\\\\\\");
        ArrayList<String> memos = new ArrayList<String>(Arrays.asList(pieces));
        memos.remove(0);
        for(String memoInfo: memos){
            String[] infoParts = memoInfo.split("\\\\");
            int id = Integer.parseInt(infoParts[0]);
            String name = infoParts[1];
            String content = infoParts[2];
            U.readMemo(name, id);
            Memo newMemo = U.getMemoById(id);
            U.addContent(newMemo, content);
        }
    }

    private static void saveMemosToFile(UserFacade U){
        ArrayList<Memo> allMemos = U.getAllMemo();
        StringBuilder toWrite = new StringBuilder();
        toWrite.append("\n");
        if (allMemos.isEmpty()){
            toWrite.append("----------");
        }else {
            for(Memo m: allMemos){
                toWrite.append("\\\\");
                toWrite.append(m.getId());
                toWrite.append("\\");
                toWrite.append(m.getTitle());
                toWrite.append("\\");
                toWrite.append(m.getContent());
            }
        }
        toWrite.append("%\\%\\%");
        write(toWrite.toString(), U.getUserAccount());
    }

    private static void loadSeries(UserFacade U, String totalData){
        if(totalData.contains("----------")){
            return;
        }
        String[] pieces = totalData.split("\\\\\\\\");
        ArrayList<String> serieses = new ArrayList<String>(Arrays.asList(pieces));
        serieses.remove(0);
        for(String sereiesInfo: serieses){
            String[] infoParts = sereiesInfo.split("\\\\");
            int id = Integer.parseInt(infoParts[0]);
            String name = infoParts[1];
            U.readSeries(name, id);
        }
    }

    private static void saveSeriesToFile(UserFacade U){
        ArrayList<Series> allSeries = U.getAllSeries();
        StringBuilder toWrite = new StringBuilder();
        toWrite.append("\n");
        if(allSeries.isEmpty()){
            toWrite.append("----------");
        }else {
            for(Series s: allSeries){
                toWrite.append("\\\\");
                toWrite.append(s.getId());
                toWrite.append("\\");
                toWrite.append(s.getName());
            }
        }
        toWrite.append("%\\%\\%");
        write(toWrite.toString(), U.getUserAccount());
    }

    private static void loadEventAndTag(UserFacade U, String totalData){
        if(totalData.contains("----------")){
            return;
        }
        String[] pieces = totalData.split("\\\\\\\\");
        ArrayList<String> eNts = new ArrayList<String>(Arrays.asList(pieces));
        eNts.remove(0);
        for(String eNtInfo: eNts){
            String[] infoParts = eNtInfo.split("\\\\");
            int eid = Integer.parseInt(infoParts[0]);
            int tid = Integer.parseInt(infoParts[1]);
            Event newEvent = U.getEventByid(eid);
            Tag newTag = U.getTagById(tid);
            U.addTag(newTag, newEvent);
        }
    }

    private static void saveEventAndTagToFile(UserFacade U){
        ArrayList<EventAndTag> allEventAndTag = U.getAllEventAndTag();
        StringBuilder toWrite = new StringBuilder();
        toWrite.append("\n");
        if(allEventAndTag.isEmpty()){
            toWrite.append("----------");
        }else {
            for(EventAndTag eNt: allEventAndTag){
                toWrite.append("\\\\");
                toWrite.append(eNt.getEventId());
                toWrite.append("\\");
                toWrite.append(eNt.getTagId());
            }
        }
        toWrite.append("%\\%\\%");
        write(toWrite.toString(), U.getUserAccount());
    }

    private static void loadEventAndMemo(UserFacade U, String totalData){
        if(totalData.contains("----------")){
            return;
        }
        String[] pieces = totalData.split("\\\\\\\\");
        ArrayList<String> eNms = new ArrayList<String>(Arrays.asList(pieces));
        eNms.remove(0);
        for(String eNmInfo: eNms){
            String[] infoParts = eNmInfo.split("\\\\");
            int eid = Integer.parseInt(infoParts[0]);
            int mid = Integer.parseInt(infoParts[1]);
            Event newEvent = U.getEventByid(eid);
            Memo newMemo = U.getMemoById(mid);
            U.addMemo(newMemo, newEvent);
        }
    }

    private static void saveEventAndMemoToFile(UserFacade U){
        ArrayList<EventAndMemo> allEventAndMemo = U.getAllEventAndMemo();
        StringBuilder toWrite = new StringBuilder();
        toWrite.append("\n");
        if(allEventAndMemo.isEmpty()){
            toWrite.append("----------");
        }else {
            for(EventAndMemo eNm: allEventAndMemo){
                toWrite.append("\\\\");
                toWrite.append(eNm.getEventId());
                toWrite.append("\\");
                toWrite.append(eNm.getMemoId());
            }
        }
        toWrite.append("%\\%\\%");
        write(toWrite.toString(), U.getUserAccount());
    }

    private static void loadEventAndSeries(UserFacade U, String totalData){
        if(totalData.contains("----------")){
            return;
        }
        String[] pieces = totalData.split("\\\\\\\\");
        ArrayList<String> eNss = new ArrayList<String>(Arrays.asList(pieces));
        eNss.remove(0);
        for(String eNsInfo: eNss){
            String[] infoParts = eNsInfo.split("\\\\");
            int eid = Integer.parseInt(infoParts[0]);
            int sid = Integer.parseInt(infoParts[1]);
            Event newEvent = U.getEventByid(eid);
            Series newSeries = U.getSeriesById(sid);
            U.linkSingleEventToSeries(newEvent, newSeries);
        }
    }

    private static void saveEventAndSeriesToFile(UserFacade U){
        ArrayList<EventAndSeries> allEventAndSeries = U.getAllEventAndSeries();
        StringBuilder toWrite = new StringBuilder();
        toWrite.append("\n");
        if(allEventAndSeries.isEmpty()){
            toWrite.append("----------");
        }else {
            for(EventAndSeries eNs: allEventAndSeries){
                toWrite.append("\\\\");
                toWrite.append(eNs.getEventId());
                toWrite.append("\\");
                toWrite.append(eNs.getSeriesId());
            }
        }
        toWrite.append("%\\%\\%");
        write(toWrite.toString(), U.getUserAccount());
    }

    private static void loadAlerts(UserFacade U, String totalData){
        if(totalData.contains("----------")){
            return;
        }
        String[] pieces = totalData.split("\\\\\\\\");
        ArrayList<String> alerts = new ArrayList<String>(Arrays.asList(pieces));
        alerts.remove(0);
        for(String alertInfo: alerts){
            String[] infoParts = alertInfo.split("\\\\");
            int eid = Integer.parseInt(infoParts[0]);
            String type = infoParts[1];
            Duration alertDuration = StringToDuration(infoParts[2]);
            Event newEvent = U.getEventByid(eid);
            if (type.equals("S")){  //SINGLE ALERT
                U.createAlert(newEvent, false, alertDuration);
            }else {
                U.createAlert(newEvent, true, alertDuration);
            }
        }
    }

    private static void saveAlertsToFile(UserFacade U){
        ArrayList<Alert> allAlerts = U.getAllAlert();
        StringBuilder toWrite = new StringBuilder();
        toWrite.append("\n");
        if(allAlerts.isEmpty()){
            toWrite.append("----------");
        }else {
            for(Alert a: allAlerts){
                toWrite.append("\\\\");
                toWrite.append(a.getEventId());
                toWrite.append("\\");
                if (a instanceof SingleAlert){
                    toWrite.append("S");
                } else {
                    toWrite.append("R");
                }
                toWrite.append("\\");
                toWrite.append(a.getDuration().toString());
            }
        }
        toWrite.append("%\\%\\%");
        write(toWrite.toString(), U.getUserAccount());
    }

    private static void cleanFile(String Path) {
        try {
            PrintWriter writer = new PrintWriter(Path);
            writer.print("");
            writer.close();
        }
        catch(FileNotFoundException ex) { System.out.println("Unable to open file '" + Path + "'");
        }
    }

    private static void write(String toWrite, String path){
        try {
            File file = new File("phase1/res/" + path + ".txt");
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            br.write(toWrite + "\n" + "\n");
            br.close();
            fr.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + path + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + path + "'");
        }
    }

    private static String read(String filePath) {
        StringBuilder re_var = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                re_var.append(line);
            }

            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePath + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + filePath + "'");
        }
//        System.out.println(re_var);
        return re_var.toString();
    }

    private static Duration StringToDuration(String str){
        int number = Integer.parseInt(str.substring(2, str.length()-1));
        Character type = str.charAt(str.length()-1);
        Duration re_var;
        if (type.equals('H')){
            re_var = Duration.ofHours(number);
        }else {
            re_var = Duration.ofMinutes(number);
        }
        return re_var;

    }

}
