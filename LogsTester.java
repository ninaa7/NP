//package Kolokviumski2;
//
//import java.time.LocalDateTime;
//import java.util.*;
//
//enum Type {
//    INFO,
//    WARN,
//    ERROR
//}
//
//class Log {
//    String name;
//    String microserviceName;
//    Type type;
//    String message;
//    LocalDateTime timestamp;
//
//    public Log(String name) {
//        this.name = name;
//        this.microserviceName = "";
//        this.type = Type.ERROR;
//        this.message = "";
//        this.timestamp = null;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getMicroserviceName() {
//        return microserviceName;
//    }
//
//    public Type getType() {
//        return type;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public LocalDateTime getTimestamp() {
//        return timestamp;
//    }
//
//    public int calculateSeverity (){
//        if (type==Type.INFO) {
//            return 0;
//        } else if (type == Type.WARN) {
//            if(message.contains("might cause error")){
//                return 2;
//            } else
//                return 1;
//        } else {
//            if(message.contains("fatal")) {
//                return 5;
//            } else if (message.contains("exception")) {
//                return 6;
//            } else
//                return 3;
//        }
//    }
//}
//
//class LogCollector {
//    Map<String, List<Log>> logs;
//
//    public LogCollector() {
//        this.logs = new HashMap<>();
//    }
//
//    public void addLog(String log) {
//
//    }
//
//    public void printServicesBySeverity() {
//        logs.stream()
//                .sorted(Comparator.comparing(Log::calculateSeverity).reversed())
//                .forEach(x-> System.out.printf("Service name: %s Count of microservices: %d Total logs in service: %d Average severity for all logs: %.2f Average number of logs per microservice: %.2f",x.name,x));
//    }
//
//    public Map<Object, Object> getSeverityDistribution(String service, String microservice) {
//
//    }
//
//    public void displayLogs(String service, String microservice, String order) {
//
//    }
//
//    public double averageSeverity () {
//        return logs.stream().mapToInt(i->i.calculateSeverity()).sum() / logs.size();
//    }
//}
//
//public class LogsTester {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        LogCollector collector = new LogCollector();
//        while (sc.hasNextLine()) {
//            String line = sc.nextLine();
//            if (line.startsWith("addLog")) {
//                collector.addLog(line.replace("addLog ", ""));
//            } else if (line.startsWith("printServicesBySeverity")) {
//                collector.printServicesBySeverity();
//            } else if (line.startsWith("getSeverityDistribution")) {
//                String[] parts = line.split("\\s+");
//                String service = parts[1];
//                String microservice = null;
//                if (parts.length == 3) {
//                    microservice = parts[2];
//                }
//                collector.getSeverityDistribution(service, microservice).forEach((k,v)-> System.out.printf("%d -> %d%n", k,v));
//            } else if (line.startsWith("displayLogs")){
//                String[] parts = line.split("\\s+");
//                String service = parts[1];
//                String microservice = null;
//                String order = null;
//                if (parts.length == 4) {
//                    microservice = parts[2];
//                    order = parts[3];
//                } else {
//                    order = parts[2];
//                }
//                collector.displayLogs(service, microservice, order);
//            }
//        }
//    }
//}
