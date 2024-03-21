package Data;
/**
 * The type File utils.
 */

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;


public class FileUtils {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static ArrayList<String> getAllUsers(){
        String fileName = "phase2/res/Users.txt";
        String line = null;
        ArrayList<String > userNames = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                String[] UserInfo = line.split("\\\\");
                userNames.add(UserInfo[0]);
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
        return userNames;
    }

    public static boolean changePassword(String currentUser, String newPassword){
        String[] allUserInfo = read("phase2/res/Users.txt").toString().split("\\\\");
        boolean changed = false;
        for (int i = 0; i < allUserInfo.length; i++){
            if (allUserInfo[i].equalsIgnoreCase(currentUser)){
                allUserInfo[i+1] = newPassword;
                changed = true;
                break;
            }
        }
        if (!changed){
            return changed;
        }
        StringBuilder toWrite = new StringBuilder();
        for (int i = 0; i < allUserInfo.length; i++){
            toWrite.append(allUserInfo[i]);
            toWrite.append("\\");
        }
        cleanFile("phase2/res/Users.txt");
        write(toWrite.toString(),"Users");
        return changed;
    }

    public static void shareEvent(String currentUser, String targetUser, Event e, String message){
        String filePath = "phase2/res/" + targetUser + ".txt";
        String totalFile = read(filePath);
        String[] segment = totalFile.split("%\\\\%\\\\%");
        String toMod = segment[10];
        String added = currentUser + "\\" + e.getName() + "\\" + e.getStartTime().format(formatter) + "\\" + e.getEndTime().format(formatter) + "\\" + message + "\\";
        if(toMod.equalsIgnoreCase("----------")){
            toMod = added;
        }else {
            toMod = toMod + added;
        }
        cleanFile("phase2/res/" + targetUser + ".txt");
        for(int i = 0; i < 10; i++){
            String toWrite = "\n" + segment[i] + "%\\%\\%";
            write(toWrite, targetUser);
        }
        String toWrite = "\n" + toMod + "%\\%\\%";
        FileUtils.write(toWrite, targetUser);
    }

    public static ArrayList<String> getShared(String currentUser){
        ArrayList<String> re_var = new ArrayList<>();
        String filePath = "phase2/res/" + currentUser + ".txt";
        String totalFile = read(filePath);
        if(totalFile.isEmpty()){
            return re_var;
        }
        String[] segment = totalFile.split("%\\\\%\\\\%");
        String allShared = segment[10];
        if(allShared.equalsIgnoreCase("----------")){
            return re_var;
        }
        String[] infoParts = allShared.split("\\\\");
        re_var.addAll(Arrays.asList(infoParts));
        return re_var;
    }

    public static Map readUser() {
        String fileName = "phase2/res/Users.txt";
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
        cleanFile("phase2/res/" + U.getUserAccount() + ".txt");
        saveEventsToFile(U);
        saveTagsToFile(U);
        saveMemosToFile(U);
        saveSeriesToFile(U);
        saveEventAndTagToFile(U);
        saveEventAndMemoToFile(U);
        saveEventAndSeriesToFile(U);
        saveAlertsToFile(U);
        saveTodoItemToFile(U);
        saveTodoListToFile(U);
        writeEmptySharedEventToFile(U);
    }

    public static void loadDatabaseFromFile(UserFacade U){
        String filePath = "phase2/res/" + U.getUserAccount() + ".txt";
        String totalFile = read(filePath);
        if (totalFile.isEmpty()){
            return;
        }
        String[] segment = totalFile.split("%\\\\%\\\\%");
        try{
            String a = segment[10];
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
        loadTodoItems(U, segment[8]);
        loadTodolLists(U, segment[9]);
    }

    private static void saveTodoListToFile(UserFacade U){
        ArrayList<TodoList> allTodoList = U.getAllTodoList();
        StringBuilder toWrite = new StringBuilder();
        toWrite.append("\n");
        if(allTodoList.isEmpty()){
            toWrite.append("----------");
        }else {
            for (TodoList tl: allTodoList){
                toWrite.append("\\\\");
                toWrite.append(tl.getTodoId());
                toWrite.append("\\");
                toWrite.append(tl.getHeader());
                ArrayList<Integer> itemIds = tl.getTodoItemsId();
                if(itemIds.isEmpty()){
                    toWrite.append("\\");
                    toWrite.append("empty");
                }else {
                    for(Integer itemId: itemIds){
                        toWrite.append("\\");
                        toWrite.append(itemId);
                    }
                }
            }
        }
        toWrite.append("%\\%\\%");
        write(toWrite.toString(), U.getUserAccount());
    }

    private static void loadTodolLists(UserFacade U, String totalData){
        String[] pieces = totalData.split("\\\\\\\\");
        ArrayList<String> todoLists = new ArrayList<String>(Arrays.asList(pieces));
        todoLists.remove(0);
        for(String todoListIfo: todoLists){
            String[] infoParts = todoListIfo.split("\\\\");
            Integer todoListId = Integer.parseInt(infoParts[0]);
            String header = infoParts[1];
            if(infoParts[2].equalsIgnoreCase("empty")){
                U.readEmptyTodoList(todoListId, header);
            }else {
                ArrayList<Integer> ids = new ArrayList<Integer>();
                for(int i = 2; i<infoParts.length; i++){
                    ids.add(Integer.parseInt(infoParts[i]));
                }
                U.readTodoList(todoListId, header, ids);
            }

        }
    }

    private static void saveTodoItemToFile(UserFacade U){
        ArrayList<TodoItem> allTodoItems = U.getAllTodoItem();
        StringBuilder toWrite = new StringBuilder();
        toWrite.append("\n");
        if(allTodoItems.isEmpty()){
            toWrite.append("----------");
        }else {
            for(TodoItem ti: allTodoItems){
                toWrite.append("\\\\");
                toWrite.append(ti.gettItemId());
                toWrite.append("\\");
                toWrite.append(ti.getTodoListId());
                toWrite.append("\\");
                toWrite.append(ti.getEventId());
                toWrite.append("\\");
                toWrite.append(ti.getContent());
                toWrite.append("\\");
                toWrite.append(ti.getChecked());
            }
        }
        toWrite.append("%\\%\\%");
        write(toWrite.toString(), U.getUserAccount());
    }

    private static void loadTodoItems(UserFacade U, String totalData){
        String[] pieces = totalData.split("\\\\\\\\");
        ArrayList<String> todoItems = new ArrayList<String>(Arrays.asList(pieces));
        todoItems.remove(0);
        for(String todoItemInfo: todoItems){
            String[] infoParts = todoItemInfo.split("\\\\");
            int todoItemId = Integer.parseInt(infoParts[0]);
            int todoListId = Integer.parseInt(infoParts[1]);
            int eventId = Integer.parseInt(infoParts[2]);
            String content = infoParts[3];
            boolean checked;
            if(infoParts[4].equalsIgnoreCase("true")){
                checked = true;
            }else {
                checked = false;
            }
            U.readTodoItem(todoItemId, todoListId, eventId, content, checked);
        }
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
        PriorityQueue<Event> allEvents = U.getAllEvents();
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

    private static void writeEmptySharedEventToFile(UserFacade U){
        StringBuilder toWrite = new StringBuilder();
        toWrite.append("\n");
        toWrite.append("----------");
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
            File file = new File("phase2/res/" + path + ".txt");
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
