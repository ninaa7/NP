//package Kolokviumski2;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.*;
//
//interface ILocation{
//    double getLongitude();
//
//    double getLatitude();
//
//    LocalDateTime getTimestamp();
//}
//
//class UserAlreadyExistException extends Exception {
//
//}
//
//class User {
//    String name;
//    String id;
//    List<ILocation> iLocations;
//    LocalDateTime timeStamp;
//
//    public User(String name, String id) {
//        this.name = name;
//        this.id = id;
//        timeStamp = null;
//    }
//
//    public void addLocation (List<ILocation> locations) {
//        this.iLocations.addAll(locations);
//    }
//
//    public void register (LocalDateTime timestamp) {
//        timeStamp = timestamp;
//    }
//
//
//    public boolean isInfected () {
//        return timeStamp != null;
//    }
//
//    public boolean hasCloseContact (ILocation iLocation1, ILocation iLocation2) {
//        double euclideanDistance = Math.sqrt(Math.pow(iLocation1.getLongitude() - iLocation2.getLongitude(), 2) + Math.pow(iLocation1.getLatitude() - iLocation2.getLongitude(), 2));
//        long timeDifferenceInSeconds = Math.abs(Duration.between(iLocation1.getTimestamp(), iLocation2.getTimestamp()).getSeconds());
//        return euclideanDistance <= 2 && timeDifferenceInSeconds < 300;
//    }
//
//    public int numberOfContacts (User u) {
//        int counter=0;
//        for (ILocation location1 : this.iLocations) {
//            for (ILocation location2 : u.iLocations) {
//                if (hasCloseContact(location1,location2)) {
//                    counter++;
//                }
//            }
//        }
//        return counter;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getId() {
//        return id;
//    }
//}
//
//class StopCoronaApp {
//    List<User> users;
//
//    public StopCoronaApp() {
//        this.users = new ArrayList<>();
//    }
//
//    public void addUser(String name, String id) throws UserAlreadyExistException {
//        User user = new User(name,id);
//        if (users.contains(user)) {
//            throw new  UserAlreadyExistException();
//        }
//        users.add(user);
//    }
//
//    public void addLocations(String id, List<ILocation> iLocations) {
//        users.stream().filter(i->i.id.equals(id)).forEach(user -> user.addLocation(iLocations));
//    }
//
//    public void detectNewCase(String id, LocalDateTime timestamp) {
//        users.stream().filter(i->i.id.equals(id)).forEach(user -> user.register(timestamp));
//    }
//
//    public Map<User, Integer> getDirectContacts (User u) {
//        users.stream().filter(user -> !user.equals(u) && user.)
//    }
//
//    public Collection<User> getIndirectContacts (User u) {
//
//    }
//
//    public Map<User, Integer> getDirectContacts (User u) {
//        //users.stream().map()
//    }
//
//    public Collection<User> getIndirectContacts (User u) {
//
//    }
//
//    public double timeBetweenInSeconds(ILocation location1, ILocation location2) {
//        return Math.abs(Duration.between(location1.getTimestamp(), location2.getTimestamp()).getSeconds());
//    }
//
//    public void createReport() {
//
//    }
//}
//
//public class StopCoronaTest {
//
//
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//
//        StopCoronaApp stopCoronaApp = new StopCoronaApp();
//
//        while (sc.hasNext()) {
//            String line = sc.nextLine();
//            String[] parts = line.split("\\s+");
//
//            switch (parts[0]) {
//                case "REG": //register
//                    String name = parts[1];
//                    String id = parts[2];
//                    try {
//                        stopCoronaApp.addUser(name, id);
//                    } catch (UserAlreadyExistException e) {
//                        System.out.println(e.getMessage());
//                    }
//                    break;
//                case "LOC": //add locations
//                    id = parts[1];
//                    List<ILocation> locations = new ArrayList<>();
//                    for (int i = 2; i < parts.length; i += 3) {
//                        locations.add(createLocationObject(parts[i], parts[i + 1], parts[i + 2]));
//                    }
//                    stopCoronaApp.addLocations(id, locations);
//
//                    break;
//                case "DET": //detect new cases
//                    id = parts[1];
//                    LocalDateTime timestamp = LocalDateTime.parse(parts[2]);
//                    stopCoronaApp.detectNewCase(id, timestamp);
//
//                    break;
//                case "REP": //print report
//                    stopCoronaApp.createReport();
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//
//    private static ILocation createLocationObject(String lon, String lat, String timestamp) {
//        return new ILocation() {
//            @Override
//            public double getLongitude() {
//                return Double.parseDouble(lon);
//            }
//
//            @Override
//            public double getLatitude() {
//                return Double.parseDouble(lat);
//            }
//
//            @Override
//            public LocalDateTime getTimestamp() {
//                return LocalDateTime.parse(timestamp);
//            }
//        };
//    }
//}
//
//
